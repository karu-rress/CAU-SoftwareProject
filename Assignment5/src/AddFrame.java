import javax.swing.*;
import java.awt.*;

public class AddFrame extends JFrame {
    private final JPanel pNorth = new JPanel(new GridLayout(1, 2));
    private final JPanel pCenter = new JPanel(new GridBagLayout());
    private final JPanel pSouth = new JPanel(new GridLayout(2, 1));

    private final JLabel jlbTime = new JLabel("Time");
    private final JLabel jlbPicTags = new JLabel("(Picture) Tags");
    private final JLabel jlbType = new JLabel("Type");
    private final JLabel jlbName = new JLabel("Name");
    private final JLabel jlbTags = new JLabel("Tags");
    private final JLabel jlbComments = new JLabel("Comments");

    private final JTextField jtfTime = new JTextField();
    private final JTextField jtfPicTags = new JTextField();
    private final JTextField jtfType = new JTextField();
    private final JTextField jtfName = new JTextField();
    private final JTextField jtfTags = new JTextField();
    private final JTextField jtfComments = new JTextField();

    private final JButton jbtSelect = new JButton("Select Image File");
    private final JButton jbtMoreStuff = new JButton("More Stuff");
    private final JButton jbtInputEnd = new JButton("OK - INPUT END");

    public AddFrame() {
        super("Add a Picture");
        setLayout(new BorderLayout());

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
        JPanel outer = new JPanel(new BorderLayout());
        JPanel[] c_pns = new JPanel[2];
        JComponent[][] c_cps = {{ jlbType, jlbName, jlbTags }, { jtfType, jtfName, jtfTags }};
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.gridx = 0;
        for (int i = 0; i < c_pns.length; i++) {
            c_pns[i] = new JPanel(new GridLayout(3, 1));
            for (JComponent cp : c_cps[i])
                c_pns[i].add(cp);
        }
        outer.add(c_pns[0], BorderLayout.WEST);
        outer.add(c_pns[1], BorderLayout.CENTER);
        pCenter.add(outer, gbc);
        pCenter.setBorder(BorderFactory.createLineBorder(Color.GRAY));

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
        add(pCenter, BorderLayout.CENTER);
        add(pSouth, BorderLayout.SOUTH);

        pack();
        setSize(500, 250);
        setVisible(true);
    }
}