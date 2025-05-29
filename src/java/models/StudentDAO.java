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
public class StudentDAO extends DBContext{
    PreparedStatement stm; 
    ResultSet rs; 
    
    
    public Students checkLogin(String email, String password) {
        try{
           String strSQL="select id,password,full_name,email,birth_date,gender,address,Role_id"
                   + " from Student"
                   + " where email =? and password=?";
                   stm=connection.prepareStatement(strSQL);
                   stm.setString(1, email);
                   stm.setString(2, password);
                   rs=stm.executeQuery();
            while(rs.next()){
                   String id=String.valueOf(rs.getString("id"));
                   String pwd = rs.getString("password");
                String fullName = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String birthDate = rs.getString("birth_date"); 
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String roleId = String.valueOf(rs.getInt("Role_id"));
                Students student = new Students(id, pwd, fullName, emailFromDB, birthDate, gender, address, roleId);
                return student;
            }                   
        }catch(Exception e){
            System.out.println("checklogin" +e.getMessage());
        }
       return null; 
    }
    
}
