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
                                    co.fee * COUNT(cs.student_id) AS tien_khoa_hoc
                                FROM Class c
                                JOIN Course co ON c.course_id = co.id
                                JOIN Class_Student cs ON c.id = cs.class_id
                                GROUP BY c.id, c.name, co.fee
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
                                s.amount AS [Số tiền],
                                s.pay_salary_date AS [Ngày]
                            FROM salary s
                            JOIN schedule sch ON s.id_class = sch.id_class AND s.id_teacher = sch.id_teacher
                            JOIN Class c ON sch.id_class = c.id
                            JOIN Teacher t ON s.id_teacher = t.id
                            JOIN ClassFee cf ON c.id = cf.class_id
                            GROUP BY s.id, t.full_name, cf.class_name, cf.tien_khoa_hoc, 
                                     s.commission_percent, s.bonus, s.penalty, s.note, s.amount, s.pay_salary_date
                            ORDER BY s.pay_salary_date DESC;
                            
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

    // Lấy danh sách tên lớp theo giáo viên
    public ArrayList<TeacherClass> getClassesByTeacher(int teacherId) {
        ArrayList<TeacherClass> data = new ArrayList<>();
        try {
            String strSQL = """
                            SELECT DISTINCT 
                                s.id_teacher,
                                c.name AS class_name
                            FROM schedule s
                            JOIN Class c ON s.id_class = c.id
                            WHERE s.id_teacher = ?
                            ORDER BY c.name;
                            """;
            stm = connection.prepareStatement(strSQL);
            stm.setInt(1, teacherId);
            rs = stm.executeQuery();
            while (rs.next()) {
                String teacherIdStr = String.valueOf(rs.getInt("id_teacher"));
                String className = rs.getString("class_name");
                TeacherClass tc = new TeacherClass(teacherIdStr, className);
                data.add(tc);
            }
        } catch (SQLException e) {
            System.err.println("getClassesByTeacher: " + e.getMessage());
        }
        return data;
    }

    // Lấy tiền khóa học theo tên lớp
    public String getCourseCost(String className) {
        String cost = null;
        try {
            String strSQL = """
                            SELECT 
                                (co.fee * COUNT(cs.student_id)) AS tien_khoa_hoc
                            FROM 
                                Class c
                                INNER JOIN Course co ON c.course_id = co.id
                                INNER JOIN Class_Student cs ON c.id = cs.class_id
                            WHERE 
                                c.name = ?
                            GROUP BY 
                                c.id, co.fee;
                            """;
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, className);
            rs = stm.executeQuery();
            if (rs.next()) {
                cost = rs.getString("tien_khoa_hoc");
            }
        } catch (SQLException e) {
            System.err.println("getCourseCost: " + e.getMessage());
        }
        return cost;
    }

    // Thêm lương giáo viên
    public ResultMessage addSalary(SalaryTeacher s) {

        try {

            if (s.getPer() == null || s.getPer().trim().isEmpty()) {
                return new ResultMessage(false, "Lỗi: % hoa hồng không được để trống.");
            }
            double commissionPercent;
            try {
                commissionPercent = Double.parseDouble(s.getPer());
            } catch (NumberFormatException e) {
                return new ResultMessage(false, "Lỗi: % hoa hồng phải là một số hợp lệ.");
            }
            if (commissionPercent <= 0 || commissionPercent >= 100) {
                return new ResultMessage(false, "Lỗi: % hoa hồng phải lớn hơn 0 và nhỏ hơn 100.");
            }

            double bonus = 0.0;
            if (s.getBonus() != null && !s.getBonus().trim().isEmpty()) {
                try {
                    bonus = Double.parseDouble(s.getBonus().trim());
                } catch (NumberFormatException e) {
                    return new ResultMessage(false, "Lỗi: Tiền thưởng phải là một số hợp lệ.");
                }
            }

            double penalty = 0.0;
            if (s.getPenalty() != null && !s.getPenalty().trim().isEmpty()) {
                try {
                    penalty = Double.parseDouble(s.getPenalty().trim());
                } catch (NumberFormatException e) {
                    return new ResultMessage(false, "Lỗi: Tiền phạt phải là một số hợp lệ.");
                }
            }

            String note = (s.getNote() != null && !s.getNote().trim().isEmpty()) ? s.getNote().trim() : "None";

            String costStr = getCourseCost(s.getClassName());
            double tienKhoaHoc;
            try {
                tienKhoaHoc = costStr != null ? Double.parseDouble(costStr) : 0.0;
            } catch (NumberFormatException e) {
                return new ResultMessage(false, "Lỗi: Tiền khóa học không hợp lệ.");
            }

            double amount = (tienKhoaHoc * (commissionPercent / 100.0)) + bonus - penalty;

            String sql = """
                     INSERT INTO salary (id_teacher, id_class, commission_percent, bonus, penalty, note, pay_salary_date, amount)
                     VALUES (?, (SELECT id FROM Class WHERE name = ?), ?, ?, ?, ?, ?, ?)
                     """;
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(s.getTeacher()));
            stm.setString(2, s.getClassName());
            stm.setDouble(3, commissionPercent);
            stm.setDouble(4, bonus);
            stm.setDouble(5, penalty);
            stm.setString(6, note);
            stm.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
            stm.setDouble(8, amount);
            stm.executeUpdate();
            return new ResultMessage(true, "Thêm lương thành công!");
        } catch (SQLException | NumberFormatException e) {
            return new ResultMessage(false, "Lỗi khi thêm lương: " + e.getMessage());
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
        }
    }

    // Cập nhật lương giáo viên
    public ResultMessage updateSalary(SalaryTeacher s) {

        try {

            if (s.getPer() == null || s.getPer().trim().isEmpty()) {
                return new ResultMessage(false, "Lỗi: % hoa hồng không được để trống.");
            }
            double commissionPercent;
            try {
                commissionPercent = Double.parseDouble(s.getPer().trim());
            } catch (NumberFormatException e) {
                return new ResultMessage(false, "Lỗi: % hoa hồng phải là một số hợp lệ.");
            }
            if (commissionPercent <= 0 || commissionPercent >= 100) {
                return new ResultMessage(false, "Lỗi: % hoa hồng phải lớn hơn 0 và nhỏ hơn 100.");
            }

            double bonus = 0.0;
            if (s.getBonus() != null && !s.getBonus().trim().isEmpty()) {
                try {
                    bonus = Double.parseDouble(s.getBonus().trim());
                } catch (NumberFormatException e) {
                    return new ResultMessage(false, "Lỗi: Tiền thưởng phải là một số hợp lệ.");
                }
            }

            double penalty = 0.0;
            if (s.getPenalty() != null && !s.getPenalty().trim().isEmpty()) {
                try {
                    penalty = Double.parseDouble(s.getPenalty().trim());
                } catch (NumberFormatException e) {
                    return new ResultMessage(false, "Lỗi: Tiền phạt phải là một số hợp lệ.");
                }
            }

            String note = (s.getNote() != null && !s.getNote().trim().isEmpty()) ? s.getNote().trim() : "None";

            String costStr = getCourseCost(s.getClassName());
            double tienKhoaHoc;
            try {
                tienKhoaHoc = costStr != null ? Double.parseDouble(costStr) : 0.0;
            } catch (NumberFormatException e) {
                return new ResultMessage(false, "Lỗi: Tiền khóa học không hợp lệ.");
            }

            double amount = (tienKhoaHoc * (commissionPercent / 100.0)) + bonus - penalty;

            String sql = """
                     UPDATE salary
                     SET id_teacher = ?, id_class = (SELECT id FROM Class WHERE name = ?),
                         commission_percent = ?, bonus = ?, penalty = ?, note = ?, pay_salary_date = ?, amount = ?
                     WHERE id = ?
                     """;
            stm = connection.prepareStatement(sql);
            stm.setInt(1, Integer.parseInt(s.getTeacher()));
            stm.setString(2, s.getClassName());
            stm.setDouble(3, commissionPercent);
            stm.setDouble(4, bonus);
            stm.setDouble(5, penalty);
            stm.setString(6, note);
            stm.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
            stm.setDouble(8, amount);
            stm.setInt(9, Integer.parseInt(s.getId()));
            stm.executeUpdate();
            return new ResultMessage(true, "Cập nhật lương thành công!");
        } catch (SQLException | NumberFormatException e) {
            return new ResultMessage(false, "Lỗi khi cập nhật lương: " + e.getMessage());
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
        }
    }

    // Xóa lương giáo viên theo id
    public ResultMessage deleteSalary(String id) {
        PreparedStatement stm = null;
        try {
            // Validate id
            if (id == null || id.trim().isEmpty()) {
                return new ResultMessage(false, "Lỗi: Mã lương không được để trống.");
            }
            int salaryId;
            try {
                salaryId = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                return new ResultMessage(false, "Lỗi: Mã lương phải là một số hợp lệ.");
            }

            String sql = "DELETE FROM salary WHERE id = ?";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, salaryId);
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Xóa lương thành công!");
            } else {
                return new ResultMessage(false, "Lỗi: Không tìm thấy lương với mã " + id);
            }
        } catch (SQLException e) {
            return new ResultMessage(false, "Lỗi khi xóa lương: " + e.getMessage());
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    System.err.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
        }
    }

    // Lấy lương giáo viên theo id
    public SalaryTeacher getSalaryById(String id) {
        SalaryTeacher s = null;
        try {
            String sql = """
                SELECT 
                    s.id,
                    s.id_teacher,
                    t.full_name AS [Tên giáo viên],
                    cf.class_name AS [Tên lớp],
                    cf.tien_khoa_hoc AS [Tiền khóa học],
                    s.commission_percent AS [% hoa hồng],
                    s.bonus AS [Tiền thưởng],
                    s.penalty AS [Tiền phạt],
                    s.note AS [Ghi chú],
                    s.amount AS [Số tiền],
                    s.pay_salary_date AS [Ngày]
                FROM salary s
                INNER JOIN schedule sch ON s.id_class = sch.id_class AND s.id_teacher = sch.id_teacher
                INNER JOIN Class c ON sch.id_class = c.id
                INNER JOIN Teacher t ON s.id_teacher = t.id
                INNER JOIN (
                    SELECT 
                        c.id AS class_id,
                        c.name AS class_name,
                        co.fee * COUNT(cs.student_id) AS tien_khoa_hoc
                    FROM Class c
                    INNER JOIN Course co ON c.course_id = co.id
                    INNER JOIN Class_Student cs ON c.id = cs.class_id
                    GROUP BY c.id, c.name, co.fee
                ) cf ON c.id = cf.class_id
                WHERE s.id = ?
                """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new SalaryTeacher(
                        String.valueOf(rs.getInt(1)),
                        rs.getString(2),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11)
                );
            }
        } catch (Exception e) {
            System.out.println("getSalaryById: " + e.getMessage());
        }
        return s;
    }

    // Tìm lương giáo viên theo tên giáo viên
    public ArrayList<SalaryTeacher> getSalaryByTeacherName(String teacherName) {
        ArrayList<SalaryTeacher> data = new ArrayList<>();
        try {
            String strSQL = """
                            WITH ClassFee AS (
                                SELECT 
                                    c.id AS class_id,
                                    c.name AS class_name,
                                    co.fee * COUNT(cs.student_id) AS tien_khoa_hoc
                                FROM Class c
                                JOIN Course co ON c.course_id = co.id
                                JOIN Class_Student cs ON c.id = cs.class_id
                                GROUP BY c.id, c.name, co.fee
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
                                s.amount AS [Số tiền],
                                s.pay_salary_date AS [Ngày]
                            FROM salary s
                            JOIN schedule sch ON s.id_class = sch.id_class AND s.id_teacher = sch.id_teacher
                            JOIN Class c ON sch.id_class = c.id
                            JOIN Teacher t ON s.id_teacher = t.id
                            JOIN ClassFee cf ON c.id = cf.class_id
                            WHERE t.full_name LIKE ?
                            GROUP BY s.id, t.full_name, cf.class_name, cf.tien_khoa_hoc, 
                                     s.commission_percent, s.bonus, s.penalty, s.note, s.amount, s.pay_salary_date
                            ORDER BY s.pay_salary_date DESC;
                            """;
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + teacherName + "%"); // Tìm kiếm không phân biệt chính xác
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
            System.out.println("getSalaryByTeacherName: " + e.getMessage());
        }
        return data;
    }

    // Lọc lương giáo viên theo tên lớp
    public ArrayList<SalaryTeacher> getSalaryByClassName(String className) {
        ArrayList<SalaryTeacher> data = new ArrayList<>();
        try {
            String strSQL = """
                            WITH ClassFee AS (
                                SELECT 
                                    c.id AS class_id,
                                    c.name AS class_name,
                                    co.fee * COUNT(cs.student_id) AS tien_khoa_hoc
                                FROM Class c
                                JOIN Course co ON c.course_id = co.id
                                JOIN Class_Student cs ON c.id = cs.class_id
                                GROUP BY c.id, c.name, co.fee
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
                                s.amount AS [Số tiền],
                                s.pay_salary_date AS [Ngày]
                            FROM salary s
                            JOIN schedule sch ON s.id_class = sch.id_class AND s.id_teacher = sch.id_teacher
                            JOIN Class c ON sch.id_class = c.id
                            JOIN Teacher t ON s.id_teacher = t.id
                            JOIN ClassFee cf ON c.id = cf.class_id
                            WHERE cf.class_name = ?
                            GROUP BY s.id, t.full_name, cf.class_name, cf.tien_khoa_hoc, 
                                     s.commission_percent, s.bonus, s.penalty, s.note, s.amount, s.pay_salary_date
                            ORDER BY s.pay_salary_date DESC;
                            """;
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, className);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String teacher = rs.getString(2);
                String classNameResult = rs.getString(3);
                String cost = rs.getString(4);
                String per = rs.getString(5);
                String bonus = rs.getString(6);
                String penalty = rs.getString(7);
                String note = rs.getString(8);
                String salary = rs.getString(9);
                String date = rs.getString(10);

                SalaryTeacher p = new SalaryTeacher(id, teacher, classNameResult, cost, per, bonus, penalty, note, salary, date);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getSalaryByClassName: " + e.getMessage());
        }
        return data;
    }

    // Lấy danh sách tất cả các lớp từ bảng Class
    public ArrayList<TeacherClass> getAllClasses() {
        ArrayList<TeacherClass> data = new ArrayList<>();
        try {
            String strSQL = "SELECT [id], [name] AS class_name FROM [BIGDREAM].[dbo].[Class]";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String classId = String.valueOf(rs.getInt("id"));
                String className = rs.getString("class_name");
                TeacherClass tc = new TeacherClass(classId, className); // Sử dụng classId thay vì teacherId
                data.add(tc);
            }
        } catch (SQLException e) {
            System.err.println("getAllClasses: " + e.getMessage());
        }
        return data;
    }
}
