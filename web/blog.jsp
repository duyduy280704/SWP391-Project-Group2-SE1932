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
                <div class="col-lg-9">
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
                            <!-- Menu căn giữa -->
                            <div class="navbar-nav mx-auto">
                                <a href="HomePage" class="nav-item nav-link">Trang Chủ</a>
                                <a href="about.jsp" class="nav-item nav-link">Giới Thiệu</a>
                                <a href="course.jsp" class="nav-item nav-link">Khóa Học</a>
                                <a href="teacher.jsp" class="nav-item nav-link">Giáo Viên</a>
                                <a href="blog.jsp" class="nav-item nav-link active">Tin Tức</a>
                            </div>

                            <!-- Nút hành động về phía phải -->
                            <div class="ml-auto">
                                <a class="btn btn-primary py-2 px-4 d-none d-lg-block" href="#">Tham Gia Ngay</a>
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
                    <h3 class="display-4 text-white text-uppercase">Tin Tức</h3>
                    <div class="d-inline-flex text-white">
                        <p class="m-0 text-uppercase"><a class="text-white" href="HomePage">Trang Chủ</a></p>
                        <i class="fa fa-angle-double-right pt-1 px-3"></i>
                        <p class="m-0 text-uppercase">Tin Tức</p>
                    </div>
                </div>
            </div>
        </div>
        <!-- Header End -->


        <!-- Blog Start -->
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="row">
                    <div class="col-lg-8">
                        <div class="row pb-3">
                            <div class="col-lg-6 mb-4">
                                <div class="blog-item position-relative overflow-hidden rounded mb-2">
                                    <img class="img-fluid" src="img/blog-1.jpg" alt="">
                                    <a class="blog-overlay text-decoration-none" href="">
                                        <h5 class="text-white mb-3">Lorem elitr magna stet eirmod labore amet labore clita at ut clita</h5>
                                        <p class="text-primary m-0">Jan 01, 2050</p>
                                    </a>
                                </div>
                            </div>
                            <div class="col-lg-6 mb-4">
                                <div class="blog-item position-relative overflow-hidden rounded mb-2">
                                    <img class="img-fluid" src="img/blog-2.jpg" alt="">
                                    <a class="blog-overlay text-decoration-none" href="">
                                        <h5 class="text-white mb-3">Lorem elitr magna stet eirmod labore amet labore clita at ut clita</h5>
                                        <p class="text-primary m-0">Jan 01, 2050</p>
                                    </a>
                                </div>
                            </div>
                            <div class="col-lg-6 mb-4">
                                <div class="blog-item position-relative overflow-hidden rounded mb-2">
                                    <img class="img-fluid" src="img/blog-3.jpg" alt="">
                                    <a class="blog-overlay text-decoration-none" href="">
                                        <h5 class="text-white mb-3">Lorem elitr magna stet eirmod labore amet labore clita at ut clita</h5>
                                        <p class="text-primary m-0">Jan 01, 2050</p>
                                    </a>
                                </div>
                            </div>
                            <div class="col-lg-6 mb-4">
                                <div class="blog-item position-relative overflow-hidden rounded mb-2">
                                    <img class="img-fluid" src="img/blog-1.jpg" alt="">
                                    <a class="blog-overlay text-decoration-none" href="">
                                        <h5 class="text-white mb-3">Lorem elitr magna stet eirmod labore amet labore clita at ut clita</h5>
                                        <p class="text-primary m-0">Jan 01, 2050</p>
                                    </a>
                                </div>
                            </div>
                            <div class="col-lg-6 mb-4">
                                <div class="blog-item position-relative overflow-hidden rounded mb-2">
                                    <img class="img-fluid" src="img/blog-2.jpg" alt="">
                                    <a class="blog-overlay text-decoration-none" href="">
                                        <h5 class="text-white mb-3">Lorem elitr magna stet eirmod labore amet labore clita at ut clita</h5>
                                        <p class="text-primary m-0">Jan 01, 2050</p>
                                    </a>
                                </div>
                            </div>
                            <div class="col-lg-6 mb-4">
                                <div class="blog-item position-relative overflow-hidden rounded mb-2">
                                    <img class="img-fluid" src="img/blog-3.jpg" alt="">
                                    <a class="blog-overlay text-decoration-none" href="">
                                        <h5 class="text-white mb-3">Lorem elitr magna stet eirmod labore amet labore clita at ut clita</h5>
                                        <p class="text-primary m-0">Jan 01, 2050</p>
                                    </a>
                                </div>
                            </div>
                            <div class="col-12">
                                <nav aria-label="Page navigation">
                                    <ul class="pagination pagination-lg justify-content-center mb-0">
                                        <li class="page-item disabled">
                                            <a class="page-link" href="#" aria-label="Previous">
                                                <span aria-hidden="true">«</span>
                                                <span class="sr-only">Previous</span>
                                            </a>
                                        </li>
                                        <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                        <li class="page-item"><a class="page-link" href="#">2</a></li>
                                        <li class="page-item"><a class="page-link" href="#">3</a></li>
                                        <li class="page-item">
                                            <a class="page-link" href="#" aria-label="Next">
                                                <span aria-hidden="true">»</span>
                                                <span class="sr-only">Next</span>
                                            </a>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-4 mt-5 mt-lg-0">
                        <!-- Author Bio -->


                        <!-- Search Form -->
                        <div class="mb-5">
                            <form action="">
                                <div class="input-group">
                                    <input type="text" class="form-control form-control-lg" placeholder="Keyword">
                                    <div class="input-group-append">
                                        <span class="input-group-text bg-transparent text-primary"><i
                                                class="fa fa-search"></i></span>
                                    </div>
                                </div>
                            </form>
                        </div>

                        <!-- Category List -->
                        <div class="mb-5">
                            <h3 class="text-uppercase mb-4" style="letter-spacing: 5px;">Categories</h3>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <a href="" class="text-decoration-none h6 m-0">Web Design</a>
                                    <span class="badge badge-primary badge-pill">150</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <a href="" class="text-decoration-none h6 m-0">Web Development</a>
                                    <span class="badge badge-primary badge-pill">131</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <a href="" class="text-decoration-none h6 m-0">Online Marketing</a>
                                    <span class="badge badge-primary badge-pill">78</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <a href="" class="text-decoration-none h6 m-0">Keyword Research</a>
                                    <span class="badge badge-primary badge-pill">56</span>
                                </li>
                                <li class="list-group-item d-flex justify-content-between align-items-center px-0">
                                    <a href="" class="text-decoration-none h6 m-0">Email Marketing</a>
                                    <span class="badge badge-primary badge-pill">98</span>
                                </li>
                            </ul>
                        </div>

                        <!-- Recent Post -->
                        <div class="mb-5">
                            <h3 class="text-uppercase mb-4" style="letter-spacing: 5px;">Recent Post</h3>
                            <a class="d-flex align-items-center text-decoration-none mb-3" href="">
                                <img class="img-fluid rounded" src="img/blog-80x80.jpg" alt="">
                                <div class="pl-3">
                                    <h6 class="m-1">Diam lorem dolore justo eirmod lorem dolore</h6>
                                    <small>Jan 01, 2050</small>
                                </div>
                            </a>
                            <a class="d-flex align-items-center text-decoration-none mb-3" href="">
                                <img class="img-fluid rounded" src="img/blog-80x80.jpg" alt="">
                                <div class="pl-3">
                                    <h6 class="m-1">Diam lorem dolore justo eirmod lorem dolore</h6>
                                    <small>Jan 01, 2050</small>
                                </div>
                            </a>
                            <a class="d-flex align-items-center text-decoration-none mb-3" href="">
                                <img class="img-fluid rounded" src="img/blog-80x80.jpg" alt="">
                                <div class="pl-3">
                                    <h6 class="m-1">Diam lorem dolore justo eirmod lorem dolore</h6>
                                    <small>Jan 01, 2050</small>
                                </div>
                            </a>
                        </div>

                        <!-- Tag Cloud -->
                    </div>
                </div>
            </div>
        </div>
        <!-- Blog End -->


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
    </body>

</html>