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
import java.time.LocalDateTime;
import java.util.*;

/**
 * Manages a collection of pictures using an ArrayList.
 */
public class PictureList {
    private final ArrayList<Picture> pictures;

    public static final String DATA_FILE = "picture-normal.data";

    public enum FilterMethod {
        ALL, ANY
    }

    /**
     * Default constructor that initializes an empty PictureList.
     */
    public PictureList() {
        pictures = new ArrayList<>();
    }

    /**
     * Copy constructor that creates a new PictureList
     * by copying the contents of another PictureList.
     *
     * @param rhs The PictureList to copy from.
     */
    public PictureList(PictureList rhs) {
        pictures = new ArrayList<>(rhs.pictures);
    }

    /**
     * Constructor that initializes the PictureList
     * by reading from the specified data file.
     *
     * @param infoFileName The name of the data file containing picture information.
     */
    public PictureList(String infoFileName) {
        pictures = new ArrayList<>();
        readFile(infoFileName);
        sortByDate();
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
     * Check if the picture list is empty.
     *
     * @return {@code true} if the list is empty, {@code false} otherwise.
     */
    public boolean isEmpty() {
        return pictures.isEmpty();
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
        if (idExists(pic))
            throw new IllegalArgumentException(pic.getId() + ": ID already exists!");

        pictures.add(pic);
        sortByDate();
    }

    /**
     * Add all pictures from another PictureList to this list.
     * If a picture with the same ID already exists, it will not be added.
     * 
     * @param list The PictureList to add pictures from.
     */
    public void add(PictureList list) {
        for (Picture pic : list.getPictures()) {
            if (!idExists(pic))
                pictures.add(pic);
        }
        sortByDate();
    }

    /**
     * Remove a picture from the list.
     *
     * @param pic The Picture object to remove from the list.
     */
    public void remove(Picture pic) {
        pictures.remove(pic);
    }

    /**
     * Remove a picture at the specified index from the list.
     *
     * @param index The index of the picture to remove.
     */
    public void remove(int index) {
        pictures.remove(index);
    }

    /**
     * Filter pictures in the list based on a date range.
     * 
     * @param from Start date. If {@code null}, the earliest date is used.
     * @param to   End date. If {@code null}, the latest date is used.
     * @return Filtered picture list.
     */
    public PictureList filterByDate(LocalDateTime from, LocalDateTime to) {
        if (from == null)
            from = LocalDateTime.MIN;
        if (to == null)
            to = LocalDateTime.MAX;

        var result = new PictureList();
        for (Picture picture : pictures) {
            if (picture.getTime().isAfter(from) && picture.getTime().isBefore(to) ||
                    picture.getTime().isEqual(from) || picture.getTime().isEqual(to))
                result.add(picture);
        }
        return result;
    }

    /**
     * Filter pictures in the list based on image tags.
     * 
     * @param tags   The list of image tags to filter by.
     * @param method The filtering method (ALL, ANY).
     * @return Filtered picture list.
     */
    public PictureList filterByImageTags(ArrayList<Tag> tags, FilterMethod method) {
        var result = new PictureList();
        for (Picture picture : pictures) {
            if (method == FilterMethod.ALL) {
                if (picture.getTags().containsAll(tags)) // Contains all tags?
                    result.add(picture);
            } else if (method == FilterMethod.ANY) {
                if (!Collections.disjoint(picture.getTags(), tags)) // Contains any tag?
                    result.add(picture);
            }
        }
        return result;
    }

    /**
     * Filter pictures in the list based on comments.
     * 
     * @param comment The comment to filter by.
     * @return Filtered picture list.
     */
    public PictureList filterByComment(String comment) {
        var result = new PictureList();
        for (Picture picture : pictures) {
            if (picture.getComment().contains(comment))
                result.add(picture);
        }
        return result;
    }

    /**
     * Filter pictures in the list based on stuff type.
     * 
     * @param type The stuff type to filter by.
     * @return Filtered picture list.
     */
    public PictureList filterByStuffType(String type) {
        var result = new PictureList();
        for (Picture picture : pictures) {
            for (Stuff stuff : picture.getStuffs()) {
                if (stuff.getType().contains(type)) {
                    result.add(picture);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Filter pictures in the list based on stuff name.
     * 
     * @param name The stuff name to filter by.
     * @return Filtered picture list.
     */
    public PictureList filterByStuffName(String name) {
        var result = new PictureList();
        for (Picture picture : pictures) {
            for (Stuff stuff : picture.getStuffs()) {
                if (stuff.getName().contains(name)) {
                    result.add(picture);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Filter pictures in the list based on stuff tags.
     * 
     * @param tags   The list of stuff tags to filter by.
     * @param method The filtering method (ALL, ANY).
     * @return Filtered picture list.
     */
    public PictureList filterByStuffTags(ArrayList<Tag> tags, FilterMethod method) {
        var result = new PictureList();
        for (Picture picture : pictures) {
            for (Stuff stuff : picture.getStuffs()) {
                if (method == FilterMethod.ALL) {
                    if (stuff.getTags().containsAll(tags)) {
                        result.add(picture);
                        break;
                    }
                } else if (method == FilterMethod.ANY) {
                    if (!Collections.disjoint(stuff.getTags(), tags)) {
                        result.add(picture);
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * Save the picture information to a file.
     * 
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void saveToFile() throws IOException {
        saveToFile(DATA_FILE);
    }

    /**
     * Save the picture information to a file.
     *
     * @param infoFileName The name of the data file to write to.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void saveToFile(String infoFileName) throws IOException {
        var file = new File(infoFileName);
        if (!file.exists())
            file.createNewFile();

        var output = new PrintWriter(file);
        for (Picture picture : pictures)
            output.println(picture);

        output.close();
    }

    /**
     * Read picture information from a file and populate the PictureList.
     * Each line in the file should contain picture information in a specific
     * format.
     *
     * @param infoFileName The name of the data file to read from.
     */
    private void readFile(String infoFileName) {
        try (Scanner input = new Scanner(new File(infoFileName))) {
            int current = 0, success = 0;
            while (input.hasNext()) {
                try {
                    String line = input.nextLine().strip();
                    current++;

                    if (!line.startsWith("//")) {
                        String[] array = processLine(line);
                        add(new Picture(trimArrayElements(array)));
                        success++;
                    }
                } catch (Exception e) {
                    System.err.printf("Error on line #%d: %s%n", current, e.getMessage());
                }
            }
            System.out.printf("Successfully loaded %d items.%n", success);
        } catch (FileNotFoundException e) {
            System.err.println("Error has occurred while reading file.");
        }
    }

    /**
     * Process a line of picture information
     * 
     * @param line A line of picture information
     * @return An array of strings containing the picture information
     * @throws IllegalArgumentException If the line is not in the expected format
     */
    private String[] processLine(String line) throws IllegalArgumentException {
        String[] array = line.substring(1, line.length() - 1).split("> <", -1);
        if (array.length != 6)
            throw new IllegalArgumentException("Array length is not 6.");

        return array;
    }

    /**
     * Trim whitespace from each element of an array of strings.
     * 
     * @param array Parsed array of strings
     * @return An array of strings with leading and trailing whitespace removed
     */
    private String[] trimArrayElements(String[] array) {
        String[] trimArray = new String[array.length];
        for (int i = 0; i < array.length; i++)
            trimArray[i] = array[i].trim();

        return trimArray;
    }

    /**
     * Check if a picture ID already exists.
     * 
     * @param pic Picture information
     * @return {@code true} if the ID exists, {@code false} otherwise
     */
    private boolean idExists(Picture pic) {
        for (var picture : pictures) {
            if (picture.getId().equals(pic.getId()))
                return true;
        }
        return false;
    }

    /**
     * Sort the pictures by date.
     */
    private void sortByDate() {
        pictures.sort(Comparator.comparing(Picture::getTime));
    }

    /**
     * Check if this PictureList is equal to another object.
     * 
     * @param obj The object to compare with.
     * @return {@code true} if the other object is a PictureList with the same
     *         pictures, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PictureList rhs)
            return pictures.equals(rhs.pictures);

        return false;

    }
}
