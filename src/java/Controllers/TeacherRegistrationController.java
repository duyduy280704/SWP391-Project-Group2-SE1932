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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

//Huyền
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
        response.setContentType("text/html;charset=UTF-8");

        CourseDAO courseDAO = new CourseDAO();
        List<TypeCourse> courseTypes = courseDAO.getCourseType(); // Lấy danh sách loại khóa học

        String message = "";
        if (courseTypes == null || courseTypes.isEmpty()) {
            message = "Không thể tải loại khóa học. Vui lòng kiểm tra kết nối database hoặc dữ liệu.<br>";
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("message", message);
        request.setAttribute("courseTypes", courseTypes);
        request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
    }

    // Xử lý khi người dùng gửi form đăng ký (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        TeacherApplicationDAO teacherDAO = new TeacherApplicationDAO();
        CourseDAO courseDAO = new CourseDAO();
        List<TypeCourse> courseTypes = courseDAO.getCourseType(); // Lấy lại danh sách để gửi về nếu có lỗi

        // Lấy thông tin từ form
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String birthDate = request.getParameter("birthDate");
        String gender = request.getParameter("gender");
        String expertise = request.getParameter("expertise");
        String idTypeCourse = request.getParameter("idTypeCourse");
        String yearOfExpertise = request.getParameter("yearsOfExperience");
        String phone = request.getParameter("phone");
        Part filePart = request.getPart("cvFile"); // File CV tải lên
        String agreeTerms = request.getParameter("agreeTerms");

        String message = "";
        boolean check = true;

        // ==== VALIDATION ====

        // Kiểm tra họ tên
        if (fullName == null || fullName.trim().isEmpty()) {
            message += "Bạn chưa nhập họ và tên<br>";
            check = false;
        } else if (!fullName.trim().matches("^[A-Za-zÀ-ỹ\\s]+$")) {
            message += "Họ và tên chỉ được chứa chữ cái và dấu cách<br>";
            check = false;
        } else if (fullName.trim().length() < 2 || fullName.trim().length() > 50) {
            message += "Họ và tên phải từ 2 đến 50 ký tự<br>";
            check = false;
        }

        // Kiểm tra email
        if (email == null || email.trim().isEmpty()) {
            message += "Bạn chưa nhập email<br>";
            check = false;
        } else if (!email.trim().contains("@") || !email.trim().contains(".") || email.trim().endsWith(".")) {
            message += "Email không đúng định dạng<br>";
            check = false;
        } else {
            try {
                if (teacherDAO.isEmailExists(email.trim())) {
                    message += "Email đã được sử dụng<br>";
                    check = false;
                }
            } catch (SQLException e) {
                message += "Lỗi kiểm tra email: " + e.getMessage() + "<br>";
                check = false;
            }
        }

        // Kiểm tra số điện thoại
        if (phone == null || phone.trim().isEmpty()) {
            message += "Bạn chưa nhập số điện thoại<br>";
            check = false;
        } else if (!phone.trim().matches("^(03|05|07|08|09)[0-9]{8}$")) {
            message += "Số điện thoại không hợp lệ<br>";
            check = false;
        } else {
            try {
                if (teacherDAO.isPhoneExists(phone.trim())) {
                    message += "Số điện thoại đã được sử dụng<br>";
                    check = false;
                }
            } catch (SQLException e) {
                message += "Lỗi kiểm tra số điện thoại: " + e.getMessage() + "<br>";
                check = false;
            }
        }

        // Kiểm tra ngày sinh
        if (birthDate == null || birthDate.isEmpty()) {
            message += "Bạn chưa nhập ngày sinh<br>";
            check = false;
        } else if (!birthDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            message += "Định dạng ngày sinh không hợp lệ (YYYY-MM-DD)<br>";
            check = false;
        } else {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                java.util.Date parsedDate = dateFormat.parse(birthDate);
                Calendar cal = Calendar.getInstance();
                cal.setTime(parsedDate);
                Calendar now = Calendar.getInstance();
                int age = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)) age--;

                if (age < 18) {
                    message += "Giáo viên phải ít nhất 18 tuổi<br>";
                    check = false;
                }
                if (parsedDate.after(new java.util.Date())) {
                    message += "Ngày sinh không được là ngày tương lai<br>";
                    check = false;
                }
            } catch (ParseException e) {
                message += "Ngày sinh không hợp lệ<br>";
                check = false;
            }
        }

        // Kiểm tra giới tính
        if (gender == null || (!gender.equals("Nam") && !gender.equals("Nữ") && !gender.equals("Khác"))) {
            message += "Giới tính không hợp lệ<br>";
            check = false;
        }

        // Kiểm tra chuyên môn
        if (expertise == null || expertise.trim().isEmpty()) {
            message += "Bạn chưa nhập chuyên môn<br>";
            check = false;
        } else if (expertise.trim().length() < 2 || expertise.trim().length() > 100) {
            message += "Chuyên môn phải từ 2 đến 100 ký tự<br>";
            check = false;
        }

        // Kiểm tra loại khóa học
        if (idTypeCourse == null || idTypeCourse.trim().isEmpty()) {
            message += "Bạn chưa chọn loại khóa học<br>";
            check = false;
        } else {
            try {
                Integer.parseInt(idTypeCourse);
            } catch (NumberFormatException e) {
                message += "ID loại khóa học không hợp lệ<br>";
                check = false;
            }
        }

        // Kiểm tra năm kinh nghiệm
        if (yearOfExpertise == null || yearOfExpertise.trim().isEmpty()) {
            message += "Bạn chưa nhập số năm kinh nghiệm<br>";
            check = false;
        } else {
            try {
                int years = Integer.parseInt(yearOfExpertise);
                if (years < 0 || years > 50) {
                    message += "Số năm kinh nghiệm phải từ 0 đến 50<br>";
                    check = false;
                }
            } catch (NumberFormatException e) {
                message += "Số năm kinh nghiệm phải là số hợp lệ<br>";
                check = false;
            }
        }

        // Kiểm tra file CV
        byte[] fileData = null;
        String fileName = null;
        String fileExtension = null;

        if (filePart == null || filePart.getSize() == 0) {
            message += "Bạn chưa tải lên file CV<br>";
            check = false;
        } else {
            long maxSize = 10 * 1024 * 1024;
            if (filePart.getSize() > maxSize) {
                message += "File CV không được lớn hơn 10MB<br>";
                check = false;
            }

            fileName = extractFileName(filePart);
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            if (!fileExtension.equals("pdf") && !fileExtension.equals("doc") && !fileExtension.equals("docx")) {
                message += "File CV phải có định dạng .pdf, .doc hoặc .docx<br>";
                check = false;
            }

            if (check) {
                try (InputStream is = filePart.getInputStream()) {
                    fileData = is.readAllBytes();
                } catch (IOException e) {
                    message += "Lỗi khi đọc file CV<br>";
                    check = false;
                }
            }
        }

        // Kiểm tra đã đồng ý điều khoản chưa
        if (agreeTerms == null || !agreeTerms.equals("on")) {
            message += "Bạn chưa đồng ý với các điều khoản và cam kết<br>";
            check = false;
        }

        // Nếu có lỗi, trả lại form cùng dữ liệu đã nhập
        if (!check) {
            request.setAttribute("message", message);
            request.setAttribute("courseTypes", courseTypes);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("birthDate", birthDate);
            request.setAttribute("gender", gender);
            request.setAttribute("expertise", expertise);
            request.setAttribute("idTypeCourse", idTypeCourse);
            request.setAttribute("yearsOfExperience", yearOfExpertise);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
            return;
        }

        //  xử lý đăng ký 
        try {
            // Lưu file vào thư mục Uploads
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            String uploadPath = getServletContext().getRealPath("") + File.separator + "Uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdir();

            try (FileOutputStream fos = new FileOutputStream(uploadPath + File.separator + uniqueFileName)) {
                fos.write(fileData);
            }

            // Tạo đối tượng giáo viên
            TeacherApplications teacher = new TeacherApplications();
            teacher.setFullName(fullName.trim());
            teacher.setEmail(email.trim());
            teacher.setCvlink("Uploads/" + uniqueFileName);
            teacher.setBirthDate(birthDate);
            teacher.setGender(gender);
            teacher.setExpertise(expertise.trim());
            teacher.setIdTypeCourse(idTypeCourse);
            teacher.setYearOfExpertise(yearOfExpertise);
            teacher.setPhone(phone.trim());

            teacherDAO.registerTeacher(teacher); // Thêm vào DB
            request.getRequestDispatcher("registSuccesssul.jsp").forward(request, response);

        } catch (SQLException | ParseException e) {
            message += "Lỗi khi đăng ký: " + e.getMessage() + "<br>";
            request.setAttribute("message", message);
            request.setAttribute("courseTypes", courseTypes);
            request.setAttribute("fullName", fullName);
            request.setAttribute("email", email);
            request.setAttribute("birthDate", birthDate);
            request.setAttribute("gender", gender);
            request.setAttribute("expertise", expertise);
            request.setAttribute("idTypeCourse", idTypeCourse);
            request.setAttribute("yearsOfExperience", yearOfExpertise);
            request.setAttribute("phone", phone);
            request.getRequestDispatcher("TeacherRegistion.jsp").forward(request, response);
        }
    }

    // Hàm phụ để lấy tên file từ phần upload
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý đăng ký giáo viên";
    }
}
