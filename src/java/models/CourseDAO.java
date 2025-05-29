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
public class CourseDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Courses> getCourses() {

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
                tc.setId(rs.getInt("id"));
                tc.setName(rs.getString("name"));
                list.add(tc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<TypeCourse> getAllWithCourses() {
        List<TypeCourse> list = new ArrayList<>();
        String sql = """
                     SELECT tc.id AS type_id, tc.name AS type_name, c.id AS course_id, c.name AS course_name 
                                          FROM type_course tc LEFT JOIN course c ON tc.id = c.type_id 
                                          ORDER BY tc.id
                     """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Map<Integer, TypeCourse> map = new LinkedHashMap<>();

            while (rs.next()) {
                int typeId = rs.getInt("type_id");
                String typeName = rs.getString("type_name");

                TypeCourse tc = map.get(typeId);
                if (tc == null) {
                    tc = new TypeCourse();
                    tc.setId(typeId);
                    tc.setName(typeName);
                    tc.setCourse(new ArrayList<>());
                    map.put(typeId, tc);
                }

                String courseId = rs.getString("id");
                String courseName = rs.getString("name");

                if (courseName != null) {
                    Courses course = new Courses();
                    course.setId(courseId);
                    course.setName(courseName);
                    tc.getCourse().add(course);
                }
            }

            list.addAll(map.values());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
