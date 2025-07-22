/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dwight
 */
public class RefundInfo {

    private int studentId;
    private int courseId;
    private String regisitionStatus;
    private double originalPrice;
    private int discountPercent;
    private double refundAmount;
    private String method;
    private String orderCode;
    private String paymentDate;
    private int paymentId;
    private String paymentStatus;

    public RefundInfo() {
    }

    public RefundInfo(int studentId, int courseId, String regisitionStatus, double originalPrice, int discountPercent, double refundAmount, String method, String orderCode, String paymentDate, int paymentId, String paymentStatus) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.regisitionStatus = regisitionStatus;
        this.originalPrice = originalPrice;
        this.discountPercent = discountPercent;
        this.refundAmount = refundAmount;
        this.method = method;
        this.orderCode = orderCode;
        this.paymentDate = paymentDate;
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
    }

    

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getRegisitionStatus() {
        return regisitionStatus;
    }

    public void setRegisitionStatus(String regisitionStatus) {
        this.regisitionStatus = regisitionStatus;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}