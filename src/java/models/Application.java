/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Quang
 */
public class Application {

    String id, name, applicationType, content, date, status, className;

    public Application() {
    }

    public Application(String id, String name, String applicationType, String content, String date, String status) {
        this.id = id;
        this.name = name;
        this.applicationType = applicationType;
        this.content = content;
        this.date = date;
        this.status = status;
    }

    public Application(String id, String name, String applicationType, String content, String date, String status, String className) {
        this.id = id;
        this.name = name;
        this.applicationType = applicationType;
        this.content = content;
        this.date = date;
        this.status = status;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    
    

}