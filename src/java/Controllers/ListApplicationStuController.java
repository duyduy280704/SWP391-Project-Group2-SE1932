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
import models.Application;
import models.ApplicationDAO;
import models.ResultMessage;
import models.TeacherApplicationType;

/**
 *
 * @author Quang
 */
public class ListApplicationStuController extends HttpServlet {

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
            out.println("<title>Servlet ListApplicationStuController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListApplicationStuController at " + request.getContextPath() + "</h1>");
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
        ApplicationDAO ad = new ApplicationDAO();
        ResultMessage result = null;
        String errorMessage = null;

        try {
            String processId = request.getParameter("processId");
            String action = request.getParameter("action");

            if (processId != null && !processId.isEmpty()) {
                try {
                    int id = Integer.parseInt(processId);
                    if ("approve".equalsIgnoreCase(action)) {
                        result = ad.processApplicationStu(id);
                    } else if ("reject".equalsIgnoreCase(action)) {
                        result = ad.processApplicationStu2(id);
                    } else {
                        errorMessage = "Hành động không hợp lệ.";
                        request.getSession().setAttribute("message", errorMessage);
                        request.getSession().setAttribute("success", false);
                        response.sendRedirect("listapplicationStu");
                        return;
                    }
                    request.getSession().setAttribute("message", result.getMessage());
                    request.getSession().setAttribute("success", result.isSuccess());
                    response.sendRedirect("listapplicationStu");
                    return;
                } catch (NumberFormatException e) {
                    errorMessage = "ID đơn không hợp lệ.";
                    request.getSession().setAttribute("message", errorMessage);
                    request.getSession().setAttribute("success", false);
                    response.sendRedirect("listapplicationStu");
                    return;
                }
            }

            // Lấy tham số tìm kiếm và lọc
            String nameSearch = request.getParameter("nameSearch");
            String typeFilter = request.getParameter("typeFilter");
            ArrayList<Application> data = null;

            // Xử lý tìm kiếm và lọc
            if (nameSearch != null && !nameSearch.trim().isEmpty()) {
                data = ad.getListApplicationStuByName(nameSearch);
            } else if (typeFilter != null && !typeFilter.equals("0")) {
                try {
                    int typeId = Integer.parseInt(typeFilter);
                    data = ad.getListApplicationStuByType(typeId);
                } catch (NumberFormatException e) {
                    errorMessage = "ID kiểu đơn không hợp lệ.";
                    request.getSession().setAttribute("message", errorMessage);
                    request.getSession().setAttribute("success", false);
                    response.sendRedirect("listapplicationStu");
                    return;
                }
            } else {
                data = ad.getListApplicationStu();
            }

            
            ArrayList<TeacherApplicationType> data1 = ad.getStudentApplicationTypeList();
            request.setAttribute("data", data);
            request.setAttribute("data1", data1);

        } catch (Exception e) {
            errorMessage = "Lỗi khi xử lý yêu cầu: " + e.getMessage();
            request.getSession().setAttribute("message", errorMessage);
            request.getSession().setAttribute("success", false);
            response.sendRedirect("listapplicationStu");
            return;
        }

        request.getRequestDispatcher("ListApplicationStu.jsp").forward(request, response);
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
