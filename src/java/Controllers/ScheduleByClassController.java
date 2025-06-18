package Controllers;

import java.io.IOException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.CategoriesTeacher;
import models.Categories_class;
import models.ScheduleDAO;
import models.Schedules;

public class ScheduleByClassController extends HttpServlet {

    @Override
    
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String mode = request.getParameter("mode");
    String classId = request.getParameter("id");
    String date = request.getParameter("date");
    String className = request.getParameter("name");
    String scheduleId = request.getParameter("sid");

    ScheduleDAO dao = new ScheduleDAO();
    ArrayList<Schedules> scheduleList = new ArrayList<>();

   
    if ("searchByClass".equals(mode)) {
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            request.setAttribute("err", "Vui lòng nhập từ khóa tìm kiếm.");
            scheduleList = dao.getScheduleByClassId(classId);
        } else {
            scheduleList = dao.searchScheduleByClassIdAndKeyword(classId, keyword);
            if (scheduleList == null || scheduleList.isEmpty()) {
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
        if (classId == null || classId.trim().isEmpty()) {
            response.sendRedirect("schedule.jsp");
            return;
        }
        request.setAttribute("className", className);
        scheduleList = dao.getScheduleByClassId(classId);
        request.setAttribute("scheduleList", scheduleList);
        request.setAttribute("classId", classId);
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
        request.getRequestDispatcher("scheduleByClass.jsp").forward(request, response);
        return;
    }

  
    if (date != null && !date.trim().isEmpty()) {
        scheduleList = dao.getSchedulesByClassIdAndDate(classId, date);
    } else if (classId != null && !classId.trim().isEmpty()) {
        scheduleList = dao.getScheduleByClassId(classId);
    }

    request.setAttribute("scheduleList", scheduleList);
    request.setAttribute("classId", classId);
    request.setAttribute("date", date);
    request.getRequestDispatcher("scheduleByClass.jsp").forward(request, response);
}


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

       
        if ("deleteOne".equals(action)) {
            String scheduleId = request.getParameter("scheduleId");
            String classId = request.getParameter("classId");

            ScheduleDAO dao = new ScheduleDAO();
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

        ScheduleDAO dao = new ScheduleDAO();
        Schedules s = new Schedules(scheduleId, classID, startTime, endTime, day, teacherID, room);

        if (request.getParameter("update") != null) {
            if (classID == null || classID.isEmpty()
                    || startTime == null || startTime.isEmpty()
                    || endTime == null || endTime.isEmpty()
                    || day == null || day.isEmpty()
                    || teacherID == null || teacherID.isEmpty()
                    || room == null || room.isEmpty()) {

                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin để sửa lịch học.");
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;

            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;

            } else if (dao.isScheduleExist(s, true)) {
                request.setAttribute("err", "Lịch học này đã tồn tại. Vui lòng kiểm tra lại.");
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;

            } else {
                dao.update(s);
                response.sendRedirect("scheduleByClass?id=" + classID + "&mode=1");
            }
        }
    }
}
