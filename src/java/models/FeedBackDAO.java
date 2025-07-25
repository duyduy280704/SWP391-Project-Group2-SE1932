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
 * @author Dương
 */
//Dương_Homepage
public class FeedBackDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<FeedBack> getFeedbacks() {
        ArrayList<FeedBack> data = new ArrayList<>();
        try {
            String strSQL = "SELECT f.id, f.id_student, f.id_course, f.text, f.date, u.full_name\n"
                    + "FROM feedback f JOIN Student u ON f.id_student = u.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int studentId = rs.getInt("id_student");
                int courseId = rs.getInt("id_course");
                String feedbackText = rs.getString("text");
                String feedbackDate = rs.getString("date"); // nếu muốn kiểu java.sql.Date thì dùng getDate()
                String studentName = rs.getString("full_name");

                FeedBack f = new FeedBack(id, studentId, courseId, feedbackText, feedbackDate, studentName);
                data.add(f);
            }
        } catch (Exception e) {
            System.out.println("getFeedbacks: " + e.getMessage());
        }
        return data;
    }
 public ArrayList<FeedBack> getTop3Feedbacks() {
    ArrayList<FeedBack> data = new ArrayList<>();
    try {
        String strSQL = "SELECT TOP 3 f.id, f.id_student, f.id_course, f.text, f.date, u.full_name "
                + "FROM feedback f JOIN Student u ON f.id_student = u.id "
                + "ORDER BY f.date DESC";
        stm = connection.prepareStatement(strSQL);
        rs = stm.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            int studentId = rs.getInt("id_student");
            int courseId = rs.getInt("id_course");
            String feedbackText = rs.getString("text");
            String feedbackDate = rs.getString("date");
            String studentName = rs.getString("full_name");

            FeedBack f = new FeedBack(id, studentId, courseId, feedbackText, feedbackDate, studentName);
            data.add(f);
        }
    } catch (Exception e) {
        System.out.println("getTop3Feedbacks: " + e.getMessage());
    }
    return data;
}

}
