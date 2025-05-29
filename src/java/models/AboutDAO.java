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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AboutDAO extends DBContext {
    public List<About> getAllAbouts() {
        List<About> list = new ArrayList<>();
        String sql = "SELECT * FROM about ORDER BY id ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String image = rs.getString("image");
                String updatedAt = rs.getString("updated_at");

                About about = new About(id, title, content, image, updatedAt);
                list.add(about);
            }
        } catch (Exception e) {
            System.out.println("getAllAbouts: " + e.getMessage());
        }

        return list;
    }
}
