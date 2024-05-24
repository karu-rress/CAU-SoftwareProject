public class MyArrayList<E> {
    private Object[] array;     // 모든 타입(Object) 저장
    private int numberOfElems;  // 길이 (numberOfElems <= array.length)
    private static final int DEFAULT_CAPACITY = 8;

    public MyArrayList() { this(DEFAULT_CAPACITY); }
    public MyArrayList(int capacity) {
        array = new Object[capacity];
        numberOfElems = 0;
    }
    public MyArrayList(E[] array) {
        numberOfElems = array.length;
        this.array = new Object[numberOfElems];
        System.arraycopy(array, 0, this.array, 0, numberOfElems);
    }

    public int size() { return numberOfElems; }
    public boolean isEmpty() { return numberOfElems == 0; }

    public E get(int index) {
        if (index < numberOfElems)
            return (E)array[index]; // E 타입만 저장되므로 문제 없음
        return null;
    }

    public void add(int index, E o) { // E 타입만 받음
        if (numberOfElems >= array.length)
            increaseCapacity();

        shift(index, numberOfElems++ - 1, true);
        array[index] = o;
    }

    public void add(E o) {            // E 타입만 받음
        if (numberOfElems >= array.length)
            increaseCapacity();

        array[numberOfElems++] = o;
    }

    public E remove(int index) {
        E ret = (E)array[index]; // E 타입만 저장되므로 문제 없음
        shift(index + 1, --numberOfElems, false);
        array[numberOfElems] = null;
        return ret;
    }

    private void increaseCapacity() {
        Object[] temp = new Object[array.length * 2];
        System.arraycopy(array, 0, temp, 0, numberOfElems);
        array = temp;
    }

    private void shift(int start, int end, boolean toRight) {
        if (toRight) {
            for (int i = end; i >= start; i--)
                array[i + 1] = array[i];
        }
        else {
            for (int i = start; i <= end; i++)
                array[i - 1] = array[i];
        }
    }
}