
/**
 *
 * Spring 2024
 * Software Project
 *
 * Test.java
 *
 * Test class to demonstrate the functionality of
 * ObjectArrayListLimitedCapacity, ObjectArrayList, and MyArrayList.
 *
 */

import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        TestObjectArrayListLimitedCapacity();
        TestObjectArrayList();
        TestMyArrayListRaw();
        TestMyArrayList();

        String[] origin = { "Hello,", " Java!" };
        MyArrayList<String> sarray = new MyArrayList<String>(origin);
        System.out.println(sarray.get(0) + sarray.get(1));
    }

    private static void TestObjectArrayListLimitedCapacity() {
        System.out.println("####### ObjectArrayListLimitedCapacity Test");

        var array = new ObjectArrayListLimitedCapacity(5);

        array.add(1);
        array.add(2);
        array.add(3);
        array.add(1, "HI");
        array.add(0, '!');

        for (int i = 0; i < array.size(); i++)
            System.out.println("get(" + i + "): " + array.get(i));

        for (int i = array.size() - 1; i >= 0; i--)
            System.out.println(array.remove(i) + "\tremoved!");

        if (array.isEmpty())
            System.out.println("array is empty.\n");
    }

    private static void TestObjectArrayList() {
        System.out.println("####### ObjectArrayList Test");

        var array = new ObjectArrayList();
        for (int i = 1; i <= 100; i++)
            array.add(i);

        System.out.println(array.size() + " elements stored.");

        int sum = 0;
        for (int i = 0; i < 50; i++)
            sum += (int) array.get(i);
        System.out.println("Sum of first 50 elements: " + sum);

        array.add(0, "Hello, world!");
        array.add(1, 3.14159265);
        array.add(2, LocalDateTime.now());

        for (int i = 0; i < 3; i++)
            System.out.println("get(" + i + "): " + array.get(i));

        int removed = 0;
        for (int i = array.size() - 1; i >= 0; i--) {
            array.remove(i);
            removed++;
        }
        System.out.println(removed + " elements removed.");

        if (array.isEmpty())
            System.out.println("array is empty.\n");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static void TestMyArrayListRaw() {
        System.out.println("####### MyArrayList (RAW) Test");

        MyArrayList array = new MyArrayList();
        for (int i = 1; i <= 100; i++)
            array.add(i);

        System.out.println(array.size() + " elements stored.");

        int sum = 0;
        for (int i = 0; i < 50; i++)
            sum += (int) array.get(i);
        System.out.println("Sum of first 50 elements: " + sum);

        array.add(0, "Hello, world!");
        array.add(1, 3.14159265);
        array.add(2, LocalDateTime.now());

        for (int i = 0; i < 3; i++)
            System.out.println("get(" + i + "): " + array.get(i));

        int removed = 0;
        for (int i = array.size() - 1; i >= 0; i--) {
            array.remove(i);
            removed++;
        }
        System.out.println(removed + " elements removed.");

        if (array.isEmpty())
            System.out.println("array is empty.\n");
    }

    private static void TestMyArrayList() {
        System.out.println("####### MyArrayList<E> Test");

        MyArrayList<String> array = new MyArrayList<>();
        for (int i = 65; i <= 65 + 10; i++)
            array.add(Character.toString(i)); // ASCII to String

        System.out.println(array.size() + " elements stored.");

        String concat = new String();
        for (int i = 0; i < array.size(); i++)
            concat += array.get(i);
        System.out.println("Concatenated string: " + concat);

        array.add(0, "Hello, world!");
        // array.add(1, 3.14159265);
        // array.add(2, LocalDateTime.now()); // Type Error

        for (int i = 0; i < 3; i++)
            System.out.println("get(" + i + "): " + array.get(i));

        int removed = 0;
        for (int i = array.size() - 1; i >= 0; i--) {
            array.remove(i);
            removed++;
        }
        System.out.println(removed + " elements removed.");

        if (array.isEmpty())
            System.out.println("array is empty.\n");
    }
}
