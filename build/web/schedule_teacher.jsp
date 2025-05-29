<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<%
    // Mảng ngày trong tuần
    String[] weekdays = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"};
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thời khóa biểu Giáo viên</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #E6F0FA; /* Màu xanh nhạt */
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
                font-size: 32px; /* Tăng cỡ chữ tiêu đề */
            }

            .table-container {
                width: 100%;
                max-width: 1000px; /* Tăng chiều rộng tối đa */
                background-color: #ffffff;
                border-radius: 10px;
                box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                overflow-x: hidden; /* Loại bỏ thanh cuộn ngang */
            }

            table {
                width: 100%;
                border-collapse: collapse;
                table-layout: fixed; /* Đảm bảo các cột có kích thước cố định */
            }

            th, td {
                border: 1px solid #dee2e6;
                padding: 18px; /* Tăng padding để ô lớn hơn */
                text-align: center;
                font-size: 18px; /* Tăng cỡ chữ */
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
                width: 160px; /* Tăng chiều rộng cột "Thông tin" */
            }

            td {
                background-color: #f8f9fa;
            }

            tr:nth-child(even) td {
                background-color: #e9ecef;
            }

            @media (max-width: 768px) {
                th, td {
                    padding: 12px; /* Giảm padding trên màn hình nhỏ */
                    font-size: 16px; /* Giảm cỡ chữ trên màn hình nhỏ */
                }

                th:first-child {
                    width: 120px; /* Giảm chiều rộng cột "Thông tin" trên màn hình nhỏ */
                }

                h2 {
                    font-size: 26px; /* Giảm cỡ chữ tiêu đề trên màn hình nhỏ */
                }
            }
        </style>
    </head>
    <body>
        <div class="table-container">
            <h2>Thời khóa biểu giáo viên</h2>
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
                    <% for (String day : weekdays) { %>
                        <td>
                            <c:forEach var="s" items="${scheduleTeacher}">
                                <c:if test="${s.day == day}">
                                    ${s.specificDay}<br>
                                </c:if>
                            </c:forEach>
                        </td>
                    <% } %>
                </tr>
                <tr>
                    <td>Lớp</td>
                    <% for (String day : weekdays) { %>
                        <td>
                            <c:forEach var="s" items="${scheduleTeacher}">
                                <c:if test="${s.day == day}">
                                    ${s.nameClass}<br>
                                </c:if>
                            </c:forEach>
                        </td>
                    <% } %>
                </tr>
                <tr>
                    <td>Bắt đầu</td>
                    <% for (String day : weekdays) { %>
                        <td>
                            <c:forEach var="s" items="${scheduleTeacher}">
                                <c:if test="${s.day == day}">
                                    ${s.startTime}<br>
                                </c:if>
                            </c:forEach>
                        </td>
                    <% } %>
                </tr>
                <tr>
                    <td>Kết thúc</td>
                    <% for (String day : weekdays) { %>
                        <td>
                            <c:forEach var="s" items="${scheduleTeacher}">
                                <c:if test="${s.day == day}">
                                    ${s.endTime}<br>
                                </c:if>
                            </c:forEach>
                        </td>
                    <% } %>
                </tr>
                <tr>
                    <td>Phòng học</td>
                    <% for (String day : weekdays) { %>
                        <td>
                            <c:forEach var="s" items="${scheduleTeacher}">
                                <c:if test="${s.day == day}">
                                    ${s.room}<br>
                                </c:if>
                            </c:forEach>
                        </td>
                    <% } %>
                </tr>
                <!-- Giữ vòng lặp cũ nhưng sửa để không tạo hàng mới -->
                <c:forEach items="${scheduleTeacher}" var="s">
                    <!-- Không tạo hàng mới, chỉ dùng để debug nếu cần -->
                    <!-- Có thể bỏ phần này nếu không cần -->
                    <tr style="display:none;">
                        <td>${s.day}</td>
                        <td>${s.nameClass}</td>
                        <td>${s.startTime}</td>
                        <td>${s.endTime}</td>
                        <td>${s.room}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </body>
</html>