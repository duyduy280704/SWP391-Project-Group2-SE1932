<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Phản hồi từ học sinh</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: 'Poppins', sans-serif;
            background-color: #f5f7fa;
        }
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            height: 100vh;
            width: 220px;
            background-color: #fff;
            padding-top: 60px;
            display: flex;
            flex-direction: column;
            border-right: 1px solid #ddd;
            z-index: 1000;
        }
        .sidebar a {
            color: #000;
            padding: 15px 20px;
            text-decoration: none;
            transition: 0.3s;
        }
        .sidebar a:hover,
        .sidebar a.active {
            background-color: #FF6600;
            color: #fff;
        }
        .topbar {
            margin-left: 220px;
            background-color: #f8f9fa;
            padding: 20px;
            display: flex;
            justify-content: center;
            gap: 60px;
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
        .topbar .info-block div h6 {
            font-size: 1.2rem;
            margin-bottom: 0;
            font-weight: 600;
        }
        .topbar .info-block div p {
            margin: 0;
        }
        .main-content {
            margin-left: 240px;
            padding: 40px;
        }
        table th {
            background-color: #3498db !important;
            color: white;
            text-align: center;
        }
        table td {
            text-align: center;
        }
        table tbody tr:nth-child(odd) {
            background-color: #ecf5fc;
        }
        .btn-secondary {
            background-color: #6c757d;
            border: none;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <div style="text-align: center; padding-bottom: 10px;">
        <h3><span style="color: #FF6600;">BIG</span>DREAM</h3>
    </div>
    <a href="HomePage">Trang Chủ</a>
    <a href="classStudent">Danh sách các lớp</a>
    <a href="scheduleTeacher">Lịch dạy</a>
    <a href="feedback?mode=viewAll" class="active">Xem phản hồi</a>
</div>

<!-- Topbar -->
<div class="topbar">
    <div class="info-block">
        <i class="fa fa-map-marker-alt"></i>
        <div>
            <h6>Địa Chỉ</h6>
            <p><c:out value="${setting.address}" default="Chưa cập nhật" /></p>
        </div>
    </div>
    <div class="info-block">
        <i class="fa fa-envelope"></i>
        <div>
            <h6>Email</h6>
            <p><c:out value="${setting.email}" default="Chưa cập nhật" /></p>
        </div>
    </div>
    <div class="info-block">
        <i class="fa fa-phone"></i>
        <div>
            <h6>Điện Thoại</h6>
            <p><c:out value="${setting.phone}" default="Chưa cập nhật" /></p>
        </div>
    </div>
</div>

<!-- Main Content -->
<div class="main-content">
    <h2 class="text-center mb-4">Phản hồi về lớp học bạn giảng dạy</h2>

    <c:if test="${empty feedbackList}">
        <div class="alert alert-warning text-center">Không có phản hồi nào.</div>
    </c:if>

    <c:if test="${not empty feedbackList}">
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th>STT</th>
                    <th>Tên học sinh</th>
                    <th>Lớp</th>
                    <th>Thời gian</th>
                    <th>Nội dung phản hồi</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="f" items="${feedbackList}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${f.studentName}</td>
                        <td>${f.className}</td>
                        <td>${f.feedbackDate}</td>
                        <td>${f.feedbackText}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>

    
</div>

</body>
</html>
