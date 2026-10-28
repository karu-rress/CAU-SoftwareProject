/**
 *
 * Spring 2024
 * Software Project
 *
 * PictureSection.java
 *
 * Manages the collection of picture panels
 * displayed in the main window.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PictureSection extends JPanel {
    private final PictureList pictureList = new PictureList();
    private final ArrayList<PicturePanel> picturePanels = new ArrayList<PicturePanel>();

    public PictureSection() {
        this(null);
    }

    /**
     * Constructs a PictureSection with an optional search query.
     *
     * @param searchQuery The query to search for in the picture list.
     */
    public PictureSection(Object searchQuery) {
        searchPictures(searchQuery);

        setLayout(new GridLayout(picturePanels.size(), 1));
        for (PicturePanel pp : picturePanels) {
            add(pp);
        }
    }

    private void searchPictures(Object query) {
        // Search feature will be implemented in Assignment 6.
        // For now, we will just display all pictures.
        for (Picture p : pictureList.getPictures())
            picturePanels.add(new PicturePanel(p));
    }
}
