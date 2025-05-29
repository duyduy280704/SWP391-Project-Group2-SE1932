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
public class CourseDAO extends DBContext{
    PreparedStatement stm; 
    ResultSet rs; 
    
    public ArrayList<Courses> getCourses() {

        ArrayList<Courses> data = new ArrayList<>();
        try {
            String strSQL = "select * from Courses ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type = rs.getString(3);
                String description = rs.getString(4);
                String fee = rs.getString(5);

                Courses p = new Courses(id, name, type, description, fee);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getCourses" + e.getMessage());

        }
        return data;
    }
}
