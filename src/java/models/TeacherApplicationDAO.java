package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TeacherApplicationDAO extends DBContext {

    private PreparedStatement stm;
    private ResultSet rs;

    public TeacherApplications registerTeacher(TeacherApplications teacher) throws SQLException, ParseException {
        String sql = "INSERT INTO TeacherApplications (full_name, email, cv_link, status, birth_date, gender, expertise,  id_type_course, years_of_experience,phone) "
                + "VALUES (?, ?, ?,? , ?, ?, ?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, teacher.getFullName());
            stm.setString(2, teacher.getEmail());
            stm.setString(3, teacher.getCvlink());
            stm.setString(4, "Đang chờ");

            String birthDateStr = teacher.getBirthDate();
            java.sql.Date birthDate = null;
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = dateFormat.parse(birthDateStr);
                birthDate = new java.sql.Date(utilDate.getTime());
            }
            stm.setDate(5, birthDate);
            stm.setString(6, teacher.getGender());
            stm.setString(7, teacher.getExpertise());

            stm.setInt(8, Integer.parseInt(teacher.getIdTypeCourse()));
            stm.setInt(9, Integer.parseInt(teacher.getYearOfExpertise()));
            stm.setInt(10, Integer.parseInt(teacher.getPhone()));
            stm.executeUpdate();

            // Lấy ID tự động tăng
            ResultSet generatedKeys = stm.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                teacher.setId(id);
            }
            return teacher;
        } catch (SQLException | ParseException e) {
            throw e;
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
    }

    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM TeacherApplications WHERE email = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, email);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

    public boolean isPhoneExists(String phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM TeacherApplications WHERE phone = ?";
        try {
            stm = connection.prepareStatement(sql);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
        }
    }

}
