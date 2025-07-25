<%-- 
    Document   : eventStaff
    Created on : Jun 28, 2025, 12:24:19 PM
    Author     : Quang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
            <!-- Navbar Brand-->
            <a class="navbar-brand ps-3" href="index.html">BIG DREAM</a>
            <!-- Sidebar Toggle-->
            <button class="btn btn-link btn-sm order-1 order-lg-0 me-4 me-lg-0" id="sidebarToggle" href="#!"><i class="fas fa-bars"></i></button>
            <!-- Navbar Search-->
            <form class="d-none d-md-inline-block form-inline ms-auto me-0 me-md-3 my-2 my-md-0">

            </form>
            <!-- Navbar-->
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
                        <h1 class="mt-4">Bảng Điều Khiển</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Nhân Viên</li>
                        </ol>

                        <form action="eventstaff" method="post" enctype="multipart/form-data" class="search-filter-form">
                            <div>
                                <input type="text" name="nameSearch" placeholder="Tìm kiếm sự kiện...">
                                <button type="submit" name="search">Tìm kiếm</button> 
                            </div>
                            <div>
                                <select name="genderFilter">
                                    <option value="0">Khóa học</option>

                                    <c:forEach items= "${data1}" var="c">
                                        <option value="${c.getId()}"
                                                <c:if test="${p.courseid==c.getId()}">
                                                    selected 
                                                </c:if>
                                                > ${c.getName()}</option>

                                    </c:forEach>
                                </select>
                                <input type="submit" name="filterGender" value="Lọc theo khóa học"/>
                            </div>
                        </form>

                        <div class="card mb-4">
                            <div class="card-body">
                                <form action="eventstaff" method="post" enctype="multipart/form-data">

                                    <table >
                                        <tr>

                                            <td>Tên sự kiện: </td>
                                            <td><input type="text" name="name" value="${p.getName()}"></td>
                                            <td>Khóa học: </td>
                                            <td>
                                                <select name="course">
                                                    <option value="0">Tất cả khóa học</option>

                                                    <c:forEach items= "${data1}" var="c">
                                                        <option value="${c.getId()}"
                                                                <c:if test="${p.courseid==c.getId()}">
                                                                    selected 
                                                                </c:if>
                                                                > ${c.getName()}</option>

                                                    </c:forEach>
                                                </select>
                                            </td>

                                        </tr>
                                        <tr>
                                            <td>Ngày:</td>
                                            <td><input type="text" name="date" value="${p.getDate()}"></td>

                                            <td>Ảnh: </td>
                                            <td><input type="file" name="image" value="">

                                                <c:if test="${not empty p.id and not empty p.img}">
                                                    <div style="margin-top: 10px;">
                                                        <img src="imageevent?id=${p.id}" alt="Current Event Image" style="max-width: 100px; max-height: 100px;" onerror="this.src='images/no-image.png'; this.alt='Image not available';">
                                                    </div>
                                                </c:if>
                                            </td>

                                        </tr>
                                        <tr>
                                            <td>Nội dung: </td>
                                            <td><textarea name="content" id="description" cols="60" rows="5" >${p.getContent()}</textarea></td>

                                        </tr>

                                        <tr>

                                            <td><input type="submit" name="add" value="Thêm"></td>
                                            <td><input type="submit" name="update" value="Lưu"></td>

                                            <td><input type="hidden" name="id" value="${p.getId()}"></td>

                                        </tr>
                                        <tr>
                                            <c:if test="${not empty message}">
                                            <p class="${success ? 'success' : 'error'}">${message}</p>
                                        </c:if>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                        </div>


                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                Danh Sách Sự Kiện
                            </div>
                            <div class="card-body">
                                <table class="course-list-table" >
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tên sự kiện</th>
                                            <th>Khóa học</th>
                                            <th>Nội dung</th>
                                            <th>Ảnh</th>
                                            <th>Ngày</th>
                                            <th>Chức năng</th>

                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${data}" var="item">
                                            <tr>
                                                <td>${item.getId()}</td>
                                                <td>${item.getName()}</td>
                                                <td>${item.getCourseid()}</td>
                                                <td>${item.getContent()}</td>

                                                <td>
                                                    <c:if test="${not empty item.img}">
                                                        <img src="imageevent?id=${item.id}" alt="Event Image" style="max-width: 100px; max-height: 100px;" onerror="this.src='images/no-image.png'; this.alt='Image not available';">
                                                    </c:if>
                                                </td>
                                                <td>${item.getDate()}</td>
                                                <td>
                                                    <a href="eventstaff?id=${item.getId()}&mode=1" class="btn btn-edit">✏️ Sửa</a>
                                                    <a href="eventstaff?id=${item.id}&mode=2" class="btn btn-delete" 
                                                       onclick="return confirm('Bạn có chắc chắn muốn xóa không?')">🗑️ Xóa</a>
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
        <script src="https://cdn.ckeditor.com/4.22.1/standard/ckeditor.js"></script>
        <script>
                                    document.addEventListener('DOMContentLoaded', function () {
                                        // Initialize DataTables
                                        const dataTable = document.getElementById('datatablesSimple');
                                        if (dataTable) {
                                            new simpleDatatables.DataTable(dataTable);
                                            console.log('DataTables initialized for datatablesSimple');
                                        } else {
                                            console.error('DataTable element not found');
                                        }

                                        // Initialize CKEditor
                                        const descriptionTextarea = document.getElementById('description');
                                        if (descriptionTextarea && typeof CKEDITOR !== 'undefined') {
                                            // Destroy existing CKEditor instance if it exists
                                            if (CKEDITOR.instances.description) {
                                                CKEDITOR.instances.description.destroy(true);
                                            }

                                            // Initialize CKEditor
                                            CKEDITOR.replace('description', {
                                                height: 200,
                                                toolbar: [
                                                    {name: 'basic', items: ['Bold', 'Italic', 'Underline', 'Link', 'Unlink', 'NumberedList', 'BulletedList']},
                                                    {name: 'paragraph', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight']},
                                                    {name: 'styles', items: ['Font', 'FontSize']},
                                                    {name: 'colors', items: ['TextColor', 'BGColor']}
                                                ]
                                            });

                                            // Set description value safely
                                            const descriptionValue = '${fn:escapeXml(p.content)}';

                                            CKEDITOR.instances.description.setData(descriptionValue);
                                            console.log('CKEditor initialized with description:', descriptionValue);
                                        } else {
                                            console.error('CKEditor not loaded or textarea not found.');
                                            if (!descriptionTextarea) {
                                                console.error('Textarea with id "description" not found.');
                                            }
                                            if (typeof CKEDITOR === 'undefined') {
                                                console.error('CKEDITOR object is undefined. Verify ckeditor.js is loaded.');
                                            }
                                        }
                                    });
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