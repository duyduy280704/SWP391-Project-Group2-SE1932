package Controllers;

import dal.DBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import models.UserDAO;


//Huyền-Quên mật khẩu
public class ForgotPasswordServlet extends HttpServlet {
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
    }


  

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDAO dao = new UserDAO();
        String table = dao.checkExistEmail(email);
        
        if (table != null) {
 
            request.setAttribute("email", email);
            request.setAttribute("table", table);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "Không tìm thấy email trong hẹ thống.");
            request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
        }
    }

   
}