/**
 *
 * Spring 2024
 * Software Project
 *
 * MainFrame.java
 *
 * The main application window. Provides buttons
 * for displaying all pictures, adding and deleting
 * pictures, loading and saving data, and searching.
 *
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * Main application window for managing pictures.
 * Provides buttons for displaying all pictures,
 * adding and deleting pictures, loading and saving data,
 * and searching.
 */
public class MainFrame extends JFrame {
    private final JPanel pEast = new JPanel(new GridLayout(5, 1));
    private final JScrollPane spCenter = new JScrollPane();

    private final JButton jbtShowAll = new JButton("Show All Pictures");
    private final JButton jbtAdd = new JButton("ADD");
    private final JButton jbtDelete = new JButton("DELETE");
    private final JButton jbtLoad = new JButton("LOAD");
    private final JButton jbtSave = new JButton("SAVE");
    private final JButton jbtSearch = new JButton("SEARCH");

    private PictureList originalPictureList;
    private PictureSection pictureSection = new PictureSection();
    private static final FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
            "Dataset File (*.data)", "data");

    /**
     * Constructs the main application window with
     * buttons for various operations.
     */
    public MainFrame() {
        super("Simple Picture Search");
        setLayout(new BorderLayout());
        originalPictureList = new PictureList(pictureSection.getPictureList());

        // Center: PictureSection
        spCenter.setViewportView(pictureSection);

        // East: buttons for various operations
        for (JButton bt : new JButton[] { jbtAdd, jbtDelete, jbtLoad, jbtSave, jbtSearch })
            pEast.add(bt);

        add(jbtShowAll, BorderLayout.NORTH);
        add(spCenter, BorderLayout.CENTER);
        add(pEast, BorderLayout.EAST);

        pack();
        setSize(600, 800);
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(EXIT_ON_CLOSE); // If closed, terminate the program
        setVisible(true);

        // ActionListener
        ActionListener btnListener = new ButtonListenerClass();
        for (JButton btn : new JButton[] {
                jbtShowAll, jbtAdd, jbtDelete, jbtLoad, jbtSave, jbtSearch })
            btn.addActionListener(btnListener);
    }

    public PictureSection getPictureSection() {
        return pictureSection;
    }

    /**
     * Ignore the search result and show all pictures.
     */
    private void showAllPictures() {
        pictureSection = new PictureSection(originalPictureList);
        spCenter.setViewportView(pictureSection);
    }

    /**
     * Add a picture to the current picture list.
     * 
     * @param pic The picture to add.
     */
    public void addPicture(Picture pic) {
        PictureList list = pictureSection.getPictureList();
        list.add(pic);
        refreshPictureSection(list);
    }

    /**
     * Delete the selected picture from the current picture list.
     */
    private void deletePicture() {
        if (!pictureSection.isPictureSelected()) {
            JOptionPane.showMessageDialog(
                    MainFrame.this, "No picture selected for deletion.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        PictureList list = pictureSection.getPictureList();
        list.remove(pictureSection.getSelectedPicture());
        refreshPictureSection(list);
    }

    /**
     * Refresh the PictureSection with the updated picture list.
     * 
     * @param list The updated picture list.
     */
    private void refreshPictureSection(PictureList list) {
        pictureSection = new PictureSection(list);
        spCenter.setViewportView(pictureSection);
    }

    /**
     * Reload the picture list from a file and refresh the PictureSection.
     * 
     * @param fileName The name of the data file.
     */
    private void reloadPictureList(String fileName) {
        PictureList list = new PictureList(fileName);
        originalPictureList = new PictureList(list);
        refreshPictureSection(list);
    }

    /**
     * Load picture data from a file and refresh the PictureSection.
     */
    private void loadFromFile() {
        var jf = new JFileChooser(".");
        jf.setDialogTitle("Load from a datafile");
        jf.setFileFilter(extFilter);
        if (jf.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            String path = jf.getSelectedFile().getAbsolutePath();
            reloadPictureList(path);
            JOptionPane.showMessageDialog(
                    MainFrame.this, "Data loaded successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Save the current picture list to a file and refresh the PictureSection.
     */
    private void saveToFile() {
        var jf = new JFileChooser(".");
        jf.setDialogTitle("Save to a datafile");
        jf.setFileFilter(extFilter);
        if (jf.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            String path = jf.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".data"))
                path += ".data";
            try {
                pictureSection.getPictureList().saveToFile(path);

                JOptionPane.showMessageDialog(
                        MainFrame.this, "Data saved successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(
                        MainFrame.this, "Error has occurred while saving data.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Apply the search result to the PictureSection,
     * replacing the current picture list.
     * 
     * @param result PictureList containing the search result
     */
    public void applySearchResult(PictureList result) {
        refreshPictureSection(result);
    }

    private class ButtonListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == jbtShowAll)
                showAllPictures();
            else if (source == jbtAdd)
                new AddFrame(MainFrame.this);
            else if (source == jbtDelete)
                deletePicture();
            else if (source == jbtLoad)
                loadFromFile();
            else if (source == jbtSave)
                saveToFile();
            else if (source == jbtSearch)
                new SearchFrame(MainFrame.this);
        }
    }
}
