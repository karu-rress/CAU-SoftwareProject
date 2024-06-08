import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * 메인 프레임 (메인 화면)
 */
public class MainFrame extends JFrame {
    private final JPanel      pEast    = new JPanel(new GridLayout(5, 1));
    private final JScrollPane spCenter = new JScrollPane();

    private final JButton jbtShowAll   = new JButton("Show All Pictures");
    private final JButton jbtAdd       = new JButton("ADD");
    private final JButton jbtDelete    = new JButton("DELETE");
    private final JButton jbtLoad      = new JButton("LOAD");
    private final JButton jbtSave      = new JButton("SAVE");
    private final JButton jbtSearch    = new JButton("SEARCH");

    private PictureList originalPictureList;
    private PictureSection pictureSection = new PictureSection();
    private static final FileNameExtensionFilter extFilter = new FileNameExtensionFilter(
            "Dataset File (*.data)", "data");

    public MainFrame() {
        super("Simple Picture Search");
        setLayout(new BorderLayout());
        originalPictureList = new PictureList(pictureSection.getPictureList());

        // Center: PictureSection
        spCenter.setViewportView(pictureSection);

        // East: 버튼들
        for (JButton bt : new JButton[]{ jbtAdd, jbtDelete, jbtLoad, jbtSave, jbtSearch })
            pEast.add(bt);

        add(jbtShowAll, BorderLayout.NORTH);
        add(spCenter, BorderLayout.CENTER);
        add(pEast, BorderLayout.EAST);

        pack();
        setSize(600, 800);
        setLocationRelativeTo(null);                // 화면 중앙에 생성
        setDefaultCloseOperation(EXIT_ON_CLOSE);    // '닫기' 시 프로그램 종료
        setVisible(true);

        // ActionListener
        ActionListener btnListener = new ButtonListenerClass();
        for (JButton btn : new JButton[]{
                jbtShowAll, jbtAdd, jbtDelete, jbtLoad, jbtSave, jbtSearch })
            btn.addActionListener(btnListener);
    }

    public PictureSection getPictureSection() { return pictureSection; }

    /**
     * 검색 결과를 무시하고, 모든 사진을 보여주는 함수
     */
    private void showAllPictures() {
        pictureSection = new PictureSection(originalPictureList);
        spCenter.setViewportView(pictureSection);
    }

    // 추후 이걸 callback으로 구현해도 좋을 것 같다.
    public void addPicture(Picture pic) {
        PictureList list = pictureSection.getPictureList();
        list.add(pic);
        refreshPictureSection(list);
    }

    /**
     * 사진을 삭제하는 함수
     */
    private void deletePicture() {
        if (!pictureSection.isPictureSelected()) {
            JOptionPane.showMessageDialog(
                    MainFrame.this, "삭제할 사진을 먼저 선택하세요.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        PictureList list = pictureSection.getPictureList();
        list.remove(pictureSection.getSelectedPicture());
        refreshPictureSection(list);
    }

    /**
     * PictureSection을 갱신하는 함수
     * @param list 사진 정보가 담긴 리스트
     */
    private void refreshPictureSection(PictureList list) {
        pictureSection = new PictureSection(list);
        spCenter.setViewportView(pictureSection);
    }

    /**
     * 사진 정보를 파일에서 다시 불러오고, PictureSection을 갱신하는 함수
     * @param fileName 데이터 파일 이름
     */
    private void reloadPictureList(String fileName) {
        PictureList list = new PictureList(fileName);
        originalPictureList = new PictureList(list);
        refreshPictureSection(list);
    }

    private void loadFromFile() {
        JFileChooser jf = new JFileChooser(".");
        jf.setDialogTitle("Load from a datafile");
        jf.setFileFilter(extFilter);
        if (jf.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            String path = jf.getSelectedFile().getAbsolutePath();
            reloadPictureList(path);
            JOptionPane.showMessageDialog(
                    MainFrame.this, "Data loaded successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void saveToFile() {
        JFileChooser jf = new JFileChooser(".");
        jf.setDialogTitle("Save to a datafile");
        jf.setFileFilter(extFilter);
        if (jf.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            String path = jf.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".data"))
                path += ".data";
            try {
                pictureSection.getPictureList().saveToFile(path);

                JOptionPane.showMessageDialog(
                        MainFrame.this, "Data saved successfully.",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(
                        MainFrame.this, "Error has occurred while saving data.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 검색 결과를 적용하는 함수
     * @param result 검색 결과
     */
    public void applySearchResult(PictureList result) { refreshPictureSection(result); }

    private class ButtonListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == jbtShowAll)
                showAllPictures();
            else if (source == jbtAdd)
                new AddFrame(MainFrame.this);
            else if (source == jbtDelete)
                deletePicture();
            else if (source == jbtLoad)
                loadFromFile();
            else if (source == jbtSave)
                saveToFile();
            else if (source == jbtSearch)
                new SearchFrame(MainFrame.this);
        }
    }
}
