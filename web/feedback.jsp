<%-- 
    Document   : feedback
    Created on : Jun 17, 2025, 12:34:47 AM
    Author     : maith
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 40px;
        }
        form {
            max-width: 500px;
        }
        label {
            font-weight: bold;
        }
        textarea, select, input[type="submit"] {
            width: 100%;
            padding: 8px;
            margin-top: 8px;
            margin-bottom: 16px;
        }
    </style>
</head>
<body>
    <h2>Gửi phản hồi về khóa học đã học</h2>

    <form action="FeedbackController" method="post">
        <input type="hidden" name="studentId" value="${sessionScope.loggedStudent.id}" />

        <label for="courseId">Chọn khóa học:</label>
        <select name="courseId" id="courseId" required>
            <c:forEach var="course" items="${courseList}">
                <option value="${course.id}">${course.name}</option>
            </c:forEach>
        </select>

        <label for="satisfaction">Mức độ hài lòng:</label>
        <select name="satisfaction" id="satisfaction" required>
            <option value="5">Rất hài lòng</option>
            <option value="4">Hài lòng</option>
            <option value="3">Bình thường</option>
            <option value="2">Không hài lòng</option>
            <option value="1">Rất không hài lòng</option>
        </select>

        <label for="comment">Phản hồi chi tiết:</label>
        <textarea name="comment" id="comment" rows="5" placeholder="Viết phản hồi của bạn..." required></textarea>

        <input type="submit" value="Gửi phản hồi">
    </form>
</body>
</html>
