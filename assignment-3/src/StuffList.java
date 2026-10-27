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

import java.util.Objects;

public class StuffList {
    private static final Stuff[] stuffs = new Stuff[100];
    private static int count = 0;

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
    public static void add(String kind, String name, Tag[] tags) {
        String id = String.valueOf(count);
        for (int i = 0; i < count; i++) {
            if (Objects.equals(stuffs[i].getKind(), kind)
                    && Objects.equals(stuffs[i].getName(), name)) {
                id = stuffs[i].getId();
                break;
            }
        }
        add(new Stuff(id, kind, name, tags));
    }

    /**
     * Adds a Stuff object to the collection.
     * If the collection is full (100 objects), it does not add the new object.
     *
     * @param stuff The Stuff object to add.
     */
    public static void add(Stuff stuff) {
        if (count < 100) {
            stuffs[count++] = stuff;
        }
    }

    /**
     * Retrieves the Stuff object at the specified index.
     *
     * @param i The index of the Stuff object to retrieve.
     * @return The Stuff object at the specified index,
     *         or null if the index is out of bounds.
     */
    public static Stuff get(int i) {
        if (i >= 0 && i < count)
            return stuffs[i];

        return null;
    }

    /**
     * Returns the number of Stuff objects in the collection.
     *
     * @return The number of Stuff objects in the collection.
     */
    public static int length() {
        return count;
    }

    /**
     * Prints all Stuff objects in the collection to the console.
     */
    public static void print() {
        for (int i = 0; i < count; i++) {
            stuffs[i].print();
        }
    }
}
