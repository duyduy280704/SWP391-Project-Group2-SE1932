<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Điểm danh lớp</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                padding: 20px;
            }
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                border: 1px solid #ccc;
                padding: 8px;
                text-align: center;
            }
            th {
                background: #f0f0f0;
            }
            h2 {
                margin-bottom: 20px;
            }
            .message-success {
                color: green;
                font-weight: bold;
            }
            .message-error {
                color: red;
                font-weight: bold;
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
        </style>
    </head>
    <body>
        <h2>Điểm danh lớp: ${className} - Ngày: ${day}</h2>

        <c:if test="${not empty message}">
            <p class="message-success">${message}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p class="message-error">${error}</p>
        </c:if>

        <c:if test="${empty attendanceList}">
            <p>Không có học sinh nào trong lớp này.</p>
        </c:if>

        <c:if test="${not empty attendanceList}">
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
                                           <c:if test="${sa.status eq 'Có mặt'}">checked</c:if>> Có mặt
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
        </c:if>
    </body>
</html>
