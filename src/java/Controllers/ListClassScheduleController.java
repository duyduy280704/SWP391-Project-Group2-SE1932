/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import models.CategoriesTeacher;
import models.Categories_class;
import models.ScheduleDAO;
import models.Schedules;
// Mai Thuy _ Danh sách lớp có thời khóa biểu, có thể sửa xóa, tạo
public class ListClassScheduleController extends HttpServlet {

    ScheduleDAO dao = new ScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mode = request.getParameter("mode");

        if ("1".equals(mode)) {
            ArrayList<Categories_class> data1 = dao.getCategories_class();
            request.setAttribute("data1", data1);

            ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
            request.setAttribute("data3", data3);

            request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
        } else {
            
            List<Categories_class> classList = dao.getClassesHaveSchedule();
            request.setAttribute("classList", classList);
            request.getRequestDispatcher("ListClassSchedule.jsp").forward(request, response);
        }
    }

    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("search".equals(action)) {
            String keyword = request.getParameter("search");
            List<Categories_class> classList = (keyword != null && !keyword.trim().isEmpty())
                    ? dao.searchClass(keyword)
                    : dao.getClassesHaveSchedule();

            request.setAttribute("classList", classList);
            request.getRequestDispatcher("ListClassSchedule.jsp").forward(request, response);
        }

        if ("delete".equals(action)) {
    String classId = request.getParameter("classId");
    dao.deleteScheduleByClassId(classId);
    response.sendRedirect("listClassSchedule");  
    return;
}


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
                ArrayList<Categories_class> data1 = dao.getCategories_class();
                request.setAttribute("data1", data1);

                ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
                request.setAttribute("data3", data3);
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;

            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
                request.setAttribute("s", s);
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;
            } else if (dao.isScheduleExist(s, true)) { // chưa kiểm tra nếu trùng một ít giờ thì sao 
                request.setAttribute("err", "Lịch học này đã tồn tại. Vui lòng kiểm tra lại.");
                request.setAttribute("s", s);
                ArrayList<Categories_class> data1 = dao.getCategories_class();
                request.setAttribute("data1", data1);

                ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
                request.setAttribute("data3", data3);
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

        ArrayList<Categories_class> data1 = dao.getCategories_class();
        request.setAttribute("data1", data1);

        ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
        request.setAttribute("data3", data3);

        request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
        return;

    } else if (startTime.compareTo(endTime) >= 0) {
        request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
        request.setAttribute("s", s);

        ArrayList<Categories_class> data1 = dao.getCategories_class();
        request.setAttribute("data1", data1);

        ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
        request.setAttribute("data3", data3);

        request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
        return;

    }
    else {  
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate inputDate = LocalDate.parse(day, formatter);
            LocalDate today = LocalDate.now();

            if (inputDate.isBefore(today)) {
                request.setAttribute("err", "Không thể tạo lịch học trong quá khứ.");
                request.setAttribute("s", s);

                ArrayList<Categories_class> data1 = dao.getCategories_class();
                request.setAttribute("data1", data1);

                ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
                request.setAttribute("data3", data3);

                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;
            }
        } catch (Exception e) {
            request.setAttribute("err", "Định dạng ngày không hợp lệ.");
            request.setAttribute("s", s);

            ArrayList<Categories_class> data1 = dao.getCategories_class();
            request.setAttribute("data1", data1);

            ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
            request.setAttribute("data3", data3);

            request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
            return;
        }

        if (dao.isScheduleExist(s, false)) {
            request.setAttribute("err", "Lịch học này đã tồn tại. Vui lòng kiểm tra lại.");
            request.setAttribute("s", s);

            ArrayList<Categories_class> data1 = dao.getCategories_class();
            request.setAttribute("data1", data1);

            ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
            request.setAttribute("data3", data3);

            request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
            return;
        }

        dao.add(s);
        response.sendRedirect("listClassSchedule");
        return;
    }
}

        request.setAttribute("schedule", schedule);
        request.getRequestDispatcher("ListClassSchedule.jsp").forward(request, response);

    }

}