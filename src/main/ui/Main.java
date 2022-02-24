package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new FitMe();
        } catch (FileNotFoundException e) {
            System.out.println("No prior record found");
        }
    }

}

