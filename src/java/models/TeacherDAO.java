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
    // lấy toàn bộ danh sách giáo viên
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
                byte[] pic = rs.getBytes(8);
                String role = rs.getString(14);
                String course = rs.getString(16);
                String years = String.valueOf(rs.getInt(11));
                String phone = rs.getString(12);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return data;
    }
    
    //Huyền-checklogin của teacher
    public Teachers checkLogin(String phone, String password) {
        try {
            String strSQL = "SELECT id, password, full_name, email, birth_date, gender, Expertise, picture, role_id,id_type_course,years_of_experience,phone "
                    + "FROM Teacher "
                    + "WHERE phone = ? AND password = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
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
                byte[] picture = rs.getBytes("picture");
                String roleId = String.valueOf(rs.getInt("role_id")); 
                String idcourse=rs.getString("id_type_course");
                String yearexp=rs.getString("years_of_experience");
                String phones=rs.getString("phone");
                Teachers teacher = new Teachers(id, fullName, emailFromDB, pwd, birthDate, gender, expertise, picture, roleId,idcourse, yearexp,phones);
                return teacher;
            }
        } catch (Exception e) {
            System.out.println("checkLogin: " + e.getMessage());
        }
        return null;
    }
    
// lấy kiểu khóa học

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
                byte[] pic = rs.getBytes(8);
                String role = rs.getString(9);
                String course = rs.getString(10);
                String years = String.valueOf(rs.getInt(11));
                String phone = rs.getString(12);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years, phone);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return null;
    }
    // Sửa thông tin giáo viên

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
        int teacherId;
        try {
            teacherId = Integer.parseInt(s.id);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID giáo viên phải là một số hợp lệ: " + s.id);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE Teacher SET password = ?, full_name = ?, email = ?, birth_date = ?, gender = ?, Expertise = ?, picture = ?, id_type_course = ?, years_of_experience = ?, phone = ? WHERE id = ?")) {
            // Kiểm tra email trùng với giáo viên khác (ngoại trừ chính nó)
            if (isEmailExistForOther(s.email, teacherId)) {
                return new ResultMessage(false, "Email '" + s.email + "' đã được sử dụng bởi giáo viên khác.");
            }
            // Kiểm tra số điện thoại trùng với giáo viên khác (ngoại trừ chính nó)
            if (isPhoneExistForOther(s.phone, teacherId)) {
                return new ResultMessage(false, "Số điện thoại '" + s.phone + "' đã được sử dụng bởi giáo viên khác.");
            }
            stm.setString(1, s.password);
            stm.setString(2, s.name);
            stm.setString(3, s.email);
            stm.setString(4, s.birthdate);
            stm.setString(5, s.gender);
            stm.setString(6, s.exp);
            if (s.pic != null) {
                stm.setBytes(7, s.pic);
            } else {
                stm.setNull(7, Types.BLOB);
            }
            stm.setString(8, s.course);
            stm.setString(9, s.year);
            stm.setString(10, s.phone);
            stm.setInt(11, teacherId);
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

    // Kiểm tra số điện thoại đã tồn tại cho giáo viên khác chưa
    private boolean isPhoneExistForOther(String phone, int excludeId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Teacher WHERE phone = ? AND id != ?")) {
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

    // Thêm giáo viên
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
                "INSERT INTO Teacher (full_name, email, password, Expertise, birth_date, gender, picture, role_id, id_type_course, years_of_experience, phone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            if (isAccountExist(s.email)) {
                return new ResultMessage(false, "Tài khoản '" + s.email + "' đã tồn tại.");
            }
            if (isPhoneExist(s.phone)) {
                return new ResultMessage(false, "Số điện thoại '" + s.phone + "' đã tồn tại.");
            }
            stm.setString(1, s.name);
            stm.setString(2, s.email);
            stm.setString(3, s.password);
            stm.setString(4, s.exp);
            stm.setString(5, s.birthdate);
            stm.setString(6, s.gender);
            if (s.pic != null) {
                stm.setBytes(7, s.pic);
            } else {
                stm.setNull(7, Types.BLOB);
            }
            stm.setInt(8, roleId);
            stm.setString(9, s.course);
            stm.setString(10, s.year);
            stm.setString(11, s.phone);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(true, "Thêm giáo viên thành công!");
        } catch (SQLException e) {
            System.err.println("SQL Error in add: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Kiểm tra email đã tồn tại chưa
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

    // Kiểm tra số điện thoại đã tồn tại chưa
    private boolean isPhoneExist(String phone) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Teacher WHERE phone = ?")) {
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
// xóa thông tin giáo viên

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

    // lấy ảnh giáo viên theo id
    public byte[] getPicById(String teacherId) throws SQLException {
        if (teacherId == null || teacherId.isEmpty()) {
            return null;
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT picture FROM Teacher WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(teacherId));
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("picture");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong getPicById: " + e.getMessage());
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("Invalid teacher ID: " + teacherId);
            throw e;
        }
        return null;
    }
    
    // tìm kiếm giáo viên theo tên 
    public ArrayList<Teachers> getTeacherByName(String name1) {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "  SELECT * FROM Teacher WHERE full_name LIKE ? ";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + name1 + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String birthdate = rs.getString(5);
                String gender = rs.getString(6);
                String exp = rs.getString(7);
                byte[] pic = rs.getBytes(8);
                String role = rs.getString(9);
                String course = rs.getString(10);
                String years = String.valueOf(rs.getInt(11));
                String phone = rs.getString(12);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getProducts" + e.getMessage());

        }
        return data;
    }
    // tìm kiếm giáo viên theo giới tính
    public ArrayList<Teachers> getTeachersByGender(String gender) {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM Teacher WHERE gender = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, gender);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String birthdate = rs.getString(5);
                String teacherGender = rs.getString(6);
                String exp = rs.getString(7);
                byte[] pic = rs.getBytes(8);
                String role = rs.getString(9);
                String course = rs.getString(10);
                String years = String.valueOf(rs.getInt(11));
                String phone = rs.getString(12);

                Teachers p = new Teachers(id, name, email, password, birthdate, teacherGender, exp, pic, role, course, years, phone);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getStudentsByGender: " + e.getMessage());
        }
        return data;
    }
    public ArrayList<Teachers> get4Teachers() {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = """
                            select TOP 4 * from Teacher s join Role r on s.role_id = r.id join type_course t on s.id_type_course = t.id 
                                                        ORDER BY s.years_of_experience DESC
                            """
                    ;

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

             while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String birthdate = rs.getString(5);
                String gender = rs.getString(6);
                String exp = rs.getString(7);
                byte[] pic = rs.getBytes(8);
                String role = rs.getString(14);
                String course = rs.getString(16);
                String years = String.valueOf(rs.getInt(11));
                String phone = rs.getString(12);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years, phone);
                data.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
       public Teachers getByPhone(String phone) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, Expertise, picture, role_id, id_type_course, years_of_experience, phone "
                    + "FROM Teacher WHERE phone = ?";
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
                String expertise = rs.getString("Expertise");
                byte[] picture = rs.getBytes("picture");
                String role = String.valueOf(rs.getInt("role_id"));
                String idcourse = rs.getString("id_type_course");
                String yearexp = rs.getString("years_of_experience");
                String phones = rs.getString("phone");
                return new Teachers(id, name, email, password, birthdate, gender, expertise, picture, role, idcourse, yearexp, phones);
            }
        } catch (Exception e) {
            System.out.println("getByPhone: " + e.getMessage());
        }
        return null;
    }

    public Teachers getByEmail(String email) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, Expertise, picture, role_id, id_type_course, years_of_experience, phone "
                    + "FROM Teacher WHERE email = ?";
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
                String expertise = rs.getString("Expertise");
                byte[] picture = rs.getBytes("picture");
                String role = String.valueOf(rs.getInt("role_id"));
                String idcourse = rs.getString("id_type_course");
                String yearexp = rs.getString("years_of_experience");
                String phone = rs.getString("phone");
                return new Teachers(id, name, emailFromDB, password, birthdate, gender, expertise, picture, role, idcourse, yearexp, phone);
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
            String strSQL = "UPDATE Teacher SET password = ? WHERE phone = ?";
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
        String sql = "SELECT 1 FROM Teacher WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExist(String email) {
        String sql = "SELECT 1 FROM Teacher WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

