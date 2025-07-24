package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.StudentDAO;
import models.TeacherDAO;
import models.AdminStaffDAO;
//Huyền
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");

        if (phone == null || phone.trim().isEmpty() || !phone.matches("^\\+?[0-9]{10,11}$")) {
            request.setAttribute("message", "Số điện thoại không hợp lệ trong liên kết.");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }
        if (role == null || role.trim().isEmpty() || !role.matches("^(student|teacher|admin_staff)$")) {
            request.setAttribute("message", "Vai trò không hợp lệ trong liên kết.");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        System.out.println("doGet: phone=" + phone + ", role=" + role); // Logging
        request.setAttribute("phone", phone);
        request.setAttribute("role", role);
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra dữ liệu đầu vào
        if (phone == null || phone.trim().isEmpty() || !phone.matches("^\\+?[0-9]{10,11}$")) {
            request.setAttribute("message", "Số điện thoại không hợp lệ.");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        if (role == null || role.trim().isEmpty() || !role.matches("^(student|teacher|admin_staff)$")) {
            request.setAttribute("message", "Vai trò không hợp lệ.");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            request.setAttribute("message", "Mật khẩu mới không được để trống.");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        if (confirmPassword == null || confirmPassword.trim().isEmpty()) {
            request.setAttribute("message", "Mật khẩu nhập lại không được để trống.");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Mật khẩu nhập lại không khớp với mật khẩu mới.");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
         if (newPassword.length() <= 6 || !newPassword.matches(".*[a-z]*")
                || !newPassword.matches(".*[A-Z]*") || !newPassword.matches(".*\\d.*") || !newPassword.matches(".*[^a-zA-Z0-9].*")) {
            request.setAttribute("message", "Mật khẩu phải trên 6 kí tự,có ít nhất 1 chữ thường,1 chữ in hoa, 1 số và 1 ký hiệu đặc biệt");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        StudentDAO studentDAO = new StudentDAO();
        TeacherDAO teacherDAO = new TeacherDAO();
        AdminStaffDAO adminDAO = new AdminStaffDAO();

        try {
            System.out.println("doPost: Cập nhật mật khẩu cho phone=" + phone + ", role=" + role); // Logging
            boolean updated = false;

            switch (role) {
                case "student":
                    updated = studentDAO.updatePassword(phone, newPassword);
                    break;
                case "teacher":
                    updated = teacherDAO.updatePassword(phone, newPassword);
                    break;
                case "admin_staff":
                    updated = adminDAO.updatePassword(phone, newPassword);
                    break;
                default:
                    request.setAttribute("message", "Vai trò không hợp lệ: " + role);
                    request.setAttribute("phone", phone);
                    request.setAttribute("role", role);
                    request.getRequestDispatcher("changePassword.jsp").forward(request, response);
                    return;
            }

            if (updated) {
                request.setAttribute("message", "Đổi mật khẩu thành công. Vui lòng đăng nhập lại.");
            } else {
                request.setAttribute("message", "Số điện thoại không tồn tại hoặc không thể cập nhật mật khẩu.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
        }

        request.setAttribute("phone", phone);
        request.setAttribute("role", role);
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }
}