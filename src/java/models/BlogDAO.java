/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dwight
 */
//Dương_Homepage
import dal.DBContext;
import java.sql.*;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class BlogDAO extends DBContext {
    public List<Blog> getLatest3Blogs() {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT TOP 3 * FROM blog ORDER BY dateBlog DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("name");
                String content = rs.getString("content");
                String publishDate = rs.getString("dateBlog"); 
                byte[] picture = rs.getBytes("img");

                Blog blog = new Blog(id, title, content, publishDate, picture);
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println("getLatest3Blogs: " + e.getMessage());
        }

        return list;
    }
    public List<Blog> getLatestBlogs() {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT * FROM blog ORDER BY dateBlog DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("name");
                String content = rs.getString("content");
                String publishDate = rs.getString("dateBlog"); 
                byte[] picture = rs.getBytes("img");

                Blog blog = new Blog(id, title, content, publishDate, picture);
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println("getLatest3Blogs: " + e.getMessage());
        }

        return list;
    }
    public byte[] getBlogImageById(int id) {
        String sql = "SELECT img FROM blog WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBytes("img");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Blog getBlogById(int id) {
        String sql = "SELECT * FROM blog WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String title = rs.getString("name");
                String content = rs.getString("content");
                String publishDate = rs.getString("dateBlog");
                byte[] picture = rs.getBytes("img");
                return new Blog(id, title, content, publishDate, picture);
            }
        } catch (Exception e) {
            System.out.println("getBlogById: " + e.getMessage());
        }
        return null;
    }
     public void addBlog(Blog blog) {
        String sql = "INSERT INTO blog (name, content, img, dateBlog) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, blog.getTitle());
            ps.setString(2, blog.getContent());
            ps.setBytes(3, blog.getImg());
            ps.setString(4, blog.getPublishDate());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("addBlog: " + e.getMessage());
        }
    }

    public void updateBlog(Blog blog) {
        String sql = "UPDATE blog SET name = ?, content = ?, img = ?, dateBlog = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, blog.getTitle());
            ps.setString(2, blog.getContent());
            ps.setBytes(3, blog.getImg());
            ps.setString(4, blog.getPublishDate());
            ps.setInt(5, blog.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateBlog: " + e.getMessage());
        }
    }

    public void deleteBlog(int id) {
        String sql = "DELETE FROM blog WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteBlog: " + e.getMessage());
        }
    }

    // Added methods to support ProductController-style functionality
    public ArrayList<Blog> getBlogs() {
        ArrayList<Blog> list = new ArrayList<>();
        String sql = "SELECT * FROM blog ORDER BY dateBlog DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("name");
                String content = rs.getString("content");
                String publishDate = rs.getString("dateBlog");
                byte[] picture = rs.getBytes("img");
                Blog blog = new Blog(id, title, content, publishDate, picture);
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println("getBlogs: " + e.getMessage());
        }
        return list;
    }

    public ArrayList<Blog> getBlogByTitleAndDate(String keyword, String fromDate, String toDate) {
    ArrayList<Blog> allBlogs = getBlogs(); // lấy tất cả blog
    ArrayList<Blog> filtered = new ArrayList<>();

    String search = removeAccents(keyword).toLowerCase();

    for (Blog blog : allBlogs) {
        String blogTitle = removeAccents(blog.getTitle()).toLowerCase();
        String blogDate = blog.getPublishDate();

        boolean matchTitle = blogTitle.contains(search);

        boolean matchDate = true;
        if (fromDate != null && !fromDate.isEmpty()) {
            matchDate &= blogDate.compareTo(fromDate) >= 0;
        }
        if (toDate != null && !toDate.isEmpty()) {
            matchDate &= blogDate.compareTo(toDate) <= 0;
        }

        if (matchTitle && matchDate) {
            filtered.add(blog);
        }
    }

    return filtered;
}
    public static String removeAccents(String input) {
    if (input == null) return "";
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
}
}