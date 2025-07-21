/*
 * Click nbfs://SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import models.SalaryTeacher;
import models.SalaryTeacherDAO;
import models.Teachers;
import java.time.Year;

/**
 *
 * @author Quang
 */
public class SalaryTeacherController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teachers teacher = (Teachers) session.getAttribute("account");

        // Kiểm tra đăng nhập
        if (teacher == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId;
        try {
            teacherId = Integer.parseInt(teacher.getId());
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID giáo viên không hợp lệ!");
            response.sendRedirect("login.jsp");
            return;
        }

        SalaryTeacherDAO sd = new SalaryTeacherDAO();
        ArrayList<SalaryTeacher> data;

        // Lấy tham số lọc tháng/năm
        String monthYear = request.getParameter("monthYear");
        if (monthYear != null && !monthYear.isEmpty()) {
            // Kiểm tra định dạng YYYY-MM
            if (!monthYear.matches("\\d{4}-\\d{2}")) {
                request.setAttribute("error", "Định dạng tháng/năm không hợp lệ!");
                data = sd.getSalaryListByTeacherId(teacherId);
            } else {
                // Kiểm tra năm và tháng hợp lệ
                String[] parts = monthYear.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int currentYear = Year.now().getValue();
                if (year < 2000 || year > currentYear || month < 1 || month > 12) {
                    request.setAttribute("error", "Tháng/năm không hợp lệ!");
                    data = sd.getSalaryListByTeacherId(teacherId);
                } else {
                    data = sd.getSalaryListByTeacherIdAndMonthYear(teacherId, monthYear);
                }
            }
        } else {
            data = sd.getSalaryListByTeacherId(teacherId);
        }

        // Kiểm tra dữ liệu
        if (data == null || data.isEmpty()) {
            request.setAttribute("error", monthYear != null && !monthYear.isEmpty()
                    ? "Không tìm thấy dữ liệu lương cho tháng " + monthYear
                    : "Không tìm thấy dữ liệu lương.");
        } else {
            request.setAttribute("data", data);
        }

        // Đặt năm hiện tại cho form lọc
        request.setAttribute("currentYear", Year.now().getValue());
        request.setAttribute("picturePath", session.getAttribute("picturePath"));
        request.setAttribute("profile", teacher);
        request.getRequestDispatcher("salaryTeacher.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng POST về GET để xử lý lọc
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet hiển thị bảng lương giáo viên";
    }
}
