package model;

import java.util.concurrent.ThreadLocalRandom;

public class DayLog {

    private String notes;
    private int day;
    private int month;
    private int year;
    private ExerciseType type;
    private int logNumber;
    private Profile mainprofile;

    public DayLog(ExerciseType type, int day, int month, int year, String notes, int logNumber) {
        this.type = type;
        this.day = day;
        this.month = month;
        this.year = year;
        this.notes = notes;
        this.logNumber = logNumber;
    }

    // getters
    public String getNotes() {
        return notes;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public ExerciseType getType() {
        return type;
    }

    public int getLogNumber() {
        return logNumber;
    }

    //EFFECT: returns a string representation of date
    public String printDate() {
        return getMonth() + "/" + getDay() + "/" + getYear();
    }







}








