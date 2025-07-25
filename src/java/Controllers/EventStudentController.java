package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import models.Event;
import models.EventDAO;
import models.StudentDAO;
import models.Students;

//Huyền
public class EventStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        HttpSession session = request.getSession();
        Students st = (Students) session.getAttribute("account");
        if (st == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        String studentId = st.getId();
        EventDAO dao = new EventDAO();
        ArrayList<Event> events = dao.getEvents(); 
        StudentDAO studentDAO = new StudentDAO();
        Students stu = studentDAO.getStudentById(studentId);
        request.setAttribute("name", stu.getName());
        request.setAttribute("profile", stu); // Truyền thông tin giáo viên
        request.setAttribute("picturePath", session.getAttribute("picturePath"));
        request.setAttribute("eventList", events);
        request.getRequestDispatcher("EventStudent.jsp").forward(request, response);
    }
}