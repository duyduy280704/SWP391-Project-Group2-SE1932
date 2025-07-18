<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Student Class Assignment for Staff" />
        <meta name="author" content="" />
        <title>Student Class Assignment - SB Staff</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .card-body {
                padding: 20px;
                height: 100%;
            }
            .form-inline .form-control, .form-inline select, .form-inline input {
                margin-right: 10px;
            }
            .table-responsive {
                margin-top: 20px;
            }
            .badge.bg-success {
                padding: 6px 12px;
                border-radius: 4px;
            }
            .disabled-option {
                color: #999;
                font-style: italic;
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="staffhome">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0"></form>
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item">
                    <a class="nav-link" href="#" id="loadNotice"><i class="fas fa-bell"></i> Thông báo</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#!">Thông tin cá nhân</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="logout">Đăng xuất</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
        <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                            <!-- Trang chủ -->
                            <a class="nav-link" href="staffhome">
                                <div class="sb-nav-link-icon"><i class="fas fa-home"></i></div>
                                Trang Chủ
                            </a>

                            <!-- Quản lý khóa học -->
                            <a class="nav-link" href="coursestaff">
                                <div class="sb-nav-link-icon"><i class="fas fa-book"></i></div>
                                Quản lý khóa học
                            </a>

                            <!-- Quản lý sự kiện -->
                            <a class="nav-link" href="eventstaff">
                                <div class="sb-nav-link-icon"><i class="fas fa-calendar-check"></i></div>
                                Quản lý sự kiện
                            </a>

                            <!-- Quản lý blog -->
                            <a class="nav-link" href="Blog">
                                <div class="sb-nav-link-icon"><i class="fas fa-blog"></i></div>
                                Quản lý blog
                            </a>

                            <!-- Quản lý phân lớp -->
                            <a class="nav-link" href="AssignClass">
                                <div class="sb-nav-link-icon"><i class="fas fa-th-list"></i></div>
                                Quản lý phân lớp
                            </a>

                            <!-- Quản lý đăng ký -->
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseRegistration" aria-expanded="false" aria-controls="collapseRegistration">
                                <div class="sb-nav-link-icon"><i class="fas fa-clipboard-list"></i></div>
                                Quản lý đăng ký
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseRegistration" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="Approve">
                                        <div class="sb-nav-link-icon"><i class="fas fa-user-graduate"></i></div>
                                        Học Sinh
                                    </a>
                                    <a class="nav-link" href="TeacherApplication">
                                        <div class="sb-nav-link-icon"><i class="fas fa-chalkboard-teacher"></i></div>
                                        Giáo Viên
                                    </a>
                                </nav>
                            </div>

                            <!-- Quản lý đơn -->
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseApplications" aria-expanded="false" aria-controls="collapseApplications">
                                <div class="sb-nav-link-icon"><i class="fas fa-file-alt"></i></div>
                                Quản lý đơn
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseApplications" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="listapplicationStu">
                                        <div class="sb-nav-link-icon"><i class="fas fa-user-graduate"></i></div>
                                        Học Sinh
                                    </a>
                                    <a class="nav-link" href="listapplicationTea">
                                        <div class="sb-nav-link-icon"><i class="fas fa-chalkboard-teacher"></i></div>
                                        Giáo Viên
                                    </a>
                                </nav>
                            </div>

                            <!-- Quản lý lớp học -->
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseClasses">
                                <div class="sb-nav-link-icon"><i class="fas fa-school"></i></div>
                                Quản lý lớp học
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseClasses" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="createClass"><i class="fas fa-plus me-2"></i> Tạo lớp mới</a>
                                    <a class="nav-link" href="classStudent"><i class="fas fa-users me-2"></i> Danh sách lớp</a>
                                </nav>
                            </div>

                            <!-- Thời khóa biểu -->
                            <a class="nav-link" href="listClassSchedule">
                                <div class="sb-nav-link-icon"><i class="fas fa-calendar-alt"></i></div>
                                Thời khóa biểu
                            </a>

                            <!-- Đánh giá -->
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseFeedback">
                                <div class="sb-nav-link-icon"><i class="fas fa-comments"></i></div>
                                Đánh giá
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseFeedback">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="feedback?mode=viewAll"><i class="fas fa-comment-dots me-2"></i> Phản hồi của học viên</a>
                                    <a class="nav-link" href="feedbackByTeacher?mode=staffView"><i class="fas fa-star-half-alt me-2"></i> Giáo viên đánh giá học sinh</a>
                                </nav>
                            </div>

                            <!-- Xử lý đơn chuyển lớp -->
                            <a href="classTransfer" class="nav-link">
                                <div class="sb-nav-link-icon"><i class="fas fa-exchange-alt"></i></div>
                                Xử lý đơn chuyển lớp
                            </a>
                        </div>
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Phân Lớp Học Viên</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="staffhome">Bảng Điều Khiển</a></li>
                            <li class="breadcrumb-item active">Phân Lớp Học Viên</li>
                        </ol>

                        <!-- Student Class Assignment Start -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                Danh Sách Đăng Ký Học Viên
                            </div>
                            <div class="card-body">
                                <!-- Form lọc -->
                                <form class="filter-form form-inline mb-3" method="get" action="AssignClass">
                                    <label>Khóa học:</label>
                                    <select name="courseId" class="form-control">
                                        <option value="">Tất cả</option>
                                        <c:forEach var="c" items="${courseList}">
                                            <option value="${c.id}" ${param.courseId == c.id ? "selected" : ""}>${c.name}</option>
                                        </c:forEach>
                                    </select>

                                    <label>Trạng thái:</label>
                                    <select name="status" class="form-control">
                                        <option value="">Tất cả</option>
                                        <option value="chưa phân lớp" ${param.status == 'chưa phân lớp' ? "selected" : ""}>Chưa phân lớp</option>
                                        <option value="đã phân lớp" ${param.status == 'đã phân lớp' ? "selected" : ""}>Đã phân lớp</option>
                                    </select>

                                    <label>Tên học viên:</label>
                                    <input type="text" name="studentName" class="form-control" value="${param.studentName}" placeholder="Nhập tên..." />

                                    <button type="submit" class="btn btn-secondary">🔍 Lọc</button>
                                </form>

                                <!-- Messages -->
                                <c:if test="${not empty sessionScope.messages}">
                                    <div class="alert alert-danger" style="padding: 10px; border-radius: 5px;">
                                        <ul style="margin: 0; padding-left: 20px;">
                                            <c:forEach var="msg" items="${sessionScope.messages}">
                                                <li>${msg}</li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                    <c:remove var="messages" scope="session" />
                                </c:if>

                                <!-- Danh sách -->
                                <form class="assign-form" method="post" action="AssignClass">
                                    <div class="table-responsive">
                                        <table class="table table-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Học viên</th>
                                                    <th>Khóa học</th>
                                                    <th>Trạng thái</th>
                                                    <th>Ghi chú</th>
                                                    <th>Phân lớp</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="r" items="${regisitions}">
                                                    <tr>
                                                        <td>${r.studentName}</td>
                                                        <td>${r.courseName}</td>
                                                        <td>${r.status}</td>
                                                        <td>${r.note}</td>
                                                        <td>
                                                            <c:set var="assignedClass" value="${assignedClassNames[r.id]}" />
                                                            <c:choose>
                                                                <c:when test="${not empty assignedClass}">
                                                                    <span class="badge bg-success">${assignedClass}</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <select name="regisitionId_${r.id}" class="form-control">
                                                                        <option value="">-- Chọn lớp --</option>
                                                                        <c:forEach var="cls" items="${classByCourse[r.courseId]}">
                                                                            <c:choose>
                                                                                <c:when test="${classFullStatus[cls.id_class]}">
                                                                                    <option value="${cls.id_class}" disabled class="disabled-option">
                                                                                        ${cls.name_class} (${classStudentCount[cls.id_class]}/30 - Đã đầy)
                                                                                    </option>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <option value="${cls.id_class}">
                                                                                        ${cls.name_class} (${classStudentCount[cls.id_class]}/30)
                                                                                    </option>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </c:forEach>
                                                                    </select>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                    <button type="submit" class="btn btn-primary">✅ Phân lớp</button>
                                </form>
                            </div>
                        </div>
                        <!-- Student Class Assignment End -->
                    </div>

                    <!-- POPUP THÔNG BÁO AJAX -->
                    <div id="noticeContainer" style="position: absolute; top: 60px; right: 20px; width: 400px; z-index: 9999; background: white; border: 1px solid #ccc; display: none;">
                        <div class="p-3">
                            <div class="d-flex justify-content-between align-items-center">
                                <h5 class="mb-0">Thông báo</h5>
                                <button class="btn-close" onclick="document.getElementById('noticeContainer').style.display = 'none'"></button>
                            </div>
                            <hr>
                            <div id="noticeContent">Đang tải...</div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="js/datatables-simple-demo.js"></script>
        <script>
            document.getElementById("loadNotice").addEventListener("click", function (e) {
                e.preventDefault();
                const container = document.getElementById("noticeContainer");
                const content = document.getElementById("noticeContent");
                container.style.display = container.style.display === "none" ? "block" : "none";

                if (container.style.display === "block") {
                    fetch("noticetostaff")
                        .then(response => response.text())
                        .then(data => {
                            content.innerHTML = data;
                        })
                        .catch(error => {
                            content.innerHTML = "<p class='text-danger'>Lỗi khi tải thông báo.</p>";
                        });
                }
            });
        </script>
    </body>
</html>