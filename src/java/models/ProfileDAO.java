package models;

import dal.DBContext;
import java.sql.*;

public class ProfileDAO extends DBContext {

    // ==== STUDENT ====
    public Students getStudentByPhone(String phone) {
        String sql = "SELECT * FROM Student WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Students(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        rs.getBytes("picture"),
                        rs.getString("address"),
                        String.valueOf(rs.getInt("Role_id")),
                        rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Students getStudentById(String id) {
        String sql = "SELECT * FROM Student WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Students(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        rs.getBytes("picture"),
                        rs.getString("address"),
                        String.valueOf(rs.getInt("Role_id")),
                        rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateStudent(Students s) {
        String sql = "UPDATE Student SET full_name=?, email=?, birth_date=?, gender=?, address=?, picture=? WHERE phone=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getBirthdate());
            ps.setString(4, s.getGender());
            ps.setString(5, s.getAddress());
            ps.setBytes(6, s.getPic());
            ps.setString(7, s.getPhone());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateStudentAccount(Students s, String oldPassword, String newPassword, String newPhone) {
        if (!oldPassword.equals(s.getPassword())) return false;
        String sql = "UPDATE Student SET phone=?, password=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPhone);
            ps.setString(2, newPassword);
            ps.setInt(3, Integer.parseInt(s.getId()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==== TEACHER ====
    public Teachers getTeacherByPhone(String phone) {
        String sql = "SELECT * FROM Teacher WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Teachers(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        rs.getString("Expertise"),
                        rs.getBytes("picture"),
                        String.valueOf(rs.getInt("role_id")),
                        String.valueOf(rs.getInt("id_type_course")),
                        String.valueOf(rs.getInt("years_of_experience")),
                        rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Teachers getTeacherById(String id) {
        String sql = "SELECT * FROM Teacher WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Teachers(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        rs.getString("Expertise"),
                        rs.getBytes("picture"),
                        String.valueOf(rs.getInt("role_id")),
                        String.valueOf(rs.getInt("id_type_course")),
                        String.valueOf(rs.getInt("years_of_experience")),
                        rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateTeacher(Teachers t) {
        String sql = "UPDATE Teacher SET full_name=?, email=?, birth_date=?, gender=?, Expertise=?, id_type_course=?, years_of_experience=?, picture=? WHERE phone=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, t.getName());
            ps.setString(2, t.getEmail());
            ps.setString(3, t.getBirthdate());
            ps.setString(4, t.getGender());
            ps.setString(5, t.getExp());
            ps.setString(6, t.getCourse());
            ps.setString(7, t.getYear());
            ps.setBytes(8, t.getPic());
            ps.setString(9, t.getPhone());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateTeacherAccount(Teachers t, String oldPassword, String newPassword, String newPhone) {
        if (!oldPassword.equals(t.getPassword())) return false;
        String sql = "UPDATE Teacher SET phone=?, password=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPhone);
            ps.setString(2, newPassword);
            ps.setInt(3, Integer.parseInt(t.getId()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==== ADMIN STAFF ====
    public AdminStaffs getAdminStaffByPhone(String phone) {
        String sql = "SELECT * FROM Admin_staff WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new AdminStaffs(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        String.valueOf(rs.getInt("role_id")),
                        rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public AdminStaffs getAdminStaffById(String id) {
        String sql = "SELECT * FROM Admin_staff WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new AdminStaffs(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("full_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("birth_date"),
                        rs.getString("gender"),
                        String.valueOf(rs.getInt("role_id")),
                        rs.getString("phone")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAdminStaff(AdminStaffs a) {
        String sql = "UPDATE Admin_staff SET full_name=?, email=?, birth_date=?, gender=? WHERE phone=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, a.getName());
            ps.setString(2, a.getEmail());
            ps.setString(3, a.getBirthdate());
            ps.setString(4, a.getGender());
            ps.setString(5, a.getPhone());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateAdminStaffAccount(AdminStaffs a, String oldPassword, String newPassword, String newPhone) {
        if (!oldPassword.equals(a.getPassword())) return false;
        String sql = "UPDATE Admin_staff SET phone=?, password=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPhone);
            ps.setString(2, newPassword);
            ps.setInt(3, Integer.parseInt(a.getId()));
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean checkStudentOldPassword(String id, String oldPassword) {
    String sql = "SELECT 1 FROM Student WHERE id = ? AND password = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(id));
        ps.setString(2, oldPassword);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public boolean checkTeacherOldPassword(String id, String oldPassword) {
    String sql = "SELECT 1 FROM Teacher WHERE id = ? AND password = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(id));
        ps.setString(2, oldPassword);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

public boolean checkAdminStaffOldPassword(String id, String oldPassword) {
    String sql = "SELECT 1 FROM Admin_staff WHERE id = ? AND password = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, Integer.parseInt(id));
        ps.setString(2, oldPassword);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}        
