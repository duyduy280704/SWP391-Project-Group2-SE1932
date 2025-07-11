/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Admin
 */
public class Categories_class {
    String id_class, name_class;
     private String courseName;

    public Categories_class() {
    }

    public Categories_class(String id_class, String name_class) {
        this.id_class = id_class;
        this.name_class = name_class;
    }

    public Categories_class(String id_class, String name_class, String courseName) {
        this.id_class = id_class;
        this.name_class = name_class;
        this.courseName = courseName;
    }
    
    
    public String getId_class() {
        return id_class;
    }

    public void setId_class(String id_class) {
        this.id_class = id_class;
    }

    public String getName_class() {
        return name_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    
    
}