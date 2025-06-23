package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.TeacherApplications;
import models.TeacherApplicationDAO;
import models.CourseDAO;
import models.TypeCourse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author xAI
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)
@WebServlet("/resgiterTeacher")
public class TeacherRegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TeacherApplicationDAO teacherDAO = new TeacherApplicationDAO();
        CourseDAO courseDAO = new CourseDAO();
        List<TypeCourse> courseTypes = courseDAO.getCourseType();
        String message = null;

        if (courseTypes == null || courseTypes.isEmpty()) {
            message = "Không thể tải loại khóa học. Vui lòng kiểm tra kết nối database hoặc dữ liệu. Xem log để biết chi tiết.";
        }

        request.setAttribute("courseTypes", courseTypes);
        request.setAttribute("message", message);
        request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        TeacherApplicationDAO teacherDAO = new TeacherApplicationDAO();

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String cvLink = request.getParameter("cvLink");
        String birthDate = request.getParameter("birthDate");
        String gender = request.getParameter("gender");
        String expertise = request.getParameter("expertise");
        String idTypeCourse = request.getParameter("idTypeCourse");
        String yearOfExpertise = request.getParameter("yearsOfExperience");
        String phone = request.getParameter("phone");

        TeacherApplications teacher = new TeacherApplications();
        teacher.setFullName(fullName);
        teacher.setEmail(email);
        teacher.setCvlink(cvLink);
        teacher.setBirthDate(birthDate);
        teacher.setGender(gender);
        teacher.setExpertise(expertise);
        teacher.setIdTypeCourse(idTypeCourse);
        teacher.setYearOfExpertise(yearOfExpertise);
        teacher.setPhone(phone);

        String message = "";
        try {
            if (teacherDAO.isEmailExists(email)) {
                message = "Email đã được sử dụng!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                CourseDAO courseDAO = new CourseDAO();
                List<TypeCourse> courseTypes = courseDAO.getCourseType();
                request.setAttribute("courseTypes", courseTypes);
                request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
                return;
            }

            // Check for duplicate phone
            if (teacherDAO.isPhoneExists(phone)) {
                message = "Số điện thoại đã được sử dụng!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                CourseDAO courseDAO = new CourseDAO();
                List<TypeCourse> courseTypes = courseDAO.getCourseType();
                request.setAttribute("courseTypes", courseTypes);
                request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
                return;
            }
            if (!phone.matches("\\d{10,11}")) {
                message = "Số điện thoại phải có 10-11 số!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
                return;
            }
            if (birthDate != null && !birthDate.isEmpty() && !birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                message = "Định dạng ngày sinh không hợp lệ (dd-MM-yyyy)!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
                return;
            }

            String agreeTerms = request.getParameter("agreeTerms");
            if (agreeTerms == null || !agreeTerms.equals("on")) {
                message = "Vui lòng đồng ý với các điều khoản và cam kết!";
            }

            teacherDAO.registerTeacher(teacher);
            request.getRequestDispatcher("registSuccesssul.jsp").forward(request, response);

        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            message = "Lỗi khi đăng ký: " + e.getMessage();
            request.setAttribute("message", message);
            CourseDAO courseDAO = new CourseDAO();
            List<TypeCourse> courseTypes = courseDAO.getCourseType();
            request.setAttribute("courseTypes", courseTypes);
            request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            message = "ID loại khóa học hoặc số năm kinh nghiệm phải là số hợp lệ!";
            request.setAttribute("message", message);
            CourseDAO courseDAO = new CourseDAO();
            List<TypeCourse> courseTypes = courseDAO.getCourseType();
            request.setAttribute("courseTypes", courseTypes);
            request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
        }
    }   

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng ký giáo viên";
    }
}
