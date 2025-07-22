package Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import models.ScheduleDAO;
import models.Schedules;
// Mai Thủy_ lịch học khi ấn vào tên lớp , có thể xóa, sửa,lọc thời gian ngày 

public class ScheduleByClassController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");
        String classId = request.getParameter("id_class");
        if (classId == null || classId.isEmpty()) {
            classId = request.getParameter("id");
        }
        String className = request.getParameter("name");
        String scheduleId = request.getParameter("sid");
        String date = request.getParameter("date");
        String from = request.getParameter("from");
        String to = request.getParameter("to");

        ScheduleDAO dao = new ScheduleDAO();
        ArrayList<Schedules> scheduleList = new ArrayList<>();

        try {
            if ("searchByClass".equals(mode)) {
                String keyword = request.getParameter("keyword");
                if (keyword == null || keyword.trim().isEmpty()) {
                    request.setAttribute("err", "Vui lòng nhập từ khóa tìm kiếm.");
                    scheduleList = dao.getScheduleByClassId(classId);
                } else {
                    scheduleList = dao.searchScheduleByClassIdAndKeyword(classId, keyword);
                    if (scheduleList.isEmpty()) {
                        request.setAttribute("msg", "Không tìm thấy kết quả phù hợp với từ khóa: " + keyword);
                    } else {
                        request.setAttribute("msg", "Kết quả tìm kiếm cho: " + keyword);
                    }
                }
                request.setAttribute("scheduleList", scheduleList);
                request.setAttribute("classId", classId);
                request.setAttribute("className", className);
                request.getRequestDispatcher("scheduleByClass.jsp").forward(request, response);
                return;
            }

            if ("1".equals(mode)) {
                HttpSession session = request.getSession();
                String msg = (String) session.getAttribute("msg");
                if (msg != null) {
                    request.setAttribute("msg", msg);
                    session.removeAttribute("msg");
                }
                System.out.println(classId);
                scheduleList = dao.getScheduleByClassId(classId);
                request.setAttribute("scheduleList", scheduleList);
                request.setAttribute("classId", classId);
                request.setAttribute("className", className);
                request.getRequestDispatcher("scheduleByClass.jsp").forward(request, response);
                return;
            }

            if ("2".equals(mode)) {
                Schedules s = dao.getSchedulesById(scheduleId);
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;
            }

            if ("3".equals(mode)) {
                dao.delete(scheduleId);
                request.setAttribute("msg", "Đã xóa lịch học thành công.");

                scheduleList = dao.getScheduleByClassId(classId);
                request.setAttribute("scheduleList", scheduleList);
                request.setAttribute("classId", classId);
                request.setAttribute("className", className);

                request.getRequestDispatcher("scheduleByClass.jsp").forward(request, response);
                return;
            }

            if (from != null && !from.isEmpty() && to != null && !to.isEmpty()) {
                scheduleList = dao.getSchedulesByTime(classId, from, to);
            } else if (date != null && !date.trim().isEmpty()) {
                scheduleList = dao.getSchedulesByClassIdAndDate(classId, date);
            } else {
                scheduleList = dao.getScheduleByClassId(classId);
            }

            request.setAttribute("scheduleList", scheduleList);
            request.setAttribute("classId", classId);
            request.setAttribute("className", className);
            request.setAttribute("date", date);
            request.setAttribute("from", from);
            request.setAttribute("to", to);
            request.getRequestDispatcher("scheduleByClass.jsp").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("err", "Lỗi hệ thống: " + e.getMessage());
          
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        ScheduleDAO dao = new ScheduleDAO();

        if ("deleteOne".equals(action)) {
            String scheduleId = request.getParameter("scheduleId");
            String classId = request.getParameter("classId");
            dao.deleteScheduleById(scheduleId);
            response.sendRedirect("scheduleByClass?id=" + classId + "&mode=1");
            return;
        }

        String scheduleId = request.getParameter("scheduleId");
        String classID = request.getParameter("classname");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String day = request.getParameter("date");
        String teacherID = request.getParameter("teacher");
        String room = request.getParameter("room");

        Schedules s = new Schedules(scheduleId, classID, startTime, endTime, day, teacherID, room);

        if (request.getParameter("update") != null) {
            if (classID.isEmpty() || startTime.isEmpty() || endTime.isEmpty()
                    || day.isEmpty() || teacherID.isEmpty() || room.isEmpty()) {
                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin.");
            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
            } else if (dao.isScheduleExist(s, true)) {
                request.setAttribute("err", "Phòng học " + room + " đã được sử dụng vào thời gian này.");
            } else {
                dao.update(s);
                HttpSession session = request.getSession();
                session.setAttribute("msg", "Đã sửa lịch học thành công.");
                response.sendRedirect("scheduleByClass?id=" + classID + "&mode=1");
                return;
            }

            request.setAttribute("s", s);
            request.setAttribute("data1", dao.getCategories_class());
            request.setAttribute("data3", dao.getCategoriesTeacher());
            request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
            return;
        }

    }
}