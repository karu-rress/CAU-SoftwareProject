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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A record representing a tag, which is a keyword
 * or label used to categorize or describe an item.
 *
 * @param name The name of the tag.
 */
public record Tag(String name) implements Serializable {

    /**
     * Parses a string of tags and returns an array of Tag objects.
     * The input string should contain tags separated by spaces.
     *
     * @param tagLine The string containing tags to parse.
     * @return An array of Tag objects parsed from the input string.
     * @throws IllegalArgumentException if the input string is null
     */
    public static ArrayList<Tag> parse(String tagLine) throws IllegalArgumentException {
        if (tagLine == null)
            throw new IllegalArgumentException("tagLine is null");

        var result = new ArrayList<Tag>();
        if (!tagLine.isEmpty()) {
            for (String token : tagLine.split(" "))
                result.add(new Tag(token.strip().substring(1)));
        }
        return result;
    }

    /**
     * Converts Tag[] to a String representation.
     * Each tag is prefixed with a '#' symbol and separated by spaces.
     *
     * @param tags The array of Tag objects to convert.
     * @return A string representation of the array of Tag objects.
     * @throws IllegalArgumentException if the input array is null
     */
    public static String arrayToString(ArrayList<Tag> tags) throws IllegalArgumentException {
        if (tags == null)
            throw new IllegalArgumentException("tags is null");

        StringBuilder result = new StringBuilder();
        for (Tag tag : tags)
            result.append(tag.toString() + " ");

        return result.toString().strip();
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
     * Checks if this Tag is equal to another object.
     * Two Tag objects are considered equal if they have the same name.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the other object is a Tag with the same name,
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tag tag)
            return name.equals(tag.name);

        return false;
    }
}
