import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PictureSection extends JPanel {
    private final PictureList pictureList = new PictureList();
    private final ArrayList<PicturePanel> picturePanels = new ArrayList<PicturePanel>();
    public PictureSection() { this(null); }

    public PictureSection(Object searchQuery) {
        searchPictures(searchQuery);

        setLayout(new GridLayout(picturePanels.size(), 1));
        for (PicturePanel pp : picturePanels) {
            add(pp);
        }
    }

    private void searchPictures(Object query) {
        // TODO: 과제 6에서 구현
        for (Picture p : pictureList.getPictures())
            picturePanels.add(new PicturePanel(p));
    }
}
