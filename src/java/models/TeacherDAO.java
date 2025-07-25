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
            String strSQL = "SELECT s.id, s.password, s.full_name, s.email, s.birth_date, s.gender, s.Expertise, " +
                           "s.picture, s.role_id, s.id_type_course, s.years_of_experience, s.phone, s.offer_salary, " +
                           "r.name AS role_name, t.name AS course_name " +
                           "FROM Teacher s JOIN Role r ON s.role_id = r.id JOIN type_course t ON s.id_type_course = t.id";
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
                String course = rs.getString(15);
                String years = String.valueOf(rs.getInt(11));
                String phone = rs.getString(12);
                String offerSalary = rs.getString(13);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years, phone, offerSalary);
                data.add(p);
            }
        } catch (SQLException e) {
            System.err.println("getTeachers: " + e.getMessage());
        }
        return data;
    }

    // lấy kiểu khóa học
    public ArrayList<TypeCourse> getCourseType() {
        ArrayList<TypeCourse> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM type_course";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String code = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                TypeCourse c = new TypeCourse(code, name);
                data.add(c);
            }
        } catch (SQLException e) {
            System.err.println("getCourseType: " + e.getMessage());
        }
        return data;
    }

    // lấy giáo viên theo id
    public Teachers getTeacherById(String id) {
        try {
            String strSQL = "SELECT id, password, full_name, email, birth_date, gender, Expertise, picture, role_id, " +
                           "id_type_course, years_of_experience, phone, offer_salary FROM Teacher WHERE id = ?";
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
                String offerSalary = rs.getString(13);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years, phone, offerSalary);
                return p;
            }
        } catch (SQLException e) {
            System.err.println("getTeacherById: " + e.getMessage());
        }
        return null;
    }

    // sửa thông tin giáo viên
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
            int years = Integer.parseInt(s.year);
            if (years < 0) {
                return new ResultMessage(false, "Số năm kinh nghiệm không được âm.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Số năm kinh nghiệm phải là một số hợp lệ: " + s.year);
        }
        if (s.phone == null || s.phone.isEmpty()) {
            return new ResultMessage(false, "Số điện thoại không được để trống.");
        }
        if (!s.phone.matches("^\\+?[0-9]{10,11}$")) {
            return new ResultMessage(false, "Định dạng số điện thoại không hợp lệ: " + s.phone);
        }
        if (s.offerSalary == null || s.offerSalary.isEmpty()) {
            return new ResultMessage(false, "Mức lương đề xuất không được để trống.");
        }
        try {
            double salary = Double.parseDouble(s.offerSalary);
            if (salary < 0) {
                return new ResultMessage(false, "Mức lương đề xuất không được âm.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Mức lương đề xuất phải là một số hợp lệ: " + s.offerSalary);
        }
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        int teacherId;
        try {
            teacherId = Integer.parseInt(s.id);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID giáo viên phải là một số hợp lệ: " + s.id);
        }

        int courseId;
        try {
            courseId = Integer.parseInt(s.course);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID loại khóa học phải là một số hợp lệ: " + s.course);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE Teacher SET password = ?, full_name = ?, email = ?, birth_date = ?, gender = ?, Expertise = ?, " +
                "picture = ?, id_type_course = ?, years_of_experience = ?, phone = ?, offer_salary = ? WHERE id = ?")) {
            if (isEmailExistForOther(s.email, teacherId)) {
                return new ResultMessage(false, "Email '" + s.email + "' đã được sử dụng bởi giáo viên khác.");
            }
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
            stm.setInt(8, courseId);
            stm.setString(9, s.year);
            stm.setString(10, s.phone);
            stm.setDouble(11, Double.parseDouble(s.offerSalary));
            stm.setInt(12, teacherId);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Cập nhật giáo viên thành công!" : "Không tìm thấy giáo viên với ID: " + s.id);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // kiểm tra email đã tồn tại cho giáo viên khác chưa
    private boolean isEmailExistForOther(String email, int excludeId) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Teacher WHERE email = ? AND id != ?")) {
            stm.setString(1, email);
            stm.setInt(2, excludeId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // kiểm tra số điện thoại đã tồn tại cho giáo viên khác chưa
    private boolean isPhoneExistForOther(String phone, int excludeId) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Teacher WHERE phone = ? AND id != ?")) {
            stm.setString(1, phone);
            stm.setInt(2, excludeId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // thêm giáo viên
    public ResultMessage add(Teachers s) {
        if (s == null) {
            return new ResultMessage(false, "Dữ liệu giáo viên không hợp lệ.");
        }
        if (s.name == null || s.name.isEmpty()) {
            return new ResultMessage(false, "Tên không được để trống.");
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
            int years = Integer.parseInt(s.year);
            if (years < 0) {
                return new ResultMessage(false, "Số năm kinh nghiệm không được âm.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Số năm kinh nghiệm phải là một số hợp lệ: " + s.year);
        }
        if (s.phone == null || s.phone.isEmpty()) {
            return new ResultMessage(false, "Số điện thoại không được để trống.");
        }
        if (!s.phone.matches("^\\+?[0-9]{10,11}$")) {
            return new ResultMessage(false, "Định dạng số điện thoại không hợp lệ: " + s.phone);
        }
        if (s.offerSalary == null || s.offerSalary.isEmpty()) {
            return new ResultMessage(false, "Mức lương đề xuất không được để trống.");
        }
        try {
            double salary = Double.parseDouble(s.offerSalary);
            if (salary < 0) {
                return new ResultMessage(false, "Mức lương đề xuất không được âm.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Mức lương đề xuất phải là một số hợp lệ: " + s.offerSalary);
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

        int courseId;
        try {
            courseId = Integer.parseInt(s.course);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID loại khóa học phải là một số hợp lệ: " + s.course);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO Teacher (full_name, email, password, Expertise, birth_date, gender, picture, role_id, id_type_course, years_of_experience, phone, offer_salary) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
                Statement.RETURN_GENERATED_KEYS)) {
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
            stm.setInt(9, courseId);
            stm.setString(10, s.year);
            stm.setString(11, s.phone);
            stm.setDouble(12, Double.parseDouble(s.offerSalary));
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    s.id = String.valueOf(rs.getInt(1)); // Set generated ID
                }
                return new ResultMessage(true, "Thêm giáo viên thành công!");
            } else {
                return new ResultMessage(false, "Không thể thêm giáo viên. Vui lòng thử lại.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong add: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // kiểm tra email đã tồn tại chưa
    public boolean isAccountExist(String email) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM Teacher WHERE email = ?")) {
            stm.setString(1, email);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // kiểm tra số điện thoại đã tồn tại chưa
    private boolean isPhoneExist(String phone) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM Teacher WHERE phone = ?")) {
            stm.setString(1, phone);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // xóa thông tin giáo viên
    public ResultMessage delete(String id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM Teacher WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Xóa giáo viên thành công!" : "Không tìm thấy giáo viên với ID: " + id);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong delete: " + e.getMessage());
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
            String strSQL = "SELECT id, password, full_name, email, birth_date, gender, Expertise, picture, role_id, " +
                           "id_type_course, years_of_experience, phone, offer_salary FROM Teacher WHERE full_name LIKE ?";
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
                String offerSalary = rs.getString(13);

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years, phone, offerSalary);
                data.add(p);
            }
        } catch (SQLException e) {
            System.err.println("getTeacherByName: " + e.getMessage());
        }
        return data;
    }

    // tìm kiếm giáo viên theo giới tính
    public ArrayList<Teachers> getTeachersByGender(String gender) {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "SELECT id, password, full_name, email, birth_date, gender, Expertise, picture, role_id, " +
                           "id_type_course, years_of_experience, phone, offer_salary FROM Teacher WHERE gender = ?";
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
                String offerSalary = rs.getString(13);

                Teachers p = new Teachers(id, name, email, password, birthdate, teacherGender, exp, pic, role, course, years, phone, offerSalary);
                data.add(p);
            }
        } catch (SQLException e) {
            System.err.println("getTeachersByGender: " + e.getMessage());
        }
        return data;
    }
    
    //duong
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
//huyen
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
    // Phương thức mới: Lấy danh sách giáo viên với tên môn học
    public ArrayList<Teachers> getTeachersWithCourseName() {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = """
                SELECT s.id, s.password, s.full_name, s.email, s.birth_date, s.gender, s.Expertise, 
                       s.picture, s.role_id, s.id_type_course, s.years_of_experience, s.phone, 
                       r.name AS role_name, t.name AS course_name
                FROM Teacher s
                JOIN Role r ON s.role_id = r.id
                JOIN type_course t ON s.id_type_course = t.id
            """;
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("full_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String birthdate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String exp = rs.getString("Expertise");
                byte[] pic = rs.getBytes("picture");
                String role = rs.getString("role_name");
                String course = rs.getString("course_name"); // Lấy tên môn học từ type_course
                String years = String.valueOf(rs.getInt("years_of_experience"));
                String phone = rs.getString("phone");
                String offerSalary = null; // Nếu không có cột offer_salary trong bảng

                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, course, years, phone, offerSalary);
                data.add(p);
            }
        } catch (SQLException e) {
            System.err.println("getTeachersWithCourseName: " + e.getMessage());
        }
        return data;
    }

}

