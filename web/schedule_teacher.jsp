<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thời khóa biểu giáo viên</title>
        <style>
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f7fb;
                padding: 30px 20px;
                display: flex;
                justify-content: center;
                align-items: flex-start;
                min-height: 100vh;
                color: #333;
            }

            h2 {
                text-align: center;
                color: #2c3e50;
                margin-bottom: 25px;
                font-size: 36px;
                font-weight: 700;
                letter-spacing: 1px;
            }

            .table-container {
                width: 100%;
                max-width: 1000px;
                background: #ffffff;
                border-radius: 12px;
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
                padding: 20px 25px;
            }

            .selector-container {
                display: flex;
                justify-content: center;
                gap: 20px;
                margin-bottom: 20px;
            }

            .selector-container select {
                padding: 10px;
                font-size: 16px;
                border-radius: 8px;
                border: 1px solid #ccc;
                background-color: #fff;
                cursor: pointer;
                outline: none;
                transition: border-color 0.3s ease;
            }

            .selector-container select:focus {
                border-color: #3498db;
            }

            table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 3px 6px rgba(0,0,0,0.05);
            }

            th, td {
                padding: 14px 18px;
                text-align: center;
                font-size: 16px;
                color: #444;
                border-bottom: 1px solid #e1e5ea;
            }

            th {
                background-color: #3498db;
                color: #fff;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.05em;
            }

            tbody tr:hover {
                background-color: #f1f8ff;
                cursor: default;
                transition: background-color 0.3s ease;
            }

            tbody tr:last-child td {
                border-bottom: none;
            }

            .error-message {
                color: #e74c3c;
                text-align: center;
                margin: 20px 0;
                font-size: 18px;
                font-weight: 600;
            }
        </style>
    </head>
    <body>
        <div class="table-container">
            <h2>Thời khóa biểu giáo viên</h2>
            <div class="selector-container">
                <form action="scheduleTeacher" method="get">
                    <label for="year">Chọn năm: </label>
                    <select name="year" id="year" onchange="this.form.submit()">
                        <c:forEach var="year" items="${years}">
                            <option value="${year}" <c:if test="${year == selectedYear}">selected</c:if>>${year}</option>
                        </c:forEach>
                    </select>
                    <label for="week">Chọn tuần: </label>
                    <select name="week" id="week" onchange="this.form.submit()">
                        <c:forEach var="week" items="${weeks}">
                            <option value="${week.startDate}" <c:if test="${week.startDate == selectedWeek}">selected</c:if>>Tuần ${week.weekNumber} (${week.displayStartDate} - ${week.displayEndDate})</option>
                        </c:forEach>
                    </select>
                </form>
            </div>
            <c:if test="${empty scheduleTeacher}">
                <p class="error-message">Không có dữ liệu thời khóa biểu cho tuần này!</p>
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
                        <c:forEach var="day" items="${weekDays}">
                            <c:set var="hasSchedule" value="false" />
                            <c:forEach var="s" items="${scheduleTeacher}">
                                <c:if test="${s.dayVN == day}">
                                    <c:set var="hasSchedule" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${hasSchedule}">
                                    <c:forEach var="s" items="${scheduleTeacher}">
                                        <c:if test="${s.dayVN == day}">
                                            <tr>
                                                <td>${s.dayVN}</td>
                                                <td>
                                                    <fmt:parseDate value="${s.day}" pattern="yyyy-MM-dd" var="parsedDate" />
                                                    <fmt:formatDate value="${parsedDate}" pattern="dd/MM" />
                                                </td>
                                                <td>${s.nameClass}</td>
                                                <td>${fn:substring(s.startTime, 0, 5)}</td>
                                                <td>${fn:substring(s.endTime, 0, 5)}</td>
                                                <td>${s.room}</td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>${day}</td>
                                        <td colspan="5"></td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>