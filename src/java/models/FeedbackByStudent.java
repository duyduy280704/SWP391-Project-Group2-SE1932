
// Thuy_ feedback của  học sinh 
package models;

public class FeedbackByStudent {

    private int id;
    private String studentId;
    private String courseId; 
    private String feedbackText;
    private String feedbackDate;
    private String studentName;
    private String className;

    public FeedbackByStudent() {
    }

    public FeedbackByStudent(int id, String studentId, String courseId, String feedbackText, String feedbackDate, String studentName, String className) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.feedbackText = feedbackText;
        this.feedbackDate = feedbackDate;
        this.studentName = studentName;
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public void setFeedbackText(String feedbackText) {
        this.feedbackText = feedbackText;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
