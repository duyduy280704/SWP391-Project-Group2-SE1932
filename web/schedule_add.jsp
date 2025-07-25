<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
    String role = (String) session.getAttribute("role");
    if (role == null || !"staff".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm Thời Khóa Biểu</title>
        <style>
            body {
                font-family: 'Segoe UI', Arial, sans-serif;
                background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%);
                display: flex;
                justify-content: center;
                align-items: center;
                min-height: 100vh;
                margin: 0;
                padding: 20px;
            }
            .form-container {
                background-color: #ffffff;
                padding: 30px;
                border-radius: 15px;
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15);
                width: 100%;
                max-width: 500px;
                transition: transform 0.3s ease;
            }
            .form-container:hover {
                transform: translateY(-5px);
            }
            .form-container h2 {
                font-size: 24px;
                color: #2c3e50;
                text-align: center;
                margin-bottom: 20px;
                font-weight: 600;
            }
            .form-container label {
                font-size: 15px;
                color: #34495e;
                margin-bottom: 8px;
                display: block;
                font-weight: 500;
            }
            .form-container input[type="date"],
            .form-container input[type="text"],
            .form-container input[type="time"],
            .form-container select {
                width: 100%;
                padding: 12px;
                margin-bottom: 20px;
                border: 1px solid #dfe6e9;
                border-radius: 8px;
                font-size: 15px;
                background-color: #f9fbfc;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .form-container input:focus,
            .form-container select:focus {
                border-color: #4a00e0;
                box-shadow: 0 0 8px rgba(74, 0, 224, 0.2);
                outline: none;
            }
            .form-container .buttons {
                display: flex;
                gap: 15px;
            }
            .form-container button {
                padding: 12px;
                border: none;
                border-radius: 8px;
                font-size: 16px;
                cursor: pointer;
                width: 100%;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }
            .form-container button.save {
                background: linear-gradient(90deg, #4a00e0, #8e2de2);
                color: #fff;
                font-weight: 500;
            }
            .form-container button.save:hover {
                background: linear-gradient(90deg, #3a00b3, #6d1ab3);
                transform: translateY(-2px);
            }
            .form-container button.cancel {
                background-color: #ecf0f1;
                color: #636e72;
                font-weight: 500;
            }
            .form-container button.cancel:hover {
                background-color: #d5dbdb;
                transform: translateY(-2px);
            }
            .alert {
                padding: 12px;
                border-radius: 8px;
                margin-bottom: 15px;
            }
            .alert-error {
                background-color: #fce4e4;
                color: #c0392b;
                border: 1px solid #e74c3c;
            }
            .alert-success {
                background-color: #e8f6ec;
                color: #27ae60;
                border: 1px solid #2ecc71;
            }
        </style>

        <script>
            function loadTeachersByClass(callback) {
                const classId = document.getElementById("classname").value;
                const teacherSelect = document.getElementById("teacher");

                if (classId === "0") {
                    teacherSelect.innerHTML = "<option value='0'>Chọn giáo viên</option>";
                    return;
                }

                fetch("schedule?mode=getTeachersByClass&classId=" + classId)
                        .then(res => res.json())
                        .then(data => {
                            teacherSelect.innerHTML = "<option value='0'>Chọn giáo viên</option>";
                            data.forEach(t => {
                                const option = document.createElement("option");
                                option.value = t.id;
                                option.text = t.name;
                                teacherSelect.appendChild(option);
                            });
                            if (callback)
                                callback();
                        })
                        .catch(err => console.error("Lỗi khi lấy danh sách giáo viên:", err));
            }

            window.onload = function () {
                const selectedClass = "${param.id_class}";
                const selectedTeacher = "${param.id_teacher}";
                if (selectedClass && selectedClass !== "0") {
                    document.getElementById("classname").value = selectedClass;
                    loadTeachersByClass(() => {
                        document.getElementById("teacher").value = selectedTeacher;
                    });
                }
            };
        </script>
    </head>
        <body>
            <div class="form-container">
                <h2>Tạo thời khóa biểu</h2>


                <c:choose>
                    <c:when test="${not empty err}">
                        <div class="alert alert-error">${err}</div>
                    </c:when>
                    <c:when test="${not empty msg}">
                        <div class="alert alert-success">${msg}</div>
                    </c:when>
                    <c:when test="${not empty sessionScope.msg}">
                        <div class="alert alert-success">${sessionScope.msg}</div>
                        <c:remove var="msg" scope="session" />
                    </c:when>
                </c:choose>



                <form action="schedule" method="post">
                    <label for="classname">Tên lớp:</label>
                    <select name="id_class" id="classname" onchange="loadTeachersByClass()">
                        <option value="0">Chọn lớp</option>
                        <c:forEach items="${data1}" var="c">
                            <option value="${c.id_class}" <c:if test="${param.id_class == c.id_class}">selected</c:if>>
                                ${c.name_class}
                            </option>
                        </c:forEach>
                    </select>

                    <label for="teacher">Giáo viên:</label>
                    <select name="id_teacher" id="teacher">
                        <option value="0">Chọn giáo viên</option>
                    </select>

                    <label for="date">Ngày học:</label>
                    <input type="date" id="date" name="date" value="${param.date}">
                    <c:set var="selectedDays" value="${param.days}" />

                    <label><input type="checkbox" name="days" value="1" <c:if test="${fn:contains(paramValues.days, '1')}">checked</c:if>> Thứ 2</label>
                    <label><input type="checkbox" name="days" value="2" <c:if test="${fn:contains(paramValues.days, '2')}">checked</c:if>> Thứ 3</label>
                    <label><input type="checkbox" name="days" value="3" <c:if test="${fn:contains(paramValues.days, '3')}">checked</c:if>> Thứ 4</label>
                    <label><input type="checkbox" name="days" value="4" <c:if test="${fn:contains(paramValues.days, '4')}">checked</c:if>> Thứ 5</label>
                    <label><input type="checkbox" name="days" value="5" <c:if test="${fn:contains(paramValues.days, '5')}">checked</c:if>> Thứ 6</label>
                    <label><input type="checkbox" name="days" value="6" <c:if test="${fn:contains(paramValues.days, '6')}">checked</c:if>> Thứ 7</label>
                    <label><input type="checkbox" name="days" value="7" <c:if test="${fn:contains(paramValues.days, '7')}">checked</c:if>> Chủ nhật</label>




                        <label for="startTime">Giờ bắt đầu:</label>
                        <input type="time" id="startTime" name="startTime" value="${param.startTime}">

                    <label for="endTime">Giờ kết thúc:</label>
                    <input type="time" id="endTime" name="endTime" value="${param.endTime}">

                    <label for="room">Phòng học:</label>
                    <input type="text" id="room" name="room" value="${param.room}">

                    <div class="buttons">
                        <button type="submit" class="save" name="add">Lưu</button>
                        <button type="button" class="cancel" onclick="window.location.href = 'listClassSchedule'">Hủy</button>
                    </div>
                </form>
            </div>
        </body>
</html>