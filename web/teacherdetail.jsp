
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Danh sách giáo viên</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Teacher List" name="keywords">
    <meta content="List of teachers" name="description">

    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"> 

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Bootstrap Stylesheet -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Customized Bootstrap Stylesheet -->
    <link href="css/style.css" rel="stylesheet">

    <!-- Custom CSS for Teacher List and Sidebar -->
    <style>
        .blog-section {
            background-color: #f9f9f9;
            padding: 50px 0;
        }
        .card {
            background-color: #fff;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
            margin-bottom: 30px;
            transition: transform 0.2s;
            cursor: pointer;
        }
        .card:hover {
            transform: scale(1.05);
        }
        .blog-image {
            width: 100%;
            height: 150px;
            object-fit: cover;
            border-radius: 10px 0 0 10px;
        }
        .blog-content {
            padding: 20px;
        }
        .blog-title {
            font-size: 1.8rem;
            font-weight: 600;
            color: #222;
            margin-bottom: 10px;
            line-height: 1.3;
        }
        .blog-date {
            font-size: 0.85rem;
            color: #6c757d;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
        }
        .blog-date i {
            margin-right: 8px;
            color: #007bff;
        }
        .blog-content-text {
            font-size: 0.95rem;
            color: #444;
            line-height: 1.7;
            margin-bottom: 15px;
        }
        .no-blog {
            font-size: 1.2rem;
            color: #6c757d;
            text-align: center;
            padding: 50px 0;
            font-style: italic;
        }
        .modal-body img {
            max-width: 100%;
            height: auto;
            border-radius: 10px;
            margin-bottom: 15px;
        }
        .modal-title {
            font-size: 1.5rem;
            font-weight: 600;
        }
        .modal-date {
            font-size: 0.9rem;
            color: #6c757d;
            margin-bottom: 15px;
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
    <div class="container-fluid d-none d-lg-block">
        <div class="row align-items-center py-4 px-xl-5">
            <div class="col-lg-3">
                <!-- Logo placeholder if needed -->
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
    <!-- Sidebar Start -->
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
        <a href="teacherHome" class="nav-link active">Trang Chủ</a>
        <a href="scheduleTeacher" class="nav-link">Lịch dạy</a>
        <a href="attendance" class="nav-link">Điểm danh</a>
        <a href="classStudent" class="nav-link">Danh sách lớp học</a>
        <a href="logout" class="logout-btn">Đăng xuất</a>
    </div>
    <!-- Sidebar End -->
    <!-- Teacher List Start -->
    <div class="main-content" id="main-content">
        <div class="container blog-section">
            <div class="text-center mb-5">
                <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Giáo Viên</h5>
                <h1>Danh sách Giáo viên</h1>
            </div>
            <div class="row">
                <c:choose>
                    <c:when test="${not empty teacherList}">
                        <c:forEach var="teacher" items="${teacherList}">
                            <div class="col-lg-4 mb-4">
                                <div class="card h-100" data-bs-toggle="modal" data-bs-target="#teacherModal"
                                     data-id="${teacher.id}"
                                     data-name="${fn:escapeXml(teacher.name)}"
                                     data-email="${fn:escapeXml(teacher.email)}"
                                     data-gender="${teacher.gender}"
                                     data-birthdate="${teacher.birthdate}"
                                     data-exp="${fn:escapeXml(teacher.exp)}"
                                     data-year="${teacher.year}"
                                     data-course="${fn:escapeXml(teacher.course)}"
                                     data-phone="${teacher.phone}"
                                     data-pic="${not empty teacher.pic ? 'picteacher?id=' += teacher.id : ''}">
                                    <c:choose>
                                        <c:when test="${not empty teacher.pic}">
                                            <img src="picteacher?id=${teacher.id}" class="blog-image" alt="Ảnh giáo viên">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="text-center p-5 bg-light text-muted">Không có ảnh</div>
                                        </c:otherwise>
                                    </c:choose>
                                    <div class="blog-content">
                                        <h2 class="blog-title">${fn:escapeXml(teacher.name)}</h2>
                                        <p class="blog-date">
                                            <i class="fa fa-calendar-alt"></i>
                                            <fmt:parseDate value="${teacher.birthdate}" pattern="yyyy-MM-dd" var="parsedDate" />
                                            <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" />
                                        </p>
                                        <div class="blog-content-text">
                                            <p><strong>Email:</strong> ${fn:escapeXml(teacher.email)}</p>
                                            <p><strong>Giới tính:</strong> ${teacher.gender}</p>
                                            <p><strong>Kinh nghiệm:</strong> ${fn:escapeXml(teacher.exp)} - ${teacher.year} năm</p>
                                            <p><strong>Môn dạy:</strong> ${fn:escapeXml(teacher.course)}</p>
                                            <p><strong>Số điện thoại:</strong> ${teacher.phone}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="col-12 no-blog">
                            <p>Không có giáo viên nào.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <!-- Teacher List End -->
    <!-- Teacher Modal -->
    <div class="modal fade" id="teacherModal" tabindex="-1" aria-labelledby="teacherModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="teacherModalLabel">Chi tiết Giáo viên</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-md-4">
                            <img id="modal-pic" src="" class="img-fluid rounded" alt="Ảnh giáo viên" style="height: 100%; object-fit: cover;">
                            <div id="modal-no-pic" class="text-center p-5 bg-light text-muted d-none">Không có ảnh</div>
                        </div>
                        <div class="col-md-8">
                            <h3 id="modal-name"></h3>
                            <p><strong>Email:</strong> <span id="modal-email"></span></p>
                            <p><strong>Giới tính:</strong> <span id="modal-gender"></span></p>
                            <p class="modal-date" id="modal-birthdate"></p>
                            <p><strong>Kinh nghiệm:</strong> <span id="modal-exp"></span> - <span id="modal-year"></span> năm</p>
                            <p><strong>Môn dạy:</strong> <span id="modal-course"></span></p>
                            <p><strong>Số điện thoại:</strong> <span id="modal-phone"></span></p>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                </div>
            </div>
        </div>
    </div>
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
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="lib/easing/easing.min.js"></script>
    <script src="lib/owlcarousel/owl.carousel.min.js"></script>
    <script src="mail/jqBootstrapValidation.min.js"></script>
    <script src="mail/contact.js"></script>
    <script src="js/main.js"></script>
    <!-- JavaScript to handle modal and sidebar -->
    <script>
        $(document).ready(function() {
            $('#teacherModal').on('show.bs.modal', function (event) {
                var card = event.relatedTarget;
                var modal = $(this);

                // Populate modal with teacher data
                modal.find('#modal-id').text(card.getAttribute('data-id'));
                modal.find('#modal-name').text(card.getAttribute('data-name'));
                modal.find('#modal-email').text(card.getAttribute('data-email'));
                modal.find('#modal-gender').text(card.getAttribute('data-gender'));
                var birthdate = card.getAttribute('data-birthdate');
                modal.find('#modal-birthdate').text(new Date(birthdate).toLocaleDateString('vi-VN', { day: '2-digit', month: '2-digit', year: 'numeric' }));
                modal.find('#modal-exp').text(card.getAttribute('data-exp'));
                modal.find('#modal-year').text(card.getAttribute('data-year'));
                modal.find('#modal-course').text(card.getAttribute('data-course'));
                modal.find('#modal-phone').text(card.getAttribute('data-phone'));

                // Handle image
                var picSrc = card.getAttribute('data-pic');
                var modalPic = modal.find('#modal-pic');
                var modalNoPic = modal.find('#modal-no-pic');
                if (picSrc) {
                    modalPic.attr('src', picSrc).removeClass('d-none');
                    modalNoPic.addClass('d-none');
                } else {
                    modalPic.addClass('d-none');
                    modalNoPic.removeClass('d-none');
                }
            });
        });

        function toggleSidebar() {
            const sidebar = document.getElementById('sidebar');
            const mainContent = document.getElementById('main-content');
            const toggleBtn = document.querySelector('.toggle-btn');
            sidebar.classList.toggle('hidden');
            mainContent.classList.toggle('full');
            toggleBtn.classList.toggle('hidden');
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
