/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Courses;
import models.Event;
import models.EventDAO;
import models.ResultMessage;

/**
 *
 * @author Quang
 */
@MultipartConfig
public class EventStaffController extends HttpServlet {

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
            out.println("<title>Servlet EventStaffController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EventStaffController at " + request.getContextPath() + "</h1>");
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
        EventDAO ed = new EventDAO();

        if (request.getParameter("mode") != null && request.getParameter("mode").equals("1")) {

            String id = request.getParameter("id");
            Event p = ed.getEventById(id);
            request.setAttribute("p", p);
        }

        if (request.getParameter("mode") != null && request.getParameter("mode").equals("2")) {

            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                ed.deleteEvent(id);
                request.setAttribute("message", "Xóa sự kiện thành công!");
            } else {
                request.setAttribute("error", "ID không hợp lệ!");
            }
        }

        ArrayList<Event> data = ed.getEvents();
        ArrayList<Courses> data1 = ed.getCourse();

        request.setAttribute("data", data);
        request.setAttribute("data1", data1);
        request.getRequestDispatcher("eventStaff.jsp").forward(request, response);
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
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String content = request.getParameter("content");
        String date = request.getParameter("date");
        String course = request.getParameter("course");
        String nameSearch = request.getParameter("nameSearch");
        String genderFilter = request.getParameter("genderFilter");

        // Handle file upload
        Part filePart = request.getPart("image");
        byte[] imageBytes = null;
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
                request.setAttribute("message", "Chỉ hỗ trợ file JPG hoặc PNG!");
                request.setAttribute("success", false);
                forwardRequest(request, response);
                return;
            }
            if (filePart.getSize() > 5 * 1024 * 1024) { // 5MB limit
                request.setAttribute("message", "File quá lớn! Tối đa 5MB.");
                request.setAttribute("success", false);
                forwardRequest(request, response);
                return;
            }
            InputStream fileContent = filePart.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = fileContent.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            imageBytes = buffer.toByteArray();
        }

        Event p = new Event(id, name, content, date, course, imageBytes);

        EventDAO ed = new EventDAO();
        ResultMessage result = null;

        try {
            if (request.getParameter("update") != null) {
                result = ed.updateEvent(p);
            } else if (request.getParameter("add") != null) {
                result = ed.addEvent(p);
            } else {
                result = new ResultMessage(true, "Đã tìm sự kiện");
            }
        } catch (NumberFormatException e) {
            result = new ResultMessage(false, "Dữ liệu không hợp lệ: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(EventStaffController.class.getName()).log(Level.SEVERE, null, ex);
            result = new ResultMessage(false, "Lỗi SQL: " + ex.getMessage());
        }

        ArrayList<Event> data;
        ArrayList<Courses> data1 = ed.getCourse();
        if (request.getParameter("search") != null && nameSearch != null && !nameSearch.trim().isEmpty()) {
            data = ed.getEventByName(nameSearch);
            request.setAttribute("nameSearch", nameSearch);
        } else if (request.getParameter("filterGender") != null && genderFilter != null && !genderFilter.trim().isEmpty()) {
            data = ed.getEventsByCourse(genderFilter);
            request.setAttribute("genderFilter", genderFilter);
        } else {
            data = ed.getEvents();
            
        }
        request.setAttribute("data", data);
        request.setAttribute("data1", data1);
        

        request.setAttribute("message", result.getMessage());
        request.setAttribute("success", result.isSuccess());
        
        request.setAttribute("p", p);
        request.getRequestDispatcher("eventStaff.jsp").forward(request, response);

    }

    private void forwardRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EventDAO ed = new EventDAO();

        ArrayList<Event> data = ed.getEvents();
        ArrayList<Courses> data1 = ed.getCourse();

        request.setAttribute("data", data);
        request.setAttribute("data1", data1);

        request.getRequestDispatcher("eventStaff.jsp").forward(request, response);
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