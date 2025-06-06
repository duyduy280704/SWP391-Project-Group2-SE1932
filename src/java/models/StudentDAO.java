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

//Dương_Homepage
public class StudentDAO extends DBContext{
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
                String address = rs.getString(7);
                String role = rs.getString(10);

                Students p = new Students(id, name, email, password, brithdate, gender, address, role);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getStudents" + e.getMessage());

        }
        return data;
    }
    

    //Huyền- checklogin của student
    
    public Students checkLogin(String email, String password) {
        try{
           String strSQL="select id,password,full_name,email,birth_date,gender,address,Role_id"
                   + " from Student"
                   + " where email =? and password=?";
                   stm=connection.prepareStatement(strSQL);
                   stm.setString(1, email);
                   stm.setString(2, password);
                   rs=stm.executeQuery();
            while(rs.next()){
                   String id=String.valueOf(rs.getString("id"));
                   String pwd = rs.getString("password");
                String fullName = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String birthDate = rs.getString("birth_date"); 
                String gender = rs.getString("gender");
                String address = rs.getString("address");
                String roleId = String.valueOf(rs.getInt("Role_id"));
                Students student = new Students(id, pwd, fullName, emailFromDB, birthDate, gender, address, roleId);
                return student;
            }                   
        }catch(Exception e){
            System.out.println("checklogin" +e.getMessage());
        }
       return null; 
    }
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
                String address = rs.getString(7);
                String role = rs.getString(8);

                Students p = new Students(id, name, email, password, brithdate, gender, address, role);
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
        if (s.brithdate != null && !s.brithdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return new ResultMessage(false, "Định dạng ngày sinh không hợp lệ (yyyy-MM-dd): " + s.brithdate);
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
                "UPDATE Student SET password = ?, full_name = ?, email = ?, birth_date = ?, gender = ?, address = ? WHERE id = ?")) {
            // Kiểm tra email trùng với học sinh khác (ngoại trừ chính nó)
            if (isEmailExistForOther(s.email, studentId)) {
                return new ResultMessage(false, "Email '" + s.email + "' đã được sử dụng bởi học sinh khác.");
            }
            stm.setString(1, s.password); // Nên mã hóa mật khẩu
            stm.setString(2, s.name);
            stm.setString(3, s.email);
            stm.setString(4, s.brithdate); // Sửa lỗi chính tả từ brithdate
            stm.setString(5, s.gender);
            stm.setString(6, s.address);
            stm.setInt(7, studentId);
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

    // Thêm học sinh
    public ResultMessage add(Students s) throws SQLException {
        if (s == null || s.email == null || s.email.isEmpty()) {
            return new ResultMessage(false, "Email không được để trống.");
        }
        if (!s.email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            return new ResultMessage(false, "Định dạng email không hợp lệ: " + s.email);
        }
        if (s.brithdate != null && !s.brithdate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return new ResultMessage(false, "Định dạng ngày sinh không hợp lệ (yyyy-MM-dd): " + s.brithdate);
        }
        if (s.gender != null && !s.gender.matches("Nam|Nữ")) {
            return new ResultMessage(false, "Giới tính không hợp lệ (phải là 'Nam' hoặc 'Nữ'): " + s.gender);
        }
        if (s.address == null || s.address.isEmpty()) {
            return new ResultMessage(false, "Địa chỉ không được để trống.");
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
                "INSERT INTO Student (full_name, email, password, birth_date, gender, address, role_id) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            if (isAccountExist(s.email)) {
                return new ResultMessage(false, "Tài khoản '" + s.email + "' đã tồn tại.");
            }
            stm.setString(1, s.name);
            stm.setString(2, s.email);
            stm.setString(3, s.password); // Nên mã hóa mật khẩu
            stm.setString(4, s.brithdate); // Sửa lỗi chính tả
            stm.setString(5, s.gender);
            stm.setString(6, s.address);
            stm.setInt(7, roleId);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(true, "Thêm học sinh thành công!");
        } catch (SQLException e) {
            System.err.println("SQL Error in add: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public boolean isAccountExist(String email) throws SQLException {
        if (connection == null) {
            throw new SQLException("Database connection is not initialized.");
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM Student WHERE email = ?")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Check Email: " + email + " - Kết quả: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
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

}

