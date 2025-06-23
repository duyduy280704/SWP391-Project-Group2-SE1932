// Thủy_  điểm danh
package models;

public class StudentAttendance {

    private Students student;
    private String status;
    private String reason;

    public StudentAttendance() {
    }

    public StudentAttendance(Students student, String status, String reason) {
        this.student = student;
        this.status = status;
        this.reason = reason;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}