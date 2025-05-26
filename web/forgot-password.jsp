<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu</title>
   
</head>
<body>
    <div class="container">
        <h2>Quên mật khẩu</h2>
        <form action="ForgotPasswordServlet" method="POST">
            <label for="email">Nhập email của bạn *</label>
            <input type="email" id="email" name="email" placeholder="Nhập email" required>
            <button type="submit">Gửi liên kết đặt lại</button>
        </form>
        <div class="message">
            <% if (request.getAttribute("message") != null) { %>
                <%= request.getAttribute("message") %>
            <% } %>
        </div>
        <a href="login.jsp">Quay lại đăng nhập</a>
    </div>
</body>
</html>