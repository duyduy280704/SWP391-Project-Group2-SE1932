package dal;

import java.sql.Connection;
import java.sql.SQLException;

public class TestDBConnection {
    public static void main(String[] args) {
        DBContext db = new DBContext();
        Connection conn = db.connection;

        if (conn != null) {
            System.out.println("✅ Kết nối CSDL thành công!");
            try {
                conn.close();
                System.out.println("🔒 Đã đóng kết nối.");
            } catch (SQLException e) {
                System.err.println("❌ Lỗi khi đóng kết nối: " + e.getMessage());
            }
        } else {
            System.out.println("❌ Kết nối thất bại!");
        }
    }
}
