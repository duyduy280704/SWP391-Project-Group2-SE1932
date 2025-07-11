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
                e.setImg(rs.getBytes("img")); // Image stored as byte[]
                e.setDate(rs.getString("date"));
                e.setCourseid(rs.getString("id_course"));
                list.add(e);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

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

    public static void main(String[] args) {
        EventDAO ev = new EventDAO();
        List<Event> events = ev.getRecentEvents(3);
        for (Event e : events) {
            System.out.println(e.getName());
        }
    }
}