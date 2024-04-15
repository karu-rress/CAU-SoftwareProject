public class Tag {
    private final String name;

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "#" + name;
    }

    public static Tag[] parse(String tagLine) {
        if (tagLine.isEmpty())
            return null;

        String[] tokens = tagLine.split(" ");
        Tag[] result = new Tag[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            result[i] = new Tag(tokens[i].strip().replaceFirst("#", ""));
        }
        return result;
    }

    public static String arrayToString(Tag[] tags) {
        String result = "";
        if (tags != null)
            for (Tag tag : tags)
                result += tag.toString() + " ";

        return result.strip();
    }
}
