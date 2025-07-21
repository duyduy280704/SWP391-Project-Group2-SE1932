package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;

import models.CategoriesTeacher;
import models.Categories_class;
import models.ScheduleDAO;
import models.Schedules;

public class ScheduleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ScheduleDAO dao = new ScheduleDAO();
        String mode = request.getParameter("mode");
        String id = request.getParameter("id");

        if ("getTeachersByClass".equals(mode)) {
            String classId = request.getParameter("classId");
            List<CategoriesTeacher> teachers = dao.getTeachersByClass(classId);

            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print("[");
            for (int i = 0; i < teachers.size(); i++) {
                CategoriesTeacher t = teachers.get(i);
                out.print("{\"id\":\"" + t.getIDTeacher() + "\",\"name\":\"" + t.getNameTeacher() + "\"}");
                if (i < teachers.size() - 1) {
                    out.print(",");
                }
            }
            out.print("]");
            out.flush();
            return;
        }

        List<Schedules> scheduleList = dao.getSchedules();

        if ("1".equals(mode)) {
            request.setAttribute("data1", dao.getCategories_class());
            request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
            return;

        } else if ("2".equals(mode)) {
            Schedules s = dao.getSchedulesById(id);
            request.setAttribute("s", s);
            request.setAttribute("data1", dao.getCategories_class());
            String classId = s.getId_class();
            List<CategoriesTeacher> teachers = dao.getTeachersByClass(classId);

            request.setAttribute("data3", teachers);

            request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
            return;

        } else if ("3".equals(mode)) {
            dao.delete(id);
            request.setAttribute("msg", "Đã xóa lịch học thành công.");
            scheduleList = dao.getSchedules();

        } else if ("filter".equals(mode)) {
            String date = request.getParameter("date");
            if (date == null || date.trim().isEmpty()) {
                request.setAttribute("err", "Vui lòng chọn ngày học.");
            } else {
                scheduleList = dao.filterSchedulesByDate(date);
                if (scheduleList.isEmpty()) {
                    request.setAttribute("err", "Không tìm thấy lịch học phù hợp với bộ lọc.");
                } else {
                    request.setAttribute("msg", "Đã lọc lịch học thành công.");
                }
            }
        }

        request.setAttribute("schedule", scheduleList);
        request.getRequestDispatcher("schedule.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ScheduleDAO dao = new ScheduleDAO();
        String scheduleId = request.getParameter("scheduleId");
        String classID = request.getParameter("id_class");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String day = request.getParameter("date"); // Đúng: Lấy từ form
        String teacherID = request.getParameter("id_teacher");
        String room = request.getParameter("room");
        String keyword = request.getParameter("keyword");

        List<Schedules> scheduleList = dao.getSchedules();

        boolean missing = classID == null || classID.equals("0")
                || startTime == null || startTime.isEmpty()
                || endTime == null || endTime.isEmpty()
                || day == null || day.isEmpty()
                || teacherID == null || teacherID.equals("0")
                || room == null || room.isEmpty();

        if (request.getParameter("update") != null) {
            Schedules s = new Schedules(scheduleId, null, startTime, endTime, day, teacherID, room, classID); // Sửa: Dùng constructor 8 tham số
            String conflictMsg = dao.getConflictMessage(s, true);

            if (missing) {
                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin để sửa lịch học.");
            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
            } else if (dao.isScheduleExist(s, true)) {
                request.setAttribute("err", "Lịch học này đã tồn tại. Vui lòng kiểm tra lại.");
            } else if (conflictMsg != null) {
                request.setAttribute("err", conflictMsg);
            } else {
                dao.update(s);
                request.setAttribute("msg", "Đã sửa lịch học thành công.");
                scheduleList = dao.getScheduleByClassId(classID);
                request.setAttribute("scheduleList", scheduleList);
                request.setAttribute("classId", classID);
                request.setAttribute("className", request.getParameter("name_class"));
                request.getRequestDispatcher("scheduleByClass.jsp").forward(request, response);
                return;
            }

            Schedules sOld = dao.getSchedulesById(scheduleId);
            request.setAttribute("s", sOld);
            request.setAttribute("data1", dao.getCategories_class());
            List<CategoriesTeacher> teachers = dao.getTeachersByClass(classID);
            request.setAttribute("data3", teachers);
            request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
            return;
        }

        if (request.getParameter("add") != null) {
            String[] days = request.getParameterValues("days");
            boolean addMissing = classID == null || classID.equals("0")
                    || startTime == null || startTime.isEmpty()
                    || endTime == null || endTime.isEmpty()
                    || day == null || day.isEmpty() // Sửa: Dùng day
                    || teacherID == null || teacherID.equals("0")
                    || room == null || room.isEmpty()
                    || days == null || days.length == 0;

            if (addMissing) {
                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin để thêm lịch học.");
            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
            } else {
                List<Integer> selectedDays = new ArrayList<>();
                for (String d : days) {
                    int dayValue = Integer.parseInt(d);
                    selectedDays.add(dayValue % 7); // Chuyển 1-7 thành 0-6 (7 mod 7 = 0 cho Chủ nhật)
                }

                Schedules sNew = new Schedules(null, null, startTime, endTime, day, teacherID, room, classID); // Sửa: Dùng day
                boolean exists = dao.isScheduleExist(sNew, false);
                String conflict = dao.getConflictMessage(sNew, false);

                if (exists) {
                    request.setAttribute("err", "Lịch học này đã tồn tại. Vui lòng kiểm tra lại.");
                } else if (conflict != null) {
                    request.setAttribute("err", conflict);
                } else {
                    int totalSessions = dao.getNumberOfSessionsByClassId(classID);
                    dao.add(sNew, totalSessions, selectedDays);
                    HttpSession session = request.getSession();
                    session.setAttribute("msg", "Đã tạo " + totalSessions + " buổi học thành công.");
                    response.sendRedirect("listClassSchedule");
                    return;
                }
            }

            Schedules s = new Schedules(null, null, startTime, endTime, day, teacherID, room, classID); // Sửa: Dùng day
            request.setAttribute("s", s);
            request.setAttribute("data1", dao.getCategories_class());
            List<CategoriesTeacher> teachers = dao.getTeachersByClass(classID);
            request.setAttribute("data3", teachers);
            request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
            return;
        }

        if (request.getParameter("search") != null) {
            if (keyword == null || keyword.trim().isEmpty()) {
                request.setAttribute("err", "Vui lòng nhập từ khóa tìm kiếm.");
            } else {
                System.out.println("Tìm kiếm với từ khóa: " + keyword); // Thêm log
                scheduleList = dao.getScheduleByName(keyword);
                if (scheduleList == null || scheduleList.isEmpty()) {
                    request.setAttribute("err", "Không tìm thấy lịch học với từ khóa: " + keyword);
                } else {
                    request.setAttribute("msg", "Đã tìm kiếm với từ khóa: " + keyword);
                        request.setAttribute("scheduleList", scheduleList);
                }
            }
            request.getRequestDispatcher("ListClassSchedule.jsp").forward(request, response);
            return;
        }
        request.setAttribute("schedule", scheduleList);
        request.getRequestDispatcher("ListClassSchedule.jsp").forward(request, response);

    }
}
