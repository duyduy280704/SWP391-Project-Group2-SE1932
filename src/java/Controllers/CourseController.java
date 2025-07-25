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
import models.CourseDAO;
import models.Courses;
import models.StudentDAO;
import models.Students;
import models.TypeCourse;

/**
 *
 * @author Dương
 */
public class CourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Students student = (Students) session.getAttribute("account");
        
        String search = request.getParameter("search");
        String type = request.getParameter("type");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        
        String role=null;
        String name=null;
        CourseDAO dao = new CourseDAO();
        if (student != null ){
            role = student.getRole();
            name = student.getName();
        }
        List<Courses> courseList = dao.searchCourses(search, type, minPrice, maxPrice);
        List<TypeCourse> typeList = dao.getType();

        request.setAttribute("courseList", courseList);
        request.setAttribute("typeList", typeList);
     
        request.setAttribute("name", name);
        request.setAttribute("profile", student); // Truyền thông tin giáo viên
        request.setAttribute("picturePath", session.getAttribute("picturePath"));
        if (role != null) {
            request.getRequestDispatcher("courseList.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("course.jsp").forward(request, response);
        }
    }
}
