import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import static java.awt.Component.CENTER_ALIGNMENT;

public class TimetableGUI {

    private JLabel timetableLabel;
    private JButton menuButton;
    private JPanel mainPanel;
    private JLabel closeButton;
    private JFrame frame;
    private JPanel topBar;
    private JLabel dateLabel;
    private JPanel timetablePanel;
    private GridLayout grid;
    private JButton buttonGrid;
    private JLabel nineLabel, tenLabel, elevenLabel, twelveLabel, thirteenLabel, fourteenLabel, fifteenLabel,
            sixteenLabel, seventeenLabel, eighteenLabel, nineteenLabel, twentyLabel;
    private ArrayList<JLabel> timeLabels;
    private JLabel mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel;
    private ArrayList<JLabel> dayLabels;
    int numberOfRows = 13;
    int numberOfColumns = 6;
    private JPanel[][] componentHolder = new JPanel[numberOfRows][numberOfColumns];



    public JPanel[][] getComponentHolder() {
        return componentHolder;
    }

    public JTextArea getLabelFromCoordinates(int column,int row) {
        return (JTextArea) componentHolder[row][column].getComponent(0);
    }

    private int posX, posY;

    public static void main(DataFactory df) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TimetableGUI window = new TimetableGUI(df);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public TimetableGUI(DataFactory dataFactory) {
        init(dataFactory);
    }

    private void init(DataFactory dataFactory) {
        DataFactory df = dataFactory;
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);


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

        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MenuGUI menuWindow  = MenuGUI.getInstance(df);
            }
        });

    }


    private void createUIComponents() {
        closeButton = new JLabel((new ImageIcon("images/close.png")));
        timetablePanel = new JPanel();
        timeLabels = new ArrayList<JLabel>();
        dayLabels = new ArrayList<JLabel>();

        nineLabel = new JLabel("9:00");
        tenLabel = new JLabel("10:00");
        elevenLabel = new JLabel("11:00");
        twelveLabel = new JLabel("12:00");
        thirteenLabel = new JLabel("13:00");
        fourteenLabel = new JLabel("14:00");
        fifteenLabel = new JLabel("15:00");
        sixteenLabel = new JLabel("16:00");
        seventeenLabel = new JLabel("17:00");
        eighteenLabel = new JLabel("18:00");
        nineteenLabel = new JLabel("19:00");
        twentyLabel = new JLabel("20:00");

        mondayLabel = new JLabel("Monday");
        tuesdayLabel = new JLabel("Tuesday");
        wednesdayLabel = new JLabel("Wednesday");
        thursdayLabel = new JLabel("Thursday");
        fridayLabel = new JLabel("Friday");


        Collections.addAll(timeLabels, nineLabel, tenLabel, elevenLabel, twelveLabel, thirteenLabel, fourteenLabel, fifteenLabel,
                sixteenLabel, seventeenLabel, eighteenLabel, nineteenLabel, twentyLabel);

        Collections.addAll(dayLabels, mondayLabel, tuesdayLabel, wednesdayLabel, thursdayLabel, fridayLabel);

        for (JLabel timeLabel: timeLabels) {
            timeLabel.setForeground(Color.white);
            timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        }

        for (JLabel dayLabel: dayLabels) {
            dayLabel.setForeground(Color.white);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 16));
        }

        timetablePanel.setLayout(new GridLayout(numberOfRows, numberOfColumns, 1, 1));


        for (int row = 0; row < numberOfRows; row++) {
            for( int column = 0; column < numberOfColumns; column++) {
                if (row <= 12 && column == 0 || row == 0 && column <= 5) {
                    componentHolder[row][column] = new JPanel(new FlowLayout(FlowLayout.CENTER));
                }
                else {
                    componentHolder[row][column] = new JPanel(new FlowLayout(FlowLayout.LEFT));

                }
                componentHolder[row][column].setBackground(Color.decode("#2D142C"));
                timetablePanel.add(componentHolder[row][column]);
            }
        }

        for (int row = 1; row < numberOfRows; row++){
            for (int column = 1; column < numberOfColumns; column++) {
                componentHolder[row][column].add(new JTextArea(""));
                getLabelFromCoordinates(column, row).setBackground(Color.decode("#2D142C"));
                getLabelFromCoordinates(column, row).setForeground(Color.white);
                getLabelFromCoordinates(column, row).setFont(new Font("Arial", Font.PLAIN, 10));
                getLabelFromCoordinates(column, row).setEditable(false);
            }
        }

//        componentHolder[5][2].setBackground(Color.decode("#606602"));
//        getLabelFromCoordinates(2, 5).setBackground(Color.decode("#606602"));
//        componentHolder[6][2].setBackground(Color.decode("#606602"));
//        getLabelFromCoordinates(2, 6).setBackground(Color.decode("#606602"));

        //Hours
        componentHolder[1][0].add(nineLabel);
        componentHolder[2][0].add(tenLabel);
        componentHolder[3][0].add(elevenLabel);
        componentHolder[4][0].add(twelveLabel);
        componentHolder[5][0].add(thirteenLabel);
        componentHolder[6][0].add(fourteenLabel);
        componentHolder[7][0].add(fifteenLabel);
        componentHolder[8][0].add(sixteenLabel);
        componentHolder[9][0].add(seventeenLabel);
        componentHolder[10][0].add(eighteenLabel);
        componentHolder[11][0].add(nineteenLabel);
        componentHolder[12][0].add(twentyLabel);


        //Days
        componentHolder[0][1].add(mondayLabel);
        componentHolder[0][2].add(tuesdayLabel);
        componentHolder[0][3].add(wednesdayLabel);
        componentHolder[0][4].add(thursdayLabel);
        componentHolder[0][5].add(fridayLabel);

    }
}

