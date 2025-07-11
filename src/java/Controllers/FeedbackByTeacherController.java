package Controllers;

import java.io.IOException;
import java.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import models.*;
/**
 *
 * @author Thuy
 * giáo viên đánh giá học sinh , xem lớp xem học sinh rồi đánh giá
 */
public class FeedbackByTeacherController extends HttpServlet {

    private final FeedbackByTeacherDAO dao = new FeedbackByTeacherDAO();

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
        String classId = request.getParameter("classId");
        String keyword = request.getParameter("keyword");
        if (keyword == null) keyword = "";

        try {
         // staff dăng nhập 
            if (account instanceof AdminStaffs) {
                if (mode == null || mode.equals("staffView")) {
                    List<Categories_class> classList = dao.getAllClassesWithFeedback();
                    request.setAttribute("classList", classList);
                    request.getRequestDispatcher("ClassFeedbackByTeacher.jsp").forward(request, response);
                    return;
                }

                if ("staffViewFeedback".equals(mode) && classId != null) {
                    List<Assessment> feedbackList = dao.getFeedbackByClass(classId);
                    String className = dao.getClassNameById(classId);

                    request.setAttribute("feedbackList", feedbackList);
                    request.setAttribute("className", className);
                    request.getRequestDispatcher("ViewFeedbackByTeacher.jsp").forward(request, response);
                    return;
                }
                response.sendRedirect("login.jsp");
                return;
            }
//giáo viên đăng nhập 
            if (account instanceof Teachers) {
                Teachers teacher = (Teachers) account;

                if ("students".equals(mode) && classId != null) {
                    List<Students> students = keyword.trim().isEmpty()
                            ? dao.getStudentsByClass(classId)
                            : dao.searchFeedback(classId, keyword.trim(), teacher.getId());

                    Map<String, String> feedbackMap = new HashMap<>();
                    for (Students s : students) {
                        String fb = dao.getFeedbackStudent(s.getId(), classId, teacher.getId());
                        feedbackMap.put(s.getId(), fb);
                    }

                    request.setAttribute("students", students);
                    request.setAttribute("classId", classId);
                    request.setAttribute("feedbackMap", feedbackMap);
                    request.setAttribute("keyword", keyword);
                    request.getRequestDispatcher("FeedbackStudent.jsp").forward(request, response);
                    return;
                }

                List<Categories_class> classes = dao.searchClass(teacher.getId(), keyword.trim());
                request.setAttribute("classes", classes);
                request.setAttribute("keyword", keyword);
                request.getRequestDispatcher("FeedbackByTeacher.jsp").forward(request, response);
                return;
            }

            response.sendRedirect("login.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");

        if (!(account instanceof Teachers)) {
            response.sendRedirect("login.jsp");
            return;
        }

        Teachers teacher = (Teachers) account;
        String classId = request.getParameter("classId");
// dánh giá từng học sinh
        String studentId = request.getParameter("studentId");
        String feedback = request.getParameter("feedback");

        if (studentId != null && feedback != null && !feedback.trim().isEmpty()) {
            dao.upsertAssessment(studentId, classId, teacher.getId(), feedback.trim());
        }
        // đánh giá cả lớp
        String[] studentIds = request.getParameterValues("studentIds");
        String[] feedbacks = request.getParameterValues("feedbacks");

        if (studentIds != null && feedbacks != null && studentIds.length == feedbacks.length) {
            for (int i = 0; i < studentIds.length; i++) {
                String sid = studentIds[i];
                String fb = feedbacks[i];
                if (fb != null && !fb.trim().isEmpty()) {
                    dao.upsertAssessment(sid, classId, teacher.getId(), fb.trim());
                }
            }
        }

        response.sendRedirect("feedbackByTeacher?mode=students&classId=" + classId);
    }
}