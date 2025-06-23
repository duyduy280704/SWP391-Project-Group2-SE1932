<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - BIGDREAM</title>
    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

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
        /* Login Container Styling */
        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, #f4f4f9 0%, #e0e7ff 100%);
            margin: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .login-container {
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 2rem;
        }

        .container-login-form {
            background-color: #ffffff;
            padding: 2.5rem;
            border-radius: 12px;
            box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
            width: 100%;
            max-width: 450px;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .container-login-form:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 32px rgba(0, 0, 0, 0.2);
        }

        h2 {
            text-align: center;
            color: #1a1a1a;
            margin-bottom: 2rem;
            font-size: 1.8rem;
            font-weight: 600;
            letter-spacing: 0.5px;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #333;
            font-size: 0.95rem;
            font-weight: 500;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 0.9rem;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-size: 1rem;
            color: #333;
            background-color: #f9f9f9;
            transition: border-color 0.3s ease, box-shadow 0.3s ease;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="password"]:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 8px rgba(0, 123, 255, 0.2);
            background-color: #ffffff;
        }

        .forgot-password {
            text-align: right;
            margin-bottom: 1.5rem;
        }

        .forgot-password a {
            color: #007bff;
            text-decoration: none;
            font-size: 0.9rem;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .forgot-password a:hover {
            color: #0056b3;
            text-decoration: underline;
        }

        input[type="submit"] {
            width: 100%;
            padding: 0.9rem;
            background: linear-gradient(90deg, #007bff, #00aaff);
            border: none;
            border-radius: 6px;
            color: #ffffff;
            font-size: 1.1rem;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        input[type="submit"]:hover {
            background: linear-gradient(90deg, #0056b3, #007bff);
            transform: translateY(-2px);
        }

        input[type="submit"]:active {
            transform: translateY(0);
        }

        .error-message {
            color: #dc3545;
            text-align: center;
            margin-top: 1.5rem;
            font-size: 0.9rem;
            font-weight: 500;
        }

        .success-message {
            color: #28a745;
            text-align: center;
            margin-top: 1.5rem;
            font-size: 0.9rem;
            font-weight: 500;
        }

        /* Responsive Design */
        @media (max-width: 576px) {
            .container-login-form {
                padding: 1.5rem;
                max-width: 90%;
            }

            h2 {
                font-size: 1.5rem;
            }

            input[type="text"],
            input[type="password"],
            input[type="submit"] {
                font-size: 0.95rem;
                padding: 0.8rem;
            }
        }

        /* Styles from registerteacher.jsp */
        .form-container {
            max-width: 42rem;
            margin-left: auto;
            margin-right: auto;
            background-color: #ffffff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 0.5rem;
            padding: 1.5rem;
            margin-top: 2.5rem;
        }

        .input-field {
            border-color: #d1d5db;
            padding: 0.75rem;
            width: 100%;
            border-radius: 0.375rem;
            transition: border-color 0.2s ease, box-shadow 0.2s ease;
        }

        .input-field:focus {
            border-color: #f97316;
            box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.5);
            outline: none;
        }

        .label {
            display: block;
            font-size: 0.875rem;
            font-weight: 500;
            color: #374151;
            margin-bottom: 0.25rem;
        }

        .error-message {
            color: #ef4444;
            font-size: 0.875rem;
            margin-top: 0.25rem;
        }

        .submit-btn {
            background-color: #f97316;
            color: #ffffff;
            padding-left: 1.5rem;
            padding-right: 1.5rem;
            padding-top: 0.5rem;
            padding-bottom: 0.5rem;
            border-radius: 0.375rem;
            transition: background-color 0.2s ease;
            font-weight: 600;
        }

        .submit-btn:hover {
            background-color: #ea580c;
        }

        .terms-link {
            color: #f97316;
            font-weight: 600;
            text-decoration: underline;
            transition: color 0.2s ease;
        }

        .terms-link:hover {
            color: #ea580c;
        }

        .modal {
            position: fixed;
            inset: 0;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 50;
            display: none;
        }

        .modal-content {
            background-color: #ffffff;
            padding: 1.5rem;
            border-radius: 0.5rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            max-width: 28rem;
            width: 100%;
            max-height: 80vh;
            overflow-y: auto;
        }

        .close-btn {
            background-color: #f97316;
            color: #ffffff;
            padding-left: 1rem;
            padding-right: 1rem;
            padding-top: 0.5rem;
            padding-bottom: 0.5rem;
            border-radius: 0.375rem;
            transition: background-color 0.2s ease;
            margin-top: 1rem;
        }

        .close-btn:hover {
            background-color: #ea580c;
        }

        .date-input {
            border-color: #d1d5db;
            padding: 0.75rem;
            width: 100%;
            border-radius: 0.375rem;
            transition: border-color 0.2s ease, box-shadow 0.2s ease;
        }

        .date-input:focus {
            border-color: #f97316;
            box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.5);
            outline: none;
        }

        input[type="date"]::-webkit-calendar-picker-indicator {
            cursor: pointer;
        }
    </style>
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
                                <a href="HomePage" class="nav-item nav-link">Trang Chủ</a>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    </div>
    <!-- Navbar End -->

    <!-- Login Form Start -->
    <div class="login-container">
        <div class="form-container">
            <h2>Đăng ký làm giáo viên</h2>
            <c:if test="${not empty message}">
                <p class="error-message">${message}</p>
            </c:if>
            <form action="resgiterTeacher" method="post" enctype="multipart/form-data" class="space-y-6">
                <div>
                    <label class="label">Họ và tên:</label>
                    <input type="text" name="fullName" value='${fullName}' class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Email:</label>
                    <input type="email" name="email" value="${email}" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Số điện thoại:</label>
                    <input type="text" name="phone" value="${phone}" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Link CV:</label>
                    <input type="text" name="cvLink" value="${cvLink}" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Ngày sinh:</label>
                    <input type="date" name="birthDate" value="${birthDate}" class="date-input" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Giới tính:</label> 
                    <select name="gender" value="${gender}" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng chọn vào đây')" oninput="this.setCustomValidity('')">
                        <option value="Nam">Nam</option>
                        <option value="Nữ">Nữ</option>
                        <option value="Khác">Khác</option>
                    </select>
                </div>
                <div>
                    <label class="label">Chuyên môn:</label>
                    <input type="text" name="expertise" value="${expertise}" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Loại khóa học:</label>
                    <select name="idTypeCourse" value="${idTypeCourse}" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng chọn vào đây')" oninput="this.setCustomValidity('')">
                        <c:forEach var="o" items="${courseTypes}">
                            <option value="${o.getId()}">${o.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label class="label">Số năm kinh nghiệm:</label>
                    <input type="number" name="yearsOfExperience" class="input-field" 
                           min="0" 
                           step="1" 
                           required 
                           oninvalid="this.setCustomValidity('Vui lòng nhập số năm kinh nghiệm')" 
                           oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">
                        <input type="checkbox" name="agreeTerms" class="mr-2" required oninvalid="this.setCustomValidity('Vui lòng chọn vào đây')" oninput="this.setCustomValidity('')">
                        Tôi đồng ý với các <a href="#" onclick="document.getElementById('termsModal').style.display = 'flex'" class="terms-link">điều khoản</a> và cam kết của ứng dụng.
                    </label>
                </div>
                <button type="submit" class="submit-btn">Đăng ký</button>
            </form>
        </div>
    </div>
    <!-- Login Form End -->

    <div id="termsModal" class="modal">
        <div class="modal-content">
            <h2 class="text-xl font-bold text-gray-800 mb-4">Điều khoản và Cam kết dành cho Giáo viên</h2>
            <div class="space-y-3 text-gray-700">
                <p>1. Người đăng ký cam kết cung cấp đầy đủ và chính xác thông tin cá nhân, chuyên môn, kinh nghiệm giảng dạy cũng như các giấy tờ liên quan theo yêu cầu của trung tâm.</p>
                <p>2. Trung tâm cam kết bảo mật tuyệt đối các thông tin cá nhân và hồ sơ của giáo viên. Các dữ liệu này sẽ chỉ được sử dụng cho mục đích tuyển dụng, phân công giảng dạy, và quản lý nhân sự nội bộ. </p>
                <p>3. Giáo viên đồng ý tuân thủ mọi nội quy, quy định hoạt động, và chính sách chuyên môn của trung tâm.</p>
                <p>4. Trung tâm có quyền đánh giá định kỳ về chất lượng giảng dạy thông qua phản hồi từ học viên, phụ huynh và các hình thức kiểm tra chuyên môn.</p>
                <p>5. Giáo viên có nghĩa vụ tham gia đầy đủ các buổi họp, tập huấn nghiệp vụ, các chương trình nâng cao trình độ chuyên môn do trung tâm tổ chức.</p>
                <p>6. Giáo viên chịu trách nhiệm cá nhân về nội dung bài giảng, tài liệu sử dụng và phong cách truyền đạt. </p>
                <p>7. Mọi hành vi phân biệt đối xử, xúc phạm, đe dọa hoặc gây ảnh hưởng xấu đến học viên, phụ huynh, đồng nghiệp hoặc uy tín của trung tâm sẽ bị xử lý nghiêm theo quy định và có thể bị truy cứu trách nhiệm pháp lý nếu cần thiết.</p>
                <p>8. Giáo viên có quyền đóng góp ý kiến, đề xuất phương pháp giảng dạy mới nhằm nâng cao hiệu quả đào tạo.</p>
                <p>9. Trong mọi tình huống phát sinh tranh chấp hoặc hiểu lầm, hai bên sẽ ưu tiên giải quyết thông qua đối thoại trực tiếp, hợp tác thiện chí và dựa trên tinh thần xây dựng.</p>
                <p>10. Khi nhấn nút "Tôi đã đọc và đồng ý", giáo viên xác nhận rằng mình đã đọc kỹ toàn bộ nội dung điều khoản và cam kết, hiểu rõ trách nhiệm và quyền lợi của bản thân khi đăng ký giảng dạy tại trung tâm.</p>
            </div>
            <button onclick="document.getElementById('termsModal').style.display = 'none'" class="close-btn">Đóng</button>
        </div>
    </div>
    <!-- Login Form End -->

    <!-- Footer Start -->
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
        // Đóng modal khi nhấp ra ngoài
        window.onclick = function (event) {
            var modal = document.getElementById('termsModal');
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
</body>
</html>