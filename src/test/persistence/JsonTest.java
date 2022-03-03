package persistence;

import model.DayLog;
import model.ExerciseType;
import model.Profile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkDayLog(ExerciseType type, int day, int month, int year, String notes, int logNumber, DayLog dayLog) {
        assertEquals(type, dayLog.getType());
        assertEquals(day, dayLog.getDay());
        assertEquals(month, dayLog.getMonth());
        assertEquals(year, dayLog.getYear());
        assertEquals(notes, dayLog.getNotes());
        assertEquals(logNumber, dayLog.getLogNumber());
    }
}
