/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ScheduleTeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public List<ScheduleTeacher> getScheduleTeacher(int teacherId) {
    ArrayList<ScheduleTeacher> data = new ArrayList<>();

    try {
        String strSQL = "SELECT * FROM schedule AS s "
                      + "JOIN Class AS c ON s.id_class = c.id "
                      + "JOIN TeacherApplications AS t ON s.id_teacher = t.id "
                      + "WHERE s.id_teacher = ?";

        stm = connection.prepareStatement(strSQL);
        stm.setInt(1, teacherId);
        rs = stm.executeQuery();

        while (rs.next()) {
            ScheduleTeacher s = new ScheduleTeacher(
                String.valueOf(rs.getInt(1)),
                rs.getString("day"),
                rs.getString("name"),
                rs.getString("start_time"),
                rs.getString("end_time"),
                rs.getString("room")
            );
            data.add(s);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi getScheduleTeacher: " + e.getMessage());
    }
    return data;
}

    
    public static void main(String[] args) {
        ScheduleTeacherDAO dao = new ScheduleTeacherDAO();
        
        int teacherId = 5; // Thay đổi theo ID hợp lệ có trong DB

        List<ScheduleTeacher> list = dao.getScheduleTeacher(teacherId);
        
        if (list.isEmpty()) {
            System.out.println("Không tìm thấy thời khóa biểu cho giáo viên có ID = " + teacherId);
        } else {
            System.out.println("Danh sách thời khóa biểu:");
            for (ScheduleTeacher s : list) {
                System.out.println("ID: " + s.getId());
                System.out.println("Day: " + s.getDay());
                System.out.println("Class: " + s.getNameClass());
                System.out.println("Start: " + s.getStartTime());
                System.out.println("End: " + s.getEndTime());
                System.out.println("Room: " + s.getRoom());
                System.out.println("------------");
            }
        }
    }

}
