package ui;

import model.DayLog;
import model.ExerciseType;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

public class EventLogAdd {
    private static JFrame frame;
    private static JPanel panel = new JPanel();

    private ExerciseType type;
    private Random rand = new Random();
    int upperbound = 100;
    private DayLog newLog;
    String notes;
    int day;
    int month;
    int year;
    int newLogNumber;


    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_LOCATION = "./data/profile.json";
    private Profile myProfile;
    private static JLabel notesLabel;
    private static JLabel dayLabel;
    private static JLabel monthLabel;
    private static JLabel yearLabel;
    private static JLabel types;
    private static JLabel title;
    private static JLabel nullLabel;

    private JRadioButton pushButton;
    private JRadioButton pullButton;
    private JRadioButton legButton;
    private JRadioButton glutesButton;
    private ButtonGroup group;

    private JTextField notesText;
    private JTextField dayText;
    private JTextField monthText;
    private JTextField yearText;

    private JButton save;

    // EFFECT: constructs a frame which adds new exercise logs
    public EventLogAdd() {
        frame = new JFrame();
        jsonReader = new JsonReader(JSON_LOCATION);
        jsonWriter = new JsonWriter(JSON_LOCATION);
        frame.setSize(500, 300);
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        frame.setTitle("New Event");
        initializeLabelsTexts();
        setBoundsLabelsTexts();
        addLabelsTexts();
        initializeSetButtons();
        addButtons();
        addSave();
        frame.setVisible(true);
        frame.requestFocus();
    }

    // EFFECT: initialize all the labels and text fields
    public void initializeLabelsTexts() {
        title = new JLabel("Please log in your exercise information:");
        types = new JLabel("Exercise Type:");
        notesLabel = new JLabel("Notes:");
        dayLabel = new JLabel("Day:");
        monthLabel = new JLabel("Month:");
        yearLabel = new JLabel("Year:");
        nullLabel = new JLabel("");
        notesText = new JTextField();
        dayText = new JTextField();
        monthText = new JTextField();
        yearText = new JTextField();
    }

    // EFFECT: initialize all the jradio buttons and set bounds
    public void initializeSetButtons() {
        pushButton = new JRadioButton("PUSH");
        pullButton = new JRadioButton("PULL");
        legButton = new JRadioButton("LEG");
        glutesButton = new JRadioButton("GLUTES");
        group = new ButtonGroup();
        pushButton.setBounds(120, 30, 120, 50);
        pullButton.setBounds(250, 30, 80, 50);
        legButton.setBounds(370, 30, 120, 50);
        glutesButton.setBounds(490, 30, 120, 50);
    }

    // EFFECT: set bounds for all labels and texts
    public void setBoundsLabelsTexts() {
        title.setBounds(20, 5, 300, 50);
        types.setBounds(20, 30, 100, 50);
        notesLabel.setBounds(20, 150, 100, 50);
        dayLabel.setBounds(20, 90, 100, 50);
        monthLabel.setBounds(20, 120, 100, 50);
        yearLabel.setBounds(20, 60, 100, 50);
        nullLabel.setBounds(20, 60, 100, 50);
        yearText.setBounds(120, 72, 80, 25);
        dayText.setBounds(120, 102, 80, 25);
        monthText.setBounds(120, 132, 80, 25);
        notesText.setBounds(120, 162, 200, 25);
    }

    // EFFECT: add labels and texts to panel
    public void addLabelsTexts() {
        panel.add(title);
        panel.add(types);
        panel.add(dayLabel);
        panel.add(monthLabel);
        panel.add(yearLabel);
        panel.add(notesLabel);
        panel.add(nullLabel);
        panel.add(yearText);
        panel.add(monthText);
        panel.add(dayText);
        panel.add(notesText);
    }

    // EFFECT: add buttons to panel and buttons to group
    public void addButtons() {
        panel.add(pushButton);
        panel.add(pullButton);
        panel.add(legButton);
        panel.add(glutesButton);
        group.add(pushButton);
        group.add(pullButton);
        group.add(legButton);
        group.add(glutesButton);
    }

    // EFFECT: add save button
    public void addSave() {
        save = new JButton("Save");
        save.setBounds(118, 196, 100, 30);
        panel.add(save);
        actionSave();
    }

    // MODIFIES: Profile
    // EFFECT: add the exercise log to Profile and save to json
    //         if profile not found, return error pane
    public void actionSave() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myProfile = jsonReader.read();
                    buttonValid();
                    parseLogs();
                    myProfile.addExerciseLog(newLog);
                    jsonWriter.open();
                    jsonWriter.write(myProfile);
                    jsonWriter.close();
                    clearPane();
                    frame.setVisible(false);
                    frame.dispose();
                    JOptionPane.showMessageDialog(null, "Data Saved", "Saved!", JOptionPane.INFORMATION_MESSAGE);
                    new ExerciseLogDisplay();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Profile Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // EFFECT: determine which exercise type button is selected
    private void buttonValid() {
        if (pushButton.isSelected()) {
            type = ExerciseType.PUSH;
        } else if (pullButton.isSelected()) {
            type = ExerciseType.PULL;
        } else if (legButton.isSelected()) {
            type = ExerciseType.LEG;
        } else {
            type = ExerciseType.GLUTES;
        }
    }

    // EFFECT: clear all text fields
    private void clearPane() {
        notesText.setText(null);
        dayText.setText(null);
        monthText.setText(null);
        yearText.setText(null);
    }

    // REQUIRES: day month and year should be Integer
    // MODIFIES: DayLog
    // EFFECT: parse exercise log details from text field and make new day log class
    public void parseLogs() {
        day = Integer.parseInt(dayText.getText().trim());
        month = Integer.parseInt(monthText.getText().trim());
        year = Integer.parseInt(yearText.getText().trim());
        newLogNumber = rand.nextInt(upperbound);
        notes = notesText.getText();
        newLog = new DayLog(type, day, month, year, notes, newLogNumber);
    }


}

