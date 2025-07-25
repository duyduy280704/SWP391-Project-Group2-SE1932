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
import models.TeacherApplicationDAO;
import models.TeacherApplications;

import models.TypeCourse;

/**
 *
 * @author Dương
 */
public class TeacherApplicationControllerad extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("keyword"); 
        String status = request.getParameter("status");   
        String typeId = request.getParameter("type");     

        TeacherApplicationDAO dao = new TeacherApplicationDAO();
        List<TeacherApplications> applications = dao.searchApplications(keyword, status, typeId);

        // lấy danh sách thể loại môn để hiển thị dropdown
        List<TypeCourse> typeCourses = dao.getAllTypeCourses();

        request.setAttribute("applications", applications);
        request.setAttribute("typeCourses", typeCourses);
        request.setAttribute("keyword", keyword);
        request.setAttribute("selectedStatus", status);
        request.setAttribute("selectedType", typeId);

        request.getRequestDispatcher("TeacherApprove.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");

        TeacherApplicationDAO dao = new TeacherApplicationDAO();

        switch (action) {
            case "accept":
                String reason1 = request.getParameter("reason1");
                dao.updateStatus(id, "Đã qua vòng CV");
                String email1 = dao.getEmailById(id); 
                SendMail.send(email1, "Kết quả xét duyệt hồ sơ", "Chúc mừng! Bạn đã vượt qua vòng xét duyệt hồ sơ." + reason1);
                break;

            case "reject":
                String reason2 = request.getParameter("reason2");
                dao.updateStatus(id, "Từ chối");
                String email2 = dao.getEmailById(id);
                SendMail.send(email2, "Kết quả xét duyệt hồ sơ", "Rất tiếc! Hồ sơ của bạn chưa đạt. Lý do: " + reason2);
                break;

            case "done":
                dao.updateStatus(id, "Đã hoàn tất");

                
                String fullName = request.getParameter("full_name");
                String email3 = request.getParameter("email");
                String birthDate = request.getParameter("birth_date");
                String gender = request.getParameter("gender");
                String expertise = request.getParameter("expertise");
                String typeId = request.getParameter("id_type_course");
                String yearsExp = request.getParameter("years_of_experience");
                String phone = request.getParameter("phone");
                String salaryStr = request.getParameter("salaryOffer"); 
                double salary = Double.parseDouble(salaryStr.trim());
                dao.insertTeacher(fullName, email3, birthDate, gender, expertise, typeId, yearsExp, phone, id, salary);

                // Gửi mail
                String content = "Chúc mừng bạn đã trở thành giáo viên của trung tâm BIGDREAM!"
                        + "Tài khoản: " + phone + "Mật khẩu: " + phone+" Hãy truy cập vào hệ thống để đổi mật khẩu localhost:8080/BIGDREAM/ResetPasswordServlet";
                SendMail.send(email3, "Xác nhận trúng tuyển", content);
                break;
        }

        
        response.sendRedirect("TeacherApplication");
    }

}
