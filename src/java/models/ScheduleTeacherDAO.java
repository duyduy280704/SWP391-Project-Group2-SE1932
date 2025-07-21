package models;

import dal.DBContext;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ScheduleTeacherDAO extends DBContext {

    // Lấy thời khóa biểu của giáo viên theo tuần
    public List<ScheduleTeacher> getScheduleTeacher(int teacherId, String startDate) {
        List<ScheduleTeacher> schedules = new ArrayList<>();
        try {
            LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate end = start.plusDays(6);

            String sql = "SELECT s.id, s.day, s.id_class, c.name AS class_name, s.start_time, s.end_time, s.room " +
                         "FROM schedule s " +
                         "JOIN class c ON s.id_class = c.id " +
                         "WHERE s.id_teacher = ? AND s.day BETWEEN ? AND ?";

            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, teacherId);
            stm.setString(2, start.toString());
            stm.setString(3, end.toString());

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ScheduleTeacher st = new ScheduleTeacher(
                    String.valueOf(rs.getInt("id")),
                    rs.getString("day"),
                    rs.getString("id_class"),
                    rs.getString("class_name"),
                    rs.getString("start_time"),
                    rs.getString("end_time"),
                    rs.getString("room")
                );

                st.setAttendanceTaken(isAttendanceTaken(st.getClassId(), st.getDay()));
                schedules.add(st);
            }
        } catch (Exception e) {
            System.err.println("Lỗi getScheduleTeacher: " + e.getMessage());
        }
        return schedules;
    }

    // kiểm tra đã điểm danh chưa
    private boolean isAttendanceTaken(String classId, String date) {
        String sqlCheck = "SELECT COUNT(*) FROM attendance WHERE id_class = ? AND date = ?";
        try (PreparedStatement checkStm = connection.prepareStatement(sqlCheck)) {
            checkStm.setString(1, classId);
            checkStm.setString(2, date);
            try (ResultSet checkRs = checkStm.executeQuery()) {
                if (checkRs.next()) {
                    return checkRs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi kiểm tra attendance: " + e.getMessage());
        }
        return false;
    }

    // Lấy danh sách học sinh theo scheduleId
    public List<Students> getStudentsByScheduleId(String scheduleId) {
        List<Students> students = new ArrayList<>();
        String sql = "SELECT s.id, s.full_name, s.email, s.birth_date, s.gender " +
                     "FROM student s " +
                     "JOIN class_student cs ON s.id = cs.student_id " +
                     "WHERE cs.class_id = (SELECT id_class FROM schedule WHERE id = ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, scheduleId);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Students student = new Students(
                        rs.getString("id"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        "",
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        "", 
                        "student"
                );
                students.add(student);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getStudentsByScheduleId: " + e.getMessage());
        }
        return students;
    }

    // Lưu điểm danh
    public void saveAttendance(String scheduleId, List<StudentAttendance> list, String date) {
        try {
            String classId = null;
            String sqlGetClass = "SELECT id_class FROM schedule WHERE id = ?";
            try (PreparedStatement stmClass = connection.prepareStatement(sqlGetClass)) {
                stmClass.setString(1, scheduleId);
                ResultSet rsClass = stmClass.executeQuery();
                if (rsClass.next()) {
                    classId = rsClass.getString("id_class");
                } else {
                    return;
                }
            }          
            String sqlDelete = "DELETE FROM attendance WHERE id_class = ? AND date = ? AND id_student = ?";
            try (PreparedStatement stmDelete = connection.prepareStatement(sqlDelete)) {
                for (StudentAttendance sa : list) {
                    stmDelete.setString(1, classId);
                    stmDelete.setString(2, date);
                    stmDelete.setString(3, sa.getStudent().getId());
                    stmDelete.addBatch();
                }
                stmDelete.executeBatch();
            }
            String sqlInsert = "INSERT INTO attendance (id_student, id_class, date, status, reason) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmInsert = connection.prepareStatement(sqlInsert)) {
                for (StudentAttendance sa : list) {
                    stmInsert.setString(1, sa.getStudent().getId());
                    stmInsert.setString(2, classId);
                    stmInsert.setString(3, date);
                    stmInsert.setString(4, sa.getStatus());
                    stmInsert.setString(5, sa.getReason() == null ? "" : sa.getReason());
                    stmInsert.addBatch();
                }
                stmInsert.executeBatch();
            }

        } catch (SQLException e) {
            System.err.println("Lỗi saveAttendance: " + e.getMessage());
        }
    }

    // Lấy danh sách điểm danh theo lớp và ngày 
    public List<StudentAttendance> getStudentAttendanceList(String classId, String date) {
        List<StudentAttendance> attendanceList = new ArrayList<>();
        String sql = "SELECT a.id_student, a.status, a.reason, s.full_name, s.email, s.birth_date, s.gender " +
                     "FROM attendance a " +
                     "JOIN student s ON a.id_student = s.id " +
                     "WHERE a.id_class = ? AND a.date = ?";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, classId);
            stm.setString(2, date);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Students student = new Students(
                        rs.getString("id_student"),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        "", 
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        "", 
                        "student"
                );
                StudentAttendance sa = new StudentAttendance(
                        student,
                        rs.getString("status"),
                        rs.getString("reason")
                );
                attendanceList.add(sa);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi getStudentAttendanceList: " + e.getMessage());
        }

        return attendanceList;
    }
}
