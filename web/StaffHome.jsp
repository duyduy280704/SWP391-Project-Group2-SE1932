<%-- 
    Document   : StaffHome
    Created on : Jun 17, 2025, 8:56:42 AM
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
        <title>Dashboard - SB Staff</title>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/styles.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <style>
            .dashboard-table {
                width: 100%;
                table-layout: fixed;
                border-collapse: collapse;
            }



            .card-body {
                padding: 20px;
                height: 100%;
            }

            #courseCountChart {
                width: 100% !important;
                height: auto !important;
                max-height: 400px;
            }

            @media (max-width: 768px) {
                .dashboard-table td {
                    width: 100%;
                    display: block;
                }
            }
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
                <li class="nav-item">
                    <a class="nav-link" href="#" id="loadNotice"><i class="fas fa-bell"></i> Thông báo</a>

                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
                    <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#!">Thông tin cá nhân</a></li>
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
                            <a class="nav-link" href="staffhome">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Trang Chủ
                            </a>



                            <a class="nav-link" href="coursestaff">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Quản lý khóa học
                            </a>


                            <a class="nav-link" href="#">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Quản lý lịch học
                            </a>
                            <a class="nav-link" href="#">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Quản lý sự kiện
                            </a>
                            <a class="nav-link" href="#">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Quản lý blog
                            </a>


                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseLayouts" aria-expanded="false" aria-controls="collapseLayouts">
                                <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                Quản lý đăng ký
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseLayouts" aria-labelledby="headingOne" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="#">
                                        <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                        Học Sinh
                                    </a>
                                    <a class="nav-link" href="#">
                                        <div class="sb-nav-link-icon"><i class="fas fa-table"></i></div>
                                        Giáo Viên
                                    </a>

                                </nav>
                            </div>

                            <a class="nav-link collapsed" href="#" >
                                <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                                Gửi thông báo

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
                            <li class="breadcrumb-item active">Nhân viên</li>
                        </ol>
                        <div class="row">
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-primary text-white mb-4">
                                    <div class="card-body">Số khóa học: ${course}</div>

                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-warning text-white mb-4">
                                    <div class="card-body">Số học sinh: ${student}</div>

                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-success text-white mb-4">
                                    <div class="card-body">Số giáo viên: ${teacher}</div>

                                </div>
                            </div>
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-danger text-white mb-4">
                                    <div class="card-body">Số nhân viên: ${staff}</div>

                                </div>
                            </div>
                        </div>

                        <table class="dashboard-table">
                            <tr>
                                <td>
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
                                    </td>
                                    <td>
                                        <h4>Bình Luận đánh giá mới nhất</h4>
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                                <tr>
                                                    <th>Khóa học</th>
                                                    <th>Học sinh</th>
                                                    <th>Nội dung</th>
                                                    <th>Ngày</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${data}" var="item">
                                                <tr>
                                                    <td>${item.course}</td>
                                                    <td>${item.student}</td>
                                                    <td>${item.text}</td>
                                                    <td>${item.date}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </td>
                            </tr>
                        </table>

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
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
                <script src="js/datatables-simple-demo.js"></script>
                <script src="assets/demo/chart-area-demo.js"></script>
                <script>
                                    document.addEventListener('DOMContentLoaded', function () {
                                        const courseCanvas = document.getElementById("courseCountChart");
                                        if (!courseCanvas) {
                                            console.error('Canvas element with id "courseCountChart" not found.');
                                            return;
                                        }
                                        let labels = (courseCanvas.getAttribute("data-labels") || '').split(',').map(l => l.trim());
                                        let values = (courseCanvas.getAttribute("data-values") || '').split(',').map(v => Math.round(Number(v.trim()) || 0));
                                        if (labels.length > 0 && values.length > 0 && labels.length === values.length) {
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
                                                        y: {beginAtZero: true, min: 0, ticks: {stepSize: 1, callback: v => Number.isInteger(v) ? v : null}},
                                                        x: {title: {display: true, text: 'Loại khóa học'}}
                                                    },
                                                    responsive: true,
                                                    maintainAspectRatio: false,
                                                    layout: {
                                                        padding: {left: 10, right: 10, top: 10, bottom: 10}
                                                    }
                                                }
                                            });
                                        } else {
                                            console.error('Invalid chart data:', {labels, values});
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
            </div>
        </div>
    </body>
</html>
