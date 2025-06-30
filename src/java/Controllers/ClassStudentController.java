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
        String search = request.getParameter("search");
        String classId = request.getParameter("classId");

        // === STAFF ===
        if (account instanceof AdminStaffs) {
            if (mode == null || mode.equals("list")) {
                List<Courses> classes = (search != null && !search.isEmpty())
                        ? dao.searchClassesByName(search)
                        : dao.getAllClasses();
                request.setAttribute("search", search);
                request.setAttribute("classes", classes);
                request.getRequestDispatcher("ListClass.jsp").forward(request, response);
                return;
            }

            if ("students".equals(mode) && classId != null) {
                List<Students> students = (search != null && !search.isEmpty())
                        ? dao.searchStudentName(classId, search)
                        : dao.getStudentsByClassId(classId);
                String className = dao.getClassNameById(classId);
                request.setAttribute("classId", classId);
                request.setAttribute("className", className);
                request.setAttribute("search", search);
                request.setAttribute("students", students);
                request.getRequestDispatcher("ListStudentByClass.jsp").forward(request, response);
                return;
            }
        }

        // === GIÁO VIÊN ===
        if (account instanceof Teachers) {
            Teachers teacher = (Teachers) account;

            // Xem danh sách lớp giáo viên dạy
            if (mode == null || mode.equals("teacherView")) {
                List<Courses> teacherClasses = dao.getClassesByTeacher(teacher.getId());
                request.setAttribute("classes", teacherClasses);
                request.getRequestDispatcher("ListClass_Teacher.jsp").forward(request, response);
                return;
            }

            // Xem danh sách học sinh trong lớp (nếu giáo viên dạy lớp đó)
            if ("students".equals(mode) && classId != null) {
                List<Students> students = (search != null && !search.isEmpty())
                        ? dao.searchStudentName(classId, search)
                        : dao.getStudentsByClassId(classId);
                String className = dao.getClassNameById(classId);

                request.setAttribute("classId", classId);
                request.setAttribute("className", className);
                request.setAttribute("search", search);
                request.setAttribute("students", students);

                // Đảm bảo giáo viên luôn vào đúng giao diện riêng
                request.getRequestDispatcher("ListStudentByClass_Teacher.jsp").forward(request, response);
                return;
            }

        }

        // Nếu không phải giáo viên hoặc staff → về trang login
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
