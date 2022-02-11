package model;

import java.util.ArrayList;
import java.util.List;

// Profile information for the person's personal information including exercise logs
public class Profile {

    private String name;
    private int age;
    private int weight;
    private ArrayList<DayLog> exerciseLog;
    private DayLog daylog;

    public Profile(String name, int age, int weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        exerciseLog = new ArrayList<>();
    }

    // getters
    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int getWeight() {
        return this.weight;
    }

    public List<DayLog> getExerciseLog() {
        return this.exerciseLog;
    }

    // MODIFIES: this
    // EFFECTS: consumes an exerciseLog (represented as a ExerciseType) object, and adds it to the
    // exercise log
    public void addExerciseLog(DayLog log) {
        this.exerciseLog.add(log);
    }

    // REQUIRES: exerciseLog must be a non-empty list
    // MODIFIES: this
    // EFFECTS: removes an exercise log with the logNumber from the list of exercise logs.
    public boolean removeLog(int logNumber) {
        for (DayLog dayLog : exerciseLog) {
            if (dayLog.getLogNumber() == logNumber) {
                exerciseLog.remove(dayLog);
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns the number of DayLog the profile has entered.
    public int countDayLog() {
        int count = 0;
        for (DayLog dayLog : exerciseLog) {
            count++;
        }
        return count;
    }
}



