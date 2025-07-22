/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.time.Year;

/**
 *
 * @author Quang
 */
public class SalaryTeacherDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;

    // Lấy danh sách giáo viên
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

    // Lấy danh sách lương theo teacherId
    public ArrayList<SalaryTeacher> getSalaryListByTeacherId(int teacherId) {
        ArrayList<SalaryTeacher> salaries = new ArrayList<>();
        String sql = "SELECT s.id, t.full_name AS teacher, s.offer_salary, s.number_of_sessions, s.bonus, s.penalty, s.amount, s.note, s.month "
                + "FROM salaryTeacher s "
                + "JOIN Teacher t ON s.id_teacher = t.id "
                + "WHERE s.id_teacher = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, teacherId);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SalaryTeacher salary = new SalaryTeacher(
                        rs.getString("id"),
                        rs.getString("teacher"),
                        rs.getString("offer_salary"),
                        rs.getString("number_of_sessions"),
                        rs.getString("bonus"),
                        rs.getString("penalty"),
                        rs.getString("amount"),
                        rs.getString("note"),
                        rs.getString("month")
                );
                salaries.add(salary);
            }
        } catch (SQLException e) {
            System.err.println("getSalaryListByTeacherId: " + e.getMessage());
        }
        return salaries;
    }

    // Lấy danh sách lương theo teacherId và tháng/năm
    public ArrayList<SalaryTeacher> getSalaryListByTeacherIdAndMonthYear(int teacherId, String monthYear) {
        ArrayList<SalaryTeacher> salaries = new ArrayList<>();
        String sql = "SELECT s.id, t.full_name AS teacher, s.offer_salary, s.number_of_sessions, s.bonus, s.penalty, s.amount, s.note, s.month "
                + "FROM salaryTeacher s "
                + "JOIN Teacher t ON s.id_teacher = t.id "
                + "WHERE s.id_teacher = ? AND s.month = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, teacherId);
            stm.setString(2, monthYear);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SalaryTeacher salary = new SalaryTeacher(
                        rs.getString("id"),
                        rs.getString("teacher"),
                        rs.getString("offer_salary"),
                        rs.getString("number_of_sessions"),
                        rs.getString("bonus"),
                        rs.getString("penalty"),
                        rs.getString("amount"),
                        rs.getString("note"),
                        rs.getString("month")
                );
                salaries.add(salary);
            }
        } catch (SQLException e) {
            System.err.println("getSalaryListByTeacherIdAndMonthYear: " + e.getMessage());
        }
        return salaries;
    }

    // Tính số buổi dạy theo id_teacher, năm và tháng
    public int getSessionCountByTeacherMonth(int idTeacher, String monthYear) {
        int totalSessions = 0;
        // Tách year và month từ monthYear (định dạng YYYY-MM)
        String[] parts = monthYear.split("-");
        if (parts.length != 2) {
            System.err.println("getSessionCountByTeacherMonth: Định dạng tháng/năm không hợp lệ: " + monthYear);
            return 0;
        }
        int year, month;
        try {
            year = Integer.parseInt(parts[0]);
            month = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.err.println("getSessionCountByTeacherMonth: Lỗi parse year/month: " + e.getMessage());
            return 0;
        }

        String sql = "SELECT COUNT(*) AS total_sessions "
                + "FROM TeachingConfirmation "
                + "WHERE teacher_id = ? "
                + "AND status = 'present' "
                + "AND YEAR(day) = ? "
                + "AND MONTH(day) = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, idTeacher);
            stm.setInt(2, year);
            stm.setInt(3, month);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                totalSessions = rs.getInt("total_sessions");
            }
        } catch (SQLException e) {
            System.err.println("getSessionCountByTeacherMonth: " + e.getMessage());
        }
        return totalSessions;
    }

    // Lấy lương cứng của giáo viên theo id_teacher
    public String getTeacherOfferSalary(int idTeacher) {
        String offerSalary = "0";
        try (PreparedStatement stm = connection.prepareStatement(
                "SELECT offer_salary FROM [BIGDREAM].[dbo].[Teacher] WHERE id = ?")) {
            stm.setInt(1, idTeacher);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                double salary = rs.getDouble("offer_salary");
                offerSalary = String.valueOf((int) salary);
            }
        } catch (SQLException e) {
            System.err.println("getTeacherOfferSalary: " + e.getMessage());
        }
        return offerSalary;
    }

    // Lấy tất cả bản ghi lương
    public ArrayList<SalaryTeacher> getAll() {
        ArrayList<SalaryTeacher> salaries = new ArrayList<>();
        String sql = "SELECT s.id, t.full_name AS teacher, s.offer_salary, s.number_of_sessions, s.bonus, s.penalty, s.amount, s.note, s.month "
                + "FROM salaryTeacher s "
                + "JOIN Teacher t ON s.id_teacher = t.id";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SalaryTeacher salary = new SalaryTeacher(
                        rs.getString("id"),
                        rs.getString("teacher"),
                        rs.getString("offer_salary"),
                        rs.getString("number_of_sessions"),
                        rs.getString("bonus"),
                        rs.getString("penalty"),
                        rs.getString("amount"),
                        rs.getString("note"),
                        rs.getString("month")
                );
                salaries.add(salary);
            }
        } catch (SQLException e) {
            System.err.println("getAll: " + e.getMessage());
        }
        return salaries;
    }

    // Tìm kiếm và lọc lương theo tên giáo viên và tháng/năm
    public ArrayList<SalaryTeacher> getSalariesBySearchAndFilter(String searchTeacherName, String filterMonthYear) {
        ArrayList<SalaryTeacher> salaries = new ArrayList<>();
        String sql = "SELECT s.id, t.full_name AS teacher, s.offer_salary, s.number_of_sessions, s.bonus, s.penalty, s.amount, s.note, s.month "
                + "FROM salaryTeacher s "
                + "JOIN Teacher t ON s.id_teacher = t.id "
                + "WHERE 1=1";

        // Thêm điều kiện tìm kiếm theo tên giáo viên
        if (searchTeacherName != null && !searchTeacherName.trim().isEmpty()) {
            sql += " AND t.full_name LIKE ?";
        }

        // Thêm điều kiện lọc theo tháng/năm
        if (filterMonthYear != null && !filterMonthYear.trim().isEmpty()) {
            sql += " AND s.month = ?";
        }

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            int paramIndex = 1;
            if (searchTeacherName != null && !searchTeacherName.trim().isEmpty()) {
                stm.setString(paramIndex++, "%" + searchTeacherName.trim() + "%");
            }
            if (filterMonthYear != null && !filterMonthYear.trim().isEmpty()) {
                stm.setString(paramIndex, filterMonthYear);
            }

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                SalaryTeacher salary = new SalaryTeacher(
                        rs.getString("id"),
                        rs.getString("teacher"),
                        rs.getString("offer_salary"),
                        rs.getString("number_of_sessions"),
                        rs.getString("bonus"),
                        rs.getString("penalty"),
                        rs.getString("amount"),
                        rs.getString("note"),
                        rs.getString("month")
                );
                salaries.add(salary);
            }
        } catch (SQLException e) {
            System.err.println("getSalariesBySearchAndFilter: " + e.getMessage());
        }
        return salaries;
    }

    // Xử lý dữ liệu và tính toán lương
    public ResultMessage processSalary(String action, String id, String teacherId, String monthYear, String bonus, String penalty, String note) {
        try {
            // Kiểm tra teacherId
            if (teacherId == null || teacherId.isEmpty() || teacherId.equals("0")) {
                return new ResultMessage(false, "Vui lòng chọn giáo viên!");
            }
            int teacher;
            try {
                teacher = Integer.parseInt(teacherId);
            } catch (NumberFormatException e) {
                return new ResultMessage(false, "ID giáo viên không hợp lệ!");
            }

            // Kiểm tra monthYear
            if (monthYear == null || monthYear.isEmpty() || monthYear.equals("0")) {
                // Sử dụng tháng/năm hiện tại làm mặc định
                monthYear = Year.now().getValue() + "-01";
            }
            // Kiểm tra định dạng YYYY-MM
            if (!monthYear.matches("\\d{4}-\\d{2}")) {
                return new ResultMessage(false, "Định dạng tháng/năm không hợp lệ!");
            }
            // Tách year và month từ monthYear
            String[] parts = monthYear.split("-");
            if (parts.length != 2) {
                return new ResultMessage(false, "Định dạng tháng/năm không hợp lệ!");
            }
            int year, month;
            try {
                year = Integer.parseInt(parts[0]);
                month = Integer.parseInt(parts[1]);
                if (year < 2000 || year > Year.now().getValue()) {
                    return new ResultMessage(false, "Năm phải từ 2000 đến " + Year.now().getValue() + "!");
                }
                if (month < 1 || month > 12) {
                    return new ResultMessage(false, "Tháng phải từ 1 đến 12!");
                }
            } catch (NumberFormatException e) {
                return new ResultMessage(false, "Tháng/năm không hợp lệ!");
            }

            // Lấy lương cứng
            String offerSalary = getTeacherOfferSalary(teacher);
            if (offerSalary == null || offerSalary.isEmpty()) {
                return new ResultMessage(false, "Lương cứng của giáo viên không hợp lệ!");
            }
            int offerSalaryValue;
            try {
                offerSalaryValue = Integer.parseInt(offerSalary);
            } catch (NumberFormatException e) {
                return new ResultMessage(false, "Lương cứng không phải số hợp lệ!");
            }

            // Lấy số buổi dạy
            int sessionCount = getSessionCountByTeacherMonth(teacher, monthYear);

            // Kiểm tra và parse bonus
            int bonusValue = 0;
            if (bonus != null && !bonus.trim().isEmpty()) {
                try {
                    double bonusDouble = Double.parseDouble(bonus.trim());
                    if (bonusDouble < 0) {
                        return new ResultMessage(false, "Tiền thưởng không được âm!");
                    }
                    bonusValue = (int) bonusDouble;
                } catch (NumberFormatException e) {
                    return new ResultMessage(false, "Tiền thưởng không phải số hợp lệ!");
                }
            }

            // Kiểm tra và parse penalty
            int penaltyValue = 0;
            if (penalty != null && !penalty.trim().isEmpty()) {
                try {
                    double penaltyDouble = Double.parseDouble(penalty.trim());
                    if (penaltyDouble < 0) {
                        return new ResultMessage(false, "Tiền phạt không được âm!");
                    }
                    penaltyValue = (int) penaltyDouble;
                } catch (NumberFormatException e) {
                    return new ResultMessage(false, "Tiền phạt không phải số hợp lệ!");
                }
            }

            // Tính tổng lương: lương cứng * số buổi + tiền thưởng - tiền phạt
            int amount = offerSalaryValue * sessionCount + bonusValue - penaltyValue;

            // Tạo đối tượng SalaryTeacher
            SalaryTeacher salary = new SalaryTeacher(
                    id != null ? id : "0",
                    teacherId,
                    String.valueOf(offerSalaryValue),
                    String.valueOf(sessionCount),
                    String.valueOf(bonusValue),
                    String.valueOf(penaltyValue),
                    String.valueOf(amount),
                    note != null ? note : "",
                    monthYear // Lưu định dạng YYYY-MM
            );

            // Thực hiện hành động thêm hoặc cập nhật
            if ("add".equals(action)) {
                return addSalary(salary);
            } else {
                return updateSalary(salary);
            }
        } catch (Exception e) {
            System.err.println("processSalary: " + e.getMessage());
            return new ResultMessage(false, "Lỗi hệ thống: " + e.getMessage());
        }
    }

    // Thêm bản ghi lương mới
    public ResultMessage addSalary(SalaryTeacher salary) {
        String sql = "INSERT INTO salaryTeacher (id_teacher, offer_salary, number_of_sessions, bonus, penalty, amount, note, month) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, Integer.parseInt(salary.getTeacher()));
            stm.setString(2, salary.getOffer_salary());
            stm.setString(3, salary.getNumber_of_sessions());
            stm.setString(4, salary.getBonus());
            stm.setString(5, salary.getPenalty());
            stm.setString(6, salary.getAmount());
            stm.setString(7, salary.getNote());
            stm.setString(8, salary.getMonth()); // Lưu month dạng YYYY-MM
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Thêm lương thành công!");
            } else {
                return new ResultMessage(false, "Thêm lương thất bại!");
            }
        } catch (SQLException e) {
            System.err.println("addSalary: " + e.getMessage());
            return new ResultMessage(false, "Lỗi khi thêm lương: " + e.getMessage());
        }
    }

    // Cập nhật bản ghi lương
    public ResultMessage updateSalary(SalaryTeacher salary) {
        String sql = "UPDATE salaryTeacher SET offer_salary = ?, number_of_sessions = ?, bonus = ?, penalty = ?, amount = ?, note = ?, month = ? "
                + "WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setString(1, salary.getOffer_salary());
            stm.setString(2, salary.getNumber_of_sessions());
            stm.setString(3, salary.getBonus());
            stm.setString(4, salary.getPenalty());
            stm.setString(5, salary.getAmount());
            stm.setString(6, salary.getNote());
            stm.setString(7, salary.getMonth()); // Lưu month dạng YYYY-MM
            stm.setInt(8, Integer.parseInt(salary.getId()));
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Cập nhật lương thành công!");
            } else {
                return new ResultMessage(false, "Cập nhật lương thất bại!");
            }
        } catch (SQLException e) {
            System.err.println("updateSalary: " + e.getMessage());
            return new ResultMessage(false, "Lỗi khi cập nhật lương: " + e.getMessage());
        }
    }

    // Xóa bản ghi lương
    public ResultMessage deleteSalary(String id) {
        String sql = "DELETE FROM salaryTeacher WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, Integer.parseInt(id));
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                return new ResultMessage(true, "Xóa lương thành công!");
            } else {
                return new ResultMessage(false, "Xóa lương thất bại!");
            }
        } catch (SQLException e) {
            System.err.println("deleteSalary: " + e.getMessage());
            return new ResultMessage(false, "Lỗi khi xóa lương: " + e.getMessage());
        }
    }

    // Lấy bản ghi lương theo id
    public SalaryTeacher getSalaryById(String id) {
        String sql = "SELECT id, id_teacher AS teacher, offer_salary, number_of_sessions, bonus, penalty, amount, note, month "
                + "FROM salaryTeacher WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, Integer.parseInt(id));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                String bonus = rs.getString("bonus");
                String penalty = rs.getString("penalty");
                return new SalaryTeacher(
                        rs.getString("id"),
                        rs.getString("teacher"),
                        rs.getString("offer_salary"),
                        rs.getString("number_of_sessions"),
                        bonus != null ? bonus : "0",
                        penalty != null ? penalty : "0",
                        rs.getString("amount"),
                        rs.getString("note") != null ? rs.getString("note") : "",
                        rs.getString("month")
                );
            }
        } catch (SQLException e) {
            System.err.println("getSalaryById: " + e.getMessage());
        }
        return null;
    }
}
