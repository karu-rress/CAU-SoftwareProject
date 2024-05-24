import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Picture {
    private final String id; // 사진 ID
    public final LocalDateTime time; // timestamp
    private final Image imageInfo; // 이미지 정보
    private Stuff[] stuffs; // 사물 정보
    private Tag[] tags; // 태그
    private String comment; // 주석


    private final String dateFormat = "yyyy-MM-dd_HH:mm:ss:SSS";
    private String formatDate(LocalDateTime dt, String prefix) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'" + prefix + "'" + dateFormat);
        return dt.format(formatter);
    }

    public String getId() {
        return id;
    }

    /**
     * 현재 시각으로 time 및 id를 만드는 생성자
     * @param imageFileName: image 파일 이름
     */
    public Picture(String imageFileName) {
        LocalDateTime dt = LocalDateTime.now();

        id = formatDate(dt, "m_");
        time = dt;
        // TODO: GUI 적용 시 바뀔 수 있음.
        imageInfo = new Image(formatDate(dt, "IMG"), imageFileName);
        // 이후 다른 변수들 초기화
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

    public String getTimeAsString() {
        return time.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return  "< " + id +                            " > " +
                "< " + getTimeAsString() +             " > " +
                "< " + imageInfo +                     " > " +
                "< " + Stuff.arrayToString(stuffs) +   " > " +
                "< " + Tag.arrayToString(tags) +       " > " +
                "< " + comment + 					   " >";
    }
}
