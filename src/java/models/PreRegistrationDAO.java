package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Dwight
 */
//Dương_Homepage
import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.PreRegistration;

public class PreRegistrationDAO extends DBContext {

    public boolean insertPreRegistration(PreRegistration preReg) {
        String sql = "INSERT INTO PreRegistrations (full_name, email, phone, birth_date, gender, address, course_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, preReg.getFull_name());
            ps.setString(2, preReg.getEmail());
            ps.setString(3, preReg.getPhone());
            ps.setString(4, preReg.getBirth_date());
            ps.setString(5, preReg.getGender());
            ps.setString(6, preReg.getAddress());
            ps.setInt(7, preReg.getCourse_id());
            ps.setString(8, preReg.getStatus());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("insertPreRegistration: " + e.getMessage());
            return false;
        }
    }

    public List<PreRegistration> getAllPreRegistrations() {
        List<PreRegistration> list = new ArrayList<>();
        String sql = """
                SELECT 
                             pr.id, 
                             pr.full_name, 
                             pr.email, 
                             pr.phone, 
                             pr.birth_date, 
                             pr.gender, 
                             pr.address, 
                             c.name AS course_name, 
                             pr.status 
                         FROM PreRegistrations pr 
                         JOIN Course c ON pr.course_id = c.id 
                         ORDER BY 
                             CASE 
                                 WHEN LOWER(TRIM(pr.status)) = N'đang chờ' THEN 0 
                                 WHEN LOWER(TRIM(pr.status)) = 'đã duyệt' THEN 1 
                                 ELSE 2 
                             END, 
                             pr.status;	
                 """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PreRegistration pr = new PreRegistration();
                pr.setId(rs.getInt("id"));
                pr.setFull_name(rs.getString("full_name"));
                pr.setEmail(rs.getString("email"));
                pr.setPhone(rs.getString("phone"));
                pr.setBirth_date(rs.getString("birth_date"));
                pr.setGender(rs.getString("gender"));
                pr.setAddress(rs.getString("address"));
                pr.setCourseName(rs.getString("course_name")); // phải có field này trong bean
                pr.setStatus(rs.getString("status"));
                list.add(pr);
            }
        } catch (Exception e) {
            System.out.println("getAllPreRegistrations: " + e.getMessage());
        }

        return list;
    }

    public void updateStatus(int id, String status) {
        String sql = "UPDATE PreRegistrations SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateStatus: " + e.getMessage());
        }
    }

    // Nếu bạn muốn lấy email để gửi mail
    public String getEmailById(int id) {
        String sql = "SELECT email FROM PreRegistrations WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
            }
        } catch (Exception e) {
            System.out.println("getEmailById: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        PreRegistrationDAO dao = new PreRegistrationDAO(); // bạn cần đảm bảo class này có kết nối DB

        List<PreRegistration> list = dao.getAllPreRegistrations();

        for (PreRegistration pr : list) {
            System.out.println("ID: " + pr.getId());
            System.out.println("Họ tên: " + pr.getFull_name());
            System.out.println("Email: " + pr.getEmail());
            System.out.println("Ngày sinh: " + pr.getBirth_date());
            System.out.println("Giới tính: " + pr.getGender());
            System.out.println("Địa chỉ: " + pr.getAddress());
            System.out.println("Khóa học: " + pr.getCourseName());
            System.out.println("Trạng thái: " + pr.getStatus());
            System.out.println("-----------------------------");
        }
    }
}
