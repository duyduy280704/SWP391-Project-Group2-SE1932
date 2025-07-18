<%-- 
    Document   : TeacherApplication
    Created on : Jul 6, 2025, 10:07:33 PM
    Author     : Quang
--%>

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


            @media (max-width: 768px) {
                .course-list-table {
                    margin: 15px 0;
                    font-size: 13px;
                }
                .course-list-table th, .course-list-table td {
                    padding: 10px;
                }
            }

            form select, form input[type="text"] {
                width: 100%;
                padding: 6px;
                box-sizing: border-box;
            }

            form input[type="submit"] {
                padding: 6px 12px;
                margin-right: 5px;
            }

            .table thead th {
                padding: 12px;
                text-align: center;
            }

            .table tbody td {
                vertical-align: middle;
                padding: 12px;
                text-align: center;
            }

            .testimonial-img {
                width: 50px;
                height: 50px;
                border-radius: 50%;
                object-fit: cover;
                border: 2px solid #FF6600;
            }

            .table-striped tbody tr:nth-of-type(odd) {
                background-color: #f8f9fa;
            }

            .table-hover tbody tr:hover {
                background-color: #e9ecef;
                transition: background-color 0.3s ease;
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
                overflow-y: auto;
                width: 250px;
                background-color: #ffffff;
                padding-top: 60px;
                display: flex;
                flex-direction: column;
                transition: transform 0.3s ease-in-out;
                z-index: 1000;
            }

            .sidebar.hidden {
                transform: translateX(-250px);
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
                margin-left: 250px;
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
            .application-card {
                max-width: 1110px;
                margin: 30px auto;
                border-radius: 10px;
            }

            .application-card .card-header {
                text-align: center;
                border-bottom: 1px solid #eee;
            }

            .application-card .form-label {
                font-weight: 500;
            }


            .success {
                color: green;
            }
            .error {
                color: red;
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
            <!-- Topbar End -->

            <!-- Navbar Start -->
            <div class="sidebar" id="sidebar">
                <div class="col-lg-3">
                    <a href="" class="text-decoration-none">
                        <h1 class="m-0"><span class="text-primary">BIG</span>DREAM</h1>
                    </a>
                </div>
                <div class="profile-container">
                    <c:set var="picturePath" value="${sessionScope.picturePath}" />
                    <c:choose>
                        <c:when test="${not empty picturePath}">
                            <a href="profile" class="profile-avatar">
                                <img src="${pageContext.request.contextPath}/${picturePath}" alt="Profile Avatar">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="profile" class="profile-avatar">
                                <img src="${pageContext.request.contextPath}/img/default-avatar.jpg" alt="Default Avatar">
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <div class="profile-name">${sessionScope.account.name}</div>
                </div>

                <a href="teacherHome" class="nav-link active">Trang Chủ</a>
                <a href="scheduleTeacher" class="nav-link ">Lịch dạy</a>
                <a href="classStudent" class="nav-link">Danh sách lớp học</a>
                <a href="feedbackByTeacher" class="nav-item nav-link">Dánh giá sinh viên </a>
                <a href="salaryteacher" class="nav-item nav-link">Bảng Lương </a>
                <a href="teacherapplication" class="nav-item nav-link">Gửi Đơn </a>
                <a href="feedback?mode=viewAll" class="nav-link"> Xem phản hồi  </a>
                <a href="#" class="nav-link"> Tin Tức </a>
                <a href="#" class="nav-link"> Sự Kiện</a>
                <a href="logout" class="logout-btn">Đăng xuất</a>
            </div>
            <!-- Navbar End -->

            <!-- Main Content -->
            <div class="main-content" id="main-content">
                <!-- Content --> 
                <div class="card shadow application-card">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Nộp Đơn Từ Giáo Viên</h5>
                    </div>
                    <div class="card-body">
                        <form method="post" action="teacherapplication">
                            <div class="mb-3">
                                <label for="applicationType" class="form-label">Loại đơn</label>
                                <select id="applicationType" name="application" class="form-select">
                                    <option value="0">-- Chọn loại đơn --</option>
                                    <c:forEach items="${data}" var="c">
                                        <option value="${c.getId()}">${c.getName()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="mb-3">
                                <label for="content" class="form-label">Nội dung đơn</label>
                                <textarea id="content" name="content" rows="4" class="form-control" placeholder="Nhập nội dung đơn..."></textarea>
                                <c:if test="${not empty message}">
                                    <p class="${success ? 'success' : 'error'}">${message}</p>
                                </c:if>
                            </div>
                            <div class="text-center">
                                <button type="submit" name="add" class="btn btn-primary">Gửi đơn</button>
                            </div>
                        </form>
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
    </body>
</html>