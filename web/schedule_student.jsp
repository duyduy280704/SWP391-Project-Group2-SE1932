<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
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
                padding: 0px;
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
            .img-container {
                position: relative;
                width: 100%;
                padding-top: 66.66%;
                overflow: hidden;
                background-color: #f8f8f8;
            }

            .course-img {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
           
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
            .topbar .logo img {
                height: 40px;
            }
            .topbar .contact-info {
                display: flex;
                gap: 20px;
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
                padding: 0x 30px 30px;
            }
        </style>
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
        </div>
        <!-- Topbar End -->

        <!-- sidebar Start -->
        <div class="sidebar" id="sidebar">
            <div class="col-lg-3">
                <a href="" class="text-decoration-none">
                    <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                </a>
            </div>
            <a href="StudentHome" class="nav-item nav-link active">Trang Chủ</a>
            <a href="Course" class="nav-item nav-link">Khóa Học</a>
            <a href="scheduleStudent" class="nav-item nav-link">Lịch Học</a>
            <a href="TeacherList" class="nav-item nav-link">Giáo Viên</a>
            <a href="classTransfer" class="nav-link">Xin Chuyển Lớp</a>
            <a href="StudentPayment" class="nav-item nav-link">Thanh Toán</a>
            <a href="studentapplication" class="nav-link">Gửi Đơn</a>
            <a href="feedback" class="nav-link">Phản Hồi Khóa Học</a>
            <a href="Notification" class="nav-item nav-link">Thông Báo</a>
            <a href="blog.jsp" class="nav-item nav-link">Tin Tức</a>
            <a href="#" class="nav-item nav-link">Sự Kiện</a> 
            <a href="logout" class="nav-item nav-link">Đăng Xuất</a>
        </div>
        <!-- sidebar End -->

        <div class="main-content">
            <h2>Thời khóa biểu học sinh</h2>
            <div class="selector-container">
                <form action="scheduleStudent" method="get">
                    <label for="year">Chọn năm: </label>
                    <select name="year" id="year" onchange="this.form.submit()">
                        <c:forEach var="year" items="${years}">
                            <option value="${year}" <c:if test="${year == selectedYear}">selected</c:if>>${year}</option>
                        </c:forEach>
                    </select>
                    <label for="week">Chọn tuần: </label>
                    <select name="week" id="week" onchange="this.form.submit()">
                        <c:forEach var="week" items="${weeks}">
                            <option value="${week.startDate}" <c:if test="${week.startDate == selectedWeek}">selected</c:if>>${week.displayStartDate} - ${week.displayEndDate}</option>
                        </c:forEach>
                    </select>
                </form>
            </div>

            <c:if test="${empty scheduleStudent}">
                <p class="error-message">Không có dữ liệu thời khóa biểu cho tuần này!</p>
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
                            <th>Điểm danh</th>
                            <th>Lý do</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="day" items="${weekDays}">
                            <c:set var="count" value="0" />
                            <c:forEach var="s" items="${scheduleStudent}">
                                <c:if test="${s.dayVN == day}">
                                    <c:set var="count" value="${count + 1}" />
                                </c:if>
                            </c:forEach>

                            <c:if test="${count > 0}">
                                <c:set var="printed" value="false" />
                                <c:forEach var="s" items="${scheduleStudent}">
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
                                                <c:choose>
                                                    <c:when test="${s.attendanceStatus == 'present'}">Có mặt</c:when>
                                                    <c:when test="${s.attendanceStatus == 'absent'}">Vắng mặt</c:when>
                                                    <c:otherwise>Chưa điểm danh</c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty s.reason}">
                                                        ${s.reason}
                                                    </c:when>
                                                    <c:otherwise>-</c:otherwise>
                                                </c:choose>
                                            </td>

                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </c:if>

                            <c:if test="${count == 0}">
                                <tr>
                                    <td>${day}</td>
                                    <td colspan="6"></td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    
        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <!-- Contact Javascript File -->
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>
        <!-- Template Javascript -->
        <script src="js/main.js"></script>
        <!-- Sidebar Toggle Script -->
        <script>
            function toggleSidebar() {
                const sidebar = document.getElementById('sidebar');
                const mainContent = document.getElementById('main-content');
                const toggleBtn = document.querySelector('.toggle-btn');

                sidebar.classList.toggle('hidden');
                mainContent.classList.toggle('full');
                toggleBtn.classList.toggle('hidden');

                // Change icon based on sidebar state
                const icon = toggleBtn.querySelector('i');
                if (sidebar.classList.contains('hidden')) {
                    icon.classList.remove('fa-times');
                    icon.classList.add('fa-bars');
                } else {
                    icon.classList.remove('fa-bars');
                    icon.classList.add('fa-times');
                }
            }
        </script>
    </body>
</html>