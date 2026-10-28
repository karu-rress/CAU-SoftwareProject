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
import java.util.ArrayList;

/**
 * A panel that displays information about a Stuff object
 */
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

    // GridBagConstraints can also be used outside
    public static final GridBagConstraints GBC = new GridBagConstraints();
    static {
        GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.weightx = 1;
        GBC.gridx = 0;
    }

    /**
     * Constructs a StuffPanel for displaying information about
     * a Stuff object. (Not editable)
     *
     * @param s The Stuff object to display.
     */
    public StuffPanel(Stuff s) {
        this(s, false);
    }

    /**
     * Constructs a StuffPanel for displaying information about
     * a Stuff object.
     *
     * @param s        The Stuff object to display.
     * @param editable Whether the text fields should be editable.
     */
    public StuffPanel(Stuff s, boolean editable) {
        super(new BorderLayout());
        stuff = s;

        // West: labels
        for (JLabel lb : new JLabel[] { jlbType, jlbName, jlbTags })
            pWest.add(lb);

        // if stuff is not null, set the text fields with its data
        if (stuff != null) {
            jtfType.setText(stuff.getType());
            jtfName.setText(stuff.getName());
            jtfTags.setText(Tag.arrayToString(stuff.getTags()));
        }

        // Center: text fields
        for (JTextField tf : new JTextField[] { jtfType, jtfName, jtfTags }) {
            tf.setEditable(editable);
            pCenter.add(tf);
        }

        add(pWest, BorderLayout.WEST);
        add(pCenter, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
    }

    /**
     * Checks if all text fields in the panel are empty.
     *
     * @return true if all text fields are empty, false otherwise.
     */
    public boolean isAllEmpty() {
        for (var tf : new JTextField[] { jtfType, jtfName, jtfTags }) {
            if (!tf.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the Stuff object associated with this panel.
     *
     * @return The Stuff object associated with this panel.
     */
    public Stuff getStuff() {
        if (stuff != null) // if the panel is read-only, return the original stuff
            return stuff;

        return new Stuff(getStuffType(), getStuffName(), getStuffTags());
    }

    public String getStuffType() {
        return jtfType.getText();
    }

    public String getStuffName() {
        return jtfName.getText();
    }

    public ArrayList<Tag> getStuffTags() {
        return Tag.parse(jtfTags.getText());
    }
}
