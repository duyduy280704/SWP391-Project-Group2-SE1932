/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Quang
 */
public class SalaryTeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    //lấy danh sách lương giáo viên
    public ArrayList<SalaryTeacher> getSalaryList() {

        ArrayList<SalaryTeacher> data = new ArrayList<>();
        try {
            String strSQL = """
                            WITH ClassFee AS (
                                SELECT 
                                    c.id AS class_id,
                                    c.name AS class_name,
                                    co.fee AS fee_per_student,
                                    (co.fee * COUNT(cs.student_id)) AS tien_khoa_hoc
                                FROM 
                                    Class c
                                    INNER JOIN Course co ON c.course_id = co.id
                                    INNER JOIN Class_Student cs ON c.id = cs.class_id
                                GROUP BY 
                                    c.id,
                                    c.name,
                                    co.fee
                            )
                            SELECT 
                                s.id,
                                t.full_name AS [Tên giáo viên],
                                cf.class_name AS [Tên lớp],
                                cf.tien_khoa_hoc AS [Tiền khóa học],
                                s.commission_percent AS [% hoa hồng],
                                s.bonus AS [Tiền thưởng],
                                s.penalty AS [Tiền phạt],
                                s.note AS [Ghi chú],
                                ((cf.tien_khoa_hoc * (s.commission_percent / 100)) + s.bonus - s.penalty) AS [Tổng lương],
                                s.pay_salary_date AS [Ngày]
                            FROM 
                                salary s
                                INNER JOIN schedule sch ON s.id_class = sch.id_class AND s.id_teacher = sch.id_teacher
                                INNER JOIN Class c ON sch.id_class = c.id
                                INNER JOIN Teacher t ON s.id_teacher = t.id
                                INNER JOIN ClassFee cf ON c.id = cf.class_id
                            GROUP BY 
                                s.id,
                                t.full_name,
                                cf.class_name,
                                cf.tien_khoa_hoc,
                                s.commission_percent,
                                s.bonus,
                                s.penalty,
                                s.note,
                                s.pay_salary_date;
                            
                            """;
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String teacher = rs.getString(2);
                String className = rs.getString(3);
                String cost = rs.getString(4);
                String per = rs.getString(5);
                String bonus = rs.getString(6);
                String penalty = rs.getString(7);
                String note = rs.getString(8);
                String salary = rs.getString(9);
                String date = rs.getString(10);

                SalaryTeacher p = new SalaryTeacher(id, teacher, className, cost, per, bonus, penalty, note, salary, date);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getSalaryList" + e.getMessage());

        }
        return data;
    }

// lấy danh sách giáo viên
    public ArrayList<Teachers> getTeacherList() {
        ArrayList<Teachers> data = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT TOP 1000 id, full_name FROM Teacher;")) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String code = String.valueOf(rs.getInt("id"));
                String name = rs.getString("full_name");
                Teachers c = new Teachers(code, name);
                data.add(c);
            }
        } catch (SQLException e) {
            System.err.println("getTeacherList: " + e.getMessage());
        }
        return data;
    }

}
