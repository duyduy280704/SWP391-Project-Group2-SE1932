/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;

//Dương_Homepage
public class TeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    public ArrayList<Teachers> get4Teachers() {
        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "SELECT TOP 4 t.id, t.full_name, t.password, t.email, t.birth_date, t.years_of_experience, "
                    + "t.gender, t.Expertise, t.picture, r.name AS role_name "
                    + "FROM Teacher t JOIN role r ON t.role_id = r.id "
                    + "ORDER BY t.years_of_experience DESC";

            PreparedStatement stm = connection.prepareStatement(strSQL);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String fullName = rs.getString("full_name");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                byte[] picture = rs.getBytes("picture");
                String role = rs.getString("role_name");
                String idcourse = rs.getString("id_type_course");
                String yearexp = rs.getString("years_of_experience");
                String phones = rs.getString("phone");

                Teachers t = new Teachers(id, fullName, password, email, birthDate, gender, expertise, picture, role, idcourse, yearexp, phones);
                data.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    //Huyền-checklogin của teacher
    public Teachers checkLogin(String phone, String password) {
        try {
            String strSQL = "SELECT id, password, full_name, email, birth_date, gender, Expertise, picture, role_id,id_type_course,years_of_experience,phone "
                    + "FROM Teacher "
                    + "WHERE phone = ? AND password = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            stm.setString(2, password);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String pwd = rs.getString("password");
                String fullName = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String birthDate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                byte[] picture = rs.getBytes("picture");
                String roleId = String.valueOf(rs.getInt("role_id"));
                String idcourse = rs.getString("id_type_course");
                String yearexp = rs.getString("years_of_experience");
                String phones = rs.getString("phone");
                Teachers teacher = new Teachers(id, fullName, emailFromDB, pwd, birthDate, gender, expertise, picture, roleId, idcourse, yearexp, phones);
                return teacher;
            }
        } catch (Exception e) {
            System.out.println("checkLogin: " + e.getMessage());
        }
        return null;
    }

    //Quang
    public ArrayList<Teachers> getTeachers() {

        ArrayList<Teachers> data = new ArrayList<>();
        try {
            String strSQL = "select * from Teacher s join Role r on s.role_id = r.id join type_course t on s.id_type_course = t.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(3);
                String password = rs.getString(2);
                String email = rs.getString(4);
                String birthdate = rs.getString(5);
                String gender = rs.getString(6);
                String exp = rs.getString(7);
                byte[] pic = rs.getBytes(8);
                String role = rs.getString(13);
                String idcourse = rs.getString(10);
                String yearexp = rs.getString(11);
                String phones = rs.getString(12);
                Teachers p = new Teachers(id, name, email, password, birthdate, gender, exp, pic, role, idcourse, yearexp, phones);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getTeachers" + e.getMessage());

        }
        return data;
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

    public Teachers getByPhone(String phone) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, Expertise, picture, role_id, id_type_course, years_of_experience, phone "
                    + "FROM Teacher WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, phone);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("full_name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String birthdate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                byte[] picture = rs.getBytes("picture");
                String role = String.valueOf(rs.getInt("role_id"));
                String idcourse = rs.getString("id_type_course");
                String yearexp = rs.getString("years_of_experience");
                String phones = rs.getString("phone");
                return new Teachers(id, name, email, password, birthdate, gender, expertise, picture, role, idcourse, yearexp, phones);
            }
        } catch (Exception e) {
            System.out.println("getByPhone: " + e.getMessage());
        }
        return null;
    }

    public Teachers getByEmail(String email) {
        try {
            String strSQL = "SELECT id, full_name, email, password, birth_date, gender, Expertise, picture, role_id, id_type_course, years_of_experience, phone "
                    + "FROM Teacher WHERE email = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, email);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String name = rs.getString("full_name");
                String emailFromDB = rs.getString("email");
                String password = rs.getString("password");
                String birthdate = rs.getString("birth_date");
                String gender = rs.getString("gender");
                String expertise = rs.getString("Expertise");
                byte[] picture = rs.getBytes("picture");
                String role = String.valueOf(rs.getInt("role_id"));
                String idcourse = rs.getString("id_type_course");
                String yearexp = rs.getString("years_of_experience");
                String phone = rs.getString("phone");
                return new Teachers(id, name, emailFromDB, password, birthdate, gender, expertise, picture, role, idcourse, yearexp, phone);
            }
        } catch (Exception e) {
            System.out.println("getByEmail: " + e.getMessage());
        }
        return null;
    }

    public boolean updatePassword(String phone, String newPassword) {
        if (phone == null || newPassword == null) {
            System.out.println("updatePassword: Phone hoặc mật khẩu null");
            return false;
        }
        try {
            String strSQL = "UPDATE Teacher SET password = ? WHERE phone = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, newPassword);
            stm.setString(2, phone);
            int rowsAffected = stm.executeUpdate();
            System.out.println("updatePassword: Cập nhật " + rowsAffected + " hàng cho phone " + phone);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("updatePassword: Lỗi SQL - " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
            } catch (SQLException e) {
                System.err.println("updatePassword: Lỗi khi đóng statement - " + e.getMessage());
            }
        }
    }

    public boolean isPhoneExist(String phone) {
        String sql = "SELECT 1 FROM Teacher WHERE phone = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmailExist(String email) {
        String sql = "SELECT 1 FROM Teacher WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
