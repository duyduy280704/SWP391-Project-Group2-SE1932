package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import models.ScheduleStudent;
import models.ScheduleStudentDAO;
import models.Students;
import models.ScheduleWeek;
// Thuy_ in thời khóa biểu học sinh, tuần , năm 
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
        int id;
        try {
            id = Integer.parseInt(studentId);
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp");
            return;
        }

        
        String selectedYear = request.getParameter("year");
        String selectedWeek = request.getParameter("week");
        int year;
        LocalDate baseDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        
        if (selectedYear != null && !selectedYear.isEmpty()) {
            try {
                year = Integer.parseInt(selectedYear);
            } catch (NumberFormatException e) {
                year = LocalDate.now().getYear();
            }
        } else {
            year = LocalDate.now().getYear();
        }

       
        if (selectedWeek != null && !selectedWeek.isEmpty()) {
            try {
                baseDate = LocalDate.parse(selectedWeek, formatter);
            } catch (Exception e) {
                baseDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
            }
        } else {
            baseDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        }

        
        ScheduleStudentDAO dao = new ScheduleStudentDAO();
        List<ScheduleStudent> scheduleStudent = dao.getScheduleStudent(id, baseDate.format(formatter));

        // tạo hai năm trước sau
        List<Integer> years = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 2; i <= currentYear + 2; i++) {
            years.add(i);
        }

        // tạo tuần 
        List<ScheduleWeek> weeks = new ArrayList<>();
        DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd/MM");
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate firstMonday = startOfYear.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        for (int i = 0; i < 52; i++) {
            LocalDate weekStart = firstMonday.plusWeeks(i);
            if (weekStart.getYear() == year) {
                LocalDate weekEnd = weekStart.plusDays(6);
                weeks.add(new ScheduleWeek(
                        weekStart.format(fullFormatter),
                        weekEnd.format(fullFormatter),
                        weekStart.format(displayFormatter),
                        weekEnd.format(displayFormatter),
                        i + 1
                ));
            }
        }

        List<String> weekDays = Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật");

        // Set request attributes
        request.setAttribute("weekDays", weekDays);
        request.setAttribute("scheduleStudent", scheduleStudent);
        request.setAttribute("weeks", weeks);
        request.setAttribute("years", years);
        request.setAttribute("selectedWeek", baseDate.format(fullFormatter));
        request.setAttribute("selectedYear", year);
        request.getRequestDispatcher("schedule_student.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying student's schedule";
    }
}