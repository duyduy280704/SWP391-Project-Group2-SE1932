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
public class TeacherDAO extends DBContext{
    PreparedStatement stm; 
    ResultSet rs; 
    
    public ArrayList<Teachers> getTeachers() {

        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "select * from Teachers s join Roles r on s.id_role = r.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String account = rs.getString(3);
                String password = rs.getString(4);
                String exp = rs.getString(5);
                String email = rs.getString(6);
                String sdt = rs.getString(7);
                String pic = rs.getString(8);
                String address = rs.getString(9);
                String role = rs.getString(12);
                

                Teachers p = new Teachers(id, name, account, password, exp, email, sdt, pic, address, role);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return data;
    }
    
    public Teachers getTeacherById(String id) {
        try {
            String strSQL = "select * from Teachers where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                String name = rs.getString(2);
                String account = rs.getString(3);
                String password = rs.getString(4);
                String exp = rs.getString(5);
                String email = rs.getString(6);
                String sdt = rs.getString(7);
                String pic = rs.getString(8);
                String address = rs.getString(9);
                String role = rs.getString(10);

                Teachers p = new Teachers(id, name, account, password, exp, email, sdt, pic, address, role);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return null;
    }

    public void update(Teachers s) {
        try {
            String strSQL = "update Teachers set username=?,account=?,password=?,expertise=?,email=?,sdt=?,picture=?,address=? where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, s.name);
            stm.setString(2, s.account);
            stm.setString(3, s.password);
            stm.setString(4, s.exp);
            stm.setString(5, s.email);
            stm.setString(6, s.sdt);
            stm.setString(7, s.pic);
            stm.setString(8, s.address);
            stm.setInt(9, Integer.parseInt(s.id));
            
            stm.execute();
        } catch (Exception e) {
            System.out.println("update:" + e.getMessage());
        }
    }

    public void add(Teachers s) {
    try {
        // Kiểm tra nếu account đã tồn tại
        if (isAccountExist(s.account)) {
            System.out.println("Tài khoản '" + s.account + "' đã tồn tại. Không thể thêm mới.");
            return; // Không thêm dữ liệu nếu account đã tồn tại
        }

        String strSQL = "INSERT INTO Teachers(username, account, password, expertise, email, sdt, picture, address, id_role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        stm = connection.prepareStatement(strSQL);
        stm.setString(1, s.name);
        stm.setString(2, s.account);
        stm.setString(3, s.password);
        stm.setString(4, s.exp);
        stm.setString(5, s.email);
        stm.setString(6, s.sdt);
        stm.setString(7, s.pic);
        stm.setString(8, s.address);
        stm.setInt(9, Integer.parseInt(s.role));
        stm.executeUpdate(); // dùng executeUpdate thay vì execute
        System.out.println("Thêm học sinh thành công!");

    } catch (Exception e) {
        System.out.println("add: " + e.getMessage());
    }
}
    
    public boolean isAccountExist(String account) {

        try {
            String strSQL = "SELECT COUNT(*) FROM Teachers WHERE account = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, account);
            rs = stm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Check account: " + account + " - Kết quả: " + count);
                return count > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL khi kiểm tra account: " + e.getMessage());
        }
        return false;
    }

    public void delete(String id) {
     try {
            String strSQL = "delete from Teachers where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);

            stm.execute();

        } catch (Exception e) {
            System.out.println("add:" + e.getMessage());
        }
    }
}
