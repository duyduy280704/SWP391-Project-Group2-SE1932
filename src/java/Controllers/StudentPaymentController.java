package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import models.PaymentView;

import java.io.IOException;
import java.util.List;
import models.PaymentDAO;
import models.Students;

public class StudentPaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra session và lấy thông tin học sinh
        HttpSession session = request.getSession();
        Students student = (Students) session.getAttribute("account");

        if (student == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }

        PaymentDAO payDao = new PaymentDAO();

        // Danh sách chưa thanh toán
        List<PaymentView> unpaidList = payDao.getPaymentsByStatus(student.getId(), "Chưa thanh toán", keyword);

        // Danh sách đã chuyển khoản hoặc đã thanh toán
        List<PaymentView> paidList = payDao.getPaymentsByStatus(student.getId(), "Đã chuyển khoản", keyword);
        paidList.addAll(payDao.getPaymentsByStatus(student.getId(), "Hoàn tất", keyword));

        // Truyền dữ liệu về JSP
        request.setAttribute("unpaidList", unpaidList);
        request.setAttribute("paidList", paidList);
        request.getRequestDispatcher("paymentHistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String orderCode = request.getParameter("orderCode");
        if (orderCode != null && !orderCode.isEmpty()) {
            PaymentDAO payDao = new PaymentDAO();
            payDao.updateStatusByOrderCode(orderCode, "Đã chuyển khoản");
        }

        // Sau khi cập nhật, chuyển hướng lại về trang danh sách
        response.sendRedirect("StudentPayment");
    }
}
