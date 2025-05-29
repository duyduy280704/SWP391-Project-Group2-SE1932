package models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleStudent {

    private String id;
    private String day;
    private String nameClass;
    private String startTime;
    private String endTime;
    private String room;
    private String specificDay;
    private String dayVN;

    public ScheduleStudent() {
    }

    public ScheduleStudent(String id, String day, String nameClass, String startTime, String endTime, String room) {
        this.id = id;
        this.day = day;
        this.nameClass = nameClass;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        computeDayOfWeek();
    }

    public void computeDayOfWeek() {
        try {
            LocalDate date = LocalDate.parse(day, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            this.specificDay = dayOfWeek.toString();
            switch (dayOfWeek) {
                case MONDAY:
                    this.dayVN = "Thứ 2";
                    break;
                case TUESDAY:
                    this.dayVN = "Thứ 3";
                    break;
                case WEDNESDAY:
                    this.dayVN = "Thứ 4";
                    break;
                case THURSDAY:
                    this.dayVN = "Thứ 5";
                    break;
                case FRIDAY:
                    this.dayVN = "Thứ 6";
                    break;
                case SATURDAY:
                    this.dayVN = "Thứ 7";
                    break;
                case SUNDAY:
                    this.dayVN = "Chủ nhật";
                    break;
            }
        } catch (Exception e) {
            this.specificDay = "UNKNOWN";
            this.dayVN = "Không xác định";
        }
    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getNameClass() {
        return nameClass;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getRoom() {
        return room;
    }

    public String getSpecificDay() {
        return specificDay;
    }

    public String getDayVN() {
        return dayVN;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDay(String day) {
        this.day = day;
        computeDayOfWeek();
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}