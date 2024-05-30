import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.util.*;

public class PicturePanel extends JPanel {
    private final JPanel pNorth = new JPanel(new GridLayout(1, 2));
    private final JPanel pStuffs = new JPanel(new GridBagLayout());
    private final JLabel jlbTime = new JLabel();
    private final JLabel jlbImage;
    private final JTextField jtfTags = new JTextField();
    private final JScrollPane spEast = new JScrollPane();
    private final JTextField jtfComment = new JTextField();

    private final Picture picture;
    private ImageIcon icon;
    private final ArrayList<StuffPanel> stuffPanels = new ArrayList<StuffPanel>();
    private final int picSize = 130;

    public PicturePanel(Picture pic) {
        setLayout(new BorderLayout());

        picture = pic;
        icon = new ImageIcon(pic.getImageInfo().getFileName());
        icon = new ImageIcon(icon.getImage().getScaledInstance(picSize, picSize, Image.SCALE_SMOOTH));
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

        // Center
        for (StuffPanel sp : stuffPanels)
            pStuffs.add(sp, StuffPanel.GBC);
        spEast.setViewportView(pStuffs);
        spEast.setPreferredSize(new Dimension(0, picSize));

        // South
        jtfComment.setText(picture.getComment());
        jtfComment.setEditable(false);

        add(pNorth, BorderLayout.NORTH);
        add(jlbImage, BorderLayout.WEST);
        add(spEast, BorderLayout.CENTER);
        add(jtfComment, BorderLayout.SOUTH);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public Picture getPicture() { return picture; }

    public void setBorderAsSelected(boolean isSelected) {
        setBorder(BorderFactory.createLineBorder(
                isSelected ? Color.RED : Color.GRAY));
    }
}
