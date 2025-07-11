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
public class StaffDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;
//lấy toàn bộ danh sách nhân viên

    public ArrayList<Staff> getStaff() {

        ArrayList<Staff> data = new ArrayList<>();
        try {
            String strSQL = "select * from Admin_staff s join Role r on s.role_id = r.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(6);
                String birthdate = rs.getString(4);
                String gender = rs.getString(5);
                String role = rs.getString(10);
                String phone = rs.getString(8);

                Staff p = new Staff(id, name, email, password, birthdate, gender, role, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getStaff" + e.getMessage());

        }
        return data;
    }

    public Staff getStaffById(String id) {
        try {
            String strSQL = "select * from Admin_staff where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(6);
                String birthdate = rs.getString(4);
                String gender = rs.getString(5);
                String role = rs.getString(7);
                String phone = rs.getString(8);

                Staff p = new Staff(id, name, email, password, birthdate, gender, role, phone);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getStaff" + e.getMessage());

        }
        return null;
    }

    // Sửa thông tin nhân viên
    public ResultMessage update(Staff s) {
        // Kiểm tra đầu vào
        if (s == null) {
            return new ResultMessage(false, "Dữ liệu nhân viên không hợp lệ.");
        }
        if (s.id == null || s.id.isEmpty()) {
            return new ResultMessage(false, "ID nhân viên không được để trống.");
        }
        if (s.name == null || s.name.isEmpty()) {
            return new ResultMessage(false, "Tên nhân viên không được để trống.");
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
        if (s.role == null || s.role.isEmpty()) {
            return new ResultMessage(false, "Vai trò không được để trống.");
        }
        if (s.phone == null || s.phone.isEmpty()) {
            return new ResultMessage(false, "Số điện thoại không được để trống.");
        }
        if (!s.phone.matches("^\\+?[0-9]{10,11}$")) {
            return new ResultMessage(false, "Định dạng số điện thoại không hợp lệ: " + s.phone);
        }
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        // Kiểm tra định dạng ID
        int staffId;
        try {
            staffId = Integer.parseInt(s.id);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID nhân viên phải là một số hợp lệ: " + s.id);
        }

        int roleId;
        try {
            roleId = Integer.parseInt(s.role);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID nhân viên phải là một số hợp lệ: " + s.role);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE Admin_staff SET full_name = ?, email = ?, birth_date = ?, gender = ?, password = ?,role_id=? , phone = ? WHERE id = ?")) {
            // Kiểm tra email trùng với nhân viên khác (ngoại trừ chính nó)
            if (isEmailExistForOther(s.email, staffId)) {
                return new ResultMessage(false, "Email '" + s.email + "' đã được sử dụng bởi nhân viên khác.");
            }
            // Kiểm tra số điện thoại trùng với nhân viên khác (ngoại trừ chính nó)
            if (isPhoneExistForOther(s.phone, staffId)) {
                return new ResultMessage(false, "Số điện thoại '" + s.phone + "' đã được sử dụng bởi nhân viên khác.");
            }
            stm.setString(1, s.name);
            stm.setString(2, s.email);
            stm.setString(3, s.birthdate);
            stm.setString(4, s.gender);
            stm.setString(5, s.password);
            stm.setInt(6, roleId);
            stm.setString(7, s.phone);
            stm.setInt(8, staffId);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Cập nhật nhân viên thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy nhân viên với ID: " + s.id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Kiểm tra email đã tồn tại cho nhân viên khác chưa
    private boolean isEmailExistForOther(String email, int excludeId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Admin_staff WHERE email = ? AND id != ?")) {
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

    // Kiểm tra số điện thoại đã tồn tại cho nhân viên khác chưa
    private boolean isPhoneExistForOther(String phone, int excludeId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Admin_staff WHERE phone = ? AND id != ?")) {
            stm.setString(1, phone);
            stm.setInt(2, excludeId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Kiểm tra số điện thoại: " + phone + " (ngoại trừ ID: " + excludeId + ") - Kết quả: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong isPhoneExistForOther: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
        return false;
    }

    // Thêm nhân viên
    public ResultMessage add(Staff s) throws SQLException {
        // Kiểm tra đầu vào
        if (s == null || s.email == null || s.email.isEmpty()) {
            return new ResultMessage(false, "Email không được để trống.");
        }
        if (!s.email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return new ResultMessage(false, "Định dạng email không hợp lệ: " + s.email);
        }
        if (s.name == null || s.name.isEmpty()) {
            return new ResultMessage(false, "Tên nhân viên không được để trống.");
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
        if (s.role == null || s.role.isEmpty()) {
            return new ResultMessage(false, "Vai trò không được để trống.");
        }
        if (s.phone == null || s.phone.isEmpty()) {
            return new ResultMessage(false, "Số điện thoại không được để trống.");
        }
        if (!s.phone.matches("^\\+?[0-9]{10,11}$")) {
            return new ResultMessage(false, "Định dạng số điện thoại không hợp lệ: " + s.phone);
        }
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        // Kiểm tra role_id
        int roleId;
        try {
            roleId = Integer.parseInt(s.role);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID vai trò phải là một số hợp lệ: " + s.role);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO Admin_staff (full_name, email, birth_date, gender, password, role_id, phone) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            if (isAccountExist(s.email)) {
                return new ResultMessage(false, "Tài khoản '" + s.email + "' đã tồn tại.");
            }
            if (isPhoneExist(s.phone)) {
                return new ResultMessage(false, "Số điện thoại '" + s.phone + "' đã tồn tại.");
            }
            stm.setString(1, s.name);
            stm.setString(2, s.email);
            stm.setString(3, s.birthdate);
            stm.setString(4, s.gender);
            stm.setString(5, s.password);
            stm.setInt(6, roleId);
            stm.setString(7, s.phone);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Thêm nhân viên thành công!");
            } else {
                return new ResultMessage(false, "Không thể thêm nhân viên. Vui lòng thử lại.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong add: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean isAccountExist(String email) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM Admin_staff WHERE email = ?")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Kiểm tra email: " + email + " - Kết quả: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong isAccountExist: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
        return false;
    }

    // Kiểm tra số điện thoại đã tồn tại chưa
    private boolean isPhoneExist(String phone) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Admin_staff WHERE phone = ?")) {
            stm.setString(1, phone);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Kiểm tra số điện thoại: " + phone + " - Kết quả: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong isPhoneExist: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
        return false;
    }
// xóa thông tin nhân viên
    public ResultMessage delete(String id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM Admin_staff WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Xóa nhân viên thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy nhân viên với ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in delete: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID không hợp lệ: " + id);
        }
    }
    
    // tìm kiếm nhân viên theo tên 
    public ArrayList<Staff> getStaffByName(String name1) {
        ArrayList<Staff> data = new ArrayList<>();
        try {
            String strSQL = "  SELECT * FROM Admin_staff WHERE full_name LIKE ? ";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + name1 + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(6);
                String birthdate = rs.getString(4);
                String gender = rs.getString(5);
                String role = rs.getString(7);
                String phone = rs.getString(8);

                Staff p = new Staff(id, name, email, password, birthdate, gender, role, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getProducts" + e.getMessage());

        }
        return data;
    }
    // tìm kiếm nhân viên theo giới tính
    public ArrayList<Staff> getStaffsByGender(String gender) {
        ArrayList<Staff> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM Admin_staff WHERE gender = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, gender);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String email = rs.getString(3);
                String password = rs.getString(6);
                String birthdate = rs.getString(4);
                String staffgender = rs.getString(5);
                String role = rs.getString(7);
                String phone = rs.getString(8);

                Staff p = new Staff(id, name, email, password, birthdate, staffgender, role, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getStudentsByGender: " + e.getMessage());
        }
        return data;
    }
}