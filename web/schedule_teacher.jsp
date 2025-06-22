
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thời khóa biểu giáo viên</title>
        <link rel="stylesheet" href="css/style.css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <style>
            body {
                margin: 0;
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f7fb;
                color: #333;
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
                z-index: 1000;
                box-shadow: 2px 0 10px rgba(0,0,0,0.05);
            }
            .sidebar a {
                color: #000;
                padding: 15px 20px;
                text-decoration: none;
            }
            .sidebar a:hover,
            .sidebar a.active {
                background-color: #FF6600;
                color: white;
            }
            .topbar {
                background-color: #f8f9fa;
                padding: 10px 30px;
                border-bottom: 1px solid #ddd;
                display: flex;
                justify-content: space-between;
                align-items: center;
                position: fixed;
                top: 0;
                left: 0;
                right: 0;
                height: 60px;
                z-index: 999;
            }
            .topbar .logo h3 {
                margin: 0;
                font-size: 24px;
                font-weight: bold;
            }
            .topbar .logo span {
                color: #FF6600;
            }
            .topbar .contact-info {
                display: flex;
                gap: 20px;
                font-size: 14px;
                align-items: center;
            }
            .topbar .contact-info i {
                margin-right: 5px;
                color: #007bff;
            }
            h2 {
                text-align: center;
                color: #2c3e50;
                margin-bottom: 25px;
                margin-top: 80px;
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
                border: 1px solid #ddd;
            }
            th {
                background-color: #3498db;
                color: white;
            }
            .error-message {
                color: #e74c3c;
                text-align: center;
                margin: 20px 0;
            }
            .main-content {
                margin-left: 220px;
                padding: 90px 30px 30px;
            }
        </style>
    </head>
    <body>
        <div class="topbar">
            <div class="logo">
                <h3><span>BIG</span>DREAM</h3>
            </div>
            <div class="contact-info">
                <div><i class="fas fa-map-marker-alt"></i> <c:out value="${setting.address}" default="Địa chỉ chưa cập nhật"/></div>
                <div><i class="fas fa-envelope"></i> <c:out value="${setting.email}" default="Email chưa cập nhật"/></div>
                <div><i class="fas fa-phone"></i> <c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật"/></div>
            </div>
        </div>

        <div class="sidebar">
            <div style="text-align: center; padding-bottom: 10px;">
                <h3><span style="color: #FF6600;">BIG</span>DREAM</h3>
            </div>
            <a href="HomePage" class="nav-link">Trang Chủ</a>
            <a href="scheduleTeacher" class="nav-link active">Lịch dạy</a>
            <a href="attendance" class="nav-link">Điểm danh</a>
            <a href="profile?action=view" class="nav-link">Hồ sơ</a>
        </div>

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
                            <th>Điểm danh</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="day" items="${weekDays}">
                            <c:set var="count" value="0" />

                            <c:forEach var="s" items="${scheduleTeacher}">
                                <c:if test="${s.dayVN == day}">
                                    <c:set var="count" value="${count + 1}" />
                                </c:if>
                            </c:forEach>

                            <c:choose>

                                <c:when test="${count > 0}">
                                    <c:set var="printed" value="false" />
                                    <c:forEach var="s" items="${scheduleTeacher}">
                                        <c:if test="${s.dayVN == day}">
                                            <tr>
                                                <c:if test="${not printed}">
                                                    <td rowspan="${count}">${day}</td>
                                                    <c:set var="printed" value="true" />
                                                </c:if>
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
                                                        <button type="submit"
                                                                style="background-color: ${s.attendanceTaken ? '#28a745' : '#dc3545'};
                                                                color: white; padding: 6px 12px;
                                                                border: none; border-radius: 5px;">
                                                            ${s.attendanceTaken ? 'Đã điểm danh' : 'Điểm danh'}
                                                        </button>
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
