<!-- Dương_homepage -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>BIGDREAM</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free JSP Templates" name="keywords">
        <meta content="Free JSP Templates" name="description">

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
            /* Animation */
            .wow {
                animation-duration: 1s;
                animation-fill-mode: both;
            }
            .fadeInLeft {
                animation-name: fadeInLeft;
            }
            .fadeInRight {
                animation-name: fadeInRight;
            }
            @keyframes fadeInLeft {
                from {
                    opacity: 0;
                    transform: translateX(-30px);
                }
                to {
                    opacity: 1;
                    transform: translateX(0);
                }
            }
            @keyframes fadeInRight {
                from {
                    opacity: 0;
                    transform: translateX(30px);
                }
                to {
                    opacity: 1;
                    transform: translateX(0);
                }
            }

            /* Button Hover */
            .btn-light:hover {
                background-color: #fff3cd;
                color: #d63384;
                transform: scale(1.05);
                transition: all 0.3s ease;
            }
            
            
            .profile-card {
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
            border-radius: 15px;
        }

        .profile-img {
            max-height: 400px;
            object-fit: cover;
            border-top-left-radius: 15px;
            border-bottom-left-radius: 15px;
        }

        .profile-info {
            padding: 30px;
        }
        </style>


    </head>
    <body>
        <!-- Topbar Start -->
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
                <div class="col-lg-9 mx-auto">  <!-- Thêm mx-auto để căn giữa khối nav -->
                    <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 px-0">
                        <!-- Logo cho mobile -->
                        <a href="HomePage" class="navbar-brand d-block d-lg-none text-decoration-none">
                            <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                        </a>

                        <!-- Nút toggle cho mobile -->
                        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>

                        <!-- Menu + Nút hành động -->
                        <div class="collapse navbar-collapse" id="navbarCollapse">
                            <div class="d-flex justify-content-between align-items-center w-100">
                                <!-- Menu căn giữa -->
                                <div class="navbar-nav mx-auto">
                                    <a href="HomePage" class="nav-item nav-link ">Trang Chủ</a>
                                    <a href="about.jsp" class="nav-item nav-link">Giới Thiệu</a>
                                    <a href="Course" class="nav-item nav-link">Khóa Học</a>
                                    <a href="teacher.jsp" class="nav-item nav-link active">Giáo Viên</a>
                                    <a href="blog.jsp" class="nav-item nav-link">Tin Tức</a>
                                </div>
                                <!-- Nút hành động về phía phải -->
                                <a class="btn btn-primary py-2 px-4 d-none d-lg-block ml-lg-3" href="login">Tham Gia Ngay</a>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>
        <!-- Navbar End -->

        <!-- Header Start -->
        <div class="container-fluid page-header" style="margin-bottom: 90px;">
            <div class="container">
                <div class="d-flex flex-column justify-content-center" style="min-height: 300px">
                    <h3 class="display-4 text-white text-uppercase">Giáo Viên</h3>
                    <div class="d-inline-flex text-white">
                        <p class="m-0 text-uppercase"><a class="text-white" href="HomePage">Trang Chủ</a></p>
                        <i class="fa fa-angle-double-right pt-1 px-3"></i>
                        <p class="m-0 text-uppercase">Giáo Viên</p>
                    </div>
                </div>
            </div>
        </div>
        <!-- Header End -->

        <div class="container py-5">
    <a href="teacher.jsp" class="btn btn-secondary mb-4">← Quay về danh sách giáo viên</a>
    <div class="row justify-content-center">
        <div class="col-md-10 profile-card bg-white d-flex">
            <div class="col-md-5 p-0">
                <img src="picteacher?id=${teacher.id}" alt="Ảnh giáo viên" class="img-fluid profile-img w-100">
            </div>
            <div class="col-md-7 profile-info">
                <h2 class="text-primary mb-3">${teacher.name}</h2>
                <h5 class="text-muted">Kinh nghiệm: ${teacher.year}</h5>
                <hr>
                <p><strong>Giới thiệu:</strong></p>
                

                <c:if test="${not empty teacher.exp}">
                    <p><strong>Kỹ năng / Môn dạy:</strong></p>
                    <ul>
                        <c:forEach var="exp" items="${teacher.exp}">
                            <li>${exp}</li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
        </div>
    </div>
</div>





        <!-- Team End -->

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

        <!-- Contact Javascript File -->
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>

        <!-- Form Submission Script -->
        <script>
            function submitNewsletter() {
                var email = document.getElementById('newsletter-email').value;
                // Add logic to handle newsletter submission
                console.log('Newsletter subscribed:', {email: email});
                // Example: window.location.href = 'newsletter?email=' + encodeURIComponent(email);
            }
        </script>
    </body>
</html>