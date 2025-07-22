<%-- 
    Document   : setting
    Created on : Jun 12, 2025, 7:22:52 AM
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
            /* Container styling for the table */
            table {
                width: 100%;
                max-width: 1200px; /* Increased width for a wider layout */
                margin: 30px auto;
                border-collapse: collapse;
                font-family: 'Segoe UI', Arial, sans-serif;
                background-color: #ffffff;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                border-radius: 8px;
                overflow: hidden;
            }

            /* Styling for table cells */
            td {
                padding: 15px 20px; /* Increased padding for more space */
                vertical-align: middle;
                border-bottom: 1px solid #e8ecef;
            }

            /* Label cells */
            td:nth-child(odd) {
                font-weight: 600;
                color: #2c3e50;
                width: 25%; /* Wider label column */
                background-color: #f8fafc;
                text-align: right;
            }

            /* Input field styling */
            input[type="text"] {
                width: 100%;
                padding: 12px; /* Larger input fields */
                border: 1px solid #ced4da;
                border-radius: 6px;
                font-size: 16px;
                transition: border-color 0.3s ease, box-shadow 0.3s ease;
                box-sizing: border-box;
            }

            input[type="text"]:focus {
                outline: none;
                border-color: #1e90ff;
                box-shadow: 0 0 8px rgba(30, 144, 255, 0.2);
            }

            /* Submit button styling */
            input[type="submit"] {
                background-color: #1e90ff;
                color: white;
                padding: 12px 30px; /* Larger button */
                border: none;
                border-radius: 6px;
                cursor: pointer;
                font-size: 16px;
                font-weight: 500;
                transition: background-color 0.3s ease, transform 0.2s ease;
                display: block;
                margin: 0 auto;
            }

            input[type="submit"]:hover {
                background-color: #1565c0;
                transform: translateY(-2px);
            }

            /* Hover effect for table rows */
            tr:hover {
                background-color: #f1faff;
            }

            /* Responsive design for smaller screens */
            @media (max-width: 768px) {
                table {
                    width: 95%;
                }

                td {
                    display: block;
                    width: 100%;
                    box-sizing: border-box;
                    text-align: left;
                }

                td:nth-child(odd) {
                    background-color: #f0f4f8;
                    font-weight: 500;
                    padding-bottom: 5px;
                }

                input[type="text"] {
                    margin-bottom: 15px;
                }

                input[type="submit"] {
                    width: 100%;
                    padding: 12px;
                }
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

                        <h1 class="mt-4">Bảng Điều Khiển</h1>
                        <div><c:if test="${not empty message}">
                                <p class="${success ? 'success' : 'error'}">${message}</p>
                            </c:if></div>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">Quản Lý</li>
                        </ol>
                        <form action="setting" method="post">
                            <h4>Thông tin liên hệ</h4>
                            <table>
                                <tr>
                                    <td>Địa chỉ</td>
                                    <td><input type="text" name="address" value="${data.getAddress()}"></td>
                                    <td>Số điện thoại</td>
                                    <td><input type="text" name="phone" value="${data.getPhone()}"></td>
                                </tr>
                                <tr>
                                    <td>Email</td>
                                    <td><input type="text" name="email" value="${data.getEmail()}"></td>
                                    <td>Facebook</td>
                                    <td><input type="text" name="facebook" value="${data.getFacebook()}"></td>
                                </tr>
                                <tr>
                                    <td>Instagram</td>
                                    <td><input type="text" name="instagram" value="${data.getInstagram()}"></td>
                                    <td>Youtube</td>
                                    <td><input type="text" name="youtube" value="${data.getYoutube()}"></td>
                                </tr>
                                <tr>
                                    <td>Thông tin</td>
                                    <td><input type="text" name="about" value="${data.getAbout()}"></td>
                                    <td>Bản quyền</td>
                                    <td><input type="text" name="copyright" value="${data.getCopyright()}"></td>
                                </tr>
                                <tr>
                                    <td>Chính sách</td>
                                    <td><input type="text" name="policy" value="${data.getPolicy()}"></td>
                                    <td>Điều khoản</td>
                                    <td><input type="text" name="terms" value="${data.getTerms()}"></td>
                                </tr>
                                <tr>
                                    <td></td>
                                    <td><input type="submit" name="update" value="Lưu Thông Tin"></td>
                                    <td></td>
                                    <td></td>
                                </tr>

                            </table>
                        </form>

                        <form action="setabout" method="post" enctype="multipart/form-data">
                            <h4>Thông tin về trung tâm</h4>  
                            <table>
                                <tr>
                                    <td>

                                        <table>
                                            <input type="text" name="id" value="${p.getId()}" hidden="">
                                            <tr>
                                                <td>Tiêu đề</td>
                                                <td><input type="text" name="tieude1" value="${p.getTitle()}"></td>
                                            </tr>
                                            <tr>
                                                <td>Nội dung</td>
                                                <td><textarea name="description" id="description" cols="100" rows="5" >${p.getContent()}</textarea></td>
                                            </tr>
                                            <tr>
                                                <td>Ảnh</td>
                                                <td><input type="file" name="anh1" ></td>
                                            </tr>
                                            <tr>

                                                <td><input type="submit" name="add1" value="Thêm"></td>
                                                <td><input type="submit" name="update1" value="Lưu"></td>
                                            </tr>
                                        </table>
                                    </td>

                                </tr>

                                <tr>
                                    <td>
                                        <table>
                                            <thead>
                                                <tr>
                                                    <td>Tiêu đề</td>
                                                    <td>Nội dung</td>
                                                    <td>Ảnh</td>
                                                    <td>Chức năng</td>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${dataAbout}" var="item">
                                                    <tr>
                                                        <td>${item.getTitle()}</td>
                                                        <td>${item.getContent()}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty item.image}">
                                                                    <img src="imageabout?id=${item.id}" alt="Course Picture" style="max-width: 100px; max-height: 100px;" onerror="this.src='images/no-image.png'; this.alt='Image not available';">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span>No Image</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td><a href="setabout?id=${item.getId()}&mode=1" class="btn btn-edit">✏️ Sửa</a>
                                                            <a href="setabout?id=${item.id}&mode=2" class="btn btn-delete" 
                                                               onclick="return confirm('Bạn có chắc chắn muốn xóa không?')">🗑️ Xóa</a></td>
                                                    </tr>
                                                </c:forEach>

                                            </tbody>
                                        </table>
                                    </td>
                                </tr>

                            </table>
                        </form>
                        <h4>Thông tin hiển thị</h4>
                        <form action="setbanner" method="post" enctype="multipart/form-data">
                            <table>
                                <tr>
                                    <td>

                                        <table>
                                            <input type="text" name="id2" value="${s.getId()}" hidden="">
                                            <tr>
                                                <td>Tiêu đề</td>
                                                <td><input type="text" name="tieude2" value="${s.getTitle()}"></td>
                                            </tr>

                                            <tr>
                                                <td>Ảnh</td>
                                                <td><input type="file" name="anh2" ></td>
                                            </tr>
                                            <tr>
                                                <td><input type="submit" name="add2" value="Thêm"></td>
                                                <td><input type="submit" name="update2" value="Lưu"></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <table>
                                            <thead>
                                                <tr>

                                                    <td>Tiêu đề</td>
                                                    <td>Ảnh</td>
                                                    <td>Chức năng</td>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${dataBanner}" var="item">
                                                    <tr>

                                                        <td>${item.getTitle()}</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${not empty item.image}">
                                                                    <img src="imagebanner?id2=${item.id}" alt="Course Picture" style="max-width: 100px; max-height: 100px;" onerror="this.src='images/no-image.png'; this.alt='Image not available';">
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <span>No Image</span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td><a href="setbanner?id2=${item.getId()}&mode=3" class="btn btn-edit">✏️ Sửa</a>
                                                            <a href="setbanner?id2=${item.id}&mode=4" class="btn btn-delete" 
                                                               onclick="return confirm('Bạn có chắc chắn muốn xóa không?')">🗑️ Xóa</a></td>
                                                    </tr>
                                                </c:forEach>

                                            </tbody>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </form>
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

