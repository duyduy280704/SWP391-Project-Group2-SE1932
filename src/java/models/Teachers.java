package models;

public class Teachers {

    String id, name, email, password, birthdate, gender, exp, pic, role,idtypecourse,yearofcourse,phone;

    public Teachers() {
    }

    public Teachers(String id, String name, String email, String password, String birthdate, String gender, String exp, String pic, String role, String idtypecourse, String yearofcourse, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.exp = exp;
        this.pic = pic;
        this.role = role;
        this.idtypecourse = idtypecourse;
        this.yearofcourse = yearofcourse;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getIdtypecourse() {
        return idtypecourse;
    }

    public void setIdtypecourse(String idtypecourse) {
        this.idtypecourse = idtypecourse;
    }

    public String getYearofcourse() {
        return yearofcourse;
    }

    public void setYearofcourse(String yearofcourse) {
        this.yearofcourse = yearofcourse;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    
}

   