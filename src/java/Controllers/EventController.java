package Controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.ArrayList;
import models.Event;
import models.EventDAO;

//Huyền
public class EventController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        EventDAO dao = new EventDAO();
        ArrayList<Event> events = dao.getEvents(); 

        request.setAttribute("eventList", events);
        request.getRequestDispatcher("EventDetail.jsp").forward(request, response);
    }
}
