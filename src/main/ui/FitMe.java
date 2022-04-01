package ui;

// FitMe application

import model.DayLog;
import model.ExerciseType;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

// Runs the FitMe console application
public class FitMe {

    private static final String JSON_LOCATION = "./data/profile.json";
    private static final String VIEW_PROFILE = "v";
    private static final String ADD_NEW_DAYLOG = "a";
    private static final String VIEW_ALL_DAYLOG = "e";
    private static final String QUIT_COMMAND = "q";
    private static final String DELETE_EVENT = "d";
    private static final String SAVE_EVENTS = "s";
    private static final String LOAD_EVENTS = "l";

    private Scanner input;
    private Profile myProfile;
    private DayLog newDayLog;
    private boolean runProgram;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Random rand = new Random();

    // EFFECTS: runs the FitMe application
    // REFERENCE: FitLifeGymKiosk Code
    public FitMe() throws FileNotFoundException {
        input = new Scanner(System.in);
        runProgram = true;
        jsonWriter = new JsonWriter(JSON_LOCATION);
        jsonReader = new JsonReader(JSON_LOCATION);
        runFitMe();
    }

    // EFFECTS: prints instructions to use the app
    private void printInstructions() {
        System.out.println("Please choose between the following: \n "
                + "\tv -> to view your profile"
                + "\ta -> to add a daily exercise log"
                + "\td -> to delete previous exercise log"
                + "\te -> to view all exercise logs"
                + "\ts -> save all exercise logs to file"
                + "\tl -> load previous exercise logs from file"
                + "\tq -> to quit");
    }

    // EFFECTS: prints welcome message
    private void printWelcomeMessage() {
        System.out.println("Welcome to Fit!me, an exercise diary for you to achieve your physical goals!"
        );
    }

    // MODIFIES: this
    // EFFECTS: processes user input until user quits
    //REFERENCE: FitLifeGymKiosk Code
    private void runFitMe() {
        printWelcomeMessage();
        generateProfile();
        printInstructions();
        handleUserInput();
    }

    //EFFECTS: parses user input until user quits
    //REFERENCE: FitLifeGymKiosk Code
    public void handleUserInput() {
        String str;
        while (runProgram) {
            if (input.hasNext()) {
                str = input.nextLine();
                parseInput(str);
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: prints menu options and info depending on input string
    // REFERENCE: FitLifeGymKiosk Code
    private void parseInput(String str) {
        if (str.length() > 0) {
            if (str.equals(VIEW_PROFILE)) {
                viewProfile();
            } else if (str.equals(ADD_NEW_DAYLOG)) {
                addDayLog();
            } else if (str.equals(VIEW_ALL_DAYLOG)) {
                viewDayLog();
            } else if (str.equals(DELETE_EVENT)) {
                deleteDayLog();
            } else if (str.equals(SAVE_EVENTS)) {
                saveExerciseLogs();
            } else if (str.equals(LOAD_EVENTS)) {
                loadProfileInfo();
            } else if (str.equals(QUIT_COMMAND)) {
                endProgram();
                runProgram = false;
            } else {
                System.out.println("Sorry, I didn't understand that command. Please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: generates new profile. If wrong input, starts again.
    private void generateProfile() {
        while (true) {
            try {
                System.out.println("What is your name?");
                String name = input.nextLine();
                System.out.println("What is your age?");
                String age = input.nextLine();

                System.out.println("What is your current weight(ibs)?");
                String weight = input.nextLine();

                myProfile = new Profile(name, age, weight);
                System.out.println("Thank you for updating your profile " + myProfile.getName() + "!");
                break;
            } catch (Exception e) {
                System.out.println("Invalid input: please try again");
            }
        }
    }

    // EFFECT: view current profile
    private void viewProfile() {
        System.out.println("Hello " + myProfile.getName() + ":");
        System.out.println("    - Age: " + myProfile.getAge());
        System.out.println(("    - Weight: " + myProfile.getWeight() + "ibs"));
    }

    // MODIFIES: this
    // EFFECT: Make new DayLog. If invalid, prints statement to note the entered input it wrong and restarts.
    private void addDayLog() {
        while (true) {
            try {
                int upperbound = 100;
                System.out.println("Where did you work on? Please input one of - PUSH, PULL, LEG, GLUTES:");
                ExerciseType exercise = ExerciseType.valueOf(input.nextLine());
                System.out.println("Input day of the month (format: DD):");
                int day = input.nextInt();
                System.out.println("Input today's month (format: MM):");
                int month = input.nextInt();
                System.out.println("Input today's year (format: YYYY):");
                int year = input.nextInt();
                System.out.println("Notes:");
                input.nextLine();
                String notes = input.nextLine();
                int newLogNumber = rand.nextInt(upperbound);
                newDayLog = new DayLog(exercise, day, month, year, notes, newLogNumber);
                myProfile.addExerciseLog(newDayLog);
                printInstructions();
                break;
            } catch (Exception e) {
                System.out.println("Invalid input: Please try again");
            }
        }
    }

    // EFFECT: view historical exercise day logs and counts how many days you have exercised.
    private void viewDayLog() {
        System.out.println("You have exercised the following dates: (Month/Day/Year)");
        if (myProfile.getExerciseLog().isEmpty()) {
            System.out.println("No exercise have been yet recorded.");
        } else {
            for (DayLog dayLog : myProfile.getExerciseLog()) {
                System.out.println("- [Log#: " + dayLog.getLogNumber() + "] " + dayLog.printDate()
                        + "- " + dayLog.getNotes() + ">");
            }
        }
        System.out.println("You have exercised: " + myProfile.countDayLog() + " time(s)");
        printInstructions();
    }

    // EFFECTS: saves exercise logs to file
    // REFERENCE: JsonSerializationDemo
    private void saveExerciseLogs() {
        try {
            jsonWriter.open();
            jsonWriter.write(myProfile);
            jsonWriter.close();
            System.out.println("Saved!");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot save file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads logs from file
    // REFERENCE: JsonSerializationDemo
    private void loadProfileInfo() {
        try {
            myProfile = jsonReader.read();
            System.out.println("Loaded profile");
        } catch (IOException e) {
            System.out.println("unable to find exercise history :( ");
        }
    }

    // MODIFIES: this
    // EFFECT: By inputting log number of past day log, removes the particular entry.
    //         If wrong log number entered, goes back to main menu.
    private void deleteDayLog() {
        while (true) {
            try {
                System.out.println("Please enter the log number of the entry you would like to delete. If uncertain, "
                        + "please view all events by typing 'e'");
                int logNumber = input.nextInt();
                myProfile.removeLog(logNumber);
                System.out.println("Log updated as per request.");
                printInstructions();
                break;
            } catch (Exception e) {
                printInstructions();
                break;
            }
        }
    }

    //EFFECTS: stops the program.
    public void endProgram() {
        System.out.println("Thank you and come back!");
        input.close();
    }
}
