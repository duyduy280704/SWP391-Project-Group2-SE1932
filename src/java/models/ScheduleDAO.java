package models;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Admin
 */
// Thuy-Thêm, sửa, xóa thời khóa biểu của admin 
public class ScheduleDAO extends DBContext {

    PreparedStatement stm;
    ResultSet rs;
// in tất cả thời khóa biểu 
    public ArrayList<Schedules> getSchedules() {
        ArrayList<Schedules> data = new ArrayList<>();

        try {
            String strSQL = "SELECT s.*,            \n"
                    + "    c.name,         \n"
                    + "    t.full_name       \n"
                    + "FROM \n"
                    + "    schedule AS s\n"
                    + "JOIN \n"
                    + "    Class AS c ON s.id_class = c.id\n"
                    + "JOIN \n"
                    + "    Teacher AS t ON s.id_teacher = t.id;";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id = String.valueOf(rs.getInt(1));
                String nameClass = rs.getString(8);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String day = rs.getString(6);
                String nameTeacher = rs.getString(9);
                String room = rs.getString(7);
                Schedules s = new Schedules(id, nameClass, start, end, day, nameTeacher, room);
                data.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi getAllSchedules: " + e.getMessage());
        }

        System.out.println("Tìm thấy " + data.size() + " lịch học.");
        return data;
    }

    // timf kiếm 
    public ArrayList<Schedules> getScheduleByName(String keyword) {
        ArrayList<Schedules> data = new ArrayList<>();

        try {
            String strSQL = "SELECT \n"
                    + "*\n"
                    + "FROM \n"
                    + "    schedule s\n"
                    + "JOIN \n"
                    + "    Class c ON s.id_class = c.id\n"
                    + "JOIN \n"
                    + "    Teacher t ON s.id_teacher = t.id\n"
                    + "	WHERE c.name like ?";

            stm = connection.prepareStatement(strSQL);
            stm.setString(1, "%" + keyword + "%");
            rs = stm.executeQuery();

            while (rs.next()) {

                String id = String.valueOf(rs.getInt(1));
                String nameClass = rs.getString(9);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String day = rs.getString(6);
                String nameTeacher = rs.getString(13);
                String room = rs.getString(7);
                Schedules s = new Schedules(id, nameClass, start, end, day, nameTeacher, room);
                data.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi getAllSchedules: " + e.getMessage());
        }

        System.out.println("Tìm thấy " + data.size() + " lịch học.");
        return data;
    }
// lấy thời khóa biểu theo id
    public Schedules getSchedulesById(String id) {
        Schedules s = new Schedules();

        try {
            String strSQL = "SELECT *FROM  schedule Where id = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            rs = stm.executeQuery();
            while (rs.next()) {
                String sid = String.valueOf(rs.getInt(1));
                String nameClass = rs.getString(2);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String day = rs.getString(6);
                String nameTeacher = rs.getString(3);
                String room = rs.getString(7);
                s = new Schedules(sid, nameClass, start, end, day, nameTeacher, room);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi getAllSchedules: " + e.getMessage());
        }
        return s;
    }
// xóa theo id 
    public void delete(String id) {

        try {
            String strSQL = "delete from schedule where id = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, id);
            stm.execute();
        } catch (Exception e) {
            System.out.println("delete: " + e.getMessage());
        }

    }
// lấy danh sách lớp _ để chọn 
    public ArrayList<Categories_class> getCategories_class() {
        ArrayList<Categories_class> data1 = new ArrayList<Categories_class>();
        try {
            String strSQL = "SELECT id, name FROM  Class;";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String id_class = String.valueOf(rs.getInt(1));
                String name_class = rs.getString(2);
                Categories_class s = new Categories_class(id_class, name_class);
                data1.add(s);
            }
        } catch (Exception e) {
            System.out.println("getCategories : " + e.getMessage());
        }
        return data1;
    }
// lấy danh sách tên môn  _ để chọn 
    public ArrayList<CategoriesCourse> getCategoriesCourse() {
        ArrayList<CategoriesCourse> data2 = new ArrayList<CategoriesCourse>();
        try {
            String strSQL = "SELECT id, name FROM  Course";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String IDCourse = String.valueOf(rs.getInt(1));
                String nameCourse = rs.getString(2);
                CategoriesCourse s = new CategoriesCourse(IDCourse, nameCourse);
                data2.add(s);
            }
        } catch (Exception e) {
            System.out.println("getCategories : " + e.getMessage());
        }
        return data2;
    }
// lấy  danh sach tên giáo viên _ để chọn 
    public ArrayList<CategoriesTeacher> getCategoriesTeacher() {
        ArrayList<CategoriesTeacher> data3 = new ArrayList<CategoriesTeacher>();
        try {
            String strSQL = "select id, full_name from Teacher;";
            stm = connection.prepareStatement(strSQL);
            rs = stm.executeQuery();
            while (rs.next()) {
                String IDTeacher = String.valueOf(rs.getInt(1));
                String nameTeacher = rs.getString(2);
                CategoriesTeacher s = new CategoriesTeacher(IDTeacher, nameTeacher);
                data3.add(s);
            }
        } catch (Exception e) {
            System.out.println("getCategories : " + e.getMessage());
        }
        return data3;
    }
// sửathời khóa biểu
    public void update(Schedules s) {
        try {
            String strSQL = "Update schedule set id_class=?, start_time=?,end_time=?,day=?,id_teacher=?, room=?  where id = ?";
            stm = connection.prepareStatement(strSQL);

            stm.setString(1, s.getNameClass());
            stm.setString(2, s.getStartTime());
            stm.setString(3, s.getEndTime());
            stm.setString(4, s.getDay());
            stm.setString(5, s.getTeacher());
            stm.setString(6, s.getRoom());
            stm.setString(7, s.getId());

            stm.executeUpdate();
        } catch (Exception e) {
            System.out.println("update: " + e.getMessage());
        }
    }
// tạo thời khóa biêur
    public void add(Schedules s) {
        try {
            String sql = "INSERT INTO schedule(id_class, start_time, end_time, day, id_teacher, room) VALUES (?, ?, ?, ?, ?, ?)";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();

            cal.setTime(sdf.parse(s.getDay()));

            for (int i = 0; i < 10; i++) {
                stm = connection.prepareStatement(sql);
                stm.setString(1, s.getNameClass());
                stm.setString(2, s.getStartTime());
                stm.setString(3, s.getEndTime());
                String newDay = sdf.format(cal.getTime());
                stm.setString(4, newDay);
                stm.setString(5, s.getTeacher());
                stm.setString(6, s.getRoom());

                stm.execute();
                cal.add(Calendar.DAY_OF_MONTH, 7);
            }
        } catch (Exception e) {
            System.out.println("add: " + e.getMessage());
        }
    }
// lọc thời gian 
    public ArrayList<Schedules> filterSchedulesByDate(String date) {
        ArrayList<Schedules> data = new ArrayList<>();
        try {
            String strSQL = "SELECT s.id, s.id_class, s.id_teacher, s.start_time, s.end_time, s.day, s.room, c.name AS class_name, t.full_name AS teacher_name "
                    + "FROM schedule AS s "
                    + "JOIN Class AS c ON s.id_class = c.id "
                    + "JOIN Teacher AS t ON s.id_teacher = t.id "
                    + "WHERE s.day = ?";
            stm = connection.prepareStatement(strSQL);
            stm.setString(1, date);
            rs = stm.executeQuery();

            while (rs.next()) {
                String id = String.valueOf(rs.getInt("id"));
                String nameClass = rs.getString("class_name");
                String start = rs.getString("start_time");
                String end = rs.getString("end_time");
                String day = rs.getString("day");
                String nameTeacher = rs.getString("teacher_name");
                String room = rs.getString("room");
                Schedules s = new Schedules(id, nameClass, start, end, day, nameTeacher, room);
                data.add(s);
            }
            System.out.println("Lọc date " + date + ": Tìm thấy " + data.size() + " lịch học.");
        } catch (SQLException e) {
            System.out.println("Lỗi filterSchedulesByDate: " + e.getMessage());
        }
        return data;
    }
// check trùng ngày hay k 
    public boolean isScheduleExist(Schedules s, boolean isUpdate) {
        try {
            String strSQL = "SELECT COUNT(*) FROM schedule WHERE id_class = ? AND start_time = ? AND end_time = ? AND day = ? AND id_teacher = ? AND room = ?";
            if (isUpdate) {
                strSQL += " AND id != ?";
            }

            stm = connection.prepareStatement(strSQL);
            stm.setString(1, s.getNameClass());
            stm.setString(2, s.getStartTime());
            stm.setString(3, s.getEndTime());
            stm.setString(4, s.getDay());
            stm.setString(5, s.getTeacher());
            stm.setString(6, s.getRoom());
            if (isUpdate) {
                stm.setString(7, s.getId());
            }

            rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu tìm thấy lịch trùng
            }
        } catch (SQLException e) {
            System.out.println("Lỗi isScheduleExist: " + e.getMessage());
        }
        return false;
    }
// in danh sách lớp có lịch học 
        public List<Categories_class> getClassesHaveSchedule() {
            List<Categories_class> list = new ArrayList<>();
            String sql = "SELECT DISTINCT c.id, c.name "
                    + "FROM schedule s JOIN Class c ON s.id_class = c.id";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Categories_class(rs.getString("id"), rs.getString("name")));
                }
            } catch (Exception e) {
                System.out.println("getClassesHaveSchedule: " + e.getMessage());
            }
            return list;
        }
// tìm kiếm 
        public List<Categories_class> searchClass(String keyword) {
            List<Categories_class> list = new ArrayList<>();
            String sql = "SELECT DISTINCT c.id, c.name "
                    + "FROM schedule s JOIN Class c ON s.id_class = c.id "
                    + "WHERE c.name LIKE ?";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, "%" + keyword + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Categories_class(rs.getString("id"), rs.getString("name")));
                }
            } catch (Exception e) {
                System.out.println("searchClassesWithSchedule: " + e.getMessage());
            }
            return list;
        }
// xóa toàn bộ lịch 
        public void deleteScheduleByClassId(String classId) {
            try {
                String sql = "DELETE FROM schedule WHERE id_class = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, classId);
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("deleteScheduleByClassId: " + e.getMessage());
            }
        }
//lấy lịch theo id lớp
        public ArrayList<Schedules> getSchedulesByClassId(String classId) {
            ArrayList<Schedules> data = new ArrayList<>();
            try {
                String strSQL = "SELECT s.*, c.name AS class_name, t.full_name AS teacher_name "
                        + "FROM schedule s "
                        + "JOIN Class c ON s.id_class = c.id "
                        + "JOIN Teacher t ON s.id_teacher = t.id "
                        + "WHERE s.id_class = ?";
                stm = connection.prepareStatement(strSQL);
                stm.setString(1, classId);
                rs = stm.executeQuery();

                while (rs.next()) {
                    String id = String.valueOf(rs.getInt("id"));
                    String nameClass = rs.getString("class_name");
                    String start = rs.getString("start_time");
                    String end = rs.getString("end_time");
                    String day = rs.getString("day");
                    String nameTeacher = rs.getString("teacher_name");
                    String room = rs.getString("room");
                    Schedules s = new Schedules(id, nameClass, start, end, day, nameTeacher, room);
                    data.add(s);
                }
            } catch (Exception e) {
                System.out.println("getSchedulesByClassId: " + e.getMessage());
            }
            return data;
        }

        // Lấy danh sách lịch học theo id_class
        public ArrayList<Schedules> getScheduleByClassId(String classId) {
            ArrayList<Schedules> list = new ArrayList<>();
            try {
                String sql = "SELECT s.id, s.id_class, s.id_teacher, s.start_time, s.end_time, s.day, s.room, "
                        + "c.name AS class_name, t.full_name AS teacher_name "
                        + "FROM schedule s "
                        + "JOIN Class c ON s.id_class = c.id "
                        + "JOIN Teacher t ON s.id_teacher = t.id "
                        + "WHERE s.id_class = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, classId);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Schedules s = new Schedules(
                            rs.getString("id"),
                            rs.getString("class_name"),
                            rs.getString("start_time"),
                            rs.getString("end_time"),
                            rs.getString("day"),
                            rs.getString("teacher_name"),
                            rs.getString("room")
                    );
                    list.add(s);
                }
            } catch (Exception e) {
                System.out.println("getScheduleByClassId: " + e.getMessage());
            }
            return list;
        }

    // Lấy lịch học theo lớp và ngày
        public ArrayList<Schedules> getSchedulesByClassIdAndDate(String classId, String date) {
            ArrayList<Schedules> list = new ArrayList<>();
            try {
                String sql = "SELECT s.id, s.id_class, s.id_teacher, s.start_time, s.end_time, s.day, s.room, "
                        + "c.name AS class_name, t.full_name AS teacher_name "
                        + "FROM schedule s "
                        + "JOIN Class c ON s.id_class = c.id "
                        + "JOIN Teacher t ON s.id_teacher = t.id "
                        + "WHERE s.id_class = ? AND s.day = ?";
                stm = connection.prepareStatement(sql);
                stm.setString(1, classId);
                stm.setString(2, date);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Schedules s = new Schedules(
                            rs.getString("id"),
                            rs.getString("class_name"),
                            rs.getString("start_time"),
                            rs.getString("end_time"),
                            rs.getString("day"),
                            rs.getString("teacher_name"),
                            rs.getString("room")
                    );
                    list.add(s);
                }
            } catch (Exception e) {
                System.out.println("getSchedulesByClassIdAndDate: " + e.getMessage());
            }
            return list;
        }

        public void deleteScheduleById(String id) {
            try {
                String sql = "DELETE FROM schedule WHERE id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, id);
                ps.executeUpdate();
                ps.close();
            } catch (Exception e) {
                System.out.println("deleteScheduleById: " + e.getMessage());
            }
        }
//tìm theo id và từ khóa
public ArrayList<Schedules> searchScheduleByClassIdAndKeyword(String classId, String keyword) {
    ArrayList<Schedules> list = new ArrayList<>();
    try {
        String sql = "SELECT s.*, t.full_name FROM schedule s " +
                     "JOIN teacher t ON s.id_teacher = t.id " +
                     "WHERE s.id_class = ? AND " +
                     "(t.full_name LIKE ? OR s.room LIKE ? OR s.day LIKE ?)";
        PreparedStatement stm = connection.prepareStatement(sql);
        stm.setString(1, classId);
        String key = "%" + keyword + "%";
        stm.setString(2, key);
        stm.setString(3, key);
        stm.setString(4, key);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            list.add(new Schedules(
                rs.getString("id"),
                rs.getString("id_class"),
                rs.getString("start_time"),
                rs.getString("end_time"),
                rs.getString("day"),
                rs.getString("full_name"), // lấy tên giáo viên thay vì ID
                rs.getString("room")
            ));
        }
    } catch (Exception e) {
        System.out.println("searchScheduleByClassIdAndKeyword: " + e.getMessage());
    }
    return list;
}
// lọc tuần tháng năm
public ArrayList<Schedules> getSchedulesByTime(String classId, String startDate, String endDate) {
        ArrayList<Schedules> list = new ArrayList<>();
        try {
            String sql = "SELECT s.id, s.id_class, s.id_teacher, s.start_time, s.end_time, s.day, s.room, "
                       + "c.name AS class_name, t.full_name AS teacher_name "
                       + "FROM schedule s "
                       + "JOIN Class c ON s.id_class = c.id "
                       + "JOIN Teacher t ON s.id_teacher = t.id "
                       + "WHERE s.id_class = ? AND s.day BETWEEN ? AND ? "
                       + "ORDER BY s.day, s.start_time";
            stm = connection.prepareStatement(sql);
            stm.setString(1, classId);
            stm.setString(2, startDate);
            stm.setString(3, endDate);
            rs = stm.executeQuery();
            while (rs.next()) {
                Schedules s = new Schedules(
                    rs.getString("id"),
                    rs.getString("class_name"),
                    rs.getString("start_time"),
                    rs.getString("end_time"),
                    rs.getString("day"),
                    rs.getString("teacher_name"),
                    rs.getString("room")
                );
                list.add(s);
            }
        } catch (Exception e) {
            System.out.println("getSchedulesByClassIdAndRange: " + e.getMessage());
        }
        return list;
    }
    }