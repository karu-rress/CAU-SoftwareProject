/**
 *
 * Spring 2024
 * Software Project
 *
 * Tag.java
 *
 * Represents an individual tag and provides methods
 * for parsing and formatting tag collections.
 *
 */

import java.util.ArrayList;

public class Tag {
    private final String name;

    public Tag(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the tag.
     *
     * @return The name of the tag.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the tag with a '#' prefix.
     *
     * @return A string representation of the tag.
     */
    @Override
    public String toString() {
        return "#" + name;
    }

    /**
     * Parses a string of tags and returns an array of Tag objects.
     * The input string should contain tags separated by spaces.
     *
     * @param tagLine The string containing tags to parse.
     * @return An array of Tag objects parsed from the input string,
     *         or null if the input string is empty.
     */
    public static ArrayList<Tag> parse(String tagLine) {
        if (tagLine.isEmpty())
            return null;

        String[] tokens = tagLine.split(" ");
        ArrayList<Tag> result = new ArrayList<Tag>();

        for (String token : tokens) {
            result.add(new Tag(token.strip().replaceFirst("#", "")));
        }
        return result;
    }

    /**
     * Converts Tag[] to a String representation.
     * Each tag is prefixed with a '#' symbol and separated by spaces.
     *
     * @param tags The array of Tag objects to convert.
     * @return A string representation of the array of Tag objects,
     *         or an empty string if the input array is null.
     */
    public static String arrayToString(ArrayList<Tag> tags) {
        String result = "";
        if (tags != null)
            for (Tag tag : tags)
                result += tag.toString() + " ";

        return result.strip();
    }
}
