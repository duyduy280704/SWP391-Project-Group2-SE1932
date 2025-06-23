/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.sql.Date;

/**
 *
 * @author Dwight
 */
//Dương_Homepage
public class PreRegistration {

    private int id;
    private String full_name;
    private String email;
    private String phone;
    private String birth_date;
    private String gender;
    private String address;
    private String courseName;
    private int course_id;
    private String status;

    public PreRegistration() {
    }

    public PreRegistration(int id, String full_name, String email, String birth_date, String gender, String address, int course_id, String status) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.birth_date = birth_date;
        this.gender = gender;
        this.address = address;
        this.course_id = course_id;
        this.status = status;
    }

    public PreRegistration(String full_name, String email, String phone, String birth_date, String gender, String address, int course_id, String status) {

        this.full_name = full_name;
        this.email = email;
        this.phone = phone;
        this.birth_date = birth_date;
        this.gender = gender;
        this.address = address;
        this.course_id = course_id;
        this.status = status;
    }

    public PreRegistration(int id, String full_name, String email, String birth_date, String gender, String address, String courseName, String status) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.birth_date = birth_date;
        this.gender = gender;
        this.address = address;
        this.courseName = courseName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
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

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
