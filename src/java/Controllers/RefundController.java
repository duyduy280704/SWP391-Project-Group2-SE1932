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
import models.PaymentDAO;
import models.RefundInfo;

/**
 *
 * @author Dwight
 */
public class RefundController extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PaymentDAO dao = new PaymentDAO();
        List<RefundInfo> list = dao.getRefundList();
        request.setAttribute("refundList", list);
        request.getRequestDispatcher("CancelCourseAdmin.jsp").forward(request, response);
    }
    
    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    String idParam = request.getParameter("paymentId");
    if (idParam != null) {
        int paymentId = Integer.parseInt(idParam);
        PaymentDAO dao = new PaymentDAO();
        dao.markAsRefunded(paymentId);
    }
    response.sendRedirect("Refund");
}

}
