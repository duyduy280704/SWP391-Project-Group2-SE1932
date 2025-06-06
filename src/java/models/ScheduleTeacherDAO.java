package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public List<ScheduleTeacher> getScheduleTeacher(int teacherId, String startDate) {
        List<ScheduleTeacher> data = new ArrayList<>();
        try {
            LocalDate requestedStart = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate requestedEnd = requestedStart.plusDays(6);

            String strSQL = "SELECT \n"
                    + "    s.id,\n"
                    + "    s.day,\n"
                    + "    c.name AS class_name,\n"
                    + "    s.start_time,\n"
                    + "    s.end_time,\n"
                    + "    s.room\n"
                    + "FROM \n"
                    + "    schedule s\n"
                    + "JOIN \n"
                    + "    class c ON s.id_class = c.id\n"
                    + "WHERE \n"
                    + "    s.id_teacher = ? \n"
                    + "    AND s.day BETWEEN ? AND ?";
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, teacherId);
            stm.setString(2, requestedStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            stm.setString(3, requestedEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            rs = stm.executeQuery();
            while (rs.next()) {
                String day = rs.getString("day");
                if (day != null && day.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    ScheduleTeacher s = new ScheduleTeacher(
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
            System.out.println("Error in getScheduleTeacher: " + e.getMessage());
        }
        return data;
    }

    // Test main
    public static void main(String[] args) {
        ScheduleTeacherDAO dao = new ScheduleTeacherDAO();
        int teacherId = 5;
        String startDate = "2025-06-09"; // Test tuần tiếp theo
        List<ScheduleTeacher> list = dao.getScheduleTeacher(teacherId, startDate);

        if (list.isEmpty()) {
            System.out.println("Không có thời khóa biểu cho tuần từ " + startDate);
        } else {
            for (ScheduleTeacher s : list) {
                System.out.println("ID: " + s.getId());
                System.out.println("Ngày: " + s.getDay());
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
