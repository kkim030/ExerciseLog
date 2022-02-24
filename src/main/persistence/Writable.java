package persistence;

import org.json.JSONObject;

public interface Writable {
    //EFFECT: returns this as JSON Object
    //REFERENCE: JsonSerializationDemo
    JSONObject toJson();
}
