package Controllers;

import models.ClassStudentDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import models.Courses;
import models.Students;
import models.AdminStaffs;
import models.Teachers;

public class ClassStudentController extends HttpServlet {
// Thủy _ danh sách học sinh của lớp
    private final ClassStudentDAO dao = new ClassStudentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String mode = request.getParameter("mode");
        String keyword = request.getParameter("keyword");
        String classId = request.getParameter("classId");

        // dăng nhập của staff
        if (account instanceof AdminStaffs) {
            if (mode == null || mode.equals("list")) {
                List<Courses> classes = (keyword != null && !keyword.isEmpty())
                        ? dao.searchClassesByName(keyword)
                        : dao.getAllClasses();

                request.setAttribute("keyword", keyword);
                request.setAttribute("classes", classes);
                request.getRequestDispatcher("ListClass.jsp").forward(request, response);
                return;
            }

            if ("students".equals(mode) && classId != null) {
                List<Students> students = (keyword != null && !keyword.isEmpty())
                        ? dao.searchStudentName(classId, keyword)
                        : dao.getStudentsByClassId(classId);

                String className = dao.getClassNameById(classId);
                request.setAttribute("classId", classId);
                request.setAttribute("className", className);
                request.setAttribute("keyword", keyword);
                request.setAttribute("students", students);
                request.getRequestDispatcher("ListStudentByClass.jsp").forward(request, response);
                return;
            }
        }

        // Teacher đăng nhập
        if (account instanceof Teachers) {
            Teachers teacher = (Teachers) account;

            // Xem danh sách lớp
            if (mode == null || mode.equals("teacherView")) {
                List<Courses> teacherClasses = (keyword != null && !keyword.isEmpty())
                        ? dao.search(teacher.getId(), keyword)
                        : dao.getClassesByTeacher(teacher.getId());

                request.setAttribute("keyword", keyword);
                request.setAttribute("classes", teacherClasses);
                request.getRequestDispatcher("ListClass_Teacher.jsp").forward(request, response);
                return;
            }

            // Xem học sinh trong lớp
            if ("students".equals(mode) && classId != null) {
                List<Students> students = (keyword != null && !keyword.isEmpty())
                        ? dao.searchStudentName(classId, keyword)
                        : dao.getStudentsByClassId(classId);

                String className = dao.getClassNameById(classId);
                request.setAttribute("classId", classId);
                request.setAttribute("className", className);
                request.setAttribute("keyword", keyword);
                request.setAttribute("students", students);
                request.getRequestDispatcher("ListStudentByClass_Teacher.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}