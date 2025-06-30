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
                        <a href="HomePage" class="navbar-brand d-block d-lg-none text-decoration-none">
                            <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                        </a>
                        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse" id="navbarCollapse">
                            <div class="d-flex justify-content-between align-items-center w-100">
                                <div class="navbar-nav mx-auto">
                                    <a href="HomePage" class="nav-item nav-link active">Trang Chủ</a>
                                    <a href="about.jsp" class="nav-item nav-link">Giới Thiệu</a>
                                    <a href="course.jsp" class="nav-item nav-link">Khóa Học</a>
                                    <a href="teacher.jsp" class="nav-item nav-link">Giáo Viên</a>
                                    <a href="blog.jsp" class="nav-item nav-link">Tin Tức</a>
                                </div>
                                
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

        <!-- Blog Management Start -->
        <div class="container pt-5 pb-3">
            <div class="text-center mb-5">
                <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Quản Lý Tin Tức</h5>
                <h1>Danh Sách Bài Viết</h1>
            </div>

            <!-- Search Form -->
            <!-- Search Form -->
           <form action="Blog" method="post" class="form-inline mb-3 justify-content-center">
    <input type="text" name="title" class="form-control mr-2" placeholder="Tìm tiêu đề" value="${searchTitle}">
    <input type="date" name="fromDate" class="form-control mr-2" value="${fromDate}">
    <input type="date" name="toDate" class="form-control mr-2" value="${toDate}">
    <input type="hidden" name="search" value="true">
    <button type="submit" class="btn btn-secondary">Tìm Kiếm</button>
</form>

            <!-- Search Message -->
            <c:if test="${not empty searchMessage}">
                <div class="alert alert-info text-center">${searchMessage}</div>
            </c:if>

            <!-- Add Blog Form -->
            <div class="mb-5">
                <h3 class="text-uppercase mb-3">Thêm Bài Viết Mới</h3>
                <form action="Blog" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="add" value="true">
                    <div class="form-group">
                        <label>Tiêu Đề</label>
                        <input type="text" name="title" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Nội Dung</label>
                        <textarea name="content" class="form-control" rows="4" required></textarea>
                    </div>
                    <div class="form-group">
                        <label>Hình Ảnh</label>
                        <input type="file" name="img" class="form-control-file" accept="image/*">
                    </div>
                    <div class="form-group">
                        <label>Ngày Đăng</label>
                        <input type="date" name="publishDate" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-primary">Thêm Bài Viết</button>
                </form>
            </div>

            <!-- Edit Blog Form (shown when mode=1) -->
            <c:if test="${blog != null}">
                <div class="mb-5">
                    <h3 class="text-uppercase mb-3">Chỉnh Sửa Bài Viết</h3>
                    <form action="Blog" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="update" value="true">
                        <input type="hidden" name="id" value="${blog.id}">
                        <div class="form-group">
                            <label>Tiêu Đề</label>
                            <input type="text" name="title" class="form-control" value="${blog.title}" required>
                        </div>
                        <div class="form-group">
                            <label>Nội Dung</label>
                            <textarea name="content" class="form-control" rows="4" required>${blog.content}</textarea>
                        </div>
                        <div class="form-group">
                            <label>Hình Ảnh</label>
                            <input type="file" name="img" class="form-control-file" accept="image/*">
                            <c:if test="${blog.img != null}">
                                <img src="Blog?mode=image&id=${blog.id}" alt="Blog Image" class="img-fluid mt-2" style="max-width: 100px;"/>
                                <small class="form-text text-muted">Để trống để giữ hình ảnh hiện tại</small>
                            </c:if>
                        </div>
                        <div class="form-group">
                            <label>Ngày Đăng</label>
                            <input type="date" name="publishDate" class="form-control" value="${blog.publishDate}" required>
                        </div>
                        <button type="submit" class="btn btn-primary">Cập Nhật Bài Viết</button>
                    </form>
                </div>
            </c:if>

            <!-- Blog List -->
            <h3 class="text-uppercase mb-3">${search == 'true' ? 'Kết Quả Tìm Kiếm' : 'Danh Sách Bài Viết'}</h3>
            <div class="table-responsive">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Tiêu Đề</th>
                            <th>Ngày Đăng</th>
                            <th>Hình Ảnh</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="blog" items="${data}">
                            <tr>
                                <td>${blog.id}</td>
                                <td>${blog.title}</td>
                                <td>${fn:substring(blog.publishDate, 0, 10)}</td>
                                <td>
                                    <c:if test="${blog.img != null}">
                                        <img src="Blog?mode=image&id=${blog.id}" alt="Blog Image" class="img-fluid" style="max-width: 100px;"/>
                                    </c:if>
                                </td>
                                <td>
                                    <a href="Blog?mode=1&id=${blog.id}" class="btn btn-info btn-sm">Chỉnh Sửa</a>
                                    <a href="Blog?mode=2&id=${blog.id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <!-- Blog Management End -->

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
    </body>
</html>