import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 여러 사진 정보들을 관리하는 클래스
 */
public class PictureList implements Iterable<Picture>, Printable, Serializable {
    private final ArrayList<Picture> pictures;

    // 기본 데이터 파일 이름은 프로그램에서 유일한 객체이므로, static final.
    public static final String DATA_FILE = "picture-normal-bin.data";

    public enum FilterMethod { ALL, ANY }

    public PictureList() { pictures = new ArrayList<>(); }
    public PictureList(PictureList rhs) { pictures = new ArrayList<>(rhs.pictures); }
    public PictureList(String infoFileName) {
        pictures = new ArrayList<>();
        readFile(infoFileName);
        sortByDate();
    }

    public int      size()      { return pictures.size(); }
    public boolean  isEmpty()   { return pictures.isEmpty(); }
    public Picture  get(int i)  { return pictures.get(i); }
    public ArrayList<Picture> getPictures() { return pictures; }

    /**
     * 새로운 사진을 추가하는 함수
     * @param pic 사진 정보
     * @throws IllegalArgumentException 동일한 ID가 존재하는 경우
     */
    public void add(Picture pic) throws IllegalArgumentException {
        if (idExists(pic))
            throw new IllegalArgumentException(pic.getId() + ": ID already exists!");

        pictures.add(pic);
        sortByDate();
    }

    /**
     * 여러 사진을 추가하는 함수. 중복된 ID는 추가하지 않음
     * @param list 사진 정보 리스트
     */
    public void add(PictureList list) {
        for (Picture pic : list.getPictures()) {
            if (!idExists(pic))
                pictures.add(pic);
        }
        sortByDate();
    }

    public void remove(Picture pic) { pictures.remove(pic); }
    public void remove(int index)   { pictures.remove(index); }

    /**
     * 날짜에 따라 사진을 필터링하여 반환하는 함수
     * @param from 시작 날짜. {@code null}인 경우 가장 이전 날짜
     * @param to 끝 날짜. {@code null}인 경우 가장 이후 날짜
     * @return 필터링된 사진 리스트
     */
    public PictureList filterByDate(LocalDateTime from, LocalDateTime to) {
        if (from == null)   from = LocalDateTime.MIN;
        if (to == null)     to = LocalDateTime.MAX;

        PictureList result = new PictureList();
        for (Picture picture : pictures) {
            if (picture.getTime().isAfter(from) && picture.getTime().isBefore(to) ||
                    picture.getTime().isEqual(from) || picture.getTime().isEqual(to))
                result.add(picture);
        }
        return result;
    }

    /**
     * 주어진 태그를 가지고 있는 사진을 필터링하여 반환하는 함수
     * @param tags 태그 리스트
     * @param method 필터링 방법 (전부, 일부)
     * @return 필터링된 사진 리스트
     */
    public PictureList filterByImageTags(ArrayList<Tag> tags, FilterMethod method) {
        PictureList result = new PictureList();
        for (Picture picture : pictures) {
            if (method == FilterMethod.ALL) {
                if (picture.getTags().containsAll(tags))            // tags를 전부 포함하는가?
                    result.add(picture);
            }
            else if (method == FilterMethod.ANY) {
                if (!Collections.disjoint(picture.getTags(), tags)) // tags의 일부를 포함하는가?
                    result.add(picture);
            }
        }
        return result;
    }

    /**
     * 주석을 포함하는 사진을 필터링하여 반환하는 함수
     * @param comment 주석
     * @return 필터링된 사진 리스트
     */
    public PictureList filterByComment(String comment) {
        PictureList result = new PictureList();
        for (Picture picture : pictures) {
            if (picture.getComment().contains(comment))
                result.add(picture);
        }
        return result;
    }

    /**
     * 사물 종류에 따라 사진을 필터링하여 반환하는 함수
     * @param type 사물 종류
     * @return 필터링된 사진 리스트
     */
    public PictureList filterByStuffType(String type) {
        PictureList result = new PictureList();
        for (Picture picture : pictures) {
            for (Stuff stuff : picture.getStuffs()) {
                if (stuff.getType().contains(type)) {
                    result.add(picture);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 사물 이름에 따라 사진을 필터링하여 반환하는 함수
     * @param name 사물 이름
     * @return 필터링된 사진 리스트
     */
    public PictureList filterByStuffName(String name) {
        PictureList result = new PictureList();
        for (Picture picture : pictures) {
            for (Stuff stuff : picture.getStuffs()) {
                if (stuff.getName().contains(name)) {
                    result.add(picture);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 사물 태그가 모두 포함되는 사진을 반환하는 함수
     * @param tags 태그 리스트
     * @param method 필터링 방법 (전부, 일부)
     * @return 필터링된 사진 리스트
     */
    public PictureList filterByStuffTags(ArrayList<Tag> tags, FilterMethod method) {
        PictureList result = new PictureList();
        for (Picture picture : pictures) {
            for (Stuff stuff : picture.getStuffs()) {
                if (method == FilterMethod.ALL) {
                    if (stuff.getTags().containsAll(tags)) {
                        result.add(picture);
                        break;
                    }
                }
                else if (method == FilterMethod.ANY) {
                    if (!Collections.disjoint(stuff.getTags(), tags)) {
                        result.add(picture);
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 사진 정보를 파일로 저장하는 함수 (기본 파일)
     * @throws IOException 파일 입출력 예외
     */
    public void saveToFile() throws IOException { saveToFile(DATA_FILE); }

    /**
     * 사진 정보를 파일로 저장하는 함수
     * @param infoFileName 파일 이름
     * @throws IOException 파일 입출력 예외
     * @return {@code true} if the named file does not exist and was successfully created; {@code false} if the named file already exists
     */
    public boolean saveToFile(String infoFileName) throws IOException {
        File file = new File(infoFileName);
        boolean result = file.createNewFile();

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(pictures);
        }
        return result;
    }

    /**
     * 파일을 읽어 사진 정보를 불러오는 함수
     * @param infoFileName 파일 이름
     */
    private void readFile(String infoFileName) {
        File file = new File(infoFileName);
        assert file.exists() : "File does not exist!";

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            for (Object item : (ArrayList<?>)input.readObject()) {
                pictures.add((Picture)item);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error has occurred while reading file.");
        }
    }

    /**
     * 사진 ID가 이미 존재하는지 확인하는 함수
     * @param pic 사진 정보
     * @return 존재하는 경우 {@code true}, 아닌 경우 {@code false}
     */
    private boolean idExists(Picture pic) {
        for (Picture picture : pictures) {
            if (picture.getId().equals(pic.getId()))
                return true;
        }
        return false;
    }

    private void sortByDate() {
        pictures.sort(Comparator.comparing(Picture::getTime));
        assert isSorted() : "Sorting failed!";
    }

    private boolean isSorted() {
        for (int i = 1; i < pictures.size(); i++) {
            if (pictures.get(i - 1).getTime().isAfter(pictures.get(i).getTime()))
                return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PictureList pl)
            return pictures.equals(pl.pictures);

        return false;
    }

    @Override
    public Iterator<Picture> iterator() {
        return pictures.iterator();
    }

    @Override
    public void print() {
        for (Picture picture : pictures)
            picture.print();
    }
}