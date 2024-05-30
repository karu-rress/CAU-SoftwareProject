import java.util.*;

public class StuffList {
    private static final ArrayList<Stuff> stuffs = new ArrayList<Stuff>();
    private StuffList() { } // static class: private construct (객체 생성 방지)

    public static void add(String type, String name, ArrayList<Tag> tags) {
        String id = getStuffId(type, name);
        if (id != null) {
            add(new Stuff(id, type, name, tags));
        }
        add(new Stuff(String.valueOf(stuffs.size()), type, name, tags));
    }
    public static void add(Stuff stuff) { stuffs.add(stuff); }

    public static Stuff get(int i) { return stuffs.get(i); }
    public static String getStuffId(String type, String name) {
        for (Stuff stuff : stuffs) {
            if (stuff.getType().equals(type) && stuff.getName().equals(name))
                return stuff.getId();
        }
        return null;
    }
    public static int length() { return stuffs.size(); }

    public static void print() {
        for (Stuff stuff : stuffs) {
            stuff.print();
        }
    }
}


// addIfNonExistent(String id, String type, String name)
// String newStuffID()
// boolean isExistingStuff(String id, String type, String name);
// -private addStuff(String id, String type, String name)
