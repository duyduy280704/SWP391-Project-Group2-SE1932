package Controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.*;
// Mai Thuy _ Học sinh feedback lớp học , staff có thể xem tất cả feddback 
public class FeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String mode = request.getParameter("mode");
        FeedbackCourseDAO dao = new FeedbackCourseDAO();

        // Giáo viên và Staff vào xem
        if ((account instanceof Teachers || account instanceof AdminStaffs) && "viewAll".equals(mode)) {
            List<FeedbackByStudent> feedbackList = dao.getAllFeedback(); 
            request.setAttribute("feedbackList", feedbackList);
            request.getRequestDispatcher("feedbackView.jsp").forward(request, response);
            return;
        }

        // Học sinh ghi Feedback
        if (account instanceof Students) {
            Students student = (Students) account;
            List<Courses> courseList = dao.getCoursesByStudentId(student.getId());
            request.setAttribute("courseList", courseList);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }

        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");

        if (account == null || !(account instanceof Students)) {
            response.sendRedirect("login.jsp");
            return;
        }

        Students student = (Students) account;
        String studentId = student.getId();
        String courseId = request.getParameter("courseId");
        String comment = request.getParameter("comment");

        FeedbackCourseDAO dao = new FeedbackCourseDAO();

        if (courseId == null || comment == null || comment.trim().isEmpty()) {
            request.setAttribute("error", "Vui lòng điền đầy đủ thông tin phản hồi.");
        } else {
            dao.addFeedback(studentId, courseId, comment);
            request.setAttribute("message", "Gửi phản hồi thành công!");
        }

        List<Courses> courseList = dao.getCoursesByStudentId(studentId);
        request.setAttribute("courseList", courseList);
        request.getRequestDispatcher("feedback.jsp").forward(request, response);
    }
}