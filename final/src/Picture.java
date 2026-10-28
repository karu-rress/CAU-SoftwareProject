/**
 *
 * Spring 2024
 * Software Project
 *
 * Picture.java
 *
 * Represents a picture, including its ID,
 * timestamp, image information, associated objects,
 * tags, and comment.
 *
 */

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Picture class represents a picture, including its ID,
 * timestamp, image information, associated objects, tags, and comment.
 */
public class Picture implements Printable, Serializable {
    private final String id; // Picture ID
    public final LocalDateTime time; // Timestamp
    private final Image imageInfo; // Image information
    private ArrayList<Stuff> stuffs; // Stuffs information
    private ArrayList<Tag> tags; // Tags
    private String comment; // Comments

    // These can be static since they are the same for all instances
    private static final String dateFormat = "yyyy-MM-dd_HH:mm:ss:SSS";
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

    /**
     * Construct a Picture object with the specified timestamp,
     * image file name, stuffs, tags, and comment.
     * 
     * @param time          The timestamp of the picture.
     * @param imageFileName The image file name.
     * @param stuffs        An ArrayList of Stuff objects associated with the
     *                      picture.
     * @param tags          An ArrayList of Tag objects associated with the picture.
     * @param comment       A comment for the picture.
     */
    public Picture(LocalDateTime time, String imageFileName,
            ArrayList<Stuff> stuffs, ArrayList<Tag> tags, String comment) {
        if (time == null)
            time = LocalDateTime.now();

        this.id = formatDate(time, "m_");
        this.time = time;
        this.imageInfo = new Image(formatDate(time, "IMG"), imageFileName);
        this.stuffs = stuffs;
        this.tags = tags;
        this.comment = comment;
    }

    // Getter
    public String getId() {
        return id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    /**
     * Get the timestamp of the picture as a formatted string.
     * The format is: yyyy-MM-dd_HH:mm:ss:SSS
     * 
     * @return The formatted timestamp string.
     */
    public String getTimeAsString() {
        return time.format(dateFormatter);
    }

    public Image getImageInfo() {
        return imageInfo;
    }

    public ArrayList<Stuff> getStuffs() {
        return stuffs;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    public String getTagsAsString() {
        return Tag.arrayToString(tags);
    }

    public String getComment() {
        return comment;
    }

    /**
     * Print the picture information to the console.
     */
    @Override
    public void print() {
        System.out.println(this);
    }

    /**
     * Return a string representation of the picture.
     * The fields are separated by angle brackets and spaces.
     * 
     * @return A string containing the picture's ID, timestamp,
     *         image information, stuffs, tags, and comment.
     */
    @Override
    public String toString() {
        return "< %s > < %s > < %s > < %s > < %s > < %s >".formatted(
                id, getTimeAsString(), imageInfo,
                Stuff.arrayToString(stuffs), Tag.arrayToString(tags), comment);
    }

    /**
     * Format a LocalDateTime object with a given prefix.
     * 
     * @param dt     The LocalDateTime object to format.
     * @param prefix The prefix to add to the formatted string.
     * @return The formatted string.
     */
    private static String formatDate(LocalDateTime dt, String prefix) {
        return dt.format(DateTimeFormatter.ofPattern("'%s'%s".formatted(prefix, dateFormat)));
    }
}
