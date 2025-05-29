package models;

import dal.DBContext;
import java.sql.*;

public class UserDAO extends DBContext {

    
    PreparedStatement stm;
    ResultSet rs;

   
    public String checkExistEmail(String email) {
        String[] tables = {"Admin_staff", "Student", "Teacher"};
        for (String table : tables) {
            try {
                String sql = "SELECT id FROM " + table + " WHERE email = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, email);
                rs = stm.executeQuery();
                if (rs.next()) {
                    return table;
                }
            } catch (SQLException e) {
                System.out.println("checkExistEmail error: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stm != null) stm.close();
                } catch (SQLException ex) {
                    System.out.println("close resources error: " + ex.getMessage());
                }
            }
        }
        return null;
    }

    
    public void updatePassword(String tableName, String email, String newPassword) {
        try {
            String sql = "UPDATE " + tableName + " SET password = ? WHERE email = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setString(2, email);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println("updatePassword error: " + e.getMessage());
        } finally {
            try {
                if (stm != null) stm.close();
            } catch (SQLException ex) {
                System.out.println("close statement error: " + ex.getMessage());
            }
        }
    }
}
