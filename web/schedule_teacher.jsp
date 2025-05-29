<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <!-- Thêm dòng này -->
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thời khóa biểu Giáo viên</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #E6F0FA;
                margin: 0;
                padding: 20px;
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
            }
            h2 {
                color: #0056b3;
                text-align: center;
                margin-bottom: 20px;
                font-size: 32px;
            }
            .table-container {
                width: 100%;
                max-width: 1000px;
                background-color: #ffffff;
                border-radius: 10px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                overflow-x: hidden;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                table-layout: fixed;
            }
            th, td {
                border: 1px solid #dee2e6;
                padding: 18px;
                text-align: center;
                font-size: 18px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
            }
            th {
                background-color: #007bff;
                color: white;
                font-weight: bold;
            }
            th:first-child {
                background-color: #e9ecef;
                color: #333;
                font-weight: bold;
                width: 160px;
            }
            td {
                background-color: #f8f9fa;
            }
            tr:nth-child(even) td {
                background-color: #e9ecef;
            }
            .error-message {
                color: red;
                text-align: center;
                margin: 20px;
            }
            @media (max-width: 768px) {
                th, td {
                    padding: 12px;
                    font-size: 16px;
                }
                th:first-child {
                    width: 120px;
                }
                h2 {
                    font-size: 26px;
                }
            }
        </style>
    </head>
    <body>
        <div class="table-container">
            <h2>Thời khóa biểu giáo viên</h2>
            <c:if test="${empty scheduleTeacher}">
                <p class="error-message">Không có dữ liệu thời khóa biểu cho giáo viên này!</p>
            </c:if>
            <c:if test="${not empty scheduleTeacher}">
                <table>
                    <thead>
                        <tr>
                            <th>Thứ</th>
                            <th>Ngày</th>
                            <th>Lớp</th>
                            <th>Bắt đầu</th>
                            <th>Kết thúc</th>
                            <th>Phòng học</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${scheduleTeacher}">
                            <tr>
                                <td>${s.dayVN}</td>
                                <td>${s.day}</td>
                                <td>${s.nameClass}</td>
                                <td>${fn:substring(s.startTime, 0, 5)}</td> <!-- Chỉ lấy giờ:phút -->
                                <td>${fn:substring(s.endTime, 0, 5)}</td>   <!-- Chỉ lấy giờ:phút -->
                                <td>${s.room}</td>
                            </tr> 
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>