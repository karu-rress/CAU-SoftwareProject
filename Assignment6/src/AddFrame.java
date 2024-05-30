import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddFrame extends JFrame {
    private final JPanel pNorth = new JPanel(new GridLayout(1, 2));
    private final JScrollPane spCenter = new JScrollPane();
    private final JPanel pStuffs = new JPanel(new GridBagLayout());
    private final JPanel pSouth = new JPanel(new GridLayout(2, 1));

    // Label은 변하지 않으므로, static final로 선언하여 모든 instance가 공유하도록 함.
    private static final JLabel jlbTime = new JLabel("Time");
    private static final JLabel jlbPicTags = new JLabel("(Picture) Tags");
    private static final JLabel jlbComments = new JLabel("Comments");

    private final JTextField jtfTime = new JTextField();
    private final JTextField jtfPicTags = new JTextField();
    private final JTextField jtfComments = new JTextField();

    private final JButton jbtSelect = new JButton("Select Image File");
    private final JButton jbtMoreStuff = new JButton("More Stuff");
    private final JButton jbtInputEnd = new JButton("OK - INPUT END");

    public AddFrame(MainFrame mainFrame) {
        super("Add a Picture");
        setLayout(new BorderLayout());

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
        pStuffs.add(new StuffPanel(null, true), StuffPanel.GBC);
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
        setLocationRelativeTo(mainFrame);
        setVisible(true);

        ActionListener btnListener = new ButtonHandlerClass();
        for (JButton btn : new JButton[]{jbtSelect, jbtMoreStuff, jbtInputEnd})
            btn.addActionListener(btnListener);
    }

    private class ButtonHandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();

            if (btn == jbtSelect) {
                JFileChooser jf = new JFileChooser();
                jf.setDialogTitle("Select an image file");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files (*.png, *.jpg, *.gif, *.jpeg, *.webp)",
                        "png", "jpg", "gif", "jpeg", "webp");
                jf.setFileFilter(filter);
                jf.showOpenDialog(AddFrame.this);








            }
            else if (btn == jbtMoreStuff) {
                pStuffs.add(new StuffPanel(null, true), StuffPanel.GBC);
                spCenter.setViewportView(pStuffs);
            }
            else if (btn == jbtInputEnd) {

            }
        }
    }
}