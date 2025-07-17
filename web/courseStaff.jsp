<%-- 
    Document   : courseStaff
    Created on : Jun 1, 2025, 12:45:10 AM
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
                    <a class="nav-link" href="#"><i class="fas fa-bell"></i> Thông báo</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#!">Thông tin cá nhân</a></li>
                        <li><hr class="dropdown-divider" /></li>
                        <li><a class="dropdown-item" href="#!">Đăng xuất</a></li>
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
                        <h1 class="mt-4">Bảng Điều Khiển</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Nhân Viên</li>
                        </ol>
                        <div class="card mb-4">
                            <div class="card-body">
                                <form action="coursestaff" method="post" enctype="multipart/form-data">

                                    <table>
                                        <tr>

                                            <td>Tên khóa học: </td>
                                            <td><input type="text" name="name" value="${p.getName()}"></td>
                                            <td>Thể loại: </td>
                                            <td><select name="type">
                                                    <option value="0">Tất cả thể loại</option>
                                                    <c:forEach items= "${data1}" var="c">
                                                        <option value="${c.getId()}"
                                                                <c:if test="${p.getType()==c.getId()}">
                                                                    selected 
                                                                </c:if>
                                                                > ${c.getName()}</option>

                                                    </c:forEach>
                                                </select></td>
                                            <td>Giá (VND): </td>
                                            <td><input type="text" name="fee" value="${p.getFee()}"> </td>
                                        </tr>
                                        <tr>
                                            <td>Mức độ: </td>
                                            <td>
                                                <select name="level">
                                                    <option value="Cơ bản" ${p.getLevel() == 'Cơ bản' ? 'selected' : ''}>Cơ bản</option>
                                                    <option value="Trung cấp" ${p.getLevel() == 'Trung cấp' ? 'selected' : ''}>Trung cấp</option>
                                                    <option value="Nâng cao" ${p.getLevel() == 'Nâng cao' ? 'selected' : ''}>Nâng cao</option>
                                                </select>
                                            </td>
                                            
                                            <td>Ảnh: </td>
                                            <td><input type="file" name="image" value="${p.getImage()}">

                                                <c:if test="${not empty p.id and not empty p.image}">
                                                    <div style="margin-top: 10px;">
                                                        <img src="image?id=${p.id}" alt="Current Course Image" style="max-width: 100px; max-height: 100px;" onerror="this.src='images/no-image.png'; this.alt='Image not available';">
                                                    </div>
                                                </c:if>
                                            </td>



                                        </tr>
                                        <tr>
                                            <td>Mô tả: </td>
                                            <td><textarea name="description" id="description" cols="60" rows="5" >${p.getDescription()}</textarea></td>

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
                                Danh Sách Khóa học
                            </div>
                            <div class="card-body">
                                <table class="course-list-table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tên khóa học</th>
                                            <th>Thể loại</th>
                                            <th>Mô tả</th>
                                            <th>giá</th>
                                            <th>Ảnh</th>
                                            <th>Mức độ</th>
                                            <th>Chức năng</th>

                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${data}" var="item">
                                            <tr>
                                                <td>${item.getId()}</td>
                                                <td>${item.getName()}</td>
                                                <td>${item.getType()}</td>
                                                <td>${item.getDescription()}</td>
                                                <td>${item.getFee()} VND</td>

                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty item.image}">
                                                            <img src="image?id=${item.id}" alt="Course Picture" style="max-width: 100px; max-height: 100px;" onerror="this.src='images/no-image.png'; this.alt='Image not available';">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span>No Image</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>${item.getLevel()}</td>
                                                <td>
                                                    <a href="coursestaff?id=${item.getId()}&mode=1" class="btn btn-edit">✏️ Sửa</a>
                                                    <a href="coursestaff?id=${item.id}&mode=2" class="btn btn-delete" 
                                                       onclick="return confirm('Bạn có chắc chắn muốn xóa không?')">🗑️ Xóa</a>
                                                </td>

                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
                            </div>
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
                                                                   const descriptionValue = '${p.description != null ? fn:escapeXml(p.description) : ''}';
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
    </body>
</html>

