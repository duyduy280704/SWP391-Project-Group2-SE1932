/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.util.ArrayList;


import models.ResultMessage;
import models.TeacherDAO;
import models.Teachers;
import models.TypeCourse;

/**
 *
 * @author Quang
 */
@MultipartConfig
public class TeacherController extends HttpServlet {

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
            out.println("<title>Servlet TeacherController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TeacherController at " + request.getContextPath() + "</h1>");
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
        TeacherDAO sd = new TeacherDAO();

        if (request.getParameter("mode") != null && request.getParameter("mode").equals("1")) {
            //tìm Product tương ứng cùng với code truyền sang
            String id = request.getParameter("id");
            Teachers s = sd.getTeacherById(id);
            request.setAttribute("s", s);
        }

        if (request.getParameter("mode") != null && request.getParameter("mode").equals("2")) {

            String id = request.getParameter("id");
            if (id != null && !id.isEmpty()) {
                sd.delete(id);
                request.setAttribute("message", "Xóa giáo viên thành công!");
            } else {
                request.setAttribute("error", "ID không hợp lệ!");
            }
        }

        ArrayList<Teachers> data = sd.getTeachers();
        ArrayList<TypeCourse> data1 = sd.getCourseType();

        request.setAttribute("data", data);
        request.setAttribute("data1", data1);
        request.getRequestDispatcher("listTeacher.jsp").forward(request, response);
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
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String birthdate = request.getParameter("birthdate");
        String gender = request.getParameter("gender");
        String exp = request.getParameter("exp");
        String course = request.getParameter("course");
        String years = request.getParameter("years");
        String phone = request.getParameter("phone");
        String offerSalary = request.getParameter("offerSalary");
        String role = "2"; // Mặc định role là giáo viên
        String nameSearch = request.getParameter("nameSearch");
        String genderFilter = request.getParameter("genderFilter");

        // Handle file upload
        byte[] imageBytes = null;
        Part filePart = request.getPart("pic");
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
            if (!fileName.endsWith(".jpg") && !fileName.endsWith(".png")) {
                request.setAttribute("message", "Chỉ hỗ trợ file JPG hoặc PNG!");
                request.setAttribute("success", false);
                ArrayList<Teachers> data = new TeacherDAO().getTeachers();
                ArrayList<TypeCourse> data1 = new TeacherDAO().getCourseType();
                request.setAttribute("data", data);
                request.setAttribute("data1", data1);
                request.getRequestDispatcher("listTeacher.jsp").forward(request, response);
                return;
            }
            if (filePart.getSize() > 5 * 1024 * 1024) { // 5MB limit
                request.setAttribute("message", "File quá lớn! Tối đa 5MB.");
                request.setAttribute("success", false);
                ArrayList<Teachers> data = new TeacherDAO().getTeachers();
                ArrayList<TypeCourse> data1 = new TeacherDAO().getCourseType();
                request.setAttribute("data", data);
                request.setAttribute("data1", data1);
                request.getRequestDispatcher("listTeacher.jsp").forward(request, response);
                return;
            }
            try (InputStream fileContent = filePart.getInputStream(); ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                byte[] data = new byte[8192]; // Buffer lớn hơn để tối ưu
                int bytesRead;
                while ((bytesRead = fileContent.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, bytesRead);
                }
                imageBytes = buffer.toByteArray();
            }
        }

        

        ResultMessage result = null;
        TeacherDAO teacherDAO = new TeacherDAO();
        Teachers teacher = new Teachers(id, name, email, password, birthdate, gender, exp, imageBytes, role, course, years, phone, offerSalary);
        
        

        try {
            if (request.getParameter("update") != null) {
                result = teacherDAO.update(teacher);
            } else if (request.getParameter("add") != null) {
                result = teacherDAO.add(teacher);
            } else {
                result = new ResultMessage(true, "Đã tìm giáo viên");
            }
        } catch (NumberFormatException e) {
            result = new ResultMessage(false, "Dữ liệu không hợp lệ: " + e.getMessage());
        }

        ArrayList<Teachers> data;
        ArrayList<TypeCourse> data1 = teacherDAO.getCourseType();
        if (request.getParameter("search") != null && nameSearch != null && !nameSearch.trim().isEmpty()) {
            data = teacherDAO.getTeacherByName(nameSearch);
            request.setAttribute("nameSearch", nameSearch);
        } else if (request.getParameter("filterGender") != null && genderFilter != null && !genderFilter.trim().isEmpty()) {
            data = teacherDAO.getTeachersByGender(genderFilter);
            request.setAttribute("genderFilter", genderFilter);
        } else {
            data = teacherDAO.getTeachers();
        }
        request.setAttribute("data", data);
        request.setAttribute("data1", data1);
        
        
        request.setAttribute("message", result.getMessage());
        request.setAttribute("success", result.isSuccess());
        request.setAttribute("s", teacher);
        request.getRequestDispatcher("listTeacher.jsp").forward(request, response);
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
