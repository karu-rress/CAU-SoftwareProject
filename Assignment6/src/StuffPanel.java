import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * 사물 정보를 표시하는 패널
 */
public class StuffPanel extends JPanel {
    // 해당 패널이 가지고 있는 사물 정보.
    private final Stuff stuff;

    private final JPanel pWest   = new JPanel(new GridLayout(3, 1));
    private final JPanel pCenter = new JPanel(new GridLayout(3, 1));

    private final JLabel jlbType = new JLabel("Type");
    private final JLabel jlbName = new JLabel("Name");
    private final JLabel jlbTags = new JLabel("Tags");

    private final JTextField jtfType = new JTextField();
    private final JTextField jtfName = new JTextField();
    private final JTextField jtfTags = new JTextField();

    // GridBagConstraints는 외부에서도 사용할 수 있게 함
    public static final GridBagConstraints GBC = new GridBagConstraints();
    static {
        GBC.fill = GridBagConstraints.HORIZONTAL;
        GBC.weightx = 1;
        GBC.gridx = 0;
    }

    // s == null로 editable을 추론할 수 있으나,
    // 확장성을 위해 Overloading 함.
    public StuffPanel(Stuff s) { this(s, false); }
    public StuffPanel(Stuff s, boolean editable) {
        super(new BorderLayout());
        stuff = s;

        // West: 레이블 생성
        for (JLabel lb : new JLabel[]{ jlbType, jlbName, jlbTags })
            pWest.add(lb);

        // stuff가 null이 아니라면, 이미 있는 정보를 불러옴.
        if (stuff != null) {
            jtfType.setText(stuff.getType());
            jtfName.setText(stuff.getName());
            jtfTags.setText(Tag.arrayToString(stuff.getTags()));
        }

        // Center: 텍스트 필드 생성
        for (JTextField tf : new JTextField[] {jtfType, jtfName, jtfTags }) {
            tf.setEditable(editable);
            pCenter.add(tf);
        }

        add(pWest, BorderLayout.WEST);
        add(pCenter, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.lightGray));
    }

    public Stuff getStuff() {
        if (stuff != null) // 읽기 전용 패널이면 stuff 반환
            return stuff;

        return new Stuff(getStuffType(), getStuffName(), getStuffTags());
    }
    public String           getStuffType()   { return jtfType.getText(); }
    public String           getStuffName()   { return jtfName.getText(); }
    public ArrayList<Tag>   getStuffTags()   { return Tag.parse(jtfTags.getText()); }
    public String           getStuffTagsAsString() { return jtfTags.getText(); }
}
