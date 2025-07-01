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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import models.FeedBack;
import models.FeedBackDAO;

/**
 *
 * @author HP
 */
public class FeedbackController extends HttpServlet {

    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        FeedBackDAO dao = new FeedBackDAO();
        ArrayList<FeedBack> feedbackList = dao.getFeedbacks();

        request.setAttribute("feedbackList", feedbackList);
        request.getRequestDispatcher("feedback.jsp").forward(request, response);
    
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
