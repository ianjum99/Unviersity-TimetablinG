import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class TimetableGUI {

    private JLabel timetableLabel;
    private JButton menuButton;
    private JPanel mainPanel;
    private JLabel mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel;
    private JLabel closeButton;
    private JFrame frame;
    private JPanel topBar;
    private JLabel nineLabel, tenLabel, elevenLabel, twelveLabel, thirteenLabel, fourteenLabel, fifteenLabel;
    private JLabel sixteenLabel, seventeenLabel, eighteenLabel, nineteenLabel, twentyLabel;


    private int posX, posY;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TimetableGUI window = new TimetableGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public TimetableGUI() {
        init();
    }

    private void init() {
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
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

        // starts a new instance of MenuGUI and calls the getInstance() method to check if one already exists
        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MenuGUI menuWindow  = MenuGUI.getInstance();
            }
        });

    }


    private void createUIComponents() {
        closeButton = new JLabel((new ImageIcon("images/close.png")));
    }
}

