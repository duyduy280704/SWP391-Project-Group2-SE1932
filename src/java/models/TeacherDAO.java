/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;

//Dương_Homepage
public class TeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Teachers> get4Teachers() {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "SELECT TOP 4 t.id, t.full_name, t.password, t.email, t.birth_date, t.years_of_experience, "
                    + "t.gender, t.Expertise, t.picture, r.name AS role_name "
                    + "FROM Teacher t JOIN role r ON t.role_id = r.id "
                    + "ORDER BY t.years_of_experience DESC"
                    ;

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                String picture = rs.getString("picture");
                String role = rs.getString("role_name");

                Teachers t = new Teachers(id, fullName, email, password, birthDate, gender, expertise, picture, role);
                data.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    //Huyền-checklogin của teacher
    public Teachers checkLogin(String email, String password) {
        try {
            String strSQL = "SELECT id, password, full_name, email, birth_date, gender, Expertise, picture, role_id "
                    + "FROM Teacher "
                    + "WHERE email = ? AND password = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, email);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String pwd = rs.getString("password");
                String fullName = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                String picture = rs.getString("picture");
                String roleId = String.valueOf(rs.getInt("role_id"));
                Teachers teacher = new Teachers(id, pwd, fullName, emailFromDB, birthDate, gender, expertise, picture, roleId);
                return teacher;
            }
        } catch (Exception e) {
            System.out.println("checkLogin: " + e.getMessage());
        }
        return null;
    }

    //Quang
    public ArrayList<Teachers> getTeachers() {

        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "select * from Teacher s join Role r on s.role_id = r.id join type_course t on s.id_type_course = t.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String birthdate = rs.getString(5);
                String gender = rs.getString(6);
                String exp = rs.getString(7);
                String pic = rs.getString(8);
                String role = rs.getString(13);
               

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return data;
    }

    public ArrayList<TypeCourse> getCourseType() {
        ArrayList<TypeCourse> data = new ArrayList<>();
        try {
            String strSQL = "select * from type_course";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String code = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);

                TypeCourse c = new TypeCourse(code, name);
                data.add(c);
            }
        } catch (Exception e) {
            System.out.println("getCourseType" + e.getMessage());

        }
        return data;
    }

 
}
