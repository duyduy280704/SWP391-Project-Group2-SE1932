package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//Thủy chuyển lớp của học sinh
public class ClassTransferRequestDAO extends DBContext {

    // Gửi đơn xin chuyển lớp của học sinh
    public void submitRequest(ClassTransferRequest req) {
        String sql = "INSERT INTO class_transfer_request (student_id, from_class_id, to_class_id, reason, request_date, status) VALUES (?, ?, ?, ?, GETDATE(), 'pending')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, req.getStudentId());
            ps.setString(2, req.getFromClassId());
            ps.setString(3, req.getToClassId());
            ps.setString(4, req.getReason());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Trả về danh sách đơn xin chuyển lớp của một học sinh
    public List<ClassTransferRequest> getRequestsByStudent(String studentId) {
        List<ClassTransferRequest> list = new ArrayList<>();
        String sql = "SELECT r.*, cf.name AS from_class_name, ct.name AS to_class_name, s.full_name AS student_name "
                + "FROM class_transfer_request r "
                + "LEFT JOIN Class cf ON r.from_class_id = cf.id "
                + "LEFT JOIN Class ct ON r.to_class_id = ct.id "
                + "LEFT JOIN Student s ON r.student_id = s.id "
                + "WHERE r.student_id = ? ORDER BY r.request_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClassTransferRequest req = new ClassTransferRequest();
                    req.setId(rs.getInt("id"));
                    req.setStudentId(rs.getString("student_id"));
                    req.setFromClassId(rs.getString("from_class_id"));
                    req.setToClassId(rs.getString("to_class_id"));
                    req.setReason(rs.getString("reason"));
                    req.setStatus(rs.getString("status"));
                    req.setRequestDate(rs.getDate("request_date"));
                    req.setResponseDate(rs.getDate("response_date"));
                    req.setStaffNote(rs.getString("staff_note"));
                    req.setFromClassName(rs.getString("from_class_name"));
                    req.setToClassName(rs.getString("to_class_name"));
                    req.setStudentName(rs.getString("student_name"));
                    list.add(req);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Tất cả đơn xin chuyển lớp
    public List<ClassTransferRequest> getAllRequests() {
        List<ClassTransferRequest> list = new ArrayList<>();
        String sql = "SELECT r.*, s.full_name AS student_name, cf.name AS from_class_name, ct.name AS to_class_name "
                + "FROM class_transfer_request r "
                + "JOIN Student s ON r.student_id = s.id "
                + "LEFT JOIN Class cf ON r.from_class_id = cf.id "
                + "LEFT JOIN Class ct ON r.to_class_id = ct.id "
                + "ORDER BY r.request_date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClassTransferRequest req = new ClassTransferRequest();
                req.setId(rs.getInt("id"));
                req.setStudentId(rs.getString("student_id"));
                req.setFromClassId(rs.getString("from_class_id"));
                req.setToClassId(rs.getString("to_class_id"));
                req.setReason(rs.getString("reason"));
                req.setStatus(rs.getString("status"));
                req.setRequestDate(rs.getDate("request_date"));
                req.setResponseDate(rs.getDate("response_date"));
                req.setStaffNote(rs.getString("staff_note"));
                req.setFromClassName(rs.getString("from_class_name"));
                req.setToClassName(rs.getString("to_class_name"));
                req.setStudentName(rs.getString("student_name"));
                list.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Duyệt đơn xin chuyển lớp
    public void approveRequest(int requestId, String staffNote) {
        String updateSql = "UPDATE class_transfer_request SET status = 'approved', response_date = GETDATE(), staff_note = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
            ps.setString(1, staffNote);
            ps.setInt(2, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String getInfoSql = "SELECT student_id, to_class_id FROM class_transfer_request WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(getInfoSql)) {
            ps.setInt(1, requestId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String studentId = rs.getString("student_id");
                    String newClassId = rs.getString("to_class_id");

                    String updateClassSql = "UPDATE Class_Student SET class_id = ? WHERE student_id = ?";
                    try (PreparedStatement update = connection.prepareStatement(updateClassSql)) {
                        update.setString(1, newClassId);
                        update.setString(2, studentId);
                        update.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Từ chối đơn xin chuyển lớp
    public void rejectRequest(int requestId, String staffNote) {
        String sql = "UPDATE class_transfer_request SET status = 'rejected', response_date = GETDATE(), staff_note = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, staffNote);
            ps.setInt(2, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // lấy tất cả lớp trong khóa học 
    public List<Categories_class> getAllOtherClasses(String currentClassId) {
        List<Categories_class> list = new ArrayList<>();
        String sql = "SELECT c.id, c.name, cr.name as course_name "
                + "FROM Class c JOIN Course cr ON c.course_id = cr.id "
                + "WHERE c.id <> ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, currentClassId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Categories_class cc = new Categories_class();
                    cc.setId_class(rs.getString("id"));
                    cc.setName_class(rs.getString("name"));
                    cc.setCourse_name(rs.getString("course_name"));
                    list.add(cc);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lấy lớp hiện tại của học sinh
    public Categories_class getClassByStudentId(String studentId) {
        String sql = "SELECT c.id, c.name, cr.name as course_name "
                + "FROM Class_Student sc "
                + "JOIN Class c ON sc.class_id = c.id "
                + "JOIN Course cr ON c.course_id = cr.id "
                + "WHERE sc.student_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Categories_class cc = new Categories_class();
                    cc.setId_class(rs.getString("id"));
                    cc.setName_class(rs.getString("name"));
                    cc.setCourse_name(rs.getString("course_name"));
                    return cc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
// mỗi học sinh chỉ dc gửi một đơn nếu có đơn dáng chờ sử lí 
    public boolean hasPendingRequest(String studentId) {
        String sql = "SELECT COUNT(*) FROM class_transfer_request WHERE student_id = ? AND status = 'pending'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //tìm kiếm theo tên học sinh
    public List<ClassTransferRequest> searchRequestsByStudentName(String keyword) {
        List<ClassTransferRequest> list = new ArrayList<>();
        String sql = "SELECT r.*, s.full_name AS student_name, cf.name AS from_class_name, ct.name AS to_class_name "
                + "FROM class_transfer_request r "
                + "JOIN Student s ON r.student_id = s.id "
                + "LEFT JOIN Class cf ON r.from_class_id = cf.id "
                + "LEFT JOIN Class ct ON r.to_class_id = ct.id "
                + "WHERE LOWER(s.full_name) LIKE ? "
                + "ORDER BY r.request_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword.toLowerCase().trim() + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ClassTransferRequest req = new ClassTransferRequest();
                    req.setId(rs.getInt("id"));
                    req.setStudentId(rs.getString("student_id"));
                    req.setFromClassId(rs.getString("from_class_id"));
                    req.setToClassId(rs.getString("to_class_id"));
                    req.setReason(rs.getString("reason"));
                    req.setStatus(rs.getString("status"));
                    req.setRequestDate(rs.getDate("request_date"));
                    req.setResponseDate(rs.getDate("response_date"));
                    req.setStaffNote(rs.getString("staff_note"));
                    req.setFromClassName(rs.getString("from_class_name"));
                    req.setToClassName(rs.getString("to_class_name"));
                    req.setStudentName(rs.getString("student_name"));
                    list.add(req);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Lấy đơn được duyệt gần nhất của học sinh
public ClassTransferRequest getLastApprovedRequest(String studentId) {
    String sql = "SELECT TOP 1 * FROM class_transfer_request "
               + "WHERE student_id = ? AND status = 'approved' "
               + "ORDER BY response_date DESC";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, studentId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                ClassTransferRequest req = new ClassTransferRequest();
                req.setId(rs.getInt("id"));
                req.setFromClassId(rs.getString("from_class_id"));
                req.setToClassId(rs.getString("to_class_id"));
                req.setResponseDate(rs.getDate("response_date"));
                return req;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

    

}