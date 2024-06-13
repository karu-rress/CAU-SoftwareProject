import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * 사진 정보를 관리하는 클래스
 */
public class Picture implements Printable, Serializable {
    // 사진 정보는 변하지 않으므로 final로 선언
    private final String            id;        // 사진 ID
    private final LocalDateTime     time;      // timestamp
    private final Image             imageInfo; // 이미지 정보
    private final ArrayList<Stuff>  stuffs;    // 사물 정보
    private final ArrayList<Tag>    tags;      // 태그
    private final String            comment;   // 주석

    // 프로그램 전체를 통틀어 유일하게 하나의 객체만 사용되므로, static.
    private static final String dateFormat = "yyyy-MM-dd_HH:mm:ss:SSS";
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);

    public Picture(LocalDateTime time, String imageFileName,
                   ArrayList<Stuff> stuffs, ArrayList<Tag> tags, String comment) {
        if (time == null)
            time = LocalDateTime.now();

        this.id         = formatDate(time, "m_");
        this.time       = time;
        this.imageInfo  = new Image(formatDate(time, "IMG"), imageFileName);
        this.stuffs     = stuffs;
        this.tags       = tags;
        this.comment    = comment;
    }

    // Getter
    public String           getId() { return id; }
    public LocalDateTime    getTime() { return time; }
    public String           getTimeAsString() { return time.format(dateFormatter); }
    public Image            getImageInfo() { return imageInfo; }
    public ArrayList<Stuff> getStuffs() { return stuffs; }
    public ArrayList<Tag>   getTags() { return tags; }
    public String           getTagsAsString() { return Tag.arrayToString(tags); }
    public String           getComment() { return comment; }

    @Override
    public void print() { System.out.println(this); }

    private String formatDate(LocalDateTime dt, String prefix) {
        return dt.format(DateTimeFormatter.ofPattern("'" + prefix + "'" + dateFormat));
    }
}
