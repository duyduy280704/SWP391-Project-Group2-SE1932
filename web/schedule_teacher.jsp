<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Thời khóa biểu giáo viên</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">

        <style>
            body {
                margin: 0;
                padding: 0;
                font-family: 'Poppins', sans-serif;
                background-color: #f4f7fb;
            }

            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                width: 220px;
                background-color: #ffffff;
                padding-top: 60px;
                display: flex;
                flex-direction: column;
                box-shadow: 2px 0 10px rgba(0,0,0,0.05);
            }

            .sidebar a {
                color: #000;
                padding: 15px 20px;
                text-decoration: none;
                transition: background 0.3s;
            }

            .sidebar a:hover,
            .sidebar a.active {
                background-color: #FF6600;
                color: white;
            }

            .topbar {
                width: 100%;
                background-color: #f8f9fa;
                padding: 10px 230px 10px 240px;
                display: flex;
                justify-content: flex-end;
                align-items: center;
                gap: 40px;
                box-shadow: 0 1px 5px rgba(0,0,0,0.1);
            }

            .topbar-item {
                display: flex;
                align-items: center;
                gap: 10px;
                font-size: 14px;
                color: #333;
            }

            .topbar-item i {
                color: #007bff;
            }

            .main-content {
                margin-left: 220px;
                padding: 30px;
            }

            h2 {
                text-align: center;
                color: #2c3e50;
                margin-bottom: 25px;
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
            }

            table {
                width: 100%;
                border-collapse: collapse;
                background: #fff;
                box-shadow: 0 3px 6px rgba(0,0,0,0.05);
            }

            th, td {
                padding: 14px;
                text-align: center;
                border-bottom: 1px solid #e1e5ea;
            }

            th {
                background-color: #3498db;
                color: white;
            }

            .error-message {
                color: #e74c3c;
                text-align: center;
                margin: 20px 0;
                font-size: 18px;
            }

            .btn-attendance {
                background-color: #28a745;
                color: white;
                padding: 6px 12px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            .btn-attendance:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>

        <!-- Sidebar -->
        <div class="sidebar">
            <div style="text-align: center; padding-bottom: 10px;">
                <h3><span style="color: #FF6600;">BIG</span>DREAM</h3>
            </div>
            <a href="HomePage" class="nav-item nav-link">Trang Chủ</a>
            <a href="classStudent" class="nav-item nav-link">Danh sách các lớp</a>
            <a href="scheduleTeacher" class="nav-item nav-link">Lịch dạy</a>
            <a class="nav-link" href="feedback?mode=viewAll"> Phản hồi học viên </a>>
        </div>

        <!-- Topbar -->
        <div class="topbar">
            <div class="topbar-item">
                <i class="fas fa-map-marker-alt"></i>
                <span><c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" /></span>
            </div>
            <div class="topbar-item">
                <i class="fas fa-envelope"></i>
                <span><c:out value="${setting.email}" default="Email chưa cập nhật" /></span>
            </div>
            <div class="topbar-item">
                <i class="fas fa-phone"></i>
                <span><c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" /></span>
            </div>
        </div>

        <!-- Main Content -->
        <div class="main-content">
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
                            <option value="${week.startDate}" <c:if test="${week.startDate == selectedWeek}">selected</c:if>>
                                ${week.displayStartDate} - ${week.displayEndDate}
                            </option>

                        </c:forEach>
                    </select>
                </form>
            </div>

            <c:if test="${empty scheduleTeacher}">
                <p class="error-message">Không có dữ liệu thời khóa biểu tuần này!</p>
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
                            <th>Điểm danh</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="day" items="${weekDays}">
                            <c:set var="hasSchedule" value="false" />
                            <c:forEach var="s" items="${scheduleTeacher}">
                                <c:if test="${not empty s.dayVN and s.dayVN == day}">
                                    <c:set var="hasSchedule" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${hasSchedule}">
                                    <c:forEach var="s" items="${scheduleTeacher}">
                                        <c:if test="${not empty s.dayVN and s.dayVN == day}">
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
                                                <td>
                                                    <form action="scheduleTeacher" method="get">
                                                        <input type="hidden" name="action" value="attendance" />
                                                        <input type="hidden" name="scheduleId" value="${s.id}" />
                                                        <input type="hidden" name="classId" value="${s.classId}" />
                                                        <input type="hidden" name="className" value="${s.nameClass}" />
                                                        <input type="hidden" name="day" value="${s.day}" />
                                                        <button type="submit" class="btn-attendance">Điểm danh</button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>${day}</td>
                                        <td colspan="6"></td>
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