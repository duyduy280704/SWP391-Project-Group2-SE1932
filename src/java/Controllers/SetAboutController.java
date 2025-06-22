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
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ResultMessage;
import models.SetAbout;
import models.SetBanner;
import models.Setting;
import models.SettingDAO;

/**
 *
 * @author Quang
 * Quản lý setting thông tin trung tâm - about (crud)
 */
@MultipartConfig
public class SetAboutController extends HttpServlet {

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
            out.println("<title>Servlet SetAboutController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SetAboutController at " + request.getContextPath() + "</h1>");
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
        SettingDAO sd = new SettingDAO();

        String aboutId = request.getParameter("id");

        if (request.getParameter("mode") != null && request.getParameter("mode").equals("1")) {
            //tìm Product tương ứng cùng với code truyền sang
            
            SetAbout p = sd.getSetAboutById(aboutId);
            request.setAttribute("p", p);
        }
        
        if (request.getParameter("mode") != null && request.getParameter("mode").equals("2")) {

            if (aboutId != null && !aboutId.isEmpty()) {
                sd.deleteAbout(aboutId);
                request.setAttribute("message", "Xóa  thành công!");
            } else {
                request.setAttribute("error", "ID không hợp lệ!");
            }
        }
        
        
        

        Setting data = sd.getSetting();
        ArrayList<SetAbout> dataAbout = sd.getSetAbout();
        ArrayList<SetBanner> dataBanner = sd.getSetBanner();

        request.setAttribute("data", data);
        request.setAttribute("dataAbout", dataAbout);
        request.setAttribute("dataBanner", dataBanner);
        request.getRequestDispatcher("setting.jsp").forward(request, response);
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
        String tieude1 = request.getParameter("tieude1");
        String description = request.getParameter("description");

        // Handle file upload
        Part filePart = request.getPart("anh1");
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
        ResultMessage result = null;
        SettingDAO sd = new SettingDAO();

        SetAbout p = new SetAbout(id, tieude1, description, imageBytes);

        try {

            if (request.getParameter("update1") != null) {
                 result = sd.updateAbout(p);
            } else if (request.getParameter("add1") != null) {
                result = sd.addAbout(p);
            } else {
                result = new ResultMessage(false, "Hành động không hợp lệ!");
            }
        } catch (NumberFormatException e) {
            result = new ResultMessage(false, "Dữ liệu không hợp lệ: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(SetAboutController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Setting data = sd.getSetting();
        ArrayList<SetAbout> dataAbout = sd.getSetAbout();
        ArrayList<SetBanner> dataBanner = sd.getSetBanner();

        request.setAttribute("data", data);
        request.setAttribute("dataAbout", dataAbout);
        request.setAttribute("dataBanner", dataBanner);
        request.setAttribute("message", result.getMessage());
        request.setAttribute("success", result.isSuccess());

        request.setAttribute("p", p);
        request.getRequestDispatcher("setting.jsp").forward(request, response);
    }

    private void forwardRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SettingDAO sd = new SettingDAO();

        Setting data = sd.getSetting();
        ArrayList<SetAbout> dataAbout = sd.getSetAbout();
        ArrayList<SetBanner> dataBanner = sd.getSetBanner();

        request.setAttribute("data", data);
        request.setAttribute("dataAbout", dataAbout);
        request.setAttribute("dataBanner", dataBanner);
        request.getRequestDispatcher("setting.jsp").forward(request, response);
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
