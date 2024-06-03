import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 사진 정보를 검색하는 Frame
 */
public class SearchFrame extends JFrame {
    private final JPanel jpnTimeSearch      = new JPanel(new GridLayout(2, 3));
    private final JPanel jpnKeywordSearch   = new JPanel(new BorderLayout());
    private final JPanel pLeft              = new JPanel(new GridLayout(2, 2));
    private final JPanel pRight             = new JPanel(new GridBagLayout());
    private final JPanel pSouth             = new JPanel();
    private final JPanel pButtons           = new JPanel();

    // Label은 변하지 않으므로, static final로 선언하여 모든 instance가 공유하도록 함.
    private static final JLabel jlbFrom          = new JLabel("From");
    private static final JLabel jlbFromFormat    = new JLabel("(yyyy-MM-dd_HH:mm:ss)");
    private static final JLabel jlbTo            = new JLabel("To");
    private static final JLabel jlbTags          = new JLabel("Tags");
    private static final JLabel jlbComments      = new JLabel("Comments");
    private static final JLabel jlbGeneralSearch = new JLabel("General Search");

    private final JTextField jtfFrom             = new JTextField();
    private final JTextField jtfTo               = new JTextField();
    private final JTextField jtfTags             = new JTextField();
    private final JTextField jtfComments         = new JTextField();
    private final JTextField jtfGeneralSearch    = new JTextField();

    private final JButton jbtAndSearch    = new JButton("AND SEARCH");
    private final JButton jbtOrSearch     = new JButton("OR SEARCH");
    private final JButton jbtClose        = new JButton("CLOSE");

    private final StuffPanel stuffPanel = new StuffPanel(null, true);;
    private final MainFrame mainFrame;

    private enum SearchMethod { AND, OR }

    public SearchFrame(MainFrame mainFrame) {
        super("Search Picture");
        setLayout(new BorderLayout());
        this.mainFrame = mainFrame;

        // Time Search
        jpnTimeSearch.setBorder(BorderFactory.createTitledBorder("Time Search"));
        JComponent[] cmps = {jlbFrom, jtfFrom, jlbFromFormat, jlbTo, jtfTo};
        for (JComponent cp : cmps)
            jpnTimeSearch.add(cp);

        // Keyword Search
        jpnKeywordSearch.setBorder(BorderFactory.createTitledBorder("Keyword Search"));
        cmps = new JComponent[]{jlbTags, jtfTags, jlbComments, jtfComments};
        for (JComponent cp : cmps)
            pLeft.add(cp);

        pRight.add(stuffPanel, StuffPanel.GBC);

        jtfGeneralSearch.setColumns(10);
        pSouth.add(jlbGeneralSearch);
        pSouth.add(jtfGeneralSearch);

        JPanel outer2 = new JPanel(new GridLayout(1, 2));
        outer2.add(pLeft);
        outer2.add(pRight);
        jpnKeywordSearch.add(outer2, BorderLayout.CENTER);
        jpnKeywordSearch.add(pSouth, BorderLayout.SOUTH);

        // Buttons
        for (JButton bt : new JButton[]{ jbtAndSearch, jbtOrSearch, jbtClose })
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
        for (JButton btn : new JButton[]{ jbtAndSearch, jbtOrSearch, jbtClose })
            btn.addActionListener(btnListener);
    }

    /**
     * 날짜 검색을 수행하는 함수
     * @param method AND 또는 OR
     * @return 검색 결과가 담긴 PictureList
     */
    private PictureList searchByDate(SearchMethod method) {
        LocalDateTime from, to;
        from = jtfFrom.getText().isEmpty() ? null : LocalDateTime.parse(jtfFrom.getText(), Picture.dateFormatter);
        to = jtfTo.getText().isEmpty() ? null : LocalDateTime.parse(jtfTo.getText(), Picture.dateFormatter);

        PictureList list = mainFrame.getPictureSection().getPictureList();
        if (method == SearchMethod.OR && (from == null && to == null))
            return new PictureList(); // OR 검색에서 시간이 없으면 빈 리스트 반환

        return list.filterByDate(from, to);
    }

    /**
     * 이미지 정보를 통한 검색을 수행하는 함수
     * @param list 검색 대상 PictureList
     * @param method AND 또는 OR
     * @return 검색 결과가 담긴 PictureList
     */
    private PictureList searchByImage(PictureList list, SearchMethod method) {
        ArrayList<Tag> tags = Tag.parse(jtfTags.getText());
        String comments = jtfComments.getText();

        if (method == SearchMethod.AND) {
            if (!tags.isEmpty())
                list = list.filterByImageTags(tags, PictureList.FilterMethod.ALL);
            if (!comments.isEmpty())
                list = list.filterByComment(comments);
        }
        else {
            PictureList temp = new PictureList();
            if (!tags.isEmpty())
                temp.add(list.filterByImageTags(tags, PictureList.FilterMethod.ANY));
            if (!comments.isEmpty())
                temp.add(list.filterByComment(comments));
        }
        return list;
    }

    private PictureList searchByStuff(PictureList list, SearchMethod method) {
        String type = stuffPanel.getStuffType();
        String name = stuffPanel.getStuffName();
        ArrayList<Tag> tags = stuffPanel.getStuffTags();

        if (method == SearchMethod.AND) {
            if (!type.isEmpty())
                list = list.filterByStuffType(type);
            if (!name.isEmpty())
                list = list.filterByStuffName(name);
            if (!tags.isEmpty())
                list = list.filterByStuffTags(tags, PictureList.FilterMethod.ALL);
        }
        else {
            PictureList temp = new PictureList();
            if (!type.isEmpty())
                temp.add(list.filterByStuffType(type));
            if (!name.isEmpty())
                temp.add(list.filterByStuffName(name));
            if (!tags.isEmpty())
                temp.add(list.filterByStuffTags(tags, PictureList.FilterMethod.ANY));
        }
        return list;
    }

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

    private void generalSearch() {
        String query = jtfGeneralSearch.getText();

        PictureList list = mainFrame.getPictureSection().getPictureList();
        PictureList temp = new PictureList();

        ArrayList<Tag> tags = new ArrayList<>();
        for (String token : query.split(" ")) {
            String trimmed = token.strip();
            if (trimmed.isEmpty())        continue;
            if (trimmed.charAt(0) == '#') trimmed = trimmed.substring(1);
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
                if (!jtfGeneralSearch.getText().isEmpty()) { // General Search가 비어 있지 않다면
                    generalSearch();
                    return;
                }
                if (btn == jbtAndSearch)
                    andSearch();
                else
                    orSearch();
            }
            else if (btn == jbtClose)
                dispose();
        }
    }
}
