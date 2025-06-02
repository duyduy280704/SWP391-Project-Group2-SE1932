/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;

//Dương_Homepage
public class TeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Teachers> get4Teachers() {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "SELECT TOP 4 t.id, t.full_name, t.password, t.email, t.birth_date, t.years_of_experience, "
                    + "t.gender, t.Expertise, t.picture, r.name AS role_name "
                    + "FROM Teacher t JOIN role r ON t.role_id = r.id "
                    + "ORDER BY t.years_of_experience DESC"
                    ;

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                String picture = rs.getString("picture");
                String role = rs.getString("role_name");

                Teachers t = new Teachers(id, fullName, email, password, birthDate, gender, expertise, picture, role);
                data.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    //Huyền-checklogin của teacher
    public Teachers checkLogin(String email, String password) {
        try {
            String strSQL = "SELECT id, password, full_name, email, birth_date, gender, Expertise, picture, role_id "
                    + "FROM Teacher "
                    + "WHERE email = ? AND password = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, email);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String pwd = rs.getString("password");
                String fullName = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                String picture = rs.getString("picture");
                String roleId = String.valueOf(rs.getInt("role_id"));
                Teachers teacher = new Teachers(id, pwd, fullName, emailFromDB, birthDate, gender, expertise, picture, roleId);
                return teacher;
            }
        } catch (Exception e) {
            System.out.println("checkLogin: " + e.getMessage());
        }
        return null;
    }

    //Quang
    public ArrayList<Teachers> getTeachers() {

        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "select * from Teacher s join Role r on s.role_id = r.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String birthdate = rs.getString(5);
                String gender = rs.getString(6);
                String exp = rs.getString(7);
                String pic = rs.getString(8);
                String role = rs.getString(11);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return data;
    }

    public Teachers getTeacherById(String id) {
        try {
            String strSQL = "select * from Teacher where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String birthdate = rs.getString(5);
                String gender = rs.getString(6);
                String exp = rs.getString(7);
                String pic = rs.getString(8);
                String role = rs.getString(9);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return null;
    }

    public ResultMessage update(Teachers s) {
        // Kiểm tra đầu vào
        if (s == null) {
            return new ResultMessage(false, "Dữ liệu giáo viên không hợp lệ.");
        }
        if (s.id == null || s.id.isEmpty()) {
            return new ResultMessage(false, "ID giáo viên không được để trống.");
        }
        if (s.name == null || s.name.isEmpty()) {
            return new ResultMessage(false, "Tên giáo viên không được để trống.");
        }
        if (s.email == null || s.email.isEmpty()) {
            return new ResultMessage(false, "Email không được để trống.");
        }
        if (!s.email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return new ResultMessage(false, "Định dạng email không hợp lệ: " + s.email);
        }
        if (s.birthdate != null && !s.birthdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return new ResultMessage(false, "Định dạng ngày sinh không hợp lệ (yyyy-MM-dd): " + s.birthdate);
        }
        if (s.gender != null && !s.gender.matches("Nam|Nữ")) {
            return new ResultMessage(false, "Giới tính không hợp lệ (phải là 'Nam' hoặc 'Nữ'): " + s.gender);
        }
        if (s.password == null || s.password.isEmpty()) {
            return new ResultMessage(false, "Mật khẩu không được để trống.");
        }
        if (s.exp == null || s.exp.isEmpty()) {
            return new ResultMessage(false, "Kinh nghiệm không được để trống.");
        }
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        // Kiểm tra định dạng ID
        int teacherId;
        try {
            teacherId = Integer.parseInt(s.id);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID giáo viên phải là một số hợp lệ: " + s.id);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE Teacher SET password = ?, full_name = ?, email = ?, birth_date = ?, gender = ?, Expertise = ?, Picture = ? WHERE id = ?")) {
            // Kiểm tra email trùng với giáo viên khác (ngoại trừ chính nó)
            if (isEmailExistForOther(s.email, teacherId)) {
                return new ResultMessage(false, "Email '" + s.email + "' đã được sử dụng bởi giáo viên khác.");
            }
            stm.setString(1, s.password); // Nên mã hóa mật khẩu
            stm.setString(2, s.name);
            stm.setString(3, s.email);
            stm.setString(4, s.birthdate);
            stm.setString(5, s.gender);
            stm.setString(6, s.exp);
            stm.setString(7, s.pic != null ? s.pic : ""); // Xử lý Picture null
            stm.setInt(8, teacherId);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Cập nhật giáo viên thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy giáo viên với ID: " + s.id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Kiểm tra email đã tồn tại cho giáo viên khác chưa
    private boolean isEmailExistForOther(String email, int excludeId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Teacher WHERE email = ? AND id != ?")) {
            stm.setString(1, email);
            stm.setInt(2, excludeId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Kiểm tra email: " + email + " (ngoại trừ ID: " + excludeId + ") - Kết quả: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong isEmailExistForOther: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
        return false;
    }

    public ResultMessage add(Teachers s) throws SQLException {
        if (s == null || s.email == null || s.email.isEmpty()) {
            return new ResultMessage(false, "Email không được để trống.");
        }
        if (!s.email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return new ResultMessage(false, "Định dạng email không hợp lệ: " + s.email);
        }
        if (s.birthdate != null && !s.birthdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return new ResultMessage(false, "Định dạng ngày sinh không hợp lệ (yyyy-MM-dd): " + s.birthdate);
        }
        if (s.gender != null && !s.gender.matches("Nam|Nữ")) {
            return new ResultMessage(false, "Giới tính không hợp lệ (phải là 'Nam' hoặc 'Nữ'): " + s.gender);
        }
        if (s.name == null || s.name.isEmpty()) {
            return new ResultMessage(false, "Tên không được để trống.");
        }
        if (s.exp == null || s.exp.isEmpty()) {
            return new ResultMessage(false, "Kinh nghiệm không được để trống.");
        }
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        int roleId;
        try {
            roleId = Integer.parseInt(s.role);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Giá trị role_id không hợp lệ: " + s.role);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO Teacher (full_name, email, password, Expertise, birth_date, gender, Picture, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            if (isAccountExist(s.email)) {
                return new ResultMessage(false, "Tài khoản '" + s.email + "' đã tồn tại.");
            }
            stm.setString(1, s.name);
            stm.setString(2, s.email);
            stm.setString(3, s.password); // Nên mã hóa mật khẩu
            stm.setString(4, s.exp);
            stm.setString(5, s.birthdate);
            stm.setString(6, s.gender);
            stm.setString(7, s.pic);
            stm.setInt(8, roleId);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(true, "Thêm giáo viên thành công!");
        } catch (SQLException e) {
            System.err.println("SQL Error in add: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public boolean isAccountExist(String email) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM Teacher WHERE email = ?")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Check email: " + email + " - Kết quả: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
        return false;
    }

    public ResultMessage delete(String id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM Teacher WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Xóa giáo viên thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy giáo viên với ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in delete: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID không hợp lệ: " + id);
        }
    }
}
