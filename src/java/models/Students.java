package models;

/**
 *
 * @author Quang
 */
public class Students {
    String id, name, email, password, birthdate, gender, address, role, phone;
    byte[] pic;

    public Students() {
    }

    public Students(String id, String name, String email, String password, String birthdate, String gender, String address, String role, String phone, byte[] pic) { // Thay đổi từ String thành byte[]
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.role = role;
        this.phone = phone;
        this.pic = pic; // Thay đổi từ String thành byte[]
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public byte[] getPic() { 
        return pic;
    }

    public void setPic(byte[] pic) { 
        this.pic = pic;
    }
}