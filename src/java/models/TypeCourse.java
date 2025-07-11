/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.List;

/**
 *
 * @author Dwight
 */
//Dương_Homepage
public class TypeCourse {

    private String id;
    private String name;
    private List<Courses> course;

    public TypeCourse() {
    }

    public TypeCourse(String id, String name, List<Courses> course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }

    public TypeCourse(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Courses> getCourse() {
        return course;
    }

    public void setCourse(List<Courses> course) {
        this.course = course;
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
}