package Controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.AdminStaffDAO;
import models.AdminStaffs;
import models.StudentDAO;
import models.Students;
import models.TeacherDAO;
import models.Teachers;
//Huyền-Đăng Nhập

public class LoginControllers extends HttpServlet {

    private final String LOGIN_PAGE = "login.jsp";
    private final String STAFF_PAGE = "staff.jsp";
    private final String ADMIN_PAGE = "admin.jsp";
    private final String TEACHER_PAGE = "teacherPage.jsp";
    private final String STUDENT_PAGE = "student.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = LOGIN_PAGE;
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            System.out.println("Email: " + email + ", Password: " + password);

            request.setAttribute("email", email);
            request.setAttribute("password", password);

            if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("accountLoginError", "Vui lòng nhập email và mật khẩu!");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            AdminStaffDAO adminStaffDao = new AdminStaffDAO();
            AdminStaffs adminStaff = adminStaffDao.checkLogin(email, password);

            StudentDAO studentDao = new StudentDAO();
            Students student = studentDao.checkLogin(email, password);

            TeacherDAO teacherDao = new TeacherDAO();
            Teachers teacher = teacherDao.checkLogin(email, password);

            if (adminStaff != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", adminStaff);
                System.out.println("AdminStaff RoleId: " + adminStaff.getRoleId());
                if ("4".equalsIgnoreCase(adminStaff.getRoleId())) {
                    url = ADMIN_PAGE;
                } else if ("3".equalsIgnoreCase(adminStaff.getRoleId())) {
                    url = STAFF_PAGE;
                }
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            } else if (student != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", student);
                System.out.println("Student RoleId: " + student.getRole());
                if ("1".equalsIgnoreCase(student.getRole())) {
                    url = STUDENT_PAGE;
                }
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            } else if (teacher != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", teacher);
                System.out.println("Teacher RoleId: " + teacher.getRole());
                if ("2".equalsIgnoreCase(teacher.getRole())) {
                    url = TEACHER_PAGE;
                }
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            } else {
                request.setAttribute("accountLoginError", "Email hoặc mật khẩu không đúng!");
            }
            System.out.println("Redirecting to: " + url);
        } catch (Exception e) {
            log("Unexpected error: " + e.getMessage(), e);
            request.setAttribute("accountLoginError", "Lỗi hệ thống: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "LoginControllers for user authentication";
    }
}