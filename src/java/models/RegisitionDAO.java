/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dwight
 */
public class RegisitionDAO extends DBContext {

        // Lọc danh sách đăng ký theo khóa học, trạng thái, tên học viên
    public List<Regisition> filterRegisitions(String courseId, String status, String studentName) {
        List<Regisition> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT r.id, r.course_id, s.full_name AS studentName, c.name AS courseName, r.status, r.note "
                + "FROM regisition r "
                + "JOIN Student s ON r.student_id = s.id "
                + "JOIN Course c ON r.course_id = c.id "
                + "WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (courseId != null && !courseId.isEmpty()) {
            sql.append(" AND r.course_id = ?");
            params.add(courseId);
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND r.status = ?");
            params.add(status);
        }

        if (studentName != null && !studentName.trim().isEmpty()) {
            sql.append(" AND s.full_name LIKE ?");
            params.add("%" + studentName.trim() + "%");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Regisition r = new Regisition();
                r.setId(rs.getInt("id"));
                r.setCourseId(rs.getInt("course_id"));
                r.setStudentName(rs.getString("studentName"));
                r.setCourseName(rs.getString("courseName"));
                r.setStatus(rs.getString("status"));
                r.setNote(rs.getString("note"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy toàn bộ khóa học (để lọc)
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

    // Lấy danh sách lớp theo id khóa học
    public List<Categories_class> getClassesByCourse(String courseId) {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT c.id, c.name, tc.name AS courseName "
                   + "FROM Class c JOIN Course tc ON c.course_id = tc.id "
                   + "WHERE c.course_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categories_class cls = new Categories_class();
                cls.setId_class(rs.getString("id"));
                cls.setName_class(rs.getString("name"));
                cls.setCourseName(rs.getString("courseName"));
                list.add(cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phân lớp cho từng học viên
    public void assignToClassSingle(int regisitionId, int classId) {
        String sql = "INSERT INTO Class_Student (id_class, id_student) "
                   + "SELECT ?, student_id FROM regisition WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ps.setInt(2, regisitionId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật trạng thái phân lớp cho từng học viên
    public void updateStatus(int regisitionId, String status) {
        String sql = "UPDATE regisition SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, regisitionId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
