/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dwight
 */
public class Regisition {

    private int id;
    private String studentName;
    private String courseName;
    private String status;
    private int courseId;
    private String note;
    private String studentId;

    public Regisition() {
    }

    public Regisition(int id, String studentName, String courseName, String status, String note) {
        this.id = id;
        this.studentName = studentName;
        this.courseName = courseName;
        this.status = status;
        this.note = note;
    }

    public Regisition(int id, String studentName, String courseName, String status, int courseId, String note) {
        this.id = id;
        this.studentName = studentName;
        this.courseName = courseName;
        this.status = status;
        this.courseId = courseId;
        this.note = note;
    }

    public Regisition(String studentId, int courseId, String note, String status) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.note = note;
        this.status = status;
    }

    // Getter - Setter
    public int getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}