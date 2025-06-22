package Controllers;

import models.ClassStudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import models.Courses;
import models.Students;

// Thuy _ Danh sách học sinh trong lớp 
public class ClassStudentController extends HttpServlet {

    private ClassStudentDAO dao = new ClassStudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        

        String mode = request.getParameter("mode");
        String search = request.getParameter("search");

        if (mode == null || mode.equals("list")) {
            List<Courses> classes;
            if (search != null && !search.isEmpty()) {
                classes = dao.searchClassesByName(search);
            } else {
                classes = dao.getAllClasses();
            }
            request.setAttribute("search", search);
            request.setAttribute("classes", classes);
            request.getRequestDispatcher("ListClass.jsp").forward(request, response);
        } else if (mode.equals("students")) {
            String classId = request.getParameter("classId");
            List<Students> students;
            if (search != null && !search.isEmpty()) {
                students = dao.searchStudentName(classId, search);
            } else {
                students = dao.getStudentsByClassId(classId);
            }
            String className = dao.getClassNameById(classId);
            request.setAttribute("classId", classId);
            request.setAttribute("className", className);
            request.setAttribute("search", search);
            request.setAttribute("students", students);
            request.getRequestDispatcher("ListStudentByClass.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}