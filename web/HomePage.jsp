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
                <div class="col-lg-9 mx-auto"> 
                    <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 px-0">
                        <!-- Logo cho mobile -->
                        <a href="HomePage" class="navbar-brand d-block d-lg-none text-decoration-none">
                            <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                        </a>

                        <!-- Nút toggle cho mobile -->
                        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>

                        <div class="collapse navbar-collapse" id="navbarCollapse">
                            <div class="d-flex justify-content-between align-items-center w-100">

                                <div class="navbar-nav mx-auto">
                                    <a href="HomePage" class="nav-item nav-link active">Trang Chủ</a>
                                    <a href="about.jsp" class="nav-item nav-link">Giới Thiệu</a>
                                    <a href="Course" class="nav-item nav-link">Khóa Học</a>
                                    <a href="teacher.jsp" class="nav-item nav-link">Giáo Viên</a>
                                    <a href="blog.jsp" class="nav-item nav-link">Tin Tức</a>
                                </div>

                                <a class="btn btn-primary py-2 px-4 d-none d-lg-block ml-lg-3" href="login">Tham Gia Ngay</a>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
        </div>

        <!-- Navbar End -->

        <!-- Carousel Start -->
        <div class="container-fluid p-0 pb-5 mb-5">
            <div id="header-carousel" class="carousel slide carousel-fade" data-ride="carousel">
                <!-- Carousel indicators -->
                <ol class="carousel-indicators">
                    <c:forEach var="slide" items="${slides}" varStatus="status">
                        <li data-target="#header-carousel" data-slide-to="${status.index}"
                            class="${status.index == 0 ? 'active' : ''}"></li>
                        </c:forEach>
                </ol>

                <!-- Carousel items -->
                <div class="carousel-inner">
                    <c:forEach var="slide" items="${slides}" varStatus="status">
                        <div class="carousel-item ${status.index == 0 ? 'active' : ''}" style="min-height: 300px;">
                            <!-- Dùng servlet để hiển thị ảnh dạng byte[] -->
                            <img src="imagebanner?id2=${slide.id}"
                                 style="width: 1366px; height: 768px; object-fit: cover; object-position: center center;"
                                 alt="${slide.title}">

                            <div class="carousel-caption d-flex align-items-center justify-content-center">
                                <div class="p-5" style="width: 100%; max-width: 900px;">

                                    <h1 class="display-3 text-white mb-md-4">${slide.title}</h1>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>


        <!-- Carousel End -->

        <!-- About Start -->
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="row align-items-center">
                    <c:forEach var="a" items="${aboutList}" varStatus="loop">
                        <c:if test="${loop.first}">
                            <div class="col-lg-5">
                                <!-- Sửa src: dùng servlet lấy ảnh theo id -->
                                <img class="img-fluid rounded mb-4 mb-lg-0" src="imageabout?id=${a.id}" alt="About Image">
                            </div>
                            <div class="col-lg-7">
                                <div class="text-left mb-4">
                                    <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Giới Thiệu</h5>
                                    <h1>${a.title}</h1>
                                </div>
                                <p>${a.content}</p>
                                <a href="about.jsp" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold mt-2">Xem Thêm</a>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>


        <!-- About End -->


        <!-- Courses Start -->
        <div class="container-fluid py-5 bg-light">
            <div class="container py-5">
                <div class="text-center mb-5">
                    <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Khóa Học</h5>
                    <h1>Một số khóa học của chúng tôi</h1>
                </div>
                <form action="coursestaff" method="post" enctype="multipart/form-data">
                    <div class="row">
                        <c:forEach var="c" items="${courseList}">
                            <div class="col-lg-4 col-md-6 mb-4 d-flex align-items-stretch">
                                <div class="card shadow-sm border-0 w-100 d-flex flex-column">
                                    <div style="width: 100%; height: 200px; overflow: hidden;">
                                        <c:choose>
                                            <c:when test="${not empty c.image}">
                                                <img src="image?id=${c.id}" alt="Course Picture"
                                                     style="width: 100%; height: 100%; object-fit: cover;"
                                                     onerror="this.src='images/no-image.png'; this.alt='Image not available';">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="bg-secondary text-white d-flex align-items-center justify-content-center"
                                                     style="width: 100%; height: 100%;">
                                                    No Image
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <div class="card-body bg-white flex-grow-1 d-flex flex-column">
                                        <div class="d-flex justify-content-between mb-2 text-muted small">
                                            <span><i class="fa fa-folder text-primary mr-1"></i>${c.type}</span>
                                            <span><i class="far fa-clock text-primary mr-1"></i>1h 30m</span>
                                        </div>
                                        <h5 class="card-title">${c.name}</h5>
                                        <p class="card-text text-body" style="min-height: 72px;">${c.description}</p>
                                        <div class="mt-auto">
                                            <div class="card-footer bg-white border-top d-flex justify-content-between align-items-center px-0 pt-3">
                                                <span class="text-warning"><i class="fa fa-star mr-1"></i>4.5 <small class="text-muted">(250)</small></span>
                                                <span class="text-primary font-weight-bold">${c.fee} đ</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>

                        <!-- Đưa "Xem thêm" ra ngoài row để tránh bị lồng sai vị trí -->
                    </div>
                    <div class="text-center mt-4">
                        <a href="course.jsp" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold">Xem Thêm</a>
                    </div>
                </form>
            </div>
        </div>



        <!-- Courses End -->

        <!-- Team Start -->
        <div class="container-fluid py-5">
            <div class="container pt-5 pb-3">
                <div class="text-center mb-5">
                    <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Giáo Viên</h5>
                    <h1>Một số thầy cô của trung tâm</h1>
                </div>
                <div class="row">
                    <c:forEach var="t" items="${teacherList}">
                        <div class="col-md-6 col-lg-3 text-center team mb-4">
                            <div class="team-item rounded overflow-hidden mb-2">
                                <div class="team-img position-relative">
                                    <!-- Sửa src để gọi servlet lấy ảnh theo id giáo viên -->
                                    <img class="card-img-top w-100" src="picteacher?id=${t.id}" 
                                         alt="image" style="height: 200px; object-fit: cover;">
                                    <div class="team-social">
                                        <a class="btn btn-outline-light btn-square mx-1" href="#">
                                            <i class="fab fa-facebook-f"></i>
                                        </a>
                                    </div>
                                </div>
                                <div class="bg-secondary p-4">
                                    <h5>${t.name}</h5>
                                    <p class="m-0">${t.exp}</p>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                    <!-- Nút "Xem thêm" -->
                    <div class="col-12 text-center mt-4">
                        <a href="teacher.jsp" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold mt-2">Xem Thêm</a>
                    </div>
                </div>
            </div>
        </div>




        <!-- Team End -->

        <!-- Testimonial Start -->
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="text-center mb-5">
                    <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Đánh Giá</h5>
                    <h1>Các học viên nói về khóa học</h1>
                </div>
                <div class="row justify-content-center">
                    <div class="col-lg-8">
                        <div class="owl-carousel testimonial-carousel">
                            <c:forEach var="f" items="${feedbackList}">
                                <div class="text-center">
                                    <i class="fa fa-3x fa-quote-left text-primary mb-4"></i>
                                    <h4 class="font-weight-normal mb-4">${f.feedbackText}</h4>
                                    <img class="img-fluid mx-auto mb-3" src="picstudent?id=${f.studentId}" alt="stu">
                                    <h5 class="m-0">${f.studentName}</h5>
                                    <span>${f.feedbackDate}</span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Testimonial End -->

        <!-- Blog Start -->
        <div class="container pt-5 pb-3">
            <div class="text-center mb-5">
                <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Tin Tức</h5>
                <h1>Các tin gần đây</h1>
            </div>

            <div class="row pb-3">
                <c:if test="${not empty blogList}">
                    <c:forEach var="n" items="${blogList}">
                        <div class="col-lg-4 mb-4">
                            <div class="blog-item position-relative overflow-hidden rounded mb-2">
                                <img class="img-fluid" src="BlogImage?id=${n.id}" alt="Ảnh blog">

                                <a class="blog-overlay text-decoration-none" href="#">
                                    <h5 class="text-white mb-3">${n.title}</h5>
                                    <p class="text-primary m-0">
                                        ${fn:substring(n.publishDate, 0, 10)}
                                    </p>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty blogList}">
                    <div class="col-12 text-center">
                        <p>Không có bài viết nào gần đây.</p>
                    </div>
                </c:if>
            </div>
        </div>

        <!-- Blog End -->

        <!-- Footer Start -->
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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