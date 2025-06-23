<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Điểm danh lớp</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css">
        <link href="https://fonts.googleapis.com/css2?family=Poppins&display=swap" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">

        <style>
            body {
                font-family: 'Poppins', sans-serif;
                background: #f4f7fb;
                margin: 0;
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
                color: #2c3e50;
                margin-bottom: 20px;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                background: white;
                margin-top: 20px;
                box-shadow: 0 3px 6px rgba(0,0,0,0.05);
            }

            th, td {
                padding: 12px;
                border: 1px solid #ddd;
                text-align: center;
            }

            th {
                background: #3498db;
                color: white;
            }

            input[type=text] {
                width: 90%;
                border-radius: 4px;
                border: 1px solid #ccc;
                padding: 5px;
            }

            .submit-btn {
                margin-top: 20px;
                padding: 10px 20px;
                background: #3498db;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            .submit-btn:hover {
                background-color: #2980b9;
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
            <a href="profile?action=view" class="nav-item nav-link">Hồ sơ</a>
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
            <h2>Điểm danh lớp: ${className} - Ngày: ${day}</h2>

            <c:if test="${not empty message}">
                <p style="color:green">${message}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p style="color:red">${error}</p>
            </c:if>

            <form action="scheduleTeacher" method="post">
                <input type="hidden" name="action" value="submitAttendance" />
                <input type="hidden" name="scheduleId" value="${scheduleId}" />
                <input type="hidden" name="day" value="${day}" />
                <input type="hidden" name="classId" value="${classId}" />
                <input type="hidden" name="className" value="${className}" />

                <table>
                    <thead>
                        <tr>
                            <th>STT</th>
                            <th>Tên học sinh</th>
                            <th>Trạng thái điểm danh</th>
                            <th>Lý do nghỉ</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${students}" varStatus="loop">
                            <tr>
                                <td>${loop.index + 1}</td>
                                <td>
                                    ${s.name}
                                    <input type="hidden" name="studentId[]" value="${s.id}" />
                                </td>
                                <td>
                                    <c:set var="status" value="" />
                                    <c:forEach var="a" items="${attendanceList}">
                                        <c:if test="${a.student.id == s.id}">
                                            <c:set var="status" value="${a.status}" />
                                        </c:if>
                                    </c:forEach>
                                    <label>
                                        <input type="radio" name="attendance_${s.id}" value="Có mặt"
                                               <c:if test="${status == 'Có mặt'}">checked</c:if>> Có mặt
                                        </label>
                                        <label>
                                            <input type="radio" name="attendance_${s.id}" value="Vắng mặt"
                                               <c:if test="${status == 'Vắng mặt'}">checked</c:if>> Vắng mặt
                                        </label>
                                    </td>
                                    <td>
                                    <c:set var="reason" value="" />
                                    <c:forEach var="a" items="${attendanceList}">
                                        <c:if test="${a.student.id == s.id}">
                                            <c:set var="reason" value="${a.reason}" />
                                        </c:if>
                                    </c:forEach>
                                    <input type="text" name="reason_${s.id}" placeholder="Lý do nghỉ" value="${reason}" />
                            </tr>
                            </td>
                        </c:forEach>
                    </tbody>
                </table>

                <input type="submit" class="submit-btn" value="Lưu điểm danh" />
            </form>
        </div>

    </body>
</html>