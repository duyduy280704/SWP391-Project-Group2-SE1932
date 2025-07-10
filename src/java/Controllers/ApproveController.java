/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.CourseDAO;
import models.Courses;
import models.PreRegistration;
import models.PreRegistrationDAO;
import models.StudentDAO;
import models.Students;
import models.TypeCourse;

/**
 *
 * @author Dwight
 */
public class ApproveController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PreRegistrationDAO dao = new PreRegistrationDAO();
        CourseDAO courseDAO = new CourseDAO();

        String keyword = request.getParameter("keyword");
        String courseName = request.getParameter("course");
        String status = request.getParameter("status");

        List<PreRegistration> list;

        // Nếu có filter thì lọc theo điều kiện, không thì lấy toàn bộ
        if ((keyword != null && !keyword.trim().isEmpty())
                || (courseName != null && !courseName.trim().isEmpty())
                || (status != null && !status.trim().isEmpty())) {

            list = dao.filterPreRegistrations(keyword, courseName, status);
        } else {
            list = dao.getAllPreRegistrations();
        }

        List<Courses> courses = courseDAO.getCourses();

        request.setAttribute("list", list);
        request.setAttribute("courses", courses);
        request.setAttribute("keyword", keyword);
        request.setAttribute("selectedCourse", courseName);
        request.setAttribute("selectedStatus", status);

        request.getRequestDispatcher("confirmCourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));
        PreRegistrationDAO dao = new PreRegistrationDAO();

        if ("accept".equals(action)) {
    // Cập nhật trạng thái thành "Đã duyệt"
    dao.updateStatus(id, "Đã duyệt");

    // Lấy dữ liệu từ PreRegistrations
    PreRegistration pre = dao.getPreRegistrationById(id);

    // Tạo Student mới
    Students student = new Students();
    student.setName(pre.getFull_name());
    student.setEmail(pre.getEmail());
    student.setPhone(pre.getPhone());
    student.setBirthdate(pre.getBirth_date());
    student.setGender(pre.getGender());
    student.setAddress(pre.getAddress());
    student.setPassword(pre.getPhone());
    student.setRole("1"); // 1 = học sinh

    // Insert vào bảng Student nếu email chưa tồn tại
    StudentDAO studentDAO = new StudentDAO();
    if (!studentDAO.existsByEmail(pre.getEmail())) {
        boolean inserted = studentDAO.insert(student);
        if (inserted) {
            request.getSession().setAttribute("message", "✔️ Duyệt thành công và đã tạo tài khoản học sinh.");
        } else {
            request.getSession().setAttribute("message", "❌ Duyệt thành công nhưng lỗi khi tạo tài khoản.");
        }
    } else {
        request.getSession().setAttribute("message", "⚠️ Email này đã tồn tại trong hệ thống. Không thể tạo lại.");
    }

} else if ("reject".equals(action)) {
    String reason = request.getParameter("reason");
    dao.updateStatus(id, "Chưa xếp được lớp");
    request.getSession().setAttribute("message", "📌 Học viên đã được chuyển về trạng thái 'Chưa xếp được lớp'");
}

        
        response.sendRedirect("Approve");

        String note = request.getParameter("note");

        dao.updateNote(id, note);

        response.setContentType("text/plain");
        response.getWriter().write("success");
    }
}
