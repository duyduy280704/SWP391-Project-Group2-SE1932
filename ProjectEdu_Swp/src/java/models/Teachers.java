/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Quang
 */
public class Teachers {
    String id, name, account, password, exp, email, sdt, pic, address, role;

    public Teachers() {
    }

    public Teachers(String id, String name, String account, String password, String exp, String email, String sdt, String pic, String address, String role) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.exp = exp;
        this.email = email;
        this.sdt = sdt;
        this.pic = pic;
        this.address = address;
        this.role = role;
    }

    public Teachers(String id, String name, String account, String password, String exp, String email, String sdt, String pic, String address) {
        this.id = id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.exp = exp;
        this.email = email;
        this.sdt = sdt;
        this.pic = pic;
        this.address = address;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
    
    
}
