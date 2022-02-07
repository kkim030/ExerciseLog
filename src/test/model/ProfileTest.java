package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    private Profile mainProfile;
    private DayLog log1;
    private DayLog log2;
    private DayLog log3;
    private DayLog log4;


    @BeforeEach
    public void setUp() {
        mainProfile = new Profile("Kelly Kim", 18, 120);
        //DayLog(ExerciseType type, int day, int month, int year, String notes, int logNumber)
        log1 = new DayLog(ExerciseType.PUSH, 1, 3, 2022, "new record 198ibs leg press", 3);
        log2 = new DayLog(ExerciseType.PULL, 2, 10, 2021, "Very tired today - weak performance", 99);
        log3 = new DayLog(ExerciseType.GLUTES, 2, 10, 2021, "Very tired today - weak performance", 4);
        log4 = new DayLog(ExerciseType.LEG, 21, 12, 2021, "Best workout ever", 19);

        mainProfile.addExerciseLog(log1);
        mainProfile.addExerciseLog(log2);
        mainProfile.addExerciseLog(log3);
        mainProfile.addExerciseLog(log4);
    }

    @Test
    public void testConstructor() {
        assertEquals("Kelly Kim", mainProfile.getName());
        assertEquals(18, mainProfile.getAge());
        assertEquals(120, mainProfile.getWeight());
        assertEquals(4, mainProfile.getExerciseLog().size());
    }


    @Test
    public void testAddExerciseLog() {
        assertEquals(4, mainProfile.getExerciseLog().size());
        DayLog testDayLog = new DayLog(ExerciseType.PUSH, 2, 12, 2021, "NEW DAY", 13);
        mainProfile.addExerciseLog(testDayLog);
        assertEquals(5, mainProfile.getExerciseLog().size());
        assertTrue(mainProfile.getExerciseLog().contains(testDayLog));
    }

    @Test
    public void testRemoveLog() {
        assertTrue(mainProfile.getExerciseLog().contains(log1));
        assertEquals(4, mainProfile.getExerciseLog().size());
        assertTrue(mainProfile.removeLog(3));
        assertEquals(3, mainProfile.getExerciseLog().size());
        assertFalse(mainProfile.removeLog(5));
        assertEquals(3, mainProfile.getExerciseLog().size());
        assertTrue(mainProfile.removeLog(99));
        assertTrue(mainProfile.removeLog(4));
        assertEquals(1, mainProfile.getExerciseLog().size());
        assertTrue(mainProfile.removeLog(19));
        assertEquals(0, mainProfile.getExerciseLog().size());
    }

    @Test
    public void testCountDayLog() {
        assertEquals(4, mainProfile.countDayLog());
        DayLog testDayLog = new DayLog(ExerciseType.PUSH, 2, 12, 2021, "NEW DAY", 13);
        mainProfile.addExerciseLog(testDayLog);
        assertEquals(5, mainProfile.countDayLog());
        mainProfile.removeLog(13);
        assertEquals(4, mainProfile.countDayLog());
        mainProfile.removeLog(99);
        mainProfile.removeLog(4);
        mainProfile.removeLog(19);
        mainProfile.removeLog(3);
        assertEquals(0, mainProfile.countDayLog());
    }
}








