/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Quang
 */
public class SettingDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public Setting getSetting() {

        Setting p = null;
        try {
            String strSQL = "select * from setting ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String address = rs.getString(2);
                String phone = rs.getString(3);
                String email = rs.getString(4);
                String facebook = rs.getString(5);
                String instagram = rs.getString(6);
                String youtube = rs.getString(7);
                String about = rs.getString(8);
                String copyright = rs.getString(9);
                String policy = rs.getString(10);
                String terms = rs.getString(11);

                p = new Setting(address, phone, email, facebook, instagram, youtube, about, copyright, policy, terms);

            }
        } catch (Exception e) {
            System.out.println("getStudents" + e.getMessage());

        }
        return p;
    }

    public ResultMessage update(Setting s) {

        if (s == null) {
            return new ResultMessage(false, "Dữ liệu học sinh không hợp lệ.");
        }

        if (s.address == null || s.address.trim().isEmpty()) {
            return new ResultMessage(false, "Địa chỉ không được để trống.");
        }
        if (s.email == null || s.email.trim().isEmpty()) {
            return new ResultMessage(false, "Email không được để trống.");
        }
        if (s.phone == null || s.phone.trim().isEmpty()) {
            return new ResultMessage(false, "Số điện thoại không được để trống.");
        }

        
        String phonePattern = "^\\+?\\d+$";
        if (!s.phone.trim().matches(phonePattern)) {
            return new ResultMessage(false, "Số điện thoại chỉ được chứa các chữ số hoặc bắt đầu bằng dấu + theo sau là các chữ số.");
        }

        int Id = 1;
        try (PreparedStatement stm = connection.prepareStatement(
                " UPDATE setting SET address = ?, phone = ?, email = ?, facebookLink = ?, instagramLink = ?, youtubeLink = ?, about = ?, copyright = ?, policyLink = ?, termsLink = ? WHERE id = ? ")) {

            stm.setString(1, s.address);
            stm.setString(2, s.phone);
            stm.setString(3, s.email);
            stm.setString(4, s.facebook);
            stm.setString(5, s.instagram);
            stm.setString(6, s.youtube);
            stm.setString(7, s.about);
            stm.setString(8, s.copyright);
            stm.setString(9, s.policy);
            stm.setString(10, s.terms);
            stm.setInt(11, Id);

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Cập nhật cài đặt thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy ");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }
}
