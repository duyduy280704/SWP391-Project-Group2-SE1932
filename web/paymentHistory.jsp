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
            .text-orange {
                color: #fd7e14;
            }
            .bg-orange {
                background-color: #fd7e14;
                color: white;
            }
            .btn-orange {
                background-color: #fd7e14;
                color: white;
                border: none;
            }
            .btn-orange:hover {
                background-color: #e96b10;
                color: white;
            }
            .border-orange {
                border-color: #fd7e14 !important;
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
            <a href="Course" class="nav-item nav-link">Khóa Học</a>
            <a href="scheduleStudent" class="nav-item nav-link">Lịch Học</a>
            <a href="TeacherList" class="nav-item nav-link">Giáo Viên</a>
            <a href="StudentPayment" class="nav-item nav-link active">Thanh Toán</a>
            <a href="studentapplication" class="nav-link">Gửi Đơn</a>
            <a href="feedback" class="nav-link">Phản Hồi Khóa Học</a>
            <a href="Notification" class="nav-item nav-link">Thông Báo</a>
            <a href="BlogStudent" class="nav-item nav-link">Tin Tức</a>
            <a href="EventStudent" class="nav-item nav-link">Sự Kiện</a> 
            <a href="logout" class="nav-item nav-link">Đăng Xuất</a>
        </div>
        <!-- sidebar End -->

        <!-- Main Content -->
        <div class="main-content" id="main-content">

            <div class="container my-5">
                <h2 class="mb-4 text-orange">📋 Lịch sử thanh toán khóa học</h2>

                <!-- 🔍 Tìm kiếm -->
                <form method="get" action="StudentPayment" class="row g-3 mb-5">
                    <div class="col-md-4">
                        <input type="text" name="keyword" class="form-control" placeholder="🔍 Tên khóa học" value="${param.keyword}">
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-orange w-100">Tìm kiếm</button>
                    </div>
                </form>

                <!-- 💸 Khóa học CHƯA thanh toán -->
                <h4 class="text-orange mb-3">🕗 Khóa học chưa thanh toán</h4>
                <c:if test="${empty unpaidList}">
                    <div class="alert alert-info">Không có đơn nào cần thanh toán.</div>
                </c:if>

                <div class="row row-cols-1 row-cols-md-2 g-4 mb-5">
                    <c:forEach var="p" items="${unpaidList}">
                        <div class="col">
                            <div class="card h-100 border border-orange shadow-sm">
                                <div class="card-body">
                                    <h5 class="card-title text-orange fw-bold">${p.courseName}</h5>
                                    <ul class="list-unstyled mb-3">
                                        <li><strong>Giá gốc:</strong> ${p.courseFee} VND</li>
                                        <li><strong>Giảm giá:</strong> ${p.salePercent}%</li>
                                        <li><strong>Phải thanh toán:</strong> <span class="text-danger fw-bold">${p.finalAmount} VND</span></li>
                                        <li><strong>Mã đơn:</strong> ${p.orderCode}</li>
                                        <li><strong>Ngày tạo:</strong> ${p.date}</li>
                                        <li><strong>Trạng thái:</strong>
                                            <span class="badge
                                                  ${p.status == 'Chưa thanh toán' ? 'bg-warning text-dark' :
                                                    (p.status == 'Đã chuyển khoản' ? 'bg-info' : 'bg-success')}">
                                                      ${p.status}
                                                  </span>
                                            </li>
                                        </ul>

                                        <!-- Nút mở modal hướng dẫn -->
                                        <button type="button" class="btn btn-outline-orange btn-sm mb-2" data-bs-toggle="modal" data-bs-target="#paymentGuideModal_${p.orderCode}">
                                            💰 Xem hướng dẫn chuyển khoản
                                        </button>

                                        <!-- Nút "Tôi đã chuyển khoản" -->
                                        <c:if test="${p.status == 'Chưa thanh toán'}">
                                            <form action="StudentPayment" method="post">
                                                <input type="hidden" name="orderCode" value="${p.orderCode}">
                                                <button type="submit" class="btn btn-orange btn-sm">✅ Tôi đã chuyển khoản</button>
                                            </form>
                                        </c:if>
                                    </div>
                                </div>
                            </div>

                            <!-- Modal hướng dẫn chuyển khoản -->
                            <div class="modal fade" id="paymentGuideModal_${p.orderCode}" tabindex="-1" aria-labelledby="guideModalLabel_${p.orderCode}" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <div class="modal-header bg-orange">
                                            <h5 class="modal-title" id="guideModalLabel_${p.orderCode}">💸 Hướng dẫn chuyển khoản</h5>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Đóng"></button>
                                        </div>
                                        <div class="modal-body">
                                            <p>📌 Vui lòng chuyển khoản đến thông tin sau:</p>
                                            <ul class="list-unstyled">
                                                <li><strong>Ngân hàng:</strong> Vietcombank</li>
                                                <li><strong>Chủ tài khoản:</strong> Trung tâm BigDream</li>
                                                <li><strong>Số tài khoản:</strong> 123456789</li>
                                                <li><strong>Nội dung:</strong> <span class="fw-bold text-danger">Thanh toan ${p.orderCode}</span></li>
                                            </ul>
                                            <p class="mt-2 text-muted">⚠️ Lưu ý: Sau khi chuyển khoản, bấm nút "Tôi đã chuyển khoản" để xác nhận.</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- ✅ Khóa học ĐÃ thanh toán -->
                    <h4 class="text-success mb-3">✅ Khóa học đã thanh toán</h4>
                    <c:if test="${empty paidList}">
                        <div class="alert alert-info">Bạn chưa có thanh toán nào được ghi nhận.</div>
                    </c:if>

                    <div class="row row-cols-1 row-cols-md-2 g-4">
                        <c:forEach var="p" items="${paidList}">
                            <div class="col">
                                <div class="card border-success h-100 shadow-sm">
                                    <div class="card-body">
                                        <h5 class="card-title text-success fw-bold">${p.courseName}</h5>
                                        <ul class="list-unstyled mb-3">
                                            <li><strong>Số tiền đã thanh toán:</strong> <span class="text-success fw-bold">${p.finalAmount} VND</span></li>
                                            <li><strong>Mã đơn:</strong> ${p.orderCode}</li>
                                            <li><strong>Ngày thanh toán:</strong> ${p.date}</li>
                                            <li><strong>Trạng thái:</strong> <span class="badge bg-success">${p.status}</span></li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
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
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>
    </html>