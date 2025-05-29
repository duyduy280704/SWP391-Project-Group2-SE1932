package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.CategoriesTeacher;
import models.Categories_class;
import models.ScheduleDAO;
import models.Schedules;
// Thuy-Thêm, sửa, xóa thời khóa biểu của admin 
public class ScheduleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScheduleDAO dao = new ScheduleDAO();
        List<Schedules> schedule = dao.getSchedules();
        String mode = request.getParameter("mode");
        String id = request.getParameter("id");

        if (mode != null) {
            if (mode.equals("1")) {
                ArrayList<Categories_class> data1 = dao.getCategories_class();
                request.setAttribute("data1", data1);

                ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
                request.setAttribute("data3", data3);
                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;
            }

            if (mode.equals("2")) {
                Schedules s = dao.getSchedulesById(id);
                request.setAttribute("s", s);
                ArrayList<Categories_class> data1 = dao.getCategories_class();
                request.setAttribute("data1", data1);

                ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
                request.setAttribute("data3", data3);
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;
            }

            if (mode.equals("3")) {
                dao.delete(id);
                request.setAttribute("msg", "Đã xóa lịch học thành công.");
                schedule = dao.getSchedules();
            }
        }

        request.setAttribute("schedule", schedule);
        request.getRequestDispatcher("schedule.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String scheduleId = request.getParameter("scheduleId");
        String classID = request.getParameter("classname");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String day = request.getParameter("date");
        String teacherID = request.getParameter("teacher");
        String room = request.getParameter("room");
        String keyword = request.getParameter("keyword");

        Schedules s = new Schedules(scheduleId, classID, startTime, endTime, day, teacherID, room);
        ScheduleDAO dao = new ScheduleDAO();
        ArrayList<Schedules> schedule = dao.getSchedules();

        if (request.getParameter("update") != null) {
            if (classID == null || classID.isEmpty()
                    || startTime == null || startTime.isEmpty()
                    || endTime == null || endTime.isEmpty()
                    || day == null || day.isEmpty()
                    || teacherID == null || teacherID.isEmpty()
                    || room == null || room.isEmpty()) {
                
                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin để sửa lịch học.");
                request.setAttribute("s", s);
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;
                
            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
                request.setAttribute("s", s);
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;
            } else {
                
                dao.update(s);
                request.setAttribute("msg", "Đã sửa lịch học thành công.");
                schedule = dao.getSchedules();
            }
            
        } else if (request.getParameter("add") != null) {
            if (classID == null || classID.isEmpty()
                    || startTime == null || startTime.isEmpty()
                    || endTime == null || endTime.isEmpty()
                    || day == null || day.isEmpty()
                    || teacherID == null || teacherID.isEmpty()
                    || room == null || room.isEmpty()) {

                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin để thêm lịch học.");
                request.setAttribute("s", s);
                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;

            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
                request.setAttribute("s", s);
                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;
                
            } else {
                dao.add(s);
                request.setAttribute("msg", "Đã tạo lịch học thành công.");
                schedule = dao.getSchedules();
            }
            
        } else if (request.getParameter("search") != null) {
            if (keyword == null || keyword.trim().isEmpty()) {
                request.setAttribute("err", "Vui lòng nhập từ khóa tìm kiếm.");
                schedule = dao.getSchedules(); 
            } else {
                schedule = dao.getScheduleByName(keyword);
                if (schedule == null || schedule.isEmpty()) {
                    request.setAttribute("err", "Không tìm thấy lịch học với từ khóa: " + keyword);
                } else {
                    request.setAttribute("msg", "Đã tìm kiếm với từ khóa: " + keyword);
                }
            }
        }

        request.setAttribute("schedule", schedule);
        request.getRequestDispatcher("schedule.jsp").forward(request, response);
    }
}