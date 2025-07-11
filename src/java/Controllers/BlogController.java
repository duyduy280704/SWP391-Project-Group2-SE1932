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


@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // 5MB
public class BlogController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BlogDAO blogDAO = new BlogDAO();
        String mode = request.getParameter("mode");

        if ("image".equals(mode)) {
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

        if ("1".equals(mode)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Blog blog = blogDAO.getBlogById(id);
            request.setAttribute("blog", blog);
        }

        if ("2".equals(mode)) {
            int id = Integer.parseInt(request.getParameter("id"));
            blogDAO.deleteBlog(id);
            request.setAttribute("message", "Xóa bài viết thành công!");
        }

        ArrayList<Blog> data = blogDAO.getBlogs();
        request.setAttribute("data", data);
        request.getRequestDispatcher("blogstaff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        BlogDAO blogDAO = new BlogDAO();
        Blog blog = null;

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

            String message = "";

            // Validate input
            if (title == null || title.trim().isEmpty()) {
                message += "Tiêu đề không được để trống.<br>";
            } else if (title.length() > 200) {
                message += "Tiêu đề phải dưới 200 ký tự.<br>";
            }

            if (content == null || content.trim().isEmpty()) {
                message += "Nội dung không được để trống.<br>";
            }

            if (publishDate == null || publishDate.trim().isEmpty()) {
                message += "Ngày đăng không được để trống.<br>";
            } else if (!publishDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                message += "Ngày đăng phải theo định dạng YYYY-MM-DD.<br>";
            }

            if (filePart != null && filePart.getSize() > 0) {
                String fileType = filePart.getContentType();
                if (!fileType.startsWith("image/")) {
                    message += "Chỉ cho phép tải lên tệp hình ảnh.<br>";
                }
                if (filePart.getSize() > 1024 * 1024 * 5) {
                    message += "Kích thước ảnh phải nhỏ hơn 5MB.<br>";
                }
            }

            // Nếu có lỗi, trả lại form
            if (!message.isEmpty()) {
                request.setAttribute("message", message);
                request.setAttribute("blogAdd", new Blog(0, title, content, publishDate, img));
                ArrayList<Blog> data = blogDAO.getBlogs();
                request.setAttribute("data", data);
                request.getRequestDispatcher("blogstaff.jsp").forward(request, response);
                return;
            }

            // Xử lý thêm mới
            if ("true".equals(request.getParameter("add"))) {
                blog = new Blog(0, title, content, publishDate, img);
                blogDAO.addBlog(blog);
                request.setAttribute("message", "Thêm bài viết thành công!");
            }

            // Xử lý cập nhật
            else if ("true".equals(request.getParameter("update"))) {
                int id = Integer.parseInt(request.getParameter("id"));

                // Lấy ảnh cũ nếu không có ảnh mới
                if (img == null) {
                    Blog existingBlog = blogDAO.getBlogById(id);
                    img = existingBlog.getImg();
                }

                blog = new Blog(id, title, content, publishDate, img);
                blogDAO.updateBlog(blog);
                request.setAttribute("message", "Cập nhật bài viết thành công!");
            }

        }

        // Xử lý tìm kiếm
        else if (request.getParameter("search") != null) {
            String title = request.getParameter("title");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");

            ArrayList<Blog> data = blogDAO.getBlogByTitleAndDate(title, fromDate, toDate);
            if (data == null || data.isEmpty()) {
                request.setAttribute("message", "Không tìm thấy bài viết nào phù hợp.");
            }

            request.setAttribute("data", data);
            request.setAttribute("search", "true");
            request.setAttribute("searchTitle", title);
            request.setAttribute("fromDate", fromDate);
            request.setAttribute("toDate", toDate);
            request.getRequestDispatcher("blogstaff.jsp").forward(request, response);
            return;
        }

        ArrayList<Blog> data = blogDAO.getBlogs();
        request.setAttribute("data", data);
        request.setAttribute("blog", blog); // Chỉ dùng cho form chỉnh sửa
        request.getRequestDispatcher("blogstaff.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Blog Controller for managing blog entries and serving images";
    }
}
