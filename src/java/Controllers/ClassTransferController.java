    package Controllers;

    import jakarta.servlet.*;
    import jakarta.servlet.http.*;
    import java.io.IOException;
    import models.*;

    public class ClassTransferController extends HttpServlet {

        private final ClassTransferRequestDAO dao = new ClassTransferRequestDAO();

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            HttpSession session = request.getSession();
            String keyword = request.getParameter("keyword");
            String fromDateStr = request.getParameter("fromDate");
            String toDateStr = request.getParameter("toDate");

            // Xử lý tìm kiếm
            if (keyword != null && !keyword.trim().isEmpty()) {
                request.setAttribute("keyword", keyword);
                request.setAttribute("history", dao.searchTransferHistory(keyword));
            } // Xử lý lọc theo ngày
            else if ((fromDateStr != null && !fromDateStr.isEmpty()) || (toDateStr != null && !toDateStr.isEmpty())) {
                request.setAttribute("fromDate", fromDateStr);
                request.setAttribute("toDate", toDateStr);

                try {
                    java.sql.Date fromDate = fromDateStr != null && !fromDateStr.isEmpty()
                            ? java.sql.Date.valueOf(fromDateStr) : null;
                    java.sql.Date toDate = toDateStr != null && !toDateStr.isEmpty()
                            ? java.sql.Date.valueOf(toDateStr) : null;

                    if (fromDate != null && toDate != null && fromDate.after(toDate)) {
                        request.setAttribute("error", "Ngày bắt đầu phải trước hoặc bằng ngày kết thúc.");
                        request.setAttribute("history", dao.getTransferHistory()); 
                    } else {
                        request.setAttribute("history", dao.filterTransferHistory(fromDateStr, toDateStr));
                    }
                } catch (IllegalArgumentException e) {
                    request.setAttribute("error", "Định dạng ngày không hợp lệ.");
                    request.setAttribute("history", dao.getTransferHistory());
                }
            } else {
                request.setAttribute("history", dao.getTransferHistory());
            }

            // Lấy thông báo thành công từ session và chuyển sang request
            String message = (String) session.getAttribute("message");
            if (message != null) {
                request.setAttribute("message", message);
                session.removeAttribute("message");
            }

            String error = (String) session.getAttribute("error");
            if (error != null) {
                request.setAttribute("error", error);
                session.removeAttribute("error");
            }

            String action = request.getParameter("action");
            String courseId = request.getParameter("courseId");
            String classId = request.getParameter("classId");
            String studentId = request.getParameter("studentId");

            String toClassId = request.getParameter("toClassId");
            request.setAttribute("toClassId", toClassId);


            request.setAttribute("courses", dao.getAllCourses());

            if (courseId != null && !courseId.isEmpty()) {
                request.setAttribute("selectedCourseId", courseId);
                request.setAttribute("classes", dao.getClassesByCourse(courseId));

                if (classId != null && !classId.isEmpty()) {
                    request.setAttribute("selectedClassId", classId);
                    request.setAttribute("students", dao.getStudentsByClass(classId));

                    if (studentId != null && !studentId.isEmpty()) {
                        request.setAttribute("selectedStudentId", studentId);
                        request.setAttribute("studentName", dao.getStudentNameById(studentId));
                        request.setAttribute("transferCount", dao.getTransferCount(studentId, courseId));
                        request.setAttribute("targetClasses", dao.getTargetClasses(courseId, classId));
                    }
                }
            }
            request.getRequestDispatcher("StaffManageTransfer.jsp").forward(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");

            String courseId = request.getParameter("courseId");
            String classId = request.getParameter("classId");
            String studentId = request.getParameter("studentId");
            String toClassId = request.getParameter("toClassId");

            HttpSession session = request.getSession();

            // Kiểm tra đầu vào
            if (courseId == null || classId == null || studentId == null || toClassId == null
                    || courseId.isEmpty() || classId.isEmpty() || studentId.isEmpty() || toClassId.isEmpty()) {

                session.setAttribute("error", "Vui lòng chọn đầy đủ thông tin để chuyển lớp.");
                response.sendRedirect("classTransfer?action=selectStudent&courseId=" + courseId
                        + "&classId=" + classId + "&studentId=" + studentId + "&toClassId=" + toClassId);

                return;
            }

            // Kiểm tra số lần chuyển lớp trong khóa học
            int transferCount = dao.getTransferCount(studentId, courseId);
            if (transferCount >= 2) {
                session.setAttribute("error", "Học sinh đã chuyển lớp quá 2 lần trong khóa này.");
                response.sendRedirect("classTransfer?action=selectStudent&courseId=" + courseId
                        + "&classId=" + classId + "&studentId=" + studentId + "&toClassId=" + toClassId);

                return;
            }

            try {
                dao.processTransfer(studentId, classId, toClassId);
                System.out.println("processTransfer thành công, chuẩn bị insert lịch sử");
                dao.insertTransferRequest(studentId, classId, toClassId, "Chuyển lớp theo yêu cầu", "approved");
                System.out.println("Đã gọi insertTransferRequest");
                session.removeAttribute("error");
                session.setAttribute("message", "Chuyển lớp thành công.");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Đã xảy ra lỗi trong quá trình chuyển lớp.");
            }

            response.sendRedirect("classTransfer?action=selectStudent&courseId=" + courseId
                    + "&classId=" + classId + "&studentId=" + studentId + "&toClassId=" + toClassId);

        }

    }
