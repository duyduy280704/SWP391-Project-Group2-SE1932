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
        String sql = "INSERT INTO PreRegistrations (full_name, email, phone, birth_date, gender, address, course_id, status, note, register_date, id_sale) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, preReg.getFull_name());
            ps.setString(2, preReg.getEmail());
            ps.setString(3, preReg.getPhone());
            ps.setString(4, preReg.getBirth_date());
            ps.setString(5, preReg.getGender());
            ps.setString(6, preReg.getAddress());
            ps.setInt(7, preReg.getCourse_id());
            ps.setString(8, preReg.getStatus());
            ps.setString(9, preReg.getNote());
            ps.setInt(10, preReg.getId_sale());

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
                             pr.status, 
                             pr.note 
                         FROM PreRegistrations pr 
                         JOIN Course c ON pr.course_id = c.id 
                         ORDER BY 
                             CASE 
                                 WHEN LOWER(TRIM(pr.status)) = N'đang chờ' THEN 0 
                                 WHEN LOWER(TRIM(pr.status)) = N'Chưa xếp được lớp' THEN 1 
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
                pr.setNote(rs.getString("note"));
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

    public void updateNote(int id, String note) {
        String sql = "UPDATE PreRegistrations SET note = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, note);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateNote: " + e.getMessage());
        }
    }

    public boolean isEmailOrPhoneExists(String email, String phone) {
        String sql = "SELECT 1 FROM PreRegistrations WHERE email = ? OR phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // chỉ cần có 1 dòng trùng là trả về true
            }
        } catch (Exception e) {
            System.out.println("isEmailOrPhoneExists: " + e.getMessage());
        }
        return false;
    }

    public List<PreRegistration> filterPreRegistrations(String keyword, String courseName, String status) {
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
            pr.status, 
            pr.note 
        FROM PreRegistrations pr 
        JOIN Course c ON pr.course_id = c.id 
        WHERE 
            (? IS NULL OR pr.full_name LIKE ?) 
            AND (? IS NULL OR c.name = ?) 
            AND (? IS NULL OR pr.status = ?)
        ORDER BY 
            CASE 
                WHEN LOWER(LTRIM(RTRIM(pr.status))) = N'đang chờ' THEN 0 
                WHEN LOWER(LTRIM(RTRIM(pr.status))) = N'chưa xếp được lớp' THEN 1 
                ELSE 2 
            END, 
            pr.status;
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // full_name filter
            ps.setString(1, isEmpty(keyword) ? null : keyword);
            ps.setString(2, isEmpty(keyword) ? null : "%" + keyword + "%");

            // course_name filter
            ps.setString(3, isEmpty(courseName) ? null : courseName);
            ps.setString(4, isEmpty(courseName) ? null : courseName);

            // status filter
            ps.setString(5, isEmpty(status) ? null : status);
            ps.setString(6, isEmpty(status) ? null : status);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PreRegistration pr = new PreRegistration();
                    pr.setId(rs.getInt("id"));
                    pr.setFull_name(rs.getString("full_name"));
                    pr.setEmail(rs.getString("email"));
                    pr.setPhone(rs.getString("phone"));
                    pr.setBirth_date(rs.getString("birth_date"));
                    pr.setGender(rs.getString("gender"));
                    pr.setAddress(rs.getString("address"));
                    pr.setCourseName(rs.getString("course_name"));
                    pr.setStatus(rs.getString("status"));
                    pr.setNote(rs.getString("note"));
                    list.add(pr);
                }
            }
        } catch (Exception e) {
            System.out.println("filterPreRegistrations: " + e.getMessage());
        }

        return list;
    }

    private boolean isEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }

    public PreRegistration getPreRegistrationById(int id) {
        String sql = "SELECT * FROM PreRegistrations WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PreRegistration p = new PreRegistration();
                    p.setId(rs.getInt("id"));
                    p.setFull_name(rs.getString("full_name"));
                    p.setEmail(rs.getString("email"));
                    p.setPhone(rs.getString("phone"));
                    p.setBirth_date(rs.getString("birth_date"));
                    p.setGender(rs.getString("gender"));
                    p.setAddress(rs.getString("address"));
                    return p;
                }
            }
        } catch (Exception e) {
            System.out.println("getPreRegistrationById: " + e.getMessage());
        }
        return null;
    }

}
