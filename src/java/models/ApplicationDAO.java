/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import Controllers.SendMail;
import dal.DBContext;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Quang
 */
public class ApplicationDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    // add(nộp) đơn của giáo viên
    public ResultMessage addApplicationByTeacher(Application s) {
        try {
            String sql = "INSERT INTO TeacherApplicationList (teacher_id, application_id, content, date, status) VALUES (?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(s.getName()));
            stm.setInt(2, Integer.parseInt(s.getApplicationType()));
            stm.setString(3, s.getContent());
            stm.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            stm.setString(5, s.getStatus());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Nộp đơn thành công.");
            } else {
                return new ResultMessage(false, "Nộp đơn thất bại.");
            }
        } catch (SQLException e) {
            return new ResultMessage(false, "Lỗi khi thêm đơn: " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                // Log lỗi nếu cần
            }
        }
    }

    // lấy danh sách kiểu đơn của giáo viên
    public ArrayList<TeacherApplicationType> getTeacherApplicationTypeList() {
        ArrayList<TeacherApplicationType> data = new ArrayList<>();
        String sql = "  SELECT * FROM TeacherApplicationTypes";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                TeacherApplicationType tat = new TeacherApplicationType(id, name);
                data.add(tat);
            }
        } catch (SQLException e) {
            System.err.println("getTeacherApplicationTypeList: " + e.getMessage());
        }
        return data;
    }

    // add(nộp) đơn của học sinh
    public ResultMessage addApplicationByStudent(Application s) {
        try {
            String sql = "INSERT INTO StudentApplicationList (student_id, application_id, content, date, status) VALUES (?, ?, ?, ?, ?)";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(s.getName()));
            stm.setInt(2, Integer.parseInt(s.getApplicationType()));
            stm.setString(3, s.getContent());
            stm.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            stm.setString(5, s.getStatus());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Nộp đơn thành công.");
            } else {
                return new ResultMessage(false, "Nộp đơn thất bại.");
            }
        } catch (SQLException e) {
            return new ResultMessage(false, "Lỗi khi thêm đơn: " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                // Log lỗi nếu cần
            }
        }
    }

    // lấy danh sách kiểu đơn của học sinh
    public ArrayList<TeacherApplicationType> getStudentApplicationTypeList() {
        ArrayList<TeacherApplicationType> data = new ArrayList<>();
        String sql = "  SELECT * FROM StudentApplicationTypes";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                TeacherApplicationType tat = new TeacherApplicationType(id, name);
                data.add(tat);
            }
        } catch (SQLException e) {
            System.err.println("getTeacherApplicationTypeList: " + e.getMessage());
        }
        return data;
    }

    // lấy danh sách đơn của học sinh
    public ArrayList<Application> getListApplicationStu() {

        ArrayList<Application> data = new ArrayList<>();
        try {
            String strSQL = """
                              select * from StudentApplicationList s 
                              join StudentApplicationTypes r on s.application_id = r.id
                              join Student t on s.student_id = t.id
                            """;
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(11);
                String application = rs.getString(8);
                String content = rs.getString(4);
                String date = rs.getString(5);
                String status = rs.getString(6);

                Application p = new Application(id, name, application, content, date, status);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getListApplicationStu" + e.getMessage());

        }
        return data;
    }

    // gửi email thông báo cho học sinh
    private void sendEmailNotificationStudent(int applicationId, int studentId, String appTypeName) throws UnsupportedEncodingException {
        try {
            // Lấy email của học sinh
            String selectEmailSql = "SELECT email FROM Student WHERE id = ?";
            stm = connection.prepareStatement(selectEmailSql);
            stm.setInt(1, studentId);
            rs = stm.executeQuery();
            String studentEmail = null;
            if (rs.next()) {
                studentEmail = rs.getString("email");
            }
            rs.close();
            stm.close();

            // Gửi email nếu email tồn tại
            if (studentEmail != null && !studentEmail.isEmpty()) {
                String subject = "Thông báo xử lý đơn đăng ký";
                String messageText = "Kính gửi,\n\nĐơn đăng ký của bạn (" + appTypeName + ") đã được xử lý thành công vào ngày " + new java.util.Date() + ".\n\nTrân trọng,\nHệ thống BIG DREAM";
                SendMail.send(studentEmail, subject, messageText);
            } else {
                System.err.println("Không tìm thấy email cho student_id: " + studentId);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy email học sinh: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources in sendEmailNotification: " + e.getMessage());
            }
        }
    }

    // xử lý đơn của học sinh và thêm thông báo
    public ResultMessage processApplicationStu(int applicationId) throws UnsupportedEncodingException {
        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Check if the application is already processed
            String checkStatusSql = "SELECT status FROM StudentApplicationList WHERE id = ?";
            stm = connection.prepareStatement(checkStatusSql);
            stm.setInt(1, applicationId);
            rs = stm.executeQuery();
            if (rs.next() && "Đã xử lý".equals(rs.getString("status"))) {
                rs.close();
                stm.close();
                connection.setAutoCommit(true);
                return new ResultMessage(false, "Đơn đã được xử lý trước đó.");
            }
            rs.close();
            stm.close();

            // Lấy student_id và tên loại đơn
            String selectSql = """
                              SELECT sal.student_id, sal.application_id, sat.name 
                              FROM StudentApplicationList sal 
                              JOIN StudentApplicationTypes sat ON sal.application_id = sat.id 
                              WHERE sal.id = ?
                            """;
            stm = connection.prepareStatement(selectSql);
            stm.setInt(1, applicationId);
            rs = stm.executeQuery();
            int studentId = 0;
            String appTypeName = "";
            if (rs.next()) {
                studentId = rs.getInt("student_id");
                appTypeName = rs.getString("name");
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                return new ResultMessage(false, "Không tìm thấy đơn với ID: " + applicationId);
            }
            rs.close();
            stm.close();

            // Cập nhật trạng thái đơn
            String updateSql = "UPDATE StudentApplicationList SET status = ? WHERE id = ?";
            stm = connection.prepareStatement(updateSql);
            stm.setString(1, "Đã xử lý");
            stm.setInt(2, applicationId);
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                // Thêm thông báo vào bảng notification
                String insertNotificationSql = "INSERT INTO notification (id_student, messenge, date) VALUES (?, ?, ?)";
                stm = connection.prepareStatement(insertNotificationSql);
                stm.setInt(1, studentId);
                stm.setString(2, "Đơn đăng ký của bạn (" + appTypeName + ") đã được xử lý.");
                stm.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                stm.executeUpdate();
                stm.close();

                // Gửi email thông báo
                sendEmailNotificationStudent(applicationId, studentId, appTypeName);

                // Commit transaction
                connection.commit();
                return new ResultMessage(true, "Xử lý đơn, gửi thông báo và email thành công.");
            } else {
                connection.rollback();
                return new ResultMessage(false, "Không tìm thấy đơn để xử lý.");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback failed: " + rollbackEx.getMessage());
            }
            return new ResultMessage(false, "Lỗi khi xử lý đơn: " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (rs != null) {
                    rs.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error closing resources in processApplicationStu: " + e.getMessage());
            }
        }
    }
    
    
    
    // lấy danh sách đơn của giáo viên
    public ArrayList<Application> getListApplicationTea() {

        ArrayList<Application> data = new ArrayList<>();
        try {
            String strSQL = """
                              select * from TeacherApplicationList s 
                              join TeacherApplicationTypes r on s.application_id = r.id
                              join Teacher t on s.teacher_id = t.id
                            """;
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(11);
                String application = rs.getString(8);
                String content = rs.getString(4);
                String date = rs.getString(5);
                String status = rs.getString(6);

                Application p = new Application(id, name, application, content, date, status);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getListApplicationTea" + e.getMessage());

        }
        return data;
    }

    // gửi email thông báo cho giáo viên
    private void sendEmailNotificationTeacher(int applicationId, int teacherId, String appTypeName) throws UnsupportedEncodingException {
        try {
            // Lấy email của học sinh
            String selectEmailSql = "SELECT email FROM Teacher WHERE id = ?";
            stm = connection.prepareStatement(selectEmailSql);
            stm.setInt(1, teacherId);
            rs = stm.executeQuery();
            String teacherEmail = null;
            if (rs.next()) {
                teacherEmail = rs.getString("email");
            }
            rs.close();
            stm.close();

            // Gửi email nếu email tồn tại
            if (teacherEmail != null && !teacherEmail.isEmpty()) {
                String subject = "Thông báo xử lý đơn đăng ký";
                String messageText = "Kính gửi,\n\nĐơn đăng ký của bạn (" + appTypeName + ") đã được xử lý thành công vào ngày " + new java.util.Date() + ".\n\nTrân trọng,\nHệ thống BIG DREAM";
                SendMail.send(teacherEmail, subject, messageText);
            } else {
                System.err.println("Không tìm thấy email cho teacher_id: " + teacherId);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy email giáo viên: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing resources in sendEmailNotification: " + e.getMessage());
            }
        }
    }

    // xử lý đơn của giáo viên và thêm thông báo
    public ResultMessage processApplicationTea(int applicationId) throws UnsupportedEncodingException {
        try {
            // Bắt đầu transaction
            connection.setAutoCommit(false);

            // Check if the application is already processed
            String checkStatusSql = "SELECT status FROM TeacherApplicationList WHERE id = ?";
            stm = connection.prepareStatement(checkStatusSql);
            stm.setInt(1, applicationId);
            rs = stm.executeQuery();
            if (rs.next() && "Đã xử lý".equals(rs.getString("status"))) {
                rs.close();
                stm.close();
                connection.setAutoCommit(true);
                return new ResultMessage(false, "Đơn đã được xử lý trước đó.");
            }
            rs.close();
            stm.close();

            // Lấy teacher_id và tên loại đơn
            String selectSql = """
                              SELECT sal.teacher_id, sal.application_id, sat.name 
                              FROM TeacherApplicationList sal 
                              JOIN TeacherApplicationTypes sat ON sal.application_id = sat.id 
                              WHERE sal.id = ?
                            """;
            stm = connection.prepareStatement(selectSql);
            stm.setInt(1, applicationId);
            rs = stm.executeQuery();
            int teacherId = 0;
            String appTypeName = "";
            if (rs.next()) {
                teacherId = rs.getInt("teacher_id");
                appTypeName = rs.getString("name");
            } else {
                connection.rollback();
                connection.setAutoCommit(true);
                return new ResultMessage(false, "Không tìm thấy đơn với ID: " + applicationId);
            }
            rs.close();
            stm.close();

            // Cập nhật trạng thái đơn
            String updateSql = "UPDATE TeacherApplicationList SET status = ? WHERE id = ?";
            stm = connection.prepareStatement(updateSql);
            stm.setString(1, "Đã xử lý");
            stm.setInt(2, applicationId);
            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                // Thêm thông báo vào bảng notification
                String insertNotificationSql = "INSERT INTO NoticeToTeacher (id_teacher, message, date) VALUES (?, ?, ?)";
                stm = connection.prepareStatement(insertNotificationSql);
                stm.setInt(1, teacherId);
                stm.setString(2, "Đơn đăng ký của bạn (" + appTypeName + ") đã được xử lý.");
                stm.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                stm.executeUpdate();
                stm.close();

                // Gửi email thông báo
                sendEmailNotificationTeacher(applicationId, teacherId, appTypeName);

                // Commit transaction
                connection.commit();
                return new ResultMessage(true, "Xử lý đơn, gửi thông báo và email thành công.");
            } else {
                connection.rollback();
                return new ResultMessage(false, "Không tìm thấy đơn để xử lý.");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Rollback failed: " + rollbackEx.getMessage());
            }
            return new ResultMessage(false, "Lỗi khi xử lý đơn: " + e.getMessage());
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (rs != null) {
                    rs.close();
                }
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Error closing resources in processApplicationTea: " + e.getMessage());
            }
        }
    }

}