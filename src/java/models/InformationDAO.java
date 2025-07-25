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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InformationDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public Information getSetting() {
        Information setting = null;
        try {
            String sql = "SELECT * FROM setting";
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            if (rs.next()) {
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String facebookLink = rs.getString("facebookLink");
                String instagramLink = rs.getString("instagramLink");
                String youtubeLink = rs.getString("youtubeLink");
                String about = rs.getString("about");
                String copyright = rs.getString("copyright");
                String policyLink = rs.getString("policyLink");
                String termsLink = rs.getString("termsLink");

                setting = new Information(address, phone, email,
                        facebookLink, instagramLink, youtubeLink,
                        copyright, policyLink, termsLink, about);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return setting;
    }

    public ArrayList<Banner> getSlides() {
    ArrayList<Banner> slides = new ArrayList<>();
    try {
        String sql = "SELECT id, title, image FROM carousel";
        stm = connection.prepareStatement(sql);
        rs = stm.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title"); // hoặc getNString nếu dùng Unicode
            byte[] imageData = rs.getBytes("image"); // Lấy ảnh dưới dạng byte[]

            Banner slide = new Banner(id, title, imageData);
            slides.add(slide);
        }
    } catch (Exception e) {
        e.printStackTrace(); // In lỗi ra console
    }
    return slides;
}

    public byte[] getBannerImageById(int id) {
    String sql = "SELECT image FROM carousel WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getBytes("image");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

}
