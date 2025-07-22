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
            stm.setString(10, teacher.getPhone());
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

    public List<TeacherApplications> getAllApplications() {
        List<TeacherApplications> list = new ArrayList<>();
        String sql = """
                     SELECT TOP (1000) 
                         ta.[id],
                         ta.[full_name],
                         ta.[email],
                         ta.[cv_link],
                         ta.[status],
                         ta.[birth_date],
                         ta.[gender],
                         ta.[Expertise],
                         ta.[id_type_course],
                         tc.[name] AS type_course_name,
                         ta.[years_of_experience],
                         ta.[phone]
                     FROM TeacherApplications ta
                     JOIN type_course tc ON ta.id_type_course = tc.id
                     """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TeacherApplications t = new TeacherApplications();
                t.setId(rs.getInt("id"));
                t.setFullName(rs.getString("full_name"));
                t.setEmail(rs.getString("email"));
                t.setCvlink(rs.getString("cv_link"));
                t.setStatus(rs.getString("status"));
                t.setBirthDate(rs.getString("birth_date"));
                t.setGender(rs.getString("gender"));
                t.setExpertise(rs.getString("Expertise"));
                t.setIdTypeCourse(rs.getString("id_type_course"));
                t.setTypeCourseName(rs.getString("type_course_name"));
                t.setYearOfExpertise(rs.getString("years_of_experience"));
                t.setPhone(rs.getString("phone"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStatus(int id, String newStatus) {
        String sql = "UPDATE TeacherApplications SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEmailById(int id) {
        String sql = "SELECT email FROM TeacherApplications WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TeacherApplications> searchApplications(String keyword, String status, String typeId) {
        List<TeacherApplications> list = new ArrayList<>();
        String sql = """
        SELECT ta.*, tc.name AS type_course_name
        FROM TeacherApplications ta
        JOIN type_course tc ON ta.id_type_course = tc.id
        WHERE 1=1
    """;

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " AND (ta.full_name LIKE ? OR ta.email LIKE ?) ";
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND ta.status = ? ";
        }
        if (typeId != null && !typeId.isEmpty()) {
            sql += " AND ta.id_type_course = ? ";
        }

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            if (keyword != null && !keyword.trim().isEmpty()) {
                ps.setString(i++, "%" + keyword + "%");
                ps.setString(i++, "%" + keyword + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(i++, status);
            }
            if (typeId != null && !typeId.isEmpty()) {
                ps.setString(i++, typeId);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TeacherApplications t = new TeacherApplications();
                t.setId(rs.getInt("id"));
                t.setFullName(rs.getString("full_name"));
                t.setEmail(rs.getString("email"));
                t.setCvlink(rs.getString("cv_link"));
                t.setStatus(rs.getString("status"));
                t.setBirthDate(rs.getString("birth_date"));
                t.setGender(rs.getString("gender"));
                t.setExpertise(rs.getString("Expertise"));
                t.setIdTypeCourse(rs.getString("id_type_course"));
                t.setTypeCourseName(rs.getString("type_course_name"));
                t.setYearOfExpertise(rs.getString("years_of_experience"));
                t.setPhone(rs.getString("phone"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

// Lấy danh sách thể loại môn
    public List<TypeCourse> getAllTypeCourses() {
        List<TypeCourse> list = new ArrayList<>();
        String sql = "SELECT id, name FROM type_course";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                TypeCourse tc = new TypeCourse();
                tc.setId(rs.getString("id"));
                tc.setName(rs.getString("name"));
                list.add(tc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getPhoneById(int id) {
        String sql = "SELECT phone FROM TeacherApplications WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("phone");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertTeacher(String fullName, String email, String birthDate, String gender,
            String expertise, String typeId, String yearsExp, String phone,
            int idApplication, double salaryOffer) {

        String sql = """
        INSERT INTO Teacher
        (password, full_name, email, birth_date, gender, Expertise, 
         role_id, id_type_course, years_of_experience, phone, id_application, offer_salary)
        VALUES (?, ?, ?, ?, ?, ?,  2, ?, ?, ?, ?, ?)
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);        // Mật khẩu mặc định là số điện thoại
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, birthDate);
            ps.setString(5, gender);
            ps.setString(6, expertise);
            ps.setString(7, typeId);
            ps.setString(8, yearsExp);
            ps.setString(9, phone);
            ps.setInt(10, idApplication);
            ps.setDouble(11, salaryOffer);

            ps.executeUpdate();
            System.out.println("✅ Đã thêm giáo viên thành công: " + fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
