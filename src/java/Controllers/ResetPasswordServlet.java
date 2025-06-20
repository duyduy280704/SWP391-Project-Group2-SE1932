

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
import models.UserDAO;

import models.UserDAO;

/**
 *
 * @author HP
 */
// Huyền- Nhập mật khẩu mới
public class ResetPasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ResetPasswordServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ResetPasswordServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String phone = request.getParameter("phone");
        String table = request.getParameter("table");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String message = "";

        if (newPassword.isBlank()) {
            message += "Bạn phải mật khẩu và xác nhận mật khẩu.";
            request.setAttribute("phone", phone);
            request.setAttribute("table", table);
            request.setAttribute("message", message);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        if (confirmPassword.isBlank()) {
            message += "Bạn phải điền mật khẩu và xác nhận mật khẩu.";
            request.setAttribute("phone", phone);
            request.setAttribute("table", table);
            request.setAttribute("message", message);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }
        

        if (newPassword.length() <= 6 || !newPassword.matches(".*[a-z]*")
                || !newPassword.matches(".*[A-Z]*") || !newPassword.matches(".*\\d.*")|| !newPassword.matches(".*[^a-zA-Z0-9].*")) {
            message += "Mật khẩu phải trên 6 kí tự,có ít nhất 1 chữ thường,1 chữ in hoa, 1 số và 1 ký hiệu đặc biệt";
            request.setAttribute("phone", phone);
            request.setAttribute("table", table);
            request.setAttribute("message", message);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            message += "Mật khẩu chưa khớp.Vui lòng nhập lại";
            request.setAttribute("phone", phone);
            request.setAttribute("table", table);
            request.setAttribute("message", message);
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        UserDAO dao = new UserDAO();
        try {
            dao.updatePassword(table, phone, newPassword);
            message += "Đổi mật khẩu thành công. Mời bạn đăng nhập.";
            request.setAttribute("phone", phone);
            request.setAttribute("table", table);
            request.setAttribute("message", message);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Có lỗi xảy ra khi cập nhật mật khẩu: " + e.getMessage());

            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
        }

    }

}
