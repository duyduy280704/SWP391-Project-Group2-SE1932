/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dương
 */
public class Payment {
    private int id;
    private String idStudent;
    private int idCourse;
    private String status;
    private String date;
    private String orderCode;
    private String method;
    private Integer idSale; // có thể null
     
    
    public Payment(String idStudent, int idCourse, String status, String date, Integer idSale) {
        this.idStudent = idStudent;
        this.idCourse = idCourse;
        this.status = status;
        this.date = date;
        
        this.idSale = idSale;
    }
    
    public Payment(String idStudent, int idCourse, String status, String date, Integer idSale, String orderCode) {
    this.idStudent = idStudent;
    this.idCourse = idCourse;
    this.status = status;
    this.date = date;
    this.idSale = idSale;
    this.orderCode = orderCode;
}
    // Getters & setters

    public int getId() {
        return id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public Integer getIdSale() {
        return idSale;
    }

    public void setIdSale(Integer idSale) {
        this.idSale = idSale;
    }
    
}

