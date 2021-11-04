
import javax.swing.*;

public class GUI {

    private JLabel uniTimetable;
    private JLabel Date;
    private JButton selectionButton;
    private JPanel mainPanel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Timetable");
        frame.setContentPane(new GUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }
}
