<%-- 
    Document   : ListStudentByClass_Teacher
    Created on : Jun 30, 2025
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Danh sách học sinh theo lớp (Giáo viên)</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <link href="img/favicon.ico" rel="icon">
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"> 
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">

        <style>
            body {
                margin: 0;
                padding: 0;
                overflow-x: hidden;
                font-family: 'Poppins', sans-serif;
                background-color: #f5f7fa;
            }

            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                width: 220px;
                background-color: #ffffff;
                padding-top: 60px;
                border-right: 1px solid #dee2e6;
                display: flex;
                flex-direction: column;
                z-index: 1000;
            }

            .sidebar a {
                color: #343a40;
                padding: 15px 20px;
                text-decoration: none;
                font-weight: 500;
                border-left: 4px solid transparent;
                transition: all 0.3s ease;
            }

            .sidebar a:hover,
            .sidebar a.active {
                background-color: #ff6600;
                color: #fff;
                border-left: 4px solid #fff;
            }

            .topbar {
                padding: 15px 30px;
                background-color: #fff;
                display: flex;
                justify-content: center;
                gap: 60px;
                border-bottom: 1px solid #dee2e6;
                margin-left: 220px;
                position: sticky;
                top: 0;
                z-index: 999;
            }

            .topbar .info-block {
                display: flex;
                align-items: center;
            }

            .topbar .info-block i {
                font-size: 1.5rem;
                color: #ff6600;
                margin-right: 10px;
            }

            .topbar .info-block div h6 {
                font-size: 1rem;
                font-weight: 600;
                margin-bottom: 3px;
            }

            .topbar .info-block div p {
                font-size: 0.95rem;
                margin: 0;
            }

            .main-content {
                margin-left: 240px;
                padding: 30px 40px;
                min-height: 100vh;
                background-color: #f9f9f9;
            }

            h2 span.text-primary {
                color: #ff6600 !important;
            }

            table.table {
                background-color: #fff;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            }

            table th, table td {
                text-align: center;
                vertical-align: middle;
            }

            table.table th {
                background-color: #3498db !important;
                color: white;
            }


            .btn-primary {
                background-color: #ff6600;
                border-color: #ff6600;
            }

            .btn-primary:hover {
                background-color: #e05500;
                border-color: #e05500;
            }

            .form-control:focus {
                border-color: #ff6600;
                box-shadow: 0 0 0 0.2rem rgba(255, 102, 0, 0.25);
            }

            .toggle-btn {
                position: fixed;
                top: 20px;
                left: 230px;
                background-color: #343a40;
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                z-index: 1001;
                border-radius: 4px;
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
            <a href="feedback?mode=viewAll" class="nav-item nav-link">Xem phản hồi</a>
        </div>

        <!-- Toggle Button -->
        <button class="toggle-btn" onclick="document.querySelector('.sidebar').classList.toggle('hidden')">
            <i class="fas fa-bars"></i>
        </button>

        <!-- Topbar -->
        <div class="topbar">
            <div class="info-block">
                <i class="fa fa-map-marker-alt"></i>
                <div>
                    <h6>Địa Chỉ</h6>
                    <p><c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" /></p>
                </div>
            </div>
            <div class="info-block">
                <i class="fa fa-envelope"></i>
                <div>
                    <h6>Email</h6>
                    <p><c:out value="${setting.email}" default="Email chưa cập nhật" /></p>
                </div>
            </div>
            <div class="info-block">
                <i class="fa fa-phone"></i>
                <div>
                    <h6>Số Điện Thoại</h6>
                    <p><c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" /></p>
                </div>
            </div>
        </div>

        <!-- Main Content -->
        <div class="main-content">
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
                        <th>Mã học sinh</th>
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
                            <td>${s.id}</td>
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
    </body>
</html>
