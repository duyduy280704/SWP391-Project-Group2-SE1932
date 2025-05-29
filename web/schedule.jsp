<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thời Khóa Biểu</title>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
        <style>
            /* Giữ nguyên phần CSS như bạn đã có */
            body {
                background-color: #f4f7fc;
                font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif;
                margin: 0;
                padding: 0;
                line-height: 1.6;
            }
            .container {
                max-width: 1280px;
                margin: 32px auto;
                padding: 32px;
                background-color: #ffffff;
                border-radius: 16px;
                box-shadow: 0 10px 30px rgba(0, 0, 0, 0.05);
                transition: transform 0.3s ease;
            }
            .container:hover {
                transform: translateY(-2px);
            }
            h1 {
                text-align: center;
                font-size: 36px;
                font-weight: 700;
                margin-bottom: 32px;
                color: #1e3a8a;
                background: linear-gradient(90deg, #1e3a8a, #3b82f6);
                -webkit-background-clip: text;
                -webkit-text-fill-color: transparent;
            }
            .top-bar {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-bottom: 24px;
                flex-wrap: wrap;
                gap: 16px;
            }
            .search-group {
                display: flex;
                align-items: center;
                gap: 8px;
            }
            .search-group input[type="text"] {
                padding: 12px 20px;
                width: 300px;
                border: 1px solid #e2e8f0;
                border-radius: 10px;
                font-size: 16px;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .search-group input[type="text"]:focus {
                outline: none;
                border-color: #3b82f6;
                box-shadow: 0 0 8px rgba(59, 130, 246, 0.2);
            }
            .search-btn {
                padding: 12px 16px;
                background-color: #3b82f6;
                border: none;
                border-radius: 10px;
                color: white;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }
            .search-btn:hover {
                background-color: #2563eb;
                transform: translateY(-1px);
            }
            .create-btn {
                background: linear-gradient(135deg, #f4a261, #e07a5f);
                color: #ffffff;
                border: none;
                padding: 12px 24px;
                font-size: 16px;
                font-weight: 500;
                border-radius: 10px;
                cursor: pointer;
                transition: transform 0.3s ease, box-shadow 0.3s ease;
            }
            .create-btn:hover {
                transform: translateY(-2px);
                box-shadow: 0 4px 12px rgba(244, 162, 97, 0.3);
            }
            table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                font-size: 15px;
                border-radius: 12px;
                overflow: hidden;
                background-color: #ffffff;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
            }
            thead {
                background: linear-gradient(135deg, #e5e7eb, #d1d5db);
                color: #1f2937;
            }
            th, td {
                padding: 16px;
                border: 1px solid #e5e7eb;
                text-align: center;
                transition: background-color 0.3s ease;
            }
            th {
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.05em;
            }
            tr:nth-child(even) {
                background-color: #f9fafb;
            }
            tr:hover {
                background-color: #eff6ff;
                transition: background-color 0.2s ease;
            }
            .row-actions {
                display: flex;
                justify-content: center;
                gap: 12px;
            }
            .action-btn {
                background: none;
                border: none;
                font-size: 20px;
                cursor: pointer;
                transition: transform 0.2s ease, color 0.2s ease;
            }
            .action-btn:hover {
                transform: scale(1.3);
            }
            .action-btn.edit {
                color: #3b82f6;
            }
            .action-btn.edit:hover {
                color: #1e40af;
            }
            .action-btn.delete {
                color: #ef4444;
            }
            .action-btn.delete:hover {
                color: #b91c1c;
            }
            @media (max-width: 768px) {
                .container {
                    margin: 16px;
                    padding: 16px;
                }
                h1 {
                    font-size: 28px;
                }
                .top-bar {
                    flex-direction: column;
                    align-items: flex-start;
                }
                .search-group input[type="text"] {
                    width: 100%;
                }
                th, td {
                    padding: 12px;
                    font-size: 14px;
                }
            }
            @media (max-width: 480px) {
                table {
                    font-size: 13px;
                }
                th, td {
                    padding: 8px;
                }
                .action-btn {
                    font-size: 16px;
                }
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Thời Khóa Biểu</h1>

            <div class="top-bar">
                <div class="search-group">
                    <form action="schedule" method="post">
                        <input type="text" name="keyword" placeholder="Tìm kiếm lớp ...">
                        <button class="search-btn" type="submit" name="search">🔍</button>
                        
                        <c:if test="${not empty err}">
                            <div style="color: red; font-weight: bold; padding: 10px;">${err}</div>
                        </c:if>
                        <c:if test="${not empty msg}">
                            <div style="color: green; font-weight: bold; padding: 10px;">${msg}</div>
                        </c:if>
                    </form>
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
                    <c:forEach items="${schedule}" var="schedule">
                        <tr>
                            <td>${schedule.getId()}</td>
                            <td>${schedule.getNameClass()}</td>
                            <td>${fn:substring(schedule.getStartTime(), 0, 5)}</td> <!-- Hiển thị giờ:phút -->
                            <td>${fn:substring(schedule.getEndTime(), 0, 5)}</td>   <!-- Hiển thị giờ:phút -->
                            <td>${schedule.getDay()}</td>
                            <td>${schedule.getTeacher()}</td>
                            <td>${schedule.getRoom()}</td>
                            <td class="row-actions">
                                <a href="schedule?id=${schedule.getId()}&mode=2" class="action-btn edit">✎</a>
                                <a href="schedule?id=${schedule.getId()}&mode=3" class="action-btn delete">🗑</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>