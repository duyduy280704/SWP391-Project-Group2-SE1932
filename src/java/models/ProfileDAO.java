/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProfileDAO extends DBContext {

    // Khai báo các thành phần xử lý Database
    PreparedStatement stm; // Thực hiện câu lệnh SQL
    ResultSet rs; // Lưu trữ và xử lý dữ liệu

    // Teacher methods
    /**
     * Lấy thông tin hồ sơ của một giáo viên dựa trên số điện thoại.
     */
    public Teachers getTeacherByPhone(String phone) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, role_id, "
                    + "Expertise, picture, id_type_course, years_of_experience, phone "
                    + "FROM Teacher WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String roleId = String.valueOf(rs.getInt("role_id"));
                String expertise = rs.getString("Expertise");
                byte[] picture = rs.getBytes("picture");
                String idTypeCourse = rs.getString("id_type_course");
                String yearsOfExperience = rs.getString("years_of_experience");
                return new Teachers(id, fullName, email, password, birthDate, gender, expertise, picture, roleId, idTypeCourse, yearsOfExperience, phone);
            }
        } catch (Exception e) {
            System.out.println("getTeacherByPhone: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    /**
     * Lấy danh sách tất cả giáo viên trong cơ sở dữ liệu.
     */
    public ArrayList<Teachers> getAllTeachers() {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, role_id, "
                    + "Expertise, picture, id_type_course, years_of_experience, phone "
                    + "FROM Teacher";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String roleId = String.valueOf(rs.getInt("role_id"));
                String expertise = rs.getString("Expertise");
                byte[] picture = rs.getBytes("picture");
                String idTypeCourse = rs.getString("id_type_course");
                String yearsOfExperience = rs.getString("years_of_experience");
                String phone = rs.getString("phone");
                Teachers teacher = new Teachers(id, fullName, email, password, birthDate, gender, expertise, picture, roleId, idTypeCourse, yearsOfExperience, phone);
                data.add(teacher);
            }
        } catch (Exception e) {
            System.out.println("getAllTeachers: " + e.getMessage());
        } finally {
            closeResources();
        }
        return data;
    }

    /**
     * Cập nhật thông tin cá nhân của giáo viên (tên, email, ngày sinh, giới
     * tính, chuyên môn, hình ảnh, v.v.).
     */
    public void updateTeacher(Teachers t) {
        try {
            String strSQL = "UPDATE Teacher SET full_name = ?, email = ?, birth_date = ?, gender = ?, "
                    + "Expertise = ?, picture = ?, id_type_course = ?, years_of_experience = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, t.getName());
            stm.setString(2, t.getEmail());
            stm.setString(3, t.getBirthdate());
            stm.setString(4, t.getGender());
            stm.setString(5, t.getExp());
            stm.setBytes(6, t.getPic() != null ? t.getPic() : getCurrentTeacherPicture(t.getPhone()));
            stm.setString(7, t.getCourse());
            stm.setString(8, t.getYear());
            stm.setString(9, t.getPhone());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateTeacher: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Cập nhật số điện thoại và/hoặc mật khẩu của giáo viên.
     */
    public boolean updateTeacherCredentials(String oldPhone, String newPhone, String newPassword) {
        try {
            String strSQL = "UPDATE Teacher SET phone = ?, password = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newPhone != null ? newPhone : oldPhone);
            stm.setString(2, newPassword != null ? newPassword : getCurrentTeacherPassword(oldPhone));
            stm.setString(3, oldPhone);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("updateTeacherCredentials: " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    /**
     * Xác minh mật khẩu của giáo viên dựa trên số điện thoại.
     */
    public boolean verifyTeacherPassword(String phone, String password) {
        try {
            String strSQL = "SELECT password FROM Teacher WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword != null && storedPassword.equals(password);
            }
        } catch (Exception e) {
            System.out.println("verifyTeacherPassword: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }

    // Student methods
    /**
     * Lấy thông tin hồ sơ của một học sinh dựa trên số điện thoại.
     */
    public Students getStudentByPhone(String phone) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, Role_id, picture, address, phone "
                    + "FROM Student WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String roleId = String.valueOf(rs.getInt("Role_id"));
                byte[] picture = rs.getBytes("picture");
                String address = rs.getString("address");
                return new Students(id, fullName, email, password, birthDate, gender,picture, address, roleId, phone);
            }
        } catch (Exception e) {
            System.out.println("getStudentByPhone: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    /**
     * Lấy danh sách tất cả học sinh trong cơ sở dữ liệu.
     */
    public ArrayList<Students> getAllStudents() {
        ArrayList<Students> data = new ArrayList<>();
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, Role_id, picture, address, phone "
                    + "FROM Student";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String roleId = String.valueOf(rs.getInt("Role_id"));
                byte[] picture = rs.getBytes("picture");
                String address = rs.getString("address");
                String phone = rs.getString("phone");
                Students student = new Students(id, fullName, email, password, birthDate, gender,picture, address, roleId, phone);
                data.add(student);
            }
        } catch (Exception e) {
            System.out.println("getAllStudents: " + e.getMessage());
        } finally {
            closeResources();
        }
        return data;
    }

    /**
     * Cập nhật thông tin cá nhân của học sinh (tên, email, ngày sinh, giới
     * tính, địa chỉ, hình ảnh).
     */
    public void updateStudent(Students s) {
        try {
            String strSQL = "UPDATE Student SET full_name = ?, email = ?, birth_date = ?, gender = ?, address = ?, picture = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, s.getName());
            stm.setString(2, s.getEmail());
            stm.setString(3, s.getBirthdate());
            stm.setString(4, s.getGender());
            stm.setString(5, s.getAddress());
            stm.setBytes(6, s.getPic() != null ? s.getPic() : getCurrentStudentPicture(s.getPhone()));
            stm.setString(7, s.getPhone());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateStudent: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Cập nhật số điện thoại và/hoặc mật khẩu của học sinh.
     */
    public boolean updateStudentCredentials(String oldPhone, String newPhone, String newPassword) {
        try {
            String strSQL = "UPDATE Student SET phone = ?, password = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newPhone != null ? newPhone : oldPhone);
            stm.setString(2, newPassword != null ? newPassword : getCurrentStudentPassword(oldPhone));
            stm.setString(3, oldPhone);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("updateStudentCredentials: " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    /**
     */
    public boolean verifyStudentPassword(String phone, String password) {
        try {
            String strSQL = "SELECT password FROM Student WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword != null && storedPassword.equals(password);
            }
        } catch (Exception e) {
            System.out.println("verifyStudentPassword: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }

    // Staff methods
    /**
     * Lấy thông tin hồ sơ của một nhân viên dựa trên số điện thoại.
     */
    public AdminStaffs getStaffByPhone(String phone) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, role_id, phone "
                    + "FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String roleId = String.valueOf(rs.getInt("role_id"));
                return new AdminStaffs(id, fullName, email, password, birthDate, gender, roleId, phone);
            }
        } catch (Exception e) {
            System.out.println("getStaffByPhone: " + e.getMessage());
        } finally {
            closeResources();
        }
        return null;
    }

    /**
     * Lấy danh sách tất cả nhân viên trong cơ sở dữ liệu.
     */
    public ArrayList<AdminStaffs> getAllStaff() {
        ArrayList<AdminStaffs> data = new ArrayList<>();
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, role_id, phone "
                    + "FROM Admin_staff";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String roleId = String.valueOf(rs.getInt("role_id"));
                String phone = rs.getString("phone");
                AdminStaffs staff = new AdminStaffs(id, fullName, email, password, birthDate, gender, roleId, phone);
                data.add(staff);
            }
        } catch (Exception e) {
            System.out.println("getAllStaff: " + e.getMessage());
        } finally {
            closeResources();
        }
        return data;
    }

    /**
     * Cập nhật thông tin cá nhân của nhân viên (tên, email, ngày sinh, giới
     * tính).
     */
    public void updateStaff(AdminStaffs s) {
        try {
            String strSQL = "UPDATE Admin_staff SET full_name = ?, email = ?, birth_date = ?, gender = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, s.getName());
            stm.setString(2, s.getEmail());
            stm.setString(3, s.getBirthdate());
            stm.setString(4, s.getGender());
            stm.setString(5, s.getPhone());
            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateStaff: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    /**
     * Cập nhật số điện thoại và/hoặc mật khẩu của nhân viên.
     */
    public boolean updateStaffCredentials(String oldPhone, String newPhone, String newPassword) {
        try {
            String strSQL = "UPDATE Admin_staff SET phone = ?, password = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newPhone != null ? newPhone : oldPhone);
            stm.setString(2, newPassword != null ? newPassword : getCurrentStaffPassword(oldPhone));
            stm.setString(3, oldPhone);
            int rowsAffected = stm.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("updateStaffCredentials: " + e.getMessage());
            return false;
        } finally {
            closeResources();
        }
    }

    /**
     * Xác minh mật khẩu của nhân viên dựa trên số điện thoại.
     */
    public boolean verifyStaffPassword(String phone, String password) {
        try {
            String strSQL = "SELECT password FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword != null && storedPassword.equals(password);
            }
        } catch (Exception e) {
            System.out.println("verifyStaffPassword: " + e.getMessage());
        } finally {
            closeResources();
        }
        return false;
    }

    // Helper methods
    /**
     * Lấy hình ảnh hiện tại của giáo viên dựa trên số điện thoại.
     */
    private byte[] getCurrentTeacherPicture(String phone) {
        try {
            String strSQL = "SELECT picture FROM Teacher WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getBytes("picture");
            }
        } catch (Exception e) {
            System.out.println("getCurrentTeacherPicture: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lấy hình ảnh hiện tại của học sinh dựa trên số điện thoại.
     */
    private byte[] getCurrentStudentPicture(String phone) {
        try {
            String strSQL = "SELECT picture FROM Student WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getBytes("picture");
            }
        } catch (Exception e) {
            System.out.println("getCurrentStudentPicture: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lấy mật khẩu hiện tại của giáo viên dựa trên số điện thoại.
     */
    private String getCurrentTeacherPassword(String phone) {
        try {
            String strSQL = "SELECT password FROM Teacher WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (Exception e) {
            System.out.println("getCurrentTeacherPassword: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lấy mật khẩu hiện tại của học sinh dựa trên số điện thoại.
     */
    private String getCurrentStudentPassword(String phone) {
        try {
            String strSQL = "SELECT password FROM Student WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (Exception e) {
            System.out.println("getCurrentStudentPassword: " + e.getMessage());
        }
        return null;
    }

    /**
     * Lấy mật khẩu hiện tại của nhân viên dựa trên số điện thoại.
     */
    private String getCurrentStaffPassword(String phone) {
        try {
            String strSQL = "SELECT password FROM Admin_staff WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (Exception e) {
            System.out.println("getCurrentStaffPassword: " + e.getMessage());
        }
        return null;
    }
    

    public void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        } catch (Exception e) {
            System.out.println("closeResources: " + e.getMessage());
        }
    }
}