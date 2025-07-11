/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Quang
 */
public class StudentDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Students> getStudents() {

        ArrayList<Students> data = new ArrayList<>();
        try {
            String strSQL = "select * from Student s join role r on s.Role_id = r.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String brithdate = rs.getString(5);
                String gender = rs.getString(6);
                byte[] pic = rs.getBytes(7);
                String address = rs.getString(8);
                String role = rs.getString(12);
                String phone = rs.getString(10);

                Students p = new Students(id, name, email, password, brithdate, gender, pic, address, role, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getStudents" + e.getMessage());

        }
        return data;
    }

    //Huyen
    public Students checkLogin(String phone, String password) {
        try {
            String strSQL = "select id,password,full_name,email,birth_date,gender,picture,address,Role_id,phone"
                    + " from Student"
                    + " where phone =? and password=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getString("id"));
                String pwd = rs.getString("password");
                String fullName = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String roleId = rs.getString("Role_id");
                String phones = rs.getString("phone");
                byte[] pic = rs.getBytes("picture");
                Students student = new Students(id, fullName, emailFromDB, pwd, birthDate, gender, pic, address, roleId, phones);
                return student;
            }
        } catch (Exception e) {
            System.out.println("checklogin" + e.getMessage());
        }
        return null;
    }

    //Quang
    public Students getStudentById(String id) {
        try {
            String strSQL = "select * from Student where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String brithdate = rs.getString(5);
                String gender = rs.getString(6);
                byte[] pic = rs.getBytes(7);
                String address = rs.getString(8);
                String role = rs.getString(9);
                String phone = rs.getString(10);

                Students p = new Students(id, name, email, password, brithdate, gender, pic, address, role, phone);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getStudents" + e.getMessage());

        }
        return null;
    }

    // Cập nhật học sinh
    public ResultMessage update(Students s) {
        // Kiểm tra đầu vào
        if (s == null) {
            return new ResultMessage(false, "Dữ liệu học sinh không hợp lệ.");
        }
        if (s.id == null || s.id.isEmpty()) {
            return new ResultMessage(false, "ID học sinh không được để trống.");
        }
        if (s.name == null || s.name.isEmpty()) {
            return new ResultMessage(false, "Tên học sinh không được để trống.");
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
        if (s.address == null || s.address.isEmpty()) {
            return new ResultMessage(false, "Địa chỉ không được để trống.");
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
        int studentId;
        try {
            studentId = Integer.parseInt(s.id);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID học sinh phải là một số hợp lệ: " + s.id);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE Student SET password = ?, full_name = ?, email = ?, birth_date = ?, gender = ?, picture = ?, address = ?, phone = ? WHERE id = ?")) {
            // Kiểm tra email trùng với học sinh khác (ngoại trừ chính nó)
            if (isEmailExistForOther(s.email, studentId)) {
                return new ResultMessage(false, "Email '" + s.email + "' đã được sử dụng bởi học sinh khác.");
            }
            // Kiểm tra số điện thoại trùng với học sinh khác (ngoại trừ chính nó)
            if (isPhoneExistForOther(s.phone, studentId)) {
                return new ResultMessage(false, "Số điện thoại '" + s.phone + "' đã được sử dụng bởi học sinh khác.");
            }
            stm.setString(1, s.password);
            stm.setString(2, s.name);
            stm.setString(3, s.email);
            stm.setString(4, s.birthdate);
            stm.setString(5, s.gender);
            if (s.pic != null) {
                stm.setBytes(6, s.pic);
            } else {
                stm.setNull(6, Types.BLOB);
            }
            stm.setString(7, s.address);
            stm.setString(8, s.phone);
            stm.setInt(9, studentId);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Cập nhật học sinh thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy học sinh với ID: " + s.id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Kiểm tra email đã tồn tại cho học sinh khác chưa
    private boolean isEmailExistForOther(String email, int excludeId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Student WHERE email = ? AND id != ?")) {
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

    // Kiểm tra số điện thoại đã tồn tại cho học sinh khác chưa
    private boolean isPhoneExistForOther(String phone, int excludeId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Student WHERE phone = ? AND id != ?")) {
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

    // Thêm học sinh
    public ResultMessage add(Students s) throws SQLException {
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
        if (s.address == null || s.address.isEmpty()) {
            return new ResultMessage(false, "Địa chỉ không được để trống.");
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

        int roleId;
        try {
            roleId = Integer.parseInt(s.role);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Giá trị role_id không hợp lệ: " + s.role);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO Student (full_name, email, password, birth_date, gender, picture, address, role_id, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            if (isAccountExist(s.email)) {
                return new ResultMessage(false, "Tài khoản '" + s.email + "' đã tồn tại.");
            }
            if (isPhoneExist(s.phone)) {
                return new ResultMessage(false, "Số điện thoại '" + s.phone + "' đã tồn tại.");
            }
            stm.setString(1, s.name);
            stm.setString(2, s.email);
            stm.setString(3, s.password);
            stm.setString(4, s.birthdate);
            stm.setString(5, s.gender);
            if (s.pic != null) {
                stm.setBytes(6, s.pic);
            } else {
                stm.setNull(6, Types.BLOB);
            }
            stm.setString(7, s.address);
            stm.setInt(8, roleId);
            stm.setString(9, s.phone);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(true, "Thêm học sinh thành công!");
        } catch (SQLException e) {
            System.err.println("SQL Error in add: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Kiểm tra email đã tồn tại chưa
    public boolean isAccountExist(String email) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Student WHERE email = ?")) {
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
    public boolean isPhoneExist(String phone) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Student WHERE phone = ?")) {
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

    // Xóa học sinh
    public ResultMessage delete(String id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM Student WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Xóa học sinh thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy học sinh với ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in delete: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID không hợp lệ: " + id);
        }
    }
// lấy ảnh của học sinh theo id

    public byte[] getPicById(String studentId) throws SQLException {
        if (studentId == null || studentId.isEmpty()) {
            return null;
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT picture FROM Student WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(studentId));
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("picture");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong getPicById: " + e.getMessage());
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("Invalid Student ID: " + studentId);
            throw e;
        }
        return null;
    }
    // tìm kiếm học sinh theo tên 

    public ArrayList<Students> getStudentByName(String name1) {
        ArrayList<Students> data = new ArrayList<>();
        try {
            String strSQL = "  SELECT * FROM Student WHERE full_name LIKE ? ";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + name1 + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String brithdate = rs.getString(5);
                String gender = rs.getString(6);
                byte[] pic = rs.getBytes(7);
                String address = rs.getString(8);
                String role = rs.getString(9);
                String phone = rs.getString(10);

                Students p = new Students(id, name, email, password, brithdate, gender, pic, address, role, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getProducts" + e.getMessage());

        }
        return data;
    }

    // tìm kiếm học sinh theo giới tính
    public ArrayList<Students> getStudentsByGender(String gender) {
        ArrayList<Students> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM Student WHERE gender = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, gender);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String birthdate = rs.getString(5);
                String studentGender = rs.getString(6);
                byte[] pic = rs.getBytes(7);
                String address = rs.getString(8);
                String role = rs.getString(9);
                String phone = rs.getString(10);

                Students p = new Students(id, name, email, password, birthdate, studentGender, pic, address, role, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getStudentsByGender: " + e.getMessage());
        }
        return data;
    }

    public Students getByPhone(String phone) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, picture, address, Role_id, phone "
                    + "FROM Student WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthdate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                byte[] pic = rs.getBytes("picture");
                String address = rs.getString("address");
                String role = String.valueOf(rs.getInt("Role_id"));
                String phones = rs.getString("phone");
                return new Students(id, name, email, password, birthdate, gender, pic, address, role, phones);
            }
        } catch (Exception e) {
            System.out.println("getByPhone: " + e.getMessage());
        }
        return null;
    }

    public Students getByEmail(String email) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, picture, address, Role_id, phone "
                    + "FROM Student WHERE email = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, email);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String password = rs.getString("password");
                String birthdate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                byte[] pic = rs.getBytes("picture");
                String address = rs.getString("address");
                String role = String.valueOf(rs.getInt("Role_id"));
                String phone = rs.getString("phone");
                return new Students(id, name, emailFromDB, password, birthdate, gender, pic, address, role, phone);
            }
        } catch (Exception e) {
            System.out.println("getByEmail: " + e.getMessage());
        }
        return null;
    }

    public boolean updatePassword(String phone, String newPassword) {
        if (phone == null || newPassword == null) {
            System.out.println("updatePassword: Phone hoặc mật khẩu null");
            return false;
        }
        try {
            String strSQL = "UPDATE Student SET password = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newPassword);
            stm.setString(2, phone);
            int rowsAffected = stm.executeUpdate();
            System.out.println("updatePassword: Cập nhật " + rowsAffected + " hàng cho phone " + phone);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("updatePassword: Lỗi SQL - " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.err.println("updatePassword: Lỗi khi đóng statement - " + e.getMessage());
            }
        }
    }

    public boolean isPhoneExists(String phone) {
        String sql = "SELECT 1 FROM Student WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
