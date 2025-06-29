<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
            body {
                margin: 0;
                padding: 0;
                overflow-x: hidden;
            }
            .sidebar {
                position: fixed;
                top: 0;
                left: 0;
                height: 100vh;
                width: 220px;
                background-color: #ffffff;
                padding-top: 60px;
                display: flex;
                flex-direction: column;
                transition: transform 0.3s ease-in-out;
                z-index: 1000;
            }
            .sidebar.hidden {
                transform: translateX(-220px);
            }
            .sidebar a {
                color: #000;
                padding: 15px 20px;
                text-decoration: none;
                transition: background 0.3s;
            }
            .sidebar a:hover,
            .sidebar a.active {
                background-color: #FF6600;
            }
            .main-content {
                margin-left: 220px;
                padding: 20px;
                transition: margin-left 0.3s ease-in-out;
            }
            .main-content.full {
                margin-left: 0;
            }
            .toggle-btn {
                position: fixed;
                top: 20px;
                left: 230px;
                z-index: 1001;
                background-color: #343a40;
                color: white;
                border: none;
                padding: 10px;
                cursor: pointer;
                transition: left 0.3s ease-in-out;
            }
            .toggle-btn.hidden {
                left: 10px;
            }
            @media (max-width: 768px) {
                .sidebar {
                    transform: translateX(-220px);
                }
                .sidebar.active {
                    transform: translateX(0);
                }
                .main-content {
                    margin-left: 0;
                }
                .main-content.full {
                    margin-left: 0;
                }
                .toggle-btn {
                    left: 10px;
                }
                .toggle-btn.hidden {
                    left: 230px;
                }
            }
            .img-container {
                position: relative;
                width: 100%;
                padding-top: 66.66%;
                overflow: hidden;
                background-color: #f8f8f8;
            }

            .course-img {
                position: absolute;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
            .profile-avatar {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                overflow: hidden;
                border: 2px solid #FF6600;
                cursor: pointer;
                margin-bottom: 5px;
                display: block;
            }

            .profile-avatar img {
                width: 100%;
                height: 100%;
                object-fit: cover;
            }
        </style>
    </head>
    <body>
        <!-- Toggle Button -->
        <button class="toggle-btn" onclick="toggleSidebar()">
            <i class="fas fa-bars"></i>
        </button>

        <!-- Topbar Start -->
        <div class="container-fluid d-none d-lg-block ">
            <div class="row align-items-center py-4 px-xl-5 justify-content-end">
                <div></div>
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

        <!-- sidebar Start -->
        <div class="sidebar" id="sidebar">
            <div class="col-lg-3">
                <a href="" class="text-decoration-none">
                    <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                </a>
            </div>
            <div class="profile-container">
                <c:set var="picturePath" value="${not empty picturePath ? picturePath : sessionScope.picturePath}" />
                <c:choose>
                    <c:when test="${not empty picturePath}">
                        <a href="profile" class="profile-avatar">
                            <img src="${pageContext.request.contextPath}/${picturePath}" alt="Profile Avatar">
                        </a>
                        <div class="profile-name">${profile != null ? profile.name : 'Tên không xác định'}</div>
                    </c:when>
                    <c:otherwise>
                        <a href="profile" class="profile-avatar">
                            <img src="${pageContext.request.contextPath}/img/default-avatar.jpg" alt="Default Avatar">
                        </a>
                        <div class="profile-name">${profile != null ? profile.name : 'Tên không xác định'}</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <a href="HomePage" class="nav-item nav-link active">Trang Chủ</a>
            <a href="about.jsp" class="nav-item nav-link">Giới Thiệu</a>
            <a href="course.jsp" class="nav-item nav-link">Khóa Học</a>
            <a href="teacher.jsp" class="nav-item nav-link">Giáo Viên</a>
            <a href="blog.jsp" class="nav-item nav-link">Tin Tức</a>
        </div>
        <!-- sidebar End -->

        <!-- Main Content -->
        <div class="main-content" id="main-content">

            <div class="mb-4">
                <h2>
                    Xin chào, ${name} 👋
                </h2>
                <p>Chúc bạn một ngày học tập hiệu quả tại BigDream!</p>
            </div>
            <!-- thông báo mới nhất -->  
            <h5 class="section-title"> 🔔Thông báo mới</h5>
            <ul>
                <c:forEach var="n" items="${notifications}">
                    <li>
                        <strong>${n.message}</strong><br/>
                        Ngày gửi: ${n.date}<br/>
                    </li>
                </c:forEach>
            </ul>
            <!-- lịch học sắp tới -->        
            <h5 class="section-title">🕒 Lịch học sắp tới</h5>
            <div class="table-responsive mb-4">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th>Ngày</th>
                            <th>Giờ</th>
                            <th>Lớp</th>
                            <th>Giáo viên</th>
                            <th>Phòng</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="s" items="${schedules}">
                            <tr>
                                <td>${s.dayVN}, ${fn:substring(s.day, 8, 10)}/${fn:substring(s.day, 5, 7)}/${fn:substring(s.day, 0, 4)}</td>
                                <td>${s.startTimeFormatted}‑${s.endTimeFormatted}</td>
                                <td>${s.nameClass}</td>
                                <td>${s.teacherName}</td>
                                <td>${s.room}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div class="col-12 mt-3">
                    <a href="scheduleStudent" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold">Xem Thêm</a>
                </div>
            </div>


            <!-- Courses Start -->
            <div class="container-fluid py-5 bg-light">
                <div class="container py-5">
                    <h5 class="section-title mt-5">🔥 Khóa học được đăng ký nhiều nhất</h5>
                    <hr>
                    <form action="coursestaff" method="post" enctype="multipart/form-data">
                        <div class="row">
                            <c:forEach var="c" items="${courseList}">
                                <div class="col-lg-4 col-md-6 mb-4 d-flex align-items-stretch">
                                    <div class="card shadow-sm border-0 w-100 d-flex flex-column">


                                        <div class="img-container">
                                            <c:choose>
                                                <c:when test="${not empty c.image}">
                                                    <img src="image?id=${c.id}" class="course-img" alt="Course Picture"
                                                         onerror="this.src='images/no-image.png'; this.alt='Image not available';">
                                                </c:when>
                                                <c:otherwise>
                                                    <img src="images/no-image.png" class="course-img" alt="No Image">
                                                </c:otherwise>
                                            </c:choose>
                                        </div>


                                        <div class="card-body bg-white flex-grow-1 d-flex flex-column">
                                            <div class="d-flex justify-content-between mb-2 text-muted small">
                                                <span><i class="fa fa-folder text-primary mr-1"></i>${c.type}</span>
                                            </div>
                                            <h5 class="card-title">${c.name}</h5>
                                            <p class="card-text text-body flex-grow-1" style="min-height: 72px;">${c.description}</p>
                                        </div>


                                        <div class="card-footer bg-white border-top d-flex justify-content-between align-items-center">
                                            <span class="text-primary font-weight-bold">${c.fee} đ</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                            <div class="col-12 mt-3">
                                <a href="course" class="btn btn-primary py-md-2 px-md-4 font-weight-semi-bold">Xem Thêm</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>


            <!-- Courses End -->

            <div class="p-3 bg-light rounded shadow-sm">
                <h5><i class="fas fa-calendar-alt text-primary me-2"></i> Sự kiện sắp tới</h5>
                <hr>
                <c:forEach var="e" items="${eventList}">
                    <div class="bg-white p-2 my-2 rounded border">
                        <strong>${e.name}</strong> - 
                        ${e.date}
                    </div>
                </c:forEach>
            </div>

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
                                    <img class="img-fluid" src="BlogImageController?id=${n.id}" alt="Ảnh blog">

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
        </div>

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
        <!-- Sidebar Toggle Script -->
        <script>
            function toggleSidebar() {
                const sidebar = document.getElementById('sidebar');
                const mainContent = document.getElementById('main-content');
                const toggleBtn = document.querySelector('.toggle-btn');

                sidebar.classList.toggle('hidden');
                mainContent.classList.toggle('full');
                toggleBtn.classList.toggle('hidden');

                // Change icon based on sidebar state
                const icon = toggleBtn.querySelector('i');
                if (sidebar.classList.contains('hidden')) {
                    icon.classList.remove('fa-times');
                    icon.classList.add('fa-bars');
                } else {
                    icon.classList.remove('fa-bars');
                    icon.classList.add('fa-times');
                }
            }
        </script>
    </body>
</html>