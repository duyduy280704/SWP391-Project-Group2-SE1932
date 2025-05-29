package models;
//Huyền-adminstaff
public class AdminStaffs {

    private String id;
    private String fullName;
    private String email;
    private String birthDate;
    private String gender;
    private String password;
    private String roleId;

    public AdminStaffs(String id, String fullName, String email, String birthDate, String gender, String password, String roleId) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.roleId = roleId;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    } // Sửa thành getRoleId

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}