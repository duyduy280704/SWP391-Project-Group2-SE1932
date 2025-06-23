package models;

public class StudentAttendance {
    private Students student;
    private String status;

    public StudentAttendance() {}

    public StudentAttendance(Students student, String status) {
        this.student = student;
        this.status = status;
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

  
}
