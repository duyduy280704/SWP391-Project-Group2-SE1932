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
    String id, name, type, description, fee, level, number;
    
    byte[] image;

    public Courses() {
    }
     public Courses(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Courses(String id, String name, String type, String description, String fee, byte[] image, String level) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.fee = fee;
        this.image = image;
        this.level = level;
        
    }
    public Courses(String id, String name, String type, String description, String fee, byte[] image, String level, String number) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.fee = fee;
        this.image = image;
        this.level = level;
        this.number = number;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

   
    
    
}
