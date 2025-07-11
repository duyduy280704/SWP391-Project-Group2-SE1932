package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import models.*;
// Thuy_ gửi đon và duyệt đơn điểm danh của học sinh
public class ClassTransferController extends HttpServlet {

    private final ClassTransferRequestDAO dao = new ClassTransferRequestDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");
// dăng nhap cua hoc sinh
        if (account instanceof Students) {
            Students student = (Students) account;
            Categories_class currentClass = dao.getClassByStudentId(student.getId());

            if (currentClass == null) {
                request.setAttribute("error", "Bạn chưa được phân vào lớp nào.");
                request.getRequestDispatcher("StudentRequestTransfer.jsp").forward(request, response);
                return;
            }

            List<Categories_class> availableClasses = dao.getAllOtherClasses(currentClass.getId_class());

            List<ClassTransferRequest> requests = dao.getRequestsByStudent(student.getId());

            request.setAttribute("currentClass", currentClass);
            request.setAttribute("availableClasses", availableClasses);
            request.setAttribute("requests", requests);

            request.getRequestDispatcher("StudentRequestTransfer.jsp").forward(request, response);
            return;
        }
// staff dăng nhập
        if (account instanceof AdminStaffs) {
            String keyword = request.getParameter("keyword");
            List<ClassTransferRequest> requests;
            if (keyword != null && !keyword.trim().isEmpty()) {
                requests = dao.searchRequestsByStudentName(keyword);
            } else {
                requests = dao.getAllRequests();
            }
            request.setAttribute("requests", requests);
            request.getRequestDispatcher("StaffManageTransfer.jsp").forward(request, response);

            return;
        }

        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Object account = session.getAttribute("account");

        if (account instanceof Students) {
            Students student = (Students) account;

            String fromClass = request.getParameter("fromClassId");
            String toClass = request.getParameter("toClassId");
            String reason = request.getParameter("reason");

            //  có đơn đang chờ hay k
            if (dao.hasPendingRequest(student.getId())) {   
                request.setAttribute("error", "Bạn đã gửi một đơn xin chuyển lớp và đang chờ xử lý.");
                doGet(request, response); 
                return;
            }

            if (fromClass != null && toClass != null && reason != null && !reason.trim().isEmpty()) {
                ClassTransferRequest req = new ClassTransferRequest();
                req.setStudentId(student.getId());
                req.setFromClassId(fromClass);
                req.setToClassId(toClass);
                req.setReason(reason.trim());

                dao.submitRequest(req);
            }

            response.sendRedirect("classTransfer");
            return;
        }
// staff sử lí đơn có chấp nhận từ chối và ghi chú
        if (account instanceof AdminStaffs) {
            String action = request.getParameter("action");
            String requestIdStr = request.getParameter("requestId");
            String staffNote = request.getParameter("staffNote");

            try {
                int requestId = Integer.parseInt(requestIdStr);
                if ("approve".equals(action)) {
                    dao.approveRequest(requestId, staffNote);
                } else if ("reject".equals(action)) {
                    dao.rejectRequest(requestId, staffNote);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            response.sendRedirect("classTransfer");
        }

    }
}