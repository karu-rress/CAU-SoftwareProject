import java.io.*;
import java.util.*;

// TODO: 여기에 메소드가 제일 많아야 함
public class PictureList {
    private final ArrayList<Picture> pictures;
    public static final String DATA_FILE = "picture-normal.data";
    public EventListener listener;

    public PictureList() { pictures = new ArrayList<Picture>();
    }
    public PictureList(PictureList rhs) {
        pictures = new ArrayList<Picture>(rhs.pictures);
    }
    public PictureList(String infoFileName) {
        pictures = new ArrayList<Picture>();
        readFile(infoFileName);
        sortByDate();
    }

    public void saveToFile() throws IOException {
        saveToFile(DATA_FILE);
    }
    public void saveToFile(String infoFileName) throws IOException {
        File file = new File(infoFileName);

        if (!file.exists())
            file.createNewFile();

        PrintWriter output = new PrintWriter(file);
        for (Picture picture : pictures) {
            output.println(picture);
        }
        output.close();
    }

    public int size() { return pictures.size(); }
    public Picture get(int i) { return pictures.get(i); }
    public ArrayList<Picture> getPictures() { return pictures; }

    public void add(Picture pic) {
        for (Picture picture : pictures) {
            if (Objects.equals(picture.getId(), pic.getId()))
                throw new IllegalArgumentException(pic.getId() + ": ID already exists!");
        }
        pictures.add(pic);
    }

    public void add(PictureList list) {
        pictures.addAll(list.getPictures());
        sortByDate();
    }

    public void sortByDate() {
        // Comparator를 이용하여 각 인스턴스의 getTime을 가져와서 비교함
        pictures.sort(Comparator.comparing(Picture::getTime));
    }

    private void readFile(String infoFileName) {
        Scanner input;

        try {
            input = new Scanner(new File(infoFileName));
        }
        catch (FileNotFoundException e) {
            System.err.println("Error has occurred while reading file.");
            return;
        }

        String line;
        int current = 0, success = 0;
        while (input.hasNext()) {
            try {
                line = input.nextLine().strip();
                current++;

                if (line.startsWith("//")) continue; // 주석 행이라면 무시

                // 처음과 끝의 <, > 삭제 후 '> <'로 구분 (-1: 빈 문자열을 제거하지 않음)
                String[] array = line.substring(1, line.length() - 1).split("> <", -1);
                if (array.length != 6)
                    throw new IllegalArgumentException("Array length is not 6.");

                String[] trimArray = new String[6];
                for (int i = 0; i < 6; i++)
                    trimArray[i] = array[i].trim();

                add(new Picture(trimArray));
                success++;
            }
            catch (Exception e) {
                System.err.println("Error on line #" + current + ": " + e.getMessage());
            }
        }
        input.close();
        System.out.println("Successfully loaded " + success + " items.");
    }
}
