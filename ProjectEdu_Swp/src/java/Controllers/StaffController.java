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
import java.util.ArrayList;
import models.Staff;
import models.StaffDAO;

/**
 *
 * @author Quang
 */
public class StaffController extends HttpServlet {
   
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
            out.println("<title>Servlet StaffController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffController at " + request.getContextPath () + "</h1>");
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
        StaffDAO sd = new StaffDAO();
        
        if (request.getParameter("mode") != null && request.getParameter("mode").equals("1")) {
            //tìm Product tương ứng cùng với code truyền sang
            String id = request.getParameter("id");
            Staff s = sd.getStaffById(id);
            request.setAttribute("s", s);
        }
        
        ArrayList<Staff> data = sd.getStaff();
        request.setAttribute("data", data);
        request.getRequestDispatcher("listStaff.jsp").forward(request, response);
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
        String id =request.getParameter("id");
        String name =request.getParameter("name");
        String account =request.getParameter("account");
        String password =request.getParameter("password");
        String email =request.getParameter("email");
        String sdt =request.getParameter("sdt");
        String address =request.getParameter("address");
        String role =request.getParameter("role");
        
        Staff s = new Staff(id, name, account, password, email, sdt, address, role);
        Staff u = new Staff(id, name, account, password, email, sdt, address);
        StaffDAO sd = new StaffDAO();
        
        if(request.getParameter("update")!=null){
            sd.update(s);
        }
        
        if(request.getParameter("add")!=null){
            sd.add(s);
        }
        
        if(request.getParameter("delete")!=null){
            sd.delete(s.getId());
        }
        
        ArrayList<Staff> data = sd.getStaff();
        
        request.setAttribute("data", data);
        request.getRequestDispatcher("listStaff.jsp").forward(request, response);
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
