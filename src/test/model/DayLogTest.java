package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayLogTest {

    private DayLog dayLog1;
    private DayLog dayLog2;
    private DayLog dayLog3;
    private DayLog dayLog4;

    @BeforeEach
    public void setUp() {
        dayLog1 = new DayLog(ExerciseType.PUSH, 1, 3, 2022, "new record 198ibs leg press", 3);
        dayLog2 = new DayLog(ExerciseType.PULL, 2, 10, 2021, "Very tired today - weak performance", 99);
        dayLog3 = new DayLog(ExerciseType.GLUTES, 2, 10, 2021, "Very tired today - weak performance", 4);
        dayLog4 = new DayLog(ExerciseType.LEG, 21, 12, 2021, "Best workout ever", 19);
    }

    @Test
    public void testConstructor() {
        assertEquals(ExerciseType.PUSH, dayLog1.getType());
        assertEquals(2, dayLog2.getDay());
        assertEquals(10, dayLog3.getMonth());
        assertEquals(99, dayLog2.getLogNumber());
        assertEquals("Very tired today - weak performance", dayLog3.getNotes());
        assertEquals(2021, dayLog4.getYear());
    }

    @Test
    public void testPrintDate() {
        assertEquals("3/1/2022", dayLog1.printDate());
        assertEquals("10/2/2021", dayLog2.printDate());
        assertEquals("10/2/2021", dayLog3.printDate());
        assertEquals("12/21/2021", dayLog4.printDate());
    }
}
