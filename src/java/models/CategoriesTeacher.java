/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Admin
 */
public class CategoriesTeacher {
    String IDTeacher , nameTeacher;

    public CategoriesTeacher() {
    }

    public CategoriesTeacher(String IDTeacher, String nameTeacher) {
        this.IDTeacher = IDTeacher;
        this.nameTeacher = nameTeacher;
    }

    public String getIDTeacher() {
        return IDTeacher;
    }

    public void setIDTeacher(String IDTeacher) {
        this.IDTeacher = IDTeacher;
    }

    public String getNameTeacher() {
        return nameTeacher;
    }

    public void setNameTeacher(String nameTeacher) {
        this.nameTeacher = nameTeacher;
    }
    
    
}