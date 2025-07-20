/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

/**
 *
 * @author Dwight
 */
public class EventDAO extends DBContext {

    private PreparedStatement stm;
    private ResultSet rs;

    public List<Event> getUpcomingEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT id, name, date FROM event WHERE date >= GETDATE() ORDER BY date ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String title = rs.getString("name");
                String startDate = rs.getString("date");

                Event event = new Event(id, title, startDate);
                list.add(event);
            }
        } catch (Exception e) {
            System.out.println("getUpcomingEvents: " + e.getMessage());
        }

        return list;
    }

    public List<Event> getRecentEvents(int limit) {
        List<Event> list = new ArrayList<>();

        try {
            String sql = "SELECT TOP (?) * FROM event ORDER BY date ASC";
            stm = connection.prepareStatement(sql);
            stm.setInt(1, limit);
            rs = stm.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getString("id"));
                e.setName(rs.getString("name"));
                e.setContent(rs.getString("content"));
                e.setImg(rs.getBytes("img"));
                e.setDate(rs.getString("date"));
                e.setCourseid(rs.getString("id_course"));
                list.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        EventDAO ev = new EventDAO();
        List<Event> events = ev.getRecentEvents(3);
        for (Event e : events) {
            System.out.println(e.name);
        }
    }
    
        // lấy dữ liệu bảng event
    public ArrayList<Event> getEvents() {

        ArrayList<Event> data = new ArrayList<>();
        try {
            String strSQL = "select * from event s join Course r on s.id_course = r.id";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String content = rs.getString(3);
                String date = rs.getString(5);
                String course = rs.getString(8);
                byte[] img = rs.getBytes(4);

                Event p = new Event(id, name, content, date, course, img);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getEvents" + e.getMessage());

        }
        return data;
    }

    // thêm  event
    public ResultMessage addEvent(Event event) throws SQLException {
        if (event == null) {
            return new ResultMessage(false, "Dữ liệu sự kiện không hợp lệ.");
        }
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            return new ResultMessage(false, "Tên sự kiện không được để trống.");
        }
        if (event.getContent() == null || event.getContent().trim().isEmpty()) {
            return new ResultMessage(false, "Nội dung sự kiện không được để trống.");
        }
        if (event.getDate() == null || event.getDate().trim().isEmpty()) {
            return new ResultMessage(false, "Ngày sự kiện không được để trống.");
        }
        if (event.getCourseid() == null || event.getCourseid().trim().isEmpty()) {
            return new ResultMessage(false, "Khóa học không được để trống.");
        }

        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "INSERT INTO [event] (name, content, date, id_course, img) VALUES (?, ?, ?, ?, ?)")) {
            stm.setString(1, event.getName());
            stm.setString(2, event.getContent());
            stm.setString(3, event.getDate());
            stm.setString(4, event.getCourseid());
            if (event.getImg() != null) {
                stm.setBytes(5, event.getImg());
            } else {
                stm.setNull(5, Types.BLOB);
            }
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Thêm sự kiện thành công!" : "Không thể thêm sự kiện.");
        } catch (SQLException e) {
            System.err.println("addEvent: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // sửa event
    public ResultMessage updateEvent(Event event) throws SQLException {
        if (event == null) {
            return new ResultMessage(false, "Dữ liệu sự kiện không hợp lệ.");
        }
        if (event.getId() == null || event.getId().trim().isEmpty()) {
            return new ResultMessage(false, "ID sự kiện không được để trống.");
        }
        if (event.getName() == null || event.getName().trim().isEmpty()) {
            return new ResultMessage(false, "Tên sự kiện không được để trống.");
        }
        if (event.getContent() == null || event.getContent().trim().isEmpty()) {
            return new ResultMessage(false, "Nội dung sự kiện không được để trống.");
        }
        if (event.getDate() == null || event.getDate().trim().isEmpty()) {
            return new ResultMessage(false, "Ngày sự kiện không được để trống.");
        }
        if (event.getCourseid() == null || event.getCourseid().trim().isEmpty()) {
            return new ResultMessage(false, "Khóa học không được để trống.");
        }

        if (connection == null) {
            return new ResultMessage(false, "Kết nối cơ sở dữ liệu chưa được khởi tạo.");
        }

        int eventId;
        try {
            eventId = Integer.parseInt(event.getId());
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID sự kiện phải là một số hợp lệ: " + event.getId());
        }

        try (PreparedStatement stm = connection.prepareStatement(
                "UPDATE [event] SET name = ?, content = ?, date = ?, id_course = ?, img = ? WHERE id = ?")) {
            stm.setString(1, event.getName());
            stm.setString(2, event.getContent());
            stm.setString(3, event.getDate());
            stm.setString(4, event.getCourseid());
            if (event.getImg() != null) {
                stm.setBytes(5, event.getImg());
            } else {
                stm.setNull(5, Types.BLOB);
            }
            stm.setInt(6, eventId);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Cập nhật sự kiện thành công!" : "Không tìm thấy sự kiện với ID: " + event.getId());
        } catch (SQLException e) {
            System.err.println("updateEvent: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // lấy  khóa học
    public ArrayList<Courses> getCourse() {
        ArrayList<Courses> data = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement("SELECT * FROM Course")) {
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String code = String.valueOf(rs.getInt("id"));
                String name = rs.getString("name");
                Courses c = new Courses(code, name);
                data.add(c);
            }
        } catch (SQLException e) {
            System.err.println("getCourseType: " + e.getMessage());
        }
        return data;
    }

    // lấy event theo id
    public Event getEventById(String id) {
        try {
            String strSQL = "select * from event where id=?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {

                String name = rs.getString(2);
                String content = rs.getString(3);

                String date = rs.getString(5);
                String id_course = rs.getString(6);
                byte[] image = rs.getBytes(4);

                Event p = new Event(id, name, content, date, id_course, image);
                return p;
            }
        } catch (Exception e) {
            System.out.println("getCoursesById" + e.getMessage());

        }
        return null;
    }

    // xóa event
    public ResultMessage deleteEvent(String id) {
        if (id == null || id.trim().isEmpty()) {
            return new ResultMessage(false, "ID sự kiện không được để trống.");
        }

        int eventId;
        try {
            eventId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new ResultMessage(false, "ID sự kiện phải là một số hợp lệ: " + id);
        }

        try (PreparedStatement stm = connection.prepareStatement("DELETE FROM [event] WHERE id = ?")) {
            stm.setInt(1, eventId);
            int rowsAffected = stm.executeUpdate();
            return new ResultMessage(rowsAffected > 0, rowsAffected > 0 ? "Xóa sự kiện thành công!" : "Không tìm thấy sự kiện với ID: " + id);
        } catch (SQLException e) {
            System.err.println("deleteEvent: " + e.getMessage());
            return new ResultMessage(false, "Lỗi cơ sở dữ liệu: " + e.getMessage());
        }
    }

    // lấy ảnh của sự kiện theo id
    public byte[] getEventImageById(String eventId) throws SQLException {
        if (eventId == null || eventId.isEmpty()) {
            return null;
        }
        try (PreparedStatement stm = connection.prepareStatement("SELECT img  FROM event WHERE id = ?")) {
            stm.setInt(1, Integer.parseInt(eventId));
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getBytes("img");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong getEventImageById: " + e.getMessage() + ", SQLState: " + e.getSQLState() + ", ErrorCode: " + e.getErrorCode());
            throw e;
        } catch (NumberFormatException e) {
            System.err.println("Invalid event ID: " + eventId);
            throw e;
        }
        return null;
    }

    // tìm kiếm event theo tên 
    public ArrayList<Event> getEventByName(String name1) {
        ArrayList<Event> data = new ArrayList<>();
        try {
            String strSQL = "  SELECT * FROM event s join Course r on s.id_course = r.id WHERE s.name LIKE ? ";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + name1 + "%");
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String content = rs.getString(3);
                String date = rs.getString(5);
                String course = rs.getString(8);
                byte[] img = rs.getBytes(4);

                Event p = new Event(id, name, content, date, course, img);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getEvents" + e.getMessage());

        }
        return data;
    }

    // tìm kiếm event theo khóa học
    public ArrayList<Event> getEventsByCourse(String course) {
        ArrayList<Event> data = new ArrayList<>();
        try {
            String strSQL = "SELECT * FROM event s join Course r on s.id_course = r.id WHERE s.id_course = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, course);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String name = rs.getString(2);
                String content = rs.getString(3);
                String date = rs.getString(5);
                String courseName = rs.getString(8);
                byte[] img = rs.getBytes(4);

                Event p = new Event(id, name, content, date, courseName, img);
                data.add(p);
            }
        } catch (Exception e) {
            System.out.println("getEventsByCourse: " + e.getMessage());
        }
        return data;
    }
    //Huyền
     public byte[] getEventImage(String eventId) {
        byte[] image = null;
        try {
            String sql = "SELECT img FROM event WHERE id = ?";
            stm = connection.prepareStatement(sql);
            stm.setString(1, eventId);
            rs = stm.executeQuery();
            if (rs.next()) {
                image = rs.getBytes("img");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return image;
    }

}
