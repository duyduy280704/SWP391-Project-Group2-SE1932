package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import models.ProfileDAO;
import models.AdminStaffs;
import models.Students;
import models.Teachers;

// Cấu hình để xử lý file upload (ảnh đại diện)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)

@WebServlet("/profile")
public class ProfileController extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy phone từ session dựa trên loại tài khoản
        String phone = getPhoneFromAccount(account);
        if (phone == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Tạo đối tượng DAO để lấy dữ liệu từ database
        ProfileDAO profileDAO = new ProfileDAO();
        String action = request.getParameter("action"); // Xác định hành động (view hoặc update)
        String message = ""; // Thông báo cho người dùng

        try {
            // Xử lý hành động "view" để xem thông tin
            if ("view".equals(action)) {
                Object profile = getProfileData(profileDAO, account, phone);
                if (profile == null) {
                    message = "Không tìm thấy thông tin cá nhân cho email: " + phone;
                }
                request.setAttribute("phone", phone);
                request.setAttribute("profile", profile);
            } // Xử lý hành động "update" để cập nhật thông tin
            else if ("update".equals(action)) {
                // Lấy dữ liệu từ form
                
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String gender = request.getParameter("gender");
                String address = request.getParameter("address");
                String birthDate = request.getParameter("birthDate");
                String expertise = request.getParameter("expertise");
                Integer yearsOfExperience = getYearsOfExperience(request);
                String picturePath = handleFileUpload(request);
                String newPhone = request.getParameter("newPhone");
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmPassword");

                boolean hasPersonalUpdate = fullName != null || gender != null || birthDate != null || address != null || expertise != null || yearsOfExperience != null || picturePath != null||email!=null;
                if (hasPersonalUpdate) {
                    if (profileDAO.updateProfile(phone,  fullName, gender, address, birthDate, expertise, yearsOfExperience, picturePath,email)) {
                        message = "Cập nhật thông tin cá nhân thành công!" ;
                        request.setAttribute("phone", phone);
                    } else {
                        message = "Cập nhật thông tin cá nhân thất bại!" ;
                        request.setAttribute("phone", phone);
                    }
                }

                // 2. Xử lý thay đổi phone (nếu có)
                if (newPhone != null && !newPhone.equals(email)) {
                    if (profileDAO.updatePhone(phone, newPhone)) {
                        email = newPhone; // Cập nhật phone trong session
                        Object updatedAccount = profileDAO.getAccountByPhone(phone);
                        if (updatedAccount != null) {
                            session.setAttribute("account", updatedAccount);
                            message += " Số điện thoại đã được cập nhật thành công!";
                        } else {
                            message += " Không thể cập nhật session sau khi đổi số điện thoại!";
                        }
                    } else {
                        message += " Cập nhật số điện thoại thất bại!";
                    }
                }

                // 3. Xử lý thay đổi mật khẩu (nếu có)
                if (newPassword != null && !newPassword.isEmpty()) {
                    boolean isValid = true;
                    if (oldPassword == null || oldPassword.isEmpty()) {
                        message = "Vui lòng nhập mật khẩu cũ!";
                        isValid = false;
                    } else if (!profileDAO.verifyPassword(phone, oldPassword)) {
                        message = "Mật khẩu cũ không đúng!";
                        isValid = false;
                    } else if (!newPassword.equals(confirmPassword)) {
                        message = "Mật khẩu mới và xác nhận không khớp!";
                        isValid = false;
                    } else if (newPassword.length() <= 6 || !newPassword.matches(".*[a-z].*")
                            || !newPassword.matches(".*[A-Z].*") || !newPassword.matches(".*\\d.*") || !newPassword.matches(".*[^a-zA-Z0-9].*")) {
                        message = "Mật khẩu phải trên 6 ký tự, có ít nhất 1 chữ thường, 1 chữ in hoa, 1 số và 1 ký hiệu đặc biệt";
                        isValid = false;
                    }

                    if (isValid) {
                        if (profileDAO.updatePassword(phone, newPassword)) {
                            message = "Cập nhật mật khẩu thành công!";
                        } else {
                            message = "Cập nhật mật khẩu thất bại!";
                        }
                    }
                    request.setAttribute("phone", phone);
                    request.setAttribute("message", message);
                }
                // Lấy lại dữ liệu sau khi cập nhật
                Object profile = getProfileData(profileDAO, account, email);
                request.setAttribute("profile", profile);
            }
        } catch (Exception e) {
            message = "Đã xảy ra lỗi: " + e.getMessage();
            e.printStackTrace(); // In lỗi chi tiết để debug
        } finally {
            // Gán thông báo và forward đến JSP
            request.setAttribute("message", message);
            profileDAO.closeConnection(); // Đóng kết nối database
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    // Lấy email từ đối tượng account dựa trên loại tài khoản
    private String getPhoneFromAccount(Object account) {
        if (account instanceof AdminStaffs) {
            return ((AdminStaffs) account).getPhone();
        } else if (account instanceof Students) {
            return ((Students) account).getPhone();
        } else if (account instanceof Teachers) {
            return ((Teachers) account).getPhone();
        }
        return null;
    }

    // Lấy dữ liệu profile từ database dựa trên loại tài khoản
    private Object getProfileData(ProfileDAO dao, Object account, String phone) {
        if (account instanceof Teachers) {
            return dao.getProfile(phone);
        } else if (account instanceof Students) {
            return dao.getProfileStudent(phone);
        } else if (account instanceof AdminStaffs) {
            return dao.getProfileStaff(phone);
        }
        return null;
    }

    // Lấy số năm kinh nghiệm từ request (nếu có)
    private Integer getYearsOfExperience(HttpServletRequest request) {
        String years = request.getParameter("yearsOfExperience");
        return years != null ? Integer.parseInt(years) : null;
    }

    // Xử lý upload file ảnh
    private String handleFileUpload(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("picture");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = extractFileName(filePart);
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String picturePath = UPLOAD_DIR + File.separator + fileName;
            filePart.write(uploadPath + File.separator + fileName);
            return picturePath;
        }
        return null;
    }

    // Trích xuất tên file từ header upload
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "ProfileController for managing user profile";
    }
}
