package models;

import dal.DBContext;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ClassTransferRequestDAO extends DBContext {
//Lấy danh sách tất cả các khóa học

    public List<CategoriesCourse> getAllCourses() {
        List<CategoriesCourse> list = new ArrayList<>();
        String sql = "SELECT id, name FROM course";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new CategoriesCourse(rs.getString(1), rs.getString(2)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//Lấy danh sách các lớp học trong một khóa học

    public List<Categories_class> getClassesByCourse(String courseId) {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT id, name FROM class WHERE course_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Categories_class(rs.getString(1), rs.getString(2)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
//danh sách học sinh đang học trong một lớp

    public List<Students> getStudentsByClass(String classId) {
        List<Students> list = new ArrayList<>();
        String sql = "SELECT s.id, s.full_name FROM student s "
                + "JOIN class_student cs ON s.id = cs.student_id "
                + "WHERE cs.class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Students s = new Students();
                    s.setId(rs.getString("id"));
                    s.setName(rs.getString("full_name"));
                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
// số lần chuyển lớp 

    public int getTransferCount(String studentId, String courseId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM class_transfer_request r "
                + "JOIN class c1 ON r.from_class_id = c1.id "
                + "WHERE r.student_id = ? AND c1.course_id = ? AND r.status = 'approved'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            System.out.println("getTransferCount: studentId=" + studentId + ", courseId=" + courseId + ", count=" + count);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("getTransferCount - Error: " + e.getMessage());
        }
        return count;
    }
//Cập nhật lớp học

    public void processTransfer(String studentId, String fromClassId, String toClassId) throws SQLException {
        // Cập nhật lớp học của học sinh trong bảng class_student
        String updateSql = "UPDATE class_student SET class_id = ? WHERE student_id = ? AND class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
            ps.setString(1, toClassId);
            ps.setString(2, studentId);
            ps.setString(3, fromClassId);
            ps.executeUpdate();
        }
    }
// lấy danh sách các lớp trừ lóp hiện tại 

    public List<Categories_class> getTargetClasses(String courseId, String currentClassId) {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT id, name FROM class WHERE course_id = ? AND id <> ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, courseId);
            ps.setString(2, currentClassId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Categories_class(rs.getString("id"), rs.getString("name")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
// thêm yêu cầu chuyển lớp

    public void insertTransferRequest(String studentId, String fromClassId, String toClassId, String reason, String status) throws SQLException {
        String sql = "INSERT INTO class_transfer_request (student_id, from_class_id, to_class_id, reason, status, request_date) VALUES (?, ?, ?, ?, ?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, fromClassId);
            ps.setString(3, toClassId);
            ps.setString(4, reason);
            ps.setString(5, status);
            ps.executeUpdate();
        }
    }
//Lấy tên  học sinh

    public String getStudentNameById(String studentId) {
        String sql = "SELECT full_name FROM student WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("full_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // lịch sử chuyển lớp
    public List<ClassTransferRequest> getTransferHistory() {
        List<ClassTransferRequest> list = new ArrayList<>();
        if (connection == null) {
            System.out.println("getTransferHistory: Database connection is null");
            return list;
        }
        String sql = "SELECT r.student_id, s.full_name, r.from_class_id, c1.name AS from_class_name, "
                + "r.to_class_id, c2.name AS to_class_name, r.request_date AS transfer_date, "
                + "c1.course_id AS course_id "
                + "FROM class_transfer_request r "
                + "JOIN student s ON r.student_id = s.id "
                + "JOIN class c1 ON r.from_class_id = c1.id "
                + "JOIN class c2 ON r.to_class_id = c2.id "
                + "ORDER BY r.request_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Students student = new Students();
                student.setId(rs.getString("student_id"));
                student.setName(rs.getString("full_name"));
                Categories_class fromClass = new Categories_class();
                fromClass.setId_class(rs.getString("from_class_id"));
                fromClass.setName_class(rs.getString("from_class_name"));
                Categories_class toClass = new Categories_class();
                toClass.setId_class(rs.getString("to_class_id"));
                toClass.setName_class(rs.getString("to_class_name"));
                Timestamp transferDate = rs.getTimestamp("transfer_date");
                String courseId = rs.getString("course_id");
                int transferCount = getTransferCount(student.getId(), courseId);
                ClassTransferRequest ctr = new ClassTransferRequest(student, fromClass, toClass, transferDate, transferCount);
                list.add(ctr);
            }
            System.out.println("getTransferHistory: Loaded " + list.size() + " records");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getTransferHistory - Error: " + e.getMessage());
        }
        return list;
    }

    // timf kiếm theo tên hoạc lớp
    public List<ClassTransferRequest> searchTransferHistory(String keyword) {
        List<ClassTransferRequest> list = new ArrayList<>();
        String sql = "SELECT r.student_id, s.full_name,\n"
                + "       r.from_class_id, c1.name AS from_class_name,\n"
                + "       r.to_class_id, c2.name AS to_class_name,\n"
                + "       r.request_date AS transfer_date,\n"
                + "       c1.course_id AS course_id\n"
                + "FROM class_transfer_request r\n"
                + "JOIN student s ON r.student_id = s.id\n"
                + "JOIN class c1 ON r.from_class_id = c1.id\n"
                + "JOIN class c2 ON r.to_class_id = c2.id\n"
                + "WHERE s.full_name LIKE ? OR c1.name LIKE ? OR c2.name LIKE ?\n"
                + "ORDER BY r.request_date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);
            ps.setString(3, kw);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Students student = new Students();
                    student.setId(rs.getString("student_id"));
                    student.setName(rs.getString("full_name"));
                    Categories_class fromClass = new Categories_class();
                    fromClass.setId_class(rs.getString("from_class_id"));
                    fromClass.setName_class(rs.getString("from_class_name"));
                    Categories_class toClass = new Categories_class();
                    toClass.setId_class(rs.getString("to_class_id"));
                    toClass.setName_class(rs.getString("to_class_name"));
                    Timestamp transferDate = rs.getTimestamp("transfer_date");
                    String courseId = rs.getString("course_id");
                    int transferCount = 0;
                    if (student.getId() != null && courseId != null) {
                        transferCount = getTransferCount(student.getId(), courseId);
                    }
                    ClassTransferRequest ctr = new ClassTransferRequest(student, fromClass, toClass, transferDate, transferCount);
                    list.add(ctr);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

//lọc theo ngày  tháng năm 
    public List<ClassTransferRequest> filterTransferHistory(String fromDate, String toDate) {
        List<ClassTransferRequest> list = new ArrayList<>();
        String sql = "SELECT r.student_id, s.full_name, "
                + "r.from_class_id, c1.name AS from_class, "
                + "r.to_class_id, c2.name AS to_class, "
                + "r.request_date AS transfer_date, "
                + "c1.course_id AS course_id "
                + "FROM class_transfer_request r "
                + "JOIN student s ON r.student_id = s.id "
                + "JOIN class c1 ON r.from_class_id = c1.id "
                + "JOIN class c2 ON r.to_class_id = c2.id "
                + "WHERE 1=1";
        boolean hasFrom = fromDate != null && !fromDate.trim().isEmpty();
        boolean hasTo = toDate != null && !toDate.trim().isEmpty();
        if (hasFrom) {
            sql += " AND r.request_date >= ?";
        }
        if (hasTo) {
            sql += " AND r.request_date < ?";
        }
        sql += " ORDER BY r.request_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            if (hasFrom) {
                ps.setDate(index++, java.sql.Date.valueOf(fromDate));
            }
            if (hasTo) {
                // Cộng thêm 1 ngày vào toDate để lọc chính xác
                LocalDate to = LocalDate.parse(toDate).plusDays(1);
                ps.setDate(index++, java.sql.Date.valueOf(to));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Students student = new Students();
                student.setId(rs.getString("student_id"));
                student.setName(rs.getString("full_name"));
                Categories_class fromClass = new Categories_class();
                fromClass.setId_class(rs.getString("from_class_id"));
                fromClass.setName_class(rs.getString("from_class"));
                Categories_class toClass = new Categories_class();
                toClass.setId_class(rs.getString("to_class_id"));
                toClass.setName_class(rs.getString("to_class"));
                Timestamp transferDate = rs.getTimestamp("transfer_date");
                String courseId = rs.getString("course_id");
                int transferCount = getTransferCount(student.getId(), courseId);
                list.add(new ClassTransferRequest(student, fromClass, toClass, transferDate, transferCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //Lấy yêu cầu chuyển lớp gần nhất
    public ClassTransferRequest getLastApprovedRequest(String studentId) {
        String sql = "SELECT TOP 1 r.student_id, s.full_name, "
                + "r.from_class_id, c1.name AS from_class, "
                + "r.to_class_id, c2.name AS to_class, "
                   + "r.request_date AS transfer_date, "
                + "c1.course_id AS course_id "
                + "FROM class_transfer_request r "
                + "JOIN student s ON r.student_id = s.id "
                + "JOIN class c1 ON r.from_class_id = c1.id "
                + "JOIN class c2 ON r.to_class_id = c2.id "
                + "WHERE r.student_id = ? "
                + "ORDER BY r.request_date DESC"; // Nếu bạn có cột request_date

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Students student = new Students();
                student.setId(rs.getString("student_id"));
                student.setName(rs.getString("full_name"));
                Categories_class fromClass = new Categories_class();
                fromClass.setId_class(rs.getString("from_class_id"));
                fromClass.setName_class(rs.getString("from_class"));
                Categories_class toClass = new Categories_class();
                toClass.setId_class(rs.getString("to_class_id"));
                toClass.setName_class(rs.getString("to_class"));
                Timestamp transferDate = rs.getTimestamp("transfer_date");
                String courseId = rs.getString("course_id");
                int transferCount = getTransferCount(student.getId(), courseId);
                return new ClassTransferRequest(student, fromClass, toClass, transferDate, transferCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy thông tin lớp học hiện tại của học sinh
    public Categories_class getClassByStudentId(String studentId) {
        String sql = "SELECT c.id, c.name AS class_name, co.name AS course_name\n"
                + "FROM class c\n"
                + "JOIN course co ON c.course_id = co.id\n"
                + "JOIN Class_Student cs ON cs.class_id = c.id\n"
                + "JOIN Student s ON cs.student_id = s.id\n"
                + "WHERE s.id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Categories_class clazz = new Categories_class();
                clazz.setId_class(rs.getString("id"));
                clazz.setName_class(rs.getString("class_name"));
                clazz.setCourse_name(rs.getString("course_name"));  // set tên khóa học
                return clazz;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
