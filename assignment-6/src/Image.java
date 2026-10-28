/**
 *
 * Spring 2024
 * Software Project
 *
 * Image.java
 *
 * Stores image-related information, including
 * its ID, file name, image name, and tags.
 *
 */

import java.util.ArrayList;

/**
 * Stores image-related information,
 * including its ID, file name, image name, and tags.
 */
public class Image {
    private final String id;
    private final String fileName;
    private final String imageName;
    private final ArrayList<Tag> tags;

    /**
     * Construct an Image object from a single line of information.
     * The line should contain the ID, file name, image name, and tags,
     * separated by semicolons.
     * 
     * @param infoLine A single line of information containing the image details.
     */
    public Image(String infoLine) {
        this(
                infoLine.split(";", -1)[0].trim(),
                infoLine.split(";", -1)[1].trim(),
                infoLine.split(";", -1)[2].trim(),
                Tag.parse(infoLine.split(";", -1)[3]));
    }

    /**
     * Construct an Image object with the specified ID and file name.
     * The image name and tags are set to empty.
     * 
     * @param id       The ID of the image.
     * @param fileName The file name of the image.
     */
    public Image(String id, String fileName) {
        this(id, fileName, "", new ArrayList<>());
    }

    /**
     * Construct an Image object with the specified ID,
     * file name, image name, and tags.
     * 
     * @param id        The ID of the image.
     * @param fileName  The file name of the image.
     * @param imageName The name of the image.
     * @param tags      An array of tags associated with the image.
     */
    public Image(String id, String fileName, String imageName, ArrayList<Tag> tags) {
        this.id = id;
        this.fileName = fileName;
        this.imageName = imageName;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getImageName() {
        return imageName;
    }

    public ArrayList<Tag> getTags() {
        return tags;
    }

    /**
     * Print the image information to the console.
     */
    public void print() {
        System.out.println(this);
    }

    /**
     * Return a string representation of the image, separated by semicolons.
     * 
     * @return A string containing the image's ID, file name, image name, and tags.
     */
    @Override
    public String toString() {
        return "%s;%s;%s;%s".formatted(id, fileName, imageName, Tag.arrayToString(tags));
    }
}
