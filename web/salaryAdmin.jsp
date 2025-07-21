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
            form select, form input[type="text"] {
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
            .search-filter-form, .teacher-form, .class-form, .main-form {
                display: flex;
                gap: 20px;
                margin: 20px 0;
                align-items: center;
                flex-wrap: wrap;
                background-color: #f8f9fa;
                padding: 15px;
                border-radius: 8px;
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }
            .search-filter-form div, .teacher-form div, .class-form div, .main-form div {
                display: flex;
                align-items: center;
                gap: 12px;
            }
            .search-filter-form input[type="text"], .teacher-form select, .class-form select, .main-form select, .main-form input[type="text"] {
                padding: 10px;
                border: 2px solid #ced4da;
                border-radius: 6px;
                font-size: 16px;
                width: 250px;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
            }
            .search-filter-form input[type="text"]:focus, .teacher-form select:focus, .class-form select:focus, .main-form select:focus, .main-form input[type="text"]:focus {
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
                outline: none;
            }
            .search-filter-form button, .main-form input[type="submit"] {
                padding: 10px 20px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 6px;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s ease, transform 0.2s ease;
            }
            .search-filter-form button:hover, .main-form input[type="submit"]:hover {
                background-color: #0056b3;
                transform: translateY(-2px);
            }
            @media (max-width: 768px) {
                .search-filter-form, .teacher-form, .class-form, .main-form {
                    flex-direction: column;
                    align-items: stretch;
                    padding: 10px;
                }
                .search-filter-form div, .teacher-form div, .class-form div, .main-form div {
                    width: 100%;
                }
                .search-filter-form input[type="text"], .teacher-form select, .class-form select, .main-form select, .main-form input[type="text"] {
                    width: 100%;
                    font-size: 14px;
                }
                .search-filter-form button, .main-form input[type="submit"] {
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
                            <a class="nav-link" href="adminhome">
                                <div class="sb-nav-link-icon"><i class="fas fa-home"></i></div>
                                Trang Chủ
                            </a>
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-users-cog"></i></div>
                                Quản lý người dùng
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="student">
                                        <div class="sb-nav-link-icon"><i class="fas fa-user-graduate"></i></div>
                                        Học Sinh
                                    </a>
                                    <a class="nav-link" href="teacher">
                                        <div class="sb-nav-link-icon"><i class="fas fa-chalkboard-teacher"></i></div>
                                        Giáo Viên
                                    </a>
                                    <a class="nav-link" href="staff">
                                        <div class="sb-nav-link-icon"><i class="fas fa-user-tie"></i></div>
                                        Nhân Viên
                                    </a>
                                </nav>
                            </div>
                            <a class="nav-link collapsed" href="setting">
                                <div class="sb-nav-link-icon"><i class="fas fa-cogs"></i></div>
                                Cài đặt thông tin
                            </a>
                            <a class="nav-link collapsed" href="salaryadmin">
                                <div class="sb-nav-link-icon"><i class="fas fa-money-check-alt"></i></div>
                                Quản lý lương giáo viên
                            </a>
                            <a class="nav-link collapsed" href="SendNotification">
                                <div class="sb-nav-link-icon"><i class="fas fa-bell"></i></div>
                                Thông báo
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

                        <!-- Search and Filter form -->
                        <form action="salaryadmin" method="post" class="search-filter-form">
                            <div>
                                <label>Tên giáo viên: </label>
                                <input type="text" name="searchTeacherName" placeholder="Tìm kiếm theo tên giáo viên..." value="${searchTeacherName}">
                            </div>
                            <div>
                                <label>Tháng/Năm: </label>
                                <select name="filterMonthYear">
                                    <option value="">Tất cả</option>
                                    <c:forEach var="year" begin="2024" end="${currentYear}">
                                        <c:forEach var="month" begin="1" end="12">
                                            <c:set var="monthYear" value="${year}-${month < 10 ? '0' : ''}${month}"/>
                                            <option value="${monthYear}" <c:if test="${filterMonthYear == monthYear}">selected</c:if>>
                                                Tháng ${month < 10 ? '0' : ''}${month}/${year}
                                            </option>
                                        </c:forEach>
                                    </c:forEach>
                                </select>
                            </div>
                            <div>
                                <button type="submit" name="search">Tìm kiếm</button>
                            </div>
                        </form>

                        <!-- Teacher selection form -->
                        <form action="salaryadmin" method="get" class="teacher-form">
                            <div>
                                <label>Tên giáo viên: </label>
                                <select name="teacher" onchange="this.form.submit()">
                                    <option value="0">Chọn giáo viên</option>
                                    <c:forEach items="${data1}" var="c">
                                        <option value="${c.getId()}"
                                                <c:if test="${selectedTeacher == c.getId()}">selected</c:if>
                                                >${c.getName()}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="action" value="loadClasses">
                            </div>
                        </form>

                        <!-- Month and Year selection form -->
                        <form action="salaryadmin" method="get" class="class-form">
                            <div>
                                <label>Tháng/Năm: </label>
                                <select name="month" onchange="this.form.submit()">
                                    <option value="0">Chọn tháng/năm</option>
                                    <c:forEach var="year" begin="2024" end="${currentYear}">
                                        <c:forEach var="month" begin="1" end="12">
                                            <c:set var="monthYear" value="${year}-${month < 10 ? '0' : ''}${month}"/>
                                            <option value="${monthYear}" <c:if test="${selectedMonthYear == monthYear}">selected</c:if>>
                                                Tháng ${month < 10 ? '0' : ''}${month}/${year}
                                            </option>
                                        </c:forEach>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="teacher" value="${selectedTeacher}">
                                <input type="hidden" name="action" value="loadSessions">
                            </div>
                        </form>

                        <!-- Main form for other fields -->
                        <form action="salaryadmin" method="post" class="main-form">
                            <table>
                                <tr>
                                    <td>Lương cứng: </td>
                                    <td><span>
                                            <c:choose>
                                                <c:when test="${not empty s}">
                                                    ${s.getOffer_salary()} VND
                                                </c:when>
                                                <c:when test="${not empty teacherOfferSalary}">
                                                    ${teacherOfferSalary} VND
                                                </c:when>
                                                <c:otherwise>
                                                    0 VND
                                                </c:otherwise>
                                            </c:choose>
                                        </span></td>
                                    <td>Số buổi dạy: </td>
                                    <td><span>
                                            <c:choose>
                                                <c:when test="${not empty s}">
                                                    ${s.getNumber_of_sessions()}
                                                </c:when>
                                                <c:when test="${not empty sessionCount}">
                                                    ${sessionCount}
                                                </c:when>
                                                <c:otherwise>
                                                    0
                                                </c:otherwise>
                                            </c:choose>
                                        </span></td>
                                </tr>
                                <tr>
                                    <td>Tiền thưởng: </td>
                                    <td><input type="text" name="bonus" value="${s != null ? s.getBonus() : '0'}"></td>
                                    <td>Tiền phạt:</td>
                                    <td><input type="text" name="penalty" value="${s != null ? s.getPenalty() : '0'}"></td>
                                    <td>Ghi chú: </td>
                                    <td><input type="text" name="note" value="${s != null ? s.getNote() : ''}"></td>
                                    <td></td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td><input type="submit" name="add" value="Thêm"></td>
                                    <td><input type="submit" name="update" value="Cập nhật"></td>
                                    <td><input type="hidden" name="id" value="${s != null ? s.getId() : ''}"></td>
                                    <td><input type="hidden" name="teacher" value="${selectedTeacher}"></td>
                                    <td><input type="hidden" name="month" value="${selectedMonthYear != null ? selectedMonthYear : currentYear + '-01'}"></td>
                                </tr>
                                <tr>
                                    <c:if test="${not empty message}">
                                        <p class="${success ? 'success' : 'error'}">${message}</p>
                                    </c:if>
                                </tr>
                            </table>
                        </form>

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
                                            <th>Lương cứng</th>
                                            <th>Số buổi dạy</th>
                                            <th>Tiền thưởng</th>
                                            <th>Tiền phạt</th>
                                            <th>Ghi chú</th>
                                            <th>Tổng lương</th>
                                            <th>Tháng/Năm</th>
                                            <th>Chức năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${data}" var="item">
                                            <tr>
                                                <td>${item.getId()}</td>
                                                <td>${item.getTeacher()}</td>
                                                <td>${item.getOffer_salary()}</td>
                                                <td>${item.getNumber_of_sessions()}</td>
                                                <td>${item.getBonus()}</td>
                                                <td>${item.getPenalty()}</td>
                                                <td>${item.getNote()}</td>
                                                <td>${item.getAmount()}</td>
                                                <td>${item.getMonth()}</td>
                                                <td>
                                                    <a href="salaryadmin?id=${item.getId()}&mode=1" class="btn btn-edit">✏️ Sửa</a>
                                                    <a href="salaryadmin?id=${item.getId()}&mode=2" class="btn btn-delete"
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