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
import java.util.List;
import models.CourseDAO;
import models.Courses;
import models.TypeCourse;

/**
 *
 * @author Dwight
 */
public class CourseController extends HttpServlet {
   
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String search = request.getParameter("search");
        String type = request.getParameter("type");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");


        CourseDAO dao = new CourseDAO();

        List<Courses> courseList = dao.searchCourses(search, type, minPrice, maxPrice);
        List<TypeCourse> typeList = dao.getType();

        request.setAttribute("courseList", courseList);
        request.setAttribute("typeList", typeList);

        request.getRequestDispatcher("course.jsp").forward(request, response);
    }
}
