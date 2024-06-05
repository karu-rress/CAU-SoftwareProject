import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.util.*;

/**
 * 사진 정보를 표시하는 패널
 */
public class PicturePanel extends JPanel {
    // 해당 패널이 가지고 있는 사진 정보
    private final Picture    picture;
    private       ImageIcon  icon;
    private final int PICTURE_SIZE = 130;

    private final JPanel pNorth         = new JPanel(new GridLayout(1, 2));
    private final JPanel pStuffs        = new JPanel(new GridBagLayout());
    private final JScrollPane spEast    = new JScrollPane();

    private final JLabel jlbTime        = new JLabel();
    private final JLabel jlbImage;

    private final JTextField jtfTags    = new JTextField();
    private final JTextField jtfComment = new JTextField();

    private final ArrayList<StuffPanel> stuffPanels = new ArrayList<StuffPanel>();

    public PicturePanel(Picture pic) {
        setLayout(new BorderLayout());

        picture = pic;
        icon = new ImageIcon(pic.getImageInfo().getFileName());
        icon = new ImageIcon(icon.getImage().getScaledInstance(PICTURE_SIZE, PICTURE_SIZE, Image.SCALE_SMOOTH));

        // 물건이 있는 만큼 StuffPanel 불러오기
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

        // Center: 불러온 StuffPanel들을 추가
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

    public Picture getPicture() { return picture; }

    /**
     * 선택 여부에 따라 사진 패널의 테두리 색상을 변경함
     * @param isSelected 선택 여부
     */
    public void setBorderAsSelected(boolean isSelected) {
        setBorder(BorderFactory.createLineBorder(isSelected ? Color.RED : Color.GRAY));
    }
}
