<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Danh sách đăng ký</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f6f9fc;
                margin: 30px;
            }

            h2 {
                color: #333;
            }

            .filter-form, .assign-form {
                margin-bottom: 20px;
                background-color: #fff;
                padding: 16px;
                border: 1px solid #ddd;
                border-radius: 8px;
            }

            select, button {
                padding: 6px 12px;
                margin-right: 12px;
                border-radius: 4px;
                border: 1px solid #ccc;
            }

            button {
                background-color: #007bff;
                color: white;
                cursor: pointer;
            }

            button:hover {
                background-color: #0056b3;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                background-color: #fff;
                border-radius: 8px;
                overflow: hidden;
                margin-bottom: 20px;
            }

            th, td {
                padding: 12px;
                border: 1px solid #eee;
                text-align: center;
            }

            th {
                background-color: #f0f0f0;
                color: #333;
            }

            tr:nth-child(even) {
                background-color: #f9f9f9;
            }

            .footer {
                margin-top: 30px;
                color: #666;
                font-size: 13px;
                text-align: center;
            }
        </style>
    </head>
    <body>

        <h2>📋 Danh sách đăng ký học viên</h2>

        <!-- Form lọc -->
        <form class="filter-form" method="get" action="AssignClass">
            <label>Khóa học:</label>
            <select name="courseId">
                <option value="">Tất cả</option>
                <c:forEach var="c" items="${courseList}">
                    <option value="${c.id}" ${param.courseId == c.id ? "selected" : ""}>${c.name}</option>
                </c:forEach>
            </select>

            <label>Trạng thái:</label>
            <select name="status">
                <option value="">Tất cả</option>
                <option value="chưa phân lớp" ${param.status == 'chưa phân lớp' ? "selected" : ""}>Chưa phân lớp</option>
                <option value="đã phân lớp" ${param.status == 'đã phân lớp' ? "selected" : ""}>Đã phân lớp</option>
            </select>

            <label>Tên học viên:</label>
            <input type="text" name="studentName" value="${param.studentName}" placeholder="Nhập tên..." />

            <button type="submit">🔍 Lọc</button>
        </form>

        <c:if test="${not empty sessionScope.messages}">
            <div class="alert">
                <ul>
                    <c:forEach var="msg" items="${sessionScope.messages}">
                        <li>${msg}</li>
                        </c:forEach>
                </ul>
            </div>
            <c:remove var="messages" scope="session" />
        </c:if>



        <!-- Danh sách -->
        <form class="assign-form" method="post" action="AssignClass">
            <table>
                <thead>
                    <tr>
                        <th>Học viên</th>
                        <th>Khóa học</th>
                        <th>Trạng thái</th>
                        <th>Ghi chú</th>
                        <th>Phân lớp</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="r" items="${regisitions}">
                        <tr>
                            <td>${r.studentName}</td>
                            <td>${r.courseName}</td>
                            <td>${r.status}</td>
                            <td>${r.note}</td>
                            <td>
                                <select name="regisitionId_${r.id}">
                                    <option value="">-- Chọn lớp --</option>
                                    <c:forEach var="cls" items="${classByCourse[r.courseId]}">
                                        <option value="${cls.id_class}">${cls.name_class}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>

            <button type="submit">✅ Phân lớp</button>
        </form>


        <div class="footer">
            Hệ thống quản lý trung tâm năng khiếu - BigDream © 2025
        </div>

    </body>
</html>
