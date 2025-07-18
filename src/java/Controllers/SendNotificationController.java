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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import models.AdminStaffs;
import models.NotificationDAO;
import models.PreRegistration;
import models.Students;
import models.Teachers;
import models.UserBasic;

/**
 *
 * @author Dwight
 */
public class SendNotificationController extends HttpServlet {




    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        NotificationDAO dao = new NotificationDAO();

        String role = request.getParameter("role");
        if (role == null || role.trim().isEmpty()) {
        request.setAttribute("error", "Vui lòng chọn vai trò cần tra cứu.");
        request.getRequestDispatcher("SendNotification.jsp").forward(request, response);
        return;
    }
        
        List<UserBasic> list = dao.getUsersByRole(role);
        request.setAttribute("userList", list);
        request.setAttribute("classList", dao.getAllClasses());
        request.setAttribute("unpaidList", dao.getStudentsWithUnpaidPayments());
        request.setAttribute("preList", dao.getApprovedRegistrations());

        // Forward sang trang gửi thông báo
        request.getRequestDispatcher("SendNotification.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sendType = request.getParameter("sendType");  // "individual", "role", "class"
        String message = request.getParameter("message");
        NotificationDAO dao = new NotificationDAO();
        String subject = "\uD83D\uDCE2 Thông báo từ trung tâm";

        switch (sendType) {
            case "individual" -> {
                String role = request.getParameter("role");
                String receiverId = request.getParameter("receiverId");
                dao.insertNotificationForOne(role, receiverId, message);

                String email = dao.getEmailByRoleAndId(role, receiverId);
                if (email != null && !email.isEmpty()) {
                    SendMail.send(email, subject, message);
                }
                request.setAttribute("message", "📬 Đã gửi thông báo cá nhân tới ID: " + receiverId);
            }

            case "role" -> {
                String roleAll = request.getParameter("roleAll");
                Map<String, String> roleEmailMap = dao.insertNotificationByRole(roleAll, message);
                for (Map.Entry<String, String> entry : roleEmailMap.entrySet()) {
                    if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                        SendMail.send(entry.getValue(), subject, message);
                    }
                }
                request.setAttribute("message", "📬 Đã gửi thông báo đến tất cả " + roleAll);
            }

            case "class" -> {
                String[] classIds = request.getParameterValues("classIds");
                if (classIds != null) {
                    Map<String, String> studentEmailMap = dao.insertNotificationByClasses(Arrays.asList(classIds), message);
                    for (Map.Entry<String, String> entry : studentEmailMap.entrySet()) {
                        if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                            SendMail.send(entry.getValue(), subject, message);
                        }
                    }
                    request.setAttribute("message", "📬 Đã gửi thông báo đến học viên trong các lớp đã chọn.");
                }

            }

            case "unpaid" -> {
                List<Students> unpaidList = dao.getStudentsWithUnpaidPaymentsDetailed();
                for (Students s : unpaidList) {
                    dao.insertNotificationForOne("student", String.valueOf(s.getId()),
                            "Bạn chưa hoàn tất học phí cho khóa học: " + s.getCourseName());

                    SendMail.send(s.getEmail(),
                            "Nhắc nhở thanh toán học phí",
                            "Xin chào " + s.getName() + ",\n\n"
                            + message + ": " + s.getCourseName() + ". "
                            + "Vui lòng thanh toán sớm để không ảnh hưởng đến việc học.\n\n"
                            + "Trân trọng.");
                }
                request.setAttribute("message", "💰 Đã gửi thông báo đến " + unpaidList.size() + " sinh viên chưa đóng tiền.");
            }

            case "preapproved" -> {
                List<PreRegistration> preList = dao.getApprovedRegistrationsDetailed();
                for (PreRegistration p : preList) {
                    dao.insertNotificationByEmail(p.getEmail(),
                            "Chào mừng bạn đã được duyệt tham gia khóa học: " + p.getCourseName());

                    SendMail.send(p.getEmail(),
                            "Thông báo xác nhận đăng ký khóa học",
                            "Xin chào " + p.getFull_name() + ",\n\n"
                            + "Bạn đã được duyệt tham gia khóa học: " + p.getCourseName() + ".\n"
                            + "Thông tin đăng nhập hệ thống:\n"
                            + "- Tài Khoản: " + p.getPhone() + "\n\n"
                            + "- Mật khẩu: " + p.getPhone() + "\n\n"
                            + "Vui lòng đăng nhập và hoàn tất các bước tiếp theo.\n\n"
                            + "Trân trọng.");

                    dao.updateStatus(p.getId(), "Đã active");
                }
                request.setAttribute("message", "✅ Đã gửi tài khoản và thông báo đến " + preList.size() + " học viên đã duyệt.");
            }

        }
        request.setAttribute("unpaidList", dao.getStudentsWithUnpaidPayments());
        request.setAttribute("preList", dao.getApprovedRegistrations());
        request.setAttribute("success", "\u2714\uFE0F Gửi thông báo thành công!");
        request.setAttribute("classList", dao.getAllClasses());
        request.getRequestDispatcher("SendNotification.jsp").forward(request, response);
    }
}
