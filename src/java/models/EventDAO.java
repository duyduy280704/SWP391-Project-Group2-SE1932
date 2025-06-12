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

    public List<Event> getUpcomingEvents() {
        List<Event> list = new ArrayList<>();
        String sql = "SELECT id, name, date FROM event WHERE date >= GETDATE() ORDER BY date ASC";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
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
}
