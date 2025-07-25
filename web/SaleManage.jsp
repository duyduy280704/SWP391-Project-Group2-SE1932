
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>Tables - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>

        <style>
            .course-list-table {
                border-collapse: collapse;
                width: 100%;
            }
            .course-list-table th, .course-list-table td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
            .course-list-table th {
                background-color: #f2f2f2;
            }
            form input[type="text"] {
                width: 100%;
                padding: 6px;
                box-sizing: border-box;
            }

            form input[type="submit"] {
                padding: 6px 12px;
                margin-right: 5px;
            }
            .success {
                color: green;
            }
            .error {
                color: red;
            }

            .search-filter-form {
                display: flex;
                gap: 20px;
                margin: 20px 0;
                align-items: center;
                flex-wrap: wrap;
                background-color: #f8f9fa; /* Nền nhẹ để nổi bật */
                padding: 15px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .search-filter-form div {
                display: flex;
                align-items: center;
                gap: 12px;
            }

            .search-filter-form input[type="text"] {
                padding: 10px;
                border: 2px solid #ced4da;
                border-radius: 6px;
                font-size: 16px;
                width: 250px; /* Kích thước cố định để đồng nhất */
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            .search-filter-form input[type="text"]:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
                outline: none;
            }

            .search-filter-form select {
                padding: 10px;
                border: 2px solid #ced4da;
                border-radius: 6px;
                font-size: 16px;
                background-color: #fff;
                cursor: pointer;
                width: 150px; /* Kích thước cố định */
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }

            .search-filter-form select:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
                outline: none;
            }

            .search-filter-form button,
            .search-filter-form input[type="submit"] {
                padding: 10px 20px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }

            .search-filter-form button:hover,
            .search-filter-form input[type="submit"]:hover {
                background-color: #0056b3;
                transform: translateY(-2px); /* Hiệu ứng nổi nhẹ */
            }

            @media (max-width: 768px) {
                .search-filter-form {
                    flex-direction: column;
                    align-items: stretch;
                    padding: 10px;
                }

                .search-filter-form div {
                    width: 100%;
                }

                .search-filter-form input[type="text"],
                .search-filter-form select {
                    width: 100%;
                    font-size: 14px;
                }

                .search-filter-form button,
                .search-filter-form input[type="submit"] {
                    width: 100%;
                    font-size: 14px;
                }
            }
        </style>
    </head>
    <body class="sb-nav-fixed">
        <nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            <a class="navbar-brand ps-3" href="index.html">BIG DREAM</a>
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
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
                            <a class="nav-link" href="Sale">
                                <div class="sb-nav-link-icon"><i class="fas fa-blog"></i></div>
                                Quản lý khuyến mãi
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
            <div id="layoutSidenav_content">
                <main>
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">Quản lý khuyến mãi</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Quản lý khuyến mãi</li>
                        </ol>

                        <!-- Search -->
                        <form action="Sale" method="get" class="search-filter-form">
                            <div>
                                <input type="text" name="keyword" placeholder="Search sale code..." value="${keyword != null ? keyword : ''}" />
                                <button type="submit">Tìm kiếm</button>
                                <a href="Sale">Quay lại</a>
                            </div>
                        </form>

                        <!-- Add / Edit Form -->
                        <div class="card mb-4">
                            <div class="card-body">
                                <form action="Sale" method="post">
                                    <table>
                                        <tr>
                                            <td>Mã giảm giá:</td>
                                            <td><input type="text" name="code" value="${sale != null ? sale.code : ''}" ></td>
                                            <td>Giá trị giảm (%):</td>
                                            <td><input type="number" step="0.01" name="value" value="${sale != null ? sale.value : ''}" ></td>
                                            <td>Số lượng:</td>
                                            <td><input type="number" name="quantity" value="${sale != null ? sale.quantity : ''}" ></td>
                                        </tr>
                                        <tr>
                                            <td></td>
                                            <td><input type="submit" name="add" value="Thêm"></td>
                                            <td><input type="submit" name="update" value="Lưu"></td>
                                            <td colspan="3"><input type="hidden" name="id" value="${sale != null ? sale.id : 0}" /></td>
                                        </tr>
                                        <tr>
                                            <c:if test="${not empty message}">
                                                <td colspan="6"><p class="${success ? 'success' : 'error'}">${message}</p></td>
                                                </c:if>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>

                        <!-- Sale List -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i> Sale List
                            </div>
                            <div class="card-body">
                                <table class="course-list-table" border="1" cellpadding="5">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Mã giảm giá</th>
                                            <th>Giá trị giảm</th>
                                            <th>Ngày tạo</th>
                                            <th>Số lượng</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${data}" var="item">
                                            <tr>
                                                <td>${item.id}</td>
                                                <td>${item.code}</td>
                                                <td>${item.value}</td>
                                                <td>${item.createdAt}</td>
                                                <td>${item.quantity}</td>
                                                <td>
                                                    <a href="Sale?id=${item.id}&mode=1" class="btn btn-edit">✏️ Edit</a>
                                                    <a href="Sale?id=${item.id}&mode=2" class="btn btn-delete"
                                                       onclick="return confirm('Are you sure to delete this sale?')">🗑️ Delete</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
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