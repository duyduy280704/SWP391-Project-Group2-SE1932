package models;

import java.sql.*;
import java.util.*;
import models.*;
import dal.DBContext;
// Thủy _ in tất cả các lớp
public class ClassStudentDAO extends DBContext {

    // Lấy danh sách tất cả lớp
    public List<Courses> getAllClasses() {
        List<Courses> list = new ArrayList<>();
        String sql = "SELECT id, name FROM Class";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Courses(rs.getString("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // sinh viên trong 1 lớp 
    public List<Students> getStudentsByClassId(String classId) {
        List<Students> list = new ArrayList<>();
        String sql = """
            SELECT s.id, s.full_name, s.email, s.password, s.birth_date, s.gender, s.address
            FROM Student s
            JOIN Class_Student cs ON s.id = cs.student_id
            WHERE cs.class_id = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Students s = new Students();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("full_name"));
                s.setEmail(rs.getString("email"));
                s.setPassword(rs.getString("password"));
                s.setBrithdate(rs.getString("birth_date"));
                s.setGender(rs.getString("gender"));
                s.setAddress(rs.getString("address"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//lấy tên lớp học vì kia đang in id 
    public String getClassNameById(String classId) {
        String sql = "SELECT name FROM Class WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    // tìm kiếm tên lớp
public List<Courses> searchClassesByName(String keyword) {
    List<Courses> list = new ArrayList<>();
    String sql = "SELECT id, name FROM Class WHERE name LIKE ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Courses(rs.getString("id"), rs.getString("name")));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
//tìm tên học sinh 
public List<Students> searchStudentName(String classId, String keyword) {
    List<Students> list = new ArrayList<>();
    String sql = """
        SELECT s.id, s.full_name, s.email, s.password, s.birth_date, s.gender, s.address
        FROM Student s
        JOIN Class_Student cs ON s.id = cs.student_id
        WHERE cs.class_id = ? AND s.full_name LIKE ?
    """;
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, classId);
        ps.setString(2, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Students s = new Students();
            s.setId(rs.getString("id"));
            s.setName(rs.getString("full_name"));
            s.setEmail(rs.getString("email"));
            s.setPassword(rs.getString("password"));
            s.setBrithdate(rs.getString("birth_date"));
            s.setGender(rs.getString("gender"));
            s.setAddress(rs.getString("address"));
            list.add(s);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}


}