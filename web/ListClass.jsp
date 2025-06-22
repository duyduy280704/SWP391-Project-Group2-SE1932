<%@ page contentType="text/html;charset=UTF-8" language="java" %>


// MaiThuy_ danh sách lớp có trong trung tâm 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Danh sách lớp học</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://cdn.jsdelivr.net/npm/startbootstrap-sb-admin@7.0.5/dist/css/styles.css" rel="stylesheet" />
    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
</head>
<body class="sb-nav-fixed">

<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <a class="navbar-brand ps-3" href="#">BIG DREAM</a>
    <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#"><i class="fas fa-bars"></i></button>
</nav>

<div id="layoutSidenav">
    <!-- Sidebar trái -->
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
                    <a class="nav-link" href="feedback?mode=viewAll">
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
            <h2>Danh sách lớp học</h2>

            <form method="get" action="classStudent" class="mb-3">
                <input type="hidden" name="mode" value="list"/>
                <input type="text" name="search" value="${search}" placeholder="Tìm theo tên lớp" class="form-control w-25 d-inline-block me-2"/>
                <input type="submit" value="Tìm kiếm" class="btn btn-primary"/>
            </form>

            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Tên lớp</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="c" items="${classes}">
                        <tr>
                            <td>${c.id}</td>
                            <td>${c.name}</td>
                            <td><a href="classStudent?mode=students&classId=${c.id}" class="btn btn-sm btn-info">Xem học sinh</a></td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty classes}">
                        <tr><td colspan="3" class="text-center text-muted">Không có lớp học nào.</td></tr>
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