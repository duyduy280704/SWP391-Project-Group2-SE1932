package Controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import models.AdminStaffDAO;
import models.AdminStaffs;
import models.StudentDAO;
import models.Students;
import models.TeacherDAO;
import models.Teachers;
//Huyền-Đăng Nhập

public class LoginControllers extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String message = "";

            if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                message += "Vui lòng nhập email và mật khẩu!";
                request.setAttribute("message", message);
                request.setAttribute("email", email); 
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
          
            AdminStaffDAO a = new AdminStaffDAO();
            AdminStaffs staff = a.checkLogin(email, password);

            StudentDAO s = new StudentDAO();
            Students student = s.checkLogin(email, password);

            TeacherDAO t = new TeacherDAO();
            Teachers teacher = t.checkLogin(email, password);

            if (staff != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", staff);
                System.out.println("AdminStaff RoleId: " + staff.getRoleId());
                if ("4".equalsIgnoreCase(staff.getRoleId())) {
                    request.getRequestDispatcher("admin.jsp").forward(request, response);
                } else if ("3".equalsIgnoreCase(staff.getRoleId())) {
                    request.getRequestDispatcher("staff.jsp").forward(request, response);
                }
                message += "Đăng nhập thành công!";
                request.setAttribute("message", message);
            } else if (student != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", student);
                System.out.println("Student RoleId: " + student.getRole());
                if ("1".equalsIgnoreCase(student.getRole())) {
                    request.getRequestDispatcher("student.jsp").forward(request, response);
                    return;
                }
                message += "Đăng nhập thành công!";
                request.setAttribute("message", message);
            } else if (teacher != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", teacher);
                System.out.println("Teacher RoleId: " + teacher.getRole());
                if ("2".equalsIgnoreCase(teacher.getRole())) {
                    request.getRequestDispatcher("teacherPage.jsp").forward(request, response);
                    return;
                }
                message += "Đăng nhập thành công!";
                request.setAttribute("message", message);
            } else {
                message += "Email hoặc mật khẩu không đúng!";
                request.setAttribute("message", message);
                return;
            }

        } catch (Exception e) {
             e.printStackTrace();
            request.setAttribute("message", "Đã xảy ra lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
