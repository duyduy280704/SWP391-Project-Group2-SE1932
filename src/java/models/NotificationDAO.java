/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dwight
 */
import dal.DBContext;
import java.sql.*;
import java.util.*;
import models.Notification;


public class NotificationDAO extends DBContext {
    private PreparedStatement stm;
    private ResultSet rs;

    public List<Notification> getLatestNotificationsByStudent(String studentId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT TOP 3 id, id_student, messenge, date " +
                     "FROM notification " +
                     "WHERE id_student = ? " +
                     "ORDER BY date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String idStudent = rs.getString("id_student");
                    String message = rs.getString("messenge");
                    String date = rs.getString("date");
                    Notification n = new Notification(id, idStudent, message, date);
                    list.add(n);
                }
            }
        } catch (Exception e) {
            System.out.println("getLatestNotificationsByStudent: " + e.getMessage());
        }

        return list;
    }
}
