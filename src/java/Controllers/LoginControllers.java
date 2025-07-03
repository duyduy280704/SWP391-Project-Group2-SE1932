package Controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import models.AdminStaffDAO;
import models.AdminStaffs;
import models.CourseDAO;
import models.Information;
import models.InformationDAO;
import models.StudentDAO;
import models.Students;
import models.TeacherDAO;
import models.Teachers;
import models.TypeCourse;

public class LoginControllers extends HttpServlet {
    
    private CourseDAO courseDAO = new CourseDAO();
    private InformationDAO daoI = new InformationDAO();
    
    
    public void init() {
        Information setting = daoI.getSetting();
        getServletContext().setAttribute("setting", setting);
        List<TypeCourse> typeList = courseDAO.getType();
        getServletContext().setAttribute("typeList", typeList);     
              
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String phone = request.getParameter("phone");
            String password = request.getParameter("password");
            String message = "";

            // Kiểm tra đầu vào
            if (phone == null || phone.trim().isEmpty()) {
                message = "Vui lòng nhập số điện thoại!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            if (!phone.matches("\\d{10,11}")) {
                message = "Số điện thoại phải có 10-11 số!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
            if (password == null || password.trim().isEmpty()) {
                message = "Vui lòng nhập mật khẩu!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Kiểm tra đăng nhập
            AdminStaffDAO a = new AdminStaffDAO();
            AdminStaffs staff = a.checkLogin(phone, password);

            StudentDAO s = new StudentDAO();
            Students student = s.checkLogin(phone, password);

            TeacherDAO t = new TeacherDAO();
            Teachers teacher = t.checkLogin(phone, password);

            HttpSession session = request.getSession();
            if (staff != null) {
                session.setAttribute("account", staff);
                session.setAttribute("role", "staff");
                System.out.println("AdminStaff RoleId: " + staff.getRole());
                if ("4".equals(staff.getRole())) {
                    response.sendRedirect("adminhome");
                } else if ("3".equals(staff.getRole())) {
                    response.sendRedirect("staffhome");
                }
                return;
            } else if (student != null) {
                session.setAttribute("account", student);
                session.setAttribute("role", "student");
                System.out.println("Student RoleId: " + student.getRole());
                response.sendRedirect("StudentHome");
                return;
            } else if (teacher != null) {
                session.setAttribute("account", teacher);
                session.setAttribute("role", "teacher");
                System.out.println("Teacher RoleId: " + teacher.getRole());
                response.sendRedirect("teacherHome");
                return;
            } else {
                message = "Số điện thoại hoặc mật khẩu không đúng!";
                request.setAttribute("message", message);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Đã xảy ra lỗi hệ thống: " + e.getMessage());
            request.setAttribute("phone", request.getParameter("phone"));
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