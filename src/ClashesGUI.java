import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Time;

public class ClashesGUI {
    private static ClashesGUI instance=null;
    private int posX, posY;
    private JPanel topBar;
    private JLabel closeButton;
    private JPanel mainPanel;
    private JLabel clashesLabel;
    private JScrollPane clashScrollPane;
    private JPanel clashesPanel;
    private JList clashList;
    private JScrollPane listScrollPane;
    private JLabel clashesSubHeaderLabel;
    private JFrame frame;
    private JPanel componentHolder;


    public static ClashesGUI getInstance(TimetableGUI gui, DataFactory df){
        if (instance == null) {
            instance = new ClashesGUI(gui, df);
        }
        return instance;
    }

    public ClashesGUI(TimetableGUI gui, DataFactory df) {
        init(gui, df);
    }

    private void init(TimetableGUI gui, DataFactory df) {
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                instance = null;
            }
        });

        topBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(frame.getX() + e.getX() - posX, frame.getY() + e.getY() - posY);
            }
        });
        topBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                posX = e.getX();
                posY = e.getY();
            }
        });
    }

    private void createUIComponents() {
        closeButton = new JLabel(new ImageIcon("images/close.png"));
        listScrollPane = new JScrollPane();
        listScrollPane.setBorder(null);
    }

}
