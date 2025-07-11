package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Thuy_ in lịch học giáo viên, điểm danh, xử lý trên cùng controller

public class ScheduleTeacherController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Teachers teacher = (Teachers) session.getAttribute("account");

        if (teacher == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId;
        try {
            teacherId = Integer.parseInt(teacher.getId());
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if ("attendance".equals(action)) {
            handleAttendance(request, response);
        } else {
            handleScheduleView(request, response, teacherId);
        }
    }

    // Hiển thị thời khóa biểu theo tuần và năm
    private void handleScheduleView(HttpServletRequest request, HttpServletResponse response, int teacherId)
            throws ServletException, IOException {

        String selectedYear = request.getParameter("year");
        String selectedWeek = request.getParameter("week");

        int year = LocalDate.now().getYear();
        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter viewFormatter = DateTimeFormatter.ofPattern("dd/MM");

        try {
            if (selectedYear != null && !selectedYear.isEmpty()) {
                year = Integer.parseInt(selectedYear);
            }
        } catch (NumberFormatException ignored) {}

        LocalDate baseDate;
        try {
            baseDate = (selectedWeek != null && !selectedWeek.isEmpty())
                    ? LocalDate.parse(selectedWeek, dbFormatter)
                    : LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        } catch (Exception e) {
            baseDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        }

        ScheduleTeacherDAO dao = new ScheduleTeacherDAO();
        List<ScheduleTeacher> scheduleTeacher = dao.getScheduleTeacher(teacherId, baseDate.format(dbFormatter));

        List<Integer> years = new ArrayList<>();
        for (int i = year - 2; i <= year + 2; i++) {
            years.add(i);
        }

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
                        0
                ));
            }
        }

        request.setAttribute("weekDays", Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"));
        request.setAttribute("scheduleTeacher", scheduleTeacher);
        request.setAttribute("weeks", weeks);
        request.setAttribute("years", years);
        request.setAttribute("selectedWeek", baseDate.format(dbFormatter));
        request.setAttribute("selectedYear", year);

        request.getRequestDispatcher("schedule_teacher.jsp").forward(request, response);
    }

    // Xử lý khi giáo viên ấn vào nút điểm danh
    private void handleAttendance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String scheduleId = request.getParameter("scheduleId");
        String classId = request.getParameter("classId");
        String className = request.getParameter("className");
        String day = request.getParameter("day");

        if (scheduleId == null || classId == null || className == null || day == null) {
            request.setAttribute("error", "Thiếu thông tin điểm danh.");
            request.getRequestDispatcher("schedule_teacher.jsp").forward(request, response);
            return;
        }

        ScheduleTeacherDAO dao = new ScheduleTeacherDAO();
        List<Students> students = dao.getStudentsByScheduleId(scheduleId);
        List<StudentAttendance> attendanceList = dao.getStudentAttendanceList(classId, day);

        request.setAttribute("students", students);
        request.setAttribute("attendanceList", attendanceList);
        request.setAttribute("className", className);
        request.setAttribute("classId", classId);
        request.setAttribute("scheduleId", scheduleId);
        request.setAttribute("day", day);

        request.getRequestDispatcher("attendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if ("submitAttendance".equals(action)) {
            handleSubmitAttendance(request, response);
        } else {
            request.setAttribute("error", "Hành động không hợp lệ.");
            request.getRequestDispatcher("schedule_teacher.jsp").forward(request, response);
        }
    }

    // Lưu điểm danh sau khi submit
    private void handleSubmitAttendance(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String scheduleId = request.getParameter("scheduleId");
        String classId = request.getParameter("classId");
        String className = request.getParameter("className");
        String day = request.getParameter("day");
        String[] studentIds = request.getParameterValues("studentId[]");

        if (scheduleId == null || classId == null || className == null || day == null || studentIds == null) {
            request.setAttribute("error", "Thiếu thông tin cần thiết để lưu điểm danh.");
            request.getRequestDispatcher("attendance.jsp").forward(request, response);
            return;
        }

        List<StudentAttendance> attendanceList = new ArrayList<>();
        for (String id : studentIds) {
            String status = request.getParameter("attendance_" + id);
            if (status == null || status.trim().isEmpty()) {
                status = "Vắng mặt";
            }

            String reason = request.getParameter("reason_" + id);

            Students student = new Students();
            student.setId(id);

            StudentAttendance sa = new StudentAttendance(student, status, reason);
            attendanceList.add(sa);
        }

        ScheduleTeacherDAO dao = new ScheduleTeacherDAO();
        dao.saveAttendance(scheduleId, attendanceList, day);

        // Lấy lại dữ liệu để hiển thị sau khi lưu
        List<Students> students = dao.getStudentsByScheduleId(scheduleId);
        List<StudentAttendance> updated = dao.getStudentAttendanceList(classId, day);

        request.setAttribute("students", students);
        request.setAttribute("attendanceList", updated);
        request.setAttribute("className", className);
        request.setAttribute("classId", classId);
        request.setAttribute("scheduleId", scheduleId);
        request.setAttribute("day", day);
        request.setAttribute("message", "Lưu điểm danh thành công!");

        request.getRequestDispatcher("attendance.jsp").forward(request, response);
    }
}