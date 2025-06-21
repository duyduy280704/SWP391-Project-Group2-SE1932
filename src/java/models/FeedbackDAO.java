/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Quang
 */
public class FeedbackDAO extends DBContext {
    
    PreparedStatement stm;
    ResultSet rs;

    // lấy top 4 đánh giá mới nhất
    public ArrayList<Feedback> getTop4Feedback() {
        ArrayList<Feedback> data = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(
                """
                select TOP (4) c.name as course_name, s.full_name as student_name, text, date 
                from feedback f
                join Course c on f.id_course = c.id
                join Student s on f.id_student = s.id
                ORDER BY f.date DESC
                """
        )) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                
                String course = rs.getString("course_name");
                String student = rs.getString("student_name");
                String text = rs.getString("text");
                String date = rs.getString("date");
                
                Feedback p = new Feedback(course, student, text, date);
                data.add(p);
            }
        } catch (SQLException e) {
            System.err.println("getCourses: " + e.getMessage());
        }
        return data;
    }
}
