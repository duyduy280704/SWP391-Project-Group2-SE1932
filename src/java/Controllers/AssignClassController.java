package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Categories_class;
import models.Courses;
import models.Regisition;
import models.RegisitionDAO;

public class AssignClassController extends HttpServlet {

    RegisitionDAO dao = new RegisitionDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String courseId = request.getParameter("courseId");
        String status = request.getParameter("status");
        String studentName = request.getParameter("studentName");

        List<Regisition> list = dao.filterRegisitions(courseId, status, studentName);
        Map<Integer, String> assignedClassNames = new HashMap<>();
        for (Regisition r : list) {
            String className = dao.getAssignedClassName(r.getId());
            if (className != null) {
                assignedClassNames.put(r.getId(), className);
            }
        }

        List<Courses> courseList = dao.getAllCourses();

        // Tạo map courseId -> danh sách lớp và trạng thái lớp
        java.util.Map<Integer, List<Categories_class>> classByCourse = new java.util.HashMap<>();
        java.util.Map<String, Boolean> classFullStatus = new java.util.HashMap<>();
        java.util.Map<String, Integer> classStudentCount = new java.util.HashMap<>();

        for (Regisition r : list) {
            int cId = r.getCourseId();
            if (!classByCourse.containsKey(cId)) {
                List<Categories_class> classList = dao.getClassesByCourse(String.valueOf(cId));
                // Lọc chỉ lấy lớp có status "chưa bắt đầu"
                List<Categories_class> filteredClassList = new ArrayList<>();
                for (Categories_class cls : classList) {
                    if ("chưa bắt đầu".equalsIgnoreCase(cls.getStatus())) {
                        filteredClassList.add(cls);
                        int classId = Integer.parseInt(cls.getId_class());
                        boolean isFull = dao.isClassFull(classId);
                        int studentCount = dao.getStudentCountInClass(classId);
                        classFullStatus.put(cls.getId_class(), isFull);
                        classStudentCount.put(cls.getId_class(), studentCount);
                    }
                }
                classByCourse.put(cId, filteredClassList);
            }
        }

        request.setAttribute("regisitions", list);
        request.setAttribute("status", status);
        request.setAttribute("courseList", courseList);
        request.setAttribute("classByCourse", classByCourse);
        request.setAttribute("classFullStatus", classFullStatus);
        request.setAttribute("classStudentCount", classStudentCount);
        request.setAttribute("assignedClassNames", assignedClassNames);

        request.getRequestDispatcher("AssignStudentClass.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> paramMap = request.getParameterMap();
        List<String> messages = new ArrayList<>();
        String action = request.getParameter("action");

        if ("unassign".equals(action)) {
            try {
                int regisitionId = Integer.parseInt(request.getParameter("regisitionId"));
                String studentName = dao.getStudentNameByRegisitionId(regisitionId);
                int courseId = dao.getCourseIdByRegisitionId(regisitionId);
                String studentId = dao.getStudentIdByRegisitionId(regisitionId);

                // Xoá khỏi bảng Class_Student
                dao.removeFromClass(studentId, courseId);

                // Cập nhật trạng thái đăng ký
                dao.updateStatus(regisitionId, "chưa phân lớp");

                messages.add("🗑 Đã huỷ phân lớp học viên <strong>" + studentName + "</strong> thành công.");
            } catch (Exception e) {
                e.printStackTrace();
                messages.add("❌ Lỗi khi huỷ phân lớp: " + e.getMessage());
            }

            request.getSession().setAttribute("messages", messages);
            response.sendRedirect("AssignClass");
            return;
        }

        for (String key : paramMap.keySet()) {
            if (key.startsWith("regisitionId_")) {
                try {
                    String regisitionIdStr = key.substring("regisitionId_".length());
                    String classIdStr = request.getParameter(key);

                    if (classIdStr != null && !classIdStr.trim().isEmpty()) {
                        int regisitionId = Integer.parseInt(regisitionIdStr.trim());
                        int classId = Integer.parseInt(classIdStr.trim());
                        String studentName = dao.getStudentNameByRegisitionId(regisitionId);

                        // ️ Check lớp đã phân
                        if (dao.isStudentInClass(regisitionId, classId)) {
                            messages.add("❌ Lỗi khi phân lớp: Học viên <strong>" + studentName + "</strong> đã có trong lớp " + classId);
                            continue;
                        }

                        // ️ Check lớp đầy
                        if (dao.isClassFull(classId)) {
                            messages.add("⚠️ Lớp đã đủ 30 học viên, không thể phân <strong>" + studentName + "</strong> vào lớp này.");
                            continue;
                        }

                        //  Phân lớp
                        boolean assigned = dao.assignToClassSingle(regisitionId, classId);
                        if (assigned) {
                            messages.add("✅ Đã phân lớp thành công cho học viên <strong>" + studentName + "</strong>.");
                        } else {
                            messages.add("⚠️ Phân lớp thất bại cho học viên <strong>" + studentName + "</strong>.");
                        }
                    } else {
                        messages.add("⚠️ Chưa chọn lớp cho học viên.");
                    }
                } catch (NumberFormatException e) {
                    messages.add("❌ Lỗi định dạng ID: " + e.getMessage());
                } catch (Exception e) {
                    messages.add("❌ Lỗi khi phân lớp: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        request.getSession().setAttribute("messages", messages);
        response.sendRedirect("AssignClass");
    }

}