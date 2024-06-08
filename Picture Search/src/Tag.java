import java.util.ArrayList;

/**
 * 태그 정보를 관리하는 레코드
 *
 * @param name 태그 이름
 */
public record Tag(String name) {

    /**
     * 태그 문자열을 파싱하여 태그 배열을 반환함
     *
     * @param tagLine 공백으로 구분된 태그 문자열
     * @return 태그 배열
     * @throws IllegalArgumentException {@code tagLine}이 {@code null}인 경우
     */
    public static ArrayList<Tag> parse(String tagLine) throws IllegalArgumentException {
        if (tagLine == null)
            throw new IllegalArgumentException("tagLine is null");

        ArrayList<Tag> result = new ArrayList<>();
        if (!tagLine.isEmpty()) {
            for (String token : tagLine.split(" "))
                result.add(new Tag(token.strip().substring(1)));
        }
        return result;
    }

    /**
     * 태그 배열을 문자열로 변환함
     *
     * @param tags 태그 배열
     * @return 태그 문자열
     * @throws IllegalArgumentException {@code tags}가 {@code null}인 경우
     */
    public static String arrayToString(ArrayList<Tag> tags) throws IllegalArgumentException {
        if (tags == null)
            throw new IllegalArgumentException("tags is null");

        StringBuilder result = new StringBuilder();
        for (Tag tag : tags)
            result.append(tag.toString()).append(" ");

        return result.toString().strip();
    }

    @Override
    public String toString() {
        return "#" + name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tag tag)
            return name.equals(tag.name);

        return false;
    }
}
