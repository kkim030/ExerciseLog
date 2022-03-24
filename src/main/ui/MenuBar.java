package ui;

import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuBar implements ActionListener {
    private JMenuBar bar;
    private JMenu jmFile;
    private JMenuItem jmiOpenLogs;
    private JMenuItem jmiSave;
    private JMenuItem jmiAbout;
    private JMenuItem jmiAdd;
    private  JFrame frame;
    private JsonWriter jsonWriter;

    private Profile myProfile;
    private JLabel userLabel;
    private JLabel ageLabel;
    private JLabel weightLabel;
    private JLabel userJason;
    private JLabel ageJason;
    private JLabel weightJason;
    private JLabel nullLabel;
    private JsonReader jsonLabel;
    private static final String JSON_LOCATION = "./data/profile.json";


    public MenuBar() {
        jsonLabel = new JsonReader(JSON_LOCATION);
        jsonWriter = new JsonWriter(JSON_LOCATION);
        frame = new JFrame("FitMe! Exercise Diary");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayProfileSetting();
        displayPic();
        bar = new JMenuBar();
        makeMenuItems();

        JMenu jmHelp = new JMenu("Help");
        jmiAbout = new JMenuItem("About");
        jmHelp.add(jmiAbout);
        bar.add(jmHelp);
        addActionListeners();

        frame.setJMenuBar(bar);
        frame.setVisible(true);
    }

    public void displayPic() {
        try {
            BufferedImage myPic = ImageIO.read(new File("./src/image/21464082.jpg"));
            JLabel picture = new JLabel(new ImageIcon(myPic));
            frame.add(picture);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Image Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void displayProfileSetting() {
        addLabels();
        try {
            myProfile = jsonLabel.read();
            userJason = new JLabel();
            userJason.setText(myProfile.getName());
            userJason.setBounds(90, 30, 165, 25);
            frame.add(userJason);
            ageJason = new JLabel();
            ageJason.setText(myProfile.getAge());
            ageJason.setBounds(90, 60, 165, 25);
            frame.add(ageJason);
            weightJason = new JLabel();
            weightJason.setText(myProfile.getWeight());
            weightJason.setBounds(90, 90, 165, 25);
            frame.add(weightJason);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Profile Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
        nullLabel = new JLabel("");
        nullLabel.setBounds(30, 30, 165, 25);
        frame.add(nullLabel);
    }

    public void addLabels() {
        userLabel = new JLabel("Name:");
        userLabel.setBounds(30, 30, 165, 25);
        weightLabel = new JLabel("Weight:");
        weightLabel.setBounds(30, 90, 165, 25);
        ageLabel = new JLabel("Age:");
        ageLabel.setBounds(30, 60, 165, 25);
        frame.add(userLabel);
        frame.add(ageLabel);
        frame.add(weightLabel);
    }

    public void makeMenuItems() {
        jmFile = new JMenu("File");
        jmiOpenLogs = new JMenuItem("Open Exercise Log Records");
        actionOpenExerciseLog();
        jmiSave = new JMenuItem("Save");
        actionOpenSave();
        jmiAdd = new JMenuItem("Add New Log");
        jmFile.add(jmiOpenLogs);
        jmFile.add(jmiSave);
        jmFile.add(jmiAdd);
        jmFile.addSeparator();
        bar.add(jmFile);
    }

    public void actionOpenExerciseLog() {
        jmiOpenLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExerciseLogDisplay();
            }
        });
    }

    public void actionOpenSave() {
        jmiSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myProfile = jsonLabel.read();
                    jsonWriter.open();
                    jsonWriter.write(myProfile);
                    jsonWriter.close();
                    JOptionPane.showMessageDialog(null, "Data Saved", "Saved!", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error saving Data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    public void addActionListeners() {
        jmiAbout.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae) {
        String comStr = ae.getActionCommand();
        System.out.println(comStr + " Selected");
    }
}
