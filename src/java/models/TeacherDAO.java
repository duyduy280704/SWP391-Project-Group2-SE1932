package models;

import dal.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TeacherDAO extends DBContext {
    PreparedStatement stm;
    ResultSet rs;

    public Teachers checkLogin(String email, String password) {
        try {
            String strSQL = "SELECT id, password, full_name, email, birth_date, gender, Expertise, picture, role_id " +
                           "FROM Teacher " +
                           "WHERE email = ? AND password = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, email);
            stm.setString(2, password); 
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id")) ;
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
}