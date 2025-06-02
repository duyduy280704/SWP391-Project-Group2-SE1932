/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Quang
 */
public class CourseDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Courses> getCourses() {
        ArrayList<Courses> data = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT c.id, c.name, t.name AS type_name, c.description, c.fee, c.image, c.level, c.sale "
                + "FROM Course c JOIN type_course t ON c.type_id = t.id")) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("name");
                String type = rs.getString("type_name");
                String description = rs.getString("description");
                String fee = String.valueOf(rs.getDouble("fee"));
                byte[] image = rs.getBytes("image");
                String level = rs.getString("level");
                String sale = String.valueOf(rs.getDouble("sale"));
                Courses p = new Courses(id, name, type, description, fee, image, level, sale);
                data.add(p);
            }
        } catch (SQLException e) {
            System.err.println("getCourses: " + e.getMessage());
        }
        return data;
    }

    public Courses getCoursesById(String id) {
        try {
            String strSQL = "select * from Course where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                String name = rs.getString(2);
                String type = rs.getString(3);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                byte[] image = rs.getBytes(6);
                String level = rs.getString(7);
                String sale = rs.getString(8);
                Courses p = new Courses(id, name, type, description, fee, image, level, sale);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getCoursesById" + e.getMessage());

        }
        return null;
    }

    public ArrayList<TypeCourse> getCourseType() {
        ArrayList<TypeCourse> data = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM type_course")) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String code = String.valueOf(rs.getInt("id"));
                String name = rs.getString("name");
                TypeCourse c = new TypeCourse(code, name);
                data.add(c);
            }
        } catch (SQLException e) {
            System.err.println("getCourseType: " + e.getMessage());
        }
        return data;
    }

    public ResultMessage update(Courses s) {
        if (s == null) {
            return new ResultMessage(false, "Dữ liệu khóa học không hợp lệ.");
        }
        if (s.id == null || s.id.isEmpty()) {
            return new ResultMessage(false, "ID khóa học không được để trống.");
        }
        if (s.name == null || s.name.isEmpty()) {
            return new ResultMessage(false, "Tên khóa học không được để trống.");
        }
        if (s.type == null || s.type.isEmpty()) {
            return new ResultMessage(false, "Loại khóa học không được để trống.");
        }
        if (s.description == null || s.description.isEmpty()) {
            return new ResultMessage(false, "Mô tả khóa học không được để trống.");
        }
        if (s.fee == null || s.fee.isEmpty()) {
            return new ResultMessage(false, "Phí khóa học không được để trống.");
        }
        if (s.level == null || s.level.isEmpty()) {
            return new ResultMessage(false, "Cấp độ khóa học không được để trống.");
        }
        if (s.sale == null || s.sale.isEmpty()) {
            return new ResultMessage(false, "Giá trị khuyến mãi không được để trống.");
        }
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        int courseId;
        try {
            courseId = Integer.parseInt(s.id);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID khóa học phải là một số hợp lệ: " + s.id);
        }

        int typeId;
        try {
            typeId = Integer.parseInt(s.type);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID loại khóa học phải là một số hợp lệ: " + s.type);
        }

        double fee;
        try {
            fee = Double.parseDouble(s.fee);
            if (fee < 0) {
                return new ResultMessage(false, "Phí khóa học không được là số âm.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Phí khóa học phải là một số hợp lệ: " + s.fee);
        }

        double sale;
        try {
            sale = Double.parseDouble(s.sale);
            if (sale < 0 || sale > 100) {
                return new ResultMessage(false, "Giá trị khuyến mãi phải nằm trong khoảng từ 0 đến 100: " + s.sale);
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Giá trị khuyến mãi phải là một số hợp lệ: " + s.sale);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE Course SET name = ?, type_id = ?, description = ?, fee = ?, image = ?, level = ?, sale = ? WHERE id = ?")) {
            if (isCourseNameExistForOther(s.name, courseId)) {
                return new ResultMessage(false, "Tên khóa học '" + s.name + "' đã được sử dụng bởi khóa học khác.");
            }
            stm.setString(1, s.name);
            stm.setInt(2, typeId);
            stm.setString(3, s.description);
            stm.setDouble(4, fee);
            if (s.image != null) {
                stm.setBytes(5, s.image);
            } else {
                stm.setNull(5, Types.BLOB);
            }
            stm.setString(6, s.level);
            stm.setDouble(7, sale);
            stm.setInt(8, courseId);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Cập nhật khóa học thành công!" : "Không tìm thấy khóa học với ID: " + s.id);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    private boolean isCourseNameExistForOther(String name, int excludeId) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT COUNT(*) FROM Course WHERE name = ? AND id != ?")) {
            stm.setString(1, name);
            stm.setInt(2, excludeId);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Kiểm tra tên khóa học: " + name + " (ngoại trừ ID: " + excludeId + ") - Kết quả: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong isCourseNameExistForOther: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
        return false;
    }

    public ResultMessage add(Courses p) {
        if (p == null) {
            return new ResultMessage(false, "Dữ liệu khóa học không hợp lệ.");
        }
        if (p.name == null || p.name.isEmpty()) {
            return new ResultMessage(false, "Tên khóa học không được để trống.");
        }
        if (p.type == null || p.type.isEmpty()) {
            return new ResultMessage(false, "Loại khóa học không được để trống.");
        }
        if (p.description == null || p.description.isEmpty()) {
            return new ResultMessage(false, "Mô tả khóa học không được để trống.");
        }
        if (p.fee == null || p.fee.isEmpty()) {
            return new ResultMessage(false, "Phí khóa học không được để trống.");
        }
        if (p.level == null || p.level.isEmpty()) {
            return new ResultMessage(false, "Cấp độ khóa học không được để trống.");
        }
        if (p.sale == null || p.sale.isEmpty()) {
            return new ResultMessage(false, "Giá trị khuyến mãi không được để trống.");
        }
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        int typeId;
        try {
            typeId = Integer.parseInt(p.type);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID loại khóa học phải là một số hợp lệ: " + p.type);
        }

        double fee;
        try {
            fee = Double.parseDouble(p.fee);
            if (fee < 0) {
                return new ResultMessage(false, "Phí khóa học không được là số âm.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Phí khóa học phải là một số hợp lệ: " + p.fee);
        }

        double sale;
        try {
            sale = Double.parseDouble(p.sale);
            if (sale < 0 || sale > 100) {
                return new ResultMessage(false, "Giá trị khuyến mãi phải nằm trong khoảng từ 0 đến 100: " + p.sale);
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Giá trị khuyến mãi phải là một số hợp lệ: " + p.sale);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO Course (name, type_id, description, fee, image, level, sale) VALUES (?, ?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            if (isCourseExist(p.name)) {
                return new ResultMessage(false, "Khóa học với tên '" + p.name + "' đã tồn tại.");
            }
            stm.setString(1, p.name);
            stm.setInt(2, typeId);
            stm.setString(3, p.description);
            stm.setDouble(4, fee);
            if (p.image != null) {
                stm.setBytes(5, p.image);
            } else {
                stm.setNull(5, Types.BLOB);
            }
            stm.setString(6, p.level);
            stm.setDouble(7, sale);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = stm.getGeneratedKeys();
                if (rs.next()) {
                    p.id = String.valueOf(rs.getInt(1)); // Set generated ID
                }
                return new ResultMessage(true, "Thêm khóa học thành công!");
            } else {
                return new ResultMessage(false, "Không thể thêm khóa học. Vui lòng thử lại.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong add: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public boolean isCourseExist(String name) throws SQLException {
        try (PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM Course WHERE name = ?")) {
            stm.setString(1, name);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Kiểm tra tên khóa học: " + name + " - Kết quả: " + count);
                return count > 0;
            }
        }
        return false;
    }

    public ResultMessage delete(String id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM Course WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Xóa khóa học thành công!" : "Không tìm thấy khóa học với ID: " + id);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong delete: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID không hợp lệ: " + id);
        }
    }

    public byte[] getCourseImageById(String courseId) throws SQLException {
        if (courseId == null || courseId.isEmpty()) {
            return null;
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT image FROM Course WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(courseId));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getBytes("image");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong getCourseImageById: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("Invalid course ID: " + courseId);
            throw e;
        }
        return null;
    }

    public ArrayList<TypeCourseCount> getCourseCountByType() {
        ArrayList<TypeCourseCount> list = new ArrayList<>();
        String sql = "SELECT t.name AS type_name, COUNT(c.id) AS course_count "
                + "FROM Course c JOIN type_course t ON c.type_id = t.id "
                + "GROUP BY t.name";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String typeName = rs.getString("type_name");
                int count = rs.getInt("course_count");
                list.add(new TypeCourseCount(typeName, count));
            }
        } catch (SQLException e) {
            System.err.println("getCourseCountByType: " + e.getMessage());
        }
        return list;
    }

    public int getStudentCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Student";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public int getTeacherCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Teacher";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }

    public int getAdminStaffCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Admin_staff";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
    
    public List<Map<String, Object>> getRoleCounts() throws SQLException {
        List<Map<String, Object>> roleCounts = new ArrayList<>();
        try {
            // Đếm số lượng từ các bảng
            int studentCount = getStudentCount();
            int teacherCount = getTeacherCount();
            int adminStaffCount = getAdminStaffCount();

            // Thêm vào danh sách
            Map<String, Object> studentMap = new HashMap<>();
            studentMap.put("role", "Students");
            studentMap.put("count", studentCount);
            roleCounts.add(studentMap);

            Map<String, Object> teacherMap = new HashMap<>();
            teacherMap.put("role", "Teachers");
            teacherMap.put("count", teacherCount);
            roleCounts.add(teacherMap);

            Map<String, Object> adminMap = new HashMap<>();
            adminMap.put("role", "Admin Staff");
            adminMap.put("count", adminStaffCount);
            roleCounts.add(adminMap);

            return roleCounts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
