/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dương
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

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                byte[] image = rs.getBytes("image");

                About about = new About(id, title, content, image);
                list.add(about);
            }
        } catch (Exception e) {
            System.out.println("getAllAbouts: " + e.getMessage());
        }

        return list;
    }

    public byte[] getAboutImageById(int id) {
        String sql = "SELECT image FROM about WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("image");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
