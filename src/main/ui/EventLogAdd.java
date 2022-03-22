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


    public EventLogAdd() {
        frame = new JFrame();
        jsonReader = new JsonReader(JSON_LOCATION);
        jsonWriter = new JsonWriter(JSON_LOCATION);
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    public void addSave() {
        save = new JButton("Save");
        save.setBounds(118, 196, 100, 30);
        panel.add(save);
        actionSave();
    }

    public void actionSave() {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myProfile = jsonReader.read();
                    if (e.getSource() == pushButton) {
                        type = ExerciseType.PUSH;
                    } else if (e.getSource() == pullButton) {
                        type = ExerciseType.PULL;
                    } else if (e.getSource() == legButton) {
                        type = ExerciseType.LEG;
                    } else {
                        type = ExerciseType.GLUTES;
                    }
                    parseLogs();
                    myProfile.addExerciseLog(newLog);
                    jsonWriter.open();
                    jsonWriter.write(myProfile);
                    jsonWriter.close();
                    JOptionPane.showMessageDialog(null, "Data Saved", "Saved!", JOptionPane.INFORMATION_MESSAGE);
                    frame.setVisible(false);
                    frame.dispose();
                    new ExerciseLogDisplay();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Profile Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

    }

    public void parseLogs() {
        day = Integer.parseInt(dayText.getText());
        month = Integer.parseInt(monthText.getText());
        year = Integer.parseInt(yearText.getText());
        newLogNumber = rand.nextInt(upperbound);
        notes = notesText.getText();
        newLog = new DayLog(type, day, month, year, notes, newLogNumber);
    }




}

