package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import models.*;

@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class ProfileController extends HttpServlet {

    private static final String UPLOAD_DIR = "Uploads";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");

        // Kiểm tra đăng nhập
        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String message = "";

        // Lấy số điện thoại và vai trò từ account
        String roleId = getRoleId(account);
        String phone = getPhone(account);
        if (phone == null || roleId == null || !isValidRoleId(roleId)) {
            message = "Thông tin tài khoản không hợp lệ!";
            request.setAttribute("message", message);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Lưu roleId vào session để profile.jsp sử dụng
        session.setAttribute("roleId", roleId);

        Object profile = getProfile(roleId, phone);

        try {
            String action = request.getParameter("action");
            String contentType = request.getContentType();

            // Debug các tham số
            System.out.println("Action: " + action);
            System.out.println("Content-Type: " + contentType);
            System.out.println("RoleId: " + roleId);

            if (action == null || action.trim().isEmpty()) {
                message = "Hành động không được xác định!";
            } else if ("updateAccount".equals(action)) {
                // Cập nhật thông tin tài khoản
                String newPhone = request.getParameter("newPhone");
                String oldPassword = request.getParameter("oldPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmPassword");

                // Kiểm tra dữ liệu đầu vào
                if ((newPhone != null && !newPhone.equals(phone)) || (newPassword != null && !newPassword.isEmpty())) {
                    boolean verified = verifyPassword(roleId, phone, oldPassword);
                    if (newPassword != null && !newPassword.isEmpty() && !verified) {
                        message = "Mật khẩu cũ không đúng!";
                    } else if (!newPassword.equals(confirmPassword)) {
                        message = "Mật khẩu xác nhận không khớp!";
                    } else if (newPhone != null && !newPhone.matches("\\d{10,11}")) {
                        message = "Số điện thoại phải có 10-11 số!";
                    } else if (newPassword != null && !newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$")) {
                        message = "Mật khẩu phải trên 6 ký tự, có chữ thường, in hoa, số và ký hiệu!";
                    } else {
                        boolean updated = updateAccount(roleId, phone, newPhone, newPassword);
                        if (updated && newPhone != null && !newPhone.equals(phone)) {
                            phone = newPhone;
                            profile = getProfile(roleId, newPhone);
                            session.setAttribute("account", profile);
                        }
                        message = updated ? "Cập nhật thông tin tài khoản thành công!" : "Cập nhật thông tin tài khoản thất bại!";
                    }
                } else {
                    message = "Không có thay đổi để cập nhật tài khoản!";
                }
                profile = getProfile(roleId, phone);
                session.setAttribute("name", getName(profile, roleId));
            } else if ("updatePersonalInfo".equals(action)) {
                // Cập nhật thông tin cá nhân
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String birthDate = request.getParameter("birthDate");
                String gender = request.getParameter("gender");
                String expertise = request.getParameter("expertise");
                String idTypeCourse = request.getParameter("idTypeCourse");
                String yearsOfExperience = request.getParameter("yearsOfExperience");
                String address = request.getParameter("address");

                // Debug các tham số
                System.out.println("fullName: " + fullName);
                System.out.println("email: " + email);
                System.out.println("birthDate: " + birthDate);
                System.out.println("gender: " + gender);
                System.out.println("expertise: " + expertise);
                System.out.println("idTypeCourse: " + idTypeCourse);
                System.out.println("yearsOfExperience: " + yearsOfExperience);
                System.out.println("address: " + address);

                byte[] pictureBytes = null;
                // Xử lý file ảnh nếu có (chỉ áp dụng cho roleId=1 hoặc 2)
                if (("1".equals(roleId) || "2".equals(roleId)) && contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
                    pictureBytes = getPictureBytes(request, phone, roleId);
                }

                // Nếu không có ảnh mới, lấy ảnh hiện tại
                if (pictureBytes == null && ("1".equals(roleId) || "2".equals(roleId))) {
                    ProfileDAO dao = new ProfileDAO();
                    try {
                        switch (roleId) {
                            case "1":
                                Students student = dao.getStudentByPhone(phone);
                                pictureBytes = student != null ? student.getPic() : null;
                                break;
                            case "2":
                                Teachers teacher = dao.getTeacherByPhone(phone);
                                pictureBytes = teacher != null ? teacher.getPic() : null;
                                break;
                        }
                    } finally {
                        dao.closeResources();
                    }
                }

                // Kiểm tra dữ liệu đầu vào
                if (fullName == null || fullName.trim().isEmpty()) {
                    message = "Họ tên không được để trống!";
                } else if (!isValidEmail(email)) {
                    message = "Email không hợp lệ!";
                } else if (!isValidBirthDate(birthDate)) {
                    message = "Ngày sinh không hợp lệ!";
                } else if (gender == null || gender.trim().isEmpty()) {
                    message = "Giới tính không được để trống!";
                } else if ("2".equals(roleId) && (expertise == null || expertise.trim().isEmpty())) {
                    message = "Chuyên môn không được để trống!";
                } else if ("2".equals(roleId) && (idTypeCourse == null || idTypeCourse.trim().isEmpty() || !isValidNumber(idTypeCourse))) {
                    message = "Loại khóa học không hợp lệ!";
                } else if ("2".equals(roleId) && (yearsOfExperience == null || yearsOfExperience.trim().isEmpty() || !isValidYearsOfExperience(yearsOfExperience))) {
                    message = "Kinh nghiệm không hợp lệ!";
                } else if ("1".equals(roleId) && (address == null || address.trim().isEmpty())) {
                    message = "Địa chỉ không được để trống!";
                } else {
                    ProfileDAO dao = new ProfileDAO();
                    boolean updated = false;
                    try {
                        switch (roleId) {
                            case "1": // Student
                                Students student = new Students(null, fullName, email, null, birthDate, gender, address, roleId, phone, pictureBytes);
                                updated = dao.updateStudent(student);
                                break;
                            case "2": // Teacher
                                Teachers teacher = new Teachers(null, fullName, email, null, birthDate, gender, expertise, pictureBytes, roleId, idTypeCourse, yearsOfExperience, phone);
                                updated = dao.updateTeacher(teacher);
                                break;
                            case "3": // Staff
                            case "4": // Admin
                                AdminStaffs staff = new AdminStaffs(null, fullName, email, null, birthDate, gender, roleId, phone);
                                updated = dao.updateStaff(staff);
                                break;
                        }
                        if (updated) {
                            profile = getProfile(roleId, phone);
                            session.setAttribute("name", getName(profile, roleId));
                            session.setAttribute("account", profile);
                            if ("1".equals(roleId) || "2".equals(roleId)) {
                                setProfilePicture(request, session, profile, roleId, phone);
                            }
                            message = "Cập nhật thông tin cá nhân thành công!";
                        } else {
                            message = "Cập nhật thông tin cá nhân thất bại!";
                        }
                    } finally {
                        dao.closeResources();
                    }
                }
            } else {
                message = "Yêu cầu không hợp lệ!";
            }
        } catch (Exception e) {
            message = "Lỗi xử lý yêu cầu: " + e.getMessage();
            e.printStackTrace();
        } finally {
            request.setAttribute("profile", profile);
            request.setAttribute("message", message);
            request.setAttribute("picturePath", session.getAttribute("picturePath_" + roleId)); // Phân biệt picturePath theo roleId
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    // Kiểm tra roleId hợp lệ
    private boolean isValidRoleId(String roleId) {
        return "1".equals(roleId) || "2".equals(roleId) || "3".equals(roleId) || "4".equals(roleId);
    }

    // Lấy số điện thoại từ account
    private String getPhone(Object account) {
        if (account == null) return null;
        if (account instanceof Students) {
            return ((Students) account).getPhone();
        } else if (account instanceof Teachers) {
            return ((Teachers) account).getPhone();
        } else if (account instanceof AdminStaffs) {
            return ((AdminStaffs) account).getPhone();
        }
        return null;
    }

    // Lấy roleId từ account
    private String getRoleId(Object account) {
        if (account == null) return null;
        if (account instanceof Students) {
            return ((Students) account).getRole();
        } else if (account instanceof Teachers) {
            return ((Teachers) account).getRole();
        } else if (account instanceof AdminStaffs) {
            return ((AdminStaffs) account).getRole();
        }
        return null;
    }

    // Lấy tên từ hồ sơ
    private String getName(Object profile, String roleId) {
        if (profile == null) return null;
        switch (roleId) {
            case "1": return ((Students) profile).getName();
            case "2": return ((Teachers) profile).getName();
            case "3":
            case "4": return ((AdminStaffs) profile).getName();
            default: return null;
        }
    }

    // Lấy hồ sơ từ cơ sở dữ liệu
    private Object getProfile(String roleId, String phone) {
        ProfileDAO dao = new ProfileDAO();
        try {
            switch (roleId) {
                case "1": return dao.getStudentByPhone(phone);
                case "2": return dao.getTeacherByPhone(phone);
                case "3":
                case "4": return dao.getStaffByPhone(phone);
                default: return null;
            }
        } finally {
            dao.closeResources();
        }
    }

    // Xác minh mật khẩu
    private boolean verifyPassword(String roleId, String phone, String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        ProfileDAO dao = new ProfileDAO();
        try {
            switch (roleId) {
                case "1": return dao.verifyStudentPassword(phone, password);
                case "2": return dao.verifyTeacherPassword(phone, password);
                case "3":
                case "4": return dao.verifyStaffPassword(phone, password);
                default: return false;
            }
        } finally {
            dao.closeResources();
        }
    }

    // Cập nhật thông tin tài khoản
    private boolean updateAccount(String roleId, String oldPhone, String newPhone, String newPassword) {
        ProfileDAO dao = new ProfileDAO();
        try {
            switch (roleId) {
                case "1": return dao.updateStudentCredentials(oldPhone, newPhone, newPassword);
                case "2": return dao.updateTeacherCredentials(oldPhone, newPhone, newPassword);
                case "3":
                case "4": return dao.updateStaffCredentials(oldPhone, newPhone, newPassword);
                default: return false;
            }
        } finally {
            dao.closeResources();
        }
    }

    // Xử lý ảnh đại diện
    private void setProfilePicture(HttpServletRequest request, HttpSession session, Object profile, String roleId, String phone) throws IOException {
        byte[] picture = null;
        switch (roleId) {
            case "1": picture = ((Students) profile).getPic(); break;
            case "2": picture = ((Teachers) profile).getPic(); break;
            case "3": // Staff không có ảnh
            case "4": // Admin không có ảnh
                return; // Bỏ qua nếu là Staff hoặc Admin
        }
        if (picture != null) {
            String prefix = "1".equals(roleId) ? "student_" : "teacher_"; // Phân biệt Student và Teacher
            String tempPath = UPLOAD_DIR + File.separator + prefix + phone + ".jpg";
            String realPath = getServletContext().getRealPath("") + File.separator + tempPath;
            File uploadDir = new File(getServletContext().getRealPath("") + File.separator + UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // Sử dụng buffer để tối ưu hóa hiệu suất ghi file
            try (FileOutputStream fos = new FileOutputStream(realPath)) {
                fos.write(picture);
            }
            request.setAttribute("picturePath", tempPath);
            session.setAttribute("picturePath_" + roleId, tempPath); // Phân biệt picturePath theo roleId
        }
    }

    // Lấy dữ liệu ảnh từ form
    private byte[] getPictureBytes(HttpServletRequest request, String phone, String roleId) throws IOException, ServletException {
        Part filePart = request.getPart("picture");
        if (filePart != null && filePart.getSize() > 0 && filePart.getContentType().startsWith("image/")) {
            String prefix = "1".equals(roleId) ? "student_" : "teacher_"; // Phân biệt Student và Teacher
            String fileName = prefix + phone + ".jpg";
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR + File.separator + fileName;
            File uploadDir = new File(getServletContext().getRealPath("") + File.separator + UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            // Sử dụng buffer để tối ưu hóa hiệu suất đọc file
            try (InputStream inputStream = filePart.getInputStream();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[8192]; // Buffer 8KB
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                filePart.write(uploadPath); // Lưu file vào thư mục Uploads
                return baos.toByteArray();
            }
        }
        return null;
    }

    // Kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Kiểm tra ngày sinh hợp lệ
    private boolean isValidBirthDate(String birthDate) {
        if (birthDate == null || birthDate.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate date = LocalDate.parse(birthDate);
            return date.isAfter(LocalDate.of(1900, 1, 1)) && date.isBefore(LocalDate.now().minusYears(10));
        } catch (Exception e) {
            return false;
        }
    }

    // Kiểm tra số năm kinh nghiệm hợp lệ
    private boolean isValidYearsOfExperience(String years) {
        if (years == null || years.trim().isEmpty()) {
            return false; // Bắt buộc phải có giá trị cho roleId=2
        }
        try {
            int y = Integer.parseInt(years);
            return y >= 0 && y <= 50;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Kiểm tra số hợp lệ
    private boolean isValidNumber(String number) {
        if (number == null || number.trim().isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getServletInfo() {
        return "ProfileController for managing user profile with update account and update personal info actions";
    }
}