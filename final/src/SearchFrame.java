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
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A frame that provides searching UI
 */
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
    private final JLabel jlbGeneralSearch = new JLabel("General Search");

    private final JTextField jtfFrom = new JTextField();
    private final JTextField jtfTo = new JTextField();
    private final JTextField jtfTags = new JTextField();
    private final JTextField jtfComments = new JTextField();
    private final JTextField jtfGeneralSearch = new JTextField(10);

    private final JButton jbtAndSearch = new JButton("AND SEARCH");
    private final JButton jbtOrSearch = new JButton("OR SEARCH");
    private final JButton jbtClose = new JButton("CLOSE");

    private final StuffPanel stuffPanel = new StuffPanel(null, true);
    private final MainFrame mainFrame;

    private enum SearchMethod {
        AND, OR
    }

    /**
     * Constructs a SearchFrame.
     * 
     * @param mainFrame The main frame to which this search frame belongs.
     */
    public SearchFrame(MainFrame mainFrame) {
        super("Search Picture");
        setLayout(new BorderLayout());
        this.mainFrame = mainFrame;

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

        pRight.add(stuffPanel, StuffPanel.GBC);

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
        setLocationRelativeTo(this.mainFrame);
        setVisible(true);

        // ActionListener
        ActionListener btnListener = new ButtonListenerClass();
        for (JButton btn : new JButton[] { jbtAndSearch, jbtOrSearch, jbtClose })
            btn.addActionListener(btnListener);
    }

    /**
     * Searches for pictures based on the specified date range
     * 
     * @param method The search method to use (AND or OR)
     * @return A PictureList containing the search results
     */
    private PictureList searchByDate(SearchMethod method) {
        LocalDateTime from, to;
        from = jtfFrom.getText().isEmpty() ? null : LocalDateTime.parse(jtfFrom.getText(), Picture.dateFormatter);
        to = jtfTo.getText().isEmpty() ? null : LocalDateTime.parse(jtfTo.getText(), Picture.dateFormatter);

        PictureList list = mainFrame.getPictureSection().getPictureList();
        if (method == SearchMethod.OR && (from == null && to == null))
            return new PictureList(); // Return an empty list if no date range is specified for OR search

        return list.filterByDate(from, to);
    }

    /**
     * Searches for pictures based on the specified image information.
     * 
     * @param list   The PictureList to search.
     * @param method The search method to use (AND or OR).
     * @return A PictureList containing the search results.
     */
    private PictureList searchByImage(PictureList list, SearchMethod method) {
        var tags = Tag.parse(jtfTags.getText());
        var comments = jtfComments.getText();

        if (method == SearchMethod.AND) {
            if (!tags.isEmpty())
                list = list.filterByImageTags(tags, PictureList.FilterMethod.ALL);
            if (!comments.isEmpty())
                list = list.filterByComment(comments);
        } else {
            var temp = new PictureList();
            if (!tags.isEmpty())
                temp.add(list.filterByImageTags(tags, PictureList.FilterMethod.ANY));
            if (!comments.isEmpty())
                temp.add(list.filterByComment(comments));
            return temp;
        }
        return list;
    }

    /**
     * Searches for pictures based on the specified stuff information.
     * 
     * @param list   The PictureList to search.
     * @param method The search method to use (AND or OR).
     * @return A PictureList containing the search results.
     */
    private PictureList searchByStuff(PictureList list, SearchMethod method) {
        var type = stuffPanel.getStuffType();
        var name = stuffPanel.getStuffName();
        var tags = stuffPanel.getStuffTags();

        if (method == SearchMethod.AND) {
            if (!type.isEmpty())
                list = list.filterByStuffType(type);
            if (!name.isEmpty())
                list = list.filterByStuffName(name);
            if (!tags.isEmpty())
                list = list.filterByStuffTags(tags, PictureList.FilterMethod.ALL);
        } else {
            var temp = new PictureList();
            if (!type.isEmpty())
                temp.add(list.filterByStuffType(type));
            if (!name.isEmpty())
                temp.add(list.filterByStuffName(name));
            if (!tags.isEmpty())
                temp.add(list.filterByStuffTags(tags, PictureList.FilterMethod.ANY));
            return temp;
        }
        return list;
    }

    /**
     * Performs an AND search based on the specified criteria.
     */
    private void andSearch() {
        // 1. time
        PictureList list = searchByDate(SearchMethod.AND);

        // 2. keyword
        list = searchByImage(list, SearchMethod.AND);
        list = searchByStuff(list, SearchMethod.AND);

        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No pictures found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
        mainFrame.applySearchResult(list);
        dispose();
    }

    /**
     * Performs an OR search based on the specified criteria.
     */
    private void orSearch() {
        // 1. time
        PictureList list = searchByDate(SearchMethod.OR);
        PictureList temp = new PictureList();

        // 2. keyword
        temp.add(searchByImage(list, SearchMethod.OR));
        temp.add(searchByStuff(list, SearchMethod.OR));

        if (temp.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No pictures found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
        mainFrame.applySearchResult(temp);
        dispose();
    }

    /**
     * Performs a general search based on the specified query.
     */
    private void generalSearch() {
        String query = jtfGeneralSearch.getText();

        var list = mainFrame.getPictureSection().getPictureList();
        var temp = new PictureList();
        var tags = new ArrayList<Tag>();

        for (String token : query.split(" ")) {
            String trimmed = token.strip();
            if (trimmed.isEmpty())
                continue;
            if (trimmed.charAt(0) == '#')
                trimmed = trimmed.substring(1);
            tags.add(new Tag(trimmed));
        }

        // Filter by Image
        temp.add(list.filterByImageTags(tags, PictureList.FilterMethod.ANY));
        temp.add(list.filterByComment(query));

        // Filter by Stuff
        temp.add(list.filterByStuffType(query));
        temp.add(list.filterByStuffName(query));
        temp.add(list.filterByStuffTags(tags, PictureList.FilterMethod.ANY));

        if (temp.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No pictures found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
        mainFrame.applySearchResult(temp);
        dispose();
    }

    private class ButtonListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object btn = e.getSource();

            if (btn == jbtAndSearch || btn == jbtOrSearch) {
                if (!jtfGeneralSearch.getText().isEmpty()) { // General Search
                    generalSearch();
                    return;
                }
                if (btn == jbtAndSearch)
                    andSearch();
                else
                    orSearch();
            } else if (btn == jbtClose)
                dispose();
            else
                assert false : "Unknown button";
        }
    }
}
