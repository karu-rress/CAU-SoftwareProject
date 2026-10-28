/**
 *
 * Spring 2024
 * Software Project
 *
 * AddFrame.java
 *
 * Provides a separate window containing
 * input fields for picture information.
 *
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Adds Picture information through a separate window.
 */
public class AddFrame extends JFrame {
    private final JPanel pNorth = new JPanel(new GridLayout(1, 2));
    private final JScrollPane spCenter = new JScrollPane();
    private final JPanel pStuffs = new JPanel(new GridBagLayout());
    private final JPanel pSouth = new JPanel(new GridLayout(2, 1));

    private final JLabel jlbTime = new JLabel("Time");
    private final JLabel jlbPicTags = new JLabel("(Picture) Tags");
    private final JLabel jlbComments = new JLabel("Comments");

    private final JTextField jtfTime = new JTextField();
    private final JTextField jtfPicTags = new JTextField();
    private final JTextField jtfComments = new JTextField();

    private final JButton jbtSelect = new JButton("Select Image File");
    private final JButton jbtMoreStuff = new JButton("More Stuff");
    private final JButton jbtInputEnd = new JButton("OK - INPUT END");

    private String relativePath = null;
    private final ArrayList<StuffPanel> stuffPanels = new ArrayList<>();
    private final MainFrame mainFrame;

    private static final FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
            "Image Files (*.png, *.jpg, *.gif, *.jpeg, *.jfif, *.webp)",
            "png", "jpg", "gif", "jpeg", "jfif", "webp");

    /**
     * Constructs an AddFrame window for inputting picture information.
     * The window includes fields for time, picture tags, comments,
     * and buttons for selecting an image file and confirming input.
     *
     * @param mainFrame The main application frame to which the picture will be
     *                  added.
     */
    public AddFrame(MainFrame mainFrame) {
        super("Add a Picture");
        setLayout(new BorderLayout());
        this.mainFrame = mainFrame;

        // North: Time, Tags
        JPanel[] n_pns = new JPanel[2];
        JLabel[] n_lbs = { jlbTime, jlbPicTags };
        JTextField[] n_tfs = { jtfTime, jtfPicTags };
        for (int i = 0; i < n_pns.length; i++) {
            n_pns[i] = new JPanel(new BorderLayout());
            n_pns[i].add(n_lbs[i], BorderLayout.WEST);
            n_pns[i].add(n_tfs[i], BorderLayout.CENTER);
            pNorth.add(n_pns[i]);
        }

        // Center: Type, Name, Tags
        StuffPanel stuffPanel = new StuffPanel(null, true);
        stuffPanels.add(stuffPanel);
        pStuffs.add(stuffPanel, StuffPanel.GBC);
        spCenter.setViewportView(pStuffs);
        spCenter.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // South: Comments, More Stuff, OK
        JPanel[] s_pns = new JPanel[2];
        for (int i = 0; i < s_pns.length; i++)
            s_pns[i] = new JPanel(new BorderLayout());
        s_pns[0].add(jlbComments, BorderLayout.WEST);
        s_pns[0].add(jtfComments, BorderLayout.CENTER);
        s_pns[1].add(jbtMoreStuff, BorderLayout.WEST);
        s_pns[1].add(jbtInputEnd, BorderLayout.EAST);
        pSouth.add(s_pns[0]);
        pSouth.add(s_pns[1]);

        add(pNorth, BorderLayout.NORTH);
        add(jbtSelect, BorderLayout.WEST);
        add(spCenter, BorderLayout.CENTER);
        add(pSouth, BorderLayout.SOUTH);

        pack();
        setSize(500, 250);
        setLocationRelativeTo(this.mainFrame);
        setVisible(true);

        ActionListener btnListener = new ButtonHandlerClass();
        for (JButton btn : new JButton[] { jbtSelect, jbtMoreStuff, jbtInputEnd })
            btn.addActionListener(btnListener);
    }

    /**
     * Selects an image file using a file chooser dialog
     * and updates the relative path.
     */
    private void selectFile() {
        JFileChooser jf = new JFileChooser(".");
        jf.setDialogTitle("Select an image file");
        jf.setFileFilter(extFilter);

        if (jf.showOpenDialog(AddFrame.this) == JFileChooser.APPROVE_OPTION) {
            String path = jf.getSelectedFile().getAbsolutePath();
            relativePath = Paths.get("").toAbsolutePath().relativize(Paths.get(path)).toString();
            JOptionPane.showMessageDialog(AddFrame.this,
                    "Image loaded: " + relativePath, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Adds a new StuffPanel to the AddFrame
     * for inputting additional stuff information.
     */
    private void addStuffPanel() {
        var sp = new StuffPanel(null, true);
        stuffPanels.add(sp);
        pStuffs.add(sp, StuffPanel.GBC);
        spCenter.setViewportView(pStuffs);
    }

    /**
     * Adds a picture to the PictureSection based on the input fields.
     */
    private void addPicture() {
        if (relativePath == null) {
            JOptionPane.showMessageDialog(AddFrame.this,
                    "Please select an image file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        var time = jtfTime.getText().isEmpty() ? null
                : LocalDateTime.parse(jtfTime.getText(), Picture.dateFormatter);
        var tags = Tag.parse(jtfPicTags.getText());
        ArrayList<Stuff> stuffs;
        if (stuffPanels.getFirst().isAllEmpty())
            stuffs = null;
        else {
            stuffs = new ArrayList<>();
            for (StuffPanel sp : stuffPanels)
                // If exisiting stuff information, use the same ID
                // otherwise, assign a new ID
                stuffs.add(sp.getStuff());
        }
        String comment = jtfComments.getText();

        var pic = new Picture(time, relativePath, stuffs, tags, comment);
        mainFrame.addPicture(pic);
        dispose();
    }

    /**
     * Handles button click events.
     */
    private class ButtonHandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object btn = e.getSource();
            if (btn == jbtSelect)
                selectFile();
            else if (btn == jbtMoreStuff)
                addStuffPanel();
            else if (btn == jbtInputEnd)
                addPicture();
        }
    }
}
