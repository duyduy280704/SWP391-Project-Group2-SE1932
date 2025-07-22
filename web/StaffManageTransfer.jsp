<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            .form-control-sm,
            .btn-sm {
                height: 38px !important; /* Đồng bộ chiều cao */
            }

            .form-label {
                margin-right: 5px;
                margin-left: 10px;
                line-height: 38px;
            }

            .date-group {
                display: flex;
                align-items: center;
            }

            .date-group input {
                margin-right: 10px;
            }
            .filter-form .form-control-sm,
            .filter-form .btn-sm {
                height: 38px !important;
            }

            .filter-form {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                align-items: center;
            }

            .filter-form input[type="text"],
            .filter-form input[type="date"] {
                width: 200px;
            }

            .filter-form .btn {
                white-space: nowrap;
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
            <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0"></form>
            <ul class="navbar-nav ms-auto ms-md-0 me-3 me-lg-4">
                <li class="nav-item">
                    <a class="nav-link" href="#" id="loadNotice"><i class="fas fa-bell"></i> Thông báo</a>

                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="profile">Thông tin cá nhân</a></li>
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

                            <!-- Quản lý lớp học -->
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseClasses" aria-expanded="false" aria-controls="collapseClasses">
                                <div class="sb-nav-link-icon"><i class="fas fa-school"></i></div>
                                Quản lý lớp học
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseClasses" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="createClass">
                                        <div class="sb-nav-link-icon"><i class="fas fa-plus"></i></div>
                                        Tạo lớp mới
                                    </a>
                                    <a class="nav-link" href="classStudent">
                                        <div class="sb-nav-link-icon"><i class="fas fa-users"></i></div>
                                        Danh sách lớp
                                    </a>
                                    <!-- Quản lý phân lớp -->
                                    <a class="nav-link" href="AssignClass">
                                        <div class="sb-nav-link-icon"><i class="fas fa-th-list"></i></div>
                                        Quản lý phân lớp
                                    </a>
                                </nav>
                            </div>



                            <!-- Quản lý đăng ký -->
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseRegistration" aria-expanded="false" aria-controls="collapseRegistration">
                                <div class="sb-nav-link-icon"><i class="fas fa-clipboard-check"></i></div>
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

                            <!-- Xử lý đơn chuyển lớp -->
                            <a class="nav-link" href="classTransfer">
                                <div class="sb-nav-link-icon"><i class="fas fa-exchange-alt"></i></div>
                                Xử lý đơn chuyển lớp
                            </a>

                            <!-- Thời khóa biểu -->
                            <a class="nav-link" href="listClassSchedule">
                                <div class="sb-nav-link-icon"><i class="fas fa-calendar-alt"></i></div>
                                Thời khóa biểu
                            </a>

                            <!-- Chấm công giáo viên -->
                            <a class="nav-link" href="teachingAttendance">
                                <div class="sb-nav-link-icon"><i class="fas fa-clock"></i></div>
                                Chấm công giáo viên
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

                            <!-- Đánh giá -->
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseFeedback" aria-expanded="false" aria-controls="collapseFeedback">
                                <div class="sb-nav-link-icon"><i class="fas fa-comments"></i></div>
                                Đánh giá
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseFeedback" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="feedback?mode=viewAll">
                                        <div class="sb-nav-link-icon"><i class="fas fa-comment-dots"></i></div>
                                        Phản hồi của học viên
                                    </a>
                                    <a class="nav-link" href="feedbackByTeacher?mode=staffView">
                                        <div class="sb-nav-link-icon"><i class="fas fa-star-half-alt"></i></div>
                                        Giáo viên đánh giá học sinh
                                    </a>
                                </nav>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>

            <!-- Main content -->
            <div id="layoutSidenav_content">
                <main class="container-fluid px-4">
                    <h2 class="mt-4"> Xử lí yêu cầu chuyển lớp</h2>


                    <!-- Hiển thị thông báo thành công -->
                    <c:if test="${not empty message}">
                        <div class="alert alert-success text-center">
                            ${message}
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <form id="transferForm" method="get" action="classTransfer" class="bg-light p-3 rounded">

                        <input type="hidden" name="action" id="formAction" value="" />

                        <!-- KHÓA HỌC -->
                        <div class="mb-3">
                            <label>Chọn khóa học</label>
                            <select name="courseId" class="form-select"
                                    onchange="document.getElementById('formAction').value = 'selectCourse'; this.form.submit();">
                                <option value="">-- Chọn khóa học --</option>
                                <c:forEach items="${courses}" var="c">
                                    <option value="${c.IDCourse}" <c:if test="${c.IDCourse == selectedCourseId}">selected</c:if>>
                                        ${c.nameCourse}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <!-- LỚP -->
                        <div class="mb-3">
                            <label>Chọn lớp</label>
                            <select name="classId" class="form-select"
                                    onchange="document.getElementById('formAction').value = 'selectClass'; this.form.submit();">
                                <option value="">-- Chọn lớp --</option>
                                <c:forEach items="${classes}" var="cl">
                                    <option value="${cl.id_class}" <c:if test="${cl.id_class == selectedClassId}">selected</c:if>>
                                        ${cl.name_class}
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="courseId" value="${selectedCourseId}" />
                        </div>
                        <!-- HỌC SINH -->
                        <div class="mb-3">
                            <label>Chọn học sinh</label>
                            <select name="studentId" class="form-select"
                                    onchange="submitWithAction('selectStudent')">
                                <option value="">-- Chọn học sinh --</option>
                                <c:forEach var="s" items="${students}">
                                    <option value="${s.id}" <c:if test="${s.id == selectedStudentId}">selected</c:if>>
                                        ${s.name}
                                    </option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="courseId" value="${selectedCourseId}" />
                            <input type="hidden" name="classId" value="${selectedClassId}" />
                        </div>

                    </form>

                    <!-- FORM POST CHUYỂN LỚP -->


                    <form method="post" action="classTransfer" class="bg-light p-3 rounded mt-3">

                        <input type="hidden" name="courseId" value="${selectedCourseId}" />
                        <input type="hidden" name="classId" value="${selectedClassId}" />
                        <input type="hidden" name="studentId" value="${selectedStudentId}" />


                        <div class="mb-3">
                            <label>Chuyển đến lớp</label>
                            <select name="toClassId" class="form-select" required>
                                <option value="">-- Chọn lớp --</option>
                                <c:forEach var="cl" items="${targetClasses}">
                                    <option value="${cl.id_class}" <c:if test="${cl.id_class == toClassId}">selected</c:if>>
                                        ${cl.name_class}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary">Chuyển lớp</button>
                    </form>


                    <h4 class="mt-5">Lịch sử chuyển lớp</h4>

                    <form method="get" action="classTransfer" class="mb-4 filter-form">
                        <input type="text" name="keyword" class="form-control form-control-sm"
                               placeholder="Tìm học sinh hoặc lớp..." value="${keyword}">
                        <button type="submit" class="btn btn-primary btn-sm">Tìm kiếm</button>
                        <a href="classTransfer" class="btn btn-primary btn-sm">Hiển thị tất cả</a>
                        <label for="fromDate" class="form-label mb-0">Từ ngày:</label>
                        <input type="date" class="form-control form-control-sm" name="fromDate" value="${fromDate}">
                        <label for="toDate" class="form-label mb-0">Đến ngày:</label>
                        <input type="date" class="form-control form-control-sm" name="toDate" value="${toDate}">
                        <button type="submit" class="btn btn-primary btn-sm">Lọc</button>

                    </form>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>STT</th>
                                <th>Học sinh</th>
                                <th>Lớp hiện tại</th>
                                <th>Chuyển đến lớp</th>
                                <th>Ngày chuyển</th>
                                <th>Số lần chuyển</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${history}" var="h" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${h.student.name}</td>
                                    <td>${h.fromClass.name_class}</td>
                                    <td>${h.toClass.name_class}</td>
                                    <td><fmt:formatDate value="${h.transferDate}" pattern="dd/MM/yyyy"/></td>
                                    <td>${h.transferCount}</td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

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
                    </body>
                </main>
            </div>
        </div>

        <!-- Scripts -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/scripts.js"></script>
        <script>
                                    function submitWithAction(actionValue) {
                                        // Gửi đúng form theo id thay vì forms[0] để tránh lỗi nếu có nhiều form
                                        document.getElementById('formAction').value = actionValue;
                                        document.getElementById('transferForm').submit();
                                    }
        </script>

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