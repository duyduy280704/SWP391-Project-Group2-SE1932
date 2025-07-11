<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, models.Schedules" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết thời khóa biểu lớp</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/startbootstrap-sb-admin@7.0.5/dist/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">

        <!-- Topbar -->
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="#">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle">
                <i class="fas fa-bars"></i>
            </button>
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
        <!-- Layout wrapper -->
        <div id="layoutSidenav">
            <!-- Sidebar -->
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
            <!-- Main content -->
            <div id="layoutSidenav_content">
                <main class="container-fluid px-4 mt-4">
                    <h2 class="mb-4">Thời khóa biểu lớp: <span class="text-primary">${className}</span></h2>

                    <!-- Hiển thị thông báo -->
                    <c:choose>
                        <c:when test="${not empty err}">
                            <div class="alert alert-danger">${err}</div>
                        </c:when>
                        <c:when test="${not empty msg}">
                            <div class="alert alert-info">${msg}</div>
                        </c:when>
                        <c:when test="${not empty sessionScope.msg}">
                            <div class="alert alert-info">${sessionScope.msg}</div>
                            <c:remove var="msg" scope="session" />
                        </c:when>
                        <c:when test="${not empty sessionScope.err}">
                            <div class="alert alert-danger">${sessionScope.err}</div>
                            <c:remove var="err" scope="session" />
                        </c:when>
                    </c:choose>

                    <!-- Form lọc ngày -->
                    <form class="row g-3 mb-4" action="scheduleByClass" method="get">
                        <input type="hidden" name="id" value="${classId}" />
                        <input type="hidden" name="name" value="${className}" />
                        <div class="col-auto">
                            <label class="col-form-label">Từ ngày:</label>
                        </div>
                        <div class="col-auto">
                            <input type="date" name="from" class="form-control" value="${param.from}">
                        </div>
                        <div class="col-auto">
                            <label class="col-form-label">Đến ngày:</label>
                        </div>
                        <div class="col-auto">
                            <input type="date" name="to" class="form-control" value="${param.to}">
                        </div>
                        <div class="col-auto">
                            <button type="submit" class="btn btn-success">Lọc</button>
                        </div>
                        <div class="col-auto">
                            <a href="scheduleByClass?id=${classId}&name=${className}&mode=1" class="btn btn-secondary">Xóa bộ lọc</a>
                        </div>
                    </form>

                    <!-- Form tìm kiếm -->
                    <form class="d-flex mb-3" action="scheduleByClass" method="get">
                        <input type="hidden" name="id" value="${classId}" />
                        <input type="hidden" name="name" value="${className}" />
                        <input type="hidden" name="mode" value="searchByClass" />
                        <input type="text" name="keyword" class="form-control me-2" placeholder="Nhập từ khóa (tên GV, phòng, ngày)..." value="${param.keyword}" />
                        <button type="submit" class="btn btn-primary">Tìm</button>
                    </form>

                    <!-- Bảng thời khóa biểu -->
                    <div class="card mb-4">
                        <div class="card-header"><i class="fas fa-table me-1"></i>Thời khóa biểu</div>
                        <div class="card-body">
                            <table class="table table-bordered table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>STT</th>
                                        <th>Giáo viên</th>
                                        <th>Ngày</th>
                                        <th>Bắt đầu</th>
                                        <th>Kết thúc</th>
                                        <th>Phòng</th>
                                        <th>Hành động</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${scheduleList}" varStatus="loop">
                                        <tr>
                                            <td>${loop.index + 1}</td>
                                            <td>${item.teacher}</td>
                                            <td>
                                                <fmt:parseDate value="${item.day}" pattern="yyyy-MM-dd" var="parsedDay" />
                                                <fmt:formatDate value="${parsedDay}" pattern="dd/MM/yyyy" />
                                            </td>
                                            <td>
                                                <fmt:parseDate value="${item.startTime}" pattern="HH:mm:ss" var="parsedStart" />
                                                <fmt:formatDate value="${parsedStart}" pattern="HH:mm" />
                                            </td>
                                            <td>
                                                <fmt:parseDate value="${item.endTime}" pattern="HH:mm:ss" var="parsedEnd" />
                                                <fmt:formatDate value="${parsedEnd}" pattern="HH:mm" />
                                            </td>
                                            <td>${item.room}</td>
                                            <td>
                                                <a href="scheduleByClass?sid=${item.id}&mode=2" class="btn btn-sm btn-warning">Sửa</a>
                                                <a href="javascript:void(0);" onclick="showDeleteConfirmation('${item.id}', '${classId}', '${className}')" class="btn btn-sm btn-danger">Xóa</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty scheduleList}">
                                        <tr><td colspan="7" class="text-center text-muted">Không có lịch học nào.</td></tr>
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
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script>
                                                    function showDeleteConfirmation(scheduleId, classId, className) {
                                                        Swal.fire({
                                                            title: 'Xác nhận xóa',
                                                            text: 'Bạn có chắc chắn muốn xóa lịch học này không?',
                                                            icon: 'warning',
                                                            showCancelButton: true,
                                                            confirmButtonText: 'Xác Nhận',
                                                            cancelButtonText: 'Hủy'
                                                        }).then((result) => {
                                                            if (result.isConfirmed) {
                                                                window.location.href = 'scheduleByClass?sid=' + scheduleId + '&id=' + classId + '&name=' + className + '&mode=3';
                                                            }
                                                        });
                                                    }
        </script>

    </body>
</html>