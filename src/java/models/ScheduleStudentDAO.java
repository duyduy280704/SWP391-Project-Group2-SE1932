// Thuy_ thời khóa biểu của học sinh 
package models;

import dal.DBContext;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleStudentDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

// lấy ba lịch học gần nhất _ studentHome
    public List<ScheduleStudent> getTop3UpcomingSchedulesByStudentId(int studentId) {
        List<ScheduleStudent> list = new ArrayList<>();
        String sql = """
        SELECT TOP 3
            s.id,
            s.day,
            c.name AS class_name,
            t.full_name AS teacher_name,
            s.start_time,
            s.end_time,
            s.room
        FROM schedule s
        JOIN class c ON s.id_class = c.id
        JOIN teacher t ON s.id_teacher = t.id
        JOIN attendance a ON a.id_class = c.id
        WHERE a.id_student = ?
          AND TRY_CAST(s.day AS DATE) >= CAST(GETDATE() AS DATE)
        ORDER BY TRY_CAST(s.day AS DATE), s.start_time
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ScheduleStudent s = new ScheduleStudent(
                            String.valueOf(rs.getInt("id")),
                            rs.getString("day"),
                            rs.getString("class_name"),
                            rs.getString("start_time"),
                            rs.getString("end_time"),
                            rs.getString("room"),
                            rs.getString("teacher_name")
                    );
                    list.add(s);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi : " + e.getMessage());
        }

        return list;
    }
// Lịch lớp cũ đến ngày duyệt (bao gồm)

    public List<ScheduleStudent> getScheduleBefore(String classId, LocalDate approvedDate) {
        List<ScheduleStudent> list = new ArrayList<>();
        String sql = "SELECT s.id, s.day, c.name AS class_name, s.start_time, s.end_time, s.room "
                + "FROM schedule s JOIN class c ON s.id_class = c.id "
                + "WHERE s.id_class = ? AND s.day <= ? ORDER BY s.day, s.start_time";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ps.setString(2, approvedDate.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ScheduleStudent(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("day"),
                        rs.getString("class_name"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("room")
                ));
            }
        } catch (Exception e) {
            System.out.println("Err getScheduleStudentBefore: " + e.getMessage());
        }
        return list;
    }

// Lịch lớp mới từ ngày duyệt trở đi
    public List<ScheduleStudent> getScheduleAfter(String classId, LocalDate approvedDate) {
        List<ScheduleStudent> list = new ArrayList<>();
        String sql = "SELECT s.id, s.day, c.name AS class_name, s.start_time, s.end_time, s.room "
                + "FROM schedule s JOIN class c ON s.id_class = c.id "
                + "WHERE s.id_class = ? AND s.day >= ? ORDER BY s.day, s.start_time";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ps.setString(2, approvedDate.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new ScheduleStudent(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("day"),
                        rs.getString("class_name"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("room")
                ));
            }
        } catch (Exception e) {
            System.out.println("Err getScheduleStudentAfter: " + e.getMessage());
        }
        return list;
    }
// Lấy toàn bộ lịch học cho học sinh chưa chuyển lớp

    public List<ScheduleStudent> getScheduleStudentAll(int studentId, String classId) {
        List<ScheduleStudent> list = new ArrayList<>();
        String sql = "SELECT s.id, s.day, c.name AS class_name, s.start_time, s.end_time, s.room, "
                + "a.status, a.reason "
                + "FROM schedule s "
                + "JOIN class c ON s.id_class = c.id "
                + "LEFT JOIN attendance a ON a.id_class = s.id_class AND a.date = s.day AND a.id_student = ? "
                + "WHERE s.id_class = ? "
                + "ORDER BY s.day, s.start_time";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setString(2, classId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ScheduleStudent s = new ScheduleStudent(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("day"),
                        rs.getString("class_name"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("room")
                );
                s.setAttendanceStatus(rs.getString("status") != null ? rs.getString("status") : "");
                s.setReason(rs.getString("reason"));
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println("Err getScheduleStudentAll: " + e.getMessage());
        }
        return list;
    }

}