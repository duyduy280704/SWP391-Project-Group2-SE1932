
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

    
}