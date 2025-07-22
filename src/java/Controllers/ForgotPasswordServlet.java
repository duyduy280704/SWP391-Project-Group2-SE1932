package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import models.StudentDAO;
import models.TeacherDAO;
import models.AdminStaffDAO;

public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        if (role == null || role.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng chọn vai trò.");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        // Kiểm tra số điện thoại rỗng hoặc không hợp lệ
        if (phone == null || phone.trim().isEmpty()) {
            request.setAttribute("message", "Vui lòng nhập số điện thoại.");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        if (!phone.matches("^\\+?[0-9]{10,11}$")) {
            request.setAttribute("message", "Số điện thoại không hợp lệ (10-11 số).");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

        String userEmail = null;
        boolean phoneExists = false;

        switch (role) {
            case "student":
                StudentDAO studentDAO = new StudentDAO();
                if (studentDAO.isPhoneExists(phone)) {
                    phoneExists = true;
                    userEmail = studentDAO.getByPhone(phone).getEmail();
                }
                break;
            case "teacher":
                TeacherDAO teacherDAO = new TeacherDAO();
                if (teacherDAO.isPhoneExists(phone)) {
                    phoneExists = true;
                    userEmail = teacherDAO.getByPhone(phone).getEmail();
                }
                break;
            case "admin_staff":
                AdminStaffDAO adminDAO = new AdminStaffDAO();
                if (adminDAO.isPhoneExist(phone)) {
                    phoneExists = true;
                    userEmail = adminDAO.getByPhone(phone).getEmail();
                }
                break;
            default:
                request.setAttribute("message", "Vai trò không hợp lệ.");
                request.setAttribute("phone", phone);
                request.setAttribute("role", role);
                request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
                return;
        }

// Xử lý kết quả
        if (!phoneExists) {
            request.setAttribute("message", "Số điện thoại không tồn tại cho vai trò " + role + ".");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

// Kiểm tra email hợp lệ
        if (userEmail == null || userEmail.trim().isEmpty()) {
            request.setAttribute("message", "Không tìm thấy email liên kết với số điện thoại.");
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }
        if (!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            request.setAttribute("message", "Email không hợp lệ: " + userEmail);
            request.setAttribute("phone", phone);
            request.setAttribute("role", role);
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
            return;
        }

// Tạo liên kết đặt lại mật khẩu
        String link = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + "/ProjectEdu_Swp/ResetPasswordServlet?phone=" + phone + "&role=" + role;
        String emailMessage = "Liên kết đặt lại mật khẩu: " + link + "\nLiên kết có hiệu lực trong 24 giờ.";

        try {
            System.out.println("Đang gửi email đến: " + userEmail); // Logging
            SendMail.send(userEmail, "Đặt lại mật khẩu", emailMessage);
            request.setAttribute("message", "Đã gửi liên kết đổi mật khẩu đến email: " + userEmail);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Không gửi được email: " + e.getMessage());
        }

        request.setAttribute("phone", phone);
        request.setAttribute("role", role);
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }
}