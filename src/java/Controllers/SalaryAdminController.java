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
import java.util.ArrayList;
import models.ResultMessage;
import models.SalaryTeacher;
import models.SalaryTeacherDAO;
import models.TeacherClass;
import models.Teachers;

/**
 *
 * @author Quang
 */
public class SalaryAdminController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SalaryAdminController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SalaryAdminController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SalaryTeacherDAO sd = new SalaryTeacherDAO();

        ArrayList<TeacherClass> classList = new ArrayList<>();
        ArrayList<TeacherClass> allClassList = sd.getAllClasses();
        
        SalaryTeacher s = null;
        String teacherId = request.getParameter("teacher");
        String className = request.getParameter("class");
        String id = request.getParameter("id");
        String mode = request.getParameter("mode");

        if (teacherId != null && !teacherId.equals("0")) {
            try {
                int parsedTeacherId = Integer.parseInt(teacherId);
                classList = sd.getClassesByTeacher(parsedTeacherId);
            } catch (NumberFormatException e) {
                request.setAttribute("message", "Lỗi: Mã giáo viên không hợp lệ.");
                request.setAttribute("success", false);
            }
        }

        if ("1".equals(mode) && id != null && !id.isEmpty()) {
            s = sd.getSalaryById(id);
            if (s != null) {
                teacherId = s.getTeacher();
                className = s.getClassName();

                try {
                    classList = sd.getClassesByTeacher(Integer.parseInt(teacherId));
                } catch (NumberFormatException e) {
                    request.setAttribute("message", "Lỗi: Mã giáo viên từ lương không hợp lệ.");
                    request.setAttribute("success", false);
                }
            } else {
                request.setAttribute("message", "Lỗi: Không tìm thấy lương với mã " + id);
                request.setAttribute("success", false);
            }
        }

        if ("2".equals(mode) && id != null && !id.isEmpty()) {
            ResultMessage result = sd.deleteSalary(id);
            request.setAttribute("message", result.getMessage());
            request.setAttribute("success", result.isSuccess());
        }

        String cost = null;
        if (className != null && !className.equals("0")) {
            if (s == null) {
                cost = sd.getCourseCost(className);
                s = new SalaryTeacher(id, teacherId, className, cost, null, null, null, null, null, null);
            }
        }

        ArrayList<SalaryTeacher> data = sd.getSalaryList();
        ArrayList<Teachers> data1 = sd.getTeacherList();

        request.setAttribute("data", data);
        request.setAttribute("data1", data1);
        request.setAttribute("classList", classList);
        request.setAttribute("allClassList", allClassList);
        request.setAttribute("s", s);
        request.setAttribute("selectedTeacher", teacherId);
        request.getRequestDispatcher("salaryAdmin.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String teacherId = request.getParameter("teacher");
        String className = request.getParameter("class");
        String per = request.getParameter("per");
        String bonus = request.getParameter("bonus");
        String penalty = request.getParameter("penalty");
        String note = request.getParameter("note");
        String id = request.getParameter("id");

        String searchTeacherName = request.getParameter("searchTeacherName");
        String filterClassName = request.getParameter("filterClassName");

        SalaryTeacherDAO sd = new SalaryTeacherDAO();
        ResultMessage result = null;

        String cost = null;
        if (className != null && !className.equals("0")) {
            cost = sd.getCourseCost(className);
        }
        SalaryTeacher s = new SalaryTeacher(id, teacherId, className, cost, per, bonus, penalty, note, null, null);

        if (request.getParameter("add") != null) {
            result = sd.addSalary(s);
        } else if (request.getParameter("update") != null) {
            result = sd.updateSalary(s);
        }

        ArrayList<SalaryTeacher> data;
        ArrayList<Teachers> data1 = sd.getTeacherList();

        ArrayList<TeacherClass> classList = new ArrayList<>();
        ArrayList<TeacherClass> allClassList = sd.getAllClasses();
        
        if (teacherId != null && !teacherId.equals("0")) {
            classList = sd.getClassesByTeacher(Integer.parseInt(teacherId));
        }

        if (searchTeacherName != null && !searchTeacherName.isEmpty()) {
            data = sd.getSalaryByTeacherName(searchTeacherName);
        } else if (filterClassName != null && !filterClassName.equals("0")) {
            data = sd.getSalaryByClassName(filterClassName);
        } else {
            data = sd.getSalaryList();
        }

        request.setAttribute("data", data);
        request.setAttribute("data1", data1);
        request.setAttribute("classList", classList);
        request.setAttribute("allClassList", allClassList);
        request.setAttribute("selectedTeacher", teacherId);

        if (result != null) {
            request.setAttribute("message", result.getMessage());
            request.setAttribute("success", result.isSuccess());
            if (result.isSuccess()) {
                s = null; // reset form khi thêm/sửa thành công
            }
        }
        request.setAttribute("s", s);
        request.getRequestDispatcher("salaryAdmin.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}