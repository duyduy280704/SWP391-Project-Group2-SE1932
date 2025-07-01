<%-- 
    Document   : salaryAdmin
    Created on : Jun 29, 2025, 3:59:56 PM
    Author     : Quang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#!">Cài đặt</a></li>

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
                            <a class="nav-link" href="adminhome">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Trang Chủ
                            </a>

                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Quản lý người dùng
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="student">
                                        <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                        Học Sinh
                                    </a>
                                    <a class="nav-link" href="teacher">
                                        <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                        Giáo Viên
                                    </a>
                                    <a class="nav-link" href="staff">
                                        <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                        Nhân Viên
                                    </a>
                                </nav>
                            </div>
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Thông báo

                            </a>

                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapsePages" aria-expanded="false" aria-controls="collapsePages">
                                <div class="sb-nav-link-icon"><i class="fas fa-book-open"></i></div>
                                Cài đặt thông tin

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
                            <li class="breadcrumb-item active">Quản Lý</li>
                        </ol>

                        <form action="student" method="post" enctype="multipart/form-data" class="search-filter-form">
                            <div>
                                <input type="text" name="nameSearch" placeholder="Tìm kiếm giáo viên...">
                                <button type="submit" name="search">Tìm kiếm</button> 
                            </div>
                            <div>
                                <select name="genderFilter">
                                    <option value="">Cả nam và nữ</option>
                                    <option value="Nam" ${genderFilter == 'Nam' ? 'selected' : ''}>Nam</option>
                                    <option value="Nữ" ${genderFilter == 'Nữ' ? 'selected' : ''}>Nữ</option>
                                </select>
                                <input type="submit" name="filterGender" value="Lọc theo giới tính"/>
                            </div>
                        </form>


                        <div class="card mb-4">
                            <div class="card-body">
                                <form action="salaryadin" method="post" enctype="multipart/form-data">

                                    <table>
                                        <tr>

                                            <td>Tên giáo viên: </td>
                                            <td>
                                                <select name="teacher">
                                                    <option value="0">Giáo viên</option>
                                                    <c:forEach items= "${data1}" var="c">
                                                        <option value="${c.getId()}"
                                                                <c:if test="${s.getTeacher()==c.getId()}">
                                                                    selected 
                                                                </c:if>
                                                                > ${c.getName()}</option>

                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>Tên lớp: </td>
                                            <td><input type="text" name="class" value="${s.getEmail()}"></td>
                                            <td>Tiền khóa học: </td>
                                            <td><span>${s.getPassword()}</span></td>
                                            <td>% hoa hồng: </td>
                                            <td><input type="text" name="per" value="${s.getBirthdate()}"></td>
                                        </tr>
                                        <tr>
                                            <td>Tiền thưởng: </td>
                                            <td><input type="text" name="bonus" value="${s.getAddress()}"></td>
                                            <td>Tiền phạt:</td>
                                            <td><input type="text" name="penalty" value="${s.getPhone()}"></td>
                                            <td>Ghi chú: </td>
                                            <td><input type="text" name="note" value="${s.getAddress()}"></td>
                                            <td></td>
                                            <td></td>
                                        </tr>

                                        <tr>

                                            <td><input type="submit" name="add" value="Thêm"></td>
                                            <td><input type="submit" name="update" value="Lưu"></td>

                                            <td><input type="hidden" name="id" value="${s.getId()}"></td>
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
                                Danh Sách Lương Giáo Viên
                            </div>
                            <div class="card-body">
                                <table class="course-list-table">
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Tên giáo viên</th>
                                            <th>Tên lớp</th>
                                            <th>Tiền khóa học</th>
                                            <th>% hoa hồng</th>
                                            <th>Tiền thưởng</th>
                                            <th>Tiền phạt</th>
                                            <th>Ghi chú</th>
                                            <th>Tổng lương</th>
                                            <th>Ngày</th>
                                            <th>Chức năng</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${data}" var="item">
                                            <tr>
                                                <td>${item.getId()}</td>
                                                <td>${item.getTeacher()}</td>
                                                <td>${item.getClassName()}</td>
                                                <td>${item.getCost()}</td>
                                                <td>${item.getPer()}</td>
                                                <td>${item.getBonus()}</td>
                                                <td>${item.getPenalty()}</td>
                                                <td>${item.getNote()}</td>
                                                <td>${item.getSalary()}</td>
                                                <td>${item.getDate()}</td>

                                                <td>
                                                    <a href="student?id=${item.getId()}&mode=1" class="btn btn-edit">✏️ Sửa</a>
                                                    <a href="student?id=${item.id}&mode=2" class="btn btn-delete"
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
    </body>
</html>
