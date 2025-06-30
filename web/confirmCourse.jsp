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
            .modal {
                display: none;
                position: fixed;
                z-index: 1050;
                left: 0;
                top: 0;
                width: 100%;
                height: 100%;
                overflow: auto;
                background-color: rgba(0,0,0,0.5);
            }

            .modal-content {
                background-color: #fff;
                margin: 10% auto;
                padding: 20px;
                border-radius: 8px;
                width: 400px;
                box-shadow: 0 5px 15px rgba(0,0,0,.5);
            }

            .modal button {
                margin-right: 10px;
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

                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                Danh Sách Đăng ký khóa học
                            </div>
                            <div class="card-body">
                                <table class="course-list-table">
                                    <thead>
                                        <tr>
                                            <th>Họ tên</th>
                                            <th>Email</th>
                                            <th>SĐT</th>
                                            <th>Ngày sinh</th>
                                            <th>Khóa học</th>
                                            <th>Giới tính</th>
                                            <th>Địa chỉ</th>
                                            <th>Ghi chú</th>
                                            <th>Trạng thái</th>
                                            <th>Chức năng</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach var="item" items="${list}">
                                            <tr>
                                                <td>${item.full_name}</td>
                                                <td>${item.email}</td>
                                                <td>${item.phone}</td>
                                                <td>${item.birth_date}</td>
                                                <td>${item.courseName}</td>
                                                <td>${item.gender}</td>
                                                <td>${item.address}</td>
                                                <td contenteditable="true"
                                                    onblur="updateNote(${item.id}, this.innerText)"
                                                    class="editable-note">
                                                    ${item.note}
                                                </td>

                                                <td>${item.status}</td>
                                                <td>
                                                    <button class="btn btn-success btn-sm" onclick="showConfirmModal(${item.id})">✔️ Xác nhận</button>
                                                    <button class="btn btn-danger btn-sm" onclick="showRejectModal(${item.id})">❌ Chờ lớp</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Modal xác nhận -->
                    <div id="confirmModal" class="modal">
                        <div class="modal-content">
                            <p>Bạn có chắc chắn muốn xác nhận đăng ký này?</p>
                            <form method="post" action="Approve">
                                <input type="hidden" name="id" id="confirmId">
                                <input type="hidden" name="action" value="accept">
                                <button type="submit" class="btn btn-success">Xác nhận</button>
                                <button type="button" onclick="closeModal('confirmModal')" class="btn btn-secondary">Hủy</button>
                            </form>
                        </div>
                    </div>

                    <!-- Modal từ chối -->
                    <div id="rejectModal" class="modal">
                        <div class="modal-content">
                            <p>Bạn có chắc chắn chờ lớp</p>
                            <form method="post" action="Approve">
                                <input type="hidden" name="id" id="rejectId">
                                <input type="hidden" name="action" value="reject">
                                
                                <button type="submit" class="btn btn-danger">Đồng Ý</button>
                                <button type="button" onclick="closeModal('rejectModal')" class="btn btn-secondary">Hủy</button>
                            </form>
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
                                    function updateNote(id, newNote) {
                                        if (!id) {
                                            console.error("Thiếu ID");
                                            return;
                                        }

                                        fetch('Approve', {
                                            method: 'POST',
                                            headers: {
                                                'Content-Type': 'application/x-www-form-urlencoded'
                                            },
                                            body: "id=" + id + "&note=" + encodeURIComponent(newNote)
                                        })
                                                .then(response => {
                                                    if (!response.ok) {
                                                        console.error("Lỗi HTTP: ", response.status);
                                                        throw new Error("Lỗi HTTP");
                                                    }
                                                    return response.text();
                                                })
                                                .then(data => {
                                                    console.log("Ghi chú cập nhật thành công:", data);
                                                })
                                                .catch(error => {
                                                    alert("Không thể cập nhật ghi chú!");
                                                    console.error("Chi tiết lỗi:", error);
                                                });
                                    }

        </script>

        <script>
            function showConfirmModal(id) {
                document.getElementById("confirmId").value = id;
                document.getElementById("confirmModal").style.display = "block";
            }

            function showRejectModal(id) {
                document.getElementById("rejectId").value = id;
                document.getElementById("rejectModal").style.display = "block";
            }

            function closeModal(modalId) {
                document.getElementById(modalId).style.display = "none";
            }

            // Đóng modal khi bấm ra ngoài nội dung
            window.onclick = function (event) {
                let confirmModal = document.getElementById('confirmModal');
                let rejectModal = document.getElementById('rejectModal');

                if (event.target === confirmModal) {
                    confirmModal.style.display = "none";
                }

                if (event.target === rejectModal) {
                    rejectModal.style.display = "none";
                }
            }
        </script>


    </body>
</html>

