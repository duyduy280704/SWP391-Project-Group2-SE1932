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
import java.util.ArrayList;
import models.ResultMessage;
import models.SalaryTeacher;
import models.SalaryTeacherDAO;
import models.Teachers;
import java.time.Year;

/**
 *
 * @author Quang
 */
public class SalaryAdminController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SalaryTeacherDAO dao = new SalaryTeacherDAO();

        // Lấy danh sách giáo viên
        ArrayList<Teachers> teacherList = dao.getTeacherList();
        request.setAttribute("data1", teacherList);

        // Đặt năm hiện tại mặc định
        int currentYear = Year.now().getValue();
        request.setAttribute("currentYear", currentYear);

        // Lấy tham số teacher và month từ request
        String teacherIdRaw = request.getParameter("teacher");
        String monthYearRaw = request.getParameter("month");
        String id = request.getParameter("id");
        String mode = request.getParameter("mode");

        int selectedTeacher = 0;
        String selectedMonthYear = currentYear + "-01"; // Mặc định là tháng 1 của năm hiện tại

        // Xử lý chọn giáo viên
        if (teacherIdRaw != null && !teacherIdRaw.equals("0")) {
            try {
                selectedTeacher = Integer.parseInt(teacherIdRaw);
                String offerSalary = dao.getTeacherOfferSalary(selectedTeacher);
                request.setAttribute("teacherOfferSalary", offerSalary);
                request.setAttribute("selectedTeacher", selectedTeacher);
            } catch (NumberFormatException e) {
                System.err.println("Lỗi parse teacherId: " + e.getMessage());
                request.setAttribute("message", "ID giáo viên không hợp lệ!");
                request.setAttribute("success", false);
            }
        }

        // Xử lý chọn tháng/năm
        if (monthYearRaw != null && !monthYearRaw.equals("0")) {
            try {
                // Kiểm tra định dạng YYYY-MM
                if (!monthYearRaw.matches("\\d{4}-\\d{2}")) {
                    throw new IllegalArgumentException("Định dạng tháng/năm không hợp lệ!");
                }
                String[] parts = monthYearRaw.split("-");
                int year = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                if (year < 2000 || year > currentYear) {
                    throw new IllegalArgumentException("Năm phải từ 2000 đến " + currentYear + "!");
                }
                if (month < 1 || month > 12) {
                    throw new IllegalArgumentException("Tháng phải từ 1 đến 12!");
                }
                selectedMonthYear = monthYearRaw;
                request.setAttribute("selectedMonthYear", selectedMonthYear);
            } catch (Exception e) {
                System.err.println("Lỗi parse monthYear: " + e.getMessage());
                request.setAttribute("message", e.getMessage());
                request.setAttribute("success", false);
            }
        }

        // Lấy số buổi dạy nếu đã chọn cả giáo viên và tháng/năm
        if (selectedTeacher > 0 && selectedMonthYear != null) {
            int sessionCount = dao.getSessionCountByTeacherMonth(selectedTeacher, selectedMonthYear);
            request.setAttribute("sessionCount", sessionCount);
        }

        // Xử lý chế độ chỉnh sửa hoặc xóa
        if (id != null && mode != null) {
            if ("1".equals(mode)) { // Chế độ chỉnh sửa
                SalaryTeacher salary = dao.getSalaryById(id);
                if (salary != null) {
                    request.setAttribute("s", salary);
                    request.setAttribute("selectedTeacher", salary.getTeacher());
                    request.setAttribute("selectedMonthYear", salary.getMonth());
                } else {
                    request.setAttribute("message", "Không tìm thấy bản ghi lương!");
                    request.setAttribute("success", false);
                }
            } else if ("2".equals(mode)) { // Chế độ xóa
                ResultMessage result = dao.deleteSalary(id);
                request.setAttribute("message", result.getMessage());
                request.setAttribute("success", result.isSuccess());
            }
        }

        // Lấy tham số tìm kiếm và lọc từ session
        String searchTeacherName = (String) request.getSession().getAttribute("searchTeacherName");
        String filterMonthYear = (String) request.getSession().getAttribute("filterMonthYear");

        // Lấy danh sách lương theo tìm kiếm và lọc
        ArrayList<SalaryTeacher> salaries = dao.getSalariesBySearchAndFilter(searchTeacherName, filterMonthYear);
        request.setAttribute("data", salaries);
        request.setAttribute("searchTeacherName", searchTeacherName);
        request.setAttribute("filterMonthYear", filterMonthYear);

        // Chuyển tiếp đến JSP
        request.getRequestDispatcher("salaryAdmin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SalaryTeacherDAO dao = new SalaryTeacherDAO();
        String search = request.getParameter("search");
        String searchTeacherName = request.getParameter("searchTeacherName");
        String filterMonthYear = request.getParameter("filterMonthYear");

        // Xử lý tìm kiếm
        if (search != null) {
            // Lưu tham số tìm kiếm và lọc vào session để giữ trạng thái
            request.getSession().setAttribute("searchTeacherName", searchTeacherName);
            request.getSession().setAttribute("filterMonthYear", filterMonthYear);

            // Lấy danh sách lương theo tìm kiếm và lọc
            ArrayList<SalaryTeacher> salaries = dao.getSalariesBySearchAndFilter(searchTeacherName, filterMonthYear);
            request.setAttribute("data", salaries);
            request.setAttribute("searchTeacherName", searchTeacherName);
            request.setAttribute("filterMonthYear", filterMonthYear);
        } else {
            // Xử lý thêm hoặc cập nhật
            String action = request.getParameter("add") != null ? "add" : "update";
            String teacherId = request.getParameter("teacher");
            String monthYear = request.getParameter("month");
            String bonus = request.getParameter("bonus");
            String penalty = request.getParameter("penalty");
            String note = request.getParameter("note");
            String id = request.getParameter("id");

            // Thêm log để kiểm tra
            System.out.println("doPost: action = " + action + ", teacherId = " + teacherId + ", monthYear = " + monthYear + 
                              ", searchTeacherName = " + searchTeacherName + ", filterMonthYear = " + filterMonthYear);

            // Gọi phương thức processSalary để kiểm tra dữ liệu và tính toán
            ResultMessage result = dao.processSalary(action, id, teacherId, monthYear, bonus, penalty, note);

            // Đặt thông báo và trạng thái
            request.setAttribute("message", result.getMessage());
            request.setAttribute("success", result.isSuccess());
        }

        // Load lại dữ liệu và chuyển tiếp
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet quản lý lương giáo viên";
    }
}