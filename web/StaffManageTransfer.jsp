<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <title>Quản lý đơn chuyển lớp</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/startbootstrap-sb-admin@7.0.5/dist/css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            body {
                font-family: Arial, sans-serif;
            }
            table {
                border-collapse: collapse;
                width: 95%;
                margin: 30px auto;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0,0,0,0.05);
            }
            th, td {
                border: 1px solid #dee2e6;
                padding: 12px;
                text-align: center;
            }
            th {
                background-color: #f8f9fa;
                font-weight: bold;
            }
            textarea {
                width: 100%;
                height: 60px;
                resize: vertical;
                padding: 8px;
                border-radius: 4px;
                border: 1px solid #ced4da;
            }
            button {
                width: 90px;
                margin: 3px;
            }
            form {
                margin: 0;
            }
            h2 {
                text-align: center;
                margin-top: 30px;
            }

        </style>
    </head>
    <body class="sb-nav-fixed">
        <!-- Top nav -->
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="#">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle">
                <i class="fas fa-bars"></i>
            </button>
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item">
                    <a class="nav-link" href="#"><i class="fas fa-bell"></i> Thông báo</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" data-bs-toggle="dropdown">
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

        <!-- Sidebar -->
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <div class="sb-sidenav-menu-heading">Staff</div>
                            <a class="nav-link" href="staffhome">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>Trang Chủ
                            </a>
                            <a class="nav-link" href="#"><div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>Biểu Đồ</a>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts">
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
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseClasses">
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
                            <a href="classTransfer" class="nav-link">Gửi đơn chuyển lớp</a>
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
                <main class="container-fluid px-4">
                    <h2 class="mt-4">Danh sách yêu cầu chuyển lớp</h2>
                    <form method="get" action="classTransfer" class="mb-3">
                        <div class="input-group" style="max-width: 400px;" class="ms-3">
                            <input type="text" name="keyword" value="${param.keyword}" class="form-control form-control-sm" placeholder="Tìm kiếm theo tên học sinh">
                            <button type="submit" class="btn btn-primary btn-sm">🔍</button>
                        </div>
                    </form>


                    <c:if test="${not empty param.keyword}">
                        <c:choose>
                            <c:when test="${empty requests}">
                                <div class="alert alert-warning text-center">
                                    Không tìm thấy kết quả phù hợp với từ khóa: "<strong>${param.keyword}</strong>"
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-info text-center">
                                    Kết quả tìm kiếm cho từ khóa: "<strong>${param.keyword}</strong>"
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:if>



                    <table class="table table-bordered mt-4">
                        <thead>
                            <tr>
                                <th>Học sinh</th>
                                <th>Từ lớp</th>
                                <th>Đến lớp</th>
                                <th>Lý do</th>
                                <th>Ngày gửi đơn</th>
                                <th>Trạng thái</th>
                                <th>Ghi chú khi xét đơn</th>
                                <th>Hành động</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="r" items="${requests}">
                                <tr>
                                    <td>${r.studentName}</td>
                                    <td>${r.fromClassName}</td>
                                    <td>${r.toClassName}</td>
                                    <td>${r.reason}</td>
                                    <td><fmt:formatDate value="${r.requestDate}" pattern="dd/MM/yyyy" /></td>

                                    <td>
                                        <c:choose>
                                            <c:when test="${r.status == 'approved'}">️ Đã đồng ý</c:when>
                                            <c:when test="${r.status == 'rejected'}"> Đã từ chối</c:when>
                                            <c:otherwise> Đang chờ</c:otherwise>
                                        </c:choose>
                                    </td>

                                    <td>
                                        <form method="post" action="classTransfer">
                                            <input type="hidden" name="requestId" value="${r.id}">
                                            <textarea name="staffNote" placeholder="Ghi chú xét đơn...">${r.staffNote}</textarea>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${r.status == 'approved' || r.status == 'rejected'}">
                                                <button class="btn btn-success btn-sm" disabled>✔️ Duyệt</button>
                                                <button class="btn btn-danger btn-sm" disabled>❌ Từ chối</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-success btn-sm" name="action" value="approve">✔️ Duyệt</button>
                                                <button class="btn btn-danger btn-sm" name="action" value="reject">❌ Từ chối</button>
                                            </c:otherwise>
                                        </c:choose>

                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </main>
            </div>
        </div>

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/scripts.js"></script>
    </body>
</html>