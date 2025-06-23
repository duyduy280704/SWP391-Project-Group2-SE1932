package models;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScheduleTeacher {

    private String id;
    private String day;
    private String classId;
    private String nameClass;
    private String startTime;
    private String endTime;
    private String room;
    private String specificDay;
    private String dayVN;
    private boolean attendanceTaken;
    public ScheduleTeacher() {
        this.dayVN = "Không xác định";
    }

    public ScheduleTeacher(String id, String day, String classId, String nameClass, String startTime, String endTime, String room) {
        this.id = id;
        this.day = day;
        this.classId = classId;
        this.nameClass = nameClass;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
        computeDayOfWeek();
    }

    public void computeDayOfWeek() {
        try {
            if (day != null && day.matches("\\d{4}-\\d{2}-\\d{2}")) {
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
                    default:
                        this.dayVN = "Không xác định";
                }
            } else {
                this.specificDay = "UNKNOWN";
                this.dayVN = "Không xác định";
            }
        } catch (Exception e) {
            this.specificDay = "UNKNOWN";
            this.dayVN = "Không xác định";
            System.out.println("Error in computeDayOfWeek: " + e.getMessage());
        }
    }

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
        computeDayOfWeek();
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSpecificDay() {
        return specificDay;
    }

    public void setSpecificDay(String specificDay) {
        this.specificDay = specificDay;
    }

    public String getDayVN() {
        return dayVN;
    }

    public void setDayVN(String dayVN) {
        this.dayVN = dayVN;
    }

    public boolean isAttendanceTaken() {
        return attendanceTaken;
    }

    public void setAttendanceTaken(boolean attendanceTaken) {
        this.attendanceTaken = attendanceTaken;
    }
    
    
}
