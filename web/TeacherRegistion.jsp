<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký làm giáo viên - BIGDREAM</title>
    <link href="https://cdn.tailwindcss.com" rel="stylesheet">
    <style>
        
        .form-container {
            max-width: 42rem; 
            margin-left: auto;
            margin-right: auto;
            background-color: #ffffff;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 0.5rem; 
            padding: 1.5rem; 
            margin-top: 2.5rem; 
        }
        .input-field {
            border-color: #d1d5db; 
            padding: 0.75rem; 
            width: 100%;
            border-radius: 0.375rem; 
            transition: border-color 0.2s ease, box-shadow 0.2s ease;
        }
        .input-field:focus {
            border-color: #f97316; 
            box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.5); /* focus:ring-orange-500 */
            outline: none;
        }
        .label {
            display: block;
            font-size: 0.875rem; /* text-sm */
            font-weight: 500; /* font-medium */
            color: #374151; /* text-gray-700 */
            margin-bottom: 0.25rem; /* mb-1 */
        }
        .error-message {
            color: #ef4444; 
            font-size: 0.875rem; 
            margin-top: 0.25rem;
        }
        .submit-btn {
            background-color: #f97316; 
            color: #ffffff;
            padding-left: 1.5rem; 
            padding-right: 1.5rem;
            padding-top: 0.5rem;
            padding-bottom: 0.5rem;
            border-radius: 0.375rem; 
            transition: background-color 0.2s ease;
            font-weight: 600; /* font-semibold */
        }
        .submit-btn:hover {
            background-color: #ea580c; 
        }
        .terms-link {
            color: #f97316; 
            font-weight: 600; 
            text-decoration: underline;
            transition: color 0.2s ease;
        }
        .terms-link:hover {
            color: #ea580c; /* hover:text-orange-700 */
        }
        .modal {
            position: fixed;
            inset: 0;
            background-color: rgba(0, 0, 0, 0.5);
            display: flex;
            justify-content: center;
            align-items: center;
            z-index: 50; /* z-50 */
            display: none;
        }
        .modal-content {
            background-color: #ffffff;
            padding: 1.5rem; /* p-6 */
            border-radius: 0.5rem; /* rounded-lg */
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            max-width: 28rem; /* max-w-md */
            width: 100%;
            max-height: 80vh;
            overflow-y: auto;
        }
        .close-btn {
            background-color: #f97316; 
            color: #ffffff;
            padding-left: 1rem; 
            padding-right: 1rem;
            padding-top: 0.5rem; 
            padding-bottom: 0.5rem;
            border-radius: 0.375rem; 
            transition: background-color 0.2s ease;
            margin-top: 1rem; 
        }
        .close-btn:hover {
            background-color: #ea580c; 
        }
        
        .date-input {
            border-color: #d1d5db;
            padding: 0.75rem; 
            width: 100%;
            border-radius: 0.375rem; 
            transition: border-color 0.2s ease, box-shadow 0.2s ease;
        }
        .date-input:focus {
            border-color: #f97316; 
            box-shadow: 0 0 0 3px rgba(249, 115, 22, 0.5); 
            outline: none;
        }
        input[type="date"]::-webkit-calendar-picker-indicator {
            cursor: pointer;
        }
    </style>
</head>
<body class="bg-gray-50 font-sans">
    <div class="container mx-auto p-4">
        <header class="text-center mb-8">
            <h1 class="text-3xl font-bold text-gray-800">Đăng ký làm giáo viên</h1>
            <p class="text-gray-600 mt-2">Trở thành một phần của đội ngũ BIGDREAM!</p>
        </header>
        <div class="form-container">
            <c:if test="${not empty message}">
                <p class="error-message">${message}</p>
            </c:if>
            <form action="resgiterTeacher" method="post" enctype="multipart/form-data" class="space-y-6">
                <div>
                    <label class="label">Họ và tên:</label>
                    <input type="text" name="fullName" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Email:</label>
                    <input type="email" name="email" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Số điện thoại:</label>
                    <input type="email" name="phone" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Link CV:</label>
                    <input type="text" name="cvLink" class="input-field"required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Ngày sinh:</label>
                    <input type="date" name="birthDate" class="date-input" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Giới tính:</label> 
                    <select name="gender" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng chọn vào đây')" oninput="this.setCustomValidity('')">
                        <option value="Nam">Nam</option>
                        <option value="Nữ">Nữ</option>
                        <option value="Khác">Khác</option>
                    </select>
                </div>
                <div>
                    <label class="label">Chuyên môn:</label>
                    <input type="text" name="expertise" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Loại khóa học:</label>
                    <select name="idTypeCourse" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng chọn vào đây')" oninput="this.setCustomValidity('')">
                        <c:forEach var="o" items="${courseTypes}">
                            <option value="${o.getId()}">${o.getName()}</option>
                        </c:forEach>
                    </select>
                </div>
                <div>
                    <label class="label">Số năm kinh nghiệm:</label>
                    <input type="text" name="yearsOfExperience" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">Ảnh:</label>
                    <input type="file" name="image" class="input-field" required oninvalid="this.setCustomValidity('Vui lòng nhập vào đây')" oninput="this.setCustomValidity('')">
                </div>
                <div>
                    <label class="label">
                        <input type="checkbox" name="agreeTerms" class="mr-2" required oninvalid="this.setCustomValidity('Vui lòng chọn vào đây')" oninput="this.setCustomValidity('')">
                        Tôi đồng ý với các <a href="#" onclick="document.getElementById('termsModal').style.display = 'flex'" class="terms-link">điều khoản</a> và cam kết của ứng dụng.
                    </label>
                </div>
                <button type="submit" class="submit-btn">Đăng ký</button>
            </form>
        </div>
    </div>

    <div id="termsModal" class="modal">
        <div class="modal-content">
            <h2 class="text-xl font-bold text-gray-800 mb-4">Điều khoản và Cam kết dành cho Giáo viên</h2>
            <div class="space-y-3 text-gray-700">
                <p>1. Người đăng ký cam kết cung cấp đầy đủ và chính xác thông tin cá nhân, chuyên môn, kinh nghiệm giảng dạy cũng như các giấy tờ liên quan theo yêu cầu của trung tâm. Mọi hành vi khai báo sai sự thật có thể dẫn đến việc từ chối hợp tác hoặc hủy bỏ đăng ký.</p>
                <p>2. Trung tâm cam kết bảo mật tuyệt đối các thông tin cá nhân và hồ sơ của giáo viên. Các dữ liệu này sẽ chỉ được sử dụng cho mục đích tuyển dụng, phân công giảng dạy, và quản lý nhân sự nội bộ. Giáo viên có quyền yêu cầu chỉnh sửa hoặc xóa thông tin cá nhân khi cần thiết.</p>
                <p>3. Giáo viên đồng ý tuân thủ mọi nội quy, quy định hoạt động, và chính sách chuyên môn của trung tâm. Bao gồm nhưng không giới hạn ở việc đảm bảo giờ giấc giảng dạy, thực hiện đầy đủ nội dung đào tạo, không tự ý nghỉ dạy khi chưa được phê duyệt.</p>
                <p>4. Trung tâm có quyền đánh giá định kỳ về chất lượng giảng dạy thông qua phản hồi từ học viên, phụ huynh và các hình thức kiểm tra chuyên môn. Những giáo viên không đạt yêu cầu có thể bị tạm ngưng hoặc chấm dứt hợp tác.</p>
                <p>5. Giáo viên có nghĩa vụ tham gia đầy đủ các buổi họp, tập huấn nghiệp vụ, các chương trình nâng cao trình độ chuyên môn do trung tâm tổ chức. Việc vắng mặt nhiều lần không lý do chính đáng có thể ảnh hưởng đến quyền lợi giảng dạy.</p>
                <p>6. Giáo viên chịu trách nhiệm cá nhân về nội dung bài giảng, tài liệu sử dụng và phong cách truyền đạt. Không được sử dụng tài liệu có bản quyền nếu không có sự cho phép hợp pháp, không được truyền bá thông tin sai lệch, tiêu cực, hoặc trái với định hướng giáo dục.</p>
                <p>7. Mọi hành vi phân biệt đối xử, xúc phạm, đe dọa hoặc gây ảnh hưởng xấu đến học viên, phụ huynh, đồng nghiệp hoặc uy tín của trung tâm sẽ bị xử lý nghiêm theo quy định và có thể bị truy cứu trách nhiệm pháp lý nếu cần thiết.</p>
                <p>8. Giáo viên có quyền đóng góp ý kiến, đề xuất phương pháp giảng dạy mới nhằm nâng cao hiệu quả đào tạo. Trung tâm luôn khuyến khích tinh thần sáng tạo, cải tiến và cầu thị trong công tác giảng dạy.</p>
                <p>9. Trong mọi tình huống phát sinh tranh chấp hoặc hiểu lầm, hai bên sẽ ưu tiên giải quyết thông qua đối thoại trực tiếp, hợp tác thiện chí và dựa trên tinh thần xây dựng. Trường hợp không thể giải quyết, sẽ tuân theo quy định pháp luật Việt Nam hiện hành.</p>
                <p>10. Khi nhấn nút "Tôi đã đọc và đồng ý", giáo viên xác nhận rằng mình đã đọc kỹ toàn bộ nội dung điều khoản và cam kết, hiểu rõ trách nhiệm và quyền lợi của bản thân khi đăng ký giảng dạy tại trung tâm.</p>
            </div>
            <button onclick="document.getElementById('termsModal').style.display = 'none'" class="close-btn">Đóng</button>
        </div>
    </div>

    <script>
        // Đóng modal khi nhấp ra ngoài
        window.onclick = function(event) {
            var modal = document.getElementById('termsModal');
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
</body>
</html>