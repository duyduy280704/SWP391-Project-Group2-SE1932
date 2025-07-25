<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"> 

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Bootstrap 5 Stylesheet -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">

        <!-- Custom CSS for Blog Section -->
        <style>
            .blog-item {
                transition: transform 0.3s;
                cursor: pointer;
            }
            .blog-item:hover {
                transform: translateY(-5px);
            }
            .blog-overlay {
                position: absolute;
                top: 0;
                left: 0;
                right: 0;
                bottom: 0;
                background: rgba(0, 0, 0, 0.5);
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                opacity: 0;
                transition: opacity 0.3s;
            }
            .blog-item:hover .blog-overlay {
                opacity: 1;
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
                            <p><c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" /></p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-envelope text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Email</h6>
                            <p><c:out value="${setting.email}" default="Email chưa cập nhật" /></p>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-phone text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Số Điện Thoại</h6>
                            <p><c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" /></p>
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
                        <button type="button" class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarCollapse">
                            <div class="d-flex justify-content-between align-items-center w-100">
                                <div class="navbar-nav mx-auto">
                                    <a href="HomePage" class="nav-item nav-link ">Trang Chủ</a>
                                    <a href="about.jsp" class="nav-item nav-link">Giới Thiệu</a>
                                    <a href="course.jsp" class="nav-item nav-link">Khóa Học</a>
                                    <a href="teacher.jsp" class="nav-item nav-link">Giáo Viên</a>
                                    <a href="blog.jsp" class="nav-item nav-link active">Tin Tức</a>
                                    
                                </div>
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
        <div class="container pt-5 pb-3">
            <div class="text-center mb-5">
                <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Tin Tức</h5>
            </div>
          
                <div class="col-lg-8 mx-auto">
                    <h1 class="mb-4">${blog.title}</h1>
                    <img class="img-fluid mb-4" src="BlogImage?id=${blog.id}" alt="Ảnh blog">
                    <p class="text-muted mb-3">
                        <fmt:parseDate value="${blog.publishDate}" pattern="yyyy-MM-dd" var="parsedDate" />
                        <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                    </p>
                    <div>${blog.content}</div>
                    <a href="blog.jsp" class="btn btn-primary mt-4">Quay lại Tin Tức</a>
                </div>
            </div>
    
      
        <!-- Blog End -->

        

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
                        <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Khóa học</h5>
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
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>
        <script src="js/main.js"></script>

        <!-- JavaScript for Blog Modal -->
        <script>
            function showBlogDetails(id, title, content, date, imageSrc) {
                console.log('showBlogDetails called with ID:', id); // Debugging
                try {
                    document.getElementById('blogModalLabel').innerText = title;
                    document.getElementById('blogModalContent').innerHTML = content;
                    document.getElementById('blogModalDate').innerText = date;
                    document.getElementById('blogModalImage').src = imageSrc || 'img/placeholder.jpg'; // Fallback image
                    const blogModal = new bootstrap.Modal(document.getElementById('blogModal'), {});
                    blogModal.show();
                } catch (error) {
                    console.error('Error in showBlogDetails:', error);
                }
            }
        </script>
    </body>
</html>