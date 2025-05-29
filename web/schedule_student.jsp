<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thời khóa biểu học sinh</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #E6F0FA;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        h2 {
            text-align: center;
            color: #0056b3;
            margin-bottom: 20px;
            font-size: 32px;
        }
        .table-container {
            width: 100%;
            max-width: 1000px;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 18px;
            border: 1px solid #ccc;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) td {
            background-color: #f1f1f1;
        }
        .error-message {
            color: red;
            text-align: center;
            margin: 20px;
        }
    </style>
</head>
<body>
    <div class="table-container">
        <h2>Thời khóa biểu học sinh</h2>
        <c:if test="${empty scheduleStudent}">
            <p class="error-message">Không có dữ liệu thời khóa biểu cho học sinh này!</p>
        </c:if>
        <c:if test="${not empty scheduleStudent}">
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
                    <c:forEach var="s" items="${scheduleStudent}">
                        <tr>
                            <td>${s.dayVN}</td>
                            <td>${s.day}</td>
                            <td>${s.nameClass}</td>
                            <td>${fn:substring(s.startTime, 0, 5)}</td>
                            <td>${fn:substring(s.endTime, 0, 5)}</td>
                            <td>${s.room}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>