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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ResultMessage;
import models.StudentDAO;
import models.Students;

/**
 *
 * @author Quang
 */
public class StudentController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet StudentController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        StudentDAO sd = new StudentDAO();

        if (request.getParameter("mode") != null && request.getParameter("mode").equals("1")) {
            //tìm Product tương ứng cùng với code truyền sang
            String id = request.getParameter("id");
            Students s = sd.getStudentById(id);
            request.setAttribute("s", s);
        }

        if (request.getParameter("mode") != null && request.getParameter("mode").equals("2")) {

            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                sd.delete(id);
                request.setAttribute("message", "Xóa học sinh thành công!");
            } else {
                request.setAttribute("error", "ID không hợp lệ!");
            }
        }

        ArrayList<Students> data = sd.getStudents();
        

        request.setAttribute("data", data);
        request.getRequestDispatcher("listStudent.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthdate = request.getParameter("birthdate");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
         String phone=request.getParameter("phone");
         String pic=request.getParameter("picture");
        String role = "1";

        ResultMessage result = null;

        Students s = new Students(id, name, email, password, birthdate, gender, address, role, phone, pic);
        Students u = new Students(id, name, email, password, birthdate, gender, address, role, phone, pic);

        StudentDAO sd = new StudentDAO();

        try {

            if (request.getParameter("update") != null) {
                // Cập nhật học sinh
                result = sd.update(u);
            } else if (request.getParameter("add") != null) {
                // Thêm học sinh
                result = sd.add(s);
            } else {
                result = new ResultMessage(false, "Hành động không hợp lệ!");
            }
        } catch (SQLException e) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, e);
            result = new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (NumberFormatException e) {
            result = new ResultMessage(false, "Dữ liệu không hợp lệ: " + e.getMessage());
        }

        ArrayList<Students> data = sd.getStudents();
        // Lưu thông báo vào request để truyền sang JSP
        request.setAttribute("message", result.getMessage());
        request.setAttribute("success", result.isSuccess());
        request.setAttribute("data", data);
        request.getRequestDispatcher("listStudent.jsp").forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}