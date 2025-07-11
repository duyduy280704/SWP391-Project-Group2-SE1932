/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.OutputStream;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Blog;
import models.BlogDAO;

/**
 *
 * @author Dwight
 */
public class BlogImageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String action = request.getParameter("action");

        try {
            BlogDAO dao = new BlogDAO();
            if ("detail".equals(action)) {
                // Xử lý yêu cầu chi tiết blog
                Blog blog = dao.getBlogById(Integer.parseInt(id));
                if (blog != null) {
                    request.setAttribute("blog", blog);
                    request.getRequestDispatcher("blog.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Blog not found");
                }
            } else {
                // Xử lý yêu cầu hình ảnh (hành vi hiện tại)
                byte[] imageData = dao.getBlogImageById(Integer.parseInt(id));
                if (imageData != null) {
                    response.setContentType("image/jpeg"); // hoặc image/png tùy kiểu ảnh
                    OutputStream os = response.getOutputStream();
                    os.write(imageData);
                    os.flush();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
                }
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}