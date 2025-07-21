/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Quang
 */
public class SalaryTeacher {

    String id, teacher, offer_salary, number_of_sessions, bonus, penalty, amount, note, month;

    public SalaryTeacher() {
    }

    public SalaryTeacher(String id, String teacher, String offer_salary, String number_of_sessions, String bonus, String penalty, String amount, String note, String month) {
        this.id = id;
        this.teacher = teacher;
        this.offer_salary = offer_salary;
        this.number_of_sessions = number_of_sessions;
        this.bonus = bonus;
        this.penalty = penalty;
        this.amount = amount;
        this.note = note;
        this.month = month;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getOffer_salary() {
        return offer_salary;
    }

    public void setOffer_salary(String offer_salary) {
        this.offer_salary = offer_salary;
    }

    public String getNumber_of_sessions() {
        return number_of_sessions;
    }

    public void setNumber_of_sessions(String number_of_sessions) {
        this.number_of_sessions = number_of_sessions;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

}
