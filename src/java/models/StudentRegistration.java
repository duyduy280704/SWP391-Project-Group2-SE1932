/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Quang
 */
public class StudentRegistration {
    private String month;
    private int approvedCount;
    private int pendingCount;

    public StudentRegistration() {
    }

    public StudentRegistration(String month, int approvedCount, int pendingCount) {
        this.month = month;
        this.approvedCount = approvedCount;
        this.pendingCount = pendingCount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getApprovedCount() {
        return approvedCount;
    }

    public void setApprovedCount(int approvedCount) {
        this.approvedCount = approvedCount;
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }

    public void updateCount(String status, int count) {
        if ("Đã duyệt".equals(status)) {
            this.approvedCount += count;
        } else if ("Đang chờ".equals(status)) {
            this.pendingCount += count;
        }
    }
}
