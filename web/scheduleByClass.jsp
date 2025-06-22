<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, models.Schedules" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Chi tiết thời khóa biểu lớp</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="https://cdn.jsdelivr.net/npm/startbootstrap-sb-admin@7.0.5/dist/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">


        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="#">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle">
                <i class="fas fa-bars"></i>
            </button>
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
                            <a class="nav-link" href="feedback?mode=viewAll">
                                <i class="fas fa-comments me-2"></i> Phản hồi học viên
                            </a>
                            <a class="nav-link text-danger" href="logout.jsp">
                                <i class="fas fa-sign-out-alt me-2"></i> Đăng xuất
                            </a>
                        </div>
                    </div>
                    <div class="sb-sidenav-footer">
                        <div class="small">Logged in as:</div>
                        Nhân viên
                    </div>
                </nav>
            </div>


            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4 mt-4">
                        <h2 class="mb-4">Thời khóa biểu lớp: <span class="text-primary">${className}</span></h2>

                        <c:if test="${not empty msg}">
                            <div class="alert alert-info">${msg}</div>
                        </c:if>
                        <c:if test="${not empty err}">
                            <div class="alert alert-danger">${err}</div>
                        </c:if>

                        <!-- Form lọc theo khoảng ngày -->
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


                        <form class="d-flex mb-3" action="scheduleByClass" method="get">
                            <input type="hidden" name="id" value="${classId}" />
                            <input type="hidden" name="name" value="${className}" />
                            <input type="hidden" name="mode" value="searchByClass" />
                            <input type="text" name="keyword" class="form-control me-2" placeholder="Nhập từ khóa (tên GV, phòng, ngày)..." value="${param.keyword}" />
                            <button type="submit" class="btn btn-primary">Tìm</button>
                        </form>


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
                    </div>
                </main>

                <footer class="py-4 bg-light mt-auto">
                    <div class="container-fluid px-4 text-center">
                        <div class="text-muted">Copyright &copy; BIG DREAM 2025</div>
                    </div>
                </footer>
            </div>
        </div>


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

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/scripts.js"></script>
    </body>
</html>