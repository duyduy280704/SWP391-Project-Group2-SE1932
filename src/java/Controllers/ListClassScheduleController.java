package Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.Categories_class;
import models.CategoriesTeacher;
import models.ScheduleDAO;
import models.Schedules;

public class ListClassScheduleController extends HttpServlet {

    ScheduleDAO dao = new ScheduleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mode = request.getParameter("mode");
//thêm lịch 
        if ("1".equals(mode)) {
            request.setAttribute("data1", dao.getCategories_class());
            request.setAttribute("data3", dao.getCategoriesTeacher());
            request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
//  hiển thị lại lịch để sửa tất cả lịch 
        } else if ("editAll".equals(mode)) {
            String classId = request.getParameter("id_class");

            request.setAttribute("data1", dao.getCategories_class());
            request.setAttribute("id_class", classId);

            if (classId != null && !classId.isEmpty()) {
                Schedules s = dao.getFirstScheduleFromToday(classId);
                if (s != null) {
                    request.setAttribute("s", s);
                    request.setAttribute("id_teacher", s.getTeacher());
                    request.setAttribute("date", s.getDay());
                    request.setAttribute("startTime", s.getStartTime());
                    request.setAttribute("endTime", s.getEndTime());
                    request.setAttribute("room", s.getRoom());
                }
            }

            request.getRequestDispatcher("schedule_update_batch.jsp").forward(request, response);
        } else {
            List<Schedules> classList = dao.getClassInfo();
            request.setAttribute("classList", classList);
            request.getRequestDispatcher("ListClassSchedule.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
//tìm kiếm 
       try {
      
        System.out.println("Action: " + action); // Debug
        
        if ("search".equals(action)) {
            String keyword = request.getParameter("search");
            System.out.println("Search keyword: " + keyword); // Debug
            List<Categories_class> classList = (keyword != null && !keyword.trim().isEmpty())
                    ? dao.searchClass(keyword)
                    : dao.getClassesHaveSchedule();
            System.out.println("Class list size: " + classList.size()); // Debug
            
            request.setAttribute("classList", classList);
            request.getRequestDispatcher("ListClassSchedule.jsp").forward(request, response);
            return;
        }
        // ... các phần còn lại của doPost ...
    } catch (Exception e) {
        e.printStackTrace(); // Ghi log lỗi
        request.setAttribute("error", "Lỗi xử lý yêu cầu: " + e.getMessage());
       
    }
//xóa
        if ("delete".equals(action)) {
            String classId = request.getParameter("classId");
            dao.deleteScheduleByClassId(classId);
            response.sendRedirect("listClassSchedule");
            return;
        }
//Sửa tất cả các lịch học từ một ngày trở đi
        if ("batchUpdate".equals(action)) {
            String classId = request.getParameter("id_class");
            String teacherId = request.getParameter("id_teacher");
            String dateFrom = request.getParameter("date");
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String room = request.getParameter("room");

            request.setAttribute("data1", dao.getCategories_class());

            request.setAttribute("id_class", classId);
            request.setAttribute("id_teacher", teacherId);
            request.setAttribute("date", dateFrom);
            request.setAttribute("startTime", startTime);
            request.setAttribute("endTime", endTime);
            request.setAttribute("room", room);

            if (classId == null || teacherId == null || dateFrom == null
                    || startTime == null || endTime == null || room == null
                    || classId.isEmpty() || teacherId.isEmpty() || dateFrom.isEmpty()
                    || startTime.isEmpty() || endTime.isEmpty() || room.isEmpty()) {
                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin.");
                request.getRequestDispatcher("schedule_update_batch.jsp").forward(request, response);
                return;
            }

            if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
                request.getRequestDispatcher("schedule_update_batch.jsp").forward(request, response);
                return;
            }

            dao.updateSchedulesFromDate(classId, dateFrom, teacherId, startTime, endTime, room);

            request.setAttribute("msg", "Đã cập nhật thời khóa biểu từ ngày " + dateFrom);
            response.sendRedirect("listClassSchedule");
            return;
        }

        // sửa 1 lịch học chi tiết
        String scheduleId = request.getParameter("scheduleId");
        String classID = request.getParameter("id_class");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String day = request.getParameter("date");
        String teacherID = request.getParameter("id_teacher");
        String room = request.getParameter("room");

        Schedules s = new Schedules(scheduleId, classID, startTime, endTime, day, teacherID, room);
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
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_update.jsp").forward(request, response);
                return;

            } else if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
                request.setAttribute("s", s);
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
                request.setAttribute("msg", "Đã sửa lịch học thành công.");
                schedule = dao.getSchedules();
            }
//  thêm lịch 
        } else if (request.getParameter("add") != null) {
            String[] days = request.getParameterValues("days");
            boolean missing = classID == null || classID.isEmpty()
                    || startTime == null || startTime.isEmpty()
                    || endTime == null || endTime.isEmpty()
                    || day == null || day.isEmpty()
                    || teacherID == null || teacherID.isEmpty()
                    || room == null || room.isEmpty()
                    || days == null || days.length == 0;

            if (missing) {
                request.setAttribute("err", "Vui lòng nhập đầy đủ thông tin để thêm lịch học.");
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;
            }

            if (startTime.compareTo(endTime) >= 0) {
                request.setAttribute("err", "Giờ kết thúc phải sau giờ bắt đầu.");
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;
            }

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate inputDate = LocalDate.parse(day, formatter);
                if (inputDate.isBefore(LocalDate.now())) {
                    request.setAttribute("err", "Không thể tạo lịch học trong quá khứ.");
                    request.setAttribute("s", s);
                    request.setAttribute("data1", dao.getCategories_class());
                    request.setAttribute("data3", dao.getCategoriesTeacher());
                    request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                request.setAttribute("err", "Định dạng ngày không hợp lệ.");
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;
            }

            // Chuyển days[] sang List<Integer>
            List<Integer> selectedDays = new ArrayList<>();
            for (String d : days) {
                try {
                    int dayValue = Integer.parseInt(d);
                    selectedDays.add(dayValue % 7); // Chủ nhật = 0
                } catch (NumberFormatException ex) {
                    // Bỏ qua giá trị không hợp lệ
                }
            }

            int totalSessions = dao.getNumberOfSessionsByClassId(classID);
            if (totalSessions <= 0) {
                request.setAttribute("err", "Số buổi học không hợp lệ hoặc không tồn tại.");
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;
            }

            if (dao.isScheduleExist(s, false)) {
                request.setAttribute("err", "Lịch học này đã tồn tại. Vui lòng kiểm tra lại.");
                request.setAttribute("s", s);
                request.setAttribute("data1", dao.getCategories_class());
                request.setAttribute("data3", dao.getCategoriesTeacher());
                request.getRequestDispatcher("schedule_add.jsp").forward(request, response);
                return;
            }

            dao.add(s, totalSessions, selectedDays);
            response.sendRedirect("listClassSchedule");
            return;
        }

        request.setAttribute("schedule", schedule);
        request.getRequestDispatcher("ListClassSchedule.jsp").forward(request, response);
    }
}
