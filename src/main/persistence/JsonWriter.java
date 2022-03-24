package persistence;

import model.DayLog;
import model.Profile;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;



// EFFECTS: REPRESENTS a writer that writes JSON representation of exerciseLogs
// REFERENCE: JsonSerializationDemo
public class JsonWriter {
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write destination file
    // REFERENCE: JsonSerializationDemo
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer, throws FileNotFoundException if destination not found
    //         to be opened for writing
    // REFERENCE: JsonSerializationDemo
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    // REFERENCE: JsonSerializationDemo
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    //REFERENCE: JsonSerializationDemo
    private void saveToFile(String json) {
        writer.print(json);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Profile to file
    // REFERENCE: JsonSerializationDemo
    public void write(Profile myProfile) {
        JSONObject json = myProfile.toJson();
        saveToFile(json.toString());
    }



}
