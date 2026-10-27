/**
 *
 * Spring 2024
 * Software Project
 *
 * PictureList.java
 *
 * Manages a collection of pictures using a
 * fixed-size array. Supports retrieving, adding,
 * and sorting picture records.
 *
 */

import java.io.*;
import java.util.*;

public class PictureList {
    private final Picture[] pictures = new Picture[100];
    private int length;

    /**
     * Default constructor that initializes the PictureList
     * by reading from the default data file "picture-normal.data".
     */
    public PictureList() {
        this("picture-normal.data");
    }

    /**
     * Constructor that initializes the PictureList
     * by reading from the specified data file.
     *
     * @param infoFileName The name of the data file containing picture information.
     */
    public PictureList(String infoFileName) {
        readFile(infoFileName);
    }

    /**
     * Read picture information from a file and populate the PictureList.
     * Each line in the file should contain picture information in a specific
     * format.
     *
     * @param infoFileName The name of the data file to read from.
     */
    private void readFile(String infoFileName) {
        Scanner input;

        try {
            input = new Scanner(new File(infoFileName));
        } catch (FileNotFoundException e) {
            System.err.println("Error has occurred while reading file.");
            return;
        }

        String line;
        int current = 0;
        while (input.hasNext()) {
            try {
                line = input.nextLine().strip();
                current++;

                if (line.startsWith("//")) // Ignore comment lines
                    continue;

                // Remove the first and last '<' and '>' and split by '> <'
                // -1: Keep trailing empty strings
                String[] array = line.substring(1, line.length() - 1).split("> <", -1);
                if (array.length != 6) {
                    throw new IllegalArgumentException("Array length is not 6.");
                }

                String[] trimArray = new String[6];
                for (int i = 0; i < 6; i++)
                    trimArray[i] = array[i].trim();

                add(new Picture(trimArray));
            } catch (Exception e) {
                System.err.printf("Error on line #%d: %s%n", current, e.getMessage());
            }
        }

        input.close();
    }

    /**
     * Write the picture information to a file.
     * Each picture's information will be written in a specific format.
     *
     * @param infoFileName The name of the data file to write to.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void writeFile(String infoFileName) throws IOException {
        File file = new File(infoFileName);

        if (!file.exists())
            file.createNewFile();

        PrintWriter output = new PrintWriter(file);
        for (int i = 0; i < length; i++) {
            output.println(pictures[i]);
        }
        output.close();
    }

    /**
     * Get the number of pictures in the list.
     *
     * @return The number of pictures in the list.
     */
    public int size() {
        return length;
    }

    /**
     * Get the picture at the specified index.
     *
     * @param i The index of the picture to retrieve.
     * @return The Picture object at the specified index,
     *         or null if the index is out of bounds.
     */
    public Picture get(int i) {
        return (i >= 0 && i < length) ? pictures[i] : null;
    }

    /**
     * Get a copy of the array of all pictures in the list.
     *
     * @return An array containing all Picture objects in the list.
     */
    public Picture[] getPictures() {
        Picture[] array = new Picture[length];
        System.arraycopy(pictures, 0, array, 0, length);
        return array;
    }

    /**
     * Add a picture to the list.
     * If the list is full or a picture with the same ID already exists,
     * the picture will not be added.
     *
     * @param pic The Picture object to add to the list.
     */
    public void add(Picture pic) {
        if (length >= 100) {
            return;
        }

        for (int i = 0; i < length; i++) {
            if (Objects.equals(pictures[i].getId(), pic.getId()))
                throw new IllegalArgumentException(pic.getId() + ": ID already exists!");
        }
        pictures[length] = pic;
        length++;
    }

    /**
     * Sort the pictures in the list by their timestamp in ascending order.
     */
    public void sortByDate() {
        // Sort with Comparator and lambda for simplicity
        Arrays.sort(pictures, 0, length,
                Comparator.comparing(pic -> pic.time));
    }
}
