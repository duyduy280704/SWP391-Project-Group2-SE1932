package models;

import dal.DBContext;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
// Thủy _ học sinh gửi phản hồi , giáo viên , staff xem
public class FeedbackCourseDAO extends DBContext {

    // Lấy danh sách lớp  đã học
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
                courses.add(new Courses(rs.getString("course_id"), rs.getString("class_name")));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getCoursesByStudentId: " + e.getMessage());
        }

        return courses;
    }

    // Thêm phản hồi
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

    // in tất cả phản hôi staff xem
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
                String formattedDate = "";
                Date rawDate = rs.getDate("date");
                if (rawDate != null) {
                    formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(rawDate);
                }

                list.add(new FeedbackByStudent(
                        rs.getInt("id"),
                        rs.getString("id_student"),
                        rs.getString("id_course"),
                        rs.getString("text"),
                        formattedDate,
                        rs.getString("full_name"),
                        rs.getString("class_name")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getAllFeedback: " + e.getMessage());
        }

        return list;
    }

    // Giáo viên xem phản hồi từ lớp mình dạy
    public List<FeedbackByStudent> getFeedbackByTeacher(String teacherId) {
        List<FeedbackByStudent> list = new ArrayList<>();
        String sql = """
            SELECT DISTINCT f.id, f.id_student, s.full_name, f.id_course, f.text, f.date, cl.name AS class_name
            FROM feedback f
            JOIN student s ON f.id_student = s.id
            JOIN course c ON f.id_course = c.id
            JOIN class cl ON cl.course_id = c.id
            JOIN schedule sch ON cl.id = sch.id_class
            WHERE sch.id_teacher = ?
            ORDER BY f.date DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String formattedDate = "";
                Date rawDate = rs.getDate("date");
                if (rawDate != null) {
                    formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(rawDate);
                }

                list.add(new FeedbackByStudent(
                        rs.getInt("id"),
                        rs.getString("id_student"),
                        rs.getString("id_course"),
                        rs.getString("text"),
                        formattedDate,
                        rs.getString("full_name"),
                        rs.getString("class_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lịch sử phản hồi của sinh viên
    public List<FeedbackByStudent> getFeedbackByStudent(String studentId) {
        List<FeedbackByStudent> list = new ArrayList<>();
        String sql = """
            SELECT f.id, f.id_student, f.id_course, f.text, f.date, cl.name AS class_name
            FROM feedback f
            JOIN class cl ON f.id_course = cl.course_id
            WHERE f.id_student = ?
            ORDER BY f.date DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String formattedDate = "";
                Date rawDate = rs.getDate("date");
                if (rawDate != null) {
                    formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(rawDate);
                }

                list.add(new FeedbackByStudent(
                        rs.getInt("id"),
                        rs.getString("id_student"),
                        rs.getString("id_course"),
                        rs.getString("text"),
                        formattedDate,
                        "",
                        rs.getString("class_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    // Tìm kiếm 

    public List<FeedbackByStudent> searchFeedback(String keyword) {
        List<FeedbackByStudent> list = new ArrayList<>();
        String sql = """
            SELECT f.id, f.id_student, s.full_name, f.id_course, cl.name AS class_name, f.text, f.date
            FROM feedback f
            JOIN student s ON f.id_student = s.id
            JOIN course c ON f.id_course = c.id
            JOIN class cl ON c.id = cl.course_id
            WHERE s.full_name LIKE ? OR cl.name LIKE ? OR f.text LIKE ?
            ORDER BY f.date DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String formattedDate = "";
                Date rawDate = rs.getDate("date");
                if (rawDate != null) {
                    formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(rawDate);
                }

                list.add(new FeedbackByStudent(
                        rs.getInt("id"),
                        rs.getString("id_student"),
                        rs.getString("id_course"),
                        rs.getString("text"),
                        formattedDate,
                        rs.getString("full_name"),
                        rs.getString("class_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
//  Tìm kiếm của lớp giáo viên dạy 

    public List<FeedbackByStudent> searchFeedbacksByTeacher(String teacherId, String keyword) {
        List<FeedbackByStudent> list = new ArrayList<>();
        String sql = """
        SELECT DISTINCT f.id, f.id_student, s.full_name, f.id_course, f.text, f.date, cl.name AS class_name
        FROM feedback f
        JOIN student s ON f.id_student = s.id
        JOIN course c ON f.id_course = c.id
        JOIN class cl ON cl.course_id = c.id
        JOIN schedule sch ON cl.id = sch.id_class
        WHERE sch.id_teacher = ?
          AND (s.full_name LIKE ? OR cl.name LIKE ? OR f.text LIKE ?)
        ORDER BY f.date DESC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, teacherId);
            ps.setString(2, kw);
            ps.setString(3, kw);
            ps.setString(4, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String formattedDate = "";
                Date rawDate = rs.getDate("date");
                if (rawDate != null) {
                    formattedDate = new java.text.SimpleDateFormat("dd/MM/yyyy").format(rawDate);
                }

                list.add(new FeedbackByStudent(
                        rs.getInt("id"),
                        rs.getString("id_student"),
                        rs.getString("id_course"),
                        rs.getString("text"),
                        formattedDate,
                        rs.getString("full_name"),
                        rs.getString("class_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}