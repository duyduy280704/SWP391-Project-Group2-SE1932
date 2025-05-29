package models;

import dal.DBContext;
import java.sql.*;
//Huyền-check mail tồn tại và update mật khẩu mới

public class UserDAO extends DBContext {

    public String checkExistEmail(String email) {
        String[] tables = {"Admin_staff", "Student", "Teacher"};
        for (String table : tables) {
            String sql = "SELECT id FROM " + table + " WHERE email = ?";
            try (PreparedStatement stm = connection.prepareStatement(sql)) {
                stm.setString(1, email);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        return table;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void updatePassword(String tableName, String email, String newPassword) throws SQLException {
        String sql = "UPDATE " + tableName + " SET password = ? WHERE email = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, newPassword);
            stm.setString(2, email);
            stm.executeUpdate();
        }
    }
}