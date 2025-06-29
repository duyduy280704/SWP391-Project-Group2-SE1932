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
import java.util.Arrays;
import java.util.Map;
import models.NotificationDAO;

/**
 *
 * @author Dwight
 */
public class SendNotificationController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        NotificationDAO dao = new NotificationDAO();
        request.setAttribute("classList", dao.getAllClasses());
        request.getRequestDispatcher("SendNotification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sendType = request.getParameter("sendType");  // "individual", "role", "class"
        String message = request.getParameter("message");
        NotificationDAO dao = new NotificationDAO();
        String subject = "\uD83D\uDCE2 Thông báo từ trung tâm";

        switch (sendType) {
            case "individual" -> {
                String role = request.getParameter("role");
                String receiverId = request.getParameter("receiverId");
                dao.insertNotificationForOne(role, receiverId, message);

                String email = dao.getEmailByRoleAndId(role, receiverId);
                if (email != null && !email.isEmpty()) {
                    SendMail.send(email, subject, message);
                }
            }

            case "role" -> {
                String roleAll = request.getParameter("roleAll");
                Map<String, String> roleEmailMap = dao.insertNotificationByRole(roleAll, message);
                for (Map.Entry<String, String> entry : roleEmailMap.entrySet()) {
                    if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                        SendMail.send(entry.getValue(), subject, message);
                    }
                }
            }

            case "class" -> {
                String[] classIds = request.getParameterValues("classIds");
                if (classIds != null) {
                    Map<String, String> studentEmailMap = dao.insertNotificationByClasses(Arrays.asList(classIds), message);
                    for (Map.Entry<String, String> entry : studentEmailMap.entrySet()) {
                        if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                            SendMail.send(entry.getValue(), subject, message);
                        }
                    }
                }
            }
        }

        request.setAttribute("success", "\u2714\uFE0F Gửi thông báo thành công!");
        request.setAttribute("classList", dao.getAllClasses());
        request.getRequestDispatcher("SendNotification.jsp").forward(request, response);
    }
}
