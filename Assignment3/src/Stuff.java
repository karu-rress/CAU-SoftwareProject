public class Stuff {
    private final String id;
    private final String kind;
    private final String name;
    private final Tag[] tags;

    public String getId() {
        return id;
    }
    public String getKind() {
        return kind;
    }
    public String getName() {
        return name;
    }
    public Tag[] getTags() {
        return tags;
    }
    public Stuff(String id, String kind, String name, Tag[] tags) {
        // int 로 변환할 수 없는 값이 있다면 입력값에 오류가 있는 것
        try {
            Integer.parseInt(id);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("id: " + id);
        }

        this.id = id;
        this.kind = kind;
        this.name = name;
        this.tags = tags;
    }

    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return id + "; " + kind + "; " + name + "; " + Tag.arrayToString(tags);
    }

    public static Stuff[] parse(String stuffLine) throws IllegalArgumentException {
        if (stuffLine.isEmpty())
            return null;

        String line = stuffLine.strip();

        // 대괄호 제거 및 '] ['로 문자열 파싱
        String[] stuffs = line.substring(1, line.length() - 1).strip().split("] \\[", -1);

        Stuff[] result = new Stuff[stuffs.length];
        for (int i = 0; i < stuffs.length; i++) {
            String[] stuffInfo = stuffs[i].split(";", -1);
            result[i] = new Stuff(stuffInfo[0].strip(), stuffInfo[1].strip(), stuffInfo[2].strip(), Tag.parse(stuffInfo[3].strip()));
        }
        return result;
    }

    public static String arrayToString(Stuff[] stuffs) {
        String result = "";
        for (Stuff stuff : stuffs) {
            result += "[" + stuff.toString() + "] ";
        }
        return result.trim();
    }
}
