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
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
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
        String sql = "SELECT c.id, c.name, tc.name AS courseName, c.status "
                + "FROM Class c JOIN Course tc ON c.course_id = tc.id "
                + "WHERE c.course_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, courseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Categories_class cls = new Categories_class();
                cls.setId_class(rs.getString("id"));
                cls.setName_class(rs.getString("name"));
                cls.setCourse_name(rs.getString("courseName"));
                cls.setStatus(rs.getString("status"));
                list.add(cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean assignToClassSingle(int regisitionId, int classId) throws SQLException {
        try {
            // Kiểm tra lớp đầy
            if (isClassFull(classId)) {
                throw new SQLException("Class " + classId + " is full.");
            }

            // Thêm mới học viên vào lớp
            String insertSql = "INSERT INTO Class_Student (class_id, student_id) "
                    + "SELECT ?, student_id FROM regisition WHERE id = ?";
            try (PreparedStatement ps = connection.prepareStatement(insertSql)) {
                ps.setInt(1, classId);
                ps.setInt(2, regisitionId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    updateStatus(regisitionId, "đã phân lớp");
                    return true;
                } else {
                    throw new SQLException("No rows affected when assigning student.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean isAlreadyAssigned(int studentId, int classId) {
        String sql = "SELECT 1 FROM Class_Student WHERE student_id = ? AND class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, classId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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

    public String getStudentNameByRegisitionId(int regisitionId) {
        String sql = "SELECT s.full_name FROM regisition r JOIN Student s ON r.student_id = s.id WHERE r.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, regisitionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("full_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Không rõ";
    }

    public void insert(Regisition r) {
        String sql = "INSERT INTO Regisition (student_id, course_id, note, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getStudentId());
            ps.setInt(2, r.getCourseId());
            ps.setString(3, r.getNote());
            ps.setString(4, r.getStatus());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("insert Regisition: " + e.getMessage());
        }
    }

    public boolean isAlreadyRegistered(String studentId, int courseId) {
        String sql = "SELECT COUNT(*) FROM regisition WHERE student_id = ? AND course_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setInt(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.out.println("isAlreadyRegistered: " + e.getMessage());
        }
        return false;
    }

    public List<Regisition> getRegisteredCourses(String studentId) {
        List<Regisition> list = new ArrayList<>();
        String sql = "SELECT r.id AS regis_id, r.status, r.note, "
                + "       c.id AS course_id, c.name AS course_name "
                + "FROM regisition r "
                + "JOIN Course c ON r.course_id = c.id "
                + "WHERE r.student_id = ? ";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, studentId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Regisition r = new Regisition();
                    r.setId(rs.getInt("regis_id"));
                    r.setCourseId(rs.getInt("course_id"));
                    r.setCourseName(rs.getString("course_name"));
                    r.setStatus(rs.getString("status"));
                    r.setNote(rs.getString("note"));
                    r.setStudentId(studentId);
                    list.add(r);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy trạng thái hiện tại từ regisition
    public String getRegistrationStatus(int regisId) {
        String sql = "SELECT status FROM regisition WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, regisId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy lịch học đầu tiên của lớp mà học sinh đang theo học
    public Timestamp getFirstSchedule(String studentId, int courseId) {
        String sql = "SELECT MIN(s.start_time) AS first_time "
                + "FROM schedule s "
                + "JOIN Class c ON c.id = s.id_class "
                + "JOIN Class_Student cs ON cs.class_id = c.id "
                + "WHERE cs.student_id = ? AND c.course_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, studentId);
            stm.setInt(2, courseId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getTimestamp("first_time");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật trạng thái hủy đăng ký
    public void cancelRegistration(int regisId) {
        String sql = "UPDATE regisition SET status = N'đã hủy' WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, regisId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa học sinh ra khỏi lớp
    public void removeFromClass(String studentId, int courseId) {
        String sql = "DELETE cs FROM Class_Student cs "
                + "JOIN Class c ON cs.class_id = c.id "
                + "WHERE cs.student_id = ? AND c.course_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, studentId);
            stm.setInt(2, courseId);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFirstScheduleAsString(String studentId, int courseId) {
        String sql = "SELECT FORMAT(MIN(s.day), 'yyyy-MM-dd HH:mm') AS first_time "
                + "FROM schedule s "
                + "JOIN Class c ON c.id = s.id_class "
                + "JOIN Class_Student cs ON cs.class_id = c.id "
                + "WHERE cs.student_id = ? AND c.course_id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, studentId);
            stm.setInt(2, courseId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("first_time"); // trả về chuỗi ngày giờ
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isClassFull(int classId) {
        String sql = "SELECT COUNT(*) AS student_count FROM Class_Student WHERE class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("student_count") >= 30; // Lớp đầy nếu có 30 học viên trở lên
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getStudentCountInClass(int classId) {
        String sql = "SELECT COUNT(*) AS student_count FROM Class_Student WHERE class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("student_count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isStudentInClass(int regisitionId, int classId) {
        String sql = "SELECT 1 FROM Class_Student cs "
                + "JOIN regisition r ON r.student_id = cs.student_id "
                + "WHERE r.id = ? AND cs.class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, regisitionId);
            ps.setInt(2, classId);
            return ps.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getAssignedClassName(int regisitionId) {
        String sql = "SELECT c.name FROM regisition r "
                + "JOIN Class_Student cs ON r.student_id = cs.student_id "
                + "JOIN Class c ON cs.class_id = c.id "
                + "WHERE r.id = ? AND c.course_id = r.course_id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, regisitionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStudentIdByRegisitionId(int regisitionId) {
        String sql = "SELECT student_id FROM regisition WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, regisitionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("student_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getCourseIdByRegisitionId(int regisitionId) {
        String sql = "SELECT course_id FROM regisition WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, regisitionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("course_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean unassignClassForStudent(int regisitionId) {
        String studentId = getStudentIdByRegisitionId(regisitionId);
        Integer courseId = getCourseIdByRegisitionId(regisitionId);
        if (studentId != null && courseId != null) {
            removeFromClass(studentId, courseId);
            updateStatus(regisitionId, "chưa phân lớp");
            return true;
        }
        return false;
    }
public String getStudentEmailByRegisitionId(int regisitionId) {
        String sql = "SELECT s.email FROM regisition r "
         + "JOIN Student s ON r.student_id = s.id WHERE r.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, regisitionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClassNameById(int classId) {
        String sql = "SELECT name FROM Class WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, classId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCourseNameByRegisitionId(int regisitionId) {
        String sql = "SELECT c.name FROM regisition r JOIN Course c ON r.course_id = c.id WHERE r.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, regisitionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
