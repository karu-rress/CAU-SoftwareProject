public class Image {
    private final String id;
    private final String fileName;
    private final String imageName;
    private final Tag[] tags;

    public Image(String infoLine) {
        String[] info = infoLine.split(";", -1);

        id = info[0];
        fileName = info[1];
        imageName = info[2];
        tags = Tag.parse(info[3]);
    }
    public Image(String id, String fileName) {
        this(id, fileName, null, null);
    }
    public Image(String id, String fileName, String imageName, Tag[] tags) {
        this.id = id;
        this.fileName = fileName;
        this.imageName = imageName;
        this.tags = tags;
    }

    public void print() {
        System.out.println(toString());
    }

    @Override
    public String toString() {
        return id + ";" + fileName + ";" + imageName + ";" + Tag.arrayToString(tags);
    }
}
