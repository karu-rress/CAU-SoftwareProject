/**
 *
 * Spring 2024
 * Software Project
 *
 * StuffList.java
 *
 * Maintains a shared collection of object records
 * using static fields and methods.
 *
 */

import java.util.ArrayList;

/**
 * A database of Stuff objects
 */
public class StuffList {
    private static final ArrayList<Stuff> stuffs = new ArrayList<>();

    // static class: private construct (Singleton pattern)
    private StuffList() {
    }

    /**
     * Adds a new Stuff object to the collection.
     *
     * @param type The type of the stuff.
     * @param name The name of the stuff.
     * @param tags The tags associated with the stuff.
     */
    public static void add(String type, String name, ArrayList<Tag> tags) {
        String id = searchStuffId(type, name);
        add(new Stuff(id != null ? id : newStuffId(), type, name, tags));
    }

    /**
     * Adds a Stuff object to the collection.
     *
     * @param stuff The Stuff object to add.
     */
    public static void add(Stuff stuff) {
        stuffs.add(stuff);
    }

    /**
     * Adds a list of Stuff objects to the collection.
     * 
     * @param stuffs A list of Stuff objects to add.
     */
    public static void add(ArrayList<Stuff> stuffs) {
        StuffList.stuffs.addAll(stuffs);
    }

    /**
     * Retrieves the Stuff object at the specified index.
     *
     * @param i The index of the Stuff object to retrieve.
     * @return The Stuff object at the specified index or
     *         {@code null} if the index is out of bounds.
     */
    public static Stuff get(int index) {
        return (index >= 0 && index < stuffs.size()) ? stuffs.get(index) : null;
    }

    /**
     * Retrieves the Stuff object with the specified ID.
     *
     * @param id The ID of the Stuff object to retrieve.
     * @return The Stuff object with the specified ID or
     *         {@code null} if no such object exists.
     */
    public static Stuff get(String id) {
        for (Stuff stuff : stuffs) {
            if (stuff.getId().equals(id))
                return stuff;
        }
        return null;
    }

    /**
     * Searches for a Stuff object by its type and name.
     *
     * @param type The type of the Stuff object to search for.
     * @param name The name of the Stuff object to search for.
     * @return The ID of the Stuff object if found, or {@code null} if not found.
     */
    public static String searchStuffId(String type, String name) {
        for (Stuff stuff : stuffs) {
            if (stuff.getType().equals(type) && stuff.getName().equals(name))
                return stuff.getId();
        }
        return null;
    }

    /**
     * Creates a new unique ID for a Stuff object.
     * 
     * @return A new unique ID as a String.
     */
    public static String newStuffId() {
        return String.valueOf(stuffs.size());
    }

    /**
     * Returns the number of Stuff objects in the collection.
     *
     * @return The number of Stuff objects in the collection.
     */
    public static int size() {
        return stuffs.size();
    }

    /**
     * Prints all Stuff objects in the collection to the console.
     */
    public static void print() {
        for (Stuff stuff : stuffs)
            stuff.print();
    }
}
