/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.CategoriesCourse;
import models.CategoriesTeacher;
import models.Categories_class;
import models.ScheduleDAO;
import models.Schedules;

public class ScheduleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScheduleDAO dao = new ScheduleDAO(); // không truyền gì vào constructor
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
            }
            
            if (mode.equals("2")) {
                Schedules s = dao.getSchedulesById(id);//Gọi 1 schedule theo id trả về để khi sang trang update có thể placeholder thông tin cũ
                request.setAttribute("s", s);// trả schedule cũ ra trang jsp
                ArrayList<Categories_class> data1 = dao.getCategories_class();
                request.setAttribute("data1", data1);
                
                ArrayList<CategoriesTeacher> data3 = dao.getCategoriesTeacher();
                request.setAttribute("data3", data3);
                
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
            }
            if (mode.equals("3")) {
                dao.delete(id);
                schedule = dao.getSchedules();
            }
        }

        request.setAttribute("schedule", schedule);
        request.getRequestDispatcher("schedule.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // thực hiện xóa, update, add, search 
        String scheduleId = request.getParameter("scheduleId");
        String classID = request.getParameter("classname");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String day = request.getParameter("date");
        String teacherID = request.getParameter("teacher");
        String room = request.getParameter("room");
        String keyword = request.getParameter("keyword");
        Schedules s = new Schedules(scheduleId, classID, startTime, endTime, day, teacherID, room);
        ScheduleDAO da = new ScheduleDAO();
        ArrayList<Schedules> schedule = da.getSchedules();

        if (request.getParameter("update") != null) {
            da.update(s);
            schedule = da.getSchedules();
            
        }

        if (request.getParameter("add") != null) {
            da.add(s);
            schedule = da.getSchedules();
        }
        if (request.getParameter("search") != null) {
            schedule = da.getScheduleByName(keyword);
        }

        request.setAttribute("schedule", schedule);
        request.getRequestDispatcher("schedule.jsp").forward(request, response);
    }
}
