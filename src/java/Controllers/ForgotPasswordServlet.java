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

        // Khởi tạo DAO
        StudentDAO studentDAO = new StudentDAO();
        TeacherDAO teacherDAO = new TeacherDAO();
        AdminStaffDAO adminDAO = new AdminStaffDAO();

        int count = 0;
        String role = null;
        String userEmail = null;

        // Kiểm tra số điện thoại trong các bảng
        if (studentDAO.isPhoneExists(phone)) {
            count++;
            role = "Student";
            userEmail = studentDAO.getByPhone(phone).getEmail();
        }
        if (teacherDAO.isPhoneExists(phone)) {
            count++;
            role = "Teacher";
            userEmail = teacherDAO.getByPhone(phone).getEmail();
        }
        if (adminDAO.isPhoneExist(phone)) {
            count++;
            role = "Admin_staff";
            userEmail = adminDAO.getByPhone(phone).getEmail();
        }

        // Xử lý kết quả
        if (count > 1) {
            request.setAttribute("message", "Số điện thoại tồn tại ở nhiều tài khoản. Vui lòng liên hệ hỗ trợ.");
        } else if (count == 0) {
            request.setAttribute("message", "Số điện thoại không tồn tại.");
        } else {
            // Kiểm tra email hợp lệ
            if (userEmail == null || userEmail.trim().isEmpty()) {
                request.setAttribute("message", "Không tìm thấy email liên kết với số điện thoại.");
            } else if (!userEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                request.setAttribute("message", "Email không hợp lệ: " + userEmail);
            } else {
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
            }
        }

        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }
}