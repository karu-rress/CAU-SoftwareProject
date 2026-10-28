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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private final JPanel pEast = new JPanel(new GridLayout(5, 1));
    private final JScrollPane spCenter = new JScrollPane();
    private final JButton jbtShowAll = new JButton("Show All Pictures");
    private final JButton jbtAdd = new JButton("ADD");
    private final JButton jbtDelete = new JButton("DELETE");
    private final JButton jbtLoad = new JButton("LOAD");
    private final JButton jbtSave = new JButton("SAVE");
    private final JButton jbtSearch = new JButton("SEARCH");
    private PictureSection pictureSection = new PictureSection();

    /**
     * Constructs the main application window with
     * buttons for various operations.
     */
    public MainFrame() {
        super("Simple Picture Search");
        setLayout(new BorderLayout());

        // Center
        spCenter.setViewportView(pictureSection);

        // East
        for (JButton bt : new JButton[] { jbtAdd, jbtDelete, jbtLoad, jbtSave, jbtSearch })
            pEast.add(bt);

        add(jbtShowAll, BorderLayout.NORTH);
        add(spCenter, BorderLayout.CENTER);
        add(pEast, BorderLayout.EAST);

        pack();
        setSize(600, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // ActionListener
        jbtShowAll.addActionListener(new ShowAllListenerClass());
        jbtAdd.addActionListener(new AddListenerClass());
        jbtDelete.addActionListener(new DeleteListenerClass());
        jbtLoad.addActionListener(new LoadListenerClass());
        jbtSave.addActionListener(new SaveListenerClass());
        jbtSearch.addActionListener(new SearchListenerClass());
    }

    /**
     * Resets the picture section to display all pictures.
     */
    private void resetPictureSection() {
        pictureSection = new PictureSection();
        spCenter.setViewportView(pictureSection);
    }

    /**
     * ActionListener for the "Show All Pictures" button.
     */
    private class ShowAllListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetPictureSection();
        }
    }

    /**
     * ActionListener for the "ADD" button.
     */
    private class AddListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddFrame();
        }
    }

    /**
     * ActionListener for the "DELETE" button.
     */
    private class DeleteListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    /**
     * ActionListener for the "LOAD" button.
     */
    private class LoadListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    /**
     * ActionListener for the "SAVE" button.
     */
    private class SaveListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        }
    }

    /**
     * ActionListener for the "SEARCH" button.
     */
    private class SearchListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SearchFrame();
        }
    }
}
