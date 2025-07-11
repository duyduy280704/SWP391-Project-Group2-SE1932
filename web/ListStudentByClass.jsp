//Thủy _ danh sách học sinh staff
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách học sinh</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/startbootstrap-sb-admin@7.0.5/dist/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">

        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="#">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#"><i class="fas fa-bars"></i></button>
        </nav>
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="staffhome">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle"><i class="fas fa-bars"></i></button>
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item">
                    <a class="nav-link" href="#" id="loadNotice"><i class="fas fa-bell"></i> Thông báo</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown">
                        <i class="fas fa-user fa-fw"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">Thông tin cá nhân</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="logout">Đăng xuất</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <!-- Sidebar đầy đủ -->
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Staff</div>
                            <a class="nav-link" href="staffhome"><div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>Trang Chủ</a>
                            <a class="nav-link" href="#"><div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>Biểu Đồ</a>
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Quản lý người dùng
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="student">Học Sinh</a>
                                    <a class="nav-link" href="teacher">Giáo Viên</a>
                                    <a class="nav-link" href="staff">Nhân Viên</a>
                                </nav>
                            </div>
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseClasses">
                                <div class="sb-nav-link-icon"><i class="fas fa-chalkboard-teacher"></i></div>
                                Quản lý lớp học
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseClasses" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="createClass">Tạo lớp mới</a>
                                    <a class="nav-link" href="classStudent">Danh sách lớp</a>
                                </nav>
                            </div>

                            <a class="nav-link" href="listClassSchedule"><i class="fas fa-calendar-alt me-2"></i> Thời khóa biểu</a>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseFeedback">
                                <div class="sb-nav-link-icon"><i class="fas fa-comments"></i></div>
                                Đánh giá
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseFeedback">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="feedback?mode=viewAll">Phản hồi của học viên</a>
                                    <a class="nav-link" href="feedbackByTeacher?mode=staffView">Giáo viên đánh giá học sinh</a>
                                </nav>
                            </div>
                            <a href="classTransfer" class="nav-link">Gửi đơn chuyển lớp  </a>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Đăng nhập với vai trò:</div>
                        Staff
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main class="container-fluid px-4 mt-4">
                    <h2 class="mb-4">Danh sách học sinh của lớp: <span class="text-primary">${className}</span></h2>

                    <c:if test="${not empty search}">
                        <div class="alert alert-info">
                            Kết quả tìm kiếm với từ khóa: <strong>${search}</strong> (${fn:length(students)} học sinh)
                        </div>
                    </c:if>

                    <form method="get" action="classStudent" class="mb-4 d-flex">
                        <input type="hidden" name="mode" value="students"/>
                        <input type="hidden" name="classId" value="${classId}"/>
                        <input type="text" name="search" value="${search}" placeholder="Tìm theo tên học sinh" class="form-control me-2" />
                        <input type="submit" value="Tìm kiếm" class="btn btn-primary"/>
                    </form>

                    <table class="table table-bordered table-hover">
                        <thead class="table-dark">
                            <tr>
                                <th>STT</th>
                                <th>Họ tên</th>
                                <th>Email</th>
                                <th>Ngày sinh</th>
                                <th>Giới tính</th>
                                <th>Địa chỉ</th>
                                <th>Số điện thoại</th> 
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="s" items="${students}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${s.name}</td>
                                    <td>${s.email}</td>
                                    <c:choose>
                                        <c:when test="${not empty s.birthdate}">
                                            <fmt:parseDate value="${s.birthdate}" pattern="yyyy-MM-dd" var="parsedDate" />
                                            <td><fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" /></td>
                                        </c:when>
                                        <c:otherwise>
                                            <td><span class="text-muted">Chưa có</span></td>
                                        </c:otherwise>
                                    </c:choose>
                                    <td>${s.gender}</td>
                                    <td>${s.address}</td>
                                    <td>${s.phone}</td> 
                                </tr>
                            </c:forEach>
                            <c:if test="${empty students}">
                                <tr><td colspan="7" class="text-center text-muted">Không có học sinh nào trong lớp.</td></tr>
                            </c:if>
                        </tbody>
                    </table>
                </main>

                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4 text-center">
                        <div class="text-muted">Copyright &copy; BIG DREAM 2025</div>
                    </div>
                </footer>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/scripts.js"></script>
    </body>
</html>