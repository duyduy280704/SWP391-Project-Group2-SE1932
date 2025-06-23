// Thuy_thoi khoa bieu học sinh in, xem 
package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.ScheduleStudent;
import models.ScheduleStudentDAO;
import models.Students;
import models.ScheduleWeek;

public class ScheduleStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Students student = (Students) session.getAttribute("account");

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int studentId;
        try {
            studentId = Integer.parseInt(student.getId());
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp");
            return;
        }

        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter viewFormatter = DateTimeFormatter.ofPattern("dd/MM");

        // Xử lý năm
        int year = LocalDate.now().getYear();
        String selectedYear = request.getParameter("year");
        if (selectedYear != null && !selectedYear.isEmpty()) {
            try {
                year = Integer.parseInt(selectedYear);
            } catch (NumberFormatException ignored) {}
        }

        // Xử lý tuần
        LocalDate baseDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        String selectedWeek = request.getParameter("week");
        if (selectedWeek != null && !selectedWeek.isEmpty()) {
            try {
                baseDate = LocalDate.parse(selectedWeek, dbFormatter);
            } catch (Exception ignored) {}
        }

        ScheduleStudentDAO dao = new ScheduleStudentDAO();
        List<ScheduleStudent> scheduleStudent = dao.getScheduleStudent(studentId, baseDate.format(dbFormatter));

        List<Integer> years = new ArrayList<>();
        for (int i = year - 2; i <= year + 2; i++) {
            years.add(i);
        }

        // Tạo danh sách tuần trong năm
        List<ScheduleWeek> weeks = new ArrayList<>();
        LocalDate startOfYear = LocalDate.of(year, 1, 1).with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        for (int i = 0; i < 52; i++) {
            LocalDate weekStart = startOfYear.plusWeeks(i);
            if (weekStart.getYear() == year || weekStart.plusDays(6).getYear() == year) {
                LocalDate weekEnd = weekStart.plusDays(6);
                weeks.add(new ScheduleWeek(
                        weekStart.format(dbFormatter),
                        weekEnd.format(dbFormatter),
                        weekStart.format(viewFormatter),
                        weekEnd.format(viewFormatter),
                        i + 1
                ));
            }
        }

        List<String> weekDays = Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật");

        request.setAttribute("weekDays", weekDays);
        request.setAttribute("scheduleStudent", scheduleStudent);
        request.setAttribute("weeks", weeks);
        request.setAttribute("years", years);
        request.setAttribute("selectedWeek", baseDate.format(dbFormatter));
        request.setAttribute("selectedYear", year);

        request.getRequestDispatcher("schedule_student.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    
}