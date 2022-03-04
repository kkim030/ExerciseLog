package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.concurrent.ThreadLocalRandom;

// DayLog is each exercise diary logged under the profile
public class DayLog implements Writable {

    private String notes;
    private int day;
    private int month;
    private int year;
    private ExerciseType type;
    private int logNumber;

    // Constructor
    // EFFECTS: constructs DayLog class that records the exercise details
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

    @Override
    //EFFECT: puts the details of DayLog toJson
    //REFERENCE: JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("day", day);
        json.put("month", month);
        json.put("year", year);
        json.put("notes", notes);
        json.put("logNumber", logNumber);
        return json;
    }
}








