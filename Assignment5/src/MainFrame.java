import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // ActionListener
        jbtShowAll.addActionListener(new ShowAllListenerClass());
        jbtAdd.addActionListener(new AddListenerClass());
        jbtDelete.addActionListener(new DeleteListenerClass());
        jbtLoad.addActionListener(new LoadListenerClass());
        jbtSave.addActionListener(new SaveListenerClass());
        jbtSearch.addActionListener(new SearchListenerClass());
    }

    private void resetPictureSection() {
        pictureSection = new PictureSection();
        spCenter.setViewportView(pictureSection);
    }

    private class ShowAllListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { resetPictureSection(); }
    }
    private class AddListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { new AddFrame(); }
    }
    private class DeleteListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { }
    }
    private class LoadListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { }
    }
    private class SaveListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {  }
    }
    private class SearchListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) { new SearchFrame(); }
    }
}
