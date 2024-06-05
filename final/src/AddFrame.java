import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * 사진 정보를 추가하는 Frame
 */
public class AddFrame extends JFrame {
    private final JPanel pNorth         = new JPanel(new GridLayout(1, 2));
    private final JScrollPane spCenter  = new JScrollPane();
    private final JPanel pStuffs        = new JPanel(new GridBagLayout());
    private final JPanel pSouth         = new JPanel(new GridLayout(2, 1));

    // Label은 변하지 않으므로, static final로 선언하여 모든 instance가 공유하도록 함.
    private static final JLabel jlbTime     = new JLabel("Time");
    private static final JLabel jlbPicTags  = new JLabel("(Picture) Tags");
    private static final JLabel jlbComments = new JLabel("Comments");

    private final JTextField jtfTime        = new JTextField();
    private final JTextField jtfPicTags     = new JTextField();
    private final JTextField jtfComments    = new JTextField();

    private final JButton jbtSelect         = new JButton("Select Image File");
    private final JButton jbtMoreStuff      = new JButton("More Stuff");
    private final JButton jbtInputEnd       = new JButton("OK - INPUT END");

    private String imagePath = null;
    private final ArrayList<StuffPanel> stuffPanels = new ArrayList<>();
    private final MainFrame mainFrame;
    private static final FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
            "Image Files (*.png, *.jpg, *.gif, *.jpeg, *.jfif, *.webp)",
            "png", "jpg", "gif", "jpeg", "jfif", "webp");

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
        for (JButton btn : new JButton[]{jbtSelect, jbtMoreStuff, jbtInputEnd})
            btn.addActionListener(btnListener);
    }

    /**
     * 이미지 파일을 선택하는 함수
     */
    private void selectFile() {
        JFileChooser jf = new JFileChooser(".");
        jf.setDialogTitle("Select an image file");
        jf.setFileFilter(extFilter);

        if (jf.showOpenDialog(AddFrame.this) == JFileChooser.APPROVE_OPTION) {
            String path = jf.getSelectedFile().getAbsolutePath();
            imagePath = getRelativePath(path);
            JOptionPane.showMessageDialog(AddFrame.this,
                    "Image loaded: " + imagePath, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 절대 경로를 상대 경로로 변환하는 함수
     * @param absolutePath 절대 경로
     * @return 상대 경로
     */
    private static String getRelativePath(String absolutePath) {
        Path workingPath = Paths.get("").toAbsolutePath();
        Path pathRelative = workingPath.relativize(Paths.get(absolutePath));
        return pathRelative.toString();
    }

    /**
     * 사물 패널을 추가하는 함수
     */
    private void addStuffPanel() {
        StuffPanel sp = new StuffPanel(null, true);
        stuffPanels.add(sp);
        pStuffs.add(sp, StuffPanel.GBC);
        spCenter.setViewportView(pStuffs);
    }

    /**
     * PictureSection에 사진 패널을 추가하는 함수
     */
    private void addPicture() {
        if (imagePath == null) {
            JOptionPane.showMessageDialog(AddFrame.this,
                    "Please select an image file.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime time = jtfTime.getText().isEmpty() ? null
                : LocalDateTime.parse(jtfTime.getText(), Picture.dateFormatter);
        ArrayList<Tag> tags = Tag.parse(jtfPicTags.getText());
        ArrayList<Stuff> stuffs =  new ArrayList<Stuff>();
        for (StuffPanel sp : stuffPanels)
            stuffs.add(sp.getStuff()); // TODO: Stuff가 없을 때는 아예 null 부여
        String comment = jtfComments.getText();

        Picture pic = new Picture(time, imagePath, stuffs, tags, comment);
        mainFrame.addPicture(pic);
        dispose();
    }

    // 버튼 클릭시 콜백
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
