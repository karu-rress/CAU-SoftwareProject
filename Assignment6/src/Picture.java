import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * 사진 정보를 관리하는 클래스
 */
public class Picture {
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

    // 현재 시각으로 id와 time 생성
    public Picture(String imageFileName) {
        this(null, imageFileName, new ArrayList<>(), new ArrayList<>(), "");
    }

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

    // 파일에서 읽은 정보로 객체 생성
    public Picture(String[] infoFields) {
        id          = infoFields[0];
        time        = LocalDateTime.parse(infoFields[1], dateFormatter);
        imageInfo   = new Image(infoFields[2]);
        stuffs      = Stuff.parse(infoFields[3]);
        tags        = Tag.parse(infoFields[4]);
        comment     = infoFields[5];

        // 파일에서 읽어왔으니 StuffList에 추가
        StuffList.add(stuffs);
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

    public void print() { System.out.println(this); }

    @Override
    public String toString() {
        return STR."< \{id} > < \{getTimeAsString()} > < \{imageInfo} > < \{Stuff.arrayToString(stuffs)} > < \{Tag.arrayToString(tags)} > < \{comment} >";
    }

    private String formatDate(LocalDateTime dt, String prefix) {
        return dt.format(DateTimeFormatter.ofPattern(STR."'\{prefix}'\{dateFormat}"));
    }
}
