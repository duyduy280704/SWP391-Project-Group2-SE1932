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
            .filter-form {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
                align-items: center;
                background-color: #f9f9f9;
                padding: 15px;
                border-radius: 10px;
                margin-bottom: 25px;
                box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            }

            .filter-form input,
            .filter-form select {
                padding: 8px 12px;
                font-size: 14px;
                border: 1px solid #ccc;
                border-radius: 6px;
                min-width: 180px;
            }

            .filter-form button {
                padding: 8px 16px;
                font-size: 14px;
                background-color: #007bff;
                color: white;
                border: none;
                border-radius: 6px;
                cursor: pointer;
                transition: background-color 0.2s ease;
            }

            .filter-form button:hover {
                background-color: #0056b3;
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
                            <!-- Trang chủ -->
                            <a class="nav-link" href="staffhome">
                                <div class="sb-nav-link-icon"><i class="fas fa-home"></i></div>
                                Trang Chủ
                            </a>

                            <!-- Quản lý khóa học -->
                            <a class="nav-link" href="coursestaff">
                                <div class="sb-nav-link-icon"><i class="fas fa-book"></i></div>
                                Quản lý khóa học
                            </a>
                            <a class="nav-link" href="Sale">
                                <div class="sb-nav-link-icon"><i class="fas fa-blog"></i></div>
                                Quản lý khuyến mãi
                            </a>
                            <!-- Quản lý lớp học -->
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseClasses" aria-expanded="false" aria-controls="collapseClasses">
                                <div class="sb-nav-link-icon"><i class="fas fa-school"></i></div>
                                Quản lý lớp học
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseClasses" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="createClass">
                                        <div class="sb-nav-link-icon"><i class="fas fa-plus"></i></div>
                                        Tạo lớp mới
                                    </a>
                                    <a class="nav-link" href="classStudent">
                                        <div class="sb-nav-link-icon"><i class="fas fa-users"></i></div>
                                        Danh sách lớp
                                    </a>
                                    <!-- Quản lý phân lớp -->
                                    <a class="nav-link" href="AssignClass">
                                        <div class="sb-nav-link-icon"><i class="fas fa-th-list"></i></div>
                                        Quản lý phân lớp
                                    </a>
                                </nav>
                            </div>



                            <!-- Quản lý đăng ký -->
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseRegistration" aria-expanded="false" aria-controls="collapseRegistration">
                                <div class="sb-nav-link-icon"><i class="fas fa-clipboard-check"></i></div>
                                Quản lý đăng ký
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseRegistration" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="Approve">
                                        <div class="sb-nav-link-icon"><i class="fas fa-user-graduate"></i></div>
                                        Học Sinh
                                    </a>
                                    <a class="nav-link" href="TeacherApplication">
                                        <div class="sb-nav-link-icon"><i class="fas fa-chalkboard-teacher"></i></div>
                                        Giáo Viên
                                    </a>
                                </nav>
                            </div>

                            <!-- Quản lý đơn -->
                            <a class="nav-link" href="#" data-bs-toggle="collapse" data-bs-target="#collapseApplications" aria-expanded="false" aria-controls="collapseApplications">
                                <div class="sb-nav-link-icon"><i class="fas fa-file-alt"></i></div>
                                Quản lý đơn
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseApplications" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="listapplicationStu">
                                        <div class="sb-nav-link-icon"><i class="fas fa-user-graduate"></i></div>
                                        Học Sinh
                                    </a>
                                    <a class="nav-link" href="listapplicationTea">
                                        <div class="sb-nav-link-icon"><i class="fas fa-chalkboard-teacher"></i></div>
                                        Giáo Viên
                                    </a>
                                </nav>
                            </div>

                            <!-- Xử lý đơn chuyển lớp -->
                            <a class="nav-link" href="classTransfer">
                                <div class="sb-nav-link-icon"><i class="fas fa-exchange-alt"></i></div>
                                Xử lý đơn chuyển lớp
                            </a>

                            <!-- Thời khóa biểu -->
                            <a class="nav-link" href="listClassSchedule">
                                <div class="sb-nav-link-icon"><i class="fas fa-calendar-alt"></i></div>
                                Thời khóa biểu
                            </a>

                            <!-- Chấm công giáo viên -->
                            <a class="nav-link" href="teachingAttendance">
                                <div class="sb-nav-link-icon"><i class="fas fa-clock"></i></div>
                                Chấm công giáo viên
                            </a>

                            <!-- Quản lý sự kiện -->
                            <a class="nav-link" href="eventstaff">
                                <div class="sb-nav-link-icon"><i class="fas fa-calendar-check"></i></div>
                                Quản lý sự kiện
                            </a>

                            <!-- Quản lý blog -->
                            <a class="nav-link" href="Blog">
                                <div class="sb-nav-link-icon"><i class="fas fa-blog"></i></div>
                                Quản lý blog
                            </a>

                            <!-- Đánh giá -->
                            <a class="nav-link collapsed" href="#" data-bs-toggle="collapse" data-bs-target="#collapseFeedback" aria-expanded="false" aria-controls="collapseFeedback">
                                <div class="sb-nav-link-icon"><i class="fas fa-comments"></i></div>
                                Đánh giá
                                <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                            </a>
                            <div class="collapse" id="collapseFeedback" data-bs-parent="#sidenavAccordion">
                                <nav class="sb-sidenav-menu-nested nav">
                                    <a class="nav-link" href="feedback?mode=viewAll">
                                        <div class="sb-nav-link-icon"><i class="fas fa-comment-dots"></i></div>
                                        Phản hồi của học viên
                                    </a>
                                    <a class="nav-link" href="feedbackByTeacher?mode=staffView">
                                        <div class="sb-nav-link-icon"><i class="fas fa-star-half-alt"></i></div>
                                        Giáo viên đánh giá học sinh
                                    </a>
                                </nav>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
            <div id="layoutSidenav_content">
                <main>
                    <form method="get" action="TeacherApplication" class="filter-form" style="margin-bottom: 20px;">
                        <input type="text" name="keyword" placeholder="Tìm kiếm theo tên hoặc email" value="${keyword}" />

                        <select name="status">
                            <option value="">-- Tất cả trạng thái --</option>
                            <option value="Đang chờ" ${selectedStatus == 'Chờ duyệt' ? 'selected' : ''}>Chờ duyệt</option>
                            <option value="Đã qua vòng CV" ${selectedStatus == 'Đã qua vòng CV' ? 'selected' : ''}>Đã qua vòng CV</option>
                            <option value="Từ chối" ${selectedStatus == 'Từ chối' ? 'selected' : ''}>Từ chối</option>
                            <option value="Đã hoàn tất" ${selectedStatus == 'Đã hoàn tất' ? 'selected' : ''}>Đã hoàn tất</option>
                        </select>

                        <select name="type">
                            <option value="">-- Tất cả thể loại --</option>
                            <c:forEach var="tc" items="${typeCourses}">
                                <option value="${tc.id}" ${selectedType == tc.id ? 'selected' : ''}>${tc.name}</option>
                            </c:forEach>
                        </select>

                        <button type="submit">Lọc</button>
                    </form>
                    <div class="container-fluid px-4">

                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
                                Danh Sách Ứng Tuyển Giáo Viên
                            </div>
                            <div class="card-body">
                                <table class="course-list-table">
                                    <thead>
                                        <tr>
                                            <th>Họ tên</th>
                                            <th>Email</th>
                                            <th>CV</th>
                                            <th>Trạng thái</th>
                                            <th>Ngày sinh</th>
                                            <th>Giới tính</th>
                                            <th>Chuyên môn</th>
                                            <th>Loại khóa học</th>
                                            <th>Kinh nghiệm (năm)</th>
                                            <th>SĐT</th>
                                            <th>Lương Offer</th>
                                            <th>Hành động</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="t" items="${applications}">
                                            <tr>
                                                <td>${t.fullName}</td>
                                                <td>${t.email}</td>
                                                <td>
                                                    <a class="cv-link" href="${t.cvlink}" target="_blank">Xem CV</a>
                                                </td>
                                                <td>${t.status}</td>
                                                <td>${t.birthDate}</td>
                                                <td>${t.gender}</td>
                                                <td>${t.expertise}</td>
                                                <td>${t.typeCourseName}</td>
                                                <td>${t.yearOfExpertise}</td>
                                                <td>${t.phone}</td>
                                                <td class="salary-offer-cell" contenteditable="true"></td>


                                                <td>
                                                    <!-- Nút Pass CV -->
                                                    <button class="btn btn-primary" onclick="openConfirmModal(${t.id})">Pass CV</button>

                                                    <!-- Nút Reject CV -->
                                                    <button class="btn btn-warning" onclick="openRejectModal(${t.id})">Reject CV</button>

                                                    <!-- Nút Done -->

                                                    <button class="btn btn-success"
                                                            onclick="const row = this.closest('tr');
                                                                    const salary = row.querySelector('.salary-offer-cell').textContent.trim();
                                                                    if (!salary) {
                                                                        alert('Vui lòng nhập lương offer trước!');
                                                                        return;
                                                                    }
                                                                    openDoneModal(
                                                                            '${t.id}',
                                                                            '${t.fullName}',
                                                                            '${t.email}',
                                                                            '${t.birthDate}',
                                                                            '${t.gender}',
                                                                            '${t.expertise}',
                                                                            '${t.idTypeCourse}',
                                                                            '${t.yearOfExpertise}',
                                                                            '${t.phone}',
                                                                            salary);">
                                                        Pass Phỏng Vấn
                                                    </button>
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
                            <p>Bạn có chắc chắn muốn xác nhận CV này?</p>
                            <p>Lời nhắn</p>
                            <form method="post" action="TeacherApplication">
                                <input type="hidden" name="id" id="confirmId">
                                <input type="hidden" name="action" value="accept">
                                <textarea name="reason1" required class="form-control" rows="3"></textarea>
                                <br>
                                <button type="submit" class="btn btn-success">Xác nhận</button>
                                <button type="button" onclick="closeModal('confirmModal')" class="btn btn-secondary">Hủy</button>
                            </form>
                        </div>
                    </div>

                    <!-- Modal từ chối -->
                    <div id="rejectModal" class="modal">
                        <div class="modal-content">
                            <p>Nhập lý do từ chối:</p>
                            <form method="post" action="TeacherApplication">
                                <input type="hidden" name="id" id="rejectId">
                                <input type="hidden" name="action" value="reject">
                                <textarea name="reason2" required class="form-control" rows="3"></textarea>
                                <br>
                                <button type="submit" class="btn btn-danger">Gửi từ chối</button>
                                <button type="button" onclick="closeModal('rejectModal')" class="btn btn-secondary">Hủy</button>
                            </form>
                        </div>
                    </div>

                    <div id="doneModal" class="modal">
                        <div class="modal-content">
                            <p>Bạn có chắc chắn muốn xác nhận là <strong>Qua Vòng Phỏng Vấn</strong> không?</p>
                            <form method="post" action="TeacherApplication">
                                <input type="hidden" name="id" id="doneId">
                                <input type="hidden" name="action" value="done">

                                <!-- Truyền thông tin để insert Teacher -->
                                <input type="hidden" name="full_name" id="doneFullName">
                                <input type="hidden" name="email" id="doneEmail">
                                <input type="hidden" name="birth_date" id="doneBirthDate">
                                <input type="hidden" name="gender" id="doneGender">
                                <input type="hidden" name="expertise" id="doneExpertise">
                                <input type="hidden" name="id_type_course" id="doneTypeCourse">
                                <input type="hidden" name="years_of_experience" id="doneYearsExp">
                                <input type="hidden" name="phone" id="donePhone">
                                <input type="hidden" id="salaryOffer" name="salaryOffer">

                                <p>Ghi chú nội bộ (tùy chọn):</p>
                                <textarea name="reason3" class="form-control" rows="3"></textarea>
                                <br>
                                <button type="submit" class="btn btn-success">Xác nhận hoàn tất</button>
                                <button type="button" onclick="closeModal('doneModal')" class="btn btn-secondary">Hủy</button>
                            </form>
                        </div>
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


            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js" crossorigin="anonymous"></script>
        <script src="js/datatables-simple-demo.js"></script>
        <script src="https://cdn.ckeditor.com/4.22.1/standard/ckeditor.js"></script>
        <script>
                                    document.addEventListener('DOMContentLoaded', function () {
                                        // Initialize DataTables
                                        const dataTable = document.getElementById('datatablesSimple');
                                        if (dataTable) {
                                            new simpleDatatables.DataTable(dataTable);
                                            console.log('DataTables initialized for datatablesSimple');
                                        } else {
                                            console.error('DataTable element not found');
                                        }

                                        // Initialize CKEditor
                                        const descriptionTextarea = document.getElementById('description');
                                        if (descriptionTextarea && typeof CKEDITOR !== 'undefined') {
                                            // Destroy existing CKEditor instance if it exists
                                            if (CKEDITOR.instances.description) {
                                                CKEDITOR.instances.description.destroy(true);
                                            }

                                            // Initialize CKEditor
                                            CKEDITOR.replace('description', {
                                                height: 200,
                                                toolbar: [
                                                    {name: 'basic', items: ['Bold', 'Italic', 'Underline', 'Link', 'Unlink', 'NumberedList', 'BulletedList']},
                                                    {name: 'paragraph', items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight']},
                                                    {name: 'styles', items: ['Font', 'FontSize']},
                                                    {name: 'colors', items: ['TextColor', 'BGColor']}
                                                ]
                                            });

                                            // Set description value safely
                                            const descriptionValue = '${p.description != null ? fn:escapeXml(p.description) : ''}';
                                            CKEDITOR.instances.description.setData(descriptionValue);
                                            console.log('CKEditor initialized with description:', descriptionValue);
                                        } else {
                                            console.error('CKEditor not loaded or textarea not found.');
                                            if (!descriptionTextarea) {
                                                console.error('Textarea with id "description" not found.');
                                            }
                                            if (typeof CKEDITOR === 'undefined') {
                                                console.error('CKEDITOR object is undefined. Verify ckeditor.js is loaded.');
                                            }
                                        }
                                    });
        </script>
        <script>
            function openConfirmModal(id) {
                document.getElementById("confirmId").value = id;
                document.getElementById("confirmModal").style.display = "block";
            }

            function openRejectModal(id) {
                document.getElementById("rejectId").value = id;
                document.getElementById("rejectModal").style.display = "block";
            }

            function openDoneModal(id) {
                document.getElementById("doneId").value = id;
                document.getElementById("doneModal").style.display = "block";
            }

            function openDoneModal(id, fullName, email, birthDate, gender, expertise, idTypeCourse, yearsExp, phone, salaryOffer) {
                document.getElementById('doneId').value = id;
                document.getElementById('doneFullName').value = fullName;
                document.getElementById('doneEmail').value = email;
                document.getElementById('doneBirthDate').value = birthDate;
                document.getElementById('doneGender').value = gender;
                document.getElementById('doneExpertise').value = expertise;
                document.getElementById('doneTypeCourse').value = idTypeCourse;
                document.getElementById('doneYearsExp').value = yearsExp;
                document.getElementById('donePhone').value = phone;
                document.getElementById("salaryOffer").value = salaryOffer;

                // Mở modal
                document.getElementById('doneModal').style.display = 'block';
            }
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
    </body>
</html>

