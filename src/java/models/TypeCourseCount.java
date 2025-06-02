/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Quang
 */
public class TypeCourseCount {
    private String typeName;
    private int count;

    public TypeCourseCount(String typeName, int count) {
        this.typeName = typeName;
        this.count = count;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getCount() {
        return count;
    }
}