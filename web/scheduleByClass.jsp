<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, models.Schedules" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Chi tiết thời khóa biểu lớp</title>
    </head>
    <body>
        <h2>Chi tiết thời khóa biểu ${className}</h2>
        ${msg}
        <!-- Form lọc ngày -->
        <form action="scheduleByClass" method="get">
            <input type="hidden" name="id" value="${classId}" />
            <label for="date">Lọc theo ngày:</label>
            <input type="date" name="date" value="${date}" />
            <input type="submit" value="Lọc" />
        </form>

        <!-- Nút trở về danh sách -->
        <a href="scheduleByClass?id=${classId}">Xóa bộ lọc</a>
<!-- Form tìm kiếm -->
<form action="scheduleByClass" method="get">
    <input type="hidden" name="id" value="${classId}" />
    <input type="hidden" name="mode" value="searchByClass" />
    
    <label for="keyword">Tìm kiếm:</label>
    <input type="text" name="keyword" placeholder="Nhập từ khóa..." value="${param.keyword}" />
    <input type="submit" value="Tìm" />
</form>

        <!-- Danh sách thời khóa biểu -->
        <table border="1">
            <tr>
                <th>STT</th>
                <th>Giáo viên</th>
                <th>Ngày</th>
                <th>Bắt đầu</th>
                <th>Kết thúc</th>
                <th>Phòng</th>
                <th>Hành động</th>
            </tr>
            <c:forEach var="item" items="${scheduleList}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${item.teacher}</td>
                    <td>${item.day}</td>
                    <td>${item.startTime}</td>
                    <td>${item.endTime}</td>
                    <td>${item.room}</td>
                    <td>
                        <a href="scheduleByClass?sid=${item.id}&mode=2" class="action-btn edit">✎</a>
                        <a href="#" class="action-btn delete"
               onclick="showDeleteConfirmation('${item.id}', '${classId}'); return false;">🗑</a>
                    </td>

                </tr>
            </c:forEach>
        </table>
        <!-- SweetAlert2 CDN -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
    function showDeleteConfirmation(scheduleId, classId) {
        Swal.fire({
            title: 'Xác nhận xóa',
            text: 'Bạn có chắc chắn muốn xóa lịch học này không?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Xác Nhận',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
                // Điều hướng tới servlet với scheduleId và classId
                window.location.href = 'scheduleByClass?sid=' + scheduleId + '&id=' + classId + '&mode=3';
            }
        });
    }
</script>



    </body>
</html>
