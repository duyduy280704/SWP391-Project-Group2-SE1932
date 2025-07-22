package models;

import dal.DBContext;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TeachingAttendanceDAO extends DBContext {

// hàm lấy dữ liệu khi join để luu dưới dạng bảng tuy vấn sang đói tượng teachetattendance
    private ArrayList<TeachingAttendance> extractAttendancesFromResultSet(ResultSet rs) throws Exception {
        ArrayList<TeachingAttendance> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        while (rs.next()) {
            TeachingAttendance ta = new TeachingAttendance(
                    rs.getInt("confirmationId"),
                    rs.getString("status"),
                    rs.getString("note"),
                    rs.getString("teacherName"),
                    rs.getString("className"),
                    rs.getString("courseName"),
                    sdf.format(rs.getTimestamp("start_time")),
                    sdf.format(rs.getTimestamp("end_time")),
                    rs.getString("room"),
                    rs.getInt("classId"),
                    rs.getInt("teacherId"),
                    rs.getInt("scheduleId")
            );
            boolean duplicate = false;
            for (TeachingAttendance existing : list) {
                if (existing.getScheduleId() == ta.getScheduleId()) {
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                list.add(ta);
            }
        }
        return list;
    }

    // lấy tất cả lớp học trong hôm nay
    public ArrayList<TeachingAttendance> getAllTodayAttendances() {
        try {
            String sql = "SELECT "
                    + "tc.id AS confirmationId, tc.status, tc.note, "
                    + "t.full_name AS teacherName, "
                    + "c.name AS className, "
                    + "co.name AS courseName, "
                    + "s.start_time, s.end_time, s.room, "
                    + "s.id_class AS classId, s.id_teacher AS teacherId, s.day, s.id AS scheduleId "
                    + "FROM Schedule s "
                    + "JOIN Teacher t ON s.id_teacher = t.id "
                    + "JOIN Class c ON s.id_class = c.id "
                    + "JOIN Course co ON c.course_id = co.id "
                    + "LEFT JOIN TeachingConfirmation tc ON s.id = tc.schedule_id "
                    + "WHERE s.day = CAST(GETDATE() AS DATE)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return extractAttendancesFromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
// tìm kiếm trong hôm nay

    public ArrayList<TeachingAttendance> getTodayAttendancesByKeyword(String keyword) {
        try {
            String sql = "SELECT "
                    + "tc.id AS confirmationId, tc.status, tc.note, "
                    + "t.full_name AS teacherName, "
                    + "c.name AS className, "
                    + "co.name AS courseName, "
                    + "s.start_time, s.end_time, s.room, "
                    + "s.id_class AS classId, s.id_teacher AS teacherId, s.day, s.id AS scheduleId "
                    + "FROM Schedule s "
                    + "JOIN Teacher t ON s.id_teacher = t.id "
                    + "JOIN Class c ON s.id_class = c.id "
                    + "JOIN Course co ON c.course_id = co.id "
                    + "LEFT JOIN TeachingConfirmation tc ON s.id = tc.schedule_id "
                    + "WHERE s.day = CAST(GETDATE() AS DATE) AND (t.full_name LIKE ? OR c.name LIKE ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            return extractAttendancesFromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
// có r thì trả id lịch, chưa thì chèn status với note null

    public int ensureConfirmationExistsBySchedule(int scheduleId) {
        try {
            // Kiểm tra đã tồn tại
            String checkSql = "SELECT id FROM TeachingConfirmation WHERE schedule_id = ?";
            PreparedStatement checkStm = connection.prepareStatement(checkSql);
            checkStm.setInt(1, scheduleId);
            ResultSet rs = checkStm.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
            // Lấy dữ liệu từ bảng Schedule
            String infoSql = "SELECT id_class, id_teacher, day FROM Schedule WHERE id = ?";
            PreparedStatement infoStm = connection.prepareStatement(infoSql);
            infoStm.setInt(1, scheduleId);
            ResultSet infoRs = infoStm.executeQuery();

            if (!infoRs.next()) {
                return 0;
            }
            int classId = infoRs.getInt("id_class");
            int teacherId = infoRs.getInt("id_teacher");
            Date day = infoRs.getDate("day");

            // Insert đầy đủ dữ liệu
            String insertSql = "INSERT INTO TeachingConfirmation "
                    + "(schedule_id, class_id, teacher_id, status, note, day) "
                    + "VALUES (?, ?, ?, NULL, NULL, ?)";
            PreparedStatement insertStm = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            insertStm.setInt(1, scheduleId);
            insertStm.setInt(2, classId);
            insertStm.setInt(3, teacherId);
            insertStm.setDate(4, day);
            insertStm.executeUpdate();

            ResultSet generatedKeys = insertStm.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

// cập nhật
    public void updateStatusAndNote(int id, String status, String note) {
        try {
            String sql = "UPDATE TeachingConfirmation SET status = ?, note = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            if ("pending".equals(status)) {
                ps.setNull(1, Types.VARCHAR);
            } else {
                ps.setString(1, status);
            }
            if (note == null || note.trim().isEmpty()) {
                ps.setNull(2, Types.VARCHAR);
            } else {
                ps.setString(2, note);
            }
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//lấy in ra theo ngày cụ thể

    public ArrayList<TeachingAttendance> getAttendancesByDay(Date day) {
        try {
            String sql = "SELECT "
                    + "tc.id AS confirmationId, tc.status, tc.note, "
                    + "t.full_name AS teacherName, "
                    + "c.name AS className, "
                    + "co.name AS courseName, "
                    + "s.start_time, s.end_time, s.room, "
                    + "s.id_class AS classId, s.id_teacher AS teacherId, s.day, s.id AS scheduleId "
                    + "FROM Schedule s "
                    + "JOIN Teacher t ON s.id_teacher = t.id "
                    + "JOIN Class c ON s.id_class = c.id "
                    + "JOIN Course co ON c.course_id = co.id "
                    + "LEFT JOIN TeachingConfirmation tc ON s.id = tc.schedule_id "
                    + "WHERE s.day = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, day);
            ResultSet rs = ps.executeQuery();
            return extractAttendancesFromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
// tìm kiếm ở ngày cụ thể

    public ArrayList<TeachingAttendance> getAttendancesByDayAndKeyword(Date day, String keyword) {
        try {
            String sql = "SELECT "
                    + "tc.id AS confirmationId, tc.status, tc.note, "
                    + "t.full_name AS teacherName, "
                    + "c.name AS className, "
                    + "co.name AS courseName, "
                    + "s.start_time, s.end_time, s.room, "
                    + "s.id_class AS classId, s.id_teacher AS teacherId, s.day, s.id AS scheduleId "
                    + "FROM Schedule s "
                    + "JOIN Teacher t ON s.id_teacher = t.id "
                    + "JOIN Class c ON s.id_class = c.id "
                    + "JOIN Course co ON c.course_id = co.id "
                    + "LEFT JOIN TeachingConfirmation tc ON s.id = tc.schedule_id "
                    + "WHERE s.day = ? AND (t.full_name LIKE ? OR c.name LIKE ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDate(1, day);
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            return extractAttendancesFromResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}