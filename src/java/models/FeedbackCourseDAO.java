// xử lí sinh viên điền feedback, staff xem tất cả
package models;

import dal.DBContext;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FeedbackCourseDAO extends DBContext {

    // in danh sách lớp dã học
    public List<Courses> getCoursesByStudentId(String studentId) {
        List<Courses> courses = new ArrayList<>();
        String sql = """
            SELECT DISTINCT 
                c.id AS course_id,
                cl.name AS class_name
            FROM regisition r
            JOIN course c ON r.course_id = c.id
            JOIN class cl ON cl.course_id = c.id
            WHERE r.student_id = ?
        """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, studentId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = rs.getString("course_id");
                String name = rs.getString("class_name");
                courses.add(new Courses(id, name));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getCoursesByStudentId: " + e.getMessage());
        }

        return courses;
    }

    // thêm feedback
    public void addFeedback(String studentId, String courseId, String comment) {
        String sql = "INSERT INTO feedback (id_student, id_course, text, date) VALUES (?, ?, ?, GETDATE())";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, studentId);
            stm.setString(2, courseId);
            stm.setString(3, comment);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm phản hồi vào database: " + e.getMessage());
        }
    }

    // in ra tất cả feedback 
    public List<FeedbackByStudent> getAllFeedback() {
        List<FeedbackByStudent> list = new ArrayList<>();
        String sql = """
            SELECT f.id, f.id_student, s.full_name, f.id_course, cl.name AS class_name, f.text, f.date
            FROM feedback f
            JOIN student s ON f.id_student = s.id
            JOIN course c ON f.id_course = c.id
            JOIN class cl ON c.id = cl.course_id
        """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String studentId = rs.getString("id_student");
                String courseId = rs.getString("id_course");
                String studentName = rs.getString("full_name");
                String className = rs.getString("class_name");
                String text = rs.getString("text");

                // Chuyển Date sang chuỗi định dạng dd/MM/yyyy
                String formattedDate = "";
                Date rawDate = rs.getDate("date");
                if (rawDate != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    formattedDate = sdf.format(rawDate);
                }

                FeedbackByStudent fb = new FeedbackByStudent(id, studentId, courseId, text, formattedDate, studentName, className);
                list.add(fb);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllFeedback: " + e.getMessage());
        }

        return list;
    }
}