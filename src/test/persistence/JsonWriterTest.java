package persistence;

import model.DayLog;
import model.ExerciseType;
import model.Profile;
import org.junit.Test;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    @Test
    public void testWriterInvalidFile() {
        try {
            Profile profile = new Profile("Kelly", "20", "130");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }
    @Test
    public void testWriterEmptyWorkroom() {
        try {
            Profile profile = new Profile("Kelly","20", "130" );
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(profile);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            profile = reader.read();
            assertEquals("Kelly", profile.getName());
            assertEquals("20", profile.getAge());
            assertEquals("130", profile.getWeight());
            assertEquals(0, profile.countDayLog());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testWriterGeneralWorkroom() {
        try {
            Profile profile = new Profile("Kelly", "90", "180");
            profile.addExerciseLog(new DayLog(ExerciseType.PUSH, 1, 3, 2022, "new record 198ibs leg press", 3));
            profile.addExerciseLog(new DayLog(ExerciseType.PULL, 2, 10, 2021, "Very tired today - weak performance", 99));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(profile);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            profile = reader.read();
            assertEquals("Kelly", profile.getName());
            assertEquals("90", profile.getAge());
            assertEquals("180", profile.getWeight());
            List<DayLog> dayLogs = profile.getExerciseLog();
            assertEquals(2, dayLogs.size());
            checkDayLog(ExerciseType.PUSH, 1, 3, 2022, "new record 198ibs leg press", 3, dayLogs.get(0));
            checkDayLog(ExerciseType.PULL, 2, 10, 2021, "Very tired today - weak performance", 99, dayLogs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }




}
