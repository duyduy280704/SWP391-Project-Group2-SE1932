<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Thông Tin Cá Nhân</title>
        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"> 

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">

        <style>
            body {
                font-family: 'Poppins', sans-serif;
                margin: 0;
                padding: 0;
                background: linear-gradient(135deg, #f4f4f9 0%, #e0e7ff 100%);
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            .container {
                display: flex;
                max-width: 1200px;
                margin: 20px auto;
                padding: 0 15px;
                flex: 1;
            }

            .sidebar, .main-content {
                background: #ffffff;
                padding: 25px;
                border-radius: 12px;
                box-shadow: 0 6px 15px rgba(0, 0, 0, 0.1);
                margin-bottom: 20px;
            }

            .sidebar {
                width: 30%;
                margin-right: 20px;
            }

            .main-content {
                width: 70%;
            }

            h2 {
                text-align: center;
                color: #2c3e50;
                cursor: pointer;
                padding: 12px;
                background-color: #ecf0f1;
                border-radius: 8px;
                font-size: 1.6rem;
                margin-bottom: 20px;
                transition: background-color 0.3s ease;
            }

            h2:hover {
                background-color: #dcdde1;
            }

            .content {
                display: none;
                padding: 15px 0;
            }

            .content.active {
                display: block;
            }

            .message {
                text-align: center;
                margin-bottom: 20px;
                padding: 12px;
        border-radius: 6px;
            }

            .message.success {
                color: #28a745;
                background-color: #e9f5e9;
                border: 1px solid #28a745;
            }

            .message.error {
                color: #dc3545;
                background-color: #f8d7da;
                border: 1px solid #dc3545;
            }

            .form-group {
                margin-bottom: 20px;
            }

            label {
                display: block;
                font-weight: 500;
                margin-bottom: 8px;
                color: #34495e;
            }

            input, select {
                width: 100%;
                padding: 12px;
                border: 1px solid #ddd;
                border-radius: 6px;
                font-size: 1rem;
                color: #2c3e50;
                background-color: #fff;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            input[readonly] {
                background-color: #ecf0f1;
                cursor: not-allowed;
            }

            input:focus, select:focus {
                border-color: #3498db;
                outline: none;
                box-shadow: 0 0 8px rgba(52, 152, 219, 0.3);
            }

            button {
                width: 100%;
                padding: 12px;
                background-color: #3498db;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 1.1rem;
                transition: background-color 0.3s ease;
            }

            button:hover {
                background-color: #2980b9;
            }

            a {
                display: block;
                text-align: center;
                margin-top: 15px;
                color: #3498db;
                text-decoration: none;
                font-weight: 500;
                font-size: 1rem;
            }

            a:hover {
                text-decoration: underline;
            }

            .profile-image {
                max-width: 200px;
                max-height: 200px;
                margin-bottom: 15px;
                border-radius: 6px;
                object-fit: cover;
                border: 2px solid #ecf0f1;
            }

            /* Responsive Design */
            @media (max-width: 768px) {
                .container {
                    flex-direction: column;
                }

                .sidebar, .main-content {
                    width: 100%;
                    margin-right: 0;
                    margin-bottom: 20px;
                }
            }

            /* Footer Styles */
            footer {
                background-color: #2c3e50;
                color: #ffffff;
                padding: 40px 0;
                margin-top: auto;
            }

            footer .container {
                max-width: 1200px;
                margin: 0 auto;
                padding: 0 15px;
            }

            footer h5 {
                font-size: 1.2rem;
                margin-bottom: 20px;
                color: #ecf0f1;
            }

            footer p, footer a {
                font-size: 0.95rem;
                color: #bdc3c7;
            }

            footer a:hover {
                color: #ffffff;
                text-decoration: none;
            }

            footer .list-unstyled li {
                margin-bottom: 10px;
            }

            footer hr {
                border-color: #34495e;
                margin: 20px 0;
            }

            footer .text-right a {
                margin-left: 10px;
            }
        </style>
        <script>
            function toggleContent(section) {
                const contents = document.getElementsByClassName('content');
                for (let content of contents) {
                    content.classList.remove('active');
                }
                document.getElementById(section).classList.add('active');
            }
            window.onload = function() {
                document.getElementById('personalInfo').classList.add('active');
            };
        </script>
    </head>
    <body>
        <!-- Topbar Start -->
        <div class="container-fluid d-none d-lg-block">
            <div class="row align-items-center py-4 px-xl-5">
                <div class="col-lg-3">
                    <a href="HomePage" class="text-decoration-none">
                        <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                    </a>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-map-marker-alt text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Địa Chỉ</h6>
                            <p>
                                <c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" />
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
            <div class="row border-top px-xl-5">
                <div class="col-lg-9 mx-auto">
                    <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 px-0">
                        <a href="HomePage" class="navbar-brand d-block d-lg-none text-decoration-none">
                            <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                        </a>
                        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarCollapse">
                            <div class="d-flex justify-content-between align-items-center w-100">
                                <div class="navbar-nav mx-auto">
                                    <!-- Bỏ phần "Trang Chủ" -->
                                </div>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
        <!-- Navbar End -->

        <div class="container">
            <div class="sidebar">
                <h2 onclick="toggleContent('accountInfo')">Thông Tin Tài Khoản</h2>
                <div id="accountInfo" class="content">
                    <c:if test="${not empty message}">
                        <div class="message ${message.contains('thành công') ? 'success' : 'error'}">
                            ${message}
                        </div>
                    </c:if>
                    <c:choose>
                        <c:when test="${not empty profile}">
                            <form action="profile" method="post" enctype="multipart/form-data">
                                <div class="form-group">
                                    <label>Số điện thoại mới:</label>
                                    <input type="email" name="newPhone" value="${profile.phone}" required oninvalid="this.setCustomValidity('Vui lòng điền vào đây')" oninput="this.setCustomValidity('')">
                                </div>
                                <div class="form-group">
                                    <label>Mật Khẩu Cũ:</label>
                                    <input type="password" name="oldPassword" placeholder="Nhập mật khẩu cũ" required oninvalid="this.setCustomValidity('Vui lòng điền vào đây')" oninput="this.setCustomValidity('')">
                                </div>
                                <div class="form-group">
                                    <label>Mật Khẩu Mới:</label>
                                    <input type="password" name="newPassword" id="newPassword" placeholder="Nhập mật khẩu mới" required oninvalid="this.setCustomValidity('Vui lòng điền vào đây')" oninput="this.setCustomValidity('')">
                                </div>
                                <div class="form-group">
                                    <label>Xác Nhận Mật Khẩu Mới:</label>
                                    <input type="password" name="confirmPassword" id="confirmPassword" placeholder="Xác nhận mật khẩu mới" required oninput="checkPasswordMatch()">
                                </div>
                                <p id="passwordMatchError" style="color: red; display: none;">Mật khẩu không khớp!</p>
                                <button type="submit" name="action" value="update">Cập Nhật</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <p>Bạn chưa có thông tin tài khoản. Vui lòng liên hệ admin.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="main-content">
                <h2 onclick="toggleContent('personalInfo')">Thông Tin Cá Nhân</h2>
                <div id="personalInfo" class="content">
                    <c:if test="${not empty message}">
                        <div class="message ${message.contains('thành công') ? 'success' : 'error'}">
                            ${message}
                        </div>
                    </c:if>
                    <c:set var="roleId" value="" />
                    <c:if test="${not empty sessionScope.account}">
                        <c:choose>
                            <c:when test="${sessionScope.account['class'].simpleName == 'AdminStaffs'}">
                                <c:set var="roleId" value="${sessionScope.account.roleId}" />
                            </c:when>
                            <c:when test="${sessionScope.account['class'].simpleName == 'Students'}">
                                <c:set var="roleId" value="${sessionScope.account.role}" />
                            </c:when>
                            <c:when test="${sessionScope.account['class'].simpleName == 'Teachers'}">
                                <c:set var="roleId" value="${sessionScope.account.role}" />
                            </c:when>
                        </c:choose>
                    </c:if>
                    <c:choose>
                        <c:when test="${not empty profile}">
                            <form action="profile" method="post" enctype="multipart/form-data">
                                 <div class="form-group">
                                    <label>Số điện thoại:</label>
                                    <input type="text" name="phone" value="${profile.phone}" readonly>
                                </div>
                                <div class="form-group">
                                    <label>Tài khoản (Email):</label>
                                    <input type="text" name="email" value="${profile.email}" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                                </div>
                                <div class="form-group">
                                    <label>Họ và Tên:</label>
                                    <input type="text" name="fullName" value="${profile.name}" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                                </div>
                                
                                <div class="form-group">
                                    <label>Giới Tính:</label>
                                    <select name="gender" required oninvalid="this.setCustomValidity('Vui lòng chọn vào đây')" oninput="this.setCustomValidity('')">
                                        <option value="Nam" ${profile.gender == 'Nam' ? 'selected' : ''}>Nam</option>
                                        <option value="Nữ" ${profile.gender == 'Nữ' ? 'selected' : ''}>Nữ</option>
                                        <option value="Khác" ${profile.gender == 'Khác' ? 'selected' : ''}>Khác</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label>Ngày Sinh:</label>
                                    <input type="date" name="birthDate" value="${profile.birthdate}" required oninvalid="this.setCustomValidity('Vui lòng điền vào đây')" oninput="this.setCustomValidity('')">
                                </div>
                                
                                <c:if test="${roleId == '1'}">
                                    <div class="form-group">
                                        <label>Địa Chỉ:</label>
                                        <input type="text" name="address" value="${profile.address}" required oninvalid="this.setCustomValidity('Vui lòng điền vào đây')" oninput="this.setCustomValidity('')">
                                    </div>
                                </c:if>
                                <c:if test="${roleId == '2'}">
                                    <div class="form-group">
                                        <label>Chuyên Môn:</label>
                                        <input type="text" name="expertise" value="${profile.exp}" required oninvalid="this.setCustomValidity('Vui lòng điền vào đây')" oninput="this.setCustomValidity('')">
                                    </div>
                                    <div class="form-group">
                                        <label>Kinh Nghiệm (Năm):</label>
                                        <input type="number" name="yearsOfExperience" value="${profile.yearofcourse}" required oninvalid="this.setCustomValidity('Vui lòng điền vào đây')" oninput="this.setCustomValidity('')">
                                    </div>
                                    <div class="form-group">
                                        <label>Ảnh Đại Diện:</label>
                                        <c:if test="${not empty profile.pic}">
                                            <img src="${pageContext.request.contextPath}/uploads/${profile.pic}" alt="Profile Picture" class="profile-image">
                                        </c:if>
                                        <input type="file" name="picture" accept="image/*">
                                    </div>
                                </c:if>
                                <button type="submit" name="action" value="update">Cập Nhật</button>
                            </form>
                            <c:if test="${not empty profile.name or not empty profile.gender or not empty profile.birthdate or 
                                          (roleId == '1' and not empty profile.address) or 
                                          (roleId == '2' and (not empty profile.exp or not empty profile.pic))}">
                                <a href="profile?action=view">Quay Lại Xem Thông Tin</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <p>Bạn chưa có thông tin cá nhân. Vui lòng liên hệ admin để thêm thông tin.</p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <!-- Footer Start -->
        <footer class="bg-dark text-white pt-5 pb-4">
            <div class="container text-center text-md-left">
                <div class="row">
                    <!-- Liên hệ -->
                    <div class="col-md-4 mb-4 mb-md-0">
                        <h5 class="text-uppercase font-weight-bold text-primary">Liên Hệ</h5>
                        <p><i class="fa fa-map-marker-alt mr-2"></i> <c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" /></p>
                        <p><i class="fa fa-phone-alt mr-2"></i> <c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" /></p>
                        <p><i class="fa fa-envelope mr-2"></i> <c:out value="${setting.email}" default="Email chưa cập nhật" /></p>
                        <div class="mt-3">
                            <a href="${setting.facebookLink != null ? setting.facebookLink : '#'}" class="btn btn-outline-light btn-sm mr-2"><i class="fab fa-facebook-f"></i></a>
                            <a href="${setting.instagramLink != null ? setting.instagramLink : '#'}" class="btn btn-outline-light btn-sm mr-2"><i class="fab fa-instagram"></i></a>
                            <a href="${setting.youtubeLink != null ? setting.youtubeLink : '#'}" class="btn btn-outline-light btn-sm"><i class="fab fa-youtube"></i></a>
                        </div>
                    </div>
                    <!-- Khoá học -->
                    <div class="col-md-4 mb-4 mb-md-0">
                        <h5 class="text-uppercase font-weight-bold text-primary">Khoá học</h5>
                        <ul class="list-unstyled">
                            <c:forEach var="t" items="${applicationScope.typeList}">
                                <li><a href="#" class="text-white"><i class="fa fa-angle-right mr-2"></i> ${t.name}</a></li>
                            </c:forEach>
                        </ul>
                    </div>
                    <!-- Thông tin thêm -->
                    <div class="col-md-4">
                        <h5 class="text-uppercase font-weight-bold text-primary">Về Chúng Tôi</h5>
                        <p><c:out value="${setting.about}" default="Thông tin chưa cập nhật." /></p>
                    </div>
                </div>
                <hr class="bg-light my-4">
                <div class="row align-items-center">
                    <div class="col-md-6">
                        <p class="mb-0"><c:out value="${setting.copyright}" default="© 2025 Trung Tâm Năng Khiếu. All rights reserved." /></p>
                    </div>
                    <div class="col-md-6 text-md-right">
                        <a href="${setting.policyLink != null ? setting.policyLink : '#'}" class="text-white">Chính sách</a> |
                        <a href="${setting.termsLink != null ? setting.termsLink : '#'}" class="text-white">Điều khoản</a>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Footer End -->

        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="fa fa-angle-double-up"></i></a>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>
        <script src="js/main.js"></script>

        <script>
            function checkPasswordMatch() {
                const newPassword = document.getElementById('newPassword').value;
                const confirmPassword = document.getElementById('confirmPassword').value;
                const errorMsg = document.getElementById('passwordMatchError');
                if (newPassword && confirmPassword && newPassword !== confirmPassword) {
                    errorMsg.style.display = 'block';
                } else {
                    errorMsg.style.display = 'none';
                }
            }
        </script>
    </body>
</html>