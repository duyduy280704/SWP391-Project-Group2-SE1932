<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <meta charset="UTF-8">
        <title>Thời Khóa Biểu</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <style>
            :root {
                --primary-color: #1e3a8a;
                --accent-color: #3b82f6;
                --secondary-accent: #f4a261;
                --danger-color: #ef4444;
                --background-color: #f4f7fc;
                --card-background: #ffffff;
                --text-color: #1f2937;
                --border-color: #e2e8f0;
            }

            body {
                background-color: var(--background-color);
                font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                margin: 0;
                padding: 0;
                line-height: 1.6;
                color: var(--text-color);
            }

            .container {
                max-width: 1280px;
                margin: 40px auto;
                padding: 32px;
                background-color: var(--card-background);
                border-radius: 20px;
                box-shadow: 0 12px 40px rgba(0, 0, 0, 0.08);
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }

            .container:hover {
                transform: translateY(-4px);
                box-shadow: 0 16px 48px rgba(0, 0, 0, 0.1);
            }

            h1 {
                text-align: center;
                font-size: 40px;
                font-weight: 700;
                margin-bottom: 40px;
                background: linear-gradient(90deg, var(--primary-color), var(--accent-color));
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
                letter-spacing: -0.02em;
            }

            .top-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 32px;
                flex-wrap: wrap;
                gap: 20px;
            }

            .search-group {
                display: flex;
                align-items: center;
                gap: 12px;
                position: relative;
            }
            .form-container {
                display: block;
                width: 100%;
                margin-bottom: 20px;
            }

            .search-form {
                display: flex;
                align-items: center;
                gap: 10px;
                flex-wrap: wrap; /* Đảm bảo xuống dòng nếu chật */
            }

            .message {
                margin-top: 6px;
                font-weight: bold;
            }

            .message.error {
                color: red;
            }

            .message.success {
                color: green;
            }

            .search-group input[type="text"] {
                padding: 12px 20px;
                width: 320px;
                height: 48px;
                border: 1px solid var(--border-color);
                border-radius: 12px;
                font-size: 16px;
                background-color: #fafafa;
                transition: all 0.3s ease;
                box-sizing: border-box;
            }

            .search-group input[type="text"]:focus {
                outline: none;
                border-color: var(--accent-color);
                box-shadow: 0 0 10px rgba(59, 130, 246, 0.3);
                background-color: #ffffff;
            }

            .search-btn {
                padding: 0 20px;
                height: 48px;
                background-color: var(--accent-color);
                border: none;
                border-radius: 12px;
                color: white;
                font-size: 16px;
                cursor: pointer;
                transition: all 0.3s ease;
                display: flex;
                align-items: center;
                justify-content: center;
                gap: 8px;
                box-sizing: border-box;
            }

            .search-btn:hover {
                background-color: #2563eb;
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
            }

            .create-btn {
                background: linear-gradient(135deg, var(--secondary-accent), #e07a5f);
                color: #ffffff;
                border: none;
                padding: 14px 28px;
                font-size: 16px;
                font-weight: 600;
                border-radius: 12px;
                cursor: pointer;
                transition: all 0.3s ease;
                text-decoration: none;
                display: inline-flex;
                align-items: center;
                gap: 8px;
            }

            .create-btn:hover {
                transform: translateY(-3px);
                box-shadow: 0 6px 16px rgba(244, 162, 97, 0.4);
            }

            .filter-form {
                display: flex;
                gap: 20px;
                align-items: center;
                margin-bottom: 32px;
                flex-wrap: wrap;
                background-color: #f9fafb;
                padding: 20px;
                border-radius: 12px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
            }

            .filter-form label {
                font-weight: 500;
                color: var(--text-color);
                margin-right: 8px;
            }

            .filter-form select,
            .filter-form input[type="date"] {
                padding: 10px 16px;
                border: 1px solid var(--border-color);
                border-radius: 8px;
                font-size: 15px;
                background-color: #ffffff;
                transition: all 0.3s ease;
            }

            .filter-form select:focus,
            .filter-form input[type="date"]:focus {
                outline: none;
                border-color: var(--accent-color);
                box-shadow: 0 0 8px rgba(59, 130, 246, 0.2);
            }

            .filter-form button {
                padding: 10px 20px;
                background-color: var(--accent-color);
                border: none;
                border-radius: 8px;
                color: white;
                font-size: 15px;
                font-weight: 500;
                cursor: pointer;
                transition: all 0.3s ease;
            }

            .filter-form button:hover {
                background-color: #2563eb;
                transform: translateY(-1px);
            }

            table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                font-size: 15px;
                border-radius: 16px;
                overflow: hidden;
                background-color: var(--card-background);
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.06);
            }

            thead {
                background: linear-gradient(135deg, #e5e7eb, #d1d5db);
                color: var(--text-color);
            }

            th, td {
                padding: 18px;
                border: 1px solid var(--border-color);
                text-align: center;
                transition: background-color 0.3s ease;
            }

            th {
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.06em;
                background-color: #f1f5f9;
            }

            tr:nth-child(even) {
                background-color: #f9fafb;
            }

            tr:hover {
                background-color: #e0f2fe;
                transition: background-color 0.2s ease;
            }

            .row-actions {
                display: flex;
                justify-content: center;
                gap: 16px;
            }

            .action-btn {
                background: none;
                border: none;
                font-size: 22px;
                cursor: pointer;
                transition: all 0.2s ease;
            }

            .action-btn:hover {
                transform: scale(1.4);
            }

            .action-btn.edit {
                color: var(--accent-color);
            }

            .action-btn.edit:hover {
                color: #1e40af;
            }

            .action-btn.delete {
                color: var(--danger-color);
            }

            .action-btn.delete:hover {
                color: #b91c1c;
            }

            .message {
                padding: 12px;
                border-radius: 8px;
                margin: 10px 0;
                font-weight: 500;
            }

            .message.error {
                background-color: #fee2e2;
                color: var(--danger-color);
            }

            .message.success {
                background-color: #dcfce7;
                color: #15803d;
            }

            @media (max-width: 768px) {
                .container {
                    margin: 20px;
                    padding: 20px;
                }

                h1 {
                    font-size: 32px;
                }

                .top-bar {
                    flex-direction: column;
                    align-items: stretch;
                }

                .search-group input[type="text"] {
                    width: 100%;
                }

                .filter-form {
                    flex-direction: column;
                    align-items: stretch;
                }

                th, td {
                    padding: 14px;
                    font-size: 14px;
                }
            }

            @media (max-width: 480px) {
                table {
                    font-size: 13px;
                }

                th, td {
                    padding: 10px;
                }

                .action-btn {
                    font-size: 18px;
                }

                .filter-form select,
                .filter-form input[type="date"] {
                    width: 100%;
                }
            }
            /* Thu nhỏ kích thước tổng thể của hộp thoại */
            .swal2-popup {
                width: 300px !important; /* Chiều rộng nhỏ hơn, mặc định thường là 500px */
                padding: 15px !important; /* Giảm padding */
                font-size: 14px !important; /* Giảm cỡ chữ tổng thể */
            }

            /* Thu nhỏ tiêu đề */
            .swal2-title {
                font-size: 18px !important; /* Cỡ chữ tiêu đề nhỏ hơn */
                margin: 5px 0 !important; /* Giảm khoảng cách */
            }

            /* Thu nhỏ nội dung (text) */
            .swal2-content {
                font-size: 14px !important; /* Cỡ chữ nội dung nhỏ hơn */
            }

            /* Thu nhỏ nút */
            .swal2-confirm, .swal2-cancel {
                font-size: 12px !important; /* Cỡ chữ nút nhỏ hơn */
                padding: 5px 15px !important; /* Giảm padding của nút */
                margin: 5px !important; /* Giảm khoảng cách giữa các nút */
            }

            /* Thu nhỏ icon */
            .swal2-icon {
                width: 50px !important; /* Kích thước icon nhỏ hơn */
                height: 50px !important;
            }
            .filter-form .reset-btn {
                padding: 10px 20px;
                background: linear-gradient(135deg, #9ca3af, #6b7280);
                border: none;
                border-radius: 8px;
                color: white;
                font-size: 15px;
                font-weight: 500;
                text-decoration: none;
                margin-left: 12px;
                transition: all 0.3s ease;
                display: inline-flex;
                align-items: center;
                justify-content: center;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .filter-form .reset-btn:hover {
                background: linear-gradient(135deg, #6b7280, #4b5563);
                transform: translateY(-2px);
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
            }
        </style>
        <script>
            function showDeleteConfirmation(id) {
                Swal.fire({
                    title: 'Xác nhận xóa',
                    text: 'Bạn có chắc chắn muốn xóa lịch học này không?',
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonText: 'OK',
                    cancelButtonText: 'Hủy'
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = 'schedule?id=' + id + '&mode=3';
                    }
                });
            }
        </script>
    </head>
    <body>
        <div class="container">
            <h1>Thời Khóa Biểu</h1>


            <form action="schedule" method="get" class="filter-form">
                <input type="hidden" name="mode" value="filter">
                <div>
                    <label for="date">Ngày học:</label>
                    <input type="date" name="date" id="date" value="${param.date}">
                </div>
                <div>
                    <button type="submit">Lọc</button>
                    <a href="schedule" class="reset-btn">Xóa bộ lọc</a>
                </div>
            </form>

            <div class="top-bar">
                <div class="search-group">

                    <div class="form-container">
                        <form action="schedule" method="post" class="search-form">
                            <input type="text" name="keyword" placeholder="Tìm kiếm lớp ...">
                            <button class="search-btn" type="submit" name="search">🔍 Tìm kiếm</button>
                        </form>


                        <c:if test="${not empty err}">
                            <div class="message error">${err}</div>
                        </c:if>
                        <c:if test="${not empty msg}">
                            <div class="message success">${msg}</div>
                        </c:if>
                    </div>



                </div>
                <a href="schedule?mode=1" class="create-btn">+ Tạo mới thời khóa biểu</a>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Tên Lớp</th>
                        <th>Bắt Đầu</th>
                        <th>Kết Thúc</th>
                        <th>Ngày</th>
                        <th>Giáo Viên</th>
                        <th>Phòng</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>


                    <c:forEach items="${schedule}" var="schedule" varStatus="loop">
                        <tr> 
                            <td>${loop.index + 1}</td>

                            <td>${schedule.getNameClass()}</td>
                            <td>${fn:substring(schedule.getStartTime(), 0, 5)}</td>
                            <td>${fn:substring(schedule.getEndTime(), 0, 5)}</td>
                            <td>${fn:substring(schedule.getDay(), 8, 10)}/${fn:substring(schedule.getDay(), 5, 7)}/${fn:substring(schedule.getDay(), 0, 4)}</td>
                            <td>${schedule.getTeacher()}</td>
                            <td>${schedule.getRoom()}</td>
                            <td class="row-actions">
                                <a href="schedule?id=${schedule.getId()}&mode=2" class="action-btn edit">✎</a>
                                <a href="schedule?id=${schedule.getId()}&mode=3" class="action-btn delete"
                                   onclick="showDeleteConfirmation(${schedule.getId()});
                                           return false;">🗑</a>
                            </td>

                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>