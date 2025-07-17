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

            /* Modal Styles */
            .modal {
                display: none;
                position: fixed;
                z-index: 1002;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.5);
            }

            .modal-content {
                background-color: #fff;
                margin: 10% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 600px;
                border-radius: 10px;
                position: relative;
            }

            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }

            .close:hover,
            .close:focus {
                color: #000;
                text-decoration: none;
                cursor: pointer;
            }

            .event-image {
                max-width: 100%;
                height: auto;
                border-radius: 8px;
                margin-bottom: 15px;
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
            <!-- Topbar End -->

            <!-- Navbar Start -->
            <div class="sidebar" id="sidebar">
                <div class="col-lg-3">
                    <a href="" class="text-decoration-none">
                        <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                    </a>
                </div>
                <div class="profile-container">
                    <c:choose>
                        <c:when test="${not empty profile and not empty profile.pic}">
                            <a href="profile" class="profile-avatar">
                                <img src="${pageContext.request.contextPath}/profile?mode=image&id=${profile.id}&role=${role}" alt="Profile Avatar" class="profile-image">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="profile" class="profile-avatar">
                                <img src="${pageContext.request.contextPath}/img/default-avatar.jpg" alt="Default Avatar" class="profile-image">
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <div class="profile-name">
                        <c:choose>
                            <c:when test="${not empty profile and not empty profile.name}">
                                ${profile.name}
                            </c:when>
                            <c:otherwise>
                                Tên không xác định
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>


                <a href="teacherHome" class="nav-link active">Trang Chủ</a>
                <a href="scheduleTeacher" class="nav-link ">Lịch dạy</a>
                <a href="classStudent" class="nav-link">Danh sách lớp học</a>
                <a href="feedbackByTeacher" class="nav-item nav-link">Dánh giá sinh viên </a>
                <a href="salaryteacher" class="nav-item nav-link">Bảng Lương </a>
                <a href="teacherapplication" class="nav-item nav-link">Gửi Đơn </a>
                <a href="feedback?mode=viewAll" class="nav-link"> Xem phản hồi  </a>
                <a href="#" class="nav-link"> Tin Tức </a>
                <a href="#" class="nav-link"> Sự Kiện</a>
                <a href="logout" class="logout-btn">Đăng xuất</a>
            </div>
            <!-- Navbar End -->

            <!-- Main Content -->
            <div class="main-content" id="main-content">
                <!-- Weekly Schedule Start -->
                <div class="container-fluid py-5 bg-light">
                    <div class="container py-5">
                        <div class="text-center.mb-5">
                            <!-- Phần chào mừng và lời chúc -->
                            <c:set var="currentProfile" value="${not empty profile ? profile : sessionScope.account}" />
                            <c:if test="${not empty currentProfile.name}">
                                <h4 class="text-primary">Xin chào, ${currentProfile.name}!</h4>
                                <p class="text-muted">Chúc bạn một ngày làm việc hiệu quả và tràn đầy năng lượng! 🌟</p>
                            </c:if>
                            <c:if test="${empty currentProfile.name}">
                                <h4 class="text-primary">Xin chào, Giáo viên!</h4>
                                <p class="text-muted">Chúc bạn một ngày làm việc hiệu quả và tràn đầy năng lượng! 🌟</p>
                            </c:if>
                            <h3>🔔 Thông báo</h3>
                            <c:forEach var="notice" items="${notices}">
                                <div class="notice">
                                    <p><strong>${notice.date}</strong>: ${notice.message}</p>
                                </div>
                            </c:forEach>

                            <c:if test="${empty notices}">
                                <p>No notifications found.</p>
                            </c:if>
                            <div class="col-12 mt-3">
                                <a href="scheduleTeacher" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold">Xem Thêm</a>
                            </div>
                        </div>
                        <div class="text-center.mb-5.lich-day">  <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;"></h5>
                            <h1>Lịch dạy tuần này</h1></div>
                        <div class="selector-container">
                            <form action="${pageContext.request.contextPath}/teacherHome" method="get">
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
                            <table class="schedule-table">
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
                                                                <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />

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
                        <div class="col-12 mt-3">
                            <a href="scheduleTeacher" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold">Xem Thêm</a>
                        </div>
                    </div>
                </div>
                <!-- Weekly Schedule End -->
                <!-- Recent Events Start -->
                <div class="container-fluid py-5">
                    <div class="container pt-5 pb-3">
                        <div class="text-center.mb-5.sukien">
                            <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Sự Kiện</h5>
                            <h1>Sự kiện sắp tới</h1>
                        </div>
                        <c:if test="${empty events}">
                            <p class="text-center text-muted">Không có sự kiện nào để hiển thị!</p>
                        </c:if>
                        <c:if test="${not empty events}">
                            <div class="event-list">
                                <c:forEach var="e" items="${events}">
                                    <div class="event-list-item" onclick="showEventDetails('${e.id}', '${fn:escapeXml(e.name)}', '${fn:escapeXml(e.content)}', '${fn:escapeXml(e.date)}', '${e.id}', '${fn:escapeXml(e.courseid)}')">
                                        <div class="event-card bg-light rounded p-3 shadow-sm">
                                            <h4 class="event-title text-primary">${e.name}</h4>
                                            <p class="event-date text-muted mb-2">
                                                <i class="fa fa-calendar-alt mr-2"></i>
                                                <fmt:parseDate value="${e.date}" pattern="yyyy-MM-dd" var="parseDate"/>
                                                <fmt:formatDate value="${parseDate}" pattern="dd/MM/yyyy"/>
                                            </p>
                                            <p class="event-content">${fn:substring(e.content, 0, 150)}...</p>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>

                        </c:if>
                    </div>
                </div>
                <!-- Recent Events End -->
                <!-- Event Details Modal -->
                <div id="eventModal" class="modal">
                    <div class="modal-content">
                        <span class="close" onclick="closeEventModal()">×</span>
                        <h2 id="eventTitle"></h2>
                        <p id="eventDate" class="text-muted"></p>
                        <img id="eventImage" class="event-image" src="" alt="Event Image">
                        <p id="eventContent"></p>
                        <p id="eventCourseId" class="text-muted"></p>
                    </div>
                </div>

                <div class="container-fluid py-5">
                    <div class="container py-5">
                        <div class="text-center.mb-5.danh-gia">
                            <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Đánh Giá</h5>
                            <h1>Các học viên nói về khóa học</h1>
                        </div>
                        <c:if test="${empty feedbackList}">
                            <p class="text-center text-muted">Không có đánh giá nào để hiển thị!</p>
                        </c:if>
                        <c:if test="${not empty feedbackList}">
                            <table class="table table-striped table-hover">
                                <thead class="bg-primary text-white">
                                    <tr>
                                        <th>Học Viên</th>
                                        <th>Phản Hồi</th>
                                        <th>Ngày Đánh Giá</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="f" items="${feedbackList}">
                                        <tr>
                                            <td>${f.studentName}</td>
                                            <td>${fn:substring(f.feedbackText, 0, 100)}${f.feedbackText.length() > 100 ? '...' : ''}</td>
                                            <td>
                                                <fmt:parseDate value="${f.feedbackDate}" pattern="yyyy-MM-dd" var="parseDate"/>
                                                <fmt:formatDate value="${parseDate}" pattern="dd/MM/yyyy"/></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:if>
                        <div class="col-12 mt-3">
                            <a href="Feedback" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold">Xem Thêm</a>
                        </div>
                    </div>
                </div>
                <!-- Testimonial End -->

                <!-- Footer Start -->
                <footer class="bg-dark text-white pt-5 pb-4">
                    <div class="container text-md-left">
                        <div class="row text-md-left">
                            <!-- Liên hệ -->
                            <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                                <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Liên Hệ</h5>
                                <p><i class="fa fa-map-marker-alt mr-2"></i> 
                                    <c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" />
                                </p>
                                <p><i class="fa fa-phone-alt mr-2"></i> 
                                    <c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" />
                                </p>
                                <p><i class="fa fa-envelope mr-2"></i> 
                                    <c:out value="${setting.email}" default="Email chưa cập nhật" />
                                </p>
                                <div class="mt-3">
                                    <a class="btn btn-outline-light btn-sm mr-2" href="${setting.facebookLink != null ? setting.facebookLink : '#'}">
                                        <i class="fab fa-facebook-f"></i>
                                    </a>
                                    <a class="btn btn-outline-light btn-sm mr-2" href="${setting.instagramLink != null ? setting.instagramLink : '#'}">
                                        <i class="fab fa-instagram"></i>
                                    </a>
                                    <a class="btn btn-outline-light btn-sm mr-2" href="${setting.youtubeLink != null ? setting.youtubeLink : '#'}">
                                        <i class="fab fa-youtube"></i>
                                    </a>
                                </div>
                            </div>
                            <!-- Khoá học -->
                            <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                                <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Khoá học</h5>
                                <ul class="list-unstyled">
                                    <c:forEach var="t" items="${applicationScope.typeList}">
                                        <li>
                                            <a href="#" class="text-white">
                                                <i class="fa fa-angle-right mr-2"></i> ${t.name}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <!-- Thông tin thêm -->
                            <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                                <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Về Chúng Tôi</h5>
                                <p><c:out value="${setting.about}" default="Thông tin chưa cập nhật." /></p>
                            </div>
                        </div>
                        <hr class="mb-4">
                        <!-- Bản quyền -->
                        <div class="row align-items-center">
                            <div class="col-md-7 col-lg-8">
                                <p class="text-white">
                                    <c:out value="${setting.copyright}" default="© 2025 Trung Tâm Năng Khiếu. All rights reserved." />
                                </p>
                            </div>
                            <div class="col-md-5 col-lg-4">
                                <div class="text-right">
                                    <a class="text-white" href="${setting.policyLink != null ? setting.policyLink : '#'}">Chính sách</a> |
                                    <a class="text-white" href="${setting.termsLink != null ? setting.termsLink : '#'}">Điều khoản</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </footer>
                <!-- Footer End -->

                <!-- Back to Top -->
                <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="fa fa-angle-double-up"></i></a>
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

                            function showEventDetails(id, name, content, date, eventId, courseId) {
                                const modal = document.getElementById('eventModal');
                                const title = document.getElementById('eventTitle');
                                const eventDate = document.getElementById('eventDate');
                                const eventImage = document.getElementById('eventImage');
                                const eventContent = document.getElementById('eventContent');
                                const eventCourseId = document.getElementById('eventCourseId');

                                title.textContent = name;
                                eventDate.textContent = 'Ngày: ' + date;
                                eventContent.textContent = content;
                                eventCourseId.textContent = 'Mã khóa học: ' + (courseId || 'Không có');

                                // Set image source to TeacherHome servlet with eventId
                                if (eventId && eventId !== 'null') {
                                    eventImage.src = '${pageContext.request.contextPath}/teacherHome?eventId=' + eventId;
                                    eventImage.style.display = 'block';
                                } else {
                                    eventImage.style.display = 'none';
                                }

                                modal.style.display = 'block';
                            }

                            function closeEventModal() {
                                const modal = document.getElementById('eventModal');
                                modal.style.display = 'none';
                            }

                            // Close modal when clicking outside
                            window.onclick = function (event) {
                                const modal = document.getElementById('eventModal');
                                if (event.target == modal) {
                                    modal.style.display = 'none';
                                }
                            }
            </script>
    </body>
</html>