package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import models.*;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
//Huyền
public class TeacherHomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Trường hợp có tham số eventId → phục vụ ảnh sự kiện
        String eventId = request.getParameter("eventId");
        if (eventId != null && !eventId.trim().isEmpty()) {
            serveEventImage(request, response, eventId);
            return; 
        }

        // Kiểm tra đăng nhập
        HttpSession session = request.getSession();
        Teachers teacher = (Teachers) session.getAttribute("account");

        if (teacher == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }

        // Lấy ID giáo viên
        int teacherId;
        try {
            teacherId = Integer.parseInt(teacher.getId());
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp"); // nếu ID không hợp lệ
            return;
        }

        // Gọi hàm xử lý hiển thị lịch dạy và thông tin trang chủ
        handleScheduleView(request, response, teacherId);
    }

    // Trả ảnh sự kiện theo eventId 
    private void serveEventImage(HttpServletRequest request, HttpServletResponse response, String eventId)
            throws IOException {
        EventDAO eventDAO = new EventDAO();
        byte[] imageBytes = eventDAO.getEventImage(eventId);

        if (imageBytes != null && imageBytes.length > 0) {
            response.setContentType("image/jpeg");
            response.setContentLength(imageBytes.length);
            try (OutputStream out = response.getOutputStream()) {
                out.write(imageBytes); 
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found for event ID: " + eventId);
        }
    }

    //hiển thị giao diện giáo viên
    private void handleScheduleView(HttpServletRequest request, HttpServletResponse response, int teacherId)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Teachers teacher = (Teachers) session.getAttribute("account");

        // Lấy năm và tuần người dùng đã chọn (nếu có)
        String selectedYear = request.getParameter("year");
        String selectedWeek = request.getParameter("week");

        int year = LocalDate.now().getYear(); // mặc định: năm hiện tại
        DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // cho DB
        DateTimeFormatter viewFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // cho UI

        // Parse năm nếu có truyền lên
        try {
            if (selectedYear != null && !selectedYear.isEmpty()) {
                year = Integer.parseInt(selectedYear);
            }
        } catch (NumberFormatException ignored) {}

        // Xác định ngày đầu tuần được chọn (hoặc tuần hiện tại)
        LocalDate baseDate;
        try {
            baseDate = (selectedWeek != null && !selectedWeek.isEmpty())
                    ? LocalDate.parse(selectedWeek, dbFormatter)
                    : LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        } catch (Exception e) {
            baseDate = LocalDate.now().with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        }

        // Lấy lịch dạy của giáo viên trong tuần đó
        ScheduleTeacherDAO dao = new ScheduleTeacherDAO();
        List<ScheduleTeacher> scheduleTeacher = dao.getScheduleTeacher(teacherId, baseDate.format(dbFormatter));

        // Tạo danh sách các năm xung quanh năm hiện tại (để chọn năm)
        List<Integer> years = new ArrayList<>();
        for (int i = year - 2; i <= year + 2; i++) {
            years.add(i);
        }

        // Tạo danh sách tuần của năm (52 tuần)
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
                        0 
                ));
            }
        }

        // Truyền dữ liệu đến JSP
        request.setAttribute("profile", teacher);
        request.setAttribute("picturePath", session.getAttribute("picturePath")); // đường dẫn ảnh đại diện

        request.setAttribute("weekDays", Arrays.asList("Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ nhật"));
        request.setAttribute("scheduleTeacher", scheduleTeacher);
        request.setAttribute("weeks", weeks);
        request.setAttribute("years", years);
        request.setAttribute("selectedWeek", baseDate.format(dbFormatter));
        request.setAttribute("selectedYear", year);

        // Lấy 3 phản hồi mới nhất
        FeedBackDAO dao1 = new FeedBackDAO();
        ArrayList<FeedBack> feedbackList = dao1.getTop3Feedbacks();
        request.setAttribute("feedbackList", feedbackList);

        // Lấy 3 sự kiện mới nhất
        EventDAO ev = new EventDAO();
        List<Event> events = ev.getRecentEvents(3);
        request.setAttribute("events", events);

        // Lấy 3 blog mới nhất
        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogList = blogDAO.getLatest3Blogs();
        request.setAttribute("blogList", blogList);

        // Lấy danh sách thông báo gửi cho giáo viên
        NoticeToTeacherDAO noticedao = new NoticeToTeacherDAO();
        int id = Integer.parseInt(teacher.getId());
        List<NoticeToTeacher> notices = noticedao.getNoticesByTeacherId(id);
        request.setAttribute("notices", notices);

        request.getRequestDispatcher("TeacherHome.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); 
    }
}
