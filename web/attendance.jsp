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

            .info-block {
                display: flex;
                align-items: center;
                gap: 10px;
                font-size: 13px;
                color: #333;
            }

            .info-block i {
                font-size: 16px;
                color: #FF6600;
            }

            .info-block h6 {
                font-size: 13px;
                font-weight: 600;
                margin: 0;
            }

            .info-block p {
                font-size: 12px;
                margin: 0;
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
            <a href="profile?action=view" class="nav-item nav-link">Hồ sơ</a>
        </div>

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
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <input type="submit" class="submit-btn" value="Lưu điểm danh" />
            </form>
        </div>
    </body>
</html>
