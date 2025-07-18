/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import models.Notification;
import models.NotificationDAO;
import models.PaymentDAO;
import models.PaymentView;

/**
 *
 * @author Dwight
 */
public class AdminPaymentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String status = request.getParameter("status");

        if (keyword == null) keyword = "";
        if (status == null) status = "all";

        PaymentDAO dao = new PaymentDAO();
        List<PaymentView> payments = dao.getAllPayment(keyword, status);

        request.setAttribute("payments", payments);
        request.setAttribute("keyword", keyword);
        request.setAttribute("status", status);
        request.getRequestDispatcher("adminPayment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String orderCode = request.getParameter("orderCode");
        String idStudent = request.getParameter("idStudent");
        String email = request.getParameter("email");

        PaymentDAO paymentDAO = new PaymentDAO();
        NotificationDAO notiDAO = new NotificationDAO();

        String status = "";
        String message = "";

        if ("approve".equals(action)) {
            status = "Hoàn tất";
            message = "Admin đã duyệt thanh toán cho đơn hàng: " + orderCode;
        } else if ("reject".equals(action)) {
            status = "Chưa thanh toán";
            message = "Thanh toán đơn hàng " + orderCode + " đã bị chuyển về chưa thanh toán.";
        }

        paymentDAO.updateStatusByOrderCode(orderCode, status);

        // Gửi mail (bạn cần sửa để lấy email từ studentId nếu cần)
        try {
            SendMail.send(email, "Thông báo thanh toán", message);
        } catch (Exception e) {
            System.out.println("Gửi mail lỗi: " + e.getMessage());
        }

        // Insert thông báo
        Notification noti = new Notification(idStudent, message, java.time.LocalDate.now().toString());
        notiDAO.insert(noti);

        response.sendRedirect("AdminPayment");
    }
}
