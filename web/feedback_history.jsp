<%-- 
    Document   : feedback_history
    Created on : Jun 30, 2025, 9:33:12 PM
    Author     : Admin
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Lịch sử phản hồi của tôi</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container mt-4">
            <h2 class="text-center mb-4">Lịch sử phản hồi của bạn</h2>

            <c:if test="${empty feedbackList}">
                <div class="alert alert-info text-center">Bạn chưa gửi phản hồi nào.</div>
            </c:if>

            <table class="table table-bordered table-hover">
                <thead class="table-primary">
                    <tr>
                        <th>STT</th>
                        <th>Lớp</th>
                        <th>Ngày gửi</th>
                        <th>Nội dung</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="f" items="${feedbackList}" varStatus="loop">
                        <tr>
                            <td>${loop.index + 1}</td>
                            <td>${f.className}</td>
                            <td>${f.feedbackDate}</td>
                            <td>${f.feedbackText}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="studentHome" class="btn btn-secondary">← Quay lại</a>
        </div>
    </body>
</html>