import java.util.*;

public class StuffList {
    private static final ArrayList<Stuff> stuffs = new ArrayList<Stuff>();
    private StuffList() { } // static class: private construct (객체 생성 방지)

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
    public static void add(Stuff stuff) { stuffs.add(stuff); }

    public static Stuff get(int i) { return stuffs.get(i); }

    public static int length() { return stuffs.size(); }

    public static void print() {
        for (Stuff stuff : stuffs) {
            stuff.print();
        }
    }
}
