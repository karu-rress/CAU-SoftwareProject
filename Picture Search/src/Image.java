import java.io.Serializable;
import java.util.ArrayList;

/**
 * 이미지 정보를 관리하는 클래스
 */
public class Image implements Printable, Serializable {
    // 이미지 정보는 변하지 않으므로 final로 선언
    private final String            id;
    private final String            fileName;
    private final String            imageName;
    private final ArrayList<Tag>    tags;

    public Image(String id, String fileName) {
        this(id, fileName, "", new ArrayList<>());
    }
    public Image(String id, String fileName, String imageName, ArrayList<Tag> tags) {
        this.id         = id;
        this.fileName   = fileName;
        this.imageName  = imageName;
        this.tags       = tags;
    }

    public String           getId()         { return id; }
    public String           getFileName()   { return fileName; }
    public String           getImageName()  { return imageName; }
    public ArrayList<Tag>   getTags()       { return tags; }

    @Override
    public String toString() {
        return id + ";" + fileName + ";" + imageName + ";" + Tag.arrayToString(tags);
    }

    @Override
    public void print() { System.out.println(this); }
}
