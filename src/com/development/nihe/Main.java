package com.development.nihe;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;

/**
 * This class reads in a directory and prints out the data to a .txt and .csv
 * file
 * 
 * @author Niels Barth
 * @version 2.0 for Java 1.8
 * 
 * @param args[0] - The directory of the files
 * @param args[1] - The type of output, either comma or point
 * 
 */

public class Main {

    /**
     * This method prints the data to a .txt and .csv file
     * 
     * @param left       - The left column of the data
     * @param right      - The right column of the data
     * @param filename   - The name of the file
     * @param outputType - The type of output, either comma or point
     */

    public static void printToFiles(ArrayList<String> left, 
                                    ArrayList<String> right, 
                                    String filename, 
                                    String outputType) {

        // Setting names for the output files
        String filenameTXT = filename + " - " + "Wavenumber + " + right.get(0) + " " + outputType + ".txt";
        String filenameCSV = filename + " - " + "Wavenumber + " + right.get(0) + " " + outputType + ".csv";

        // Create file
        try {
            File file = new File(filenameTXT);
            File fileCSV = new File(filenameCSV);

            // Create .txt file
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }

            // Create .csv file
            if (fileCSV.createNewFile()) {
                System.out.println("File created: " + fileCSV.getName());
            } else {
                System.out.println("File already exists.");
            }

        } catch (IOException e) {
            System.out.println(
                    "Error while creating files, this should not happen under any circumstances. Contact the developer if this error occurs");
        }

        // Write to file
        try {

            FileWriter fileWriterCSV = new FileWriter(filenameCSV);

            for (int i = 1; i < right.size(); i++) {

                if (i != right.size() - 1) {

                    fileWriterCSV.write(left.get(i) + "\t" + right.get(i) + "\n");

                } else {

                    fileWriterCSV.write(left.get(i) + "\t" + right.get(i));
                }

            }

            fileWriterCSV.close();

        } catch (IOException e) {
            System.out.println(
                    "Error while writing to files, this should not happen under any circumstances. Contact the developer if this error occurs");
        }
    }

    public static void main(String[] args) {

        // Arraylist for useful files
        ArrayList<String> usefulFiles = new ArrayList<String>();

        // Open file
        String inputDirectory = args[0];
        String directory = inputDirectory.replace("\\", "/");

        String outputType = args[1];

        if (args[1].equals(null)) {
            System.out.println(
                    "Error while reading in the output type, please make sure you have entered either 'comma' or 'point' as the second argument. '\n Still getting errors? Contact the developer.");

            System.exit(0);
        }

        // Read file
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (file.isFile() && file.getName().endsWith(".txt")) {
                usefulFiles.add(file.getName());
            }
        }

        // Read useful files
        for (int i = 0; i < usefulFiles.size(); i++) {

            ArrayList<String> Wavenumber = new ArrayList<String>();
            ArrayList<String> Amplitude = new ArrayList<String>();
            ArrayList<String> Phase = new ArrayList<String>();
            ArrayList<String> Real = new ArrayList<String>();
            ArrayList<String> Imag = new ArrayList<String>();

            try {
                Scanner scanner = new Scanner(new File(directory + "/" + usefulFiles.get(i)));

                // Reading in all lines
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (outputType.equals("comma")) {
                        line = line.replace(".", ",");
                    }

                    String lineAsArray[] = line.split("\t");

                    Wavenumber.add(lineAsArray[0]);
                    Amplitude.add(lineAsArray[1]);
                    Phase.add(lineAsArray[2]);
                    Real.add(lineAsArray[3]);
                    Imag.add(lineAsArray[4]);
                }

                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error while reading in the files, please make sure there are only texfiles in your directory.\nStill getting errors? Contact the developer.");
            }

            // Print out the data to files
            printToFiles(Wavenumber, Amplitude, usefulFiles.get(i), outputType);
            printToFiles(Wavenumber, Phase, usefulFiles.get(i), outputType);
            printToFiles(Wavenumber, Real, usefulFiles.get(i), outputType);
            printToFiles(Wavenumber, Imag, usefulFiles.get(i), outputType);

        }
    }
}
