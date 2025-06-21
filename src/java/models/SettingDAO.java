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
            return new ResultMessage(false, "Dữ liệu không hợp lệ.");
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

        if (!s.phone.matches("^\\+?[0-9]{10,11}$")) {
            return new ResultMessage(false, "Định dạng số điện thoại không hợp lệ: " + s.phone);
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

    public ArrayList<SetAbout> getSetAbout() {

        ArrayList<SetAbout> data = new ArrayList<>();
        try {
            String strSQL = "  select * from about ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String title = rs.getString(2);
                String content = rs.getString(3);
                byte[] image = rs.getBytes(4);

                SetAbout p = new SetAbout(id, title, content, image);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getStudents" + e.getMessage());

        }
        return data;
    }

    public SetAbout getSetAboutById(String id) {
        try {
            String strSQL = "select * from about where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                String title = rs.getString(2);
                String content = rs.getString(3);
                byte[] image = rs.getBytes(4);

                SetAbout p = new SetAbout(id, title, content, image);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getCoursesById" + e.getMessage());

        }
        return null;
    }
    
    public ResultMessage addAbout(SetAbout about) throws SQLException {
        if (about == null || about.getTitle() == null || about.getTitle().trim().isEmpty()) {
            return new ResultMessage(false, "Tiêu đề không được để trống.");
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO about (title, content, image) VALUES (?, ?, ?)")) {
            stm.setString(1, about.getTitle());
            stm.setString(2, about.getContent());
            stm.setBytes(3, about.getImage());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Thêm thông tin về trung tâm thành công!");
            } else {
                return new ResultMessage(false, "Không thể thêm thông tin về trung tâm.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong addAbout: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
    }

    public ResultMessage updateAbout(SetAbout about) throws SQLException {
        if (about == null || about.getTitle() == null || about.getTitle().trim().isEmpty()) {
            return new ResultMessage(false, "Tiêu đề không được để trống.");
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE about SET title = ?, content = ?, image = ? WHERE id = ?")) {
            stm.setString(1, about.getTitle());
            stm.setString(2, about.getContent());
            stm.setBytes(3, about.getImage());
            stm.setString(4, about.getId());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Thêm thông tin về trung tâm thành công!");
            } else {
                return new ResultMessage(false, "Không thể thêm thông tin về trung tâm.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong addAbout: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
    }
    
    public ResultMessage deleteAbout(String id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM about WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Xóa  thành công!" : "Không tìm thấy khóa học với ID: " + id);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong delete: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID không hợp lệ: " + id);
        }
    }

    public byte[] getAboutImageById(String aboutId) throws SQLException {
        if (aboutId == null || aboutId.isEmpty()) {
            return null;
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT image FROM about WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(aboutId));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getBytes("image");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong getAboutIdImageById: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("Invalid aboutId : " + aboutId);
            throw e;
        }
        return null;
    }

    public ArrayList<SetBanner> getSetBanner() {

        ArrayList<SetBanner> data = new ArrayList<>();
        try {
            String strSQL = "  select * from carousel ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String title = rs.getString(2);
                byte[] image = rs.getBytes(3);

                SetBanner p = new SetBanner(id, title, image);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getStudents" + e.getMessage());

        }
        return data;
    }
    
    public SetBanner getSetBannerById(String id) {
        try {
            String strSQL = "select * from carousel where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                String title = rs.getString(2);
                byte[] image = rs.getBytes(3);

                SetBanner p = new SetBanner(id, title, image);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getCoursesById" + e.getMessage());

        }
        return null;
    }
    
    public ResultMessage addBanner(SetBanner banner) throws SQLException {
        if (banner == null || banner.getTitle() == null || banner.getTitle().trim().isEmpty()) {
            return new ResultMessage(false, "Tiêu đề không được để trống.");
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO carousel (title, image) VALUES (?, ?)")) {
            stm.setString(1, banner.getTitle());
            stm.setBytes(2, banner.getImage());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Thêm thông tin về trung tâm thành công!");
            } else {
                return new ResultMessage(false, "Không thể thêm thông tin về trung tâm.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong addAbout: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
    }

    public ResultMessage updateBanner(SetBanner banner) throws SQLException {
        if (banner == null || banner.getTitle() == null || banner.getTitle().trim().isEmpty()) {
            return new ResultMessage(false, "Tiêu đề không được để trống.");
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE carousel SET title = ?, image = ? WHERE id = ?")) {
            stm.setString(1, banner.getTitle());
            stm.setBytes(2, banner.getImage());
            stm.setString(3, banner.getId());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Thêm thông tin về trung tâm thành công!");
            } else {
                return new ResultMessage(false, "Không thể thêm thông tin về trung tâm.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong addAbout: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
    }
    
    public ResultMessage deleteBanner(String id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM carousel WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Xóa  thành công!" : "Không tìm thấy khóa học với ID: " + id);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong delete: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID không hợp lệ: " + id);
        }
    }

    public byte[] getBannerImageById(String bannerId) throws SQLException {
        if (bannerId == null || bannerId.isEmpty()) {
            return null;
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT image FROM carousel WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(bannerId));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getBytes("image");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong getBannerImageById: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("Invalid bannerId : " + bannerId);
            throw e;
        }
        return null;
    }
}
