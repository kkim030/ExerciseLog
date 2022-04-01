package ui;

import model.Event;
import model.EventLog;
import model.Profile;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


//makes the main menubar where you can access all events
public class MenuBar {
    private JMenuBar bar;
    private JMenu jmFile;
    private JMenuItem jmiOpenLogs;
    private JMenuItem jmiSave;
    private JFrame frame;
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

    // MODIFIES: this
    // EFFECT: make the main menu bar that displays profile information
    public MenuBar(Profile myProfile) {
        this.myProfile = myProfile;
        jsonLabel = new JsonReader(JSON_LOCATION);
        jsonWriter = new JsonWriter(JSON_LOCATION);
        frame = new JFrame("FitMe! Exercise Diary");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        displayProfileSetting();
        displayPic();
        bar = new JMenuBar();
        makeMenuItems();
        frame.setJMenuBar(bar);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                super.windowClosing(e);
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event.getDescription());
                }
            }

        });

    }

    // MODIFIES: this
    // EFFECT: Adds main picture
    public void displayPic() {
        try {
            BufferedImage myPic = ImageIO.read(new File("./src/image/21464082.jpg"));
            JLabel picture = new JLabel(new ImageIcon(myPic));
            frame.add(picture);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Image Not Found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECT: display Profile information
    //         if Profile not found, error pane is shown
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

    // MODIFIES: this
    // EFFECT: initialize and add labels
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

    // MODIFIES: this
    // EFFECT: make menu bar items
    public void makeMenuItems() {
        jmFile = new JMenu("File");
        jmiOpenLogs = new JMenuItem("Open Exercise Log Records");
        actionOpenExerciseLog();
        jmiSave = new JMenuItem("Save");
        actionOpenSave();
        jmFile.add(jmiOpenLogs);
        jmFile.add(jmiSave);
        jmFile.addSeparator();
        bar.add(jmFile);
    }

    // EFFECT: open new Exercise Log Display
    public void actionOpenExerciseLog() {
        jmiOpenLogs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExerciseLogDisplay(myProfile);
            }
        });
    }

    // EFFECT: save all Profile information to file. if saved, "saved!" pane opens.
    //         if file path is wrong, error pane is shown.
    public void actionOpenSave() {
        jmiSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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
}
