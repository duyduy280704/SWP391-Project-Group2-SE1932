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
 * @author Dwight
 */
public class PaymentDAO extends DBContext {

    public void insert(Payment p) {
        String sql = "INSERT INTO Payment (id_student, id_course, status, date, id_sale, order_code) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getIdStudent());
            ps.setInt(2, p.getIdCourse());
            ps.setString(3, p.getStatus());
            ps.setString(4, p.getDate());
            if (p.getIdSale() != null) {
                ps.setInt(5, p.getIdSale());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setString(6, p.getOrderCode()); // thêm mã đơn hàng
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("insert Payment: " + e.getMessage());
        }
    }

    public List<PaymentView> getPaymentsByStatus(String studentId, String status, String keyword) {
        List<PaymentView> list = new ArrayList<>();

        String sql = "SELECT p.order_code, p.date, p.status, "
                + "c.name AS course_name, c.fee, s.value AS sale_percent "
                + "FROM payment p "
                + "JOIN course c ON p.id_course = c.id "
                + "LEFT JOIN sale s ON p.id_sale = s.id "
                + "WHERE p.id_student = ? AND p.status = ? AND c.name LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ps.setString(2, status);
            ps.setString(3, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PaymentView pv = new PaymentView();
                    pv.setOrderCode(rs.getString("order_code"));
                    pv.setDate(rs.getString("date"));
                    pv.setStatus(rs.getString("status"));
                    pv.setCourseName(rs.getString("course_name"));
                    pv.setCourseFee(rs.getDouble("fee"));

                    double salePercent = rs.getDouble("sale_percent");
                    pv.setSalePercent(salePercent);
                    pv.setFinalAmount(pv.getCourseFee() * (1 - salePercent / 100.0));

                    list.add(pv);
                }
            }
        } catch (Exception e) {
            System.out.println("getPaymentsByStatus: " + e.getMessage());
        }

        return list;
    }

    public void updateStatusByOrderCode(String orderCode, String newStatus) {
        String sql = "UPDATE payment SET status = ? WHERE order_code = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, orderCode);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateStatusByOrderCode: " + e.getMessage());
        }

    }

    public List<PaymentView> getAllPayment(String keyword, String filterStatus) {
        List<PaymentView> list = new ArrayList<>();

        String sql = """
            SELECT 
                st.full_name AS student_name,
                p.id_student, 
                p.order_code, 
                p.date, 
                p.status,
                p.method, 
                c.name AS course_name, 
                c.fee, 
                st.email, 
                s.value AS sale_percent
            FROM payment p
            JOIN student st ON p.id_student = st.id
            JOIN course c ON p.id_course = c.id
            LEFT JOIN sale s ON p.id_sale = s.id
            WHERE c.name LIKE ?
        """;

        if (filterStatus != null && !filterStatus.isEmpty() && !filterStatus.equals("all")) {
            sql += " AND p.status = ?";
        }

        sql += """
            ORDER BY 
                CASE 
                    WHEN p.status = N'Đã chuyển khoản' THEN 0
                    WHEN p.status = N'Chưa thanh toán' THEN 1
                    WHEN p.status = N'Hoàn tất' THEN 2
                    ELSE 3
                END, 
                p.date DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            if (filterStatus != null && !filterStatus.isEmpty() && !filterStatus.equals("all")) {
                ps.setString(2, filterStatus);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PaymentView pv = new PaymentView();
                pv.setNameStudent(rs.getString("student_name"));
                pv.setIdStudent(rs.getString("id_student"));
                pv.setEmail(rs.getString("email"));
                pv.setOrderCode(rs.getString("order_code"));
                pv.setDate(rs.getString("date"));
                pv.setStatus(rs.getString("status"));
                pv.setCourseName(rs.getString("course_name"));
                pv.setMethod(rs.getString("method"));
                pv.setCourseFee(rs.getDouble("fee"));
                double sale = rs.getDouble("sale_percent");
                pv.setSalePercent(sale);
                pv.setFinalAmount(pv.getCourseFee() * (1 - sale / 100));
                list.add(pv);
            }
        } catch (Exception e) {
            System.out.println("getAllPayments: " + e.getMessage());
        }

        return list;
    }

    public List<RefundInfo> getRefundList() {
        List<RefundInfo> list = new ArrayList<>();
        String sql = """
            SELECT 
                r.student_id,
                r.course_id,
                r.status AS regisition_status,
                c.fee AS original_price,
                ISNULL(s.value, 0) AS discount_percent,
                ROUND(
                    (c.fee * (1 - ISNULL(s.value, 0) / 100.0)) * 0.8, 
                    2
                ) AS refund_amount,
                p.id, 
                p.method,
                p.status, 
                p.order_code,
                p.date AS payment_date
            FROM 
                regisition r
            JOIN 
                payment p ON r.student_id = p.id_student AND r.course_id = p.id_course
            JOIN 
                Course c ON p.id_course = c.id
            LEFT JOIN 
                Sale s ON p.id_sale = s.id
            WHERE 
                r.status = N'Đã hủy'
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RefundInfo ri = new RefundInfo();
                ri.setStudentId(rs.getInt("student_id"));
                ri.setCourseId(rs.getInt("course_id"));
                ri.setRegisitionStatus(rs.getString("regisition_status"));
                ri.setOriginalPrice(rs.getDouble("original_price"));
                ri.setDiscountPercent(rs.getInt("discount_percent"));
                ri.setRefundAmount(rs.getDouble("refund_amount"));
                ri.setMethod(rs.getString("method"));
                ri.setOrderCode(rs.getString("order_code"));
                ri.setPaymentDate(rs.getString("payment_date"));
                ri.setPaymentStatus(rs.getString("status"));
                ri.setPaymentId(rs.getInt("id"));
                list.add(ri);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void markAsRefunded(int paymentId) {
        String sql = "UPDATE payment SET status = N'Đã hoàn tiền' WHERE id = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePaymentMethod(String orderCode, String method) {
        String sql = "UPDATE payment SET method = ? WHERE order_code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, method);
            ps.setString(2, orderCode);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
