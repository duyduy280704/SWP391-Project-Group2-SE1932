/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Quang
 */
public class CourseDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

// lấy toàn bộ khóa học
    public ArrayList<Courses> getCourses() {
        ArrayList<Courses> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM Course c JOIN type_course t ON c.type_id = t.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type = rs.getString(10);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                byte[] image = rs.getBytes(6);
                String level = rs.getString(7);
                String numberOfSessions = rs.getString(8);

                Courses p = new Courses(id, name, type, description, fee, image, level, numberOfSessions);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getCourses: " + e.getMessage());
        }
        return data;
    }

    // lấy khóa học bằng id
    public Courses getCoursesById(String id) {
        try {
            String strSQL = "SELECT * FROM Course WHERE id=?";
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
                String numberOfSessions = rs.getString(8);

                Courses p = new Courses(id, name, type, description, fee, image, level, numberOfSessions);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getCoursesById: " + e.getMessage());
        }
        return null;
    }

    // lấy kiểu khóa học
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

    // sửa khóa học
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
        if (s.number == null || s.number.isEmpty()) {
            return new ResultMessage(false, "Số buổi học không được để trống.");
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

        int numberOfSessions;
        try {
            numberOfSessions = Integer.parseInt(s.number);
            if (numberOfSessions < 1) {
                return new ResultMessage(false, "Số buổi học phải lớn hơn hoặc bằng 1.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Số buổi học phải là một số hợp lệ: " + s.number);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE Course SET name = ?, type_id = ?, description = ?, fee = ?, image = ?, level = ?, number_of_sessions = ? WHERE id = ?")) {
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
            stm.setString(7, s.number);
            stm.setInt(8, courseId);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Cập nhật khóa học thành công!" : "Không tìm thấy khóa học với ID: " + s.id);
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // check tên khóa học trùng nhau
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

    // thêm khóa học
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
        if (p.number == null || p.number.isEmpty()) {
            return new ResultMessage(false, "Số buổi học không được để trống.");
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

        int numberOfSessions;
        try {
            numberOfSessions = Integer.parseInt(p.number);
            if (numberOfSessions < 1) {
                return new ResultMessage(false, "Số buổi học phải lớn hơn hoặc bằng 1.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Số buổi học phải là một số hợp lệ: " + p.number);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO Course (name, type_id, description, fee, image, level, number_of_sessions) VALUES (?, ?, ?, ?, ?, ?, ?)",
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
            stm.setString(7, p.number);
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

    // check tên khóa học trùng nhau
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

    // xóa khóa học
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

    // lấy ảnh của khóa học theo id
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

    // tìm kiếm khóa theo tên 
    public ArrayList<Courses> getCourseByName(String name1) {
        ArrayList<Courses> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM Course c JOIN type_course t ON c.type_id = t.id WHERE c.name LIKE ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + name1 + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type = rs.getString(10);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                byte[] image = rs.getBytes(6);
                String level = rs.getString(7);
                String numberOfSessions = rs.getString(8);

                Courses p = new Courses(id, name, type, description, fee, image, level, numberOfSessions);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getCourseByName: " + e.getMessage());
        }
        return data;
    }

    // tìm kiếm khóa học theo thể loại
    public ArrayList<Courses> getCoursesByType(String type) {
        ArrayList<Courses> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM Course c JOIN type_course t ON c.type_id = t.id WHERE c.type_id = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, type);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type_id = rs.getString(10);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                byte[] image = rs.getBytes(6);
                String level = rs.getString(7);
                String numberOfSessions = rs.getString(8);

                Courses p = new Courses(id, name, type_id, description, fee, image, level, numberOfSessions);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getCoursesByType: " + e.getMessage());
        }
        return data;
    }
    
//Dương_Homepage

    public ArrayList<Courses> getAllCourses() {

        ArrayList<Courses> data = new ArrayList<>();
        try {
            String strSQL = """
                            SELECT
                                c.id, 
                                c.name, 
                                t.name AS type_name, 
                                c.description, 
                                c.fee,
                                c.image,
                                c.level
                            FROM Course c
                            JOIN type_course t ON c.type_id = t.id
                            """;
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type = rs.getString(3);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                byte[] image = rs.getBytes(6);
                String level = rs.getString(7);

                Courses p = new Courses(id, name, type, description, fee, image, level);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getCourses: " + e.getMessage());
        }
        return data;
    }
//Dương_Homepage

    public ArrayList<Courses> get6Courses() {

        ArrayList<Courses> data = new ArrayList<>();
        try {
            String strSQL = """
                            
                            SELECT TOP 6 
                                c.id, 
                                c.name, 
                                t.name AS type_name, 
                                c.description, 
                                c.fee, 
                                c.image,
                                c.level, 
                                
                                COUNT(r.course_id) AS num_registrations
                            FROM Course c
                            JOIN type_course t ON c.type_id = t.id
                            LEFT JOIN regisition r ON c.id = r.course_id
                            GROUP BY c.id, c.name, t.name, c.description, c.fee, c.image, c.level 
                            ORDER BY COUNT(r.course_id) DESC
                            
                            """;
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type = rs.getString(3);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                byte[] image = rs.getBytes(6);
                String level = rs.getString(7);

                Courses p = new Courses(id, name, type, description, fee, image, level);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("get6Courses: " + e.getMessage());
        }
        return data;
    }

    public List<TypeCourse> getType() {
        List<TypeCourse> list = new ArrayList<>();
        String sql = "SELECT id, name FROM type_course";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
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

    public ArrayList<Courses> searchCourses(String search, String type, String minPrice, String maxPrice) {
        ArrayList<Courses> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT c.id, c.name, t.name AS type_name, c.description, c.fee, c.image, c.level
            FROM Course c
            JOIN type_course t ON c.type_id = t.id
            WHERE 1=1
        """);

        ArrayList<Object> params = new ArrayList<>();

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND c.name LIKE ?");
            params.add("%" + search.trim() + "%");
        }

        if (type != null && !type.trim().isEmpty()) {
            sql.append(" AND t.name = ?");
            params.add(type);
        }

        if (minPrice != null && !minPrice.trim().isEmpty()) {
            sql.append(" AND CAST(c.fee AS FLOAT) >= ?");
            params.add(Double.parseDouble(minPrice));
        }

        if (maxPrice != null && !maxPrice.trim().isEmpty()) {
            sql.append(" AND CAST(c.fee AS FLOAT) <= ?");
            params.add(Double.parseDouble(maxPrice));
        }

        try (PreparedStatement stm = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                stm.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Courses c = new Courses(
                            rs.getString("id"),
                            rs.getString("name"),
                            rs.getString("type_name"),
                            rs.getString("description"),
                            rs.getString("fee"),
                            rs.getBytes("image"),
                            rs.getString("level")
                    );
                    list.add(c);
                }
            }
        } catch (Exception e) {
            System.out.println("searchCourses: " + e.getMessage());
        }

        return list;
    }

    public Courses getCoursesById1(String id) {
        try {
            String strSQL = """ 
                            SELECT c.id, c.name, t.name AS type_name, c.description, c.fee, c.image, c.level, c.number_of_sessions 
                            FROM Course c
                            JOIN type_course t ON c.type_id = t.id
                            WHERE c.id = ?
                            """;
            try (PreparedStatement stm = connection.prepareStatement(strSQL)) {
                stm.setString(1, id);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        String typeName = rs.getString("type_name"); // dùng alias
                        String description = rs.getString("description");
                        String fee = rs.getString("fee");
                        byte[] image = rs.getBytes("image");
                        String level = rs.getString("level");
                        String number = rs.getString("number_of_sessions");

                        return new Courses(id, name, typeName, description, fee, image, level, number);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("getCoursesById: " + e.getMessage());
        }
        return null;
    }

}
