<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập</title>
    </head>
    <body style="font-family: Arial, sans-serif; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; background-color: #f0f0f0;">
        <div style="background-color: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); width: 300px;">
            <h2 style="text-align: center; color: #333;">Đăng nhập</h2>
            <form action="login" method="post">
                <label for="username" style="display: block; margin-bottom: 5px; color: #555;">Tên đăng nhập:</label>
                <input type="text" id="username" name="username" required style="width: 100%; padding: 8px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 4px;">

                <label for="password" style="display: block; margin-bottom: 5px; color: #555;">Mật khẩu:</label>
                <input type="password" id="password" name="password" required style="width: 100%; padding: 8px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 4px;">

                <div style="text-align: right; margin-bottom: 15px;">
                    <a href="forgot-password.jsp" style="color: #4CAF50; text-decoration: none; font-size: 14px;">Quên mật khẩu?</a>
                </div>

                <input type="submit" valuloginControllere="Đăng nhập" style="width: 100%; padding: 10px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;">
            </form>
        </div>
    </body>
</html>