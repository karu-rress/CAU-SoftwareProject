import java.util.ArrayList;

/**
 * 사물 정보를 담는 클래스
 */
public class Stuff implements Printable {
    // 한 번 생성된 Stuff 객체는 수정되지 않으므로, final로 선언
    private final String           id;
    private final String           type;
    private final String           name;
    private final ArrayList<Tag>   tags;

    public Stuff(String[] stuff) {
        this(stuff[0].strip(), stuff[1].strip(), stuff[2].strip(), Tag.parse(stuff[3].strip()));
    }
    public Stuff(String id, String type, String name, ArrayList<Tag> tags) throws IllegalArgumentException {
        if (!id.matches("\\d+")) // 숫자만 포함되었는지 검사
            throw new IllegalArgumentException("ID format is wrong. ID: " + id);

        this.id     = id;
        this.type   = type;
        this.name   = name;
        this.tags   = tags;
    }
    public Stuff(String type, String name, ArrayList<Tag> tags) {
        String id = StuffList.searchStuffId(type, name);  // 이미 존재하는 사물이면 동일한 ID
        if (id == null)
            id = StuffList.newStuffId();

        this.id     = id;
        this.type   = type;
        this.name   = name;
        this.tags   = tags;
    }

    public String           getId()     { return id; }
    public String           getType()   { return type; }
    public String           getName()   { return name; }
    public ArrayList<Tag>   getTags()   { return tags; }

    @Override
    public void print() { System.out.println(this); }

    /**
     * 사물 정보가 담긴 문자열을 받아 사물 정보의 배열로 반환
     * @param stuffLine []로 둘러싸인 사물 정보들의 문자열
     * @return 사물 정보 배열
     */
    public static ArrayList<Stuff> parse(String stuffLine) {
        ArrayList<Stuff> result = new ArrayList<>();

        if (stuffLine.isEmpty()) // 비어있다면 빈 배열을 반환
            return result;

        String line = stuffLine.strip();
        line = line.substring(1, line.length() - 1).strip();

        // 대괄호 제거 및 '] ['로 문자열 파싱
        for (String stuff : line.split("] \\[", -1))
            result.add(new Stuff(stuff.split(";", -1)));

        return result;
    }

    /**
     * 사물 배열을 문자열로 변환
     * @param stuffs 사물 배열
     * @return []로 둘러싸인 사물 정보들의 문자열
     */
    public static String arrayToString(ArrayList<Stuff> stuffs) {
        StringBuilder result = new StringBuilder();
        for (Stuff stuff : stuffs)
            result.append("[").append(stuff.toString()).append("] ");

        return result.toString().trim();
    }

    @Override
    public String toString() {
        return id + "; " + type + "; " + name + "; " + Tag.arrayToString(tags);
    }
}
