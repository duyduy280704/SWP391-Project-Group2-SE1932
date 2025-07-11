/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import models.About;
import models.AboutDAO;
import models.Banner;
import models.Blog;
import models.BlogDAO;
import models.CourseDAO;
import models.Courses;
import models.FeedBack;
import models.FeedBackDAO;
import models.Information;
import models.InformationDAO;
import models.PreRegistration;
import models.PreRegistrationDAO;
import models.TeacherDAO;
import models.Teachers;
import models.TypeCourse;

/**
 *
 * @author Dwight
 */
//Dương_homapage
public class HomePageController extends HttpServlet {

    private CourseDAO courseDAO = new CourseDAO();
    private InformationDAO daoI = new InformationDAO();
    private AboutDAO dao2 = new AboutDAO();
    private FeedBackDAO dao1 = new FeedBackDAO();
    private TeacherDAO dao = new TeacherDAO();
    private BlogDAO blogDAO = new BlogDAO();

    public void init() {
        Information setting = daoI.getSetting();
        getServletContext().setAttribute("setting", setting);
        List<TypeCourse> typeList = courseDAO.getType();
        getServletContext().setAttribute("typeList", typeList);
        List<About> list = dao2.getAllAbouts();
        getServletContext().setAttribute("aboutList", list);
        ArrayList<FeedBack> feedbackList = dao1.getFeedbacks();
        getServletContext().setAttribute("feedbackList", feedbackList);
        List<Teachers> teacherlist = dao.getTeachers();
        getServletContext().setAttribute("teacherlist", teacherlist);
        List<Blog> bloglist = blogDAO.getLatest3Blogs();
        getServletContext().setAttribute("bloglist", bloglist);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Courses> course6 = courseDAO.get6Courses();
        request.setAttribute("courseList", course6);
        List<Courses> courses = courseDAO.getAllCourses();
        request.setAttribute("courses", courses);
        List<Teachers> teacherList = dao.get4Teachers();
        request.setAttribute("teacherList", teacherList);
        FeedBackDAO dao1 = new FeedBackDAO();
        ArrayList<FeedBack> feedbackList = dao1.getFeedbacks();
        request.setAttribute("feedbackList", feedbackList);
        List<Blog> blogList = blogDAO.getLatest3Blogs();
        request.setAttribute("blogList", blogList);

        ArrayList<Banner> slides = daoI.getSlides();
        request.setAttribute("slides", slides);
        request.getRequestDispatcher("HomePage.jsp").forward(request, response);
    }
    PreRegistrationDAO daopre = new PreRegistrationDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String full_name = request.getParameter("full_name");
        String email = request.getParameter("email");
        String birth_date = request.getParameter("birth_date");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String course_id_str = request.getParameter("course_id");
        String status = "Đang chờ";

        try {

            int course_id = Integer.parseInt(course_id_str);

            PreRegistration preReg = new PreRegistration();
            preReg.setFull_name(full_name);
            preReg.setEmail(email);
            preReg.setBirth_date(birth_date);
            preReg.setGender(gender);
            preReg.setAddress(address);
            preReg.setCourse_id(course_id);
            preReg.setStatus(status);

            boolean success = daopre.insertPreRegistration(preReg);

            if (success) {
                HttpSession session = request.getSession();
                session.setAttribute("successMessage", "Đăng ký thành công!");
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("error", "Đăng ký thất bại, vui lòng thử lại.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("error", "Dữ liệu không hợp lệ hoặc lỗi server: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}