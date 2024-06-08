import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * 사진 패널들을 담는 영역
 */
public class PictureSection extends JPanel {
    private final PictureList             pictureList;
    private final ArrayList<PicturePanel> picturePanels = new ArrayList<>();
    private PicturePanel            selected = null; // 현재 선택된 PicturePanel

    public PictureSection() {
        pictureList = new PictureList(PictureList.DATA_FILE);
        initialize();
    }
    public PictureSection(PictureList list) {
        pictureList = list;
        initialize();
    }

    /**
     * 사진 패널들을 불러오는 함수
     */
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

    public PictureList getPictureList() { return pictureList; }
    public Picture getSelectedPicture() {
        if (selected == null)
            return null;
        return selected.getPicture();
    }
    public boolean isPictureSelected() {
        return selected != null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PictureSection ps)
            return pictureList.equals(ps.pictureList);

        return false;
    }

    private class MouseHandlerClass extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (selected != null)             // 이전에 이미 선택된 패널이 있다면
                selected.setBorderAsSelected(false);

            PicturePanel pp = (PicturePanel)e.getSource();
            selected = pp;
            pp.setBorderAsSelected(true);   // 테두리 색 변화
        }
    }
}
