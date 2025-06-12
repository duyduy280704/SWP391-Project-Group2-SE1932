
package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// Thuy- thời khóa biểu của học sinh 
public class ScheduleStudentDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public List<ScheduleStudent> getScheduleStudent(int studentId) {
        List<ScheduleStudent> data = new ArrayList<>();
        try {
            String strSQL = "SELECT \n"
                    + "	s.id,\n"
                    + "    s.day,\n"
                    + "    c.name AS class_name,\n"
                    + "    t.full_name AS teacher_name,\n"
                    + "    s.start_time,\n"
                    + "    s.end_time,\n"
                    + "    s.room,\n"
                    + "    a.id_student\n"
                    + "FROM \n"
                    + "    schedule s\n"
                    + "JOIN \n"
                    + "    class c ON s.id_class = c.id\n"
                    + "JOIN \n"
                    + "    teacher t ON s.id_teacher = t.id\n"
                    + "JOIN \n"
                    + "    attendance a ON a.id_class = c.id\n"
                    + "		\n"
                    + "		Where   a.id_student= ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, studentId);
            rs = stm.executeQuery();
            while (rs.next()) {
                ScheduleStudent s = new ScheduleStudent(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("day"),
                        rs.getString("class_name"),
                        rs.getString("start_time"),
                        rs.getString("end_time"),
                        rs.getString("room")
                );
                s.computeDayOfWeek(); // Tính ngày trong tuần
                data.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getScheduleStudent: " + e.getMessage());
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