/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author HP
 */
public class NoticeToTeacherDAO extends DBContext {
    public List<NoticeToTeacher> getNoticesByTeacherId(int teacherId) {
        List<NoticeToTeacher> list = new ArrayList<>();
        String sql = "SELECT * FROM NoticeToTeacher WHERE id_teacher = ? ORDER BY date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NoticeToTeacher notice = new NoticeToTeacher();
                notice.setId(rs.getInt("id"));
                notice.setIdTeacher(rs.getInt("id_teacher"));
                notice.setMessage(rs.getString("message"));
                notice.setDate(rs.getTimestamp("date"));
                list.add(notice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
