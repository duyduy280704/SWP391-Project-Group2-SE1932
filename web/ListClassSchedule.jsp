<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Danh sách lớp có lịch học</title>
        <style>
            body {
                font-family: Arial;
                background: #f2f2f2;
                padding: 20px;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                background: white;
                box-shadow: 0 0 5px #ccc;
            }
            th, td {
                padding: 12px;
                border-bottom: 1px solid #ccc;
                text-align: left;
            }
            th {
                background: #4CAF50;
                color: white;
            }
            tr:hover {
                background: #f5f5f5;
            }
            .btn {
                padding: 8px 12px;
                border: none;
                cursor: pointer;
                border-radius: 4px;
            }
            .btn-delete {
                background: #e74c3c;
                color: white;
            }
            .btn-add {
                background: #3498db;
                color: white;
                margin-bottom: 10px;
            }
            .search-box {
                margin-bottom: 10px;
            }
            a {
                text-decoration: none;
                color: #2c3e50;
                font-weight: bold;
            }
            a:hover {
                color: #2980b9;
            }
        </style>
    </head>
    <body>
        <h2>Danh sách lớp có thời khóa biểu</h2>

        <form method="post" action="listClassSchedule" class="search-box">
            <input type="text" name="search" placeholder="Tìm kiếm lớp...">
            <input type="hidden" name="action" value="search">
            <button type="submit" class="btn">Tìm</button>        
        </form>

        <a href="listClassSchedule?mode=1" class="btn btn-add">Tạo thời khóa biểu</a>
        <table>
            <tr>
                <th>Tên lớp</th>
                <th>Thao tác</th>
            </tr>
            <c:forEach var="c" items="${classList}">
                <tr>
                    <td>
                        <a href="scheduleByClass?id=${c.id_class}&name=${c.name_class}&mode=1">${c.name_class}</a>
                    </td>
                    <td>
                        <form method="post" action="listClassSchedule" onsubmit="return confirm('Xóa toàn bộ thời khóa biểu của lớp này?');">
                            <input type="hidden" name="classId" value="${c.id_class}">
                            <input type="hidden" name="action" value="delete">
                            <button type="submit" class="btn btn-delete">Xóa lịch học</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
