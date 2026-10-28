/**
 *
 * Spring 2024
 * Software Project
 *
 * StuffPanel.java
 *
 * Represents a panel for displaying information
 * about a Stuff object.
 *
 */

import javax.swing.*;
import java.awt.*;

public class StuffPanel extends JPanel {
    private final Stuff stuff;
    private final JPanel pWest = new JPanel(new GridLayout(3, 1));
    private final JPanel pCenter = new JPanel(new GridLayout(3, 1));
    private final JLabel jlbType = new JLabel("Type");
    private final JLabel jlbName = new JLabel("Name");
    private final JLabel jlbTags = new JLabel("Tags");
    private final JTextField jtfType = new JTextField();
    private final JTextField jtfName = new JTextField();
    private final JTextField jtfTags = new JTextField();

    /**
     * Constructs a StuffPanel for displaying information about
     * a Stuff object.
     *
     * @param s The Stuff object to display.
     */
    public StuffPanel(Stuff s) {
        super(new BorderLayout());
        stuff = s;

        // West
        for (JLabel lb : new JLabel[] { jlbType, jlbName, jlbTags })
            pWest.add(lb);

        // Center
        jtfType.setText(stuff.getType());
        jtfName.setText(stuff.getName());
        jtfTags.setText(Tag.arrayToString(stuff.getTags()));
        for (JTextField tf : new JTextField[] { jtfType, jtfName, jtfTags }) {
            tf.setEditable(false);
            pCenter.add(tf);
        }

        add(pWest, BorderLayout.WEST);
        add(pCenter, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
}
