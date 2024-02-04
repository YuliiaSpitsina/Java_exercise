package com.shpp.p2p.cs.yuspytsyna.assignment5;

import com.shpp.cs.a.console.TextProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads a csv file and enters fields in specific columns
 */
public class Assignment5Part4 extends TextProgram {

    /**
     * Name of the csv file
     */
    public static final String FILENAME = "filename.csv";

    /**
     * Input number of columns and display fields in this column
     */
    public void run() {

        while (true) {
            int columnIndex = readInt("\nInput number of column: ");
            ArrayList<String> originColumn = extractColumn(FILENAME, columnIndex);
            displayColumn(originColumn);
        }
    }

    /**
     * Displays the contents of the provided ArrayList
     * of strings, representing a column of data
     *
     * @param originColumn ArrayList representing a column of data
     */
    private void displayColumn(ArrayList<String> originColumn) {
        if (originColumn != null) {
            for (String value : originColumn) {
                System.out.println(value);
            }
        } else {
            System.out.println("File not found or error occurred.");
        }
    }

    /**
     * Extracts a column of data from a CSV file
     * based on the provided column index
     *
     * @param filename    the name of the CSV file to extract data from
     * @param columnIndex the index of the column to extract.
     * @return ArrayList containing the values of the extracted column
     *         Returns null if the file cannot be read
     */
    private ArrayList<String> extractColumn(String filename, int columnIndex) {
        ArrayList<String> columnValues = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;

            /* Read each line in the file */
            while ((line = br.readLine()) != null) {

                /* Parse the line into fields */
                ArrayList<String> fields = fieldsIn(line);

                /* Check if the columnIndex is within bounds */
                if (columnIndex < fields.size() && columnIndex >= 0) {

                    /* Add the value at the specified column index to the ArrayList */
                    columnValues.add(fields.get(columnIndex));
                } else {
                    System.out.println("No such column");
                    break;
                }
            }
            br.close();

        } catch (IOException e) {
            return null;
        }

        return columnValues;
    }

    /**
     * Parses a line of CSV data into individual fields, handling quoted values
     *
     * @param line the line of CSV data to be parsed
     * @return arrayList of strings containing the parsed fields
     */
    private static ArrayList<String> fieldsIn(String line) {
        ArrayList<String> fields = new ArrayList<>();

        /* Flag to track if currently inside a quoted value */
        boolean insideQuotes = false;
        String currentField = "";

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (insideQuotes) {
                    if (i < line.length() - 1 && line.charAt(i + 1) == '"') {
                        /* Handle double quote within a quoted value */
                        currentField += c;
                        i++;
                    } else {
                        insideQuotes = false; // Exit quoted state
                    }
                } else {
                    insideQuotes = true; // Enter quoted state
                }

                /* If the character is a comma and not inside quotes */
            } else if (c == ',' && !insideQuotes) {
                fields.add(currentField); // Add the parsed field to the ArrayList
                currentField = "";
            } else {
                currentField += c; // Build the field character by character
            }
        }

        fields.add(currentField);  // Add the last field after the loop

        return fields;
    }
}
