/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dwight
 */
import dal.DBContext;
import java.sql.*;
import java.util.*;
import models.Notification;

public class NotificationDAO extends DBContext {

   

    public List<Notification> getLatestNotificationsByStudent(String studentId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT TOP 3 id, id_student, messenge, date "
                + "FROM notification "
                + "WHERE id_student = ? "
                + "ORDER BY date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String idStudent = rs.getString("id_student");
                    String message = rs.getString("messenge");
                    String date = rs.getString("date");
                    Notification n = new Notification(id, idStudent, message, date);
                    list.add(n);
                }
            }
        } catch (Exception e) {
            System.out.println("getLatestNotificationsByStudent: " + e.getMessage());
        }

        return list;
    }

    public List<Notification> getNotificationsByStudent(String studentId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT id, id_student, messenge, date "
                + "FROM notification "
                + "WHERE id_student = ? "
                + "ORDER BY date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String idStudent = rs.getString("id_student");
                    String message = rs.getString("messenge");
                    String date = rs.getString("date");
                    Notification n = new Notification(id, idStudent, message, date);
                    list.add(n);
                }
            }
        } catch (Exception e) {
            System.out.println("getNotificationsByStudent: " + e.getMessage());
        }

        return list;
    }

    public List<Notification> getNotificationsByStaff(String staffId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT id, id_staff, message, date "
                + "FROM NoticeToStaff "
                + "WHERE id_staff = ? "
                + "ORDER BY date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String id_staff = rs.getString("id_staff");
                    String message = rs.getString("message");
                    String date = rs.getString("date");
                    Notification n = new Notification(id, id_staff, message, date);
                    list.add(n);
                }
            }
        } catch (Exception e) {
            System.out.println("getNotificationsByStaff: " + e.getMessage());
        }

        return list;
    }

    public List<Notification> getNotificationsByTeacher(String idteacher) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT id, id_teacher, message, date "
                + "FROM NoticeToTeacher "
                + "WHERE id_teacher = ? "
                + "ORDER BY date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idteacher);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String id_teacher = rs.getString("id_teacher");
                    String message = rs.getString("message");
                    String date = rs.getString("date");
                    Notification n = new Notification(id, id_teacher, message, date);
                    list.add(n);
                }
            }
        } catch (Exception e) {
            System.out.println("getNotificationsByTeacher: " + e.getMessage());
        }

        return list;
    }

    public void insertNotification(String role, String id, String message) {
        String sql = "";
        switch (role) {
            case "student":
                sql = "INSERT INTO notification (id_student, messenge, date) VALUES (?, ?, GETDATE())";
                break;
            case "teacher":
                sql = "INSERT INTO NoticeToTeacher (id_teacher, messenge, date) VALUES (?, ?, GETDATE())";
                break;
            case "staff":
                sql = "INSERT INTO NoticeToStaff (id_staff, messenge, date) VALUES (?, ?, GETDATE())";
                break;
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, message);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gửi 1 người
    public void insertNotificationForOne(String role, String id, String message) {
        String table = switch (role) {
            case "student" ->
                "notification (id_student, messenge, date)";
            case "teacher" ->
                "NoticeToTeacher (id_teacher, message, date)";
            case "staff" ->
                "NoticeToStaff (id_staff, message, date)";
            default ->
                throw new IllegalArgumentException("Invalid role");
        };
        String sql = "INSERT INTO " + table + " VALUES (?, ?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, message);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Gửi cho toàn bộ người dùng theo role và trả lại Map<id, email> để gửi mail
    public Map<String, String> insertNotificationByRole(String role, String message) {
        Map<String, String> emailMap = new HashMap<>();
        String table = switch (role) {
            case "student" ->
                "Student";
            case "teacher" ->
                "Teacher";
            case "staff" ->
                "Admin_staff";
            default ->
                throw new IllegalArgumentException("Invalid role");
        };

        String insertSql = switch (role) {
            case "student" ->
                "INSERT INTO notification (id_student, messenge, date) VALUES (?, ?, GETDATE())";
            case "teacher" ->
                "INSERT INTO NoticeToTeacher (id_teacher, message, date) VALUES (?, ?, GETDATE())";
            case "staff" ->
                "INSERT INTO NoticeToStaff (id_staff, message, date) VALUES (?, ?, GETDATE())";
            default ->
                "";
        };

        String selectSql = "SELECT id, email FROM " + table;
        try (PreparedStatement ps = connection.prepareStatement(selectSql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String email = rs.getString("email");
                emailMap.put(id, email);

                try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                    insert.setString(1, id);
                    insert.setString(2, message);
                    insert.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emailMap;
    }

    // Gửi thông báo đến tất cả học sinh trong danh sách lớp (nhiều lớp)
    public Map<String, String> insertNotificationByClasses(List<String> classIds, String message) {
        Map<String, String> emailMap = new HashMap<>();
        String sql = "SELECT cs.student_id, s.email FROM Class_Student cs JOIN Student s ON cs.student_id = s.id WHERE cs.class_id = ?";
        String insertSql = "INSERT INTO notification (id_student, messenge, date) VALUES (?, ?, GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (String classId : classIds) {
                ps.setString(1, classId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String studentId = rs.getString("student_id");
                        String email = rs.getString("email");
                        emailMap.put(studentId, email);

                        try (PreparedStatement insert = connection.prepareStatement(insertSql)) {
                            insert.setString(1, studentId);
                            insert.setString(2, message);
                            insert.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return emailMap;
    }

    // Lấy email theo role + id
    public String getEmailByRoleAndId(String role, String id) {
        String sql = switch (role) {
            case "student" ->
                "SELECT email FROM Student WHERE id = ?";
            case "teacher" ->
                "SELECT email FROM Teacher WHERE id = ?";
            case "staff" ->
                "SELECT email FROM Admin_staff WHERE id = ?";
            default ->
                "";
        };

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("email");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách tất cả lớp học (id + name)
    public List<Categories_class> getAllClasses() {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT id, name FROM Class";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Categories_class(rs.getString("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Notification n) {
        String sql = "INSERT INTO Notification (id_student, messenge, date) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, n.getIdUser());
            ps.setString(2, n.getMessage());
            ps.setString(3, n.getDate());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("insert Notification: " + e.getMessage());
        }
    }

    public List<Students> getStudentsWithUnpaidPayments() {
        List<Students> list = new ArrayList<>();
        String sql = """
        SELECT DISTINCT s.*
        FROM Student s
        JOIN Payment p ON s.id = p.id_student
        WHERE p.status = N'chưa thanh toán'
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Students s = new Students();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("full_name"));
                s.setEmail(rs.getString("email"));
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println("getStudentsWithUnpaidPayments: " + e.getMessage());
        }
        return list;
    }

    public List<PreRegistration> getApprovedRegistrations() {
        List<PreRegistration> list = new ArrayList<>();
        String sql = "SELECT * FROM PreRegistrations WHERE status = N'Đã duyệt'";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PreRegistration p = new PreRegistration();
                p.setId(rs.getInt("id"));
                p.setFull_name(rs.getString("full_name"));
                p.setEmail(rs.getString("email"));
                list.add(p);
            }
        } catch (Exception e) {
            System.out.println("getApprovedRegistrations: " + e.getMessage());
        }
        return list;
    }

    public void insertNotificationById(int id, String content) {

        String sql = "INSERT INTO Notification (id_student, messenge, date) VALUES (?, ?, GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.setString(2, content);

            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println("insertNotificationByEmail: " + e.getMessage());

        }

    }
    
    public void insertNotificationByIdTeacher(String id, String content) {

        String sql = "INSERT INTO NoticeToTeacher (id_teacher, message, date) VALUES (?, ?, GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, id);

            ps.setString(2, content);

            ps.executeUpdate();

        } catch (Exception e) {

            System.out.println("insertNotificationByEmail: " + e.getMessage());

        }

    }

    public void updateStatus(int id, String newStatus) {
        String sql = "UPDATE PreRegistrations SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateStatus: " + e.getMessage());
        }
    }

    public List<PreRegistration> getApprovedRegistrationsDetailed() {
        List<PreRegistration> list = new ArrayList<>();
        String sql = """
        SELECT pr.*, c.name AS course_name
        FROM PreRegistrations pr
        JOIN Course c ON pr.course_id = c.id
        WHERE pr.status = N'Đã duyệt'
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PreRegistration p = new PreRegistration();
                p.setId(rs.getInt("id"));
                p.setFull_name(rs.getString("full_name"));
                p.setEmail(rs.getString("email"));
                p.setPhone(rs.getString("phone"));
                p.setCourseName(rs.getString("course_name"));
                p.setNote(rs.getString("note"));
                p.setCourse_id(rs.getInt("course_id"));
                p.setId_sale(rs.getInt("id_sale"));
                list
                        .add(p);
            }
        } catch (Exception e) {
            System.out.println("getApprovedRegistrationsDetailed: " + e.getMessage());
        }
        return list;
    }

    public List<Students> getStudentsWithUnpaidPaymentsDetailed() {
        List<Students> list = new ArrayList<>();
        String sql = """
        SELECT s.id, s.full_name, s.email, s.phone, c.name AS course_name
        FROM Student s
        JOIN Payment p ON s.id = p.id_student
        JOIN Course c ON c.id = p.id_course
        WHERE p.status = N'chưa thanh toán'
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Students s = new Students();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("full_name"));
                s.setEmail(rs.getString("email"));
                s.setPhone(rs.getString("phone"));
                s.setCourseName(rs.getString("course_name")); // thêm field trong bean
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println("getStudentsWithUnpaidPaymentsDetailed: " + e.getMessage());
        }
        return list;
    }

    public List<UserBasic> getUsersByRole(String role) {
        List<UserBasic> list = new ArrayList<>();
        String sql = "";

        switch (role) {
            case "student":
                sql = "SELECT id, full_name FROM Student";
                break;
            case "teacher":
                sql = "SELECT id, full_name FROM Teacher";
                break;
            case "staff":
                sql = "SELECT id, full_name FROM Admin_staff";
                break;
            default:
                return list;
        }

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UserBasic user = new UserBasic();
                user.setId(rs.getInt("id"));
                user.setFullName(rs.getString("full_name"));
                list.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertPayment(int idStudent, int idCourse, int idSale, String orderCode) {
        String sql = """
        INSERT INTO payment (id_student, id_course, status, date, id_sale, order_code)
        VALUES (?, ?, N'Chưa thanh toán', GETDATE(), ?, ?)
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idStudent);
            ps.setInt(2, idCourse);
            ps.setInt(3, idSale);
            ps.setString(4, orderCode);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("insertPayment: " + e.getMessage());
        }
    }

    public void insertRegisition(int idStudent, int idCourse, String note) {
        String sql = """
        INSERT INTO regisition (student_id, course_id, note, status)
        VALUES (?, ?, ?, N'Chưa xếp lớp')
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idStudent);
            ps.setInt(2, idCourse);
            ps.setString(3, note);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("insertRegisition: " + e.getMessage());
        }
    }

    public int getStudentIdByEmail(String email) {

        String sql = "SELECT id FROM Student WHERE email = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return rs.getInt("id");

                }

            }

        } catch (Exception e) {

            System.out.println("getStudentIdByEmail: " + e.getMessage());

        }

        return -1;

    }
}
