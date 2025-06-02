/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Admin
 */
public class ScheduleWeek {
     private String startDate;
    private String endDate;
    private String displayStartDate;
    private String displayEndDate;
    private int weekNumber;

    public ScheduleWeek() {
    }

    public ScheduleWeek(String startDate, String endDate, String displayStartDate, String displayEndDate, int weekNumber) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.displayStartDate = displayStartDate;
        this.displayEndDate = displayEndDate;
        this.weekNumber = weekNumber;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDisplayStartDate() {
        return displayStartDate;
    }

    public void setDisplayStartDate(String displayStartDate) {
        this.displayStartDate = displayStartDate;
    }

    public String getDisplayEndDate() {
        return displayEndDate;
    }

    public void setDisplayEndDate(String displayEndDate) {
        this.displayEndDate = displayEndDate;
    }

    public int getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.weekNumber = weekNumber;
    }
    
    
}