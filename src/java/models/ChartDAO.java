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

    PreparedStatement stm;
    ResultSet rs;
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

// tổng doanh thu theo từng tháng
    public List<Revenue> getMonthlyRevenue() {
        List<Revenue> revenueList = new ArrayList<>();
        String sql = """
                     SELECT DATEPART(year, p.date) AS year, DATEPART(month, p.date) AS month, SUM(c.fee ) AS revenue 
                        FROM payment p 
                        JOIN Course c ON p.id_course = c.id                      	
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

// Lấy số học sinh đăng ký theo tháng (phân loại đã duyệt và đang chờ)
    public List<StudentRegistration> getStudentRegistrationByMonth() {
        List<StudentRegistration> studentList = new ArrayList<>();
        String sql = """
                     SELECT 
                        DATEPART(year, register_date) AS year, 
                        DATEPART(month, register_date) AS month, 
                        status, 
                        COUNT(*) AS count 
                        FROM PreRegistrations
                        GROUP BY DATEPART(year, register_date), DATEPART(month, register_date), status
                     """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            Map<String, StudentRegistration> monthData = new HashMap<>();
            while (rs.next()) {
                String month = rs.getInt("year") + "-" + String.format("%02d", rs.getInt("month"));
                String status = rs.getString("status");
                int count = rs.getInt("count");
                monthData.computeIfAbsent(month, k -> new StudentRegistration(month, 0, 0))
                        .updateCount(status, count);
            }
            studentList.addAll(monthData.values());
        } catch (SQLException e) {
            System.err.println("getStudentRegistrationByMonth: " + e.getMessage());
        }
        return studentList;
    }
    
  // Lấy số học sinh đang học theo thể loại khóa học
    public List<TypeStudentCount> getStudentCountByType() {
        List<TypeStudentCount> list = new ArrayList<>();
        String sql = """
                     SELECT tc.id, tc.name, COUNT(DISTINCT r.student_id) AS student_count
                     FROM [BIGDREAM].[dbo].[regisition] r
                     JOIN [BIGDREAM].[dbo].[Course] c ON r.course_id = c.id
                     JOIN [BIGDREAM].[dbo].[type_course] tc ON c.type_id = tc.id
                     GROUP BY tc.id, tc.name
                     """;

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int typeId = rs.getInt("id");
                String typeName = rs.getString("name");
                int studentCount = rs.getInt("student_count");
                list.add(new TypeStudentCount(typeId, typeName, studentCount));
            }
        } catch (SQLException e) {
            System.err.println("getStudentCountByType: " + e.getMessage());
        }
        return list;
    }
    

    // hiển thị thông báo
    public ArrayList<NoticeToStaff> getNoticeToStaffById(String id) {
        ArrayList<NoticeToStaff> list = new ArrayList<>();
        try {
            String strSQL = "SELECT message, date FROM NoticeToStaff WHERE id_staff = ? ";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String mess = rs.getString("message");
                String date = rs.getString("date");
                NoticeToStaff p = new NoticeToStaff(id, mess, date);
                list.add(p);
            }
        } catch (Exception e) {
            System.out.println("getCoursesById" + e.getMessage());
        }
        return list;
    }

}