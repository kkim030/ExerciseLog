package persistence;

import org.json.JSONObject;

// EFFECT: returns this as JSON Object
// REFERENCE: JsonSerializationDemo
public interface Writable {
    JSONObject toJson();
}
