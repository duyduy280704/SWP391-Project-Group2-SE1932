package models;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO extends DBContext {

    private PreparedStatement stm;
    private ResultSet rs;

    public Teachers getProfile(String phone) {
        try {
            String sql = "SELECT * FROM Teacher WHERE phone = ? ";
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            
            rs = stm.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("full_name");
                String e = rs.getString("email");
                String pas = rs.getString("password");
                String birt = rs.getString("birth_date");
                String gen = rs.getString("gender");
                String ex = rs.getString("Expertise");
                String pic = rs.getString("picture");
                String role = rs.getString("role_id");
                String idcourse = rs.getString("id_type_course");
                String yearexp = rs.getString("years_of_experience");
                String phones=rs.getString("phone");
                Teachers t = new Teachers(id, name, e, pas, birt, gen, ex, pic, role, idcourse, yearexp,phones);
                return t;
            }
        } catch (SQLException e) {
            System.out.println("getProfile Teacher: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }
    public boolean verifyPassword(String phone, String oldPassword) {
        try {
            String sql = "SELECT password FROM Teacher WHERE phone = ? UNION SELECT password FROM Student WHERE phone = ? UNION SELECT password FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            stm.setString(2, phone);
            stm.setString(3, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword != null && storedPassword.equals(oldPassword); // Simple comparison (consider hashing in production)
            }
        } catch (SQLException e) {
            System.out.println("verifyPassword: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }

    // New method to update password
   public boolean updatePassword(String phone, String newPassword) {
        try {
            String checkSql = "SELECT role_id FROM Teacher WHERE phone = ? UNION SELECT Role_id FROM Student WHERE phone = ? UNION SELECT role_id FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(checkSql);
            stm.setString(1, phone);
            stm.setString(2, phone);
            stm.setString(3, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                int roleId = rs.getInt(1);
                String updateSql = "";
                if (roleId == 2) { // Teacher
                    updateSql = "UPDATE Teacher SET password = ? WHERE phone = ?";
                } else if (roleId == 1) { // Student
                    updateSql = "UPDATE Student SET password = ? WHERE phone = ?";
                } else { // Admin_staff (role_id 3 or 4)
                    updateSql = "UPDATE Admin_staff SET password = ? WHERE phone = ?";
                }
                stm = connection.prepareStatement(updateSql);
                stm.setString(1, newPassword);
                stm.setString(2, phone);
                int rowsAffected = stm.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.out.println("updatePassword: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }

    // New method to update email (if needed)
   public boolean updatePhone(String oldphone, String newPhone) {
        try {
            String checkSql = "SELECT role_id FROM Teacher WHERE phone = ? UNION SELECT Role_id FROM Student WHERE phone = ? UNION SELECT role_id FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(checkSql);
            stm.setString(1, oldphone);
            stm.setString(2, oldphone);
            stm.setString(3, oldphone);
            rs = stm.executeQuery();
            if (rs.next()) {
                int roleId = rs.getInt(1);
                String updateSql = "";
                if (roleId == 2) { // Teacher
                    updateSql = "UPDATE Teacher SET phone = ? WHERE phone = ?";
                } else if (roleId == 1) { // Student
                    updateSql = "UPDATE Student SET phone = ? WHERE phone = ?";
                } else { // Admin_staff (role_id 3 or 4)
                    updateSql = "UPDATE Admin_staff SET phone = ? WHERE phone = ?";
                }
                stm = connection.prepareStatement(updateSql);
                stm.setString(1, newPhone);
                stm.setString(2, oldphone);
                int rowsAffected = stm.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.out.println("updateEmail: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }
   public Object getAccountByPhone(String phone) {
        try {
            String sql = "SELECT id, full_name, email, password, birth_date, gender, role_id, " +
            "NULL AS address, Expertise, picture, id_type_course, years_of_experience, phone " +
            "FROM Teacher WHERE phone = ? " +
            "UNION " +
            "SELECT id, full_name, email, password, birth_date, gender, Role_id AS role_id, " +
            "address, NULL AS Expertise, picture, NULL AS id_type_course, NULL AS years_of_experience, phone " +
            "FROM Student WHERE phone = ? " +
            "UNION " +
            "SELECT id, full_name, email, password, birth_date, gender, role_id, " +
            "NULL AS address, NULL AS Expertise, NULL AS picture, NULL AS id_type_course, NULL AS years_of_experience, phone " +
            "FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            stm.setString(2, phone);
            stm.setString(3, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String fullName = rs.getString("full_name");
                String e = rs.getString("email");
                String pas = rs.getString("password");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String roleId = rs.getString("role_id");
                String RoleId=rs.getString("Role_id");
                String address = rs.getString("address"); 
                String expertise = rs.getString("Expertise"); 
                String picture = rs.getString("picture"); 
                String idTypeCourse = rs.getString("id_type_course"); 
                String yearsOfExperience = rs.getString("years_of_experience"); 
                String phones=rs.getString("phone");
                 
                if ("2".equals(roleId)) {
                    return new Teachers(id, fullName, e, pas, birthDate, gender, expertise, picture, roleId, idTypeCourse, yearsOfExperience,phones);
                } else if ("1".equals(roleId)) {
                    return new Students(id, fullName, e, pas, birthDate, gender, address, RoleId,phones,picture);
                } else {
                    return new AdminStaffs(id, fullName, e, pas, birthDate, gender, roleId,phones);
                }
            }
        } catch (SQLException e) {
            System.out.println("getAccountByEmail: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }
   

    public Students getProfileStudent(String phone) {
        try {
            String sql = "SELECT * FROM Student WHERE phone = ? " ;
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
           
            rs = stm.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("full_name");
                String e = rs.getString("email");
                String pas = rs.getString("password");
                String birt = rs.getString("birth_date");
                String gen = rs.getString("gender");
                String add = rs.getString("address");
                String role = rs.getString("Role_id");
                String phones=rs.getString("phone");
                 String pic=rs.getString("picture");
                Students s = new Students(id, name, e, pas, birt, gen, add, role,phones,pic);
                return s;
            }
        } catch (SQLException e) {
            System.out.println("getProfile Student: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    public Staff getProfileStaff(String phone) {
        try {
            String sql = "SELECT * FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            
            rs = stm.executeQuery();
            if (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("full_name");
                String e = rs.getString("email");
                String pas = rs.getString("password");
                String birt = rs.getString("birth_date");
                String gen = rs.getString("gender");
                String role = rs.getString("role_id");
                String phones=rs.getString("phone");
                Staff s = new Staff(id, name, e, pas, birt, gen, role,phones);
                return s;
            }
        } catch (SQLException e) {
            System.out.println("getProfile Staff: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    public boolean updateProfile(String phone, String fullName, String gender, String address, String birthDate, String expertise, Integer yearsOfExperience, String picturePath,String email) {
        try {
            String checkSql = "SELECT role_id FROM Teacher WHERE phone = ? UNION SELECT Role_id FROM Student WHERE phone = ? UNION SELECT role_id FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(checkSql);
            stm.setString(1, phone);
            stm.setString(2, phone);
            stm.setString(3, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                int roleId = rs.getInt(1);
                String updateSql = "";
                if (roleId == 2) { // Teacher
                    updateSql = "UPDATE Teacher SET full_name = ?, gender = ?, birth_date = ?, Expertise = ?, years_of_experience = ?, picture = ?,email=? WHERE phone  = ?";
                    stm = connection.prepareStatement(updateSql);
                    stm.setString(1, fullName);
                    stm.setString(2, gender);
                    stm.setString(3, birthDate);
                    stm.setString(4, expertise);
                    stm.setInt(5, yearsOfExperience != null ? yearsOfExperience : 0);
                    stm.setString(6, picturePath != null ? picturePath : "");
                    stm.setString(8, phone);
                    stm.setString(7, email);
                } else if (roleId == 1) { // Student
                    updateSql = "UPDATE Student SET full_name = ?, gender = ?, birth_date = ?, address = ? ,picture=? email=? WHERE phone = ?";
                    stm = connection.prepareStatement(updateSql);
                    stm.setString(1, fullName);
                    stm.setString(2, gender);
                    stm.setString(3, birthDate);
                    stm.setString(4, address);
                    stm.setString(7, phone);
                    stm.setString(6, email);
                    stm.setString(5, picturePath != null ? picturePath : "");
                } else { // Admin_staff (role_id 3 or 4)
                    updateSql = "UPDATE Admin_staff SET full_name = ?, gender = ?, birth_date = ? WHERE phone = ?";
                    stm = connection.prepareStatement(updateSql);
                    stm.setString(1, fullName);
                    stm.setString(2, gender);
                    stm.setString(3, birthDate);
                    stm.setString(5, phone);
                    stm.setString(4, email);
                    
                }
                int rowsAffected = stm.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            System.out.println("updateProfile: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }

    private void closeResources() {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { System.out.println("close ResultSet: " + e.getMessage()); }
        }
        if (stm != null) {
            try { stm.close(); } catch (SQLException e) { System.out.println("close Statement: " + e.getMessage()); }
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("closeConnection: " + e.getMessage());
            }
        }
    }
}