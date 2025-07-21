package Controllers;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.TeachingAttendance;
import models.TeachingAttendanceDAO;

public class TeachingAttendanceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        String success = (String) session.getAttribute("success");
        if (success != null) {
            request.setAttribute("success", success);
            session.removeAttribute("success");
        }

        String keyword = request.getParameter("keyword");
        String rawDay = request.getParameter("day");
        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", error);
        }
// sử lí lấy luôn ngày hôm nay khi mà staff vào chấm công
        Date day;
        try {
            day = (rawDay == null || rawDay.trim().isEmpty())
                    ? new Date(System.currentTimeMillis())
                    : Date.valueOf(rawDay);
        } catch (IllegalArgumentException e) {
            day = new Date(System.currentTimeMillis());
        }

        TeachingAttendanceDAO dao = new TeachingAttendanceDAO();
        ArrayList<TeachingAttendance> list = (keyword != null && !keyword.trim().isEmpty())
                ? dao.getAttendancesByDayAndKeyword(day, keyword)
                : dao.getAttendancesByDay(day);

        request.setAttribute("list", list);
        request.setAttribute("selectedDay", day.toString());
        request.getRequestDispatcher("teachingAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));
        String status = request.getParameter("status");
        String note = request.getParameter("note");
        String redirectDay = request.getParameter("day");
        String keyword = request.getParameter("keyword");
// kiểm tra xem khi chọn có đúng ngày k 
        Date day;
        try {
            day = Date.valueOf(redirectDay);
        } catch (IllegalArgumentException e) {
            response.sendRedirect("teachingAttendance?error=invalid_date");
            return;
        }
//sử lí chỉ dc sửa truocs 2 ngày ,k dc sửa trong tương lai
        LocalDate inputDate = day.toLocalDate();
        LocalDate today = LocalDate.now();

        if (inputDate.isAfter(today)) {
            response.sendRedirect("teachingAttendance?day=" + redirectDay + "&error=future_date_not_allowed");
            return;
        }

        if (ChronoUnit.DAYS.between(inputDate, today) > 2) {
            response.sendRedirect("teachingAttendance?day=" + redirectDay + "&error=edit_not_allowed");
            return;
        }

        TeachingAttendanceDAO dao = new TeachingAttendanceDAO();
        int confirmationId = dao.ensureConfirmationExistsBySchedule(scheduleId);
        dao.updateStatusAndNote(confirmationId, status, note);

        HttpSession session = request.getSession();
        session.setAttribute("success", "updated");

        String redirectUrl = "teachingAttendance?day=" + redirectDay;
        if (keyword != null && !keyword.trim().isEmpty()) {
            redirectUrl += "&keyword=" + java.net.URLEncoder.encode(keyword, "UTF-8");
        }

        response.sendRedirect(redirectUrl);
    }
}
