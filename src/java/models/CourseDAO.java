/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Quang
 */
//Dương_Homepage
public class CourseDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Courses> getAllCourses() {

        ArrayList<Courses> data = new ArrayList<>();
        try {
            String strSQL = "SELECT \n"
                    + "    c.id, \n"
                    + "    c.name, \n"
                    + "    t.name AS type_name, \n"
                    + "    c.description, \n"
                    + "    c.fee\n, "
                    + "    c.image\n"
                    + "FROM Course c\n"
                    + "JOIN type_course t ON c.type_id = t.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type = rs.getString(3);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                String picture = rs.getString(6);

                Courses p = new Courses(id, name, type, description, fee, picture);
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
            String strSQL = "SELECT TOP 6 \n"
                    + "    c.id, \n"
                    + "    c.name, \n"
                    + "    t.name AS type_name, \n"
                    + "    c.description, \n"
                    + "    c.fee\n, "
                    + "    c.image\n"
                    + "FROM Course c\n"
                    + "JOIN type_course t ON c.type_id = t.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type = rs.getString(3);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                String picture = rs.getString(6);

                Courses p = new Courses(id, name, type, description, fee, picture);
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

    //quang - quản lý khóa học
    public ArrayList<Courses> getCourses() {

        ArrayList<Courses> data = new ArrayList<>();
        try {
            String strSQL = "select * from Course c join type_course t on c.type_id= t.id ";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String type = rs.getString(8);
                String description = rs.getString(4);
                String fee = rs.getString(5);
                String image = rs.getString(6);

                Courses p = new Courses(id, name, type, description, fee, image);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getCourses" + e.getMessage());

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
                String image = rs.getString(6);
                Courses p = new Courses(id, name, type, description, fee, image);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getCoursesById" + e.getMessage());

        }
        return null;
    }

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

    public ResultMessage update(Courses s) {
        // Kiểm tra đầu vào
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
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        // Kiểm tra định dạng ID
        int courseId;
        try {
            courseId = Integer.parseInt(s.id);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID khóa học phải là một số hợp lệ: " + s.id);
        }

        // Kiểm tra định dạng type_id
        int typeId;
        try {
            typeId = Integer.parseInt(s.type);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID loại khóa học phải là một số hợp lệ: " + s.type);
        }

        // Kiểm tra định dạng fee
        double fee;
        try {
            fee = Double.parseDouble(s.fee);
            if (fee < 0) {
                return new ResultMessage(false, "Phí khóa học không được là số âm.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Phí khóa học phải là một số hợp lệ: " + s.fee);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE Course SET name = ?, type_id = ?, description = ?, fee = ?, image = ? WHERE id = ?")) {
            // Kiểm tra tên khóa học trùng với khóa học khác (ngoại trừ chính nó)
            if (isCourseNameExistForOther(s.name, courseId)) {
                return new ResultMessage(false, "Tên khóa học '" + s.name + "' đã được sử dụng bởi khóa học khác.");
            }
            stm.setString(1, s.name);
            stm.setInt(2, typeId);
            stm.setString(3, s.description);
            stm.setDouble(4, fee);
            stm.setString(5, s.picture != null ? s.picture : ""); // Xử lý image null
            stm.setInt(6, courseId);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Cập nhật khóa học thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy khóa học với ID: " + s.id);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong update: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // Kiểm tra tên khóa học đã tồn tại cho khóa học khác chưa
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
        // Kiểm tra đầu vào
        if (p == null || p.name == null || p.name.isEmpty()) {
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
        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        // Kiểm tra định dạng fee
        double fee;
        try {
            fee = Double.parseDouble(p.fee);
            if (fee < 0) {
                return new ResultMessage(false, "Phí khóa học không được là số âm.");
            }
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "Phí khóa học phải là một số hợp lệ: " + p.fee);
        }

        // Kiểm tra type_id
        int typeId;
        try {
            typeId = Integer.parseInt(p.type);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID loại khóa học phải là một số hợp lệ: " + p.type);
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO Course (name, type_id, description, fee, image) VALUES (?, ?, ?, ?, ?)")) {
            if (isCourseExist(p.name)) {
                return new ResultMessage(false, "Khóa học với tên '" + p.name + "' đã tồn tại.");
            }
            stm.setString(1, p.name);
            stm.setString(2, p.type);
            stm.setString(3, p.description);
            stm.setDouble(4, fee);
            stm.setString(5, p.picture != null ? p.picture : ""); // Xử lý image null
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Thêm khóa học thành công!");
            } else {
                return new ResultMessage(false, "Không thể thêm khóa học. Vui lòng thử lại.");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong add: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    public boolean isCourseExist(String name) throws SQLException {
        if (connection == null) {
            throw new SQLException("Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT COUNT(*) FROM Course WHERE name = ?")) {
            stm.setString(1, name);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Kiểm tra tên khóa học: " + name + " - Kết quả: " + count);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong isCourseExist: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        }
        return false;
    }

    public ResultMessage delete(String id) {
        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM Course WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Xóa khóa học thành công!");
            } else {
                return new ResultMessage(false, "Không tìm thấy khóa học với ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error in delete: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID không hợp lệ: " + id);
        }
    }
}
