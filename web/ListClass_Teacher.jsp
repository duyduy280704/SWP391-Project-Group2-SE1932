<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lớp dạy của giáo viên</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="img/favicon.ico" rel="icon">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"> 
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
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
            display: flex;
            flex-direction: column;
            z-index: 1000;
            border-right: 1px solid #dee2e6;
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
        .main-content {
            margin-left: 240px;
            padding: 30px 40px;
            min-height: 100vh;
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
        .topbar {
            padding: 20px;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            gap: 60px;
            margin-left: 220px;
            text-align: center;
        }
        .topbar .info-block {
            display: flex;
            align-items: center;
        }
        .topbar .info-block i {
            font-size: 1.5rem;
            color: #FF6600;
            margin-right: 10px;
        }
        .topbar .info-block div {
            line-height: 1.2;
        }
        .topbar .info-block div h6 {
            font-size: 1.3rem;
            font-weight: 600;
        }
        .topbar .info-block div p {
            font-size: 1.1rem;
            margin-bottom: 0;
        }

        /* Màu bảng xanh dương */
        table.table th {
            background-color: #3498db !important;
            color: white;
            text-align: center;
            vertical-align: middle;
        }

        table.table td {
            text-align: center;
            vertical-align: middle;
        }

        table.table tbody tr:nth-child(odd) {
            background-color: #ecf5fc;
        }

        .btn-primary {
            background-color: #3498db;
            border-color: #3498db;
        }

        .btn-primary:hover {
            background-color: #2c80b4;
            border-color: #2c80b4;
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
                <h6 class="mb-0">Địa Chỉ</h6>
                <p class="mb-0"><c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" /></p>
            </div>
        </div>
        <div class="info-block">
            <i class="fa fa-envelope"></i>
            <div>
                <h6 class="mb-0">Email</h6>
                <p class="mb-0"><c:out value="${setting.email}" default="Email chưa cập nhật" /></p>
            </div>
        </div>
        <div class="info-block">
            <i class="fa fa-phone"></i>
            <div>
                <h6 class="mb-0">Số Điện Thoại</h6>
                <p class="mb-0"><c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" /></p>
            </div>
        </div>
    </div>

    <!-- Main Content -->
    <div class="main-content">
        <div class="container mt-4">
            <h2 class="text-center mb-4">Danh sách lớp bạn đang giảng dạy</h2>

            <c:if test="${not empty classes}">
                <table class="table table-bordered table-hover">
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên lớp</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="c" items="${classes}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>${c.name}</td>
                                <td>
                                    <a class="btn btn-primary btn-sm" href="classStudent?mode=students&classId=${c.id}">Xem học sinh</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty classes}">
                <div class="alert alert-warning text-center">
                    Hiện tại bạn chưa giảng dạy lớp nào.
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
