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
        String sql = "INSERT INTO TeacherApplications (full_name, email, cv_link, status, birth_date, gender, expertise, image, id_type_course, years_of_experience,phone) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stm = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            stm.setString(1, teacher.getFullName());
            stm.setString(2, teacher.getEmail());
            stm.setString(3, teacher.getCvlink());
            stm.setString(4, teacher.getStatus());
            String birthDateStr = teacher.getBirthDate();
            java.sql.Date birthDate = null;
            if (birthDateStr != null && !birthDateStr.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                java.util.Date utilDate = dateFormat.parse(birthDateStr);
                birthDate = new java.sql.Date(utilDate.getTime());
            }
            stm.setDate(5, birthDate);
            stm.setString(6, teacher.getGender());
            stm.setString(7, teacher.getExpertise());
            stm.setString(8, teacher.getImage());
            stm.setInt(9, Integer.parseInt(teacher.getIdTypeCourse()));
            stm.setInt(10, Integer.parseInt(teacher.getYearOfExpertise()));
            stm.setInt(11, Integer.parseInt(teacher.getPhone()));
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

   

   

   
}