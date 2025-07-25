<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="models.Event"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <meta charset="UTF-8">
        <title>Danh sách sự kiện</title>
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
        <!-- Bootstrap Stylesheet -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
        <style>
            .event-card {
                transition: transform 0.2s;
                cursor: pointer;
            }
            .event-card:hover {
                transform: scale(1.02);
                box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            }
            .event-image {
                max-height: 150px;
                object-fit: cover;
            }
            .card-img-container {
                height: 150px;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f8f9fa;
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
                width: 250px;
                overflow-y: auto;
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
            .modal {
                display: none;
                position: fixed;
                z-index: 1002;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.4);
            }
            .modal-content {
                background-color: #fefefe;
                margin: 10% auto;
                padding: 20px;
                border: 1px solid #888;
                width: 80%;
                max-width: 700px;
                border-radius: 5px;
            }
            .close {
                color: #aaa;
                float: right;
                font-size: 28px;
                font-weight: bold;
                cursor: pointer;
            }
            .close:hover,
            .close:focus {
                color: black;
                text-decoration: none;
                cursor: pointer;
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
            <div class="row align-items-center py-4 px-xl-5 justify-content-end">
                <div></div>
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
            <a href="StudentPayment" class="nav-item nav-link">Thanh Toán</a>
            <a href="studentapplication" class="nav-link">Gửi Đơn</a>
            <a href="feedback" class="nav-link">Phản Hồi Khóa Học</a>
            <a href="Notification" class="nav-item nav-link">Thông Báo</a>
            <a href="BlogStudent" class="nav-item nav-link">Tin Tức</a>
            <a href="#" class="nav-item nav-link active">Sự Kiện</a> 
            <a href="logout" class="nav-item nav-link">Đăng Xuất</a>
        </div>
        <!-- Navbar End -->
        <!-- Main Content -->
        <div class="main-content" id="main-content">
            <!-- Recent Events Start -->
            <div class="container-fluid py-5">
                <div class="container pt-5 pb-3">
                    <div class="text-center mb-5 sukien">
                        <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Sự Kiện</h5>
                        <h1>Danh sách sự kiện</h1>
                    </div>
                    <c:if test="${empty eventList}">
                        <p class="text-center text-muted">Không có sự kiện nào để hiển thị!</p>
                    </c:if>
                    <c:if test="${not empty eventList}">
                        <div class="event-list row">
                            <c:forEach var="e" items="${eventList}">
                                <div class="col-md-4 event-list-item mb-4" 
                                     data-id="${e.id}"
                                     data-name="${fn:escapeXml(e.name)}"
                                     data-content="<c:out value='${e.content}' escapeXml='true'/>"
                                     data-date="${e.date}"

                                     <c:if test="${not empty e.img}">
                                         data-img="imageevent?id=${e.id}"
                                     </c:if>

                                     data-courseid="${fn:escapeXml(e.courseid)}">
                                    <div class="event-card bg-light rounded p-3 shadow-sm">
                                        <div class="card-img-container">
                                            <c:choose>
                                                <c:when test="${not empty e.img}">
                                                    <img src="imageevent?id=${e.id}" alt="Ảnh sự kiện" class="card-img-top event-image" loading="lazy">
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="text-muted">Không có ảnh</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <h4 class="event-title text-primary mt-3">${e.name}</h4>
                                        <p class="event-date text-muted mb-2">
                                            <i class="fa fa-calendar-alt mr-2"></i>
                                            <fmt:parseDate value="${e.date}" pattern="yyyy-MM-dd" var="parseDate"/>
                                            <fmt:formatDate value="${parseDate}" pattern="dd/MM/yyyy"/>
                                        </p>
                                        <p class="event-content">${fn:substring(e.content, 0, 30)}...</p>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
            </div>
            <!-- Recent Events End -->
            <!-- Event Details Modal -->
            <div id="eventModal" class="modal">
                <div class="modal-content">
                    <span class="close" onclick="closeEventModal()">×</span>
                    <h2 id="eventTitle"></h2>
                    <p id="eventDate" class="text-muted"></p>
                    <div class="card-img-container mb-3">
                        <img id="eventImage" class="event-image" src="" alt="Event Image" style="display: none;">
                        <span id="noImage" class="text-muted" style="display: none;">Không có ảnh</span>
                    </div>
                    <p id="eventContent"></p>
                    <p id="eventCourseId" class="text-muted"></p>
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

        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <!-- Contact Javascript File -->
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>
        <!-- Template Javascript -->
        <script src="js/main.js"></script>
        <!-- Bootstrap 5 Script -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
        <script>
                        document.querySelectorAll('.event-list-item').forEach(card => {
                            card.addEventListener('click', () => {
                                try {
                                    const id = card.dataset.id;
                                    const name = card.dataset.name;
                                    const content = card.dataset.content;
                                    const date = card.dataset.date;
                                    const imgSrc = card.dataset.img;
                                    const courseId = card.dataset.courseid;

                                    const modal = document.getElementById('eventModal');
                                    const eventTitle = document.getElementById('eventTitle');
                                    const eventDate = document.getElementById('eventDate');
                                    const eventImage = document.getElementById('eventImage');
                                    const noImage = document.getElementById('noImage');
                                    const eventContent = document.getElementById('eventContent');
                                    const eventCourseId = document.getElementById('eventCourseId');

                                    eventTitle.textContent = name || 'Không có tiêu đề';
                                    eventContent.innerHTML = content || '<i>Không có nội dung</i>';
                                    eventCourseId.textContent = courseId ? 'Khóa học: ' + courseId : 'Không có khóa học';

                                    const parsedDate = new Date(date);
                                    eventDate.textContent = parsedDate.toLocaleDateString('vi-VN', {
                                        day: '2-digit',
                                        month: '2-digit',
                                        year: 'numeric'
                                    }) || 'Không có ngày';

                                    if (imgSrc) {
                                        eventImage.src = imgSrc;
                                        eventImage.style.display = 'block';
                                        noImage.style.display = 'none';
                                    } else {
                                        eventImage.style.display = 'none';
                                        noImage.style.display = 'block';
                                    }

                                    modal.style.display = 'block';
                                } catch (error) {
                                    console.error('Error displaying event details:', error);
                                    alert('Có lỗi xảy ra khi hiển thị chi tiết sự kiện.');
                                }
                            });
                        });

                        function closeEventModal() {
                            const modal = document.getElementById('eventModal');
                            modal.style.display = 'none';
                        }

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

                        // Close modal when clicking outside
                        window.onclick = function (event) {
                            const modal = document.getElementById('eventModal');
                            if (event.target === modal) {
                                modal.style.display = 'none';
                            }
                        }
        </script>
    </body>
</html>