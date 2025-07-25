package Controllers;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.*;
// Thủy _ sử lí gửi feedback, xem
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
        if (mode == null) {
            mode = "write"; 
        }

        FeedbackCourseDAO dao = new FeedbackCourseDAO();

        //  Staff xem danh sách phản hồi 
        if (account instanceof AdminStaffs) {
            AdminStaffs staff = (AdminStaffs) account;
            if ("3".equals(staff.getRole()) || "4".equals(staff.getRole())) {
                String keyword = request.getParameter("keyword");
                List<FeedbackByStudent> feedbackList;
                if (keyword != null && !keyword.trim().isEmpty()) {
                    feedbackList = dao.searchFeedback(keyword.trim());
                } else {
                    feedbackList = dao.getAllFeedback();
                }
                request.setAttribute("feedbackList", feedbackList);
                request.setAttribute("keyword", keyword);
                request.getRequestDispatcher("feedbackView.jsp").forward(request, response);
                return;
            } else {
                response.getWriter().println("<h3 style='color:red'>Bạn không có quyền truy cập chức năng này.</h3>");
                return;
            }
        }

        // Giáo viên xem phản hồi lớp mình dạy 
        if (account instanceof Teachers && "viewAll".equals(mode)) {
            Teachers teacher = (Teachers) account;
            String keyword = request.getParameter("keyword");
            List<FeedbackByStudent> feedbackList;
            if (keyword != null && !keyword.trim().isEmpty()) {
                feedbackList = dao.searchFeedbacksByTeacher(teacher.getId(), keyword.trim());
            } else {
                feedbackList = dao.getFeedbackByTeacher(teacher.getId());
            }
            request.setAttribute("feedbackList", feedbackList);
            request.setAttribute("keyword", keyword);
            request.getRequestDispatcher("feedback_teacher.jsp").forward(request, response);
            return;
        }

        // Sinh viên gửi phản hồi
        if (account instanceof Students && "write".equals(mode)) {
            Students student = (Students) account;
            List<Courses> courseList = dao.getCoursesByStudentId(student.getId());
            request.setAttribute("courseList", courseList);
            request.getRequestDispatcher("feedback.jsp").forward(request, response);
            return;
        }

        //  Sinh viên xem lịch sử phản hồi( chưa chạy dc)
        if (account instanceof Students && "history".equals(mode)) {
            Students student = (Students) account;
            List<FeedbackByStudent> feedbackList = dao.getFeedbackByStudent(student.getId());
            request.setAttribute("feedbackList", feedbackList);
            request.getRequestDispatcher("feedback_history.jsp").forward(request, response);
            return;
        }

        response.getWriter().println("<h3 style='color:red'>Bạn không có quyền truy cập chức năng này.</h3>");
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