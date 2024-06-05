import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 여러 사진 정보들을 관리하는 클래스
 */
public class PictureList {
    private final ArrayList<Picture> pictures;

    // 기본 데이터 파일 이름은 외부에서 볼 수 있는 static final 문자열로 저장
    public static final String DATA_FILE = "picture-normal.data";

    public enum FilterMethod { ALL, ANY }

    public PictureList() { pictures = new ArrayList<Picture>(); }
    public PictureList(PictureList rhs) { pictures = new ArrayList<Picture>(rhs.pictures); }
    public PictureList(String infoFileName) {
        pictures = new ArrayList<Picture>();
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
     */
    public void saveToFile(String infoFileName) throws IOException {
        File file = new File(infoFileName);
        if (!file.exists())
            file.createNewFile();

        PrintWriter output = new PrintWriter(file);
        for (Picture picture : pictures)
            output.println(picture);

        output.close();
    }

    /**
     * 파일을 읽어 사진 정보를 불러오는 함수
     * @param infoFileName 파일 이름
     */
    private void readFile(String infoFileName) {
        try (Scanner input = new Scanner(new File(infoFileName))) {
            int current = 0, success = 0;
            while (input.hasNext()) {
                try {
                    String line = input.nextLine().strip();
                    current++;

                    if (!line.startsWith("//")) {
                        String[] array = processLine(line);
                        add(new Picture(trimArrayElements(array)));
                        success++;
                    }
                }
                catch (Exception e) {
                    System.err.println("Error on line #" + current + ": " + e.getMessage());
                }
            }
            System.out.println("Successfully loaded " + success + " items.");
        }
        catch (FileNotFoundException e) {
            System.err.println("Error has occurred while reading file.");
        }
    }

    /**
     * 파일의 한 줄을 처리하는 함수
     * @param line 한 줄
     * @return 사진 정보 배열
     * @throws IllegalArgumentException 배열 길이가 6이 아닌 경우
     */
    private String[] processLine(String line) throws IllegalArgumentException {
        String[] array = line.substring(1, line.length() - 1).split("> <", -1);
        if (array.length != 6)
            throw new IllegalArgumentException("Array length is not 6.");

        return array;
    }

    /**
     * 배열의 각 요소의 앞뒤 공백을 제거하는 함수
     * @param array 파싱된 요소
     * @return 공백이 제거된 요소
     */
    private String[] trimArrayElements(String[] array) {
        String[] trimArray = new String[array.length];
        for (int i = 0; i < array.length; i++)
            trimArray[i] = array[i].trim();

        return trimArray;
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
        // Comparator를 이용하여 각 인스턴스의 getTime을 가져와서 비교함
        pictures.sort(Comparator.comparing(Picture::getTime));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PictureList))
            return false;

        PictureList rhs = (PictureList)obj;
        return pictures.equals(rhs.pictures);
    }
}