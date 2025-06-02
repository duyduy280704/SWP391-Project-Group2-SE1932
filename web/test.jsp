<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thời Khóa Biểu</title>
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
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                background-color: #f4f7fb;
                padding: 30px 20px;
                display: flex;
                justify-content: center;
                align-items: flex-start;
                min-height: 100vh;
                color: #333;
            }

            h2 {
                text-align: center;
                color: #2c3e50;
                margin-bottom: 25px;
                font-size: 36px;
                font-weight: 700;
                letter-spacing: 1px;
            }

            .table-container {
                width: 100%;
                max-width: 1000px;
                background: #ffffff;
                border-radius: 12px;
                box-shadow: 0 6px 20px rgba(0, 0, 0, 0.08);
                padding: 20px 25px;
            }

            .selector-container {
                display: flex;
                justify-content: center;
                gap: 20px;
                margin-bottom: 20px;
            }

            .selector-container select {
                padding: 10px;
                font-size: 16px;
                border-radius: 8px;
                border: 1px solid #ccc;
                background-color: #fff;
                cursor: pointer;
                outline: none;
                transition: border-color 0.3s ease;
            }

            .selector-container select:focus {
                border-color: #3498db;
            }

            table {
                width: 100%;
                border-collapse: separate;
                border-spacing: 0;
                border-radius: 12px;
                overflow: hidden;
                box-shadow: 0 3px 6px rgba(0,0,0,0.05);
            }

            th, td {
                padding: 14px 18px;
                text-align: center;
                font-size: 16px;
                color: #444;
                border-bottom: 1px solid #e1e5ea;
            }

            th {
                background-color: #3498db;
                color: #fff;
                font-weight: 600;
                text-transform: uppercase;
                letter-spacing: 0.05em;
            }

            tbody tr:hover {
                background-color: #f1f8ff;
                cursor: default;
                transition: background-color 0.3s ease;
            }

            tbody tr:last-child td {
                border-bottom: none;
            }

            .error-message {
                color: #e74c3c;
                text-align: center;
                margin: 20px 0;
                font-size: 18px;
                font-weight: 600;
            }
        </style>
    </head>
    <body>
        <!-- Topbar Start -->
        

        <div class="table-container">
            <h2>Thời khóa biểu học sinh</h2>
            <div class="selector-container">
                <form action="scheduleStudent" method="get">
                    <label for="year">Chọn năm: </label>
                    <select name="year" id="year" onchange="this.form.submit()">
                        <c:forEach var="year" items="${years}">
                            <option value="${year}" <c:if test="${year == selectedYear}">selected</c:if>>${year}</option>
                        </c:forEach>
                    </select>
                    <label for="week">Chọn tuần: </label>
                    <select name="week" id="week" onchange="this.form.submit()">
                        <c:forEach var="week" items="${weeks}">
                            <option value="${week.startDate}" <c:if test="${week.startDate == selectedWeek}">selected</c:if>>Tuần ${week.weekNumber} (${week.displayStartDate} - ${week.displayEndDate})</option>
                        </c:forEach>
                    </select>
                </form>
            </div>
            <c:if test="${empty scheduleStudent}">
                <p class="error-message">Không có dữ liệu thời khóa biểu cho tuần này!</p>
            </c:if>
            <c:if test="${not empty scheduleStudent}">
                <table>
                    <thead>
                        <tr>
                            <th>Thứ</th>
                            <th>Ngày</th>
                            <th>Lớp</th>
                            <th>Bắt đầu</th>
                            <th>Kết thúc</th>
                            <th>Phòng học</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="day" items="${weekDays}">
                            <c:set var="hasSchedule" value="false" />
                            <c:forEach var="s" items="${scheduleStudent}">
                                <c:if test="${s.dayVN == day}">
                                    <c:set var="hasSchedule" value="true" />
                                </c:if>
                            </c:forEach>

                            <c:choose>
                                <c:when test="${hasSchedule}">
                                    <c:forEach var="s" items="${scheduleStudent}">
                                        <c:if test="${s.dayVN == day}">
                                            <tr>
                                                <td>${s.dayVN}</td>
                                                <td>
                                                    <fmt:parseDate value="${s.day}" pattern="yyyy-MM-dd" var="parsedDate" />
                                                    <fmt:formatDate value="${parsedDate}" pattern="dd/MM" />
                                                </td>
                                                <td>${s.nameClass}</td>
                                                <td>${fn:substring(s.startTime, 0, 5)}</td>
                                                <td>${fn:substring(s.endTime, 0, 5)}</td>
                                                <td>${s.room}</td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td>${day}</td>
                                        <td colspan="5"></td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>

        <!-- Footer Start -->
        <footer class="bg-dark text-white pt-5 pb-4">
            <div class="container text-md-left">
                <div class="row text-md-left">
                    <!-- Liên hệ -->
                    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                        <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Liên Hệ</h5>
                        <p><i class="fa fa-map-marker-alt mr-2"></i> 
                            <c:out value="${setting.address}" default="Địa chỉ chưa cập nhật" />
                        </p>
                        <p><i class="fa fa-phone-alt mr-2"></i> 
                            <c:out value="${setting.phone}" default="Số điện thoại chưa cập nhật" />
                        </p>
                        <p><i class="fa fa-envelope mr-2"></i> 
                            <c:out value="${setting.email}" default="Email chưa cập nhật" />
                        </p>
                        <div class="mt-3">
                            <a class="btn btn-outline-light btn-sm mr-2" href="${setting.facebookLink != null ? setting.facebookLink : '#'}">
                                <i class="fab fa-facebook-f"></i>
                            </a>
                            <a class="btn btn-outline-light btn-sm mr-2" href="${setting.instagramLink != null ? setting.instagramLink : '#'}">
                                <i class="fab fa-instagram"></i>
                            </a>
                            <a class="btn btn-outline-light btn-sm mr-2" href="${setting.youtubeLink != null ? setting.youtubeLink : '#'}">
                                <i class="fab fa-youtube"></i>
                            </a>
                        </div>
                    </div>
                    <!-- Khoá học -->
                    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                        <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Khoá học</h5>
                        <ul class="list-unstyled">
                            <c:forEach var="t" items="${applicationScope.typeList}">
                                <li>
                                    <a href="#" class="text-white">
                                        <i class="fa fa-angle-right mr-2"></i> ${t.name}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                    <!-- Thông tin thêm -->
                    <div class="col-md-4 col-lg-4 col-xl-4 mx-auto mt-3">
                        <h5 class="text-uppercase mb-4 font-weight-bold text-primary">Về Chúng Tôi</h5>
                        <p><c:out value="${setting.about}" default="Thông tin chưa cập nhật." /></p>
                    </div>
                </div>
                <hr class="mb-4">
                <!-- Bản quyền -->
                <div class="row align-items-center">
                    <div class="col-md-7 col-lg-8">
                        <p class="text-white">
                            <c:out value="${setting.copyright}" default="© 2025 Trung Tâm Năng Khiếu. All rights reserved." />
                        </p>
                    </div>
                    <div class="col-md-5 col-lg-4">
                        <div class="text-right">
                            <a class="text-white" href="${setting.policyLink != null ? setting.policyLink : '#'}">Chính sách</a> |
                            <a class="text-white" href="${setting.termsLink != null ? setting.termsLink : '#'}">Điều khoản</a>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
        <!-- Footer End -->

        <!-- Back to Top -->
        <a href="#" class="btn btn-lg btn-primary btn-lg-square back-to-top"><i class="fa fa-angle-double-up"></i></a>

        <!-- JavaScript Libraries -->
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
        <script src="lib/easing/easing.min.js"></script>
        <script src="lib/owlcarousel/owl.carousel.min.js"></script>
        <script src="mail/jqBootstrapValidation.min.js"></script>
        <script src="mail/contact.js"></script>
        <script src="js/main.js"></script>
    </body>
</html>
