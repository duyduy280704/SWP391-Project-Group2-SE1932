package Controllers;

import java.io.IOException;
import java.util.regex.Pattern;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import java.time.LocalDate;
import models.*;

public class RegistrationCourseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Students student = (Students) session.getAttribute("account");
        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {
            request.setAttribute("error", "Thiếu mã khóa học.");
            if (student != null) {
                request.getRequestDispatcher("registerCourse.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("course_registration.jsp").forward(request, response);
            }
            return;
        }

        CourseDAO courseDAO = new CourseDAO();
        Courses course = courseDAO.getCoursesById(id);

        // Xử lý mã giảm giá
        String saleCode = request.getParameter("saleCode");
        double salePercent = 0;
        Sale usedSale = null;
        String saleMessage = null;

        if (saleCode != null && !saleCode.trim().isEmpty()) {
            SaleDAO saleDAO = new SaleDAO();
            Sale sale = saleDAO.getSaleByCode(saleCode.trim());
            if (sale == null) {
                saleMessage = "Mã giảm giá không hợp lệ.";
            } else if (sale.getQuantity() <= 0) {
                saleMessage = "Mã giảm giá đã hết lượt sử dụng.";
            } else {
                salePercent = sale.getValue();
                usedSale = sale;
            }
        }

        request.setAttribute("saleCode", saleCode);
        request.setAttribute("salePercent", salePercent);
        request.setAttribute("saleMessage", saleMessage);
        request.setAttribute("course", course);

        if (request.getParameter("email") == null) {
            if (student != null) {
                request.getRequestDispatcher("registerCourse.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("course_registration.jsp").forward(request, response);
            }
            return;
        }

        String fullName = request.getParameter("full_name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String birthDate = request.getParameter("birth_date");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String note = request.getParameter("note");

        if (student == null) {
            String errorMessage = null;
            if (fullName == null || fullName.trim().isEmpty()) {
                errorMessage = "Họ và tên không được để trống.";
            } else if (email == null || !email.matches("^[\\w.+\\-]+@gmail\\.com$")) {
                errorMessage = "Email phải hợp lệ và kết thúc bằng @gmail.com.";
            } else if (phone != null && !phone.matches("\\d{10}")) {
                errorMessage = "Số điện thoại phải có 10 số!";
            } else if (birthDate == null || birthDate.trim().isEmpty()) {
                errorMessage = "Ngày sinh không được để trống.";
            } else if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", birthDate)) {
                errorMessage = "Ngày sinh phải đúng định dạng yyyy-MM-dd.";
            } else if (gender == null || gender.trim().isEmpty()) {
                errorMessage = "Vui lòng chọn giới tính.";
            } else if (address == null || address.trim().isEmpty()) {
                errorMessage = "Địa chỉ không được để trống.";
            }

            if (errorMessage != null) {
                request.setAttribute("error", errorMessage);
                request.setAttribute("fullName", fullName);
                request.setAttribute("email", email);
                request.setAttribute("phone", phone);
                request.setAttribute("birthDate", birthDate);
                request.setAttribute("gender", gender);
                request.setAttribute("address", address);
                request.setAttribute("note", note);
                request.setAttribute("course", course);
                request.setAttribute("salePercent", salePercent);
                request.setAttribute("saleCode", saleCode);

                request.getRequestDispatcher("course_registration.jsp").forward(request, response);

                return;
            }
        }

        try {
            int courseId = Integer.parseInt(id);
            if (student != null) {
                RegisitionDAO regDao = new RegisitionDAO();
                if (regDao.isAlreadyRegistered(student.getId(), courseId)) {
                    request.setAttribute("error", "Bạn đã đăng ký khóa học này rồi.");
                } else {
                    regDao.insert(new Regisition(student.getId(), courseId, note, "chưa phân lớp"));

                    PaymentDAO payDao = new PaymentDAO();
                    double fee = Double.parseDouble(course.getFee());
                    double finalFee = fee * (100 - salePercent) / 100;
                    String today = LocalDate.now().toString();
                    String orderCode = "DKH_" + System.currentTimeMillis();
                    payDao.insert(new Payment(
                            student.getId(),
                            courseId,
                            "Chưa thanh toán",
                            today,             
                            usedSale != null ? usedSale.getId() : null,
                            orderCode
                    ));

                    if (usedSale != null) {
                        SaleDAO saleDAO = new SaleDAO();
                        saleDAO.useSale(usedSale.getId());
                    }

                    NotificationDAO notiDao = new NotificationDAO();
                    notiDao.insert(new Notification(null, student.getId(), "Bạn đã đăng ký thành công khóa học: " + course.getName(), null));

                    SendMail.send(email, "đăng ký khóa học thành công",
                            "Bạn đã đăng ký thành công khóa học: " + course.getName() + "\nHọc phí: " + finalFee + " VNĐ.");

                    request.setAttribute("message", "Bạn đã đăng ký thành công.");
                    
                    
                }
            } else {
                PreRegistrationDAO dao = new PreRegistrationDAO();
                if (dao.isEmailOrPhoneExists(email, phone)) {
                    request.setAttribute("error", "Email hoặc số điện thoại này đã được đăng ký cho khóa học này.");
                    request.setAttribute("fullName", fullName);
                    request.setAttribute("email", email);
                    request.setAttribute("phone", phone);
                    request.setAttribute("birthDate", birthDate);
                    request.setAttribute("gender", gender);
                    request.setAttribute("address", address);
                    request.setAttribute("note", note);
                    request.setAttribute("course", course);
                    request.setAttribute("salePercent", salePercent);
                    request.setAttribute("saleCode", saleCode);
                    request.getRequestDispatcher("course_registration.jsp").forward(request, response);
                    return;
                }
                PreRegistration preReg = new PreRegistration(fullName, email, phone, birthDate, gender, address, courseId, "Đang Chờ", note, usedSale.getId());
                boolean success = dao.insertPreRegistration(preReg);
                if (success) {
                    if (usedSale != null) {
                        SaleDAO saleDAO = new SaleDAO();
                        saleDAO.useSale(usedSale.getId());
                    }
                    request.setAttribute("message", "Đăng ký thành công. Vui lòng chờ xác nhận.");
                } else {
                    request.setAttribute("error", "Không thể lưu đăng ký. Vui lòng thử lại sau.");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID khóa học không hợp lệ.");
        }

        request.setAttribute("course", course);
        request.setAttribute("salePercent", salePercent);
        request.setAttribute("saleCode", saleCode);

        if (student != null) {
            request.getRequestDispatcher("registerCourse.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("course_registration.jsp").forward(request, response);
        }
    }
}
