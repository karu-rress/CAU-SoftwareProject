public class ObjectArrayListLimitedCapacity {
    private Object[] array;     // 모든 타입(Object) 저장
    private int numberOfElems;  // 길이 (numberOfElems <= array.length)
    private static final int DEFAULT_CAPACITY = 8;

    public ObjectArrayListLimitedCapacity() { this(DEFAULT_CAPACITY); }
    public ObjectArrayListLimitedCapacity(int capacity) {
        array = new Object[capacity];
        numberOfElems = 0;
    }

    public int size() { return numberOfElems; }
    public boolean isEmpty() { return numberOfElems == 0; }

    public Object get(int index) {
        if (index < numberOfElems)
            return array[index];
        return null;
    }

    public void add(int index, Object o) {
        if (numberOfElems < array.length) { // 여유 공간이 있다면
            shift(index, numberOfElems++ - 1, true);
            array[index] = o;
        }
    }

    public void add(Object o) {
        if (numberOfElems < array.length) { // 여유 공간이 있다면
            array[numberOfElems++] = o;
        }
    }

    public Object remove(int index) {
        Object ret = array[index];
        shift(index + 1, --numberOfElems, false);
        array[numberOfElems] = null;
        return ret;
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
