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

import java.util.*;

public class StuffList {
    private static final ArrayList<Stuff> stuffs = new ArrayList<Stuff>();

    // static class: private construct (Singleton pattern)
    private StuffList() {
    }

    /**
     * Adds a new Stuff object to the collection.
     * If an object with the same kind and name already exists,
     * it reuses the existing ID; otherwise, it generates a new ID.
     *
     * @param kind The kind of the stuff.
     * @param name The name of the stuff.
     * @param tags The tags associated with the stuff.
     */
    public static void add(String kind, String name, ArrayList<Tag> tags) {
        String id = String.valueOf(stuffs.size());
        for (Stuff stuff : stuffs) {
            if (Objects.equals(stuff.getType(), kind)
                    && Objects.equals(stuff.getName(), name)) {
                id = stuff.getId();
                break;
            }
        }
        add(new Stuff(id, kind, name, tags));
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
     * Retrieves the Stuff object at the specified index.
     *
     * @param i The index of the Stuff object to retrieve.
     * @return The Stuff object at the specified index.
     */
    public static Stuff get(int i) {
        return stuffs.get(i);
    }

    /**
     * Returns the number of Stuff objects in the collection.
     *
     * @return The number of Stuff objects in the collection.
     */
    public static int length() {
        return stuffs.size();
    }

    /**
     * Prints all Stuff objects in the collection to the console.
     */
    public static void print() {
        for (Stuff stuff : stuffs) {
            stuff.print();
        }
    }
}
