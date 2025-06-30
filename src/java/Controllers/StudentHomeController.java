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
import models.Blog;
import models.BlogDAO;
import models.CourseDAO;
import models.Courses;
import models.Event;
import models.EventDAO;
import models.Notification;
import models.NotificationDAO;
import models.ScheduleDAO;
import models.ScheduleStudent;
import models.ScheduleStudentDAO;
import models.ScheduleWeek;
import models.StudentDAO;
import models.Students;

/**
 *
 * @author Dwight
 */
public class StudentHomeController extends HttpServlet {
    
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

        ScheduleStudentDAO dao1 = new ScheduleStudentDAO();
        List<ScheduleStudent> scheduleStudent = dao1.getScheduleStudent(id, baseDate.format(dbFormatter));

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

        request.getRequestDispatcher("StudentHome.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        request.setAttribute("eventList", eventList);
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogList = blogDAO.getLatest3Blogs();
        request.setAttribute("blogList", blogList);
        request.getRequestDispatcher("StudentHome.jsp").forward(request, response);

    }

}
