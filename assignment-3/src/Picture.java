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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Picture {
    private final String id; // Picture ID
    public final LocalDateTime time; // Timestamp
    private final Image imageInfo; // Image information
    private Stuff[] stuffs; // Stuffs information
    private Tag[] tags; // Tags
    private String comment; // Comments

    private final String dateFormat = "yyyy-MM-dd_HH:mm:ss:SSS";

    /**
     * Format a LocalDateTime object with a given prefix.
     * 
     * @param dt     The LocalDateTime object to format.
     * @param prefix The prefix to add to the formatted string.
     * @return The formatted string.
     */
    private String formatDate(LocalDateTime dt, String prefix) {
        var formatter = DateTimeFormatter.ofPattern("'%s'%s".formatted(prefix, dateFormat));
        return dt.format(formatter);
    }

    /**
     * Get the ID of the picture.
     * 
     * @return The ID of the picture.
     */
    public String getId() {
        return id;
    }

    /**
     * Construct a Picture object with the current
     * timestamp and a given image file name.
     * 
     * @param imageFileName: Image file name
     */
    public Picture(String imageFileName) {
        var dt = LocalDateTime.now();

        id = formatDate(dt, "m_");
        time = dt;
        imageInfo = new Image(formatDate(dt, "IMG"), imageFileName);
    }

    /**
     * Construct a Picture object from an array of information fields.
     * The fields should contain the ID, timestamp, image information,
     * stuffs, tags, and comment, in that order.
     * 
     * @param infoFields An array of strings containing the picture information.
     */
    public Picture(String[] infoFields) {
        id = infoFields[0];
        time = LocalDateTime.parse(infoFields[1], DateTimeFormatter.ofPattern(dateFormat));
        imageInfo = new Image(infoFields[2]);
        stuffs = Stuff.parse(infoFields[3]);
        tags = Tag.parse(infoFields[4]);
        comment = infoFields[5];

        for (Stuff stuff : stuffs)
            StuffList.add(stuff);
    }

    /**
     * Get the timestamp of the picture as a formatted string.
     * 
     * @return The formatted timestamp string.
     */
    public String getTimeAsString() {
        return time.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    /**
     * Print the picture information to the console.
     */
    public void print() {
        System.out.println(toString());
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
}
