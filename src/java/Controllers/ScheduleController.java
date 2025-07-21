package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
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
        String day = request.getParameter("date");
        String teacherID = request.getParameter("id_teacher");
        String room = request.getParameter("room");
        String keyword = request.getParameter("keyword");

        Schedules s = new Schedules(scheduleId, classID, startTime, endTime, day, teacherID, room);
        List<Schedules> scheduleList = dao.getSchedules();

        boolean missing = classID == null || classID.equals("0")
                || startTime == null || startTime.isEmpty()
                || endTime == null || endTime.isEmpty()
                || day == null || day.isEmpty()
                || teacherID == null || teacherID.equals("0")
                || room == null || room.isEmpty();

        if (request.getParameter("update") != null) {
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

            s = dao.getSchedulesById(scheduleId);
            request.setAttribute("s", s);
            request.setAttribute("data1", dao.getCategories_class());
            List<CategoriesTeacher> teachers = dao.getTeachersByClass(classID);
            request.setAttribute("data3", teachers);
            request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
            return;
        }

        if (request.getParameter("add") != null) {
            String conflictMsg = dao.getConflictMessage(s, false);

            if (missing) {
                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin để thêm lịch học.");
            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
            } else if (dao.isScheduleExist(s, false)) {
                request.setAttribute("err", "Lịch học này đã tồn tại. Vui lòng kiểm tra lại.");
            } else if (conflictMsg != null) {
                request.setAttribute("err", conflictMsg);
            } else {
                dao.add(s);
                HttpSession session = request.getSession();
                session.setAttribute("msg", "Đã tạo lịch học thành công.");
                response.sendRedirect("listClassSchedule");
                return;
            }

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
                scheduleList = dao.getScheduleByName(keyword);
                if (scheduleList == null || scheduleList.isEmpty()) {
                    request.setAttribute("err", "Không tìm thấy lịch học với từ khóa: " + keyword);
                } else {
                    request.setAttribute("msg", "Đã tìm kiếm với từ khóa: " + keyword);
                }
            }
        }

        request.setAttribute("schedule", scheduleList);
        request.getRequestDispatcher("schedule.jsp").forward(request, response);
    }
}