<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String role = (String) session.getAttribute("role");
    if (role == null || !"student".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
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
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
                font-size: 15px;
                font-family: "Segoe UI", sans-serif;
                background-color: #fff;
                border-radius: 8px;
                overflow: hidden;
                box-shadow: 0 0 10px rgba(0,0,0,0.08);
            }

            thead {
                background-color: #4a90e2;
                color: white;
                text-align: center;
            }

            th, td {
                padding: 10px 14px;
                text-align: center;
                border-bottom: 1px solid #eee;
            }

            tbody tr:hover {
                background-color: #f5faff;
            }

            tbody tr td:first-child {
                font-weight: bold;
                color: #333;
            }

            td[colspan="6"] {
                color: #999;
                font-style: italic;
            }

            @media screen and (max-width: 768px) {
                table {
                    font-size: 13px;
                }

                th, td {
                    padding: 8px;
                }
            }
            /* Thêm vào phần <style> trong file JSP hoặc file CSS riêng */
            .text-justify {
                text-align: justify; /* Giữ nguyên thuộc tính hiện tại */
                max-height: 200px; /* Giới hạn chiều cao tối đa, điều chỉnh theo nhu cầu */
                overflow-y: auto; /* Thêm thanh cuộn dọc khi nội dung vượt quá */
                padding: 10px; /* Thêm padding để nội dung dễ đọc hơn */
                border: 1px solid #eee; /* Tùy chọn: thêm viền để phân biệt */
                border-radius: 5px; /* Tùy chọn: bo góc */
            }

            /* Responsive cho thiết bị nhỏ */
            @media (max-width: 768px) {
                .text-justify {
                    max-height: 150px; /* Giảm chiều cao trên mobile */
                    font-size: 14px; /* Giảm kích thước chữ nếu cần */
                }
            }

            /* Đảm bảo container không bị tràn */
            .col-md-6 {
                word-wrap: break-word; /* Ngắt từ dài */
                overflow-wrap: break-word; /* Hỗ trợ ngắt từ trên các trình duyệt */
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
                    <c:choose>
                        <c:when test="${not empty profile and not empty profile.pic}">
                            <a href="profile" class="profile-avatar">
                                <img src="${pageContext.request.contextPath}/profile?mode=image&id=${profile.id}&role=${role}" alt="Profile Avatar" class="profile-image">
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="profile" class="profile-avatar">
                                <img src="${pageContext.request.contextPath}/img/default-avatar.jpg" alt="Default Avatar" class="profile-image">
                            </a>
                        </c:otherwise>
                    </c:choose>
                    <div class="profile-name">
                        <c:choose>
                            <c:when test="${not empty profile and not empty profile.name}">
                                ${profile.name}
                            </c:when>
                            <c:otherwise>
                                Tên không xác định
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

            <a href="StudentHome" class="nav-item nav-link ">Trang Chủ</a>
            <a href="Course" class="nav-item nav-link active">Khóa Học</a>
            <a href="scheduleStudent" class="nav-item nav-link">Lịch Học</a>
            <a href="TeacherList" class="nav-item nav-link">Giáo Viên</a>
            <a href="StudentPayment" class="nav-item nav-link">Thanh Toán</a>
            <a href="studentapplication" class="nav-link">Gửi Đơn</a>
            <a href="feedback" class="nav-link">Phản Hồi Khóa Học</a>
            <a href="Notification" class="nav-item nav-link">Thông Báo</a>
            <a href="blog.jsp" class="nav-item nav-link">Tin Tức</a>
            <a href="#" class="nav-item nav-link">Sự Kiện</a> 
            <a href="logout" class="nav-item nav-link">Đăng Xuất</a>
        </div>
        <!-- sidebar End -->

        <!-- Main Content -->
        <div class="main-content" id="main-content">

            <div class="container py-5">
                <div class="row">
                    <!-- Ảnh -->
                    <div class="col-md-6 mb-4">
                        <c:choose>
                            <c:when test="${not empty course.image}">
                                <img src="image?id=${course.id}" class="img-fluid rounded shadow"
                                     alt="Course Image"
                                     style="width: 100%; height: 400px; object-fit: cover;"
                                     onerror="this.src='images/no-image.png';">
                            </c:when>
                            <c:otherwise>
                                <img src="images/no-image.png" class="img-fluid rounded shadow"
                                     alt="No Image"
                                     style="width: 100%; height: 400px; object-fit: cover;">
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <!-- Thông tin -->
                    <div class="col-md-6">
                        <h2 class="text-dark font-weight-bold">${course.name}</h2>
                        <p class="text-muted mb-2"><strong>Loại:</strong> ${course.type}</p>
                        <p class="text-muted mb-2"><strong>Cấp độ:</strong> ${course.level}</p>
                        <p class="text-muted mb-2"><strong>Số lượng buổi học:</strong> ${course.number}</p>
                        <hr>

                        <!-- ✅ Học phí + áp dụng mã giảm giá -->
                        <div class="mb-3">
                            <c:choose>
                                <c:when test="${not empty salePercent && salePercent > 0}">
                                    <span class="text-muted"><del>
                                            <fmt:formatNumber value="${course.fee}" type="number" maxFractionDigits="0" /> đ
                                        </del></span>
                                    <br>
                                    <span class="text-success">Đã áp dụng mã giảm ${salePercent}%</span><br>
                                    <span class="text-primary h5 font-weight-bold">
                                        <fmt:formatNumber value="${course.fee * (100 - salePercent) / 100}" type="number" maxFractionDigits="0" /> đ
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-primary h5 font-weight-bold">
                                        <fmt:formatNumber value="${course.fee}" type="number" maxFractionDigits="0" /> đ
                                    </span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <c:if test="${not empty saleMessage}">
                            <div class="text-danger">${saleMessage}</div>
                        </c:if>
                        <!-- ✅ Nhập mã khuyến mãi -->
                        <form action="RegistrationCourse" method="get" class="form-inline">
                            <input type="hidden" name="id" value="${course.id}" />
                            <div class="input-group mb-3">
                                <input type="text" class="form-control" name="saleCode" placeholder="Nhập mã giảm giá"
                                       value="${saleCode}">
                                <div class="input-group-append">
                                    <button class="btn btn-outline-primary" type="submit">Áp dụng</button>
                                </div>
                            </div>
                        </form>

                        <!-- Mô tả -->
                        
                    </div>
                                <div class="mb-4">
                            <h5 class="font-weight-bold mb-2">Mô tả khóa học</h5>
                            <div class="text-justify" >
                                ${course.description}
                            </div>
                        </div>
                </div>
            </div>



            <div id="register-form" style="margin-top:15px;">
                <div class="card shadow-sm mx-auto" style="max-width:480px;">   <!-- căn giữa, giới hạn rộng -->
                    <div class="card-body p-4">     
                        <c:if test="${not empty message}">
                            <div class="alert alert-success">${message}</div>
                        </c:if>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger">${error}</div>
                        </c:if><!-- padding đều 1rem -->
                        <form action="RegistrationCourse" method="get">
                            <input type="hidden" name="full_name" value="${account.name}">
                            <input type="hidden" name="id" value="${course.id}">
                            <input type="hidden" name="saleCode" value="${saleCode}">                      

                            <div class="form-group mb-3">
                                <input type="email" class="form-control border-0 p-3" name="email"
                                       placeholder="Email"  />
                            </div>
                            <div class="form-group mb-3">
                                <input type="text" class="form-control border-0 p-3" name="note"
                                       placeholder="ghi chú(lịch học mong muốn)"  />
                            </div>

                            <div class="form-check mb-4">
                                <input type="checkbox" class="form-check-input" id="agree" name="agree">
                                <label class="form-check-label" for="agree">
                                    Tôi đồng ý với
                                    <a href="#" data-toggle="modal" data-target="#termsModal">điều khoản</a>
                                </label>
                            </div>

                            <button type="submit" class="btn btn-success btn-block">
                                Xác nhận đăng ký
                            </button>
                        </form>
                    </div>
                </div>
            </div>



            <!-- Modal Điều khoản -->
            <div class="modal fade" id="termsModal" tabindex="-1" role="dialog" aria-labelledby="termsModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-scrollable modal-lg" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="termsModalLabel">Điều khoản và Cam kết</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Đóng">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <p><strong>1. Cam kết học viên:</strong></p>
                            <ul>
                                <li>Tham gia đầy đủ và đúng giờ các buổi học.</li>
                                <li>Không gây rối hoặc làm ảnh hưởng đến lớp học.</li>
                                <li>Tuân thủ các quy định của trung tâm.</li>
                            </ul>

                            <p><strong>2. Hoàn phí và hủy đăng ký:</strong></p>
                            <ul>
                                <li>Không hoàn lại học phí sau khi đã bắt đầu khóa học.</li>
                                <li>Nếu hủy trước ngày khai giảng, hoàn lại 80% học phí.</li>
                            </ul>

                            <p><strong>3. Quyền lợi học viên:</strong></p>
                            <ul>
                                <li>Được cung cấp tài liệu học miễn phí.</li>
                                <li>Được hỗ trợ kỹ thuật và tư vấn trong suốt khóa học.</li>
                            </ul>

                            <p class="text-muted mt-3">Mọi thắc mắc xin liên hệ: <strong>0123 456 789</strong> hoặc email: <strong>support@trungtam.com</strong></p>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
                        </div>
                    </div>
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
        <script>
            function toggleRegisterForm() {
                var form = document.getElementById('register-form');
                if (form.style.display === "none") {
                    form.style.display = "block";
                } else {
                    form.style.display = "none";
                }
            }

            function showContract() {
                alert("Hiển thị popup điều khoản ở đây hoặc mở modal.");
                // Hoặc dùng Bootstrap modal nếu có sẵn
            }
        </script>
    </body>
</html>