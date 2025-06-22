<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Phản hồi lớp học</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css">
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 0;
                background-color: #f4f7fb;
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

            .sidebar {
                position: fixed;
                top: 60px;
                left: 0;
                height: calc(100vh - 60px);
                width: 220px;
                background-color: #ffffff;
                padding-top: 20px;
                display: flex;
                flex-direction: column;
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

            .main-content {
                margin-left: 220px;
                padding: 90px 40px 40px;
            }

            h2 {
                color: #333;
            }

            form {
                max-width: 600px;
                background-color: #fff;
                padding: 24px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,0.1);
            }

            label {
                font-weight: bold;
                display: block;
                margin-top: 16px;
            }

            select, textarea, input[type="submit"] {
                width: 100%;
                padding: 10px;
                margin-top: 8px;
                margin-bottom: 16px;
                border: 1px solid #ccc;
                border-radius: 4px;
            }

            input[type="submit"] {
                background-color: #007BFF;
                color: white;
                cursor: pointer;
                border: none;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            .message {
                color: green;
                font-weight: bold;
                margin-bottom: 20px;
            }

            .error {
                color: red;
                font-weight: bold;
                margin-bottom: 20px;
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
            <a href="HomePage" class="nav-link">Trang Chủ</a>
            <a href="scheduleStudent" class="nav-link">Lịch học</a>
            <a href="feedback" class="nav-link">Phản hồi khóa học </a>

            <a href="profile?action=view" class="nav-link">Hồ sơ</a>
        </div>
        <div class="main-content">
            <h2>Gửi phản hồi về lớp học bạn đã tham gia</h2>

            <c:if test="${not empty message}">
                <div class="message">${message}</div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>

            <form action="feedback" method="post">
                <input type="hidden" name="studentId" value="${sessionScope.loggedStudent.id}" />

                <label for="courseId">Chọn lớp học:</label>
                <select name="courseId" id="courseId" required>
                    <c:forEach var="course" items="${courseList}">
                        <option value="${course.id}">${course.name}</option>
                    </c:forEach>
                </select>

                <label for="comment">Nội dung phản hồi:</label>
                <textarea name="comment" id="comment" rows="5" placeholder="Nhập phản hồi của bạn..." required></textarea>

                <input type="submit" value="Gửi phản hồi">
            </form>
        </div>
    </body>
</html>