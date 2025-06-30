/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.Categories_class;
import models.Courses;
import models.Regisition;
import models.RegisitionDAO;

/**
 *
 * @author Dwight
 */
public class AssignClassController extends HttpServlet {

    RegisitionDAO dao = new RegisitionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String status = request.getParameter("status");
        String studentName = request.getParameter("studentName");

        List<Regisition> list = dao.filterRegisitions(courseId, status, studentName);
        List<Courses> courseList = dao.getAllCourses();

        // Tạo map courseId -> danh sách lớp
        java.util.Map<Integer, List<Categories_class>> classByCourse = new java.util.HashMap<>();
        for (Regisition r : list) {
            int cId = r.getCourseId();
            if (!classByCourse.containsKey(cId)) {
                List<Categories_class> classList = dao.getClassesByCourse(String.valueOf(cId));
                classByCourse.put(cId, classList);
            }
        }

        request.setAttribute("regisitions", list);
        request.setAttribute("courseList", courseList);
        request.setAttribute("classByCourse", classByCourse);

        request.getRequestDispatcher("AssignStudentClass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> paramMap = request.getParameterMap();
        List<String> messages = new ArrayList<>();

        for (String key : paramMap.keySet()) {
            if (key.startsWith("regisitionId_")) {
                try {
                    String regisitionIdStr = key.substring("regisitionId_".length());
                    String classIdStr = request.getParameter(key);

                    if (classIdStr != null && !classIdStr.isEmpty()) {
                        int regisitionId = Integer.parseInt(regisitionIdStr.trim());
                        int classId = Integer.parseInt(classIdStr.trim());

                        boolean assigned = dao.assignToClassSingle(regisitionId, classId);
                        String studentName = dao.getStudentNameByRegisitionId(regisitionId); // 👈 Lấy tên học viên

                        if (assigned) {
                            dao.updateStatus(regisitionId, "đã phân lớp");
                        } else {
                            messages.add("⚠️ Học viên <strong>" + studentName + "</strong> đã được phân vào lớp này trước đó.");
                        }
                    }

                } catch (NumberFormatException e) {
                    messages.add("❌ Lỗi định dạng số ở " + key + ": " + e.getMessage());
                }
            }
        }

        // Truyền message sang JSP
        request.getSession().setAttribute("messages", messages);
        response.sendRedirect("AssignClass");
    }

}
