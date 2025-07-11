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

    public AdminStaffs checkLogin(String phone, String password) {
        try {
            String strSQL = "SELECT id, full_name, email,birth_date,gender, password,  role_id,phone FROM Admin_staff WHERE phone = ? AND password = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String em = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String pass = rs.getString("password");
                String roleId = String.valueOf(rs.getInt("role_id"));
                String phones = rs.getString("phone");
                AdminStaffs a = new AdminStaffs(id, fullName, em, birthDate, gender, pass, roleId, phones);
                return a;

            }

        } catch (Exception e) {
            System.out.println("" + e.getMessage());
        }
        return null;
    }

    public AdminStaffs getByPhone(String phone) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, role_id, phone "
                    + "FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthdate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String role = String.valueOf(rs.getInt("role_id"));
                String phones = rs.getString("phone");
                return new AdminStaffs(id, name, email, password, birthdate, gender, role, phones);
            }
        } catch (Exception e) {
            System.out.println("getByPhone: " + e.getMessage());
        }
        return null;
    }

    public AdminStaffs getByEmail(String email) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, role_id, phone "
                    + "FROM Admin_staff WHERE email = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, email);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String password = rs.getString("password");
                String birthdate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String role = String.valueOf(rs.getInt("role_id"));
                String phone = rs.getString("phone");
                return new AdminStaffs(id, name, emailFromDB, password, birthdate, gender, role, phone);
            }
        } catch (Exception e) {
            System.out.println("getByEmail: " + e.getMessage());
        }
        return null;
    }

    public boolean updatePassword(String phone, String newPassword) {
        if (phone == null || newPassword == null) {
            System.out.println("updatePassword: Phone hoặc mật khẩu null");
            return false;
        }
        try {
            String strSQL = "UPDATE Admin_staff SET password = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newPassword);
            stm.setString(2, phone);
            int rowsAffected = stm.executeUpdate();
            System.out.println("updatePassword: Cập nhật " + rowsAffected + " hàng cho phone " + phone);
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("updatePassword: Lỗi SQL - " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (Exception e) {
                System.err.println("updatePassword: Lỗi khi đóng statement - " + e.getMessage());
            }
        }
    }

    public boolean isPhoneExist(String phone) {
        String sql = "SELECT 1 FROM Admin_staff WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExist(String email) {
        String sql = "SELECT 1 FROM Admin_staff WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
