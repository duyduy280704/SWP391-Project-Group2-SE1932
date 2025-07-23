package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import models.*;

/**
 *
 * @author Thuy lịch học của học sinh
 */
public class ScheduleStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Students student = (Students) session.getAttribute("account");
// dang nhap
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
// lấy năm và tuần 
        int year = LocalDate.now().getYear();
        String selectedYear = request.getParameter("year");
        if (selectedYear != null && !selectedYear.isEmpty()) {
            try {
                year = Integer.parseInt(selectedYear);
            } catch (NumberFormatException ignored) {
            }
        }

        LocalDate baseDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        String selectedWeek = request.getParameter("week");
        if (selectedWeek != null && !selectedWeek.isEmpty()) {
            try {
                baseDate = LocalDate.parse(selectedWeek, dbFormatter);
            } catch (Exception ignored) {
            }
        }

        ScheduleStudentDAO dao = new ScheduleStudentDAO();
        ClassTransferRequestDAO transferDAO = new ClassTransferRequestDAO();
        List<ScheduleStudent> scheduleStudent = new ArrayList<>();

        // chuyển lớp của học sinh lớp cũ hiện lịch đến ngày chuyển , lớp mới hiện lịch  mới từ ngày chuyển đi 
        ClassTransferRequest transfer = transferDAO.getLastApprovedRequest(student.getId());
        if (transfer != null && transfer.getResponseDate() != null) {
            LocalDate approvedDate = new java.sql.Date(transfer.getResponseDate().getTime())
                    .toLocalDate();

            List<ScheduleStudent> oldList = dao.getScheduleBefore(transfer.getFromClassId(), approvedDate);
            List<ScheduleStudent> newList = dao.getScheduleAfter(transfer.getToClassId(), approvedDate);

            scheduleStudent.addAll(oldList);
            scheduleStudent.addAll(newList);

            LocalDate weekStart = baseDate;
            LocalDate weekEnd = baseDate.plusDays(6);
            List<ScheduleStudent> filtered = new ArrayList<>();

            for (ScheduleStudent s : scheduleStudent) {
                LocalDate day = LocalDate.parse(s.getDay());
                if (!day.isBefore(weekStart) && !day.isAfter(weekEnd)) {
                    filtered.add(s);
                }
            }
            scheduleStudent = filtered;

        } else {
            Categories_class currentClass = transferDAO.getClassByStudentId(student.getId());
            if (currentClass != null) {
                List<ScheduleStudent> allSchedules = dao.getScheduleStudentAll(studentId, currentClass.getId_class());

                LocalDate weekStart = baseDate;
                LocalDate weekEnd = baseDate.plusDays(6);

                List<ScheduleStudent> filtered = new ArrayList<>();
                for (ScheduleStudent s : allSchedules) {
                    LocalDate day = LocalDate.parse(s.getDay());
                    if (!day.isBefore(weekStart) && !day.isAfter(weekEnd)) {
                        filtered.add(s);
                    }
                }

                scheduleStudent = filtered;
            }
        }

        // sử lí năm, in tuần để chọn, hiện các thứ
        List<Integer> years = new ArrayList<>();
        for (int i = year - 2; i <= year + 2; i++) {
            years.add(i);
        }

        List<ScheduleWeek> weeks = new ArrayList<>();
        LocalDate startOfYear = LocalDate.of(year, 1, 1).with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        for (int i = 0; i < 52; i++) {
            LocalDate weekStart = startOfYear.plusWeeks(i);
            LocalDate weekEnd = weekStart.plusDays(6);
            if (weekStart.getYear() == year || weekEnd.getYear() == year) {
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
