import java.io.Serializable;
import java.util.ArrayList;

/**
 * 사물 정보를 담는 클래스
 */
public class Stuff implements Printable, Serializable {
    // 한 번 생성된 Stuff 객체는 수정되지 않으므로, final로 선언
    private final String           id;
    private final String           type;
    private final String           name;
    private final ArrayList<Tag>   tags;

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

    @Override
    public String toString() {
        return id + "; " + type + "; " + name + "; " + Tag.arrayToString(tags);
    }
}
