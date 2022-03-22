package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// Profile information for the person's personal information including exercise logs
public class Profile implements Writable {

    private String name;
    private String age;
    private String weight;
    private ArrayList<DayLog> exerciseLog;
    private DayLog daylog;

    //Constructor
    //EFFECT: constructs profile information
    public Profile(String name, String age, String weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        exerciseLog = new ArrayList<>();
    }

    // getters
    public String getName() {
        return this.name;
    }

    public String getAge() {
        return this.age;
    }

    public String getWeight() {
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

    public Profile getProfile() {
        return this;
    }

    @Override
    //EFFECT: returns name and dayLogs to JSONObject
    //REFERENCE: JsonSerializationDemo
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("age", age);
        json.put("weight", weight);
        json.put("exerciseLogs", dayLogsToJson());
        return json;
    }

    //EFFECT: return multiple dayLogs to JSONArray
    //REFERENCE: JsonSerializationDemo
    private JSONArray dayLogsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (DayLog log : exerciseLog) {
            jsonArray.put(log.toJson());
        }

        return jsonArray;
    }
}



