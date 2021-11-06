import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class GUI {

    private JLabel timetableLabel;
    private JLabel dateLabel;
    private JButton menuButton;
    private JPanel mainPanel;
    private JLabel mondayLabel;
    private JLabel tuesdayLabel;
    private JLabel wednesdayLabel;
    private JLabel thursdayLabel;
    private JLabel fridayLabel;
    private JLabel closeButton;
    private JFrame frame;
    private JPanel topBar;

    private int mouseX, mouseY;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI window = new GUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public GUI() {
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
                frame.setLocation(frame.getX() + e.getX() - mouseX, frame.getY() + e.getY() - mouseY);
            }
        });
        topBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

    }


    private void createUIComponents() {
        closeButton = new JLabel((new ImageIcon("images/close.png")));
    }
}

