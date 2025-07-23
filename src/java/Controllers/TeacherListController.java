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
import java.util.ArrayList;
import models.TeacherDAO;
import models.Teachers;

/**
 *
 * @author HP
 */
//Huyền
public class TeacherListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TeacherDAO dao = new TeacherDAO();
        ArrayList<Teachers> teacherList = dao.getTeachersWithCourseName(); // lấy toàn bộ danh sách giáo viên
        request.setAttribute("teacherList", teacherList);
        request.getRequestDispatcher("teacherdetail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       TeacherDAO dao = new TeacherDAO();
        ArrayList<Teachers> teacherList = dao.getTeachersWithCourseName(); // lấy toàn bộ danh sách giáo viên
        request.setAttribute("teacherList", teacherList);
        request.getRequestDispatcher("teacherdetail.jsp").forward(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}