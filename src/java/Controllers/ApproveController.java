/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.PreRegistration;
import models.PreRegistrationDAO;

/**
 *
 * @author Dwight
 */
public class ApproveController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PreRegistrationDAO dao = new PreRegistrationDAO();
        List<PreRegistration> list = dao.getAllPreRegistrations();

        request.setAttribute("list", list);
        request.getRequestDispatcher("confirmCourse.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        int id = Integer.parseInt(request.getParameter("id"));
        PreRegistrationDAO dao = new PreRegistrationDAO();

        if ("accept".equals(action)) {
            // Cập nhật trạng thái thành "Đã xác nhận"
            dao.updateStatus(id, "Đã duyệt");

        } else if ("reject".equals(action)) {
            String reason = request.getParameter("reason");
            dao.updateStatus(id, "Chưa xếp được lớp");

        }

        response.sendRedirect("Approve");
        
        String note = request.getParameter("note");

       
        dao.updateNote(id, note);

        response.setContentType("text/plain");
        response.getWriter().write("success");
    }
}
