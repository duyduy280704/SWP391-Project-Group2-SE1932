<%-- 
    Document   : schedule_update_batch
    Created on : Jul 9, 2025, 11:22:19 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa thời khóa biểu</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
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
            max-width: 450px;
            transition: transform 0.3s ease;
        }
        .form-container:hover {
            transform: translateY(-5px);
        }
        .form-container h2 {
            font-size: 24px;
            color: #2c3e50;
            text-align: center;
            margin-bottom: 25px;
            font-weight: 600;
        }
        .form-container label {
            font-size: 15px;
            color: #34495e;
            margin-bottom: 8px;
            display: block;
            font-weight: 500;
        }
        .form-container input[type="date"], .form-container input[type="text"], .form-container select, .form-container input[type="time"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            border: 1px solid #dfe6e9;
            border-radius: 8px;
            font-size: 15px;
            box-sizing: border-box;
            background-color: #f9fbfc;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
        }
        .form-container input:focus, .form-container select:focus {
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
    </style>

    <script>
        function loadTeachersByClass(callback) {
            const classId = document.getElementById("id_class").value;
            const teacherSelect = document.getElementById("teacher");
            const selectedTeacherId = document.getElementById("teacher").getAttribute("data-selected");

            if (!classId) {
                teacherSelect.innerHTML = "<option value=''>Chọn giáo viên</option>";
                return;
            }

            fetch("schedule?mode=getTeachersByClass&classId=" + classId)
                .then(res => res.json())
                .then(data => {
                    teacherSelect.innerHTML = "<option value=''>Chọn giáo viên</option>";
                    data.forEach(t => {
                        const option = document.createElement("option");
                        option.value = t.id;
                        option.text = t.name;
                        if (t.id === selectedTeacherId) {
                            option.selected = true;
                        }
                        teacherSelect.appendChild(option);
                    });
                    if (callback) callback();
                })
                .catch(err => console.error("Lỗi khi tải danh sách giáo viên:", err));
        }

        window.onload = function () {
            loadTeachersByClass();
        };
    </script>
</head>
<body>
    <div class="form-container">
        <h2>Sửa thời khóa biểu </h2>
        <form action="listClassSchedule" method="post">
            <input type="hidden" name="action" value="batchUpdate">

            <label for="id_class">Tên lớp:</label>
            <select name="id_class" id="id_class" onchange="loadTeachersByClass()">
                <option value="">Chọn lớp</option>
                <c:forEach items="${data1}" var="c">
                    <option value="${c.id_class}" <c:if test="${id_class eq c.id_class}">selected</c:if>>${c.name_class}</option>
                </c:forEach>
            </select>

            <label for="teacher">Giáo viên:</label>
            <select name="id_teacher" id="teacher" data-selected="${id_teacher}">
                <option value="">Chọn giáo viên</option>
            </select>

            <label for="date">Ngày bắt đầu áp dụng:</label>
            <input type="date" id="date" name="date" value="${date}" />

            <label>Giờ bắt đầu:</label>
            <input type="time" name="startTime" value="${fn:substring(startTime, 0, 5)}" />

            <label>Giờ kết thúc:</label>
            <input type="time" name="endTime" value="${fn:substring(endTime, 0, 5)}" />

            <label>Phòng học:</label>
            <input type="text" name="room" value="${room}" />

            <c:if test="${not empty err}">
                <div style="color: red; font-weight: bold; margin-bottom: 10px;">${err}</div>
            </c:if>

            <c:if test="${empty err && not empty msg}">
                <div style="color: green; font-weight: bold; margin-bottom: 10px;">${msg}</div>
            </c:if>

            <div class="buttons">
                <button type="submit" class="save">Lưu</button>
                <button type="button" class="cancel" onclick="window.location.href = 'listClassSchedule'">Hủy</button>
            </div>
        </form>
    </div>
</body>
</html>