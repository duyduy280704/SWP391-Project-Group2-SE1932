/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *
 * @author HP
 */
public class AdminStaffDAO extends DBContext {
     PreparedStatement stm;
     ResultSet rs;
     //Huyền-checklogin của adminstaff
     public AdminStaffs checkLogin(String email,String password){
         try{
             String strSQL="SELECT id, full_name, email,birth_date,gender, password,  role_id FROM Admin_staff WHERE email = ? AND password = ?";
             stm=connection.prepareStatement(strSQL);
             stm.setString(1, email);
             stm.setString(2, password);
             rs=stm.executeQuery();
             while(rs.next()){
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String em = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String pass = rs.getString("password");
                String roleId = String.valueOf(rs.getInt("role_id"));
                AdminStaffs a = new AdminStaffs(id, fullName, em, birthDate, gender, pass, roleId);
                return a;
                 
             }
             

         }catch (Exception e){
             System.out.println(""+e.getMessage());
         }
         return null;
     }
   
}