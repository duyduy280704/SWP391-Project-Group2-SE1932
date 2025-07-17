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
                                    <a href="StudentHome" class="nav-item nav-link active">Trang Chủ</a>
                                    <a href="Course" class="nav-item nav-link">Khóa Học</a>
                                    <a href="scheduleStudent" class="nav-item nav-link">Lịch Học</a>
                                    <a href="TeacherList" class="nav-item nav-link">Giáo Viên</a>
                                    <a href="classTransfer" class="nav-link">Xin Chuyển Lớp</a>
                                    <a href="StudentPayment" class="nav-item nav-link">Thanh Toán</a>
                                    <a href="studentapplication" class="nav-link">Gửi Đơn</a>
                                    <a href="feedback" class="nav-link">Phản Hồi Khóa Học</a>
                                    <a href="Notification" class="nav-item nav-link">Thông Báo</a>
                                    <a href="blog.jsp" class="nav-item nav-link">Tin Tức</a>
                                    <a href="#" class="nav-item nav-link">Sự Kiện</a> 
                                    <a href="logout" class="nav-item nav-link">Đăng Xuất</a>
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
                    <h3 class="display-4 text-white text-uppercase">Chi Tiết Khóa Học</h3>

                </div>
            </div>
        </div>
        <!-- Header End -->

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
                    <div class="mb-4">
                        <h5 class="font-weight-bold mb-2">Mô tả khóa học</h5>
                        <div class="text-justify" style="white-space: pre-wrap;">
                            ${course.description}
                        </div>
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



                        <input type="hidden" name="id" value="${course.id}">
                        <input type="hidden" name="saleCode" value="${saleCode}">
                        <div class="form-group mb-3">
                            <input type="text" class="form-control border-0 p-3" name="full_name"
                                   placeholder="Họ và tên"  />
                        </div>

                        <div class="form-group mb-3">
                            <input type="email" class="form-control border-0 p-3" name="email"
                                   placeholder="Email"  />
                        </div>

                        <div class="form-group mb-3">
                            <input type="text" class="form-control border-0 p-3" name="phone"
                                   placeholder="phone"  />
                        </div>

                        <div class="form-group mb-3">
                            <input type="date" class="form-control border-0 p-3" name="birth_date"
                                   placeholder="Ngày sinh"  />
                        </div>

                        <div class="form-group">
                            <select name="gender" class="custom-select border-0 px-4" style="height: 47px;" >
                                <option value="" disabled selected>Giới tính</option>
                                <option value="Nam">Nam</option>
                                <option value="Nữ">Nữ</option>
                            </select>
                        </div>

                        <div class="form-group mb-3">
                            <input type="text" class="form-control border-0 p-3" name="address"
                                   placeholder="Địa chỉ"  />
                        </div>

                        <div class="form-group mb-3">
                            <input type="text" class="form-control border-0 p-3" name="note"
                                   placeholder="ghi chú(lịch học mong muốn)"  />
                        </div>

                        <div class="form-check mb-4">
                            <input type="checkbox" class="form-check-input" id="agree" >
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