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
public class TeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    //Quang
    public ArrayList<Teachers> getTeachers() {

        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "select * from Teacher s join Role r on s.role_id = r.id join type_course t on s.id_type_course = t.id";
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
                String role = rs.getString(13);
                String course = rs.getString(15);
                String years = String.valueOf(rs.getInt(11));

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return data;
    }

    public ArrayList<TypeCourse> getCourseType() {
        ArrayList<TypeCourse> data = new ArrayList<>();
        try {
            String strSQL = "select * from type_course";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String code = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);

                TypeCourse c = new TypeCourse(code, name);
                data.add(c);
            }
        } catch (Exception e) {
            System.out.println("getCourseType" + e.getMessage());

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
                String course = rs.getString(10);
                String years = String.valueOf(rs.getInt(11));

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years);
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

        if (s.pic == null || s.pic.isEmpty()) {
            return new ResultMessage(false, "Hình ảnh giáo viên không được để trống.");
        }
        if (s.course == null || s.course.isEmpty()) {
            return new ResultMessage(false, "Loại khóa học không được để trống.");
        }
        if (s.year == null || s.year.isEmpty()) {
            return new ResultMessage(false, "Số năm kinh nghiệm không được để trống.");
        }
        try {
            Integer.parseInt(s.year);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Số năm kinh nghiệm phải là một số hợp lệ: " + s.year);
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
                "UPDATE Teacher SET password = ?, full_name = ?, email = ?, birth_date = ?, gender = ?, Expertise = ?, picture = ?, id_type_course=?, years_of_experience=? WHERE id = ?")) {
            // Kiểm tra email trùng với giáo viên khác (ngoại trừ chính nó)
            if (isEmailExistForOther(s.email, teacherId)) {
                return new ResultMessage(false, "Email '" + s.email + "' đã được sử dụng bởi giáo viên khác.");
            }
            stm.setString(1, s.password);
            stm.setString(2, s.name);
            stm.setString(3, s.email);
            stm.setString(4, s.birthdate);
            stm.setString(5, s.gender);
            stm.setString(6, s.exp);
            stm.setString(7, s.pic);
            stm.setString(8, s.course);
            stm.setString(9, s.year);
            stm.setInt(10, teacherId);
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
        if (s.course == null || s.course.isEmpty()) {
            return new ResultMessage(false, "Loại khóa học không được để trống.");
        }
        if (s.year == null || s.year.isEmpty()) {
            return new ResultMessage(false, "Số năm kinh nghiệm không được để trống.");
        }
        try {
            Integer.parseInt(s.year);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Số năm kinh nghiệm phải là một số hợp lệ: " + s.year);
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
                "INSERT INTO Teacher (full_name, email, password, Expertise, birth_date, gender, picture, role_id, id_type_course, years_of_experience) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)")) {
            if (isAccountExist(s.email)) {
                return new ResultMessage(false, "Tài khoản '" + s.email + "' đã tồn tại.");
            }
            stm.setString(1, s.name);
            stm.setString(2, s.email);
            stm.setString(3, s.password);
            stm.setString(4, s.exp);
            stm.setString(5, s.birthdate);
            stm.setString(6, s.gender);
            stm.setString(7, s.pic);
            stm.setInt(8, roleId);
            stm.setString(9, s.course);
            stm.setString(10, s.year);
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
