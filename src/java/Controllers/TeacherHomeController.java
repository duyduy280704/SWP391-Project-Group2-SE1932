package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.*;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TeacherHomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check for eventId parameter to serve image
        String eventId = request.getParameter("eventId");
        if (eventId != null && !eventId.trim().isEmpty()) {
            serveEventImage(request, response, eventId);
            return;
        }

        // Existing logic for rendering the page
        HttpSession session = request.getSession();
        Teachers teacher = (Teachers) session.getAttribute("account");

        if (teacher == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int teacherId;
        try {
            teacherId = Integer.parseInt(teacher.getId());
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp");
            return;
        }

        handleScheduleView(request, response, teacherId);
    }

    private void serveEventImage(HttpServletRequest request, HttpServletResponse response, String eventId)
            throws IOException {
        EventDAO eventDAO = new EventDAO();
        byte[] imageBytes = eventDAO.getEventImage(eventId);

        if (imageBytes != null && imageBytes.length > 0) {
            response.setContentType("image/jpeg"); // Adjust MIME type as needed (e.g., image/png)
            response.setContentLength(imageBytes.length);
            try (OutputStream out = response.getOutputStream()) {
                out.write(imageBytes);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found for event ID: " + eventId);
        }
    }

    private void handleScheduleView(HttpServletRequest request, HttpServletResponse response, int teacherId)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Teachers teacher = (Teachers) session.getAttribute("account");

        String selectedYear = request.getParameter("year");
        String selectedWeek = request.getParameter("week");

        int year = LocalDate.now().getYear();
        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter viewFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            if (selectedYear != null && !selectedYear.isEmpty()) {
                year = Integer.parseInt(selectedYear);
            }
        } catch (NumberFormatException ignored) {
        }

        LocalDate baseDate;
        try {
            baseDate = (selectedWeek != null && !selectedWeek.isEmpty())
                    ? LocalDate.parse(selectedWeek, dbFormatter)
                    : LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        } catch (Exception e) {
            baseDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        }

        ScheduleTeacherDAO dao = new ScheduleTeacherDAO();
        List<ScheduleTeacher> scheduleTeacher = dao.getScheduleTeacher(teacherId, baseDate.format(dbFormatter));

        // Năm xung quanh hiện tại
        List<Integer> years = new ArrayList<>();
        for (int i = year - 2; i <= year + 2; i++) {
            years.add(i);
        }

        // Tạo danh sách các tuần
        List<ScheduleWeek> weeks = new ArrayList<>();
        LocalDate startOfYear = LocalDate.of(year, 1, 1).with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        for (int i = 0; i < 52; i++) {
            LocalDate weekStart = startOfYear.plusWeeks(i);
            if (weekStart.getYear() == year || weekStart.plusDays(6).getYear() == year) {
                LocalDate weekEnd = weekStart.plusDays(6);
                weeks.add(new ScheduleWeek(
                        weekStart.format(dbFormatter),
                        weekEnd.format(dbFormatter),
                        weekStart.format(viewFormatter),
                        weekEnd.format(viewFormatter),
                        0 // không dùng tuần số nữa
                ));
            }
        }

        // Truyền thông tin giáo viên và ảnh
        request.setAttribute("profile", teacher); // Truyền thông tin giáo viên
        request.setAttribute("picturePath", session.getAttribute("picturePath")); // Truyền đường dẫn ảnh

        request.setAttribute("weekDays", Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"));
        request.setAttribute("scheduleTeacher", scheduleTeacher);
        request.setAttribute("weeks", weeks);
        request.setAttribute("years", years);
        request.setAttribute("selectedWeek", baseDate.format(dbFormatter));
        request.setAttribute("selectedYear", year);

        FeedBackDAO dao1 = new FeedBackDAO();
        ArrayList<FeedBack> feedbackList = dao1.getTop3Feedbacks();
        request.setAttribute("feedbackList", feedbackList);

        EventDAO ev = new EventDAO();
        List<Event> events = ev.getRecentEvents(3);
        request.setAttribute("events", events);
        
        NoticeToTeacherDAO noticedao = new NoticeToTeacherDAO();
        int id=Integer.parseInt(teacher.getId());
        List<NoticeToTeacher> notices = noticedao.getNoticesByTeacherId(id);

        request.setAttribute("notices", notices);

        request.getRequestDispatcher("TeacherHome.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Xử lý POST giống GET nếu cần
    }
}