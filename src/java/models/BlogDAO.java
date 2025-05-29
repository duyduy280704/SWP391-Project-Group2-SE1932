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
                String picture = rs.getString("img");

                Blog blog = new Blog(id, title, content, publishDate, picture);
                list.add(blog);
            }
        } catch (Exception e) {
            System.out.println("getLatest3Blogs: " + e.getMessage());
        }

        return list;
    }
}

