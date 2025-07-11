<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Tạo lớp học</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/startbootstrap-sb-admin@7.0.5/dist/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">

        <!-- Top nav -->
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="#">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle"><i class="fas fa-bars"></i></button>
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-bell"></i> Thông báo</a>
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

        <!-- Side nav -->
        <div id="layoutSidenav">
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
                            <a href="classTransfer" class="nav-link">📤Gửi đơn chuyển lớp  </a>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Đăng nhập với vai trò:</div>
                        Staff
                    </div>
                </nav>
            </div>

            <!-- Nội dung chính -->
            <div id="layoutSidenav_content">
                <main class="container-fluid px-4 mt-4">
                    <h2 class="mb-4">
                        <c:choose>
                            <c:when test="${not empty edit_id}">Chỉnh sửa lớp học</c:when>
                            <c:otherwise>Tạo lớp học mới</c:otherwise>
                        </c:choose>
                    </h2>

                    <c:if test="${not empty success}">
                        <div class="alert alert-success">${success}</div>
                    </c:if>

                    <!-- Form tạo hoặc sửa lớp -->
                    <form action="createClass" method="post" class="w-50">
                        <c:if test="${not empty edit_id}">
                            <input type="hidden" name="edit_id" value="${edit_id}" />
                        </c:if>

                        <div class="mb-3">
                            <label for="class_name" class="form-label">Tên lớp:</label>
                            <input type="text" id="class_name" name="class_name" class="form-control"
                                   value="${edit_name != null ? edit_name : ''}" required>
                        </div>

                        <c:if test="${empty edit_id}">
                            <div class="mb-3">
                                <label for="course_id" class="form-label">Chọn khóa học:</label>
                                <select id="course_id" name="course_id" class="form-select" required>
                                    <option value="" disabled selected>-- Chọn khóa học --</option>
                                    <c:forEach var="c" items="${courses}">
                                        <option value="${c.id}">${c.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </c:if>

                        <button type="submit" class="btn btn-primary">
                            <c:choose>
                                <c:when test="${not empty edit_id}">Lưu thay đổi</c:when>
                                <c:otherwise>Tạo lớp</c:otherwise>
                            </c:choose>
                        </button>
                    </form>
                    <form action="createClass" method="get" class="mb-3 w-50 mt-4">
                        <div class="input-group">
                            <input type="text" name="search" class="form-control" placeholder="Tìm theo tên lớp hoặc khóa học"
                                   value="${search != null ? search : ''}">
                            <button class="btn btn-primary" type="submit">Tìm kiếm</button>
                        </div>
                    </form>

                    <h4 class="mt-5">Danh sách lớp hiện có</h4>
                    <table class="table table-bordered table-striped">
                        <thead class="table-dark">
                            <tr>
                                <th>STT</th>
                                <th>Tên lớp</th>
                                <th>Khóa học</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="c" items="${classes}" varStatus="loop">
                                <tr>
                                    <td>${loop.index + 1}</td>
                                    <td>${c.name_class}</td>
                                    <td>${c.course_name}</td>
                                    <td>
                                        <div class="d-flex gap-2">
                                            <form action="createClass" method="get" class="d-inline">
                                                <input type="hidden" name="edit_id" value="${c.id_class}" />
                                                <input type="hidden" name="edit_name" value="${c.name_class}" />
                                                <button type="submit" class="btn btn-sm btn-warning">
                                                    <i class="fas fa-edit"></i> Sửa
                                                </button>
                                            </form>
                                            <!-- Nút Xóa -->
                                            <form action="createClass" method="post" class="d-inline"
                                                  onsubmit="return confirm('Bạn chắc chắn muốn xóa lớp này?');">
                                                <input type="hidden" name="delete_id" value="${c.id_class}" />
                                                <button type="submit" class="btn btn-sm btn-danger">
                                                    <i class="fas fa-trash-alt"></i> Xóa
                                                </button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty classes}">
                                <tr>
                                    <td colspan="4" class="text-center text-muted">Chưa có lớp học nào.</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </main>

                <!-- Footer -->
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