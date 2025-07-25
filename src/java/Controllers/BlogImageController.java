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
import java.io.OutputStream;
import models.BlogDAO;

/**
 *
 * @author Dương
 */
public class BlogImageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        String id = request.getParameter("id");
        try {
            BlogDAO dao = new BlogDAO();
            byte[] imageData = dao.getBlogImageById(Integer.parseInt(id));
            
            if (imageData != null) {
                response.setContentType("image/jpeg"); 
                OutputStream os = response.getOutputStream();
                os.write(imageData);
                os.flush();
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        }
    }
}