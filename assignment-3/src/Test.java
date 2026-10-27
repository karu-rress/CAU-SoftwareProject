/**
 *
 * Spring 2024
 * Software Project
 *
 * Test.java
 *
 * Tests the PictureList class by loading a list of
 * pictures from a data file and printing their details.
 *
 */

public class Test {
    public static void main(String[] args) throws Exception {
        PictureList list = new PictureList("picture-normal.data");

        for (Picture pic : list.getPictures())
            pic.print();
    }
}
