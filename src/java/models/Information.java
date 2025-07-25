/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dương
 */
//Dương_Homepage
public class Information {
    private String address, phone, email;
    private String facebookLink, instagramLink, youtubeLink;
    private String copyright;
    private String policyLink, termsLink, about;

    public Information(String address, String phone, String email, String facebookLink, String instagramLink, String youtubeLink, String copyright, String policyLink, String termsLink, String about) {
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.facebookLink = facebookLink;
        this.instagramLink = instagramLink;
        this.youtubeLink = youtubeLink;
        this.copyright = copyright;
        this.policyLink = policyLink;
        this.termsLink = termsLink;
        this.about = about;
    }

    public Information() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getYoutubeLink() {
        return youtubeLink;
    }

    public void setYoutubeLink(String youtubeLink) {
        this.youtubeLink = youtubeLink;
    }


    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getPolicyLink() {
        return policyLink;
    }

    public void setPolicyLink(String policyLink) {
        this.policyLink = policyLink;
    }

    public String getTermsLink() {
        return termsLink;
    }

    public void setTermsLink(String termsLink) {
        this.termsLink = termsLink;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
