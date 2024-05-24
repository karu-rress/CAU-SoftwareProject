import java.util.ArrayList;

public class Tag {
    private final String name;

    public Tag(String name) { this.name = name; }

    public String getName() { return name; }

    @Override
    public String toString() { return "#" + name; }

    public static ArrayList<Tag> parse(String tagLine) {
        if (tagLine.isEmpty())
            return null;

        String[] tokens = tagLine.split(" ");
        ArrayList<Tag> result = new ArrayList<Tag>();

        for (String token : tokens) {
            result.add(new Tag(token.strip().replaceFirst("#", "")));
        }
        return result;
    }

    public static String arrayToString(ArrayList<Tag> tags) {
        String result = "";
        if (tags != null)
            for (Tag tag : tags)
                result += tag.toString() + " ";

        return result.strip();
    }
}
