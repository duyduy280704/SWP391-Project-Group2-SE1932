
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Thuy
 */

public class Schedules {

    private String id;
    private String nameClass; 
    private String startTime;
    private String endTime;
    private String day;
    private String teacher;
    private String room;
    private String id_class;

    public Schedules() {
    }

    public Schedules(String id, String nameClass, String startTime, String endTime, String day, String teacher, String room) {
        this.id = id;
        this.nameClass = nameClass;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.teacher = teacher;
        this.room = room;
    }
    
        public Schedules(String id, String nameClass, String startTime, String endTime, String day, String teacher, String room, String id_class) {
        this.id = id;
        this.nameClass = nameClass;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.teacher = teacher;
        this.room = room;
        this.id_class = id_class;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getDate(){
        try {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.parse(this.day);
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
    }

    public String getId_class() {
        return id_class;
    }

    public void setId_class(String id_class) {
        this.id_class = id_class;
    }

    
    
}
