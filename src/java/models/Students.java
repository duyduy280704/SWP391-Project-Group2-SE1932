/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Quang
 */
public class Students {
    String id, name, email, password, brithdate, gender, address, role;

    public Students() {
    }

    public Students(String id, String name, String email, String password, String brithdate, String gender, String address, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.brithdate = brithdate;
        this.gender = gender;
        this.address = address;
        this.role = role;
    }

    public Students(String id, String name, String email, String password, String brithdate, String gender, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.brithdate = brithdate;
        this.gender = gender;
        this.address = address;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBrithdate() {
        return brithdate;
    }

    public void setBrithdate(String brithdate) {
        this.brithdate = brithdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
