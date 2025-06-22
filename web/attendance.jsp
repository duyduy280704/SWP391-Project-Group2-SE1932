<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Điểm danh lớp</title>

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;600&display=swap" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

        <style>
            body {
                font-family: 'Poppins', sans-serif;
                margin: 0;
                padding: 0;
                background: #f4f7fb;
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
                z-index: 1000;
            }
            .sidebar a {
                display: block;
                color: #000;
                padding: 15px 20px;
                text-decoration: none;
            }
            .sidebar a:hover, .sidebar a.active {
                background-color: #FF6600;
                color: white;
            }
            .main-content {
                margin-left: 220px;
                padding: 30px;
            }
            .topbar {
                background: #fff;
                border-bottom: 1px solid #ddd;
                padding: 10px 30px;
                display: flex;
                justify-content: flex-end;
            }
            .topbar .info-block {
                margin-left: 30px;
            }
            .topbar i {
                color: #007bff;
                margin-right: 8px;
            }
            .submit-btn {
                padding: 10px 20px;
                background: #007bff;
                color: white;
                border: none;
                cursor: pointer;
            }
            .submit-btn:hover {
                background: #0056b3;
            }
            table {
                border-collapse: collapse;
                width: 100%;
                margin-top: 20px;
                background: white;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 4px 8px rgba(0,0,0,0.05);
            }
            th, td {
                padding: 12px;
                text-align: center;
                border-bottom: 1px solid #eee;
            }
            th {
                background-color: #3498db;
                color: white;
                text-transform: uppercase;
            }
            h2 {
                margin-top: 20px;
                font-size: 24px;
                font-weight: 600;
            }
            .message-success {
                color: green;
                font-weight: bold;
            }
            .message-error {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>

        <div class="sidebar">
            <div class="text-center mb-4">
                <h3><span style="color:#FF6600;">BIG</span>DREAM</h3>
            </div>
            <a href="HomePage">Trang Chủ</a>
            <a href="course.jsp">Danh sách các lớp</a>
            <a href="scheduleTeacher">Lịch dạy</a>
            <a href="blog.jsp">Điểm danh</a>
            <a href="profile?action=view">Hồ Sơ</a>
        </div>

        <div class="topbar">
            <div class="info-block">
                <i class="fa fa-map-marker-alt"></i>
                <span><c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" /></span>
            </div>
            <div class="info-block">
                <i class="fa fa-envelope"></i>
                <span><c:out value="${setting.email}" default="Email chưa cập nhật" /></span>
            </div>
            <div class="info-block">
                <i class="fa fa-phone"></i>
                <span><c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" /></span>
            </div>
        </div>


        <div class="main-content">
            <h2>Điểm danh lớp: ${className} - Ngày: ${day}</h2>

            <c:if test="${not empty message}">
                <p class="message-success">${message}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p class="message-error">${error}</p>
            </c:if>

            <c:choose>

                <c:when test="${not empty attendanceList}">
                    <form action="scheduleTeacher" method="post">
                        <input type="hidden" name="action" value="submitAttendance" />
                        <input type="hidden" name="scheduleId" value="${scheduleId}" />
                        <input type="hidden" name="day" value="${day}" />
                        <input type="hidden" name="classId" value="${classId}" />
                        <input type="hidden" name="className" value="${className}" />

                        <table>
                            <tr>
                                <th>STT</th>
                                <th>Tên học sinh</th>
                                <th>Trạng thái điểm danh</th>
                            </tr>
                            <c:forEach var="sa" items="${attendanceList}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>
                                        ${sa.student.name}
                                        <input type="hidden" name="studentId[]" value="${sa.student.id}" />
                                    </td>
                                    <td>
                                        <label>
                                            <input type="radio" name="attendance_${sa.student.id}" value="Có mặt"
                                                   <c:if test="${sa.status eq 'Có mặt'}">checked</c:if> required> Có mặt
                                            </label>
                                            <label>
                                                <input type="radio" name="attendance_${sa.student.id}" value="Vắng mặt"
                                                   <c:if test="${sa.status eq 'Vắng mặt'}">checked</c:if>> Vắng mặt
                                            </label>
                                        </td>
                                    </tr>
                            </c:forEach>
                        </table>

                        <br>
                        <input type="submit" value="Lưu điểm danh" class="submit-btn" />
                    </form>
                </c:when>


                <c:when test="${not empty students}">
                    <form action="scheduleTeacher" method="post">
                        <input type="hidden" name="action" value="submitAttendance" />
                        <input type="hidden" name="scheduleId" value="${scheduleId}" />
                        <input type="hidden" name="day" value="${day}" />
                        <input type="hidden" name="classId" value="${classId}" />
                        <input type="hidden" name="className" value="${className}" />

                        <table>
                            <tr>
                                <th>STT</th>
                                <th>Tên học sinh</th>
                                <th>Trạng thái điểm danh</th>
                            </tr>
                            <c:forEach var="s" items="${students}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>
                                        ${s.name}
                                        <input type="hidden" name="studentId[]" value="${s.id}" />
                                    </td>
                                    <td>
                                        <label>
                                            <input type="radio" name="attendance_${s.id}" value="Có mặt" required> Có mặt
                                        </label>
                                        <label>
                                            <input type="radio" name="attendance_${s.id}" value="Vắng mặt"> Vắng mặt
                                        </label>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>

                        <br>
                        <input type="submit" value="Lưu điểm danh" class="submit-btn" />
                    </form>
                </c:when>


                <c:otherwise>
                    <p>Không có học sinh nào trong lớp này.</p>
                </c:otherwise>
            </c:choose>
        </div>

    </body>
</html>