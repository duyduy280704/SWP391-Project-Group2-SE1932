package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import models.ScheduleStudent;
import models.ScheduleStudentDAO;
// Thuy-Thời khóa biểu của học sinh   
public class ScheduleStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        int studentId = 2; // Thay bằng ID học sinh thực tế, có thể lấy từ session
        ScheduleStudentDAO dao = new ScheduleStudentDAO();
        List<ScheduleStudent> scheduleStudent = dao.getScheduleStudent(studentId);

        for (ScheduleStudent s : scheduleStudent) {
            s.computeDayOfWeek();
        }

        request.setAttribute("scheduleStudent", scheduleStudent);
        request.getRequestDispatcher("schedule_student.jsp").forward(request, response);
    }
}