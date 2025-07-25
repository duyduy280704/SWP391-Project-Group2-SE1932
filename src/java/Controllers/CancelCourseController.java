
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
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import models.Regisition;
import models.RegisitionDAO;
import models.StudentDAO;
import models.Students;

/**
 *
 * @author Dương
 */
public class CancelCourseController extends HttpServlet {

    RegisitionDAO dao = new RegisitionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        Students student = (Students) session.getAttribute("account");

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String studentId = student.getId();
        StudentDAO studentDAO = new StudentDAO();
        Students stu = studentDAO.getStudentById(studentId);
        request.setAttribute("name", stu.getName());
        request.setAttribute("profile", stu);
        List<Regisition> list = dao.getRegisteredCourses(studentId);
        request.setAttribute("list", list);
        request.getRequestDispatcher("CourseHistory.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int regisId = Integer.parseInt(request.getParameter("regisId"));
            String studentId = request.getParameter("studentId");
            int courseId = Integer.parseInt(request.getParameter("courseId"));
            String cancelReason = request.getParameter("cancelReason");
            String status = dao.getRegistrationStatus(regisId);

            if ("Chưa phân lớp".equalsIgnoreCase(status)) {
                dao.cancelRegistration(regisId,"Đã hủy",cancelReason);
                request.setAttribute("message", "✅ Đã hủy đăng ký thành công.");
            } else if ("Đã phân lớp".equalsIgnoreCase(status)) {
                String firstScheduleStr = dao.getFirstScheduleAsString(studentId, courseId);

                if (firstScheduleStr != null) {
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date firstSchedule = sdf.parse(firstScheduleStr);
                    Date now = new Date();

                    if (firstSchedule.after(now)) {
                        dao.removeFromClass(studentId, courseId);
                        dao.cancelRegistration(regisId,"Đã hủy",cancelReason);
                        request.setAttribute("message", "✅ Hủy đăng ký và rút khỏi lớp thành công.");
                    } else {
                        request.setAttribute("error", "⛔ Khóa học đã bắt đầu, không thể hủy.");
                    }
                } else {
                    request.setAttribute("error", "❌ Không tìm thấy lịch học.");
                }
            } else {
                request.setAttribute("error", "❌ Trạng thái không hợp lệ.");
            }
        } catch (Exception e) {
            request.setAttribute("error", "❌ Lỗi xử lý: " + e.getMessage());
            e.printStackTrace();
        }

        doGet(request, response); 
    }
}
