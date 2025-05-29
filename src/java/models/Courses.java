/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Quang
 */
public class Courses {
    String id, name, type, description, fee, picture;

    public Courses() {
    }

    public Courses(String id, String name, String type, String description, String fee, String picture) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.fee = fee;
        this.picture = picture;
    }
    public Courses(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public Courses(String name, String type, String description, String fee, String picture) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.fee = fee;
        this.picture = picture;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    
}
