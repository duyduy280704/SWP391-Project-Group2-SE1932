/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;
import models.CourseDAO;
import models.Courses;
import models.PreRegistration;
import models.PreRegistrationDAO;

/**
 *
 * @author Dwight
 */
public class RegistrationCourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null || id.trim().isEmpty()) {
            request.setAttribute("error", "Thiếu mã khóa học.");
            request.getRequestDispatcher("course_registration.jsp").forward(request, response);
            return;
        }

        CourseDAO courseDAO = new CourseDAO();
        Courses course = courseDAO.getCoursesById(id);
        request.setAttribute("course", course);

        // Nếu chưa submit form thì chỉ hiển thị trang
        if (request.getParameter("full_name") == null) {
            request.getRequestDispatcher("course_registration.jsp").forward(request, response);
            return;
        }

        // Lấy thông tin từ form
        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String birthDate = request.getParameter("birth_date");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");

        // Validate dữ liệu
        String errorMessage = null;

        if (fullName == null || fullName.trim().isEmpty()) {
            errorMessage = "Họ và tên không được để trống.";
        } else if (email == null || !email.matches("^[\\w.+\\-]+@gmail\\.com$")) {
            errorMessage = "Email phải hợp lệ và kết thúc bằng @gmail.com.";
        } else if (phone != null && !phone.matches("\\d{10}")) {
            errorMessage = "Số điện thoại phải có 10 số!";
        } else if (birthDate == null || birthDate.trim().isEmpty()) {
            errorMessage = "Ngày sinh không được để trống.";
        } else if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", birthDate)) {
            errorMessage = "Ngày sinh phải đúng định dạng yyyy-MM-dd.";
        } else if (gender == null || gender.trim().isEmpty()) {
            errorMessage = "Vui lòng chọn giới tính.";
        } else if (address == null || address.trim().isEmpty()) {
            errorMessage = "Địa chỉ không được để trống.";
        }

        if (errorMessage != null) {
            request.setAttribute("error", errorMessage);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("phone", phone);
            request.setAttribute("birthDate", birthDate);
            request.setAttribute("gender", gender);
            request.setAttribute("address", address);
            request.getRequestDispatcher("course_registration.jsp").forward(request, response);
            return;
        }

        // Thêm dữ liệu vào DB nếu hợp lệ
        try {
            int courseId = Integer.parseInt(id);
            PreRegistration preReg = new PreRegistration(fullName, email, phone, birthDate, gender, address, courseId, "Đang Chờ");
            PreRegistrationDAO dao = new PreRegistrationDAO();
            boolean success = dao.insertPreRegistration(preReg);

            if (success) {
                request.setAttribute("message", "Đăng ký thành công. Vui lòng chờ xác nhận.");
            } else {
                request.setAttribute("error", "Không thể lưu đăng ký. Vui lòng thử lại sau.");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID khóa học không hợp lệ.");
        }

        request.getRequestDispatcher("course_registration.jsp").forward(request, response);
    }

}
