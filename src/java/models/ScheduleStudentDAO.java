package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleStudentDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public List<ScheduleStudent> getScheduleStudent(int studentId, String startDate) {
        List<ScheduleStudent> data = new ArrayList<>();
        try {
            LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = start.plusDays(6); // End of week

            String strSQL = "SELECT \n"
                    + "    s.id,\n"
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
                    + "WHERE \n"
                    + "    a.id_student = ? \n"
                    + "    AND s.day BETWEEN ? AND ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, studentId);
            stm.setString(2, start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stm.setString(3, end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            rs = stm.executeQuery();
            while (rs.next()) {
                String day = rs.getString("day");
                // Kiểm tra định dạng của day
                if (day != null && day.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    ScheduleStudent s = new ScheduleStudent(
                            String.valueOf(rs.getInt("id")),
                            day,
                            rs.getString("class_name"),
                            rs.getString("start_time"),
                            rs.getString("end_time"),
                            rs.getString("room")
                    );
                    data.add(s);
                } else {
                    System.out.println("Invalid date format for day: " + day);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getScheduleStudent: " + e.getMessage());
        }
        return data;
    }
}