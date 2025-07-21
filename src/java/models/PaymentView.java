package models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Dwight
 */
public class PaymentView {

    private String orderCode;
    private String date;
    private String status;
    private String courseName;
    private double courseFee;
    private double salePercent;
    private double finalAmount;
    private String nameStudent;
    private String idStudent;
    private String email;
    private String method;
    

    public PaymentView() {
    }

    public PaymentView(String orderCode, String date, String status, String courseName, double courseFee, double salePercent, double finalAmount) {
        this.orderCode = orderCode;
        this.date = date;
        this.status = status;
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.salePercent = salePercent;
        this.finalAmount = finalAmount;
    }

    public PaymentView(String orderCode, String date, String status, String courseName, double courseFee, double salePercent, double finalAmount, String nameStudent, String idStudent, String email) {
        this.orderCode = orderCode;
        this.date = date;
        this.status = status;
        this.courseName = courseName;
        this.courseFee = courseFee;
        this.salePercent = salePercent;
        this.finalAmount = finalAmount;
        this.nameStudent = nameStudent;
        this.idStudent = idStudent;
        this.email = email;
    }
    
    
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public double getCourseFee() {
        return courseFee;
    }

    public void setCourseFee(double courseFee) {
        this.courseFee = courseFee;
    }

    public double getSalePercent() {
        return salePercent;
    }

    public void setSalePercent(double salePercent) {
        this.salePercent = salePercent;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public String getNameStudent() {
        return nameStudent;
    }

    public void setNameStudent(String nameStudent) {
        this.nameStudent = nameStudent;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
