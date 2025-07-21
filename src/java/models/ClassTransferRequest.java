package models;

import java.util.Date;

public class ClassTransferRequest {

    private Students student;
    private Categories_class fromClass;
    private Categories_class toClass;
    private Date transferDate;
    private int transferCount;

    public ClassTransferRequest() {
    }

    public ClassTransferRequest(Students student, Categories_class fromClass, Categories_class toClass, Date transferDate, int transferCount) {
        this.student = student;
        this.fromClass = fromClass;
        this.toClass = toClass;
        this.transferDate = transferDate;
        this.transferCount = transferCount;
    }

    public Students getStudent() {
        return student;
    }

    public void setStudent(Students student) {
        this.student = student;
    }

    public Categories_class getFromClass() {
        return fromClass;
    }

    public void setFromClass(Categories_class fromClass) {
        this.fromClass = fromClass;
    }

    public Categories_class getToClass() {
        return toClass;
    }

    public void setToClass(Categories_class toClass) {
        this.toClass = toClass;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public int getTransferCount() {
        return transferCount;
    }

    public void setTransferCount(int transferCount) {
        this.transferCount = transferCount;
    }
    public String getFromClassId() {
    return fromClass != null ? fromClass.getId_class() : null;
}

public String getToClassId() {
    return toClass != null ? toClass.getId_class() : null;
}

}
