/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import models.About;
import models.AboutDAO;
import models.Blog;
import models.BlogDAO;
import models.Categories_class;
import models.ClassTransferRequest;
import models.ClassTransferRequestDAO;
import models.CourseDAO;
import models.Courses;
import models.Event;
import models.EventDAO;
import models.Information;
import models.InformationDAO;
import models.Notification;
import models.NotificationDAO;
import models.ScheduleStudent;
import models.ScheduleStudentDAO;
import models.ScheduleWeek;
import models.StudentDAO;
import models.Students;
import models.TeacherDAO;
import models.Teachers;
import models.TypeCourse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author Dwight
 */
public class StudentHomeController extends HttpServlet {
    
    private CourseDAO courseDAO = new CourseDAO();
    private InformationDAO daoI = new InformationDAO();
    private TeacherDAO daoo = new TeacherDAO();
    private AboutDAO dao2 = new AboutDAO();
    
    public void init() {
        Information setting = daoI.getSetting();
        getServletContext().setAttribute("setting", setting);
        List<TypeCourse> typeList = courseDAO.getType();
        getServletContext().setAttribute("typeList", typeList);     
        List<Teachers> teacherlist = daoo.getTeachers();
        getServletContext().setAttribute("teacherlist", teacherlist); 
        List<About> list = dao2.getAllAbouts();
        getServletContext().setAttribute("aboutList", list);
    }
    
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
        StudentDAO studentDAO = new StudentDAO();
        Students stu = studentDAO.getStudentById(studentId);
        request.setAttribute("name", stu.getName());
        ScheduleStudentDAO schedule = new ScheduleStudentDAO();
        List<ScheduleStudent> sche = schedule.getTop3UpcomingSchedulesByStudentId(id);
        request.setAttribute("schedules", sche);
        CourseDAO courseDAO = new CourseDAO();
        List<Courses> course6 = courseDAO.get6Courses();
        request.setAttribute("courseList", course6);
        EventDAO dao = new EventDAO();
        List<Event> eventList = dao.getUpcomingEvents();
        List<Event> events = dao.getRecentEvents(3);
        request.setAttribute("events", events);
        request.setAttribute("eventList", eventList);
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogList = blogDAO.getLatest3Blogs();
        request.setAttribute("blogList", blogList);
         NotificationDAO daoo = new NotificationDAO();
        List<Notification> list = daoo.getLatestNotificationsByStudent(studentId);

        request.setAttribute("notifications", list);
        
        

        // Xử lý năm
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

        ScheduleStudentDAO daoz = new ScheduleStudentDAO();
        ClassTransferRequestDAO transferDAO = new ClassTransferRequestDAO();
        List<ScheduleStudent> scheduleStudent = new ArrayList<>();

        // Kiểm tra chuyển lớp
        ClassTransferRequest transfer = transferDAO.getLastApprovedRequest(stu.getId());
        if (transfer != null) {
            System.out.println(">> Học sinh đã chuyển lớp, từ lớp " + transfer.getFromClass().getName_class()
                    + " đến lớp " + transfer.getToClass().getName_class()
                    + " vào ngày " + transfer.getTransferDate());
        } else {
            System.out.println(">> Học sinh chưa từng chuyển lớp");
        }

        if (transfer != null && transfer.getTransferDate() != null) {
            LocalDate transferDate = new java.sql.Date(transfer.getTransferDate().getTime()).toLocalDate();
            List<ScheduleStudent> oldList = daoz.getScheduleBefore(transfer.getFromClass().getId_class(), transferDate, id);
            System.out.println(">> oldList (lớp cũ) size = " + oldList.size());

            List<ScheduleStudent> newList = daoz.getScheduleAfter(transfer.getToClass().getId_class(), transferDate, id);
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
            Categories_class currentClass = transferDAO.getClassByStudentId(stu.getId());
            if (currentClass != null) {
                System.out.println(">> Học sinh đang thuộc lớp: " + currentClass.getName_class() + " (ID: " + currentClass.getId_class() + ")");
                String classIdToUse = currentClass.getId_class();
                System.out.println(">> Initial classId: " + classIdToUse);
                if (classIdToUse == null || classIdToUse.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    System.out.println(">> Invalid classId detected: " + classIdToUse + ", using default ID 2");
                    classIdToUse = "2";
                }
                try {
                    List<ScheduleStudent> allSchedules = daoz.getScheduleStudentAll(id, classIdToUse);
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
                System.out.println(">> No class found for studentId: " + stu.getId());
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
        session.setAttribute("profile", stu); // Truyền thông tin giáo viên
        session.setAttribute("picturePath", session.getAttribute("picturePath"));
        request.setAttribute("weekDays", weekDays);
        request.setAttribute("scheduleStudent", scheduleStudent);
        request.setAttribute("weeks", weeks);
        request.setAttribute("years", years);
        request.setAttribute("selectedWeek", baseDate.format(dbFormatter));
        request.setAttribute("selectedYear", year);

        request.getRequestDispatcher("StudentHome.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        doGet(request, response);

    }

}