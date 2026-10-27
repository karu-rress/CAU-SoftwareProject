/**
 *
 * Spring 2024
 * Software Project
 *
 * MyArrayList.java
 *
 * A generic ArrayList implementation
 * that can store elements of any type.
 *
 */

public class MyArrayList<E> {
    private Object[] array;
    private int numberOfElems;
    private static final int DEFAULT_CAPACITY = 8;

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Construct a MyArrayList with the specified initial capacity.
     * 
     * @param capacity The initial capacity of the MyArrayList.
     */
    public MyArrayList(int capacity) {
        array = new Object[capacity];
        numberOfElems = 0;
    }

    /**
     * Construct a MyArrayList from an existing array.
     * 
     * @param <E>   The type of elements in the array.
     * @param array The array to initialize the MyArrayList with.
     */
    public MyArrayList(E[] array) {
        numberOfElems = array.length;
        this.array = new Object[numberOfElems];
        System.arraycopy(array, 0, this.array, 0, numberOfElems);
    }

    /**
     * Get the number of elements in the list.
     * 
     * @return The number of elements in the list.
     */
    public int size() {
        return numberOfElems;
    }

    /**
     * Check if the list is empty.
     * 
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return numberOfElems == 0;
    }

    /**
     * Get the element at the specified index.
     * 
     * @param index The index of the element to retrieve.
     * @return The element at the specified index, or null if the index is out of
     *         bounds.
     */
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < numberOfElems)
            return (E) array[index]; // OK, since only E type is stored
        return null;
    }

    /**
     * Add an element at the specified index.
     * If the list is full, the capacity will be increased.
     * 
     * @param index The index at which to add the element.
     * @param o     The element to add.
     */
    public void add(int index, E o) {
        if (numberOfElems >= array.length)
            increaseCapacity();

        shift(index, numberOfElems++ - 1, true);
        array[index] = o;
    }

    /**
     * Add an element to the end of the list.
     * If the list is full, the capacity will be increased.
     * 
     * @param o The element to add.
     */
    public void add(E o) {
        if (numberOfElems >= array.length)
            increaseCapacity();

        array[numberOfElems++] = o;
    }

    /**
     * Remove the element at the specified index.
     * 
     * @param index The index of the element to remove.
     * @return The removed element, or null if the index is out of bounds.
     */
    public E remove(int index) {
        @SuppressWarnings("unchecked")
        E ret = (E) array[index];
        shift(index + 1, --numberOfElems, false);
        array[numberOfElems] = null;
        return ret;
    }

    /**
     * Increase the capacity of the array by doubling its size.
     */
    private void increaseCapacity() {
        Object[] temp = new Object[array.length * 2];
        System.arraycopy(array, 0, temp, 0, numberOfElems);
        array = temp;
    }

    /**
     * Shift elements in the array to the right or left.
     * 
     * @param start   The starting index for the shift.
     * @param end     The ending index for the shift.
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
