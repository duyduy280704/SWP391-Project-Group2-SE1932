package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// Thuy -thời khóa biểu của giáo viên 
public class ScheduleTeacherDAO extends DBContext {
    PreparedStatement stm;
    ResultSet rs;

    public List<ScheduleTeacher> getScheduleTeacher(int teacherId) {
        List<ScheduleTeacher> data = new ArrayList<>();
        try {
            String strSQL = "SELECT s.id, s.day, c.name AS className, s.start_time, s.end_time, s.room " +
                            "FROM schedule AS s " +
                            "JOIN Class AS c ON s.id_class = c.id " +
                            "JOIN TeacherApplications AS t ON s.id_teacher = t.id " +
                            "WHERE s.id_teacher = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, teacherId);
            rs = stm.executeQuery();
            while (rs.next()) {
                ScheduleTeacher s = new ScheduleTeacher(
                    String.valueOf(rs.getInt("id")),
                    rs.getString("day"),
                    rs.getString("className"),
                    rs.getString("start_time"),
                    rs.getString("end_time"),
                    rs.getString("room")
                );
                s.computeDayOfWeek(); // Tính ngày trong tuần tại đây
                data.add(s);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getScheduleTeacher: " + e.getMessage());
        }
        return data;
    }

    // Test main
    public static void main(String[] args) {
        ScheduleTeacherDAO dao = new ScheduleTeacherDAO();
        int teacherId = 5; // Thay ID giáo viên phù hợp
        List<ScheduleTeacher> list = dao.getScheduleTeacher(teacherId);

        if (list.isEmpty()) {
            System.out.println("Không có thời khóa biểu.");
        } else {
            for (ScheduleTeacher s : list) {
                System.out.println("ID: " + s.getId());
                System.out.println("Ngày: " + s.getDay());
                System.out.println("Thứ (EN): " + s.getSpecificDay());
                System.out.println("Thứ (VN): " + s.getDayVN());
                System.out.println("Lớp: " + s.getNameClass());
                System.out.println("Bắt đầu: " + s.getStartTime());
                System.out.println("Kết thúc: " + s.getEndTime());
                System.out.println("Phòng: " + s.getRoom());
                System.out.println("---------------------------");
            }
        }
    }
}