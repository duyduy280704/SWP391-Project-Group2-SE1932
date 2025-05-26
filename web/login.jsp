<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>
  
</head>
<body>
    <div class="container">
        <div class="image-section"></div>
        <div class="login-section">
            <h2>Đăng nhập</h2>
            <form action="LoginController" method="POST">
                <label for="username">Tên Đăng Nhập Hoặc Email *</label>
                <input type="text" id="username" name="username" placeholder="Nhập tên đăng nhập hoặc email" required>
                
                <label for="password">Mật Khẩu *</label>
                <input type="password" id="password" name="password" placeholder="Nhập mật khẩu" required>
                
                <div class="options">
                    <label>
                        <input type="checkbox" name="remember"> Ghi nhớ mật khẩu
                    </label>
                    <a href="forgot-password.jsp">Quên mật khẩu</a>
                </div>
                
                <button type="submit">Đăng nhập</button>
            </form>
            <div class="register">
                Bạn chưa có tài khoản? <a href="#">Đăng ký</a>
            </div>
        </div>
    </div>
   
</body>
</html>