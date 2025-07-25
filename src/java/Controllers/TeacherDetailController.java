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
import models.TeacherDAO;
import models.Teachers;

/**
 *
 * @author Dương
 */
public class TeacherDetailController extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        TeacherDAO dao = new TeacherDAO();
        Teachers teacher = dao.getTeacherById(id);

        request.setAttribute("teacher", teacher);
        request.getRequestDispatcher("teacherDetail.jsp").forward(request, response);
    }
   
}
