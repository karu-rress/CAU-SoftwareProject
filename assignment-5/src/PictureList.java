
/**
 *
 * Spring 2024
 * Software Project
 *
 * PictureList.java
 *
 * Manages a collection of pictures using an
 * ArrayList. Supports retrieving, adding,
 * and sorting picture records.
 *
 */

import java.io.*;
import java.util.*;

public class PictureList {
    private final ArrayList<Picture> pictures = new ArrayList<Picture>();

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
        for (Picture picture : pictures) {
            output.println(picture);
        }
        output.close();
    }

    /**
     * Get the number of pictures in the list.
     *
     * @return The number of pictures in the list.
     */
    public int size() {
        return pictures.size();
    }

    /**
     * Get the picture at the specified index.
     *
     * @param i The index of the picture to retrieve.
     * @return The picture at the specified index.
     */
    public Picture get(int i) {
        return pictures.get(i);
    }

    /**
     * Get the list of all pictures.
     *
     * @return The list of all pictures.
     */
    public ArrayList<Picture> getPictures() {
        return pictures;
    }

    /**
     * Add a picture to the list.
     * If a picture with the same ID already exists,
     * the picture will not be added.
     *
     * @param pic The Picture object to add to the list.
     * @throws IllegalArgumentException If a picture with the same ID already exists
     *                                  in the list.
     */
    public void add(Picture pic) throws IllegalArgumentException {
        for (Picture picture : pictures) {
            if (Objects.equals(picture.getId(), pic.getId()))
                throw new IllegalArgumentException(pic.getId() + ": ID already exists!");
        }
        pictures.add(pic);
    }

    /**
     * Sort the pictures in the list by their timestamp in ascending order.
     */
    public void sortByDate() {
        // Sort with Comparator and method reference for simplicity
        pictures.sort(Comparator.comparing(Picture::getTime));
    }

    /**
     * Read picture information from a file and populate the PictureList.
     * Each line in the file should contain picture information in a specific
     * format.
     *
     * @param infoFileName The name of the data file to read from.
     * @throws IllegalArgumentException If an error occurs while reading the file.
     */
    private void readFile(String infoFileName) throws IllegalArgumentException {
        Scanner input;

        try {
            input = new Scanner(new File(infoFileName));
        } catch (FileNotFoundException e) {
            System.err.println("Error has occurred while reading file.");
            return;
        }

        String line;
        int current = 0, success = 0;
        while (input.hasNext()) {
            try {
                line = input.nextLine().strip();
                current++;

                if (line.startsWith("//"))
                    continue; // Ignore comment lines

                // Remove the first and last '<' and '>' and split by '> <'
                // -1: Keep trailing empty strings
                String[] array = line.substring(1, line.length() - 1).split("> <", -1);
                if (array.length != 6)
                    throw new IllegalArgumentException("Array length is not 6.");

                String[] trimArray = new String[6];
                for (int i = 0; i < 6; i++)
                    trimArray[i] = array[i].trim();

                add(new Picture(trimArray));
                success++;
            } catch (Exception e) {
                System.err.printf("Error on line #%d: %s%n", current, e.getMessage());
            }
        }
        input.close();
        System.out.println("Successfully loaded " + success + " items.");
    }
}
