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
// lấy thời khóa biểu của học  sinh 
 public List<ScheduleStudent> getScheduleStudent(int studentId, String startDate) {
    List<ScheduleStudent> data = new ArrayList<>();
    try {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate end = start.plusDays(6);

        String sql = "SELECT s.id, s.day, c.name AS class_name, s.start_time, s.end_time, s.room, " +
                     "a.status, a.reason " +
                     "FROM schedule s " +
                     "JOIN class c ON s.id_class = c.id " +
                     "JOIN class_student cs ON cs.class_id = c.id " +
                     "LEFT JOIN attendance a ON a.id_class = s.id_class AND a.date = s.day AND a.id_student = ? " +
                     "WHERE cs.student_id = ? AND s.day BETWEEN ? AND ? " +
                     "ORDER BY s.day, s.start_time";

        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setInt(1, studentId);
        stm.setInt(2, studentId);
        stm.setString(3, start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        stm.setString(4, end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            String day = rs.getString("day");
            if (day != null && day.matches("\\d{4}-\\d{2}-\\d{2}")) {
                ScheduleStudent s = new ScheduleStudent(
                        String.valueOf(rs.getInt("id")),
                        day,
                        rs.getString("class_name"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("room")
                );
                String status = rs.getString("status");
                s.setAttendanceStatus(status != null ? status : "");  // set "" nếu null để tránh lỗi
                s.setReason(rs.getString("reason"));
                data.add(s);
            }
        }
    } catch (SQLException e) {
        System.out.println("Error in getScheduleStudent: " + e.getMessage());
    }
    return data;
}

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
            System.out.println("Lỗi getTop3UpcomingSchedulesByStudentId: " + e.getMessage());
        }

        return list;
    }
}