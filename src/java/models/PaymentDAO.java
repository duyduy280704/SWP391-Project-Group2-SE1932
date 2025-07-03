/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;

/**
 *
 * @author Dwight
 */
public class PaymentDAO extends DBContext {

    public void insert(Payment p) {
        String sql = "INSERT INTO Payment (id_student, id_course, status, date, id_sale) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getIdStudent());
            ps.setInt(2, p.getIdCourse());
            ps.setString(3, p.getStatus());
            ps.setString(4, p.getDate());

            if (p.getIdSale() != null) {
                ps.setInt(5, p.getIdSale());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("insert Payment: " + e.getMessage());
        }
    }
}
