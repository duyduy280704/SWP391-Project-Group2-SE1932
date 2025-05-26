package Controllers;


import models.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.StudentDAO;
import models.Students;
import models.TeacherDAO;
import models.Teachers;
import models.Users;

/**
 *
 * @author MY PC
 */
public class loginController extends HttpServlet {

    private final String LOGIN_PAGE = "login.jsp";
    private final String STAFF_PAGE = "index.jsp"; 
    private final String ADMIN_PAGE = "adminDashboard.jsp"; 
    private final String TEACHER_PAGE = "teacher.jsp";
    private final String STUDENT_PAGE = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        String url = LOGIN_PAGE;
        try {
            
            String username = request.getParameter("email"); 
            String password = request.getParameter("password");

           
            request.setAttribute("email", username);
            request.setAttribute("password", password);

           
            if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
                request.setAttribute("accountLoginError", "Vui lòng nhập email và mật khẩu!");
                request.getRequestDispatcher(url).forward(request, response);
                return;
            }

            
            UserDAO userDao = new UserDAO();
            Users user = userDao.checkLogin(username, password);
            
            StudentDAO studentDao = new StudentDAO();
teacher            Students student = studentDao.checkLogin(username, password);
            
            TeacherDAO teacherDao = new TeacherDAO();
            Teachers  = teacherDao.checkLogin(username, password);
            
            

            if (user != null) {
                
                HttpSession session = request.getSession();
                session.setAttribute("account", user); 
                
                if (user.getIdRole() == 1) {
                    url = ADMIN_PAGE;
                } else if(user.getIdRole() == 2) {
                    url = STAFF_PAGE;
                }
                request.setAttribute("successMessage", "Đăng nhập thành công!");
            } else if(student != null){
                
            }else if(teacher != null){
                
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
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "loginController for user authentication";
    }
}