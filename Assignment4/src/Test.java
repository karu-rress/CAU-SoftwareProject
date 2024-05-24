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
        System.out.println("####### ObjectArrayListLimitedCapacity 테스트");

        ObjectArrayListLimitedCapacity array = new ObjectArrayListLimitedCapacity(5);

        array.add(1); // add() 테스트
        array.add(2);
        array.add(3);
        array.add(1, "HI"); // 인덱스를 지정해서도 테스트
        array.add(0, '!');

        for (int i = 0; i < array.size(); i++) // size, get 테스트
            System.out.println("get(" + i + "): " + array.get(i));

        for (int i = array.size() - 1; i >= 0; i--) // remove 테스트
            System.out.println(array.remove(i) + "\tremoved!");

        if (array.isEmpty()) // isEmpty 테스트
            System.out.println("array가 비어있습니다.\n");
    }

    private static void TestObjectArrayList() {
        System.out.println("####### ObjectArrayList 테스트");

        ObjectArrayList array = new ObjectArrayList();
        for (int i = 1; i <= 100; i++)
            array.add(i);

        System.out.println(array.size() + "개의 요소가 저장됨.");

        int sum = 0;
        for (int i = 0; i < 50; i++)
            sum += (int)array.get(i); // 캐스팅이 반드시 필요
        System.out.println("1부터 50까지의 합: " + sum);

        array.add(0, "Hello, world!"); // 다양한 타입 저장
        array.add(1, 3.14159265);
        array.add(2, LocalDateTime.now());

        for (int i = 0; i < 3; i++)
            System.out.println("get(" + i + "): " + array.get(i));

        int removed = 0;
        for (int i = array.size() - 1; i >= 0; i--) {
            array.remove(i);
            removed++;
        }
        System.out.println(removed + "개의 요소 삭제 완료.");

        if (array.isEmpty()) // isEmpty 테스트
            System.out.println("array가 비어있습니다.\n");
    }

    private static void TestMyArrayListRaw() {
        System.out.println("####### MyArrayList (RAW) 테스트");

        MyArrayList array = new MyArrayList();
        for (int i = 1; i <= 100; i++)
            array.add(i);

        System.out.println(array.size() + "개의 요소가 저장됨.");

        int sum = 0;
        for (int i = 0; i < 50; i++)
            sum += (int)array.get(i); // 캐스팅이 반드시 필요
        System.out.println("1부터 50까지의 합: " + sum);

        array.add(0, "Hello, world!"); // 다양한 타입 저장
        array.add(1, 3.14159265);
        array.add(2, LocalDateTime.now());

        for (int i = 0; i < 3; i++)
            System.out.println("get(" + i + "): " + array.get(i));

        int removed = 0;
        for (int i = array.size() - 1; i >= 0; i--) {
            array.remove(i);
            removed++;
        }
        System.out.println(removed + "개의 요소 삭제 완료.");

        if (array.isEmpty()) // isEmpty 테스트
            System.out.println("array가 비어있습니다.\n");
    }

    private static void TestMyArrayList() {
        System.out.println("####### MyArrayList<E> 테스트");

        MyArrayList<String> array = new MyArrayList<>();
        for (int i = 65; i <= 65 + 10; i++)
            array.add(Character.toString(i)); // ASCII to String

        System.out.println(array.size() + "개의 요소가 저장됨.");

        String concat = new String();
        for (int i = 0; i < array.size(); i++)
            concat += array.get(i); // 캐스팅이 반드시 필요
        System.out.println("연결된 문자열: " + concat);

        array.add(0, "Hello, world!"); // 다양한 타입 저장
        // array.add(1, 3.14159265);
        // array.add(2, LocalDateTime.now()); // Type Error

        for (int i = 0; i < 3; i++)
            System.out.println("get(" + i + "): " + array.get(i));

        int removed = 0;
        for (int i = array.size() - 1; i >= 0; i--) {
            array.remove(i);
            removed++;
        }
        System.out.println(removed + "개의 요소 삭제 완료.");

        if (array.isEmpty()) // isEmpty 테스트
            System.out.println("array가 비어있습니다.\n");
    }
}
