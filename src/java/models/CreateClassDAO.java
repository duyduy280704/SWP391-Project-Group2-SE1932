package models;

import dal.DBContext;
import java.sql.*;
import java.util.*;

public class CreateClassDAO extends DBContext {
// Thuy_ tao lop mới từ khóa học đã có
    // Lấy tất cả khóa học
    public List<Courses> getAllCourses() {
        List<Courses> list = new ArrayList<>();
        String sql = "SELECT id, name FROM Course";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Courses c = new Courses();
                c.setId(rs.getString("id"));
                c.setName(rs.getString("name"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tạo lớp học mới
    public void createClass(String name, String courseId) {
        String sql = "INSERT INTO Class (name, course_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, courseId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật tên lớp học
    public void updateClassName(String id, String newName) {
        String sql = "UPDATE Class SET name = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setString(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xoá lớp học theo ID
    public void deleteClass(String id) {
        String sql = "DELETE FROM Class WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách tất cả các lớp 
    public List<Categories_class> getAllClasses() {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT c.id, c.name, co.name AS course_name " +
                     "FROM Class c JOIN Course co ON c.course_id = co.id " +
                     "ORDER BY c.id ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categories_class cl = new Categories_class();
                cl.setId_class(rs.getString("id"));
                cl.setName_class(rs.getString("name"));
                cl.setCourse_name(rs.getString("course_name"));
                list.add(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    // timg kiếm theo tên 
    public List<Categories_class> searchClasses(String keyword) {
    List<Categories_class> list = new ArrayList<>();
    String sql = "SELECT c.id, c.name, co.name AS course_name " +
                 "FROM Class c JOIN Course co ON c.course_id = co.id " +
                 "WHERE c.name LIKE ? OR co.name LIKE ? " +
                 "ORDER BY c.id ASC";

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        String searchKey = "%" + keyword + "%";
        ps.setString(1, searchKey);
        ps.setString(2, searchKey);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Categories_class cl = new Categories_class();
                cl.setId_class(rs.getString("id"));
                cl.setName_class(rs.getString("name"));
                cl.setCourse_name(rs.getString("course_name"));
                list.add(cl);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}