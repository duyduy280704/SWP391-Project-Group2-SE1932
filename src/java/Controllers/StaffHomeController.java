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
import java.util.List;
import models.ChartDAO;
import models.FeedbackStaff;
import models.FeedbackStaffDAO;

import models.TypeCourseCount;

/**
 *
 * @author Quang
 */
public class StaffHomeController extends HttpServlet {
   
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
            out.println("<title>Servlet StaffHomeController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StaffHomeController at " + request.getContextPath () + "</h1>");
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
        ChartDAO dao = new ChartDAO();
        FeedbackStaffDAO fd =new FeedbackStaffDAO();
        try {
            int student = dao.getStudentCount();
            int teacher = dao.getTeacherCount();
            int staff = dao.getAdminStaffCount();
            int course = dao.getCourseCount();
            
            ArrayList<FeedbackStaff> data = fd.getTop4Feedback();

        
        
            List<TypeCourseCount> chartData = dao.getCourseCountByType();
           
            request.setAttribute("chartData", chartData);
            request.setAttribute("student", student);
            request.setAttribute("teacher", teacher);
            request.setAttribute("staff", staff);
            request.setAttribute("course", course);
            request.setAttribute("data", data);
            request.getRequestDispatcher("StaffHome.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
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