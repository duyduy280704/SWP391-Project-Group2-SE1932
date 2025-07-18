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
            /* Sidebar and Main Content Styling */
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
                overflow-y: auto;
                width: 250px;
                background-color: #ffffff;
                padding-top: 60px;
                display: flex;
                flex-direction: column;
                transition: transform 0.3s ease-in-out;
                z-index: 1000;
            }

            .sidebar.hidden {
                transform: translateX(-250px);
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
                margin-left: 250px;
                padding: 30px;
                transition: margin-left 0.3s ease-in-out, width 0.3s ease-in-out;
                width: calc(100% - 220px);
            }

            .main-content.full {
                margin-left: 0;
                width: 100%;
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
                    width: 100%;
                }
                .main-content.full {
                    margin-left: 0;
                    width: 100%;
                }
                .toggle-btn {
                    left: 10px;
                }
                .toggle-btn.hidden {
                    left: 230px;
                }
            }

            /* Existing table and other styles retained */
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

            .schedule-table {
                width: 100%;
                border-collapse: collapse;
                padding: 0;
            }

            .schedule-table th,
            .schedule-table td {
                padding: 14px;
                text-align: center;
                border: none;
            }

            .schedule-table th {
                background-color: transparent;
                color: inherit;
            }

            .schedule-table .error-message {
                text-align: center;
                margin: 20px 0;
            }

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
                    <c:set var="picturePath" value="${not empty picturePath ? picturePath : sessionScope.picturePath}" />
                    <c:choose>
                        <c:when test="${not empty picturePath}">
                            <a href="profile" class="profile-avatar">
                                <img src="${pageContext.request.contextPath}/${picturePath}" alt="Profile Avatar">
                            </a>
                            <div class="profile-name">${profile != null ? profile.name : 'Tên không xác định'}</div>
                        </c:when>
                        <c:otherwise>
                            <a href="profile" class="profile-avatar">
                                <img src="${pageContext.request.contextPath}/img/default-avatar.jpg" alt="Default Avatar">
                            </a>
                            <div class="profile-name">${profile != null ? profile.name : 'Tên không xác định'}</div>
                        </c:otherwise>
                    </c:choose>
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
            <h2 class="text-center mb-4">Danh sách học sinh lớp: <span class="text-primary">${className}</span></h2>

            <form method="get" action="classStudent" class="mb-4">
                <input type="hidden" name="mode" value="students">
                <input type="hidden" name="classId" value="${classId}">
                <div class="row mb-3">
                    <div class="col-md-6">
                        <input type="text" name="search" class="form-control" placeholder="Tìm tên học sinh..." value="${search}">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                    </div>
                </div>
            </form>

            <table class="table table-bordered table-hover">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Họ tên</th>
                        <th>Giới tính</th>
                        <th>Ngày sinh</th>
                        <th>Số điện thoại</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="s" items="${students}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${s.name}</td>
                            <td>${s.gender}</td>
                            <td>${s.birthdate}</td>
                            <td>${s.phone}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty students}">
                        <tr>
                            <td colspan="6" class="text-center text-danger">Không có học sinh nào trong lớp này.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
   

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
        </script>
    </body>

</html>