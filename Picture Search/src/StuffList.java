import java.util.ArrayList;

/**
 * 사물들을 저장하고 관리하는 데이터베이스
 */
public class StuffList {
    private static final ArrayList<Stuff> stuffs = new ArrayList<>();

    private StuffList() { } // 생성자를 private으로 선언하여 객체 생성 방지

    /**
     * 새로운 사물을 추가하는 함수. 유일한 Stuff ID를 부여함.
     * @param type 사물의 종류
     * @param name 사물 이름
     * @param tags 태그 리스트
     */
    public static void add(String type, String name, ArrayList<Tag> tags) {
        String id = searchStuffId(type, name);
        add(new Stuff(id != null ? id : newStuffId(), type, name, tags));
    }

    /**
     * 데이터에서 읽어온 사물을 추가하는 함수. 이미 적법한 Stuff ID가 부여되었다고 가정함.
     * @param stuff 사물 정보
     */
    public static void add(Stuff stuff) { stuffs.add(stuff); }

    /**
     * 여러 사물을 추가하는 함수. 이미 적법한 Stuff ID가 부여되었다고 가정함.
     * @param stuffs 사물 리스트
     */
    public static void add(ArrayList<Stuff> stuffs) { StuffList.stuffs.addAll(stuffs); }

    /**
     * 사물 정보 반환
     * @param index 사물 정보의 인덱스
     * @return 사물 정보 || {@code null}
     */
    public static Stuff get(int index) {
        return (index >= 0 && index < stuffs.size()) ? stuffs.get(index) : null;
    }

    /**
     * ID를 통해 사물 정보 검색 후 반환
     * @param id 검색할 ID
     * @return 사물 정보 || {@code null}
     */
    public static Stuff get(String id) {
        for (Stuff stuff : stuffs) {
            if (stuff.getId().equals(id)) return stuff;
        }
        return null;
    }

    /**
     * 사물 종류와 이름으로 ID를 찾는 함수
     * @param type 사물의 종류
     * @param name 사물 이름
     * @return 사물 ID || {@code null}
     */
    public static String searchStuffId(String type, String name) {
        for (Stuff stuff : stuffs) {
            if (stuff.getType().equals(type) && stuff.getName().equals(name))
                return stuff.getId();
        }
        return null;
    }

    /**
     * 새로운 Stuff ID를 생성하는 함수
     * @return 새로운 Stuff ID
     */
    public static String newStuffId() { return String.valueOf(stuffs.size()); }

    /**
     * StuffList의 크기 반환
     * @return 사물 정보의 수
     */
    public static int size() { return stuffs.size(); }
}
