package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dwight
 */
//Dương_Homepage
import dal.DBContext;
import java.sql.*;
import models.PreRegistration;

public class PreRegistrationDAO extends DBContext {

    public boolean insertPreRegistration(PreRegistration preReg) {
        String sql = "INSERT INTO PreRegistrations (full_name, email, birth_date, gender, address, course_id, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, preReg.getFull_name());
            ps.setString(2, preReg.getEmail());
            ps.setString(3, preReg.getBirth_date());
            ps.setString(4, preReg.getGender());
            ps.setString(5, preReg.getAddress());
            ps.setInt(6, preReg.getCourse_id());
            ps.setString(7, preReg.getStatus());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("insertPreRegistration: " + e.getMessage());
            return false;
        }
    }

}
