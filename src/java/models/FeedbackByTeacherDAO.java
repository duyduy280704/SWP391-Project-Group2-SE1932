package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackByTeacherDAO extends DBContext {

    // Lấy tất cả lớp mà giáo viên đang dạy
    public List<Categories_class> getAllClassesByTeacher(String teacherId) {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.id, c.name "
                + "FROM Class c "
                + "JOIN Schedule s ON c.id = s.id_class "
                + "WHERE s.id_teacher = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Categories_class(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tìm kiếm lớp theo từ khóa và giáo viên
    public List<Categories_class> searchClass(String teacherId, String keyword) {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.id, c.name "
                + "FROM Class c "
                + "JOIN Schedule s ON c.id = s.id_class "
                + "WHERE s.id_teacher = ? AND c.name LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, teacherId);
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Categories_class(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy danh sách học sinh theo lớp
    public List<Students> getStudentsByClass(String classId) {
        List<Students> list = new ArrayList<>();
        String sql = "SELECT s.id, s.full_name "
                + "FROM Student s "
                + "JOIN Class_Student cs ON s.id = cs.student_id "
                + "WHERE cs.class_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Students student = new Students();
                student.setId(rs.getString("id"));
                student.setName(rs.getString("full_name"));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy danh sách học sinh theo lớp có từ khóa tìm kiếm theo tên hoặc đánh giá
    public List<Students> searchFeedback(String classId, String keyword, String teacherId) {
        List<Students> list = new ArrayList<>();
        String sql = "SELECT DISTINCT s.id, s.full_name "
                + "FROM Student s "
                + "JOIN Class_Student cs ON s.id = cs.student_id "
                + "LEFT JOIN student_assessment sa ON sa.student_id = s.id AND sa.class_id = ? AND sa.teacher_id = ? "
                + "WHERE cs.class_id = ? AND (s.full_name LIKE ? OR sa.text LIKE ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ps.setString(2, teacherId);
            ps.setString(3, classId);
            ps.setString(4, "%" + keyword + "%");
            ps.setString(5, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Students student = new Students();
                student.setId(rs.getString("id"));
                student.setName(rs.getString("full_name"));
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm đánh giá học sinh 
    public void insertAssessment(String studentId, String classId, String teacherId, String text) {
        String sql = "INSERT INTO student_assessment (student_id, class_id, teacher_id, text, date) "
                + "VALUES (?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, classId);
            ps.setString(3, teacherId);
            ps.setString(4, text);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật đánh giá học sinh 
    public void updateAssessment(String studentId, String classId, String teacherId, String text) {
        String sql = "UPDATE student_assessment SET text = ?, date = GETDATE() "
                + "WHERE student_id = ? AND class_id = ? AND teacher_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, text);
            ps.setString(2, studentId);
            ps.setString(3, classId);
            ps.setString(4, teacherId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Thêm hoặc cập nhật đánh giá học sinh 
    public void upsertAssessment(String studentId, String classId, String teacherId, String text) {
        String checkSql = "SELECT COUNT(*) FROM student_assessment WHERE student_id = ? AND class_id = ? AND teacher_id = ?";
        try (PreparedStatement psCheck = connection.prepareStatement(checkSql)) {
            psCheck.setString(1, studentId);
            psCheck.setString(2, classId);
            psCheck.setString(3, teacherId);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                updateAssessment(studentId, classId, teacherId, text);
            } else {
                insertAssessment(studentId, classId, teacherId, text);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy đánh giá của 1 học sinh trong lớp từ giáo viên
    public String getFeedbackStudent(String studentId, String classId, String teacherId) {
        String sql = "SELECT text FROM student_assessment WHERE student_id = ? AND class_id = ? AND teacher_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, classId);
            ps.setString(3, teacherId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("text");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Lấy tất cả đánh giá trong lớp của giáo viên (hiếm dùng)
    public List<String> getFeedbackByClassAndTeacher(String classId, String teacherId) {
        List<String> list = new ArrayList<>();
        String sql = "SELECT text FROM student_assessment WHERE class_id = ? AND teacher_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ps.setString(2, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    // Lấy tất cả lớp có đánh giá 

    public List<Categories_class> getAllClassesWithFeedback() {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT DISTINCT c.id, c.name, co.name AS course_name "
                + "FROM student_assessment sa "
                + "JOIN Class c ON sa.class_id = c.id "
                + "JOIN Course co ON c.course_id = co.id";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categories_class cls = new Categories_class();
                cls.setId_class(rs.getString("id"));
                cls.setName_class(rs.getString("name"));
                cls.setCourse_name(rs.getString("course_name"));
                list.add(cls);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

// Lấy tất cả đánh giá trong lớp của giáo viên 
    public List<Assessment> getFeedbackByClass(String classId) {
        List<Assessment> list = new ArrayList<>();
        String sql = "SELECT s.full_name AS student_name, sa.text, sa.date "
                + "FROM student_assessment sa "
                + "JOIN Student s ON sa.student_id = s.id "
                + "WHERE sa.class_id = ? "
                + "ORDER BY s.full_name";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Assessment a = new Assessment();
                a.setStudentName(rs.getString("student_name"));
                a.setText(rs.getString("text"));
                a.setDate(rs.getTimestamp("date"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public String getClassNameById(String classId) {
        String sql = "SELECT name FROM Class WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

}