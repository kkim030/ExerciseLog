package ui;

import model.Profile;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeletePane {
    private JFrame frame;
    private JsonReader jsonReader;
    private static final String JSON_LOCATION = "./data/profile.json";
    private JLabel logNumberLabel;
    private JTextField logTextField;
    private JButton deleteButton;
    Profile myProfile;

    // CONSTRUCTOR
    public DeletePane() {
        jsonReader = new JsonReader(JSON_LOCATION);
        try {
            myProfile = jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Logs Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        frame = new JFrame("DELETE LOG");
        frame.setSize(220, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        logNumberLabel = new JLabel("Log Number:");
        logNumberLabel.setBounds(10, 20, 150, 25);
        frame.add(logNumberLabel);

        logTextField = new JTextField(20);
        logTextField.setBounds(100, 20, 165, 25);
        frame.add(logTextField);
        addDeleteButton();
        frame.setVisible(true);

    }

    public void addDeleteButton() {
        deleteButton = new JButton("Log In");
        deleteButton.setBounds(150, 130, 150, 25);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int logNumber = Integer.parseInt(logTextField.getText());
                    myProfile.removeLog(logNumber);
                    JOptionPane.showMessageDialog(null, "Data Deleted", "Deleted!", JOptionPane.INFORMATION_MESSAGE);
                    new ExerciseLogDisplay();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Event Log not found", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


}
