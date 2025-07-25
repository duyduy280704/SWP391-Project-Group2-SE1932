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
import java.util.ArrayList;
import models.StudentDAO;
import models.Students;
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
        HttpSession session = request.getSession();
        Students st = (Students) session.getAttribute("account");
        if (st == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String studentId = st.getId();
        TeacherDAO dao = new TeacherDAO();
        StudentDAO studentDAO = new StudentDAO();
        Students stu = studentDAO.getStudentById(studentId);
        request.setAttribute("name", stu.getName());
        request.setAttribute("profile", stu); // Truyền thông tin giáo viên
        request.setAttribute("picturePath", session.getAttribute("picturePath"));
        ArrayList<Teachers> teacherList = dao.getTeachersWithCourseName(); // lấy toàn bộ danh sách giáo viên
        request.setAttribute("teacherList", teacherList);
        request.getRequestDispatcher("teacherdetailst.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       TeacherDAO dao = new TeacherDAO();
        ArrayList<Teachers> teacherList = dao.getTeachersWithCourseName(); // lấy toàn bộ danh sách giáo viên
        request.setAttribute("teacherList", teacherList);
        request.getRequestDispatcher("teacherdetailst.jsp").forward(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}