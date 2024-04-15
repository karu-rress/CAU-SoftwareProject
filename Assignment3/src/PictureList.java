import java.io.*;
import java.util.*;

public class PictureList {
    // TODO: 향후 ArrayList 사용
    private final Picture[] pictures = new Picture[100];
    private int length;

    public PictureList() {
        this("picture-normal.data");
    }
    public PictureList(String infoFileName) {
        readFile(infoFileName);
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
        int current = 0;
        while (input.hasNext()) {
            try {
                line = input.nextLine().strip();
                current++;

                // 주석 행이라면 무시한다.
                if (line.startsWith("//")) continue;

                // 처음과 끝의 <, > 삭제 후 '> <'로 구분 (-1: 빈 문자열을 제거하지 않음)
                String[] array = line.substring(1, line.length() - 1).split("> <", -1);
                if (array.length != 6) {
                    throw new IllegalArgumentException("Array length is not 6.");
                }


                String[] trimArray = new String[6];
                for (int i = 0; i < 6; i++)
                    trimArray[i] = array[i].trim();

                add(new Picture(trimArray));
            }
            catch (Exception e) {
                System.err.print("Error on line #" + current + ": ");
                System.err.println(e.getMessage());
            }
        }

        input.close();
    }

    public void writeFile(String infoFileName) throws IOException {
        File file = new File(infoFileName);

        if (!file.exists())
            file.createNewFile();

        PrintWriter output = new PrintWriter(file);
        for (int i = 0; i < length; i++) {
            output.println(pictures[i]);
        }
        output.close();
    }

    public int size() {
        return length;
    }

    public Picture get(int i) {
        return (i >= 0 && i < length) ? pictures[i] : null;
    }

    public Picture[] getPictures() {
        Picture[] array = new Picture[length];
        System.arraycopy(pictures, 0, array, 0, length);
        return array;
    }

    public void add(Picture pic) {
        // TODO: 향후 ArrayList 사용시 삭제
        if (length >= 100) {
            return;
        }

        for (int i = 0; i < length; i++) {
            if (Objects.equals(pictures[i].getId(), pic.getId()))
                throw new IllegalArgumentException(pic.getId() + ": ID already exists!");
        }
        pictures[length] = pic;
        length++;
    }

    public void sortByDate() {
        // Comparator와 람다를 이용하여 간단하게 정렬
        Arrays.sort(pictures, 0, length,
                Comparator.comparing(pic -> pic.time));
    }
}
