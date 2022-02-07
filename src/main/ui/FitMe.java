package ui;

// FitMe application

import model.DayLog;
import model.ExerciseType;
import model.Profile;

import java.util.Scanner;
import java.util.Random;


public class FitMe {


    private static final String VIEW_PROFILE = "v";
    private static final String ADD_NEW_DAYLOG = "a";
    private static final String VIEW_ALL_DAYLOG = "e";
    private static final String QUIT_COMMAND = "q";
    private static final String DELETE_EVENT = "d";

    private Scanner input;
    private Profile myProfile;
    private DayLog newDayLog;
    private boolean runProgram;
    private Random rand = new Random();



    // EFFECTS: runs the FitMe application
    public FitMe() {
        input = new Scanner(System.in);
        runProgram = true;
        runFitMe();
    }

    //EFFECTS: prints instructions to use the app
    private void printInstructions() {
        System.out.println("Please choose between the following: \n "
                + "\tv -> to view your profile"
                + "\ta -> to add a daily exercise log"
                + "\td -> to delete previous exercise log"
                + "\te -> to view all exercise logs"
                + "\tq -> to quit");
    }

    //EFFECTS: prints welcome message
    private void printWelcomeMessage() {
        System.out.println("Welcome to Fit!me, an exercise diary for you to achieve your physical goals!"
        );
    }

    // MODIFIES: this
    // EFFECTS: processes user input until user quits
    private void runFitMe() {
        printWelcomeMessage();
        generateProfile();
        printInstructions();
        handleUserInput();
    }


    //EFFECTS: parses user input until user quits
    public void handleUserInput() {
        String str;
        while (runProgram) {
            if (input.hasNext()) {
                str = input.nextLine();
                str = makePrettyString(str);
                parseInput(str);
            }
        }
    }

    //EFFECTS: prints menu options and info depending on input str
    private void parseInput(String str) {
        if (str.length() > 0) {
            switch (str) {
                case VIEW_PROFILE:
                    viewProfile();
                    break;
                case ADD_NEW_DAYLOG:
                    addDayLog();
                    break;
                case VIEW_ALL_DAYLOG:
                    viewDayLog();
                    break;
                case DELETE_EVENT:
                    deleteDayLog();
                    break;
                default:
                    System.out.println("Sorry, I didn't understand that command. Please try again.");
                    break;
                case QUIT_COMMAND:
                    endProgram();
                    runProgram = false;
                    break;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: generate profile
    private void generateProfile() {
        while (true) {
            try {
                System.out.println("What is your name?");
                String name = input.nextLine();
                System.out.println("What is your age?");
                String ageString = input.nextLine();
                int age = Integer.parseInt(ageString);
                System.out.println("What is your current weight(ibs)?");
                String weightString = input.nextLine();
                int weight = Integer.parseInt(weightString);
                myProfile = new Profile(name, age, weight);
                System.out.println("Thank you for updating your profile " + myProfile.getName() + "!");
                break;
            } catch (Exception e) {
                System.out.println("Invalid input: please try again");
            }
        }
    }

    //EFFECT: view current profile
    private void viewProfile() {
        System.out.println("Hello " + myProfile.getName() + ":");
        System.out.println("    - Age: " + myProfile.getAge());
        System.out.println(("    - Weight: " + myProfile.getWeight() + "ibs"));
    }

    //EFFECT: Make new DayLog.
    private void addDayLog() {
        while (true) {
            try {
                int upperbound = 100;
                System.out.println("Where did you work on? Please input one of - PUSH, PULL, LEG, GLUTES:");
                ExerciseType exercise = ExerciseType.valueOf(input.nextLine());
                System.out.println("Input day of the month (format: %%):");
                int day = input.nextInt();
                System.out.println("Input today's month (format: %%):");
                int month = input.nextInt();
                System.out.println("Input today's year (format: %%%%):");
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

    //EFFECT: view historical day long
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

    private void deleteDayLog() {
        System.out.println("Please enter the log number of the entry you would like to delete. If uncertain, please "
                + "view all events by typing 'e'");
        while (true) {
            try {
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

    //EFFECTS: removes white space and quotation marks around s
    private String makePrettyString(String s) {
        s = s.toLowerCase();
        s = s.trim();
        s = s.replaceAll("\"|\'", "");
        return s;
    }

    //EFFECTS: stops receiving user input
    public void endProgram() {
        System.out.println("Quitting...");
        input.close();
    }
}
