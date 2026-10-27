/**
 *
 * Spring 2024
 * Software Project
 *
 * ObjectArrayListLimitedCapacity.java
 *
 * A limited capacity version of ObjectArrayList
 * that does not resize when full.
 *
 */

public class ObjectArrayListLimitedCapacity {
    private Object[] array;
    private int numberOfElems; // Real length (numberOfElems <= array.length)
    private static final int DEFAULT_CAPACITY = 8;

    public ObjectArrayListLimitedCapacity() {
        this(DEFAULT_CAPACITY);
    }

    public ObjectArrayListLimitedCapacity(int capacity) {
        array = new Object[capacity];
        numberOfElems = 0;
    }

    /**
     * Get the number of elements in the list.
     * @return The number of elements in the list.
     */
    public int size() {
        return numberOfElems;
    }

    /**
     * Check if the list is empty.
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return numberOfElems == 0;
    }

    /**
     * Get the element at the specified index.
     * @param index The index of the element to retrieve.
     * @return The element at the specified index, or null if the index is out of bounds.
     */
    public Object get(int index) {
        if (index < numberOfElems)
            return array[index];
        return null;
    }

    /**
     * Add an element at the specified index.
     * If the list is full, the element will not be added.
     * @param index The index at which to add the element.
     * @param o The element to add.
     */
    public void add(int index, Object o) {
        if (numberOfElems < array.length) { // Check for overflow
            shift(index, numberOfElems++ - 1, true);
            array[index] = o;
        }
    }

    /**
     * Add an element to the end of the list.
     * If the list is full, the element will not be added.
     * @param o The element to add.
     */
    public void add(Object o) {
        if (numberOfElems < array.length) { // Check for overflow
            array[numberOfElems++] = o;
        }
    }

    /**
     * Remove the element at the specified index.
     * @param index The index of the element to remove.
     * @return The removed element.
     */
    public Object remove(int index) {
        Object ret = array[index];
        shift(index + 1, --numberOfElems, false);
        array[numberOfElems] = null;
        return ret;
    }

    /**
     * Shift elements in the array to the right or left.
     * @param start The starting index for the shift.
     * @param end The ending index for the shift.
     * @param toRight true to shift to the right, false to shift to the left.
     */
    private void shift(int start, int end, boolean toRight) {
        if (toRight) {
            for (int i = end; i >= start; i--)
                array[i + 1] = array[i];
        } else {
            for (int i = start; i <= end; i++)
                array[i - 1] = array[i];
        }
    }
}
