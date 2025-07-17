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
            #courseCountChart, #roleCountChart, #revenueChart, #studentRegistrationChart, #studentByTypeChart {
                border: 1px solid red;
                min-height: 300px;
                width: 100% !important;
                max-width: 100%;
            }
            .card-body {
                padding: 20px;
            }
            .filter-container {
                display: flex;
                gap: 10px;
                margin-bottom: 20px;
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
                        <!-- FILTER FOR CHARTS -->
                        <div class="filter-container">
                            <select id="yearFilter" class="form-select">
                                <option value="">Tất cả năm</option>
                                <c:forEach var="year" items="${years}">
                                    <option value="${year}">${year}</option>
                                </c:forEach>
                            </select>
                            <select id="monthFilter" class="form-select">
                                <option value="">Tất cả tháng</option>
                                <option value="01">Tháng 1</option>
                                <option value="02">Tháng 2</option>
                                <option value="03">Tháng 3</option>
                                <option value="04">Tháng 4</option>
                                <option value="05">Tháng 5</option>
                                <option value="06">Tháng 6</option>
                                <option value="07">Tháng 7</option>
                                <option value="08">Tháng 8</option>
                                <option value="09">Tháng 9</option>
                                <option value="10">Tháng 10</option>
                                <option value="11">Tháng 11</option>
                                <option value="12">Tháng 12</option>
                            </select>
                        </div>
                        <!-- CARD HIỂN THỊ BIỂU ĐỒ DOANH THU -->
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-chart-bar me-1"></i>
                                Tổng doanh thu theo tháng
                            </div>
                            <div class="card-body">
                                <canvas 
                                    id="revenueChart"
                                    data-labels="<c:forEach var='item' items='${revenueData}' varStatus='status'>${not empty revenueData ? (status.index > 0 ? ',' : '') : ''}${item.month}</c:forEach>"
                                    data-values="<c:forEach var='item' items='${revenueData}' varStatus='status'>${not empty revenueData ? (status.index > 0 ? ',' : '') : ''}${item.revenue}</c:forEach>"
                                        width="800" height="300" style="max-width: 100%;">
                                    </canvas>
                                </div>
                            </div>
                            <!-- CARD HIỂN THỊ BIỂU ĐỒ SỐ HỌC SINH ĐĂNG KÝ -->
                            <div class="card mb-4">
                                <div class="card-header">
                                    <i class="fas fa-chart-bar me-1"></i>
                                    Số học sinh đăng ký theo tháng
                                </div>
                                <div class="card-body">
                                    <canvas 
                                        id="studentRegistrationChart"
                                        data-labels="<c:forEach var='item' items='${studentData}' varStatus='status'>${not empty studentData ? (status.index > 0 ? ',' : '') : ''}${item.month}</c:forEach>"
                                    data-approved="<c:forEach var='item' items='${studentData}' varStatus='status'>${not empty studentData ? (status.index > 0 ? ',' : '') : ''}${item.approvedCount}</c:forEach>"
                                    data-pending="<c:forEach var='item' items='${studentData}' varStatus='status'>${not empty studentData ? (status.index > 0 ? ',' : '') : ''}${item.pendingCount}</c:forEach>"
                                        width="800" height="300" style="max-width: 100%;">
                                    </canvas>
                                </div>
                            </div>
                            <!-- CARD HIỂN THỊ BIỂU ĐỒ SỐ HỌC SINH THEO THỂ LOẠI -->
                            <div class="card mb-4">
                                <div class="card-header">
                                    <i class="fas fa-chart-bar me-1"></i>
                                    Số học sinh theo thể loại khóa học
                                </div>
                                <div class="card-body">
                                    <canvas 
                                        id="studentByTypeChart"
                                        data-type-names="<c:forEach var='item' items='${studentCountByType}' varStatus='status'>${not empty studentCountByType ? (status.index > 0 ? ',' : '') : ''}${item.typeName}</c:forEach>"
                                    data-student-counts="<c:forEach var='item' items='${studentCountByType}' varStatus='status'>${not empty studentCountByType ? (status.index > 0 ? ',' : '') : ''}${item.studentCount}</c:forEach>"
                                    width="800" height="300" style="max-width: 100%;">
                                </canvas>
                            </div>
                        </div>
                    </div>
                </main>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.4/dist/chart.umd.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2.2.0/dist/chartjs-plugin-datalabels.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
                <script src="js/datatables-simple-demo.js"></script>
                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        // Đăng ký plugin ChartDataLabels
                        if (typeof ChartDataLabels !== 'undefined') {
                            Chart.register(ChartDataLabels);
                        } else {
                            console.error("ChartDataLabels plugin not loaded.");
                        }

                        // Cấu hình font cho Chart.js
                        Chart.defaults.font.family = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
                        Chart.defaults.color = '#292b2c';

                        // Lưu trữ dữ liệu gốc
                        const revenueCanvas = document.getElementById("revenueChart");
                        const studentCanvas = document.getElementById("studentRegistrationChart");
                        const originalRevenueData = {
                            labels: revenueCanvas.getAttribute("data-labels") ? revenueCanvas.getAttribute("data-labels").split(',').filter(l => l.trim()).map(l => l.trim()) : [],
                            values: revenueCanvas.getAttribute("data-values") ? revenueCanvas.getAttribute("data-values").split(',').filter(v => v.trim()).map(v => Number(v.trim()) || 0) : []
                        };
                        const originalStudentData = {
                            labels: studentCanvas.getAttribute("data-labels") ? studentCanvas.getAttribute("data-labels").split(',').filter(l => l.trim()).map(l => l.trim()) : [],
                            approved: studentCanvas.getAttribute("data-approved") ? studentCanvas.getAttribute("data-approved").split(',').filter(v => v.trim()).map(v => Number(v.trim()) || 0) : [],
                            pending: studentCanvas.getAttribute("data-pending") ? studentCanvas.getAttribute("data-pending").split(',').filter(v => v.trim()).map(v => Number(v.trim()) || 0) : []
                        };

                        let revenueChartInstance, studentChartInstance;

                        // Hàm vẽ biểu đồ doanh thu
                        function drawRevenueChart(labels, values) {
                            if (revenueChartInstance)
                                revenueChartInstance.destroy();
                            if (labels.length > 0 && values.length > 0) {
                                revenueChartInstance = new Chart(revenueCanvas, {
                                    type: 'bar',
                                    data: {
                                        labels: labels,
                                        datasets: [{
                                                label: 'Tổng doanh thu (VND)',
                                                backgroundColor: 'rgba(75, 192, 192, 0.6)',
                                                borderColor: 'rgba(75, 192, 192, 1)',
                                                borderWidth: 1,
                                                data: values,
                                                barPercentage: 0.8,
                                                categoryPercentage: 0.9
                                            }]
                                    },
                                    options: {
                                        scales: {
                                            y: {
                                                beginAtZero: true,
                                                min: 0,
                                                ticks: {
                                                    stepSize: 50000000,
                                                    callback: function (value) {
                                                        return value.toLocaleString('vi-VN') + ' VND';
                                                    }
                                                },
                                                title: {display: true, text: 'Doanh thu (VND)'}
                                            },
                                            x: {
                                                title: {display: true, text: 'Tháng'},
                                                ticks: {font: {size: 14}}
                                            }
                                        },
                                        responsive: true,
                                        maintainAspectRatio: false,
                                        layout: {
                                            padding: {left: 20, right: 20, top: 40, bottom: 20}
                                        },
                                        plugins: {
                                            legend: {labels: {font: {size: 14}}},
                                            datalabels: {
                                                anchor: 'end',
                                                align: 'top',
                                                formatter: function (value) {
                                                    return value > 0 ? value.toLocaleString('vi-VN') + ' VND' : '';
                                                },
                                                font: {weight: 'bold', size: 12},
                                                color: '#000'
                                            }
                                        }
                                    }
                                });
                            } else {
                                console.error("No data or mismatch for revenue chart. Labels length:", labels.length, "Values length:", values.length);
                            }
                        }

                        // Hàm vẽ biểu đồ học sinh đăng ký
                        function drawStudentChart(labels, approved, pending) {
                            if (studentChartInstance)
                                studentChartInstance.destroy();
                            const maxLength = Math.max(labels.length, approved.length, pending.length);
                            while (labels.length < maxLength)
                                labels.push('');
                            while (approved.length < maxLength)
                                approved.push(0);
                            while (pending.length < maxLength)
                                pending.push(0);

                            if (labels.length > 0) {
                                studentChartInstance = new Chart(studentCanvas, {
                                    type: 'bar',
                                    data: {
                                        labels: labels,
                                        datasets: [
                                            {label: 'Đã duyệt', backgroundColor: 'rgba(54, 162, 235, 0.6)', borderColor: 'rgba(54, 162, 235, 1)', data: approved, barPercentage: 0.8, categoryPercentage: 0.9},
                                            {label: 'Đang chờ', backgroundColor: 'rgba(255, 99, 132, 0.6)', borderColor: 'rgba(255, 99, 132, 1)', data: pending, barPercentage: 0.8, categoryPercentage: 0.9}
                                        ]
                                    },
                                    options: {
                                        scales: {y: {beginAtZero: true, min: 0, ticks: {stepSize: 1}, title: {display: true, text: 'Số học sinh'}}, x: {title: {display: true, text: 'Tháng'}, ticks: {font: {size: 14}}}},
                                        responsive: true,
                                        maintainAspectRatio: false,
                                        layout: {padding: {left: 20, right: 20, top: 40, bottom: 20}},
                                        plugins: {
                                            legend: {labels: {font: {size: 14}}},
                                            datalabels: {anchor: 'end', align: 'top', formatter: (value) => value > 0 ? value : '', font: {weight: 'bold', size: 12}, color: '#000'}
                                        }
                                    }
                                });
                            } else {
                                console.error("No data for student registration chart. Labels length:", labels.length);
                            }
                        }

                        // Hàm lọc dữ liệu theo năm và tháng
                        function filterData() {
                            const year = document.getElementById('yearFilter').value;
                            const month = document.getElementById('monthFilter').value;

                            // Lọc dữ liệu doanh thu
                            let filteredRevenueLabels = [...originalRevenueData.labels];
                            let filteredRevenueValues = [...originalRevenueData.values];
                            if (year || month) {
                                filteredRevenueLabels = [];
                                filteredRevenueValues = [];
                                originalRevenueData.labels.forEach((label, index) => {
                                    const [dataYear, dataMonth] = label.split('-');
                                    if ((year && dataYear !== year) || (month && dataMonth !== month))
                                        return;
                                    filteredRevenueLabels.push(label);
                                    filteredRevenueValues.push(originalRevenueData.values[index]);
                                });
                            }
                            drawRevenueChart(filteredRevenueLabels, filteredRevenueValues);

                            // Lọc dữ liệu học sinh đăng ký
                            let filteredStudentLabels = [...originalStudentData.labels];
                            let filteredApproved = [...originalStudentData.approved];
                            let filteredPending = [...originalStudentData.pending];
                            if (year || month) {
                                filteredStudentLabels = [];
                                filteredApproved = [];
                                filteredPending = [];
                                originalStudentData.labels.forEach((label, index) => {
                                    const [dataYear, dataMonth] = label.split('-');
                                    if ((year && dataYear !== year) || (month && dataMonth !== month))
                                        return;
                                    filteredStudentLabels.push(label);
                                    filteredApproved.push(originalStudentData.approved[index]);
                                    filteredPending.push(originalStudentData.pending[index]);
                                });
                            }
                            drawStudentChart(filteredStudentLabels, filteredApproved, filteredPending);
                        }

                        // Vẽ biểu đồ ban đầu
                        drawRevenueChart(originalRevenueData.labels, originalRevenueData.values);
                        drawStudentChart(originalStudentData.labels, originalStudentData.approved, originalStudentData.pending);

                        // Thêm sự kiện cho bộ lọc
                        document.getElementById('yearFilter').addEventListener('change', filterData);
                        document.getElementById('monthFilter').addEventListener('change', filterData);

                        // Biểu đồ số học sinh theo thể loại
                        const studentByTypeCanvas = document.getElementById("studentByTypeChart");
                        if (studentByTypeCanvas) {
                            let typeNames = studentByTypeCanvas.getAttribute("data-type-names") ? studentByTypeCanvas.getAttribute("data-type-names").split(',').filter(t => t.trim()) : [];
                            let studentCounts = studentByTypeCanvas.getAttribute("data-student-counts") ? studentByTypeCanvas.getAttribute("data-student-counts").split(',').filter(c => c.trim()).map(c => Number(c.trim()) || 0) : [];
                            console.log("Type Names:", typeNames);
                            console.log("Student Counts:", studentCounts);

                            if (typeNames.length > 0 && studentCounts.length > 0) {
                                new Chart(studentByTypeCanvas, {
                                    type: 'bar',
                                    data: {
                                        labels: typeNames,
                                        datasets: [{
                                                label: 'Số học sinh',
                                                backgroundColor: 'rgba(153, 102, 255, 0.6)',
                                                borderColor: 'rgba(153, 102, 255, 1)',
                                                borderWidth: 1,
                                                data: studentCounts,
                                                barPercentage: 0.8,
                                                categoryPercentage: 0.9
                                            }]
                                    },
                                    options: {
                                        scales: {
                                            y: {
                                                beginAtZero: true,
                                                min: 0,
                                                ticks: {stepSize: 1, callback: (value) => value},
                                                title: {display: true, text: 'Số học sinh'}
                                            },
                                            x: {
                                                title: {display: true, text: 'Thể loại'},
                                                ticks: {font: {size: 14}}
                                            }
                                        },
                                        responsive: true,
                                        maintainAspectRatio: false,
                                        layout: {
                                            padding: {left: 20, right: 20, top: 40, bottom: 20}
                                        },
                                        plugins: {
                                            legend: {labels: {font: {size: 14}}},
                                            datalabels: {
                                                anchor: 'end',
                                                align: 'top',
                                                formatter: (value) => value > 0 ? value : '',
                                                font: {weight: 'bold', size: 12},
                                                color: '#000'
                                            }
                                        }
                                    }
                                });
                            } else {
                                console.error("No data for student by type chart. Type Names length:", typeNames.length, "Student Counts length:", studentCounts.length);
                            }
                        } else {
                            console.error("Student by type chart canvas not found.");
                        }
                    });
                </script>
            </div>
        </div>
    </body>
</html>