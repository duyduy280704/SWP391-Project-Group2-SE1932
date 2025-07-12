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
import models.Sale;
import models.SaleDAO;


public class SaleController extends HttpServlet {

    private SaleDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new SaleDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idRaw = request.getParameter("id");
        String modeRaw = request.getParameter("mode"); // 1: edit, 2: delete

        if (idRaw != null && modeRaw != null) {
            int id = Integer.parseInt(idRaw);
            int mode = Integer.parseInt(modeRaw);

            if (mode == 1) { // Edit
                Sale s = dao.getSaleById(id);
                request.setAttribute("sale", s);
            } else if (mode == 2) { // Delete
                dao.deleteSale(id);
                request.setAttribute("message", "Xóa thành công!");
                request.setAttribute("success", true);
            }
        }

        // Hiển thị danh sách (có lọc tìm kiếm nếu có)
        String keyword = request.getParameter("keyword");
        List<Sale> data;
        if (keyword != null && !keyword.trim().isEmpty()) {
            data = dao.searchSalesByCode(keyword);
            request.setAttribute("keyword", keyword);
        } else {
            data = dao.getAllSales();
        }
        request.setAttribute("data", data);
        request.getRequestDispatcher("SaleManage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idRaw = request.getParameter("id");
        String code = request.getParameter("code");
        String valueRaw = request.getParameter("value");
        String quantityRaw = request.getParameter("quantity");

        int id = (idRaw != null && !idRaw.isEmpty()) ? Integer.parseInt(idRaw) : 0;
        boolean valid = true;
        String message = "";

        // Validate input
        if (code == null || code.trim().isEmpty()) {
            message += "Mã code không được để trống. ";
            valid = false;
        }

        double value = 0;
        int quantity = 0;

        try {
            value = Double.parseDouble(valueRaw);
            if (value < 0) {
                message += "Giá trị giảm phải ≥ 0. ";
                valid = false;
            }
        } catch (NumberFormatException e) {
            message += "Giá trị giảm không hợp lệ. ";
            valid = false;
        }

        try {
            quantity = Integer.parseInt(quantityRaw);
            if (quantity < 0) {
                message += "Số lượng phải ≥ 0. ";
                valid = false;
            }
        } catch (NumberFormatException e) {
            message += "Số lượng không hợp lệ. ";
            valid = false;
        }

        // Nếu hợp lệ thì thêm/cập nhật
        if (valid) {
            Sale s = new Sale(id, code, value, null, quantity);
            if (request.getParameter("add") != null) {
                dao.insertSale(s);
                request.setAttribute("message", "Thêm thành công!");
                request.setAttribute("success", true);
            } else if (request.getParameter("update") != null) {
                dao.updateSale(s);
                request.setAttribute("message", "Cập nhật thành công!");
                request.setAttribute("success", true);
            }
        } else {
            // Trả lại dữ liệu lỗi
            request.setAttribute("sale", new Sale(id, code, value, null, quantity));
            request.setAttribute("message", message);
            request.setAttribute("success", false);
        }

        List<Sale> data = dao.getAllSales();
        request.setAttribute("data", data);
        request.getRequestDispatcher("SaleManage.jsp").forward(request, response);
    }

}