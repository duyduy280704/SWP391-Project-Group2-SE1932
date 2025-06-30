package Controllers;

import models.Blog;
import models.BlogDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;


@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB max file size
public class BlogController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BlogController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        BlogDAO blogDAO = new BlogDAO();

        String mode = request.getParameter("mode");
        if (mode != null && mode.equals("image")) {
            // Serve image
            int id = Integer.parseInt(request.getParameter("id"));
            Blog blog = blogDAO.getBlogById(id);
            if (blog != null && blog.getImg() != null) {
                response.setContentType("image/jpeg");
                response.setContentLength(blog.getImg().length);
                try (OutputStream out = response.getOutputStream()) {
                    out.write(blog.getImg());
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            return;
        }

        if (mode != null && mode.equals("1")) {
            // Edit mode: Get blog by ID
            int id = Integer.parseInt(request.getParameter("id"));
            Blog blog = blogDAO.getBlogById(id);
            request.setAttribute("blog", blog);
        }
        if (mode != null && mode.equals("2")) {
            // Delete mode
            int id = Integer.parseInt(request.getParameter("id"));
            blogDAO.deleteBlog(id);
        }

        ArrayList<Blog> data = blogDAO.getBlogs();
        request.setAttribute("data", data);
        request.getRequestDispatcher("blog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    BlogDAO blogDAO = new BlogDAO();
    Blog blog = null;

    // 👉 Kiểm tra nếu request là từ form upload (multipart/form-data)
    String contentType = request.getContentType();
    if (contentType != null && contentType.toLowerCase().startsWith("multipart/")) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String publishDate = request.getParameter("publishDate");
        Part filePart = request.getPart("img");

        byte[] img = null;
        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                img = inputStream.readAllBytes();
            }
        }

        if (request.getParameter("add") != null) {
            blog = new Blog(0, title, content, publishDate, img);
            blogDAO.addBlog(blog);
        }

        if (request.getParameter("update") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (img == null) {
                Blog existingBlog = blogDAO.getBlogById(id);
                img = existingBlog.getImg();
            }
            blog = new Blog(id, title, content, publishDate, img);
            blogDAO.updateBlog(blog);
        }

    } else if (request.getParameter("search") != null) {
    String title = request.getParameter("title");
    String fromDate = request.getParameter("fromDate");
    String toDate = request.getParameter("toDate");

    ArrayList<Blog> data = blogDAO.getBlogByTitleAndDate(title, fromDate, toDate);
    request.setAttribute("data", data);
    request.setAttribute("search", "true");
    request.setAttribute("searchTitle", title);
    request.setAttribute("fromDate", fromDate);
    request.setAttribute("toDate", toDate);
    request.getRequestDispatcher("blog.jsp").forward(request, response);
    return;
}

    // 👉 Load lại danh sách bài viết sau khi thêm hoặc cập nhật
    ArrayList<Blog> data = blogDAO.getBlogs();
    request.setAttribute("data", data);
    request.setAttribute("blog", blog);
    request.getRequestDispatcher("blog.jsp").forward(request, response);
}
    @Override
    public String getServletInfo() {
        return "Blog Controller for managing blog entries and serving images";
    }
}