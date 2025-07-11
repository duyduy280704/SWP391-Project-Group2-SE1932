package models;
/**
 *
 * đổi lớp
 */
import java.util.Date;

public class ClassTransferRequest {

    private int id;
    private String studentId;
    private String fromClassId;
    private String toClassId;
    private String reason;
    private String status;
    private Date requestDate;
    private Date responseDate;

    private String fromClassName;
    private String toClassName;
    private String staffNote;
    private String studentName;

    public ClassTransferRequest() {
    }

    public ClassTransferRequest(int id, String studentId, String fromClassId, String toClassId,
            String reason, String status, Date requestDate, Date responseDate, String staffNote) {
        this.id = id;
        this.studentId = studentId;
        this.fromClassId = fromClassId;
        this.toClassId = toClassId;
        this.reason = reason;
        this.status = status;
        this.requestDate = requestDate;
        this.responseDate = responseDate;
        this.staffNote = staffNote;
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

    public String getFromClassId() {
        return fromClassId;
    }

    public void setFromClassId(String fromClassId) {
        this.fromClassId = fromClassId;
    }

    public String getToClassId() {
        return toClassId;
    }

    public void setToClassId(String toClassId) {
        this.toClassId = toClassId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public String getFromClassName() {
        return fromClassName;
    }

    public void setFromClassName(String fromClassName) {
        this.fromClassName = fromClassName;
    }

    public String getToClassName() {
        return toClassName;
    }

    public void setToClassName(String toClassName) {
        this.toClassName = toClassName;
    }

    public String getStaffNote() {
        return staffNote;
    }

    public void setStaffNote(String staffNote) {
        this.staffNote = staffNote;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    
}