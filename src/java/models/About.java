/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dwight
 */
//Dương_Homepage
public class About {
    private int id;
    private String title;
    private String content;
    private String image;
    private String updatedAt;

    public About() {
    }

    public About(int id, String title, String content, String image, String updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image = image;
        this.updatedAt = updatedAt;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}

