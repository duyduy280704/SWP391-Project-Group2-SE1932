<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Sửa Thời Khóa Biểu</title>
        <style>
            body {
                font-family: 'Segoe UI', Arial, sans-serif;
                background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%); /* Gradient background */
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
                box-shadow: 0 8px 16px rgba(0, 0, 0, 0.15); /* Mềm mại hơn với bóng đổ */
                width: 100%;
                max-width: 450px; /* Tăng nhẹ chiều rộng */
                transition: transform 0.3s ease; /* Hiệu ứng khi hover */
            }
            .form-container:hover {
                transform: translateY(-5px); /* Nhấc nhẹ form khi hover */
            }
            .form-container h2 {
                font-size: 24px; /* Tăng cỡ chữ tiêu đề */
                color: #2c3e50; /* Màu tối hơn, chuyên nghiệp */
                text-align: center;
                margin-bottom: 25px;
                font-weight: 600;
            }
            .form-container label {
                font-size: 15px;
                color: #34495e; /* Màu nhạt hơn cho label */
                margin-bottom: 8px;
                display: block;
                font-weight: 500;
            }
            .form-container input[type="date"],
            .form-container input[type="text"],
            .form-container select {
                width: 100%;
                padding: 12px; /* Tăng padding cho thoải mái */
                margin-bottom: 20px;
                border: 1px solid #dfe6e9; /* Viền nhạt hơn */
                border-radius: 8px; /* Bo tròn hơn */
                font-size: 15px;
                box-sizing: border-box;
                background-color: #f9fbfc; /* Màu nền nhạt */
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .form-container input[type="date"]:focus,
            .form-container input[type="text"]:focus,
            .form-container select:focus {
                border-color: #4a00e0; /* Viền đổi màu khi focus */
                box-shadow: 0 0 8px rgba(74, 0, 224, 0.2); /* Hiệu ứng sáng nhẹ khi focus */
                outline: none;
            }
            .form-container input[type="radio"] {
                margin-right: 5px;
            }
            .form-container .radio-group {
                display: flex;
                gap: 20px;
                margin-bottom: 20px;
            }
            .form-container .buttons {
                display: flex;
                gap: 15px; /* Tăng khoảng cách giữa các nút */
            }
            .form-container button {
                padding: 12px;
                border: none;
                border-radius: 8px;
                font-size: 16px; /* Tăng cỡ chữ nút */
                cursor: pointer;
                width: 100%;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }
            .form-container button.save {
                background: linear-gradient(90deg, #4a00e0, #8e2de2); /* Gradient cho nút Lưu */
                color: #fff;
                font-weight: 500;
            }
            .form-container button.save:hover {
                background: linear-gradient(90deg, #3a00b3, #6d1ab3);
                transform: translateY(-2px); /* Nhấc nhẹ khi hover */
            }
            .form-container button.cancel {
                background-color: #ecf0f1; /* Màu nền nhạt hơn */
                color: #636e72;
                font-weight: 500;
            }
            .form-container button.cancel:hover {
                background-color: #d5dbdb;
                transform: translateY(-2px);
            }
        </style>
    </head>
    <body>
        <div class="form-container">
            <h2>Sửa thời khóa biểu</h2>

            <form action="schedule" method="post">

                <input type="hidden" name="scheduleId" value="${s.getId()}">


                <label for="className">Tên lớp:</label>               
                <select name="classname">
                    <option value="0">Chọn lớp</option>
                    <c:forEach items="${data1}" var="c">
                        <option value="${c.getId_class()}" <c:if test="${s.getNameClass() eq c.getId_class()}">selected</c:if>> 
                            ${c.name_class}
                        </option>
                    </c:forEach>
                </select>

                <label>Giáo viên:</label>
                <select name="teacher">
                    <option value="0">Chọn giáo viên</option>
                    <c:forEach items="${data3}" var="c">
                        <option value="${c.getIDTeacher()}" <c:if test="${s.teacher eq c.getIDTeacher()}">selected</c:if> >
                            ${c.nameTeacher}
                        </option>
                    </c:forEach>
                </select>

                <label for="date">Ngày học:</label>
                <input type="date" id="date" name="date" value="${s.getDay()}" >

                <label>Giờ bắt đầu:</label>
                <input type="time" name="startTime"  value="${s.getStartTime()}">

                <label>Giờ kết thúc:</label>
                <input type="time" name="endTime" value="${s.getEndTime()}">

                <label>Phòng học:</label>
                <input type="text" name="room"  placeholder="${s.getRoom()}">


                <c:if test="${not empty err}">
                    <div style="color: red; font-weight: bold; margin-bottom: 10px;">
                        ${err}
                    </div>
                </c:if>

                <c:if test="${not empty msg}">
                    <div style="color: green; font-weight: bold; margin-bottom: 10px;">
                        ${msg}
                    </div>
                </c:if>

                <div class="buttons">
                    <button type="submit" class="save" name="update"  >Lưu</button>
                    <button type="button" class="cancel" onclick="window.location.href = 'schedule'">Hủy</button>
                </div>
            </form>
        </div>
    </body>
</html>