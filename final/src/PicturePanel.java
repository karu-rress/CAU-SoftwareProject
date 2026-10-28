/**
 *
 * Spring 2024
 * Software Project
 *
 * PicturePanel.java
 *
 * Displays an individual picture's timestamp,
 * tags, image, associated objects, and comment.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.util.*;

/**
 * A panel that displays the details of a single picture
 */
public class PicturePanel extends JPanel {
    private final Picture picture;
    private ImageIcon icon;
    private final int PICTURE_SIZE = 130;

    private final JPanel pNorth = new JPanel(new GridLayout(1, 2));
    private final JPanel pStuffs = new JPanel(new GridBagLayout());
    private final JScrollPane spEast = new JScrollPane();

    private final JLabel jlbTime = new JLabel();
    private final JLabel jlbImage;

    private final JTextField jtfTags = new JTextField();
    private final JTextField jtfComment = new JTextField();

    private final ArrayList<StuffPanel> stuffPanels = new ArrayList<>();

    /**
     * Constructs a PicturePanel for displaying the details of a given picture.
     * The panel includes the picture's timestamp, tags, image, associated stuffs,
     * and comments.
     *
     * @param pic The Picture object to be displayed in this panel.
     */
    public PicturePanel(Picture pic) {
        setLayout(new BorderLayout());

        picture = pic;
        icon = new ImageIcon(pic.getImageInfo().getFileName());
        icon = new ImageIcon(icon.getImage().getScaledInstance(PICTURE_SIZE, PICTURE_SIZE, Image.SCALE_SMOOTH));

        if (pic.getStuffs() != null)
            for (Stuff stuff : pic.getStuffs())
                stuffPanels.add(new StuffPanel(stuff));

        // North
        jlbTime.setText(picture.getTimeAsString());
        jtfTags.setText(picture.getTagsAsString());
        jtfTags.setEditable(false);
        pNorth.add(jlbTime);
        pNorth.add(jtfTags);

        // West
        jlbImage = new JLabel(null, icon, SwingConstants.CENTER);

        // Center: append all StuffPanels to pStuffs
        for (StuffPanel sp : stuffPanels)
            pStuffs.add(sp, StuffPanel.GBC);
        spEast.setViewportView(pStuffs);
        spEast.setPreferredSize(new Dimension(0, PICTURE_SIZE));

        // South
        jtfComment.setText(picture.getComment());
        jtfComment.setEditable(false);

        add(pNorth, BorderLayout.NORTH);
        add(jlbImage, BorderLayout.WEST);
        add(spEast, BorderLayout.CENTER);
        add(jtfComment, BorderLayout.SOUTH);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public Picture getPicture() {
        return picture;
    }

    /**
     * Changes the border color of the panel to indicate selection.
     * 
     * @param isSelected if true, sets the border color to red;
     *                   if false, sets it to gray.
     */
    public void setBorderAsSelected(boolean isSelected) {
        setBorder(BorderFactory.createLineBorder(isSelected ? Color.RED : Color.GRAY));
    }
}
