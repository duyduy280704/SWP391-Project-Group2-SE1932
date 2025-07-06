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
import java.util.ArrayList;
import models.SalaryTeacher;
import models.SalaryTeacherDAO;
import models.Teachers;

/**
 *
 * @author Quang
 */
public class SalaryTeacherController extends HttpServlet {

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
            out.println("<title>Servlet SalaryTeacherController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SalaryTeacherController at " + request.getContextPath() + "</h1>");
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
        HttpSession session = request.getSession();
        Teachers teacher = (Teachers) session.getAttribute("account");

        if (teacher == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId;
        try {
            teacherId = Integer.parseInt(teacher.getId());
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp");
            return;
        }

        SalaryTeacherDAO sd = new SalaryTeacherDAO();
        ArrayList<SalaryTeacher> data = null;

        String monthParam = request.getParameter("month");
        Integer month = null;
        if (monthParam != null && !monthParam.isEmpty()) {
            try {
                month = Integer.parseInt(monthParam);
                if (month < 1 || month > 12) {
                    request.setAttribute("error", "Tháng không hợp lệ. Vui lòng chọn từ 1 đến 12.");
                    month = null;
                } else {
                    data = sd.getSalaryListByTeacherIdAndMonth(teacherId, month);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Tháng không hợp lệ. Vui lòng chọn lại.");
            }
        } else {
            data = sd.getSalaryListByTeacherId(teacherId);
        }

        if (data == null || data.isEmpty()) {
            request.setAttribute("error", month != null
                    ? "Không tìm thấy dữ liệu lương cho tháng " + month
                    : "Không tìm thấy dữ liệu lương.");
        } else {
            request.setAttribute("data", data);
        }

        request.setAttribute("picturePath", session.getAttribute("picturePath"));
        request.setAttribute("profile", teacher);
        request.getRequestDispatcher("salaryTeacher.jsp").forward(request, response);
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
