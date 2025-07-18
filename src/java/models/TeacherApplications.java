package models;

public class TeacherApplications {

    private int id;
    private String fullName, email, cvlink, status, birthDate, gender, expertise, idTypeCourse, yearOfExpertise, phone;
    private String typeCourseName;

    public TeacherApplications() {
    }

    public TeacherApplications(int id, String fullName, String email, String cvlink, String status, String birthDate, String gender, String expertise, String idTypeCourse, String yearOfExpertise, String phone) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.cvlink = cvlink;
        this.status = status;
        this.birthDate = birthDate;
        this.gender = gender;
        this.expertise = expertise;
        this.idTypeCourse = idTypeCourse;
        this.yearOfExpertise = yearOfExpertise;
        this.phone = phone;
    }

    public TeacherApplications(int id, String fullName, String email, String cvlink, String status, String birthDate, String gender, String expertise, String idTypeCourse, String yearOfExpertise, String phone, String typeCourseName) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.cvlink = cvlink;
        this.status = status;
        this.birthDate = birthDate;
        this.gender = gender;
        this.expertise = expertise;
        this.idTypeCourse = idTypeCourse;
        this.yearOfExpertise = yearOfExpertise;
        this.phone = phone;
        this.typeCourseName = typeCourseName;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCvlink() {
        return cvlink;
    }

    public void setCvlink(String cvlink) {
        this.cvlink = cvlink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getIdTypeCourse() {
        return idTypeCourse;
    }

    public void setIdTypeCourse(String idTypeCourse) {
        this.idTypeCourse = idTypeCourse;
    }

    public String getYearOfExpertise() {
        return yearOfExpertise;
    }

    public void setYearOfExpertise(String yearOfExpertise) {
        this.yearOfExpertise = yearOfExpertise;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTypeCourseName() {
        return typeCourseName;
    }

    public void setTypeCourseName(String typeCourseName) {
        this.typeCourseName = typeCourseName;
    }

    
}
