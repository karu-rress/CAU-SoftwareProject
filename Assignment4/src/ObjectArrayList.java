public class ObjectArrayList extends ObjectArrayListLimitedCapacityIn {
    public ObjectArrayList() {
        super();
    }
    public ObjectArrayList(int capacity) {
        super(capacity);
    }

    @Override // 관례상 오버라이드 메소드에는 @Override annotation
    public void add(int index, Object o) {
        if (numberOfElems >= array.length)
            increaseCapacity();

        shift(index, numberOfElems++ - 1, true);
        array[index] = o;
    }

    @Override
    public void add(Object o) {
        if (numberOfElems >= array.length)
            increaseCapacity();

        array[numberOfElems++] = o;
    }

    private void increaseCapacity() {
        Object[] temp = new Object[array.length * 2];
        System.arraycopy(array, 0, temp, 0, numberOfElems);
        array = temp;
    }
}


class ObjectArrayListLimitedCapacityIn {
    protected Object[] array;     // 모든 타입(Object) 저장
    protected int numberOfElems;  // 길이 (numberOfElems <= array.length)
    private static final int DEFAULT_CAPACITY = 8;

    public ObjectArrayListLimitedCapacityIn() {
        this(DEFAULT_CAPACITY);
    }
    public ObjectArrayListLimitedCapacityIn(int capacity) {
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

    protected void shift(int start, int end, boolean toRight) {
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