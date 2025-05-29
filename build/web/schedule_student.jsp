<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%
    // Mảng ngày trong tuần (có thể mở rộng nếu cần)
    String[] weekdays = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thời khóa biểu học sinh</title>
    <style>
        body {
            font-family: 'Roboto', 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%); /* Gradient nền */
            margin: 0;
            padding: 30px;
            text-align: center;
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        h2 {
            font-size: 28px;
            color: #1a237e; /* Màu xanh đậm */
            margin-bottom: 40px;
            letter-spacing: 1px;
            text-transform: uppercase;
            font-weight: 700;
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
        }

        .table-container {
            width: 100%;
            max-width: 1000px;
            margin: 0 auto;
            background-color: #ffffff;
            border-radius: 15px;
            box-shadow: 0 8px 30px rgba(0, 0, 0, 0.15);
            padding: 20px;
            overflow-x: hidden; /* Loại bỏ thanh cuộn ngang */
        }

        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            background-color: #ffffff;
            table-layout: fixed; /* Đảm bảo các cột có kích thước cố định */
        }

        thead tr {
            background: linear-gradient(90deg, #1976d2, #42a5f5); /* Gradient cho tiêu đề */
            color: #ffffff;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }

        th, td {
            padding: 15px 10px; /* Giảm padding để tiết kiệm không gian */
            border: none;
            border-bottom: 1px solid #e0e0e0;
            border-right: 1px solid #e0e0e0;
            text-align: center;
            overflow: hidden; /* Ngăn nội dung tràn ra ngoài */
            text-overflow: ellipsis; /* Cắt bớt nội dung nếu quá dài */
            white-space: nowrap; /* Ngăn xuống dòng */
            transition: background-color 0.3s ease, transform 0.2s ease;
        }

        th:last-child, td:last-child {
            border-right: none; /* Xóa viền phải của cột cuối */
        }

        tbody tr:last-child td {
            border-bottom: none; /* Xóa viền dưới của hàng cuối */
        }

        tbody tr {
            transition: background-color 0.3s ease;
        }

        tbody tr:nth-child(even) {
            background-color: #f5f7fa; /* Màu xám nhạt cho hàng chàn */
        }

        tbody tr:nth-child(odd) {
            background-color: #ffffff; /* Màu trắng cho hàng lẻ */
        }

        tbody tr:hover {
            background-color: #e3f2fd; /* Hiệu ứng hover */
            transform: scale(1.01); /* Hiệu ứng phóng to nhẹ khi hover */
        }

        th:first-child,
        td:first-child {
            background: linear-gradient(90deg, #eceff1, #f5f7fa); /* Gradient cho cột đầu */
            font-weight: 600;
            text-align: left;
            color: #37474f;
            border-right: 2px solid #e0e0e0;
            width: 120px; /* Đặt chiều rộng cố định cho cột đầu */
        }

        th {
            position: sticky;
            top: 0;
            z-index: 1;
        }

        td {
            color: #455a64;
            font-size: 15px;
            width: 120px; /* Đặt chiều rộng cố định cho các cột khác */
        }

        @media (max-width: 768px) {
            th, td {
                padding: 10px;
                min-width: 80px;
                font-size: 13px;
            }

            h2 {
                font-size: 22px;
            }

            th:first-child, td {
                width: 90px; /* Giảm chiều rộng trên màn hình nhỏ */
            }
        }
    </style>
</head>
<body>
    <div class="table-container">
        <h2>Thời khóa biểu học sinh</h2>
        <table>
            <thead>
                <tr>
                    <th>Thông tin</th>
                    <% for (String day : weekdays) { %>
                        <th><%= day %></th>
                    <% } %>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Ngày</td>
                    <% for (int i = 0; i < weekdays.length; i++) { %>
                        <td></td>
                    <% } %>
                </tr>
                <tr>
                    <td>Lớp</td>
                    <% for (int i = 0; i < weekdays.length; i++) { %>
                        <td></td>
                    <% } %>
                </tr>
                <tr>
                    <td>Bắt đầu</td>
                    <% for (int i = 0; i < weekdays.length; i++) { %>
                        <td></td>
                    <% } %>
                </tr>
                <tr>
                    <td>Kết thúc</td>
                    <% for (int i = 0; i < weekdays.length; i++) { %>
                        <td></td>
                    <% } %>
                </tr>
                <tr>
                    <td>Giáo viên</td>
                    <% for (int i = 0; i < weekdays.length; i++) { %>
                        <td></td>
                    <% } %>
                </tr>
                <tr>
                    <td>Phòng học</td>
                    <% for (int i = 0; i < weekdays.length; i++) { %>
                        <td></td>
                    <% } %>
                </tr>
                <tr>
                    <td>Điểm danh</td>
                    <% for (int i = 0; i < weekdays.length; i++) { %>
                        <td></td>
                    <% } %>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>