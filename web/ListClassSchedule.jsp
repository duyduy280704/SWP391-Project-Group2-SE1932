<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Danh sách lớp có thời khóa biểu</title>
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
    <div id="layoutSidenav_nav">
        <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
            <div class="sb-sidenav-menu">
                <div class="nav">
                    <div class="sb-sidenav-menu-heading">Staff</div>
                    <a class="nav-link" href="classStudent">
                        <i class="fas fa-chalkboard me-2"></i> Danh sách lớp
                    </a>
                    <a class="nav-link" href="listClassSchedule">
                        <i class="fas fa-calendar-alt me-2"></i> Thời khóa biểu
                    </a>
                    <a class="nav-link" href="feedback">
                        <i class="fas fa-comments me-2"></i> Phản hồi học viên
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
                                <th>Thao tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${classList}">
                                <tr>
                                    <td>
                                        <a href="scheduleByClass?id=${c.id_class}&name=${c.name_class}&mode=1" class="text-decoration-none text-dark">
                                            ${c.name_class}
                                        </a>
                                    </td>
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
                                <tr><td colspan="2" class="text-center text-muted">Không có lớp nào có thời khóa biểu.</td></tr>
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="js/scripts.js"></script>
</body>
</html>