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
            #courseCountChart, #roleCountChart, #myAreaChart {
                border: 1px solid red;
                min-height: 200px;
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
                            <a class="nav-link" href="charts.html">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-area"></i></div>
                                Biểu Đồ
                            </a>
                            <a class="nav-link" href="student">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Danh Sách Học Sinh
                            </a>
                            <a class="nav-link" href="teacher">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Danh Sách Giáo Viên
                            </a>
                            <a class="nav-link" href="staff">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Danh Sách Nhân Viên
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
                        
                        <!-- CARD HIỂN THỊ BIỂU ĐỒ KHÓA HỌC -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-bar me-1"></i>
                                Số lượng khóa học theo loại
                            </div>
                            <div class="card-body">
                                <canvas 
                                    id="courseCountChart"
                                    data-labels="<c:forEach var='item' items='${chartData}' varStatus='status'>${status.index >= 0 ? ',' : ''}${item.typeName}</c:forEach>"
                                    data-values="<c:forEach var='item' items='${chartData}' varStatus='status'>${status.index >= 0 ? ',' : ''}${item.count}</c:forEach>"
                                    width="100%" height="40">
                                </canvas>
                            </div>
                        </div>
                        <!-- CARD HIỂN THỊ BIỂU ĐỒ VAI TRÒ -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-bar me-1"></i>
                                Số lượng theo vai trò
                            </div>
                            <div class="card-body">
                                <canvas 
                                    id="roleCountChart"
                                    data-labels="<c:forEach var='role' items='${roleCounts}' varStatus='status'>${status.index >= 0 ? ',' : ''}${role.role}</c:forEach>"
                                    data-values="<c:forEach var='role' items='${roleCounts}' varStatus='status'>${status.index >= 0 ? ',' : ''}${role.count}</c:forEach>"
                                    width="100%" height="40">
                                </canvas>
                            </div>
                        </div>
                        
                    </div>
                </main>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.min.js" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
                <script src="js/datatables-simple-demo.js"></script>
                <script src="assets/demo/chart-area-demo.js"></script>
                <script>
                document.addEventListener('DOMContentLoaded', function() {
                    // Biểu đồ khóa học
                    const courseCanvas = document.getElementById("courseCountChart");
                    if (courseCanvas) {
                        let labels = (courseCanvas.getAttribute("data-labels") || '').split(',').map(l => l.trim());
                        let values = (courseCanvas.getAttribute("data-values") || '').split(',').map(v => Math.round(Number(v.trim()) || 0));
                        if (labels.length > 0 && values.length > 0) {
                            new Chart(courseCanvas, {
                                type: 'bar',
                                data: {
                                    labels: labels,
                                    datasets: [{
                                        label: 'Số khóa học',
                                        backgroundColor: 'rgba(75, 192, 192, 0.6)',
                                        borderColor: 'rgba(75, 192, 192, 1)',
                                        borderWidth: 1,
                                        data: values
                                    }]
                                },
                                options: {
                                    scales: {
                                        y: { beginAtZero: true, min: 0, ticks: { stepSize: 1, callback: v => Number.isInteger(v) ? v : null } },
                                        x: { title: { display: true, text: 'Loại khóa học' } }
                                    },
                                    responsive: true,
                                    maintainAspectRatio: false
                                }
                            });
                        }
                    }

                    // Biểu đồ vai trò
                    const roleCanvas = document.getElementById("roleCountChart");
                    if (roleCanvas) {
                        let labels = (roleCanvas.getAttribute("data-labels") || '').split(',').map(l => l.trim());
                        let values = (roleCanvas.getAttribute("data-values") || '').split(',').map(v => Math.round(Number(v.trim()) || 0));
                        if (labels.length > 0 && values.length > 0) {
                            new Chart(roleCanvas, {
                                type: 'bar',
                                data: {
                                    labels: labels,
                                    datasets: [{
                                        label: 'Số lượng',
                                        backgroundColor: 'rgba(255, 99, 132, 0.6)',
                                        borderColor: 'rgba(255, 99, 132, 1)',
                                        borderWidth: 1,
                                        data: values
                                    }]
                                },
                                options: {
                                    scales: {
                                        y: { beginAtZero: true, min: 0, ticks: { stepSize: 1, callback: v => Number.isInteger(v) ? v : null } },
                                        x: { title: { display: true, text: 'Vai trò' } }
                                    },
                                    responsive: true,
                                    maintainAspectRatio: false
                                }
                            });
                        }
                    }
                });
                </script>
            </div>
        </div>
    </body>
</html>