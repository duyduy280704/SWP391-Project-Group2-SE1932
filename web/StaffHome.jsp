<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <title>Danh sách lớp có thời khóa biểu</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">

        <!-- Topnav -->
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

        <!-- Sidenav -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <a class="nav-link" href="staffhome">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Trang Chủ
                            </a>
                            <a class="nav-link" href="classStudent">
                                <div class="sb-nav-link-icon"><i class="fas fa-chalkboard"></i></div>
                                Danh sách lớp
                            </a>
                            <a class="nav-link" href="listClassSchedule">
                                <div class="sb-nav-link-icon"><i class="fas fa-calendar-alt"></i></div>
                                Thời khóa biểu
                            </a>
                            <a class="nav-link" href="feedback">
                                <div class="sb-nav-link-icon"><i class="fas fa-comments"></i></div>
                                Phản hồi học viên
                            </a>
                        </div>
                    </div>
                </nav>
            </div>

            <!-- Main content -->
            <div id="layoutSidenav_content">
                <main class="container-fluid px-4 mt-4">
                    <h2 class="mb-4">Danh sách lớp có thời khóa biểu</h2>

                    <form method="post" action="listClassSchedule" class="d-flex mb-3">
                        <input type="text" name="search" class="form-control me-2" placeholder="Tìm kiếm lớp...">
                        <input type="hidden" name="action" value="search">
                        <button type="submit" class="btn btn-primary">Tìm</button>
                    </form>

                    <a href="listClassSchedule?mode=1" class="btn btn-success mb-3"><i class="fas fa-plus me-1"></i> Tạo thời khóa biểu</a>

                    <div class="card mb-4">
                        <div class="card-header"><i class="fas fa-table me-1"></i>Danh sách lớp</div>
                        <div class="card-body">
                            <table class="table table-bordered table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>Tên lớp</th>
                                        <th>Giáo viên</th>
                                        <th>Phòng</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="c" items="${classList}">
                                        <tr>
                                            <td>
                                                <a href="scheduleByClass?id_class=${c.id_class}&name=${c.nameClass}&mode=1" class="text-decoration-none text-dark">
                                                    ${c.nameClass}
                                                </a>
                                            </td>
                                            <td>${c.teacher}</td>
                                            <td>${c.room}</td>
                                            <td>
                                                <form method="post" action="listClassSchedule"
                                                      onsubmit="return confirm('Xóa toàn bộ thời khóa biểu của lớp này?');" class="d-inline">
                                                    <input type="hidden" name="classId" value="${c.id_class}">
                                                    <input type="hidden" name="action" value="delete">
                                                    <button type="submit" class="btn btn-danger btn-sm">
                                                        <i class="fas fa-trash-alt me-1"></i> Xóa
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty classList}">
                                        <tr><td colspan="4" class="text-center text-muted">Không có lớp nào có thời khóa biểu.</td></tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </main>

                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4 text-center">
                        <div class="text-muted">Copyright &copy; BIG DREAM 2025</div>
                    </div>
                </footer>
            </div>
        </div>

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/scripts.js"></script>

        
    </body>
</html>
