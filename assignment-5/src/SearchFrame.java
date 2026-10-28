/**
 *
 * Spring 2024
 * Software Project
 *
 * SearchFrame.java
 *
 * Provides a separate window containing
 * time-based and keyword-based search fields.
 *
 */

import javax.swing.*;
import java.awt.*;

public class SearchFrame extends JFrame {
    private final JPanel jpnTimeSearch = new JPanel(new GridLayout(2, 3));
    private final JPanel jpnKeywordSearch = new JPanel(new BorderLayout());
    private final JPanel pLeft = new JPanel(new GridLayout(2, 2));
    private final JPanel pRight = new JPanel(new GridBagLayout());
    private final JPanel pSouth = new JPanel();
    private final JPanel pButtons = new JPanel();

    private final JLabel jlbFrom = new JLabel("From");
    private final JLabel jlbFromFormat = new JLabel("(yyyy-MM-dd_HH:mm:ss)");
    private final JLabel jlbTo = new JLabel("To");
    private final JLabel jlbTags = new JLabel("Tags");
    private final JLabel jlbComments = new JLabel("Comments");
    private final JLabel jlbStuffType = new JLabel("Type");
    private final JLabel jlbStuffName = new JLabel("Name");
    private final JLabel jlbStuffTags = new JLabel("Tags");
    private final JLabel jlbGeneralSearch = new JLabel("General Search");

    private final JTextField jtfFrom = new JTextField();
    private final JTextField jtfTo = new JTextField();
    private final JTextField jtfTags = new JTextField();
    private final JTextField jtfComments = new JTextField();
    private final JTextField jtfStuffType = new JTextField();
    private final JTextField jtfStuffName = new JTextField();
    private final JTextField jtfStuffTags = new JTextField();
    private final JTextField jtfGeneralSearch = new JTextField();

    private final JButton jbtAndSearch = new JButton("AND SEARCH");
    private final JButton jbtOrSearch = new JButton("OR SEARCH");
    private final JButton jbtClose = new JButton("CLOSE");

    /**
     * Constructs a SearchFrame.
     */
    public SearchFrame() {
        super("Search Picture");
        setLayout(new BorderLayout());

        // Time Search
        jpnTimeSearch.setBorder(BorderFactory.createTitledBorder("Time Search"));
        JComponent[] cmps = { jlbFrom, jtfFrom, jlbFromFormat, jlbTo, jtfTo };
        for (JComponent cp : cmps)
            jpnTimeSearch.add(cp);

        // Keyword Search
        jpnKeywordSearch.setBorder(BorderFactory.createTitledBorder("Keyword Search"));
        cmps = new JComponent[] { jlbTags, jtfTags, jlbComments, jtfComments };
        for (JComponent cp : cmps)
            pLeft.add(cp);

        JPanel outer = new JPanel(new BorderLayout());
        JPanel[] c_pns = new JPanel[2];
        JComponent[][] c_cps = { { jlbStuffType, jlbStuffName, jlbStuffTags },
                { jtfStuffType, jtfStuffName, jtfStuffTags } };
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
        pRight.add(outer, gbc);

        jtfGeneralSearch.setColumns(10);
        pSouth.add(jlbGeneralSearch);
        pSouth.add(jtfGeneralSearch);

        JPanel outer2 = new JPanel(new GridLayout(1, 2));
        outer2.add(pLeft);
        outer2.add(pRight);
        jpnKeywordSearch.add(outer2, BorderLayout.CENTER);
        jpnKeywordSearch.add(pSouth, BorderLayout.SOUTH);

        // Buttons
        for (JButton bt : new JButton[] { jbtAndSearch, jbtOrSearch, jbtClose })
            pButtons.add(bt);
        add(pButtons, BorderLayout.SOUTH);

        // ADD
        add(jpnTimeSearch, BorderLayout.NORTH);
        add(jpnKeywordSearch, BorderLayout.CENTER);

        pack();
        setSize(500, 300);
        setVisible(true);
    }
}
