<%-- 
    Document   : courseStaff
    Created on : May 24, 2025, 12:06:36 PM
    Author     : Quang
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>ECOURSES - Online Courses HTML Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="Free HTML Templates" name="keywords">
        <meta content="Free HTML Templates" name="description">

        <!-- Favicon -->
        <link href="img/favicon.ico" rel="icon">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"> 

        <!-- Font Awesome -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
        <style>
            /* Định dạng container div của bảng nhập liệu */
            div.table-container {
                display: flex;
                justify-content: center;
                margin-top: 30px;
            }

            /* Định dạng bảng nhập liệu */
            div.table-container table {
                width: 100%;
                max-width: 800px;
                border-collapse: separate;
                border-spacing: 0;
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                font-family: 'Poppins', sans-serif;
            }

            /* Định dạng các ô trong bảng */
            div.table-container table td {
                padding: 15px;
                font-size: 16px;
                color: #333;
                vertical-align: middle;
            }

            /* Định dạng các ô chứa nhãn */
            div.table-container table td:first-child {
                font-weight: 600;
                width: 20%;
                background-color: #f8f9fa;
                border-right: 1px solid #dee2e6;
            }

            /* Định dạng các ô input */
            div.table-container table input[type="text"] {
                width: 100%;
                padding: 10px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                font-size: 14px;
                box-sizing: border-box;
                transition: border-color 0.3s ease;
            }

            div.table-container table input[type="text"]:focus {
                outline: none;
                border-color: #007bff;
                box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
            }

            /* Định dạng các nút */
            div.table-container table input[type="submit"] {
                padding: 10px 25px;
                margin: 0 10px;
                border: none;
                border-radius: 4px;
                font-size: 14px;
                font-weight: 500;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            div.table-container table input[name="add"] {
                background-color: #007bff;
                color: white;
            }

            div.table-container table input[name="add"]:hover {
                background-color: #0056b3;
            }

            div.table-container table input[name="update"] {
                background-color: #28a745;
                color: white;
            }

            div.table-container table input[name="update"]:hover {
                background-color: #218838;
            }

            /* Định dạng hàng chứa nút */
            div.table-container table tr:last-child td {
                text-align: center;
                padding: 20px;
            }

            /* Responsive design cho màn hình nhỏ */
            @media (max-width: 768px) {
                div.table-container table {
                    width: 100%;
                }

                div.table-container table td {
                    display: block;
                    width: 100%;
                    box-sizing: border-box;
                    border: none;
                    padding: 10px 15px;
                }

                div.table-container table td:first-child {
                    background-color: transparent;
                    font-weight: normal;
                    padding-bottom: 5px;
                }

                div.table-container table input[type="text"] {
                    margin-bottom: 15px;
                }

                div.table-container table tr:last-child td {
                    display: flex;
                    justify-content: center;
                    gap: 10px;
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

    <body>
        <!-- Topbar Start -->
        <div class="container-fluid d-none d-lg-block">
            <div class="row align-items-center py-4 px-xl-5">
                <div class="col-lg-3">
                    <a href="" class="text-decoration-none">
                        <h1 class="m-0"><span class="text-primary">E</span>COURSES</h1>
                    </a>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-map-marker-alt text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Our Office</h6>
                            <small>123 Street, New York, USA</small>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-envelope text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Email Us</h6>
                            <small>info@example.com</small>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3 text-right">
                    <div class="d-inline-flex align-items-center">
                        <i class="fa fa-2x fa-phone text-primary mr-3"></i>
                        <div class="text-left">
                            <h6 class="font-weight-semi-bold mb-1">Call Us</h6>
                            <small>+012 345 6789</small>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Topbar End -->
        <!-- Navbar Start -->
        <div class="container-fluid">
            <div class="row border-top px-xl-5">
                <div class="col-lg-3 d-none d-lg-block">
                    <a class="d-flex align-items-center justify-content-between bg-secondary w-100 text-decoration-none" data-toggle="collapse" href="#navbar-vertical" style="height: 67px; padding: 0 30px;">
                        <h5 class="text-primary m-0"><i class="fa fa-book-open mr-2"></i>Subjects</h5>
                        <i class="fa fa-angle-down text-primary"></i>
                    </a>
                    <nav class="collapse position-absolute navbar navbar-vertical navbar-light align-items-start p-0 border border-top-0 border-bottom-0 bg-light" id="navbar-vertical" style="width: calc(100% - 30px); z-index: 9;">
                        <div class="navbar-nav w-100">
                            <div class="nav-item dropdown">
                                <a href="#" class="nav-link" data-toggle="dropdown">Web Design <i class="fa fa-angle-down float-right mt-1"></i></a>
                                <div class="dropdown-menu position-absolute bg-secondary border-0 rounded-0 w-100 m-0">
                                    <a href="" class="dropdown-item">HTML</a>
                                    <a href="" class="dropdown-item">CSS</a>
                                    <a href="" class="dropdown-item">jQuery</a>
                                </div>
                            </div>
                            <a href="" class="nav-item nav-link">Apps Design</a>
                            <a href="" class="nav-item nav-link">Marketing</a>
                            <a href="" class="nav-item nav-link">Research</a>
                            <a href="" class="nav-item nav-link">SEO</a>
                        </div>
                    </nav>
                </div>
                <div class="col-lg-9">
                    <nav class="navbar navbar-expand-lg bg-light navbar-light py-3 py-lg-0 px-0">
                        <a href="" class="text-decoration-none d-block d-lg-none">
                            <h1 class="m-0"><span class="text-primary">E</span>COURSES</h1>
                        </a>
                        <button type="button" class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
                            <span class="navbar-toggler-icon"></span>
                        </button>
                        <div class="collapse navbar-collapse justify-content-between" id="navbarCollapse">
                            <div class="navbar-nav py-0">
                                <a href="index.html" class="nav-item nav-link">Home</a>
                                <a href="about.html" class="nav-item nav-link">About</a>
                                <a href="course.html" class="nav-item nav-link active">Courses</a>
                                <a href="teacher.html" class="nav-item nav-link">Teachers</a>
                                <div class="nav-item dropdown">
                                    <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">Blog</a>
                                    <div class="dropdown-menu rounded-0 m-0">
                                        <a href="blog.html" class="dropdown-item">Blog List</a>
                                        <a href="single.html" class="dropdown-item">Blog Detail</a>
                                    </div>
                                </div>
                                <a href="contact.html" class="nav-item nav-link">Contact</a>
                            </div>
                            <a class="btn btn-primary py-2 px-4 ml-auto d-none d-lg-block" href="">Join Now</a>
                        </div>
                    </nav>
                </div>
            </div>
        </div>

        <!-- Header Start -->
        <div class="container-fluid page-header" style="margin-bottom: -70px;">
            <div class="container">
                <div class="d-flex flex-column justify-content-center" style="min-height: 300px">
                    <h3 class="display-4 text-white text-uppercase">Courses</h3>
                    <div class="d-inline-flex text-white">
                        <p class="m-0 text-uppercase"><a class="text-white" href="">Home</a></p>
                        <i class="fa fa-angle-double-right pt-1 px-3"></i>
                        <p class="m-0 text-uppercase">Courses</p>
                    </div>
                </div>
            </div>
        </div>
        <!-- Header End -->
<!-- quang - nhân viên quản lý khóa học -->
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="text-center mb-5">
                    <h5 class="text-primary text-uppercase mb-3" style="letter-spacing: 5px;">Khóa Học</h5>
                    <h1>Quản Lý Khóa Học</h1>
                </div>

                <div class="table-container">
                    <form action="coursestaff" method="post">
                        <table>
                            <tr>
                                <td>Tên khóa học:</td>
                                <td><input type="text" name="name" value="${p.getName()}"></td>
                                <td>Học phí:</td>
                                <td><input type="text" name="fee" value="${p.getFee()}"></td>
                            </tr>
                            <tr>
                                <td>Tên thể loại:</td>
                                <td>
                                    <select name="type">
                                        <option value="0">Tất cả thể loại</option>
                                        <c:forEach items= "${data1}" var="c">
                                            <option value="${c.getId()}"
                                                    <c:if test="${p.getType()==c.getId()}">
                                                        selected 
                                                    </c:if>
                                                    > ${c.getName()}</option>

                                        </c:forEach>
                                    </select>
                                </td>
                                <td>Link ảnh:</td>
                                <td><input type="text" name="image" value="${p.getImage()}"></td>
                            </tr>

                            <tr>
                                <td>Miêu tả:</td>
                                <td><input type="text" name="des" value="${p.getDescription()}"></td>
                                <td><input type="hidden" name="id" value="${p.getId()}"></td>

                            </tr>
                            <tr>
                                <td ">
                                    <input type="submit" name="add" value="Thêm">
                                    <input type="submit" name="update" value="Sửa">
                                </td>
                            </tr>
                            <tr>
                                <c:if test="${not empty message}">
                                <p class="${success ? 'success' : 'error'}">${message}</p>
                            </c:if>
                            </tr>
                        </table>
                </div>
                </form>
                <div class="row">
                    <c:forEach items="${data}" var="item">
                        <div class="col-lg-4 col-md-6 mb-4">
                            <div class="rounded overflow-hidden mb-2">
                                <div style="position: relative; padding-top: 75%; overflow: hidden;">
                                    <img src="${item.image}" alt="Teacher Picture" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%; object-fit: cover;" onerror="this.src='${pageContext.request.contextPath}/images/default-image.jpg';">
                                </div>
                                <div class="bg-secondary p-4">
                                    <div class="d-flex justify-content-between mb-3">
                                        <small class="m-0"><i><h6>${item.id}</h6></i></small>
                                        <small class="m-0"><i><h6>${item.type}</h6></i></small>
                                    </div>
                                    <h2>${item.name}</h2>
                                    <h5>${item.description}</h5>

                                    <div class="border-top mt-4 pt-4">
                                        <div class="d-flex justify-content-between">
                                            <a href="coursestaff?id=${item.id}&mode=1" class="btn btn-edit">✏️ Sửa</a>
                                            <a href="coursestaff?id=${item.id}&mode=2" class="btn btn-delete">🗑️ Xóa</a>

                                            <h6 class="m-0">${item.fee}VND</h5>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <!-- Courses End -->





        <!-- Footer Start -->
        <div class="container-fluid bg-dark text-white py-5 px-sm-3 px-lg-5" style="margin-top: 90px;">
            <div class="row pt-5">
                <div class="col-lg-7 col-md-12">
                    <div class="row">
                        <div class="col-md-6 mb-5">
                            <h5 class="text-primary text-uppercase mb-4" style="letter-spacing: 5px;">Get In Touch</h5>
                            <p><i class="fa fa-map-marker-alt mr-2"></i>123 Street, New York, USA</p>
                            <p><i class="fa fa-phone-alt mr-2"></i>+012 345 67890</p>
                            <p><i class="fa fa-envelope mr-2"></i>info@example.com</p>
                            <div class="d-flex justify-content-start mt-4">
                                <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-twitter"></i></a>
                                <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-facebook-f"></i></a>
                                <a class="btn btn-outline-light btn-square mr-2" href="#"><i class="fab fa-linkedin-in"></i></a>
                                <a class="btn btn-outline-light btn-square" href="#"><i class="fab fa-instagram"></i></a>
                            </div>
                        </div>

                    </div>
                </div>

            </div>
        </div>

        <!-- Footer End -->


        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="fa fa-angle-double-up"></i></a>


        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Contact Javascript File -->
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>

        <!-- Template Javascript -->
        <script src="js/main.js"></script>
    </body>

</html>
