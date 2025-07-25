<!-- Dương_trang này của admin quản lý các khóa học bị hủy-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String role = (String) session.getAttribute("role");
    if (role == null || !"admin".equals(role)) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
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
                                <div class="sb-nav-link-icon"><i class="fas fa-users"></i></div>
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
                                <div class="sb-nav-link-icon"><i class="fas fa-cog"></i></div>
                                Cài đặt thông tin
                            </a>

                            <a class="nav-link collapsed" href="salaryadmin">
                                <div class="sb-nav-link-icon"><i class="fas fa-money-bill"></i></div>
                                Quản lý lương giáo viên
                            </a>

                            <a class="nav-link collapsed" href="AdminPayment">
                                <div class="sb-nav-link-icon"><i class="fas fa-credit-card"></i></div>
                                Quản lý thanh toán
                            </a>

                            <a class="nav-link collapsed" href="Refund">
                                <div class="sb-nav-link-icon"><i class="fas fa-undo"></i></div>
                                Hủy Đăng ký
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
                        <h1 class="mt-4">Hủy Đăng ký</h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Hủy đăng ký</li>
                        </ol>

                        <!-- Search -->
                        <form action="Refund" method="get" class="mb-4 d-flex align-items-center flex-wrap gap-2">
                            <input type="text" name="orderCode" class="form-control" placeholder="Tìm mã đơn" value="${param.orderCode}" />

                            <input type="text" name="studentName" class="form-control" placeholder="Tìm tên học viên" value="${param.studentName}" />

                            <select name="courseName" class="form-select">
                                <option value="">-- Tất cả khóa học --</option>
                                <c:forEach var="c" items="${courseList}">
                                    <option value="${c.name}" ${param.courseName eq c.name ? 'selected' : ''}>${c.name}</option>
                                </c:forEach>
                            </select>

                            <button type="submit" class="btn btn-primary">Tìm kiếm</button>
                        </form>



                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i> danh sách học viên hủy đăng ký
                            </div>
                            <div class="card-body">
                                <table class="course-list-table" border="1" cellpadding="5">
                                    <thead>
                                        <tr>
                                            <th>Họ và Tên</th>
                                            <th>Khóa học</th>
                                            <th>Trạng thái</th>
                                            <th>Học phí gốc</th>
                                            <th>Giảm giá (%)</th>
                                            <th>Số tiền hoàn trả</th>
                                            <th>Phương thức</th>
                                            <th>Mã đơn</th>
                                            <th>Ngày thanh toán</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="r" items="${refundList}">
                                            <tr>
                                                <td>${r.studentName}</td>
                                                <td>${r.courseName}</td>
                                                <td>${r.regisitionStatus}</td>
                                                <td>${r.originalPrice}</td>
                                                <td>${r.discountPercent}</td>
                                                <td>
                                                    <b style="color:red;">
                                                        <c:choose>
                                                            <c:when test="${r.paymentStatus eq 'Hoàn tất' ||r.paymentStatus eq 'Đã hoàn tiền'}">
                                                                ${r.refundAmount}
                                                            </c:when>
                                                            <c:otherwise>
                                                                0
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </b>
                                                </td>
                                                <td>${r.method}</td>
                                                <td>${r.orderCode}</td>
                                                <td>${r.paymentDate}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${r.paymentStatus eq 'Đã hoàn tiền'}">
                                                            ✅ Đã hoàn
                                                        </c:when>
                                                        <c:when test="${r.paymentStatus eq 'Hoàn tất'}">
                                                            <form action="Refund" method="post" style="margin:0;">
                                                                <input type="hidden" name="paymentId" value="${r.paymentId}" />
                                                                <input type="hidden" name="status" value="refu" />
                                                                <button type="submit">Xác nhận hoàn tiền</button>
                                                            </form>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form action="Refund" method="post" style="margin:0;">
                                                                <input type="hidden" name="paymentId" value="${r.paymentId}" />
                                                                <input type="hidden" name="status" value="huy" />
                                                                <button type="submit">Đã hủy</button>
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>
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