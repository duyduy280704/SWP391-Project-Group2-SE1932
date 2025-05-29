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
public class TeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Teachers> get4Teachers() {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "SELECT TOP 4 t.id, t.full_name, t.password, t.email, t.birth_date, "
                    + "t.gender, t.Expertise, t.picture, r.name AS role_name "
                    + "FROM Teacher t JOIN role r ON t.role_id = r.id";

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                String picture = rs.getString("picture");
                String role = rs.getString("role_name");

                Teachers t = new Teachers(id, fullName, email, birthDate, gender, expertise, picture);
                data.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public ArrayList<Teachers> getTeachers() {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "SELECT t.id, t.full_name, t.password, t.email, t.birth_date, "
                    + "t.gender, t.Expertise, t.picture, r.name AS role_name "
                    + "FROM Teacher t JOIN role r ON t.role_id = r.id";

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullName = rs.getString("full_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                String picture = rs.getString("picture");
                String role = rs.getString("role_name");

                Teachers t = new Teachers(id, fullName, email, birthDate, gender, expertise, picture);
                data.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

}
