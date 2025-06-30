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
            <a class="nav-link" href="feedback?mode=viewAll"> Xem phản hồi  </a>>
        </div>

        <meta charset="utf-8">
        <title>BIGDREAM</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free JSP Templates" name="keywords">
        <meta content="Free JSP Templates" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"> 

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
        <style>
            .table thead th {
                padding: 12px;
                text-align: center;
            }

            .table tbody td {
                vertical-align: middle;
                padding: 12px;
                text-align: center;
            }

            .testimonial-img {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                border: 2px solid #FF6600;
            }

            .table-striped tbody tr:nth-of-type(odd) {
                background-color: #f8f9fa;
            }

            .table-hover tbody tr:hover {
                background-color: #e9ecef;
                transition: background-color 0.3s ease;
            }

            .profile-avatar {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                overflow: hidden;
                border: 2px solid #FF6600;
                cursor: pointer;
                margin-bottom: 5px;
                display: block;
            }

            .profile-avatar img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }

            body {
                margin: 0;
                padding: 0;
                overflow-x: hidden;
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
                transition: transform 0.3s ease-in-out;
                z-index: 1000;
            }

            .sidebar.hidden {
                transform: translateX(-220px);
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
            }

            .main-content {
                margin-left: 220px;
                padding: 20px;
                transition: margin-left 0.3s ease-in-out;
            }

            .main-content.full {
                margin-left: 0;
            }

            .toggle-btn {
                position: fixed;
                top: 20px;
                left: 230px;
                z-index: 1001;
                background-color: #343a40;
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                transition: left 0.3s ease-in-out;
            }

            .toggle-btn.hidden {
                left: 10px;
            }

            @media (max-width: 768px) {
                .sidebar {
                    transform: translateX(-220px);
                }
                .sidebar.active {
                    transform: translateX(0);
                }
                .main-content {
                    margin-left: 0;
                }
                .main-content.full {
                    margin-left: 0;
                }
                .toggle-btn {
                    left: 10px;
                }
                .toggle-btn.hidden {
                    left: 230px;
                }
            }

            /* Đồng bộ hóa bảng Lịch dạy tuần này với scheduleTeacher.jsp */
            .schedule-table {
                width: 100%;
                border-collapse: collapse;
                padding: 0; /* Loại bỏ padding thừa */
            }

            .schedule-table th,
            .schedule-table td {
                padding: 14px;
                text-align: center;
                border: none; /* Loại bỏ border */
            }

            .schedule-table th {
                background-color: transparent; /* Loại bỏ màu nền */
                color: inherit; /* Sử dụng màu văn bản mặc định */
            }

            .schedule-table .error-message {
                text-align: center;
                margin: 20px 0;
            }

            /* Phần chào mừng */
            .text-center.mb-5 {
                text-align: left;
                margin-bottom: 20px;
            }

            .text-center.mb-5 h4 {
                font-size: 1.5rem;
                margin-bottom: 5px;
            }

            .text-center.mb-5 p {
                font-size: 1rem;
                margin-bottom: 0;
            }

            /* Phần Lịch dạy */
            .text-center.mb-5.lich-day {
                text-align: left;
                margin-bottom: 30px;
            }

            .text-center.mb-5.lich-day h5 {
                text-transform: uppercase;
                letter-spacing: 5px;
                font-size: 1.25rem;
                margin-bottom: 10px;
            }

            .text-center.mb-5.lich-day h1 {
                font-size: 1.25rem;
                margin: 0;
            }

            .schedule-table-container {
                padding: 20px;
            }

            /* Phần Sự kiện */
            .text-center.mb-5.sukien {
                text-align: left;
                margin-bottom: 30px;
            }

            .text-center.mb-5.sukien h5 {
                text-transform: uppercase;
                letter-spacing: 5px;
                font-size: 1.25rem;
                margin-bottom: 10px;
            }

            .text-center.mb-5.sukien h1 {
                font-size: 2.5rem;
                margin: 0;
            }

            .event-list {
                text-align: left;
                margin: 0;
                padding: 0;
            }

            .event-list-item {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
            }

            .event-list-item h4 {
                margin: 0 10px 0 0;
                font-size: 1.25rem;
            }

            .event-list-item p {
                margin: 0;
                font-size: 1rem;
            }

            /* Phần Đánh giá */
            .text-center.mb-5.danh-gia {
                text-align: left;
                margin-bottom: 30px;
            }

            .text-center.mb-5.danh-gia h5 {
                text-transform: uppercase;
                letter-spacing: 5px;
                font-size: 1.25rem;
                margin-bottom: 10px;
            }

            .text-center.mb-5.danh-gia h1 {
                font-size: 2.5rem;
                margin: 0;
            }
        </style>
    </head>
    <body>
        <!-- Toggle Button -->
        <button class="toggle-btn" onclick="toggleSidebar()">
            <i class="fas fa-bars"></i>
        </button>

        <!-- Topbar Start -->
        <div class="container-fluid d-none d-lg-block ">
            <div class="row align-items-center py-4 px-xl-5 justify-content-end">
                <div></div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-map-marker-alt text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Địa Chỉ</h6>
                            <p>
                                <c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" />
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-envelope text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Email</h6>
                            <p>
                                <c:out value="${setting.email}" default="Email chưa cập nhật" />
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-phone text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Số Điện Thoại</h6>
                            <p>
                                <c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" />
                            </p>
                        </div>
                    </div>
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