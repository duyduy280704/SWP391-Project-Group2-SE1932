/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;

/**
 *
 * @author HP
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Users;

/**
 *
 * @author MY PC
 */
public class UserDAO extends DBContext {

    public List<Users> getAllUsers() {
        List<Users> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setSdt(rs.getString("sdt"));
                user.setAddress(rs.getString("address"));
                user.setIdRole(rs.getInt("id_role"));
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println("getAllUsers: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stm);
        }
        return users;
    }

    public Users getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE id = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            rs = stm.executeQuery();

            if (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setSdt(rs.getString("sdt"));
                user.setAddress(rs.getString("address"));
                user.setIdRole(rs.getInt("id_role"));
                return user;
            }
        } catch (Exception e) {
            System.out.println("getUserById: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stm);
        }
        return null;
    }

    public Users getUserByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();

            if (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setSdt(rs.getString("sdt"));
                user.setAddress(rs.getString("address"));
                user.setIdRole(rs.getInt("id_role"));
                return user;
            }
        } catch (Exception e) {
            System.out.println("getUserByEmail: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stm);
        }
        return null;
    }

    public boolean addUser(Users user) {
        String sql = "INSERT INTO Users (username, account, password, email, sdt, address, id_role) VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getAccount());
            stm.setString(3, user.getPassword());
            stm.setString(4, user.getEmail());
            stm.setString(5, user.getSdt());
            stm.setString(6, user.getAddress());
            stm.setInt(7, user.getIdRole());

            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("addUser: " + e.getMessage());
        } finally {
            closePreparedStatement(stm);
        }
        return false;
    }

    public void register(String username, String account, String password, String email, String sdt, String address, int idRole) {
        String sql = "INSERT INTO Users (username, account, password, email, sdt, address, id_role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, account);
            stm.setString(3, password);
            stm.setString(4, email);
            stm.setString(5, sdt);
            stm.setString(6, address);
            stm.setInt(7, idRole);
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("register: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closePreparedStatement(stm);
        }
    }

    public boolean checkExistEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.out.println("checkExistEmail: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stm);
        }
        return false;
    }

    public boolean updateUser(Users user) {
        String sql = "UPDATE Users SET username = ?, account = ?, password = ?, email = ?, sdt = ?, address = ?, id_role = ? WHERE id = ?";

        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, user.getUsername());
            stm.setString(2, user.getAccount());
            stm.setString(3, user.getPassword());
            stm.setString(4, user.getEmail());
            stm.setString(5, user.getSdt());
            stm.setString(6, user.getAddress());
            stm.setInt(7, user.getIdRole());
            stm.setInt(8, user.getId());

            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("updateUser: " + e.getMessage());
        } finally {
            closePreparedStatement(stm);
        }
        return false;
    }

    public boolean updatePassword(String email, String newPassword) {
        String sql = "UPDATE Users SET password = ? WHERE email = ?";

        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, newPassword);
            stm.setString(2, email);

            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("updatePassword: " + e.getMessage());
        } finally {
            closePreparedStatement(stm);
        }
        return false;
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE id = ?";

        PreparedStatement stm = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);

            return stm.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("deleteUser: " + e.getMessage());
        } finally {
            closePreparedStatement(stm);
        }
        return false;
    }

    public Users checkLogin(String email, String password) {
        String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            rs = stm.executeQuery();

            if (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setSdt(rs.getString("sdt"));
                user.setAddress(rs.getString("address"));
                user.setIdRole(rs.getInt("id_role"));
                return user;
            }
        } catch (Exception e) {
            System.out.println("checkLogin: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stm);
        }
        return null;
    }

    public List<Users> getAdmins() {
        List<Users> admins = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE id_role = 1";

        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            stm = connection.prepareStatement(sql);
            rs = stm.executeQuery();

            while (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setAccount(rs.getString("account"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setSdt(rs.getString("sdt"));
                user.setAddress(rs.getString("address"));
                user.setIdRole(rs.getInt("id_role"));
                admins.add(user);
            }
        } catch (Exception e) {
            System.out.println("getAdmins: " + e.getMessage());
        } finally {
            closeResultSet(rs);
            closePreparedStatement(stm);
        }
        return admins;
    }

    // Helper methods for resource cleanup
    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                System.out.println("closeResultSet: " + e.getMessage());
            }
        }
    }

    private void closePreparedStatement(PreparedStatement stm) {
        if (stm != null) {
            try {
                stm.close();
            } catch (Exception e) {
                System.out.println("closePreparedStatement: " + e.getMessage());
            }
        }
    }
}
