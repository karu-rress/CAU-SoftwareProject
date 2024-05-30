import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class PictureSection extends JPanel {
    private PictureList pictureList = new PictureList(PictureList.DATA_FILE);
    private ArrayList<PicturePanel> picturePanels = new ArrayList<PicturePanel>();
    private PicturePanel selected = null; // 현재 선택된 PicturePanel

    public PictureSection() { initialize(); }
    public PictureSection(PictureList list) {
        pictureList = list;
        initialize();
    }

    public Picture getSelectedPicture() {
        if (selected == null)
            return null;
        return selected.getPicture();
    }

    public void deleteSelectedPicture() {
        if (selected == null) return;

        removePicturePanel(selected);
        selected = null;

        revalidate();
        repaint();
    }

    private void initialize() {
        for (Picture p : pictureList.getPictures())
            picturePanels.add(new PicturePanel(p));

        setLayout(new GridLayout(picturePanels.size(), 1));

        MouseListener msListener = new MouseHandlerClass();
        for (PicturePanel pp : picturePanels) {
            pp.addMouseListener(msListener); // 다시 대입
            add(pp);
        }
    }

    public void reloadFromFile() { reloadFromFile(PictureList.DATA_FILE); }

    public void reloadFromFile(String fileName) {
        removeAll(); // 모든 요소 삭제
        revalidate();
        repaint();

        pictureList = new PictureList(fileName); // 초기화
        picturePanels = new ArrayList<PicturePanel>();

        initialize();
    }

    public void saveToFile(String fileName) throws IOException {
        pictureList.saveToFile(fileName);
    }

    private void removePicturePanel(PicturePanel pp) {
        picturePanels.remove(pp); // 배열에서도 삭제하고
        this.remove(selected);    // 화면에서도 삭제한다

    }

    private class MouseHandlerClass extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // 이전에 이미 선택된 패널이 있다면
            if (selected != null)
                selected.setBorderAsSelected(false);

            PicturePanel pp = (PicturePanel)e.getSource();
            selected = pp;
            pp.setBorderAsSelected(true); // 테두리 색 변화
        }
    }
}
