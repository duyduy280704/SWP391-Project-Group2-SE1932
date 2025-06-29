/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Quang
 */
public class Event {
    String id,name,content,date,courseid;
    byte[] img;

    public Event() {
    }

    public Event(String id, String name, String content, String date, String courseid, byte[] img) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.date = date;
        this.courseid = courseid;
        this.img = img;
    }
    
    public Event(String id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCourseid() {
        return courseid;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
