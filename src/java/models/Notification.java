/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Dwight
 */
public class Notification {

    private String id;
    private String idUser;
    private String message;
    private String date;

    public Notification() {
    }

    public Notification(String id, String idUser, String message, String date) {
        this.id = id;
        this.idUser = idUser;
        this.message = message;
        this.date = date;
    }
    public Notification( String idUser, String message, String date) {
        this.idUser = idUser;
        this.message = message;
        this.date = date;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
