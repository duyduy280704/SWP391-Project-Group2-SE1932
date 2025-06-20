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
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
@WebServlet("/resgiterTeacher")
public class TeacherRegistrationController extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";

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
        

        String picturePath = handleFileUpload(request);

        TeacherApplications teacher = new TeacherApplications();
       
        String message = "";
        try {
             if (!phone.matches("\\d{10,11}")) {
                message = "Số điện thoại phải có 10-11 số!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
                return;
            }
            if (birthDate != null && !birthDate.isEmpty() && !birthDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
                message="Định dạng ngày sinh không hợp lệ (MM-dd-yyyy)!";
                 request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
                return;
            }
            if (idTypeCourse != null && !idTypeCourse.isEmpty()) {
                Integer.parseInt(idTypeCourse);
            }
            if (yearOfExpertise != null && !yearOfExpertise.isEmpty()) {
                Integer.parseInt(yearOfExpertise);
            }
            String agreeTerms = request.getParameter("agreeTerms");
            if (agreeTerms == null || !agreeTerms.equals("on")) {
               message="Vui lòng đồng ý với các điều khoản và cam kết!";
            }

            teacherDAO.registerTeacher(teacher);
            response.sendRedirect(request.getContextPath() + "/registSuccesssul.jsp");
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

    private String handleFileUpload(HttpServletRequest request) throws IOException, ServletException {
        Part filePart = request.getPart("image");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = extractFileName(filePart);
            String uploadPath = getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();
            String picturePath = UPLOAD_DIR + File.separator + fileName;
            filePart.write(uploadPath + File.separator + fileName);
            return picturePath;
        }
        return null;
    }

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
    public String getServletInfo() {
        return "Servlet xử lý đăng ký giáo viên";
    }
}