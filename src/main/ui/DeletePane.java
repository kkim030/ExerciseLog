package ui;

import model.DayLog;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeletePane {
    private JFrame frame;
    private static JPanel panel = new JPanel();
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private static final String JSON_LOCATION = "./data/profile.json";
    private JLabel logNumberLabel;
    private JTextField logTextField;
    private JButton deleteButton;
    int logNumber;
    Profile myProfile;


    // EFFECT: Constructs a pane that deletes an event log.
    public DeletePane() {
        jsonWriter = new JsonWriter(JSON_LOCATION);
        jsonReader = new JsonReader(JSON_LOCATION);
        frame = new JFrame("DELETE LOG");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);

        panel.setLayout(null);
        frame.add(panel);
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(150, 130, 150, 25);

        logNumberLabel = new JLabel("Log Number:");
        logNumberLabel.setBounds(10, 20, 150, 25);
        panel.add(logNumberLabel);

        logTextField = new JTextField(20);
        logTextField.setBounds(100, 20, 165, 25);
        panel.add(logTextField);
        panel.add(deleteButton);
        addDeleteButton();
        panel.setVisible(true);
        frame.setVisible(true);
    }


    // EFFECT: deletes the exercise log with the log number entered
    //         if there are no logs, return "No logs to be deleted."
    //         if no exercise log with the same exercise number, return "event log not found"
    public void addDeleteButton() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logNumber = Integer.parseInt(logTextField.getText());
                readJson();
                if (myProfile.getExerciseLog().size() == 0) {
                    clearPane();
                    JOptionPane.showMessageDialog(null, "No logs to be deleted",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (myProfile.findLog(logNumber)) {
                        myProfile.removeLog(logNumber);
                        writeJson();
                    } else {
                        clearPane();
                        JOptionPane.showMessageDialog(null,
                                "Event Log not found", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    // EFFECT: write to jsonWriter when event log
    private void writeJson() {
        try {
            jsonWriter.open();
            jsonWriter.write(myProfile);
            jsonWriter.close();
            clearPane();
            JOptionPane.showMessageDialog(null, "Data Deleted", "Deleted!",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            clearPane();
            JOptionPane.showMessageDialog(null,
                    "No file found. Please restart program.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECT: read json and return error pane if not logs not found.
    private void readJson() {
        try {
            myProfile = jsonReader.read();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Logs Not Found",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECT: clear the text fields and close the frame.
    private void clearPane() {
        new ExerciseLogDisplay();
        logTextField.setText(null);
        frame.setVisible(false);
        frame.dispose();
    }
}
