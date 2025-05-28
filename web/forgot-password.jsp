<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quên mật khẩu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }
        h2 {
            color: #333;
            margin-bottom: 20px;
        }
        label {
            display: block;
            text-align: left;
            margin-bottom: 5px;
            color: #555;
        }
        input[type="email"],
        select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        button:hover {
            background-color: #45a049;
        }
        .message {
            margin-top: 15px;
            color: #d32f2f;
        }
        a {
            display: block;
            margin-top: 15px;
            color: #4CAF50;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Quên mật khẩu</h2>
        <form action="ForgotPasswordServlet" method="POST">
            <label for="email">Nhập email của bạn *</label>
            <input type="email" id="email" name="email" placeholder="Nhập email" required>
            
           

            <button type="submit">Gửi mã OTP</button>
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