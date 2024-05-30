import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame {
    private final JPanel pEast = new JPanel(new GridLayout(5, 1));
    private final JScrollPane spCenter = new JScrollPane();
    private final JButton jbtShowAll = new JButton("Show All Pictures");
    private final JButton jbtAdd = new JButton("ADD");
    private final JButton jbtDelete = new JButton("DELETE");
    private final JButton jbtLoad = new JButton("LOAD");
    private final JButton jbtSave = new JButton("SAVE");
    private final JButton jbtSearch = new JButton("SEARCH");
    private PictureSection pictureSection = new PictureSection();
    private static final FileNameExtensionFilter extfilter = new FileNameExtensionFilter(
            "Dataset File (*.data)", "data");

    public MainFrame() {
        super("Simple Picture Search");
        setLayout(new BorderLayout());

        // Center
        spCenter.setViewportView(pictureSection);

        // East
        for (JButton bt : new JButton[]{ jbtAdd, jbtDelete, jbtLoad, jbtSave, jbtSearch })
            pEast.add(bt);

        add(jbtShowAll, BorderLayout.NORTH);
        add(spCenter, BorderLayout.CENTER);
        add(pEast, BorderLayout.EAST);

        pack();
        setSize(600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // ActionListener
        ActionListener btnListener = new ButtonListenerClass();
        for (JButton btn : new JButton[]{
                jbtShowAll, jbtAdd, jbtDelete, jbtLoad, jbtSave, jbtSearch })
            btn.addActionListener(btnListener);
    }





    private void reloadPictureSection() {
        reloadPictureSection(PictureList.DATA_FILE);
    }

    private void reloadPictureSection(String fileName) {
        PictureList list = new PictureList(fileName);
        pictureSection = new PictureSection(list);
        spCenter.setViewportView(pictureSection);
    }

    private void deletePicture() {
        Picture pic = pictureSection.getSelectedPicture();
        if (pic == null) {
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "삭제할 사진을 먼저 선택하세요.",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 삭제 역할은 pictureSection에서 담당함.
        pictureSection.deleteSelectedPicture();
    }

    private void loadFromFile() {
        JFileChooser jf = new JFileChooser();
        jf.setDialogTitle("Load from a datafile");
        jf.setFileFilter(extfilter);
        if (jf.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            String path = jf.getSelectedFile().getAbsolutePath();
            reloadPictureSection(path);
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Data loaded successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void saveToFile() {
        JFileChooser jf = new JFileChooser();
        jf.setDialogTitle("Save to a datafile");
        jf.setFileFilter(extfilter);
        if (jf.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
            String path = jf.getSelectedFile().getAbsolutePath();
            if (!path.endsWith(".data"))
                path += ".data";
            try {
                pictureSection.saveToFile(path);
            }
            catch (IOException e) {
                JOptionPane.showMessageDialog(
                        MainFrame.this,
                        "Error has occurred while saving data.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(
                    MainFrame.this,
                    "Data saved successfully.",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class ButtonListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source == jbtShowAll)
                reloadPictureSection();
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
