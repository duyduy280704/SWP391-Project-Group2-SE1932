package Controllers;

import models.ProfileDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import models.*;

// Huyền
@MultipartConfig(maxFileSize = 16177215)
@WebServlet("/profile")
public class ProfileController extends HttpServlet {

    ProfileDAO dao = new ProfileDAO(); 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");

        // Nếu mode là "image", thì xử lý trả về ảnh đại diện (dùng trong <img src="/profile?mode=image&id=...">)
        if ("image".equals(mode)) {
            String id = request.getParameter("id");
            String role = request.getParameter("role");
            byte[] imageData = null;

            switch (role) {
                case "student":
                    Students s = dao.getStudentById(id);
                    if (s != null) {
                        imageData = s.getPic();
                    }
                    break;
                case "teacher":
                    Teachers t = dao.getTeacherById(id);
                    if (t != null) {
                        imageData = t.getPic();
                    }
                    break;
            }

            if (imageData != null) {
                response.setContentType("image/jpeg");
                response.setContentLength(imageData.length);
                try (OutputStream os = response.getOutputStream()) {
                    os.write(imageData); // Gửi ảnh về trình duyệt
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND); // Không tìm thấy ảnh
            }
            return; // Dừng xử lý ở đây nếu là load ảnh
        }

        // Nếu không phải load ảnh, thì xử lý hiển thị thông tin người dùng
        HttpSession session = request.getSession();
        Object acc = session.getAttribute("account");
        String role = (String) session.getAttribute("role");

        // Nếu chưa đăng nhập, chuyển về trang login
        if (acc == null || role == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy lại dữ liệu từ DB để đảm bảo thông tin mới nhất
        switch (role) {
            case "student":
                Students s = (Students) acc;
                acc = dao.getStudentById(s.getId());
                break;
            case "teacher":
                Teachers t = (Teachers) acc;
                acc = dao.getTeacherById(t.getId());
                break;
            case "admin":
            case "staff":
                AdminStaffs a = (AdminStaffs) acc;
                acc = dao.getAdminStaffById(a.getId());
                break;
        }

        // Truyền dữ liệu sang profile.jsp để hiển thị
        session.setAttribute("account", acc);
        request.setAttribute("role", role);
        request.setAttribute("profile", acc);
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    // Xử lý yêu cầu POST - dùng để cập nhật thông tin người dùng
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Object acc = session.getAttribute("account");
        String role = (String) session.getAttribute("role");

        // Nếu chưa đăng nhập, chuyển về login
        if (acc == null || role == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Nếu là cập nhật tài khoản (mật khẩu, SĐT), gọi hàm riêng
        String action = request.getParameter("action");
        if ("updateAccount".equals(action)) {
            handleAccountUpdate(request, response, session, role);
            return;
        }

        // Các thông tin cá nhân cần cập nhật
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String gender = request.getParameter("gender");
        String birthdate = request.getParameter("birthDate");
        String phone = request.getParameter("phone");

        // Kiểm tra dữ liệu bắt buộc
        if (fullName == null || fullName.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || gender == null || gender.trim().isEmpty()
                || birthdate == null || birthdate.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng điền đầy đủ các trường thông tin bắt buộc.");
            doGet(request, response);
            return;
        }

        // Kiểm tra định dạng email
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.setAttribute("message", "Địa chỉ email không hợp lệ.");
            doGet(request, response);
            return;
        }

        // Xử lý ảnh upload (nếu có)
        Part filePart = request.getPart("picture");
        byte[] picture = null;

        if (filePart != null && filePart.getSize() > 0) {
            String contentType = filePart.getContentType();
            long fileSize = filePart.getSize();

            // Kiểm tra định dạng ảnh hợp lệ
            if (!contentType.equals("image/jpeg")
                    && !contentType.equals("image/png")
                    && !contentType.equals("image/gif")) {
                request.setAttribute("message", "Chỉ cho phép ảnh định dạng JPG, PNG hoặc GIF!");
                doGet(request, response);
                return;
            }

            // Kiểm tra kích thước ảnh
            if (fileSize > 5 * 1024 * 1024) {
                request.setAttribute("message", "Ảnh không được vượt quá 5MB!");
                doGet(request, response);
                return;
            }

            // Đọc ảnh vào byte[]
            try (InputStream is = filePart.getInputStream()) {
                picture = is.readAllBytes();
            }
        }

        // Cập nhật theo vai trò
        switch (role) {
            case "student":
                String address = request.getParameter("address");
                if (address == null || address.trim().isEmpty()) {
                    request.setAttribute("message", "Vui lòng nhập địa chỉ.");
                    doGet(request, response);
                    return;
                }
                Students s = (Students) acc;
                s.setName(fullName);
                s.setEmail(email);
                s.setGender(gender);
                s.setBirthdate(birthdate);
                s.setAddress(address);
                if (picture != null) {
                    s.setPic(picture);
                }
                dao.updateStudent(s);
                session.setAttribute("account", s);
                break;

            case "teacher":
                String expertise = request.getParameter("expertise");
                String idTypeCourse = request.getParameter("idTypeCourse");
                String years = request.getParameter("yearsOfExperience");

                // Kiểm tra thông tin chuyên môn
                if (expertise == null || expertise.trim().isEmpty()
                        || idTypeCourse == null || idTypeCourse.trim().isEmpty()
                        || years == null || years.trim().isEmpty()) {
                    request.setAttribute("message", "Vui lòng điền đầy đủ thông tin chuyên môn.");
                    doGet(request, response);
                    return;
                }

                try {
                    int y = Integer.parseInt(years);
                    if (y < 0 || y > 50) {
                        request.setAttribute("message", "Kinh nghiệm phải nằm trong khoảng hợp lý.");
                        doGet(request, response);
                        return;
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Kinh nghiệm phải là số.");
                    doGet(request, response);
                    return;
                }

                Teachers t = (Teachers) acc;
                t.setName(fullName);
                t.setEmail(email);
                t.setGender(gender);
                t.setBirthdate(birthdate);
                t.setExp(expertise);
                t.setCourse(idTypeCourse);
                t.setYear(years);
                if (picture != null) {
                    t.setPic(picture);
                }
                dao.updateTeacher(t);
                session.setAttribute("account", t);
                break;

            case "admin":
            case "staff":
                AdminStaffs a = (AdminStaffs) acc;
                a.setName(fullName);
                a.setEmail(email);
                a.setGender(gender);
                a.setBirthdate(birthdate);
                dao.updateAdminStaff(a);
                session.setAttribute("account", a);
                break;
        }

        request.setAttribute("message", "Cập nhật thông tin cá nhân thành công!");
        doGet(request, response); // Sau khi cập nhật, hiển thị lại form
    }

    // Hàm xử lý cập nhật tài khoản (mật khẩu & số điện thoại)
    private void handleAccountUpdate(HttpServletRequest request, HttpServletResponse response, HttpSession session, String role)
            throws ServletException, IOException {

        Object acc = session.getAttribute("account");

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String newPhone = request.getParameter("newPhone");

        if (oldPassword == null || oldPassword.trim().isEmpty()
                || newPhone == null || newPhone.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng điền đầy đủ thông tin.");
            doGet(request, response);
            return;
        }

        if (!newPhone.matches("^\\d{9,11}$")) {
            request.setAttribute("message", "Số điện thoại không hợp lệ.");
            doGet(request, response);
            return;
        }
       

        if (newPassword.length() <= 6 || !newPassword.matches(".*[a-z].*")
                || !newPassword.matches(".*[A-Z].*") || !newPassword.matches(".*\\d.*")
                || !newPassword.matches(".*[^a-zA-Z0-9].*")) {
            request.setAttribute("message", "Mật khẩu phải trên 6 kí tự, có ít nhất 1 chữ thường, 1 chữ in hoa, 1 số và 1 ký hiệu đặc biệt.");
            doGet(request, response);
            return;
        }
        boolean updated = false;

        // Cập nhật theo vai trò
        switch (role) {
            case "student":
                Students s = (Students) acc;
                if (!dao.checkStudentOldPassword(s.getId(), oldPassword)) {
                    request.setAttribute("message", "Mật khẩu cũ không đúng!");
                    doGet(request, response);
                    return;
                }
                updated = dao.updateStudentAccount(s, oldPassword, newPassword, newPhone);
                if (updated) {
                    s.setPassword(newPassword);
                    s.setPhone(newPhone);
                    session.setAttribute("account", s);
                }
                break;

            case "teacher":
                Teachers t = (Teachers) acc;
                if (!dao.checkTeacherOldPassword(t.getId(), oldPassword)) {
                    request.setAttribute("message", "Mật khẩu cũ không đúng!");
                    doGet(request, response);
                    return;
                }
                updated = dao.updateTeacherAccount(t, oldPassword, newPassword, newPhone);
                if (updated) {
                    t.setPassword(newPassword);
                    t.setPhone(newPhone);
                    session.setAttribute("account", t);
                }
                break;

            case "admin":
            case "staff":
                AdminStaffs a = (AdminStaffs) acc;
                if (!dao.checkAdminStaffOldPassword(a.getId(), oldPassword)) {
                    request.setAttribute("message", "Mật khẩu cũ không đúng!");
                    doGet(request, response);
                    return;
                }
                updated = dao.updateAdminStaffAccount(a, oldPassword, newPassword, newPhone);
                if (updated) {
                    a.setPassword(newPassword);
                    a.setPhone(newPhone);
                    session.setAttribute("account", a);
                }
                break;
        }

        if (updated) {
            request.setAttribute("message", "Cập nhật tài khoản thành công!");
        } else {
            request.setAttribute("message", "Có lỗi khi cập nhật tài khoản!");
        }

        doGet(request, response); 
    }
}
