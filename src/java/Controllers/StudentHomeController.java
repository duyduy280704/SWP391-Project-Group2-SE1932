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
        request.setAttribute("eventList", eventList);
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogList = blogDAO.getLatest3Blogs();
        request.setAttribute("blogList", blogList);
         NotificationDAO daoo = new NotificationDAO();
        List<Notification> list = daoo.getLatestNotificationsByStudent(studentId);

        request.setAttribute("notifications", list);
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
