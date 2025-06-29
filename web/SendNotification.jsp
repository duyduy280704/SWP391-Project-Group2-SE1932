<%-- 
    Document   : AdminHome
    Created on : Jun 2, 2025, 6:19 PM
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
        <title>Dashboard - SB Admin</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            #courseCountChart, #roleCountChart, #revenueChart {
                border: 1px solid red;
                min-height: 300px;
                width: 100% !important;
                max-width: 100%;
            }
            .card-body {
                padding: 20px;
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

                            <a class="nav-link" href="#">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Quản lý lương giáo viên
                            </a>
                            <a class="nav-link collapsed" href="#" >
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Thông báo

                            </a>

                            <a class="nav-link collapsed" href="setting" >
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
                        <h2>📬 Gửi thông báo</h2>
                        <form method="post" action="SendNotification">
                            <label>Hình thức gửi:</label>
                            <select name="sendType" id="sendType" onchange="toggleFields()">
                                <option >chọn chức năng gửi</option>
                                <option value="individual">👤 Gửi cá nhân</option>
                                <option value="role">👥 Gửi theo vai trò</option>
                                <option value="class">🏫 Gửi theo lớp</option>
                            </select>

                            <!-- Nếu chọn gửi cá nhân -->
                            <div id="individualFields" style="display: none; margin-top: 10px;">
                                <label>Chọn vai trò:</label>
                                <select name="role">
                                    <option value="student">Học sinh</option>
                                    <option value="teacher">Giáo viên</option>
                                    <option value="staff">Nhân viên</option>
                                </select>
                                <label>Nhập ID người nhận:</label>
                                <input type="text" name="receiverId" placeholder="Mã người nhận (VD: ST001)">
                            </div>

                            <!-- Nếu chọn gửi theo vai trò -->
                            <div id="roleFields" style="display: none; margin-top: 10px;">
                                <label>Chọn vai trò muốn gửi:</label>
                                <select name="roleAll">
                                    <option value="student">Toàn bộ học sinh</option>
                                    <option value="teacher">Toàn bộ giáo viên</option>
                                    <option value="staff">Toàn bộ nhân viên</option>
                                </select>
                            </div>

                            <!-- Nếu chọn gửi theo lớp -->
                            <div id="classFields" style="display: none; margin-top: 10px;">
                                <label>Chọn lớp muốn gửi:</label><br>
                                <c:forEach var="cls" items="${classList}">
                                    <label>
                                        <input type="checkbox" name="classIds" value="${cls.id_class}"> ${cls.name_class}
                                    </label><br>
                                </c:forEach>
                            </div>

                            <!-- Nội dung thông báo -->
                            <label style="margin-top: 10px;">Nội dung thông báo:</label>
                            <textarea name="message" rows="4" class="form-control" required></textarea>

                            <br>
                            <button type="submit" class="btn btn-primary">Gửi thông báo</button>
                        </form>



                    </div>
                </main>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
                <script src="js/datatables-simple-demo.js"></script>
                <script src="assets/demo/chart-area-demo.js"></script>

                <script>
                                function toggleFields() {
                                    const type = document.getElementById("sendType").value;
                                    document.getElementById("individualFields").style.display = (type === "individual") ? "block" : "none";
                                    document.getElementById("roleFields").style.display = (type === "role") ? "block" : "none";
                                    document.getElementById("classFields").style.display = (type === "class") ? "block" : "none";
                                }
                </script>
            </div>
        </div>
    </body>
</html>