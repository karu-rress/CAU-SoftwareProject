import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Picture {
    private final String id; // 사진 ID
    private final LocalDateTime time; // timestamp
    private final Image imageInfo; // 이미지 정보
    private ArrayList<Stuff> stuffs; // 사물 정보
    private ArrayList<Tag> tags; // 태그
    private String comment; // 주석
    private static final String dateFormat = "yyyy-MM-dd_HH:mm:ss:SSS";

    /**
     * 현재 시각으로 time 및 id를 만드는 생성자
     * @param imageFileName: image 파일 이름
     */
    public Picture(String imageFileName) {
        LocalDateTime dt = LocalDateTime.now();
        id = formatDate(dt, "m_");
        time = dt;
        imageInfo = new Image(formatDate(dt, "IMG"), imageFileName);
    }

    // 주어진 사진 정보로 객체를 만드는 생성자
    public Picture(String[] infoFields) {
        id = infoFields[0];
        time = LocalDateTime.parse(infoFields[1], DateTimeFormatter.ofPattern(dateFormat));
        imageInfo = new Image(infoFields[2]);
        stuffs = Stuff.parse(infoFields[3]);
        tags = Tag.parse(infoFields[4]);
        comment = infoFields[5];

        for (Stuff stuff : stuffs)
            StuffList.add(stuff);
    }
    public String getId() { return id; }
    public LocalDateTime getTime() { return time; }
    public String getTimeAsString() {
        return time.format(DateTimeFormatter.ofPattern(dateFormat));
    }
    public Image getImageInfo() { return imageInfo; }
    public ArrayList<Stuff> getStuffs() { return stuffs; }
    public String getTagsAsString() { return Tag.arrayToString(tags); }
    public String getComment() { return comment; }
    public void print() { System.out.println(this); }
    @Override
    public String toString() {
        return  "< " + id +                            " > " +
                "< " + getTimeAsString() +             " > " +
                "< " + imageInfo +                     " > " +
                "< " + Stuff.arrayToString(stuffs) +   " > " +
                "< " + Tag.arrayToString(tags) +       " > " +
                "< " + comment + 					   " >";
    }
    private static String formatDate(LocalDateTime dt, String prefix) {
        return dt.format(DateTimeFormatter.ofPattern("'" + prefix + "'" + dateFormat));
    }
}
