package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads dayLogs from JSON data stored in jar
public class JsonReader {
    private String source;

    // EFFECT: constructs a JSONReader
    //REFERENCE: JsonSerializationDemo
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads source file as string and returns it
    //REFERENCE: JsonSerializationDemo
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    //EFFECTS: reads Profile from file and returns it;
    //         throws IOException if an error occurs reading data from file
    //REFERENCE: JsonSerializationDemo
    public Profile read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseProfile(jsonObject);

    }

    // EFFECTS: parses profile from JSON objects and returns it
    // REFERENCE: JsonSerializationDemo
    private Profile parseProfile(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String age = jsonObject.getString("age");
        String weight = jsonObject.getString("weight");
        Profile profile = new Profile(name, age, weight);
        addDayLogs(profile, jsonObject);
        return profile;
    }

    // MODIFIES: Profile
    // EFFECTS: parses dayLogs from JSON object and adds them to Profile
    //REFERENCE: JsonSerializationDemo
    private void addDayLogs(Profile profile, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("exerciseLogs");
        for (Object json : jsonArray) {
            JSONObject nextLog = (JSONObject) json;
            addDayLog(profile, nextLog);
        }
    }

    // MODIFIES: Profile
    // EFFECTS: parses DayLog from JSON object and adds it to Profile
    private void addDayLog(Profile profile, JSONObject jsonObject) {
        ExerciseType type = ExerciseType.valueOf(jsonObject.getString("type"));
        int day = jsonObject.getInt("day");
        int month = jsonObject.getInt("month");
        int year = jsonObject.getInt("year");
        String notes = jsonObject.getString("notes");
        int logNumber = jsonObject.getInt("logNumber");
        DayLog newLog = new DayLog(type, day, month, year, notes, logNumber);
        profile.addExerciseLog(newLog);

    }
}
