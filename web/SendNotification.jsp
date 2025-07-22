<%-- 
    
    Author     : Duong
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                        <div class="card shadow-sm p-4 mt-4">
                            <h2 class="mb-4">📬 Gửi thông báo</h2>
                            <form method="post" action="SendNotification">
                                <div class="mb-3 d-flex align-items-center gap-3">
                                    <label class="form-label mb-0">Hình thức gửi:</label>
                                    <select name="sendType" id="sendType" onchange="toggleFields()" class="form-select w-auto">
                                        <option selected disabled>-- Chọn hình thức --</option>
                                        <option value="individual">👤 Gửi cá nhân</option>
                                        <option value="role">👥 Gửi theo vai trò</option>
                                        <option value="class">🏫 Gửi theo lớp</option>
                                        <option value="unpaid">💰 Gửi SV chưa đóng tiền</option>
                                        <option value="preapproved">✅ Gửi đến đăng ký đã duyệt</option>
                                    </select>

                                    <button type="button" class="btn btn-outline-secondary" onclick="showUserLookup()">🔍 Tra cứu người dùng</button>
                                </div>

                                <c:if test="${not empty message}">
                                    <div class="alert alert-success">${message}</div>
                                </c:if>

                                <!-- Gửi cá nhân -->
                                <div id="individualFields" class="mb-3" style="display: none;">
                                    <label class="form-label">Chọn vai trò:</label>
                                    <select name="role" class="form-select mb-2 w-50">
                                        <option value="student">Học sinh</option>
                                        <option value="teacher">Giáo viên</option>
                                        <option value="staff">Nhân viên</option>
                                    </select>
                                    <label class="form-label">Nhập ID người nhận:</label>
                                    <input type="text" name="receiverId" class="form-control w-50" placeholder="VD: ST001">
                                </div>

                                <!-- Gửi theo vai trò -->
                                <div id="roleFields" class="mb-3" style="display: none;">
                                    <label class="form-label">Chọn vai trò muốn gửi:</label>
                                    <select name="roleAll" class="form-select w-50">
                                        <option value="student">Toàn bộ học sinh</option>
                                        <option value="teacher">Toàn bộ giáo viên</option>
                                        <option value="staff">Toàn bộ nhân viên</option>
                                    </select>
                                </div>

                                <!-- Gửi theo lớp -->
                                <div id="classFields" class="mb-3" style="display: none;">
                                    <label class="form-label">Chọn lớp muốn gửi:</label>
                                    <div class="row">
                                        <c:forEach var="cls" items="${classList}">
                                            <div class="col-md-4">
                                                <div class="form-check">
                                                    <input class="form-check-input" type="checkbox" name="classIds" value="${cls.id_class}" id="class_${cls.id_class}">
                                                    <label class="form-check-label" for="class_${cls.id_class}">
                                                        ${cls.name_class}
                                                    </label>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </div>

                                <!-- Gửi cho SV chưa đóng tiền -->
                                <div id="unpaidFields" class="mb-3" style="display: none;">
                                    <p class="fw-bold">📌 Danh sách sinh viên chưa thanh toán:</p>
                                    <c:choose>
                                        <c:when test="${empty unpaidList}">
                                            <p class="text-danger">Không có sinh viên nào cần gửi thông báo.</p>
                                        </c:when>
                                        <c:otherwise>
                                            <ul class="list-group">
                                                <c:forEach var="s" items="${unpaidList}">
                                                    <li class="list-group-item">${s.name} (${s.email})</li>
                                                    </c:forEach>
                                            </ul>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- Gửi cho học viên đã duyệt -->
                                <div id="preapprovedFields" class="mb-3" style="display: none;">
                                    <p class="fw-bold">📌 Danh sách học viên đã duyệt:</p>
                                    <c:choose>
                                        <c:when test="${empty preList}">
                                            <p class="text-danger">Không có học viên nào ở trạng thái 'Đã duyệt'.</p>
                                        </c:when>
                                        <c:otherwise>
                                            <ul class="list-group">
                                                <c:forEach var="p" items="${preList}">
                                                    <li class="list-group-item">${p.full_name} (${p.email})</li>
                                                    </c:forEach>
                                            </ul>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!-- Nội dung thông báo -->
                                <div class="mb-3">
                                    <label class="form-label">Nội dung thông báo:</label>
                                    <textarea name="message" rows="4" class="form-control" required></textarea>
                                </div>

                                <button type="submit" class="btn btn-primary">📨 Gửi thông báo</button>
                            </form>
                        </div>
                    </div>

                    <div id="userLookupModal" class="modal" style="display: none; position: fixed; top: 0; left: 0;
                         width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 1000; justify-content: center; align-items: center;">
                        <div style="background: white; padding: 20px; border-radius: 8px; width: 600px; max-height: 80%; overflow-y: auto;">
                            <div style="display: flex; justify-content: space-between; align-items: center;">
                                <h5>🔍 Tra cứu người dùng</h5>
                                <button onclick="closeUserLookup()" class="btn btn-sm btn-danger">❌</button>
                            </div>

                            <form method="get" action="SendNotification">
                                <label>Chọn vai trò:</label>
                                <select name="role" class="form-select mb-3" onchange="this.form.submit()">
                                    <option value="">-- Chọn vai trò --</option>
                                    <option value="student">Học sinh</option>
                                    <option value="teacher">Giáo viên</option>
                                    <option value="staff">Nhân viên</option>
                                </select>
                            </form>

                            <input type="text" id="searchNameInput" class="form-control mb-3" placeholder="Nhập tên cần tìm..." oninput="filterLookupUsers()">

                            <table class="table table-bordered table-sm">
                                <thead><tr><th>Họ tên</th><th>Mã người dùng</th></tr></thead>
                                <tbody id="lookupUserTableBody">
                                    <c:forEach var="u" items="${userList}">
                                        <tr>
                                            <td>${u.fullName}</td>
                                            <td>${u.id}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
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
                                    function toggleFields() {
                                        const type = document.getElementById("sendType").value;
                                        document.getElementById("individualFields").style.display = (type === "individual") ? "block" : "none";
                                        document.getElementById("roleFields").style.display = (type === "role") ? "block" : "none";
                                        document.getElementById("classFields").style.display = (type === "class") ? "block" : "none";
                                        document.getElementById("unpaidFields").style.display = (type === "unpaid") ? "block" : "none";
                                        document.getElementById("preapprovedFields").style.display = (type === "preapproved") ? "block" : "none";
                                    }
                </script>
                <script>
                    function showUserLookup() {
                        document.getElementById('userLookupModal').style.display = 'flex';
                    }

                    function closeUserLookup() {
                        document.getElementById('userLookupModal').style.display = 'none';
                    }

                    function filterLookupUsers() {
                        var input = document.getElementById("searchNameInput").value.toLowerCase();
                        var rows = document.querySelectorAll("#lookupUserTableBody tr");
                        rows.forEach(function (row) {
                            var name = row.cells[0].innerText.toLowerCase();
                            row.style.display = name.includes(input) ? "" : "none";
                        });
                    }
                </script>





            </div>
        </div>
    </body>
</html>