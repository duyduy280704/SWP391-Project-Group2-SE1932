package models;

import dal.DBContext;
import java.sql.*;
//Huyền-check mail tồn tại và update mật khẩu mới

public class UserDAO extends DBContext {

    public String checkExistPhone(String phone) {
        String[] tables = {"Admin_staff", "Student", "Teacher"};
        for (String table : tables) {
            String sql = "SELECT id FROM " + table + " WHERE phone = ?";
            try (PreparedStatement stm = connection.prepareStatement(sql)) {
                stm.setString(1, phone);
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

    public void updatePassword(String tableName, String phone, String newPassword) throws SQLException {
        String sql = "UPDATE " + tableName + " SET password = ? WHERE phone = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, newPassword);
            stm.setString(2, phone);
            stm.executeUpdate();
        }
    }
}