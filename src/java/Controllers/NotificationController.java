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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import models.AdminStaffs;
import models.Notification;
import models.NotificationDAO;
import models.Students;
import models.Teachers;

/**
 *
 * @author Dwight
 */
public class NotificationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        NotificationDAO dao = new NotificationDAO();
        List<Notification> notifications = null;

        // Lấy role và user đang đăng nhập từ session
        HttpSession session = request.getSession();
        Object acc = session.getAttribute("account");

        if (acc == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (acc instanceof Students) {
            Students student = (Students) acc;
            notifications = dao.getNotificationsByStudent(String.valueOf(student.getId()));

        } else if (acc instanceof Teachers) {
            Teachers teacher = (Teachers) acc;
            notifications = dao.getNotificationsByTeacher(String.valueOf(teacher.getId()));

        } else if (acc instanceof AdminStaffs) {
            AdminStaffs staff = (AdminStaffs) acc;
            notifications = dao.getNotificationsByStaff(String.valueOf(staff.getId()));

        }
        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("Notification.jsp").forward(request, response);
    }

    
}
