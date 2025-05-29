/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */

// Thuy-Thêm, sửa, xóa thời khóa biểu của admin 
public class ScheduleDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Schedules> getSchedules() {
        ArrayList<Schedules> data = new ArrayList<>();

        try {
            String strSQL = "SELECT \n"
                    + "    *\n"
                    + "FROM \n"
                    + "    schedule AS s\n"
                    + "JOIN \n"
                    + "    Class AS c ON s.id_class = c.id\n"
                    + "JOIN \n"
                    + "    TeacherApplications AS t ON s.id_teacher = t.id;";

            stm = connection.prepareStatement(strSQL);

            rs = stm.executeQuery();

            while (rs.next()) {

                String id = String.valueOf(rs.getInt(1));
                String nameClass = rs.getString(9);

                String start = rs.getString(4);
                String end = rs.getString(5);
                String day = rs.getString(6);
                String nameTeacher = rs.getString(13);
                String room = rs.getString(7);
                Schedules s = new Schedules(id, nameClass, start, end, day, nameTeacher, room);
                data.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi getAllSchedules: " + e.getMessage());
        } 

        System.out.println("Tìm thấy " + data.size() + " lịch học."); // Log ra để kiểm tra
        return data;
    }

    public static void main(String[] args) {
        ScheduleDAO dao = new ScheduleDAO(); // giả sử bạn có DAO đúng
        ArrayList<Schedules> courseList = dao.getSchedules();

        if (courseList.isEmpty()) {
            System.out.println("Không có khóa học nào.");
        } else {
            System.out.println("Danh sách khóa học:");
            for (Schedules c : courseList) {
                System.out.println(c);
            }
        }
    }

    public ArrayList<Schedules> getScheduleByName(String keyword) {
        ArrayList<Schedules> data = new ArrayList<>();

        try {
            String strSQL = "SELECT \n"
                    + "*\n"
                    + "FROM \n"
                    + "    schedule s\n"
                    + "JOIN \n"
                    + "    Class c ON s.id_class = c.id\n"
                    + "JOIN \n"
                    + "    TeacherApplications t ON s.id_teacher = t.id\n"
                    + "	WHERE c.name like ?";

            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + keyword + "%");
            rs = stm.executeQuery();

            while (rs.next()) {

                String id = String.valueOf(rs.getInt(1));
                String nameClass = rs.getString(9);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String day = rs.getString(6);
                String nameTeacher = rs.getString(13);
                String room = rs.getString(7);
                Schedules s = new Schedules(id, nameClass, start, end, day, nameTeacher, room);
                data.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi getAllSchedules: " + e.getMessage());
        }

        System.out.println("Tìm thấy " + data.size() + " lịch học."); // Log ra để kiểm tra
        return data;
    }

    public Schedules getSchedulesById(String id) {
        Schedules s = new Schedules();

        try {
            String strSQL = "SELECT *FROM  schedule Where id = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String sid = String.valueOf(rs.getInt(1));
                String nameClass = rs.getString(2);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String day = rs.getString(6);
                String nameTeacher = rs.getString(3);
                String room = rs.getString(7);
                s = new Schedules(sid, nameClass, start, end, day, nameTeacher, room);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi getAllSchedules: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng tài nguyên: " + e.getMessage());
            }
        }
        return s;

    }

    public void delete(String id) {

        try {
            String strSQL = "delete from schedule where id = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            stm.execute();
        } catch (Exception e) {
            System.out.println("delete: " + e.getMessage());
        }

    }

    public ArrayList<Categories_class> getCategories_class() {
        ArrayList<Categories_class> data1 = new ArrayList<Categories_class>();
        try {
            String strSQL = "SELECT id, name FROM  Class;";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id_class = String.valueOf(rs.getInt(1));
                String name_class = rs.getString(2);
                Categories_class s = new Categories_class(id_class, name_class);
                data1.add(s);
            }
        } catch (Exception e) {
            System.out.println("getCategories : " + e.getMessage());
        }
        return data1;
    }

    public ArrayList<CategoriesCourse> getCategoriesCourse() {
        ArrayList<CategoriesCourse> data2 = new ArrayList<CategoriesCourse>();
        try {
            String strSQL = "SELECT id, name FROM  Course";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String IDCourse = String.valueOf(rs.getInt(1));
                String nameCourse = rs.getString(2);
                CategoriesCourse s = new CategoriesCourse(IDCourse, nameCourse);
                data2.add(s);
            }
        } catch (Exception e) {
            System.out.println("getCategories : " + e.getMessage());
        }
        return data2;
    }

    public ArrayList<CategoriesTeacher> getCategoriesTeacher() {
        ArrayList<CategoriesTeacher> data3 = new ArrayList<CategoriesTeacher>();
        try {
            String strSQL = "select id, full_name from Teacher;";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String IDTeacher = String.valueOf(rs.getInt(1));
                String nameTeacher = rs.getString(2);
                CategoriesTeacher s = new CategoriesTeacher(IDTeacher, nameTeacher);
                data3.add(s);
            }
        } catch (Exception e) {
            System.out.println("getCategories : " + e.getMessage());
        }
        return data3;
    }

    public void update(Schedules s) {
        try {
            String strSQL = "Update schedule set id_class=?, start_time=?,end_time=?,day=?,id_teacher=?, room=?  where id = ?";
            stm = connection.prepareStatement(strSQL);

            stm.setString(1, s.getNameClass());
            stm.setString(2, s.getStartTime());
            stm.setString(3, s.getEndTime());
            stm.setString(4, s.getDay());
            stm.setString(5, s.getTeacher());
            stm.setString(6, s.getRoom());
            stm.setString(7, s.getId());

            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("update: " + e.getMessage());
        }
    }

    public void add(Schedules s) {
        try {
            String strSQL = "insert into schedule(id_class, start_time,end_time,day,id_teacher, room) values(?,?,?,?,?,?)";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, s.getNameClass());
            stm.setString(2, s.getStartTime());
            stm.setString(3, s.getEndTime());
            stm.setString(4, s.getDay());
            stm.setString(5, s.getTeacher());
            stm.setString(6, s.getRoom());
            stm.execute();
        } catch (Exception e) {
            System.out.println("add: " + e.getMessage());
        }
    }

   

    

}