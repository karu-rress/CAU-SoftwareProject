import java.util.ArrayList;

public class Image {
    private final String id;
    private final String fileName;
    private final String imageName;
    private final ArrayList<Tag> tags;

    public Image(String infoLine) {
        String[] info = infoLine.split(";", -1);

        id = info[0].trim();
        fileName = info[1].trim();
        imageName = info[2].trim();
        tags = Tag.parse(info[3]);
    }
    public Image(String id, String fileName) {
        this(id, fileName, null, null);
    }
    public Image(String id, String fileName, String imageName, ArrayList<Tag> tags) {
        this.id = id;
        this.fileName = fileName;
        this.imageName = imageName;
        this.tags = tags;
    }

    public void print() { System.out.println(this); }

    public String getFileName() { return fileName; }

    @Override
    public String toString() {
        return id + ";" + fileName + ";" + imageName + ";" + Tag.arrayToString(tags);
    }
}
