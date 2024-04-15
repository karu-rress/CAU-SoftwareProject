public class Test {
    public static void main(String[] args) throws Exception {
        PictureList list = new PictureList("picture-normal.data");

        for (Picture pic : list.getPictures())
            pic.print();
    }
}
