package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO extends DBContext {

    public List<Sale> getAllSales() {
        List<Sale> list = new ArrayList<>();
        String sql = "SELECT * FROM Sale ORDER BY id ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                double value = rs.getDouble("value");
                String createdAt = rs.getString("created_at"); // lấy kiểu String
                int quantity = rs.getInt("quantity");

                Sale s = new Sale(id, code, value, createdAt, quantity);
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println("getAllSales: " + e.getMessage());
        }
        return list;
    }

    public List<Sale> searchSalesByCode(String keyword) {
        List<Sale> list = new ArrayList<>();
        String sql = "SELECT * FROM Sale WHERE code LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String code = rs.getString("code");
                    double value = rs.getDouble("value");
                    String createdAt = rs.getString("created_at");
                    int quantity = rs.getInt("quantity");

                    Sale s = new Sale(id, code, value, createdAt, quantity);
                    list.add(s);
                }
            }
        } catch (Exception e) {
            System.out.println("searchSalesByCode: " + e.getMessage());
        }
        return list;
    }

    public Sale getSaleById(int id) {
        String sql = "SELECT * FROM Sale WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String code = rs.getString("code");
                    double value = rs.getDouble("value");
                    String createdAt = rs.getString("created_at");
                    int quantity = rs.getInt("quantity");

                    return new Sale(id, code, value, createdAt, quantity);
                }
            }
        } catch (Exception e) {
            System.out.println("getSaleById: " + e.getMessage());
        }
        return null;
    }

    public void insertSale(Sale s) {
        String sql = "INSERT INTO Sale (code, value, created_at, quantity) VALUES (?, ?, GETDATE(), ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getCode());
            ps.setDouble(2, s.getValue());
            ps.setInt(3, s.getQuantity());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("insertSale: " + e.getMessage());
        }
    }

    public void updateSale(Sale s) {
        String sql = "UPDATE Sale SET code = ?, value = ?, quantity = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getCode());
            ps.setDouble(2, s.getValue());
            ps.setInt(3, s.getQuantity());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("updateSale: " + e.getMessage());
        }
    }

    public void deleteSale(int id) {
        String sql = "DELETE FROM Sale WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("deleteSale: " + e.getMessage());
        }
    }

    //Duong
    public Sale getSaleByCode(String code) {
        Sale sale = null;
        String sql = "SELECT * FROM Sale WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String saleCode = rs.getString("code");
                    double value = rs.getDouble("value");
                    String createdAt = rs.getString("created_at");
                    int quantity = rs.getInt("quantity");

                    sale = new Sale(id, saleCode, value, createdAt, quantity);
                }
            }
        } catch (Exception e) {
            System.out.println("getSaleByCode: " + e.getMessage());
        }
        return sale;
    }

    public boolean useSale(int id) {
        String sql = "UPDATE Sale SET quantity = quantity - 1 WHERE id = ? AND quantity > 0";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            System.out.println("useSale: " + e.getMessage());
        }
        return false;
    }
}
