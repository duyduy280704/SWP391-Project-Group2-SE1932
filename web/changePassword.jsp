<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Đặt Lại Mật Khẩu | BIGDREAM</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Fonts -->
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Bootstrap + Custom -->
        <link href="css/style.css" rel="stylesheet">

        <!-- Inline Custom CSS for Reset Password -->
        <style>
            .reset-password-container {
                background-color: #f4f4f9;
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 60px 15px;
            }
            .reset-password-form {
                background-color: white;
                padding: 2rem;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                max-width: 450px;
                width: 100%;
            }
            .reset-password-form h2 {
                text-align: center;
                color: #333;
                margin-bottom: 1.5rem;
            }
            .form-group {
                margin-bottom: 1rem;
            }
            label {
                margin-bottom: 0.5rem;
                color: #555;
                display: block;
            }
            input[type="password"] {
                width: 100%;
                padding: 0.75rem;
                border: 1px solid #ddd;
                border-radius: 4px;
                font-size: 1rem;
            }
            input[type="submit"] {
                width: 100%;
                padding: 0.75rem;
                background-color: #007bff;
                color: white;
                border: none;
                font-size: 1rem;
                border-radius: 4px;
                cursor: pointer;
                margin-top: 1rem;
            }
            input[type="submit"]:hover {
                background-color: #0056b3;
            }
            .error-message, .success-message {
                text-align: center;
                margin-top: 1rem;
                font-weight: bold;
            }
            .error-message {
                color: red;
            }
            .success-message {
                color: green;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid d-none d-lg-block">
            <div class="row align-items-center py-4 px-xl-5">
                <div class="col-lg-3">
                    <a href="" class="text-decoration-none">
                        <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                    </a>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-map-marker-alt text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Địa Chỉ</h6>
                            <p>
                                <c:out value="${applicationScope.setting.address}" default="Địa chỉ chưa cập nhật" />
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-envelope text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Email</h6>
                            <p>
                                <c:out value="${setting.email}" default="Email chưa cập nhật" />
                            </p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-phone text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Số Điện Thoại</h6>

                            <p>
                                <c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" />
                            </p>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Topbar End -->

        <!-- Navbar Start -->
        <div class="container-fluid">
            <div class="row border-top px-xl-6">
                <div class="col-lg-9 mx-auto"> <!-- Thêm mx-auto ở đây để container con vào giữa -->
                    <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 px-0">
                        <!-- Logo cho mobile -->
                        <a href="HomePage" class="navbar-brand d-block d-lg-none text-decoration-none">
                            <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                        </a>

                        <!-- Nút toggle cho mobile -->
                        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>

                        <!-- Menu -->
                        <div class="collapse navbar-collapse justify-content-center" id="navbarCollapse">
                            <div class="navbar-nav">
                                <a href="HomePage" class="nav-item nav-link active">Trang Chủ</a>
                                <!-- Thêm các mục menu khác ở đây nếu có -->
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>

        <!-- Form Đặt Lại Mật Khẩu -->
        <section class="reset-password-container">
            <div class="reset-password-form">
                <h2>Đặt Lại Mật Khẩu</h2>
                <form action="ResetPasswordServlet" method="post">
                    <input type="hidden" name="email" value="${email}">
                    <input type="hidden" name="table" value="${table}">

                    <div class="form-group">
                        <label for="newPassword">Mật khẩu mới:</label>
                        <input type="password" id="newPassword" name="newPassword" >
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Nhập lại mật khẩu:</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" >
                    </div>
                    <input type="submit" value="Đổi mật khẩu">
                    
                </form>
                    <p class="error-message text-center mt-3 text-danger">${message}</p>
                
            </div>
        </section>

        <!-- Footer -->
        <footer class="bg-dark text-white pt-5 pb-4">
            <div class="container text-md-left">
                <div class="row text-md-left">

                    <!-- Liên hệ -->
                    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                        <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Liên Hệ</h5>
                        <p><i class="fa fa-map-marker-alt mr-2"></i> 
                            <c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" />
                        </p>
                        <p><i class="fa fa-phone-alt mr-2"></i> 
                            <c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" />
                        </p>
                        <p><i class="fa fa-envelope mr-2"></i> 
                            <c:out value="${setting.email}" default="Email chưa cập nhật" />
                        </p>
                        <div class="mt-3">
                            <a class="btn btn-outline-light btn-sm mr-2" href="${setting.facebookLink != null ? setting.facebookLink : '#'}">
                                <i class="fab fa-facebook-f"></i>
                            </a>
                            <a class="btn btn-outline-light btn-sm mr-2" href="${setting.instagramLink != null ? setting.instagramLink : '#'}">
                                <i class="fab fa-instagram"></i>
                            </a>
                            <a class="btn btn-outline-light btn-sm mr-2" href="${setting.youtubeLink != null ? setting.youtubeLink : '#'}">
                                <i class="fab fa-youtube"></i>
                            </a>
                        </div>
                    </div>

                    <!-- Khoá học -->
                    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                        <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Khoá học</h5>
                        <ul class="list-unstyled">
                            <c:forEach var="t" items="${applicationScope.typeList}">
                                <li>
                                    <a href="#" class="text-white">
                                        <i class="fa fa-angle-right mr-2"></i> ${t.name}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>

                    <!-- Thông tin thêm -->
                    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                        <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Về Chúng Tôi</h5>
                        <p><c:out value="${setting.about}" default="Thông tin chưa cập nhật." /></p>
                    </div>
                </div>

                <hr class="mb-4">

                <!-- Bản quyền -->
                <div class="row align-items-center">
                    <div class="col-md-7 col-lg-8">
                        <p class="text-white">
                            <c:out value="${setting.copyright}" default="© 2025 Trung Tâm Năng Khiếu. All rights reserved." />
                        </p>
                    </div>
                    <div class="col-md-5 col-lg-4">
                        <div class="text-right">
                            <a class="text-white" href="${setting.policyLink != null ? setting.policyLink : '#'}">Chính sách</a> |
                            <a class="text-white" href="${setting.termsLink != null ? setting.termsLink : '#'}">Điều khoản</a>
                        </div>
                    </div>
                </div>
            </div>
        </footer>

        <!-- JS Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>
