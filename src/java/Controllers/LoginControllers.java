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
import models.AdminStaffDAO;
import models.AdminStaffs;
import models.StudentDAO;
import models.Students;
import models.TeacherDAO;
import models.Teachers;

/**
 *
 * @author HP
 */
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

           
            request.setAttribute("email", email);
            request.setAttribute("password", password);

           
            if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("accountLoginError", "Vui lòng nhập email và mật khẩu!");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            
            AdminStaffDAO adminStaffDao=new AdminStaffDAO();
            AdminStaffs adminStaff= adminStaffDao.checkLogin(email, password);
            
            StudentDAO studentDao = new StudentDAO();
            Students student = studentDao.checkLogin(email, password);
            
            TeacherDAO teacherDao = new TeacherDAO();
            Teachers teacher = teacherDao.checkLogin(email, password);
            
            

            if (adminStaff != null) {
                
                HttpSession session = request.getSession();
                session.setAttribute("account", adminStaff); 
                
                if ("4".equalsIgnoreCase(adminStaff.getRoleID())) {
                    url = ADMIN_PAGE;
                } else if("3".equalsIgnoreCase(adminStaff.getRoleID())) {
                    url = STAFF_PAGE;
                }
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            } else if(student != null){
                 HttpSession session = request.getSession();
                session.setAttribute("account", student); 
                
                if ("3".equalsIgnoreCase(student.getRoleId())) {
                    url = STUDENT_PAGE;
                } 
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            
            }else if(teacher != null){
                HttpSession session = request.getSession();
                session.setAttribute("account", student); 
                
                if ("4".equalsIgnoreCase(teacher.getRoleId())) {
                    url = TEACHER_PAGE;
                } 
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            
            }else {
                
                request.setAttribute("accountLoginError", "Email hoặc mật khẩu không đúng!");
            }
        } catch (Exception e) {
            log("loginController error: " + e.getMessage(), e);
            request.setAttribute("accountLoginError", "Đã xảy ra lỗi: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
          response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = LOGIN_PAGE;
        try {
            
            String email = request.getParameter("email"); 
            String password = request.getParameter("password");

           
            request.setAttribute("email", email);
            request.setAttribute("password", password);

           
            if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("accountLoginError", "Vui lòng nhập email và mật khẩu!");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            
            AdminStaffDAO adminStaffDao=new AdminStaffDAO();
            AdminStaffs adminStaff= adminStaffDao.checkLogin(email, password);
            
            StudentDAO studentDao = new StudentDAO();
            Students student = studentDao.checkLogin(email, password);
            
            TeacherDAO teacherDao = new TeacherDAO();
            Teachers teacher = teacherDao.checkLogin(email, password);
            
            

            if (adminStaff != null) {
                
                HttpSession session = request.getSession();
                session.setAttribute("account", adminStaff); 
                
                if ("4".equalsIgnoreCase(adminStaff.getRoleID())) {
                    url = ADMIN_PAGE;
                } else if("3".equalsIgnoreCase(adminStaff.getRoleID())) {
                    url = STAFF_PAGE;
                }
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            } else if(student != null){
                 HttpSession session = request.getSession();
                session.setAttribute("account", student); 
                
                if ("3".equalsIgnoreCase(student.getRoleId())) {
                    url = STUDENT_PAGE;
                } 
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            
            }else if(teacher != null){
                HttpSession session = request.getSession();
                session.setAttribute("account", student); 
                
                if ("4".equalsIgnoreCase(teacher.getRoleId())) {
                    url = TEACHER_PAGE;
                } 
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            
            }else {
                
                request.setAttribute("accountLoginError", "Email hoặc mật khẩu không đúng!");
            }
        } catch (Exception e) {
            log("loginController error: " + e.getMessage(), e);
            request.setAttribute("accountLoginError", "Đã xảy ra lỗi: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "loginController for user authentication";
    }
}