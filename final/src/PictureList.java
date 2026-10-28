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
public class PictureList implements Iterable<Picture>, Printable, Serializable {
    private final ArrayList<Picture> pictures;

    public static final String DATA_FILE = "picture-normal-bin.data";

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

        PictureList result = new PictureList();
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
        PictureList result = new PictureList();
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
        PictureList result = new PictureList();
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
     * @return {@code true} if the named file does not exist and was successfully
     *         created; {@code false} if the named file already exists
     */
    public boolean saveToFile(String infoFileName) throws IOException {
        File file = new File(infoFileName);
        boolean result = file.createNewFile();

        try (var output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(pictures);
        }
        return result;
    }

    /**
     * Read picture information from a file and populate the PictureList.
     * Each line in the file should contain picture information in a specific
     * format.
     *
     * @param infoFileName The name of the data file to read from.
     */
    private void readFile(String infoFileName) {
        var file = new File(infoFileName);
        assert file.exists() : "File does not exist!";

        try (var input = new ObjectInputStream(new FileInputStream(file))) {
            for (Object item : (ArrayList<?>) input.readObject()) {
                pictures.add((Picture) item);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error has occurred while reading file.");
        }
    }

    /**
     * Check if a picture ID already exists.
     * 
     * @param pic Picture information
     * @return {@code true} if the ID exists, {@code false} otherwise
     */
    private boolean idExists(Picture pic) {
        for (Picture picture : pictures) {
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
        assert isSorted() : "Sorting failed!";
    }

    /**
     * Check if the pictures are sorted by date.
     * 
     * @return {@code true} if the pictures are sorted, {@code false} otherwise
     */
    private boolean isSorted() {
        for (int i = 1; i < pictures.size(); i++) {
            if (pictures.get(i - 1).getTime().isAfter(pictures.get(i).getTime()))
                return false;
        }
        return true;
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
        if (obj instanceof PictureList pl)
            return pictures.equals(pl.pictures);

        return false;
    }

    /**
     * Returns an iterator over the pictures in the list.
     *
     * @return An iterator over the pictures in the list.
     */
    @Override
    public Iterator<Picture> iterator() {
        return pictures.iterator();
    }

    /**
     * Prints the details of all pictures in the list to the console.
     */
    @Override
    public void print() {
        for (Picture picture : pictures)
            picture.print();
    }
}
