package persistence;

import model.DayLog;
import model.ExerciseType;
import model.Profile;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Profile profile = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyProfile.json");
        try {
            Profile profile = reader.read();
            assertEquals("Kelly", profile.getName());
            assertEquals("9", profile.getAge());
            assertEquals("130", profile.getWeight());
            assertEquals(0, profile.countDayLog());
        } catch (IOException e) {
            fail("unable to find exercise history :(");
        }
    }

    @Test
    public void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralProfile.json");
        try {
            Profile profile = reader.read();
            assertEquals("Kelly", profile.getName());
            assertEquals("9", profile.getAge());
            assertEquals("130", profile.getWeight());
            List<DayLog> dayLogs = profile.getExerciseLog();
            assertEquals(2, dayLogs.size());
            checkDayLog(ExerciseType.PUSH, 9, 8, 1994, "DSLFKN", 92, dayLogs.get(0));
            checkDayLog(ExerciseType.PULL, 80, 9, 2020, "HELLO WORLD", 90, dayLogs.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
