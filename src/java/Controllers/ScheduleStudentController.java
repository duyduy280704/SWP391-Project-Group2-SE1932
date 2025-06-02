/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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
        ScheduleStudentDAO dao = new ScheduleStudentDAO();
        List<ScheduleStudent> scheduleStudent = dao.getScheduleStudent(id);

        // Xử lý năm và tuần được chọn
        String selectedYear = request.getParameter("year");
        String selectedWeek = request.getParameter("week");
        LocalDate baseDate;
        int year;
        if (selectedYear != null && !selectedYear.isEmpty()) {
            try {
                year = Integer.parseInt(selectedYear);
            } catch (NumberFormatException e) {
                year = LocalDate.now().getYear();
            }
        } else {
            year = LocalDate.now().getYear();
        }

        // Tìm ngày sớm nhất trong dữ liệu
        LocalDate minDate = null;
        if (!scheduleStudent.isEmpty()) {
            for (ScheduleStudent s : scheduleStudent) {
                LocalDate date = LocalDate.parse(s.getDay(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                if (minDate == null || date.isBefore(minDate)) {
                    minDate = date;
                }
            }
        } else {
            minDate = LocalDate.now();
        }
        LocalDate startOfFirstWeek = minDate.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfTenthWeek = startOfFirstWeek.plusWeeks(9);

        if (selectedWeek != null && !selectedWeek.isEmpty()) {
            try {
                baseDate = LocalDate.parse(selectedWeek, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (Exception e) {
                baseDate = startOfFirstWeek;
            }
        } else {
            baseDate = startOfFirstWeek;
        }

        // Tính tuần cơ sở trong chu kỳ 10 tuần
        long weeksSinceStart = java.time.temporal.ChronoUnit.WEEKS.between(startOfFirstWeek, baseDate);
        long cycleWeek = weeksSinceStart % 10; // Lấy tuần trong chu kỳ 10 tuần
        LocalDate cycleBaseDate = startOfFirstWeek.plusWeeks(cycleWeek);

        // Chỉ điều chỉnh lịch nếu tuần được chọn nằm trong 10 tuần đầu tiên
        List<ScheduleStudent> adjustedSchedule = new ArrayList<>();
        if (baseDate.isBefore(startOfFirstWeek) || baseDate.isAfter(endOfTenthWeek)) {
            // Nếu tuần được chọn ngoài phạm vi 10 tuần, trả về danh sách rỗng
            adjustedSchedule = new ArrayList<>();
        } else {
            for (ScheduleStudent s : scheduleStudent) {
                LocalDate originalDate = LocalDate.parse(s.getDay(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                java.time.DayOfWeek dayOfWeek = originalDate.getDayOfWeek();
                LocalDate newDate = baseDate.with(java.time.temporal.TemporalAdjusters.nextOrSame(dayOfWeek)).withYear(year);
                ScheduleStudent adjusted = new ScheduleStudent(
                    s.getId(),
                    newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    s.getNameClass(),
                    s.getStartTime(),
                    s.getEndTime(),
                    s.getRoom()
                );
                adjusted.computeDayOfWeek();
                adjustedSchedule.add(adjusted);
            }
        }

        // Tạo danh sách năm (hiện tại và ±2 năm)
        List<Integer> years = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 2; i <= currentYear + 2; i++) {
            years.add(i);
        }

        // Tạo danh sách 52 tuần cho năm được chọn
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

        request.setAttribute("weekDays", weekDays);
        request.setAttribute("scheduleStudent", adjustedSchedule);
        request.setAttribute("weeks", weeks);
        request.setAttribute("years", years);
        request.setAttribute("selectedWeek", baseDate.format(fullFormatter));
        request.setAttribute("selectedYear", year);
        request.getRequestDispatcher("schedule_student.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Gọi doGet để xử lý POST tương tự
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying student's schedule";
    }
}