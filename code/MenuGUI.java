import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;

public class MenuGUI {
    private JPanel mainPanel;
    private JPanel topBar;
    private JLabel adminMenuLabel;
    private JLabel closeButton;
    private JFrame frame;

    private static MenuGUI instance=null;

    private int posX, posY;

    public MenuGUI() {
        init();
    }

    // checks if an instance exists before opening a new MenuGUI window
    public static MenuGUI getInstance(){
        if (instance == null) {
            instance = new MenuGUI();
        }
        return instance;
    }

    private void init() {
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // this close button on the MenuGUI closes the JFrame but it doesn't end the instance
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
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
        closeButton = new JLabel((new ImageIcon("images/close.png")));
    }
}
