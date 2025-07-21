package Controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Categories_class;
import models.Courses;
import models.CreateClassDAO;

/**
 *
 * @author Thuy tạo lớp học từ khóa học đã có
 */
public class CreateClassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        CreateClassDAO dao = new CreateClassDAO();
        String keyword = request.getParameter("search");

        List<Courses> courses = dao.getAllCourses();
        List<Categories_class> classList;

        if (keyword != null && !keyword.trim().isEmpty()) {
            classList = dao.searchClasses(keyword.trim());
            request.setAttribute("search", keyword.trim());
        } else {
            classList = dao.getAllClasses();
        }

        request.setAttribute("courses", courses);
        request.setAttribute("classes", classList);
        request.getRequestDispatcher("CreateClass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        CreateClassDAO dao = new CreateClassDAO();

        String deleteId = request.getParameter("delete_id");
        String editId = request.getParameter("edit_id");
        String className = request.getParameter("class_name");
        String courseId = request.getParameter("course_id");

        if (deleteId != null && !deleteId.isEmpty()) {
            dao.deleteClass(deleteId);
            request.setAttribute("success", "Xóa lớp thành công!");
        } else if (editId != null && courseId == null && request.getParameter("submit_edit") == null) {            
            request.setAttribute("edit_id", editId);
            request.setAttribute("edit_name", className);
        } else if (editId != null && className != null && !className.trim().isEmpty()) {
            dao.updateClassName(editId, className);
            request.setAttribute("success", "Cập nhật tên lớp thành công!");
        } else {

            if (className != null && courseId != null && !className.trim().isEmpty()) {
                dao.createClass(className, courseId);
                request.setAttribute("success", "Tạo lớp thành công!");
            }
        }

        List<Courses> courses = dao.getAllCourses();
        List<Categories_class> classList = dao.getAllClasses();
        request.setAttribute("courses", courses);
        request.setAttribute("classes", classList);
        request.getRequestDispatcher("CreateClass.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "CreateClass Controller";
    }
}
