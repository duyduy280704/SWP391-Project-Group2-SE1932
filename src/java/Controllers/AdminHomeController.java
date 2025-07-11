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
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import models.ChartDAO;
import models.CourseDAO;
import models.Revenue;
import models.StudentRegistration;
import models.TypeCourseCount;
import models.TypeStudentCount;

/**
 *
 * @author Quang
 */
public class AdminHomeController extends HttpServlet {

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
            out.println("<title>Servlet AdminHomeController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminHomeController at " + request.getContextPath() + "</h1>");
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
         ChartDAO dao = new ChartDAO();
        try {
            int student = dao.getStudentCount();
            int teacher = dao.getTeacherCount();
            int staff = dao.getAdminStaffCount();
            int course = dao.getCourseCount();
            List<Revenue> revenueData = dao.getMonthlyRevenue();
            List<StudentRegistration> studentData = dao.getStudentRegistrationByMonth();
            List<TypeStudentCount> studentCountByType = dao.getStudentCountByType();
            List<TypeCourseCount> chartData = dao.getCourseCountByType();

            // Lấy danh sách năm từ revenueData và studentData
            Set<Integer> years = new TreeSet<>();
            for (Revenue revenue : revenueData) {
                String[] parts = revenue.getMonth().split("-");
                if (parts.length > 0) {
                    years.add(Integer.valueOf(parts[0]));
                }
            }
            for (StudentRegistration registration : studentData) {
                String[] parts = registration.getMonth().split("-");
                if (parts.length > 0) {
                    years.add(Integer.valueOf(parts[0]));
                }
            }

            request.setAttribute("years", new ArrayList<>(years));
            request.setAttribute("revenueData", revenueData);
            request.setAttribute("studentData", studentData);
            request.setAttribute("chartData", chartData);
            request.setAttribute("studentCountByType", studentCountByType);
            request.setAttribute("student", student);
            request.setAttribute("teacher", teacher);
            request.setAttribute("staff", staff);
            request.setAttribute("course", course);
            request.getRequestDispatcher("AdminHome.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}