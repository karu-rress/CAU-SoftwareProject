import java.util.Objects;

public class StuffList {
    private static final Stuff[] stuffs = new Stuff[100];
    private static int count = 0;

    // static class: private construct (객체 생성 방지)
    private StuffList() { }

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
    public static void add(Stuff stuff) {
        if (count < 100) {
            stuffs[count++] = stuff;
        }
    }

    public static Stuff get(int i) {
        if (i >= 0 && i < count)
            return stuffs[i];

        return null;
    }

    public static int length() {
        return count;
    }

    public static void print() {
        for (int i = 0; i < count; i++) {
            stuffs[i].print();
        }
    }
}
