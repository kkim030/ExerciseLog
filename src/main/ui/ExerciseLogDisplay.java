package ui;

import model.DayLog;
import model.ExerciseType;
import model.Profile;
import persistence.JsonReader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ExerciseLogDisplay extends JFrame {
    private static final String JSON_LOCATION = "./data/profile.json";
    private Profile myProfile;
    private JFrame frame;
    private JTable table;
    private JsonReader jsonReader;
    private JMenuBar bar;
    private JMenu jmFile;
    private JMenuItem jmiAdd;
    private JMenuItem jmiSave;
    private JMenuItem jmiDelete;
    private JMenuItem jmiExit;
    private DefaultTableModel dtm;


    public ExerciseLogDisplay() {
        frame = new JFrame();
        jsonReader = new JsonReader(JSON_LOCATION);
        try {
            myProfile = jsonReader.read();
        } catch (IOException e) {
            System.out.println("unable");
        }
        frame.setTitle("* Diary *");
        makeTable();
        bar = new JMenuBar();
        makeMenuItems();
        frame.setJMenuBar(bar);
        frame.setVisible(true);
        actionAddExerciseLog();
        actionDeleteExerciseLog();

    }

    public void makeMenuItems() {
        jmFile = new JMenu("File");
        jmiAdd = new JMenuItem("Add");
        jmiDelete = new JMenuItem("Delete");
        jmFile.add(jmiAdd);
        jmFile.add(jmiDelete);
        bar.add(jmFile);
    }


    public void makeTable() {
        // create object of table and table model
        table = new JTable();
        dtm = new DefaultTableModel(0, 0);

        // add header of the table
        String[] header = new String[]{"Day", "Month", "Year", "type", "Log Number", "Notes"};

        // add header in table model
        dtm.setColumnIdentifiers(header);
        //set model into the table object
        table.setModel(dtm);

        if (myProfile.getExerciseLog().size() == 0) {
            dtm.addRow(new Object[]{"n/a", "n/a", "n/a",
                    "n/a", "n/a", "n/a"});
        } else {
            parseLog();
        }
        JScrollPane sp = new JScrollPane(table);
        frame.add(sp);
        frame.setSize(500, 200);
        frame.setVisible(true);
    }

    public void parseLog() {
        for (DayLog dayLog : myProfile.getExerciseLog()) {
            int day = dayLog.getDay();
            int month = dayLog.getMonth();
            int year = dayLog.getYear();
            ExerciseType type = dayLog.getType();
            int logNumber = dayLog.getLogNumber();
            String notes = dayLog.getNotes();
            dtm.addRow(new Object[]{day, month, year,
                    type, logNumber, notes});
        }
    }

    public void actionAddExerciseLog() {
        jmiAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new EventLogAdd();
            }
        });
    }

    public void actionDeleteExerciseLog() {
        jmiDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                new DeletePane();
            }
        });
    }


}
