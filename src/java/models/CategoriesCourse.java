/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Admin
 */
public class CategoriesCourse {
    String IDCourse, nameCourse;

    public CategoriesCourse() {
    }

    public CategoriesCourse(String IDCourse, String nameCourse) {
        this.IDCourse = IDCourse;
        this.nameCourse = nameCourse;
    }

    public String getIDCourse() {
        return IDCourse;
    }

    public void setIDCourse(String IDCourse) {
        this.IDCourse = IDCourse;
    }

    public String getNameCourse() {
        return nameCourse;
    }

    public void setNameCourse(String nameCourse) {
        this.nameCourse = nameCourse;
    }

    
    
}