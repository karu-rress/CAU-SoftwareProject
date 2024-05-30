import java.util.ArrayList;

public class Stuff {
    private final String id;
    private final String type;
    private final String name;
    private final ArrayList<Tag> tags;

    public String getId() { return id; }
    public String getType() { return type; }
    public String getName() { return name; }
    public ArrayList<Tag> getTags() { return tags; }
    public Stuff(String id, String type, String name, ArrayList<Tag> tags) {
        try {
            Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("id: " + id);
        }

        this.id = id;
        this.type = type;
        this.name = name;
        this.tags = tags;
    }

    public void print() { System.out.println(this); }

    @Override
    public String toString() {
        return id + "; " + type + "; " + name + "; " + Tag.arrayToString(tags);
    }

    public static ArrayList<Stuff> parse(String stuffLine) throws IllegalArgumentException {
        if (stuffLine.isEmpty())
            return new ArrayList<Stuff>();

        String line = stuffLine.strip();

        // 대괄호 제거 및 '] ['로 문자열 파싱
        String[] stuffs = line.substring(1, line.length() - 1).strip().split("] \\[", -1);

        ArrayList<Stuff> result = new ArrayList<Stuff>();
        for (String stuff : stuffs) {
            String[] stuffInfo = stuff.split(";", -1);
            result.add(new Stuff(stuffInfo[0].strip(), stuffInfo[1].strip(), stuffInfo[2].strip(), Tag.parse(stuffInfo[3].strip())));
        }
        return result;
    }

    public static String arrayToString(ArrayList<Stuff> stuffs) {
        String result = "";
        for (Stuff stuff : stuffs) {
            result += "[" + stuff.toString() + "] ";
        }
        return result.trim();
    }
}
