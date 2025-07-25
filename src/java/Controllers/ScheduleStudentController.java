
package Controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import models.*;

public class ScheduleStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Students student = (Students) session.getAttribute("account");

        // Kiểm tra đăng nhập
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
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Lấy năm và tuần
        int year = LocalDate.now().getYear(); // 2025
        String selectedYear = request.getParameter("year");
        if (selectedYear != null && !selectedYear.isEmpty()) {
            try {
                year = Integer.parseInt(selectedYear);
            } catch (NumberFormatException ignored) {
            }
        }

        LocalDate baseDate = LocalDate.now().with(java.time.DayOfWeek.MONDAY); // Mặc định: đầu tuần hiện tại
        String selectedWeek = request.getParameter("week");
        if (selectedWeek != null && !selectedWeek.isEmpty()) {
            try {
                baseDate = LocalDate.parse(selectedWeek, dbFormatter);
            } catch (Exception ignored) {
                baseDate = LocalDate.now().with(java.time.DayOfWeek.MONDAY); // fallback đúng thứ 2
            }
        }

        ScheduleStudentDAO dao = new ScheduleStudentDAO();
        ClassTransferRequestDAO transferDAO = new ClassTransferRequestDAO();
        List<ScheduleStudent> scheduleStudent = new ArrayList<>();

        // Kiểm tra chuyển lớp
        ClassTransferRequest transfer = transferDAO.getLastApprovedRequest(student.getId());

        if (transfer != null && transfer.getTransferDate() != null) {
            LocalDate transferDate = new java.sql.Date(transfer.getTransferDate().getTime()).toLocalDate();
            List<ScheduleStudent> oldList = dao.getScheduleBefore(transfer.getFromClass().getId_class(), transferDate, studentId);

            List<ScheduleStudent> newList = dao.getScheduleAfter(transfer.getToClass().getId_class(), transferDate, studentId);

            List<ScheduleStudent> merged = new ArrayList<>();
            merged.addAll(oldList);
            merged.addAll(newList);

            LocalDate weekStart = baseDate;
            LocalDate weekEnd = baseDate.plusDays(6);
            List<ScheduleStudent> filtered = new ArrayList<>();
            for (ScheduleStudent s : merged) {
                try {
                    LocalDate day = LocalDate.parse(s.getDay(), dateFormatter);
                    if (!day.isBefore(weekStart) && !day.isAfter(weekEnd)) {
                        filtered.add(s);
                    }
                } catch (Exception e) {
                }
            }
            scheduleStudent = filtered;
        } else {
            Categories_class currentClass = transferDAO.getClassByStudentId(student.getId());
            if (currentClass != null) {
                String classIdToUse = currentClass.getId_class();
                if (classIdToUse == null || classIdToUse.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    classIdToUse = "2";
                }
                try {
                    List<ScheduleStudent> allSchedules = dao.getScheduleStudentAll(studentId, classIdToUse);

                    LocalDate weekStart = baseDate;
                    LocalDate weekEnd = baseDate.plusDays(6);
                    List<ScheduleStudent> filtered = new ArrayList<>();
                    for (ScheduleStudent s : allSchedules) {
                        try {
                            LocalDate day = LocalDate.parse(s.getDay(), dateFormatter);
                            if (!day.isBefore(weekStart) && !day.isAfter(weekEnd)) {
                                filtered.add(s);
                            }
                        } catch (Exception e) {
                        }
                    }
                    scheduleStudent = filtered;

                    // Fallback: Nếu tuần hiện tại rỗng, hiển thị tuần gần nhất có dữ liệu
                    if (filtered.isEmpty()) {
                        LocalDate nearestDate = null;
                        for (ScheduleStudent s : allSchedules) {
                            LocalDate d = LocalDate.parse(s.getDay(), dateFormatter);
                            if (nearestDate == null || d.isAfter(nearestDate)) {
                                nearestDate = d;
                            }
                        }
                        if (nearestDate != null) {
                            weekStart = nearestDate.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
                            weekEnd = weekStart.plusDays(6);
                            filtered = new ArrayList<>();
                            for (ScheduleStudent s : allSchedules) {
                                LocalDate d = LocalDate.parse(s.getDay(), dateFormatter);
                                if (!d.isBefore(weekStart) && !d.isAfter(weekEnd)) {
                                    filtered.add(s);
                                }
                            }
                            scheduleStudent = filtered;
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing classId: " + classIdToUse + " | Error: " + e.getMessage());
                }
            } else {
                System.out.println(">> No class found for studentId: " + student.getId());
            }
        }

        // Xử lý năm, in tuần để chọn, hiện các thứ
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
        System.out.println("==== Danh sách thời khóa biểu học sinh ====");
        for (ScheduleStudent s : scheduleStudent) {
            System.out.println("Ngày: " + s.getDay()
                    + " | Thứ: " + s.getDayVN()
                    + " | Lớp: " + s.getNameClass()
                    + " | Bắt đầu: " + s.getStartTime()
                    + " | Phòng: " + s.getRoom()
                    + " | Điểm danh: " + s.getAttendanceStatus()
                    + " | Lý do: " + s.getReason());
        }
        System.out.println("===> Tổng số buổi học: " + scheduleStudent.size());
       
        
        request.setAttribute("weeks", weeks);
        request.setAttribute("years", years);
        request.setAttribute("selectedWeek", baseDate.format(dbFormatter));
        request.setAttribute("selectedYear", year);
        request.setAttribute("profile", student); // Truyền thông tin giáo viên
        request.setAttribute("picturePath", session.getAttribute("picturePath"));
        request.getRequestDispatcher("schedule_student.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
