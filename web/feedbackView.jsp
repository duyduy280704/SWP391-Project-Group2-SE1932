//Thủy_ viewfeedback  staff xem
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Danh sách phản hồi từ học sinh</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/startbootstrap-sb-admin@7.0.5/dist/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">

        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="#">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle"><i class="fas fa-bars"></i></button>
        </nav>

        <div id="layoutSidenav">
            <!-- Sidebar đầy đủ -->
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Staff</div>

                            <a class="nav-link" href="staffhome">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Trang Chủ
                            </a>

                            <a class="nav-link" href="classStudent">
                                <i class="fas fa-chalkboard me-2"></i> Danh sách lớp
                            </a>
                            <a class="nav-link" href="listClassSchedule">
                                <i class="fas fa-calendar-alt me-2"></i> Thời khóa biểu
                            </a>
                            <a class="nav-link" href="feedback?mode=viewAll">
                                <i class="fas fa-comments me-2"></i> Phản hồi học viên
                            </a>

                            <a class="nav-link" href="coursestaff">
                                <div class="sb-nav-link-icon"><i class="fas fa-book"></i></div>
                                Quản lý khóa học
                            </a>

                            <a class="nav-link" href="#">
                                <div class="sb-nav-link-icon"><i class="fas fa-calendar-week"></i></div>
                                Quản lý sự kiện
                            </a>

                            <a class="nav-link" href="#">
                                <div class="sb-nav-link-icon"><i class="fas fa-blog"></i></div>
                                Quản lý blog
                            </a>

                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts"
                               aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-users"></i></div>
                                Quản lý đăng ký
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="#">Học sinh</a>
                                    <a class="nav-link" href="#">Giáo viên</a>
                                </nav>
                            </div>

                            <a class="nav-link" href="#">
                                <div class="sb-nav-link-icon"><i class="fas fa-bell"></i></div>
                                Gửi thông báo
                            </a>

                            <a class="nav-link text-danger" href="logout.jsp">
                                <i class="fas fa-sign-out-alt me-2"></i> Đăng xuất
                            </a>
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
                    <h2 class="mb-4">Danh sách phản hồi từ học sinh</h2>

                    <c:if test="${empty feedbackList}">
                        <div class="alert alert-info">Không có phản hồi nào.</div>
                    </c:if>

                    <c:if test="${not empty feedbackList}">
                        <div class="card mb-4">
                            <form class="row mb-3" method="get" action="feedback">
                                <input type="hidden" name="mode" value="viewAll"/>
                                <div class="col-md-4">
                                    <input type="text" name="keyword" class="form-control" placeholder="Tìm kiếm ..." value="${param.keyword}">
                                </div>
                                <div class="col-md-2">
                                    <button type="submit" class="btn btn-primary"><i class="fas fa-search me-1"></i> Tìm kiếm</button>
                                </div>
                            </form>

                            <div class="card-header"><i class="fas fa-comments me-1"></i>Danh sách phản hồi</div>
                            <div class="card-body">
                                <table class="table table-bordered table-hover">
                                    <thead class="table-light">
                                        <tr>
                                            <th>STT</th>
                                            <th>Học sinh</th>
                                            <th>Lớp</th>
                                            <th>Nội dung</th>
                                            <th>Ngày phản hồi</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="fb" items="${feedbackList}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td>${fb.studentName}</td>
                                                <td>${fb.className}</td>
                                                <td>${fb.feedbackText}</td>
                                                <td>${fb.feedbackDate}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </c:if>
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
