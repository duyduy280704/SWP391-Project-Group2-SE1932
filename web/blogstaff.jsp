<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="Blog Management for Staff" />
        <meta name="author" content="" />
        <title>Blog Management - SB Staff</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .card-body {
                padding: 20px;
                height: 100%;
            }
            .form-inline .form-control {
                margin-right: 10px;
            }
            .table-responsive {
                margin-top: 20px;
            }
            .img-fluid {
                max-width: 100px;
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
                        <h1 class="mt-4">Quản Lý Blog</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item"><a href="staffhome">Bảng Điều Khiển</a></li>
                            <li class="breadcrumb-item active">Quản Lý Blog</li>
                        </ol>

                        <!-- Blog Management Start -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                Danh Sách Bài Viết
                            </div>
                             <!-- Search Message -->
                                <c:if test="${not empty message}">
                                    <div class="alert alert-info text-center">${message}</div>
                                </c:if>

                            <div class="card-body">
                                <!-- Search Form -->
                                <form action="Blog" method="post" class="form-inline mb-3 justify-content-center">
                                    <input type="text" name="title" class="form-control mr-2" placeholder="Tìm tiêu đề" value="${searchTitle}">
                                    <input type="date" name="fromDate" class="form-control mr-2" value="${fromDate}">
                                    <input type="date" name="toDate" class="form-control mr-2" value="${toDate}">
                                    <input type="hidden" name="search" value="true">
                                    <button type="submit" class="btn btn-secondary">Tìm Kiếm</button>
                                </form>
                             <!-- Add Blog Form -->
                                <div class="mb-5">
                                    <h3 class="text-uppercase mb-3">Thêm Bài Viết Mới</h3>
                                    <form action="Blog" method="post" enctype="multipart/form-data">
                                        <input type="hidden" name="add" value="true">
                                        <div class="form-group">
                                            <label>Tiêu Đề</label>
                                            <input type="text" name="title" class="form-control" value="${blogAdd.title}" >
                                        </div>
                                        <div class="form-group">
                                            <label>Nội Dung</label>
                                            <textarea name="content" class="form-control" rows="4" >${blogAdd.content}</textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>Hình Ảnh</label>
                                            <input type="file" name="img" class="form-control-file" accept="image/*">
                                        </div>
                                        <div class="form-group">
                                            <label>Ngày Đăng</label>
                                            <input type="date" name="publishDate" class="form-control" value="${blogAdd.publishDate}" >
                                        </div>
                                        <button type="submit" class="btn btn-primary">Thêm Bài Viết</button>
                                    </form>
                                </div>

                                <!-- Edit Blog Form (shown when mode=1) -->
                                <c:if test="${blog != null}">
                                    <div class="mb-5">
                                        <h3 class="text-uppercase mb-3">Chỉnh Sửa Bài Viết</h3>
                                        <form action="Blog" method="post" enctype="multipart/form-data">
                                            <input type="hidden" name="update" value="true">
                                            <input type="hidden" name="id" value="${blog.id}">
                                            <div class="form-group">
                                                <label>Tiêu Đề</label>
                                                <input type="text" name="title" class="form-control" value="${blog.title}" required>
                                            </div>
                                            <div class="form-group">
                                                <label>Nội Dung</label>
                                                <textarea name="content" class="form-control" rows="4" required>${blog.content}</textarea>
                                            </div>
                                            <div class="form-group">
                                                <label>Hình Ảnh</label>
                                                <input type="file" name="img" class="form-control-file" accept="image/*">
                                                <c:if test="${blog.img != null}">
                                                    <img src="Blog?mode=image&id=${blog.id}" alt="Blog Image" class="img-fluid mt-2" style="max-width: 100px;"/>
                                                    <small class="form-text text-muted">Để trống để giữ hình ảnh hiện tại</small>
                                                </c:if>
                                            </div>
                                            <div class="form-group">
                                                <label>Ngày Đăng</label>
                                                <input type="date" name="publishDate" class="form-control" value="${blog.publishDate}" required>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Cập Nhật Bài Viết</button>
                                        </form>
                                    </div>
                                </c:if>

                                <!-- Blog List -->
                                <h3 class="text-uppercase mb-3">${search == 'true' ? 'Kết Quả Tìm Kiếm' : 'Danh Sách Bài Viết'}</h3>
                                <div class="table-responsive">
                                    <table class="table table-bordered">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Tiêu Đề</th>
                                                <th>Nội Dung</th>
                                                <th>Ngày Đăng</th>
                                                <th>Hình Ảnh</th>
                                                <th>Hành Động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="blog" items="${data}">
                                                <tr>
                                                    <td>${blog.id}</td>
                                                    <td>${blog.title}</td>
                                                    <td>${blog.content}</td>
                                                    <td><fmt:parseDate value="${blog.publishDate}" var="parsedDate" pattern="yyyy-MM-dd"/>
<fmt:formatDate value="${parsedDate}" pattern="dd-MM-yyyy"/></td>
                                                    <td>
                                                        <c:if test="${blog.img != null}">
                                                            <img src="Blog?mode=image&id=${blog.id}" alt="Blog Image" class="img-fluid" style="max-width: 100px;"/>
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <a href="Blog?mode=1&id=${blog.id}" class="btn btn-info btn-sm">Chỉnh Sửa</a>
                                                        <a href="Blog?mode=2&id=${blog.id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa?')">Xóa</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!-- Blog Management End -->
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