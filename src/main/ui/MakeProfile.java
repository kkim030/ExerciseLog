package ui;

import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// makes pane to make profile
public class MakeProfile {

    private static JLabel userName;
    private static JTextField userText;
    private static JLabel ageLabel;
    private static JTextField ageText;
    private static JLabel weightLabel;
    private static JTextField weightText;
    private static JButton newProfileButton;
    private static JButton logInButton;
    private static JPanel panel = new JPanel();
    private static JFrame frame;
    private Profile myProfile;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_LOCATION = "./data/profile.json";


    // EFFECT: makes MakeProfile frame
    public MakeProfile() {
        jsonWriter = new JsonWriter(JSON_LOCATION);
        jsonReader = new JsonReader(JSON_LOCATION);
        frame = new JFrame();
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        panel.setLayout(null);
        newProfile();
        frame.setVisible(true);
    }

    // EFFECT: initiates labels, set bounds and add to panel
    public void newProfile() {
        userName = new JLabel("Name");
        userName.setBounds(10, 20, 80, 25);
        panel.add(userName);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        ageLabel = new JLabel("Age");
        ageLabel.setBounds(10, 50, 80, 25);
        panel.add(ageLabel);

        ageText = new JTextField();
        ageText.setBounds(100, 50, 165, 25);
        panel.add(ageText);

        weightLabel = new JLabel("Weight");
        weightLabel.setBounds(10, 80, 80, 25);
        panel.add(weightLabel);

        weightText = new JTextField();
        weightText.setBounds(100, 80, 165, 25);
        panel.add(weightText);

        addProfileButton();
        logInButton();

    }

    // EFFECT: add new button for new profile
    public void addProfileButton() {
        newProfileButton = new JButton("New Profile");
        newProfileButton.setBounds(10, 130, 150, 25);
        panel.add(newProfileButton);
        actionAddProfile();
    }

    // MODIFIES: Profile
    // EFFECT: adds new profile to jar
    //         if file path not found, error pane is shown
    public void actionAddProfile() {
        newProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = userText.getText().trim();
                String age = ageText.getText();
                String weight = weightText.getText();
                myProfile = new Profile(name, age, weight);
                try {
                    jsonWriter.open();
                    jsonWriter.write(myProfile);
                    jsonWriter.close();
                    JOptionPane.showMessageDialog(null, "Data Saved", "Saved!", JOptionPane.INFORMATION_MESSAGE);
                    clearPane();
                    frame.setVisible(false);
                    frame.dispose();
                    new MenuBar(myProfile);
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error saving Data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECT: clears all text fields
    private void clearPane() {
        userText.setText(null);
        ageText.setText(null);
        weightText.setText(null);
        frame.setVisible(false);
        frame.dispose();
    }

    // EFFECT: initialize, set bounds and add "Reload" button
    public void logInButton() {
        logInButton = new JButton("Reload");
        logInButton.setBounds(150, 130, 150, 25);
        actionLogInButton();
        panel.add(logInButton);
    }

    // EFFECT: loads Profile information
    //         if profile not found, error pane is shown
    public void actionLogInButton() {
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = userText.getText().trim();
                    myProfile = jsonReader.read();
                    if (myProfile.getName().equals(name)) {
                        JOptionPane.showMessageDialog(null, "Profile loaded", "LOAD", JOptionPane.INFORMATION_MESSAGE);
                        clearPane();
                        new MenuBar(myProfile);
                    } else {
                        JOptionPane.showMessageDialog(null, "Profile Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                        clearPane();
                        new MakeProfile();
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Profile Not Found?", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
