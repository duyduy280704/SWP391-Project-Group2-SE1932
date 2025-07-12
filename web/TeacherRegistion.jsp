<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng ký làm giáo viên - BIGDREAM</title>
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

            .label {
                display: block;
                font-size: 0.875rem;
                font-weight: 500;
                color: #374151;
                margin-bottom: 0.25rem;
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

            input[type="file"] {
                width: 100%;
                padding: 0.75rem;
                border: 1px solid #d1d5db;
                border-radius: 0.375rem;
                font-size: 0.875rem;
                color: #374151;
            }

            input[type="file"]:focus {
                border-color: #f97316;
                box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.5);
                outline: none;
            }

            .messageor-message {
                color: #ef4444;
                font-size: 0.875rem;
                margin-bottom: 1rem;
                text-align: left;
            }

            .submit-btn {
                background-color: #f97316;
                color: #ffffff;
                padding: 0.5rem 1.5rem;
                border-radius: 0.375rem;
                transition: background-color 0.2s ease;
                font-weight: 600;
                border: none;
                cursor: pointer;
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
                padding: 0.5rem 1rem;
                border-radius: 0.375rem;
                transition: background-color 0.2s ease;
                margin-top: 1rem;
                border: none;
                cursor: pointer;
            }

            .close-btn:hover {
                background-color: #ea580c;
            }

            input[type="date"]::-webkit-calendar-picker-indicator {
                cursor: pointer;
            }

            @media (max-width: 576px) {
                .form-container {
                    padding: 1.5rem;
                    max-width: 90%;
                }

                h2 {
                    font-size: 1.5rem;
                }

                .input-field,
                .date-input,
                input[type="file"],
                .submit-btn {
                    font-size: 0.95rem;
                    padding: 0.8rem;
                }
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

        <!-- Registration Form Start -->
        <div class="login-container">
            <div class="form-container">
                <h2>Đăng ký làm giáo viên</h2>
                <c:if test="${not empty message}">
                    <p class="messageor-message">${message}</p>
                </c:if>
                <form action="resgiterTeacher" method="post" enctype="multipart/form-data" class="space-y-6">
                    <div>
                        <label class="label">Họ và tên:</label>
                        <input type="text" name="fullName" value="${fullName}" class="input-field" required
                               oninvalid="this.setCustomValidity('Vui lòng nhập họ và tên')" oninput="this.setCustomValidity('')">
                    </div>
                    <div>
                        <label class="label">Email:</label>
                        <input type="email" name="email" value="${email}" class="input-field" required
                               oninvalid="this.setCustomValidity('Vui lòng nhập email')" oninput="this.setCustomValidity('')">
                    </div>
                    <div>
                        <label class="label">Số điện thoại:</label>
                        <input type="text" name="phone" value="${phone}" class="input-field" required
                               oninvalid="this.setCustomValidity('Vui lòng nhập số điện thoại')" oninput="this.setCustomValidity('')">
                    </div>
                    <div>
                        <label class="label">File CV (PDF, DOC, DOCX):</label>
                        <input type="file" name="cvFile" accept=".pdf,.doc,.docx" class="input-field" required
                               id="cvFile" onchange="validateFile(this)"
                               oninvalid="this.setCustomValidity('Vui lòng chọn file CV')" oninput="this.setCustomValidity('')">
                    </div>
                    <div>
                        <label class="label">Ngày sinh:</label>
                        <input type="date" name="birthDate" value="${birthDate}" class="date-input" required
                               oninvalid="this.setCustomValidity('Vui lòng chọn ngày sinh')" oninput="this.setCustomValidity('')">
                    </div>
                    <div>
                        <label class="label">Giới tính:</label> 
                        <select name="gender" class="input-field" required
                                oninvalid="this.setCustomValidity('Vui lòng chọn giới tính')" oninput="this.setCustomValidity('')">
                            <option value="" <c:if test="${empty gender}">selected</c:if>>Chọn giới tính</option>
                            <option value="Nam" <c:if test="${gender == 'Nam'}">selected</c:if>>Nam</option>
                            <option value="Nữ" <c:if test="${gender == 'Nữ'}">selected</c:if>>Nữ</option>
                            <option value="Khác" <c:if test="${gender == 'Khác'}">selected</c:if>>Khác</option>
                            </select>
                        </div>
                        <div>
                            <label class="label">Chuyên môn:</label>
                            <input type="text" name="expertise" value="${expertise}" class="input-field" required
                               oninvalid="this.setCustomValidity('Vui lòng nhập chuyên môn')" oninput="this.setCustomValidity('')">
                    </div>
                    <div>
                        <label class="label">Loại khóa học:</label>
                        <select name="idTypeCourse" class="input-field" required
                                oninvalid="this.setCustomValidity('Vui lòng chọn loại khóa học')" oninput="this.setCustomValidity('')">
                            <option value="" <c:if test="${empty idTypeCourse}">selected</c:if>>Chọn loại khóa học</option>
                            <c:forEach var="o" items="${courseTypes}">
                                <option value="${o.getId()}" <c:if test="${idTypeCourse == o.getId()}">selected</c:if>>${o.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div>
                        <label class="label">Số năm kinh nghiệm:</label>
                        <input type="number" name="yearsOfExperience" value="${yearsOfExperience}" class="input-field" 
                               min="0" step="1" required
                               oninvalid="this.setCustomValidity('Vui lòng nhập số năm kinh nghiệm')" 
                               oninput="this.setCustomValidity('')">
                    </div>
                    <div>
                        <label class="label">
                            <input type="checkbox" name="agreeTerms" class="mr-2" required
                                   oninvalid="this.setCustomValidity('Vui lòng đồng ý với điều khoản')" 
                                   oninput="this.setCustomValidity('')">
                            Tôi đồng ý với các <a href="#" onclick="document.getElementById('termsModal').style.display = 'flex'" class="terms-link">điều khoản</a> và cam kết của ứng dụng.
                        </label>
                    </div>
                    <button type="submit" class="submit-btn">Đăng ký</button>
                </form>
            </div>
        </div>
        <!-- Registration Form End -->

        <div id="termsModal" class="modal">
            <div class="modal-content">
                <h2 class="text-xl font-bold text-gray-800 mb-4">Điều khoản và Cam kết dành cho Giáo viên</h2>
                <div class="space-y-3 text-gray-700">
                    <p>1. Người đăng ký cam kết cung cấp đầy đủ và chính xác thông tin cá nhân, chuyên môn, kinh nghiệm giảng dạy cũng như các giấy tờ liên quan theo yêu cầu của trung tâm.</p>
                    <p>2. Trung tâm cam kết bảo mật tuyệt đối các thông tin cá nhân và hồ sơ của giáo viên. Các dữ liệu này sẽ chỉ được sử dụng cho mục đích tuyển dụng, phân công giảng dạy, và quản lý nhân sự nội bộ.</p>
                    <p>3. Giáo viên đồng ý tuân thủ mọi nội quy, quy định hoạt động, và chính sách chuyên môn của trung tâm.</p>
                    <p>4. Trung tâm có quyền đánh giá định kỳ về chất lượng giảng dạy thông qua phản hồi từ học viên, phụ huynh và các hình thức kiểm tra chuyên môn.</p>
                    <p>5. Giáo viên có nghĩa vụ tham gia đầy đủ các buổi họp, tập huấn nghiệp vụ, các chương trình nâng cao trình độ chuyên môn do trung tâm tổ chức.</p>
                    <p>6. Giáo viên chịu trách nhiệm cá nhân về nội dung bài giảng, tài liệu sử dụng và phong cách truyền đạt.</p>
                    <p>7. Mọi hành vi phân biệt đối xử, xúc phạm, đe dọa hoặc gây ảnh hưởng xấu đến học viên, phụ huynh, đồng nghiệp hoặc uy tín của trung tâm sẽ bị xử lý nghiêm theo quy định và có thể bị truy cứu trách nhiệm pháp lý nếu cần thiết.</p>
                    <p>8. Giáo viên có quyền đóng góp ý kiến, đề xuất phương pháp giảng dạy mới nhằm nâng cao hiệu quả đào tạo.</p>
                    <p>9. Trong mọi tình huống phát sinh tranh chấp hoặc hiểu lầm, hai bên sẽ ưu tiên giải quyết thông qua đối thoại trực tiếp, hợp tác thiện chí và dựa trên tinh thần xây dựng.</p>
                    <p>10. Khi nhấn nút "Tôi đã đọc và đồng ý", giáo viên xác nhận rằng mình đã đọc kỹ toàn bộ nội dung điều khoản và cam kết, hiểu rõ trách nhiệm và quyền lợi của bản thân khi đăng ký giảng dạy tại trung tâm.</p>
                </div>
                <button onclick="document.getElementById('termsModal').style.display = 'none'" class="close-btn">Đóng</button>
            </div>
        </div>

        <!-- Footer Start -->
        <footer class="bg-dark text-white pt-5 pb-4">
            <div class="container text-md-left">
                <div class="row text-md-left">
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
                    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                        <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Về Chúng Tôi</h5>
                        <p><c:out value="${setting.about}" default="Thông tin chưa cập nhật." /></p>
                    </div>
                </div>
                <hr class="mb-4">
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
                    window.onclick = function (event) {
                        var modal = document.getElementById('termsModal');
                        if (event.target == modal) {
                            modal.style.display = "none";
                        }
                    }
                    function validateFile(input) {
                        const file = input.files[0];
                        if (!file)
                            return;

                        const maxSize = 10 * 1024 * 1024; // 10MB
                        const allowedExtensions = ['pdf', 'doc', 'docx'];
                        const fileName = file.name.toLowerCase();
                        const fileExtension = fileName.split('.').pop();

                        if (!allowedExtensions.includes(fileExtension)) {
                            alert("❌ Chỉ chấp nhận các định dạng file .pdf, .doc hoặc .docx.");
                            input.value = ""; // reset input
                            return;
                        }

                        if (file.size > maxSize) {
                            alert("❌ File CV phải nhỏ hơn hoặc bằng 10MB.");
                            input.value = ""; // reset input
                            return;
                        }
                    }
        </script>
    </body>
</html>