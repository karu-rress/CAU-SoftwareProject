/**
 *
 * Spring 2024
 * Software Project
 *
 * Stuff.java
 *
 * Represents an object appearing in a picture,
 * including its ID, kind, name, and tags.
 *
 */

import java.util.ArrayList;

/**
 * A class representing an object appearing in a picture
 */
public class Stuff {
    private final String id;
    private final String type;
    private final String name;
    private final ArrayList<Tag> tags;

    /**
     * Constructs a Stuff object from an array of strings.
     *
     * @param stuff An array of strings containing the stuff details.
     */
    public Stuff(String[] stuff) {
        this(stuff[0].strip(), stuff[1].strip(), stuff[2].strip(), Tag.parse(stuff[3].strip()));
    }

    /**
     * Creates a new Stuff object.
     *
     * @param id   The ID of the stuff.
     * @param kind The kind of the stuff.
     * @param name The name of the stuff.
     * @param tags The tags associated with the stuff.
     */
    public Stuff(String id, String type, String name, ArrayList<Tag> tags) throws IllegalArgumentException {
        if (!id.matches("\\d+")) // Check if the ID is a valid integer
            throw new IllegalArgumentException("ID format is wrong. ID: " + id);

        this.id = id;
        this.type = type;
        this.name = name;
        this.tags = tags;
    }

    /**
     * Creates a new Stuff object with a new unique ID.
     *
     * @param type The type of the stuff.
     * @param name The name of the stuff.
     * @param tags The tags associated with the stuff.
     */
    public Stuff(String type, String name, ArrayList<Tag> tags) {
        String id = StuffList.searchStuffId(type, name);
        if (id == null)
            id = StuffList.newStuffId();

        this.id = id;
        this.type = type;
        this.name = name;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * Prints the string representation of the Stuff object to the console.
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * Parses a string representation of Stuff objects and returns an array of
     * Stuff.
     *
     * @param stuffLine The string representation of Stuff objects.
     * @return An array of Stuff objects parsed from the input string.
     */
    public static ArrayList<Stuff> parse(String stuffLine) {
        ArrayList<Stuff> result = new ArrayList<>();

        if (stuffLine.isEmpty())
            return result;

        String line = stuffLine.strip();
        line = line.substring(1, line.length() - 1).strip();

        // Remove brackets and split by '] [' to parse the string
        for (String stuff : line.split("] \\[", -1))
            result.add(new Stuff(stuff.split(";", -1)));

        return result;
    }

    /**
     * Converts an array of Stuff objects to a string representation.
     *
     * @param stuffs The array of Stuff objects to convert.
     * @return A string representation of the array of Stuff objects.
     */
    public static String arrayToString(ArrayList<Stuff> stuffs) {
        var result = new StringBuilder();
        for (Stuff stuff : stuffs)
            result.append("[%s] ".formatted(stuff.toString()));

        return result.toString().trim();
    }

    /**
     * Returns the string representation of the Stuff object.
     *
     * @return The string representation of the Stuff object.
     */
    @Override
    public String toString() {
        return "%s; %s; %s; %s".formatted(id, type, name, Tag.arrayToString(tags));
    }
}
