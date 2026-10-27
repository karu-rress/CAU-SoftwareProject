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

public class Stuff {
    private final String id;
    private final String kind;
    private final String name;
    private final Tag[] tags;

    public String getId() {
        return id;
    }

    public String getKind() {
        return kind;
    }

    public String getName() {
        return name;
    }

    public Tag[] getTags() {
        return tags;
    }

    /**
     * Creates a new Stuff object.
     *
     * @param id   The ID of the stuff.
     * @param kind The kind of the stuff.
     * @param name The name of the stuff.
     * @param tags The tags associated with the stuff.
     */
    public Stuff(String id, String kind, String name, Tag[] tags) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("id: " + id);
        }

        this.id = id;
        this.kind = kind;
        this.name = name;
        this.tags = tags;
    }

    /**
     * Prints the string representation of the Stuff object to the console.
     */
    public void print() {
        System.out.println(toString());
    }

    /**
     * Returns the string representation of the Stuff object.
     *
     * @return The string representation of the Stuff object.
     */
    @Override
    public String toString() {
        return "%s; %s; %s; %s".formatted(id, kind, name, Tag.arrayToString(tags));
    }

    /**
     * Parses a string representation of Stuff objects and returns an array of
     * Stuff.
     *
     * @param stuffLine The string representation of Stuff objects.
     * @return An array of Stuff objects parsed from the input string.
     * @throws IllegalArgumentException If the input string is invalid or cannot be
     *                                  parsed.
     */
    public static Stuff[] parse(String stuffLine) throws IllegalArgumentException {
        if (stuffLine.isEmpty())
            return null;

        String line = stuffLine.strip();

        // Remove brackets and split by '] [' to parse the string
        String[] stuffs = line.substring(1, line.length() - 1).strip().split("] \\[", -1);

        Stuff[] result = new Stuff[stuffs.length];
        for (int i = 0; i < stuffs.length; i++) {
            String[] stuffInfo = stuffs[i].split(";", -1);
            result[i] = new Stuff(stuffInfo[0].strip(), stuffInfo[1].strip(), stuffInfo[2].strip(),
                    Tag.parse(stuffInfo[3].strip()));
        }
        return result;
    }

    /**
     * Converts an array of Stuff objects to a string representation.
     *
     * @param stuffs The array of Stuff objects to convert.
     * @return A string representation of the array of Stuff objects.
     */
    public static String arrayToString(Stuff[] stuffs) {
        String result = "";
        for (Stuff stuff : stuffs) {
            result += "[" + stuff.toString() + "] ";
        }
        return result.trim();
    }
}
