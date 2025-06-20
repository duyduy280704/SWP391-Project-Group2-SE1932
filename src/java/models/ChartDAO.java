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
public class ChartDAO extends DBContext {
// đếm khóa học theo kiểu khóa học
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
// đếm số khóa học
    public int getCourseCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS CourseCount FROM Course";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        }
    }
// đếm số học sinh
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
// đếm số giáo viên
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
// đếm số nhân viên
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
// số liệu biểu đồ người dung theo từng vai trò
    public List<Map<String, Object>> getRoleCounts() throws SQLException {
        List<Map<String, Object>> roleCounts = new ArrayList<>();
        try {
            // Đếm số lượng từ các bảng
            int studentCount = getStudentCount();
            int teacherCount = getTeacherCount();
            int adminStaffCount = getAdminStaffCount();

            // Thêm vào danh sách
            Map<String, Object> studentMap = new HashMap<>();
            studentMap.put("role", "Học sinh");
            studentMap.put("count", studentCount);
            roleCounts.add(studentMap);

            Map<String, Object> teacherMap = new HashMap<>();
            teacherMap.put("role", "Giáo viên");
            teacherMap.put("count", teacherCount);
            roleCounts.add(teacherMap);

            Map<String, Object> adminMap = new HashMap<>();
            adminMap.put("role", "Nhân viên");
            adminMap.put("count", adminStaffCount);
            roleCounts.add(adminMap);

            return roleCounts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
// tổng doanh thu theo từng tháng
    public List<Revenue> getMonthlyRevenue() {
        List<Revenue> revenueList = new ArrayList<>();
        String sql = """
                     SELECT DATEPART(year, p.date) AS year, DATEPART(month, p.date) AS month, SUM(c.fee - c.sale) AS revenue 
                        FROM payment p 
                        JOIN Course c ON p.id_course = c.id 
                     	where p.status = N'Hoàn tất'
                        GROUP BY DATEPART(year, p.date), DATEPART(month, p.date)
                     """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Revenue revenue = new Revenue();
                // Ghép năm và tháng thành định dạng "YYYY-MM"
                String month = rs.getInt("year") + "-" + String.format("%02d", rs.getInt("month"));
                revenue.setMonth(month);
                revenue.setRevenue(rs.getDouble("revenue"));
                revenueList.add(revenue);
            }
        } catch (SQLException e) {
            System.err.println("getMonthlyRevenue: " + e.getMessage());
        }

        return revenueList;
    }
}
