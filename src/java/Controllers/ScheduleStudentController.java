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
        System.out.println(">> Đăng nhập với học sinh ID: " + (student != null ? student.getId() : "null"));

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
        if (transfer != null) {
            System.out.println(">> Học sinh đã chuyển lớp, từ lớp " + transfer.getFromClass().getName_class()
                    + " đến lớp " + transfer.getToClass().getName_class()
                    + " vào ngày " + transfer.getTransferDate());
        } else {
            System.out.println(">> Học sinh chưa từng chuyển lớp");
        }

        if (transfer != null && transfer.getTransferDate() != null) {
            LocalDate transferDate = new java.sql.Date(transfer.getTransferDate().getTime()).toLocalDate();
            List<ScheduleStudent> oldList = dao.getScheduleBefore(transfer.getFromClass().getId_class(), transferDate, studentId);
            System.out.println(">> oldList (lớp cũ) size = " + oldList.size());

            List<ScheduleStudent> newList = dao.getScheduleAfter(transfer.getToClass().getId_class(), transferDate, studentId);
            System.out.println(">> newList (lớp mới) size = " + newList.size());

            List<ScheduleStudent> merged = new ArrayList<>();
            merged.addAll(oldList);
            merged.addAll(newList);
            System.out.println(">> merged size = " + merged.size());

            LocalDate weekStart = baseDate;
            LocalDate weekEnd = baseDate.plusDays(6);
            List<ScheduleStudent> filtered = new ArrayList<>();
            for (ScheduleStudent s : merged) {
                try {
                    LocalDate day = LocalDate.parse(s.getDay(), dateFormatter);
                    System.out.println(">> Checking day: " + day + " against " + weekStart + " to " + weekEnd);
                    if (!day.isBefore(weekStart) && !day.isAfter(weekEnd)) {
                        filtered.add(s);
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing day for merged schedule: " + s.getDay() + " | Error: " + e.getMessage());
                }
            }
            scheduleStudent = filtered;
            System.out.println(">> filtered (merged) size = " + filtered.size());
        } else {
            Categories_class currentClass = transferDAO.getClassByStudentId(student.getId());
            if (currentClass != null) {
                System.out.println(">> Học sinh đang thuộc lớp: " + currentClass.getName_class() + " (ID: " + currentClass.getId_class() + ")");
                String classIdToUse = currentClass.getId_class();
                System.out.println(">> Initial classId: " + classIdToUse);
                if (classIdToUse == null || classIdToUse.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    System.out.println(">> Invalid classId detected: " + classIdToUse + ", using default ID 2");
                    classIdToUse = "2";
                }
                try {
                    List<ScheduleStudent> allSchedules = dao.getScheduleStudentAll(studentId, classIdToUse);
                    System.out.println(">> allSchedules size = " + allSchedules.size());

                    LocalDate weekStart = baseDate;
                    LocalDate weekEnd = baseDate.plusDays(6);
                    List<ScheduleStudent> filtered = new ArrayList<>();
                    for (ScheduleStudent s : allSchedules) {
                        try {
                            LocalDate day = LocalDate.parse(s.getDay(), dateFormatter);
                            System.out.println(">> Checking day: " + day + " against " + weekStart + " to " + weekEnd);
                            if (!day.isBefore(weekStart) && !day.isAfter(weekEnd)) {
                                filtered.add(s);
                            }
                        } catch (Exception e) {
                            System.out.println("Error parsing day for allSchedules: " + s.getDay() + " | Error: " + e.getMessage());
                        }
                    }
                    scheduleStudent = filtered;
                    System.out.println(">> filtered (allSchedules) size = " + filtered.size());

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
                            System.out.println(">> Fallback to nearest week: " + weekStart + " to " + weekEnd + ", filtered size = " + filtered.size());
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

        request.getRequestDispatcher("schedule_student.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
