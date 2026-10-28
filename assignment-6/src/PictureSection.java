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
import java.awt.event.*;
import java.util.*;

/**
 * A section that contains multiple PicturePanel components
 */
public class PictureSection extends JPanel {
    private PictureList pictureList;
    private ArrayList<PicturePanel> picturePanels = new ArrayList<>();
    private PicturePanel selected = null; // Selected PicturePanel

    /**
     * Constructs a PictureSection with the default PictureList.
     */
    public PictureSection() {
        pictureList = new PictureList(PictureList.DATA_FILE);
        initialize();
    }

    /**
     * Constructs a PictureSection with the specified PictureList.
     *
     * @param list The PictureList to be displayed in this section.
     */
    public PictureSection(PictureList list) {
        pictureList = list;
        initialize();
    }

    /**
     * Initializes the PictureSection by loading the picture panels.
     */
    private void initialize() {
        for (Picture p : pictureList.getPictures())
            picturePanels.add(new PicturePanel(p));

        setLayout(new GridLayout(picturePanels.size(), 1));

        var msListener = new MouseHandlerClass();
        for (PicturePanel pp : picturePanels) {
            pp.addMouseListener(msListener);
            add(pp);
        }
    }

    public PictureList getPictureList() {
        return pictureList;
    }

    /**
     * Returns the currently selected Picture object,
     * or null if no picture is selected.
     *
     * @return The selected Picture object, or null if none is selected.
     */
    public Picture getSelectedPicture() {
        if (selected == null)
            return null;
        return selected.getPicture();
    }

    /**
     * Checks if a picture is currently selected in the section.
     *
     * @return true if a picture is selected, false otherwise.
     */
    public boolean isPictureSelected() {
        return selected != null;
    }

    /**
     * Compares this PictureSection with another object for equality.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PictureSection ps)
            return pictureList.equals(ps.pictureList);

        return false;
    }

    private class MouseHandlerClass extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (selected != null) // If a picture is already selected, deselect it
                selected.setBorderAsSelected(false);

            PicturePanel pp = (PicturePanel) e.getSource();
            selected = pp;
            pp.setBorderAsSelected(true); // Change the border color
        }
    }
}
