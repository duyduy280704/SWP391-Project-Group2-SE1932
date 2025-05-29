package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import models.ScheduleStudent;
import models.ScheduleStudentDAO;
import models.Students;
// Thuy-Thời khóa biểu của học sinh   
public class ScheduleStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        Students st = (Students) session.getAttribute("account");
        if (st == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String studentId = st.getId();
        int id = Integer.parseInt(studentId);
        ScheduleStudentDAO dao = new ScheduleStudentDAO();
        List<ScheduleStudent> scheduleStudent = dao.getScheduleStudent(id);
        for (ScheduleStudent s : scheduleStudent) {
            s.computeDayOfWeek();
        }

        request.setAttribute("scheduleStudent", scheduleStudent);
        request.getRequestDispatcher("schedule_student.jsp").forward(request, response);
    }
}