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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class representing an object appearing in a picture
 */
public class Stuff implements Printable, Serializable {
    private final String id;
    private final String type;
    private final String name;
    private final ArrayList<Tag> tags;

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
        String id = StuffList.searchStuffId(type, name); // 이미 존재하는 사물이면 동일한 ID
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
    @Override
    public void print() {
        System.out.println(this);
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
