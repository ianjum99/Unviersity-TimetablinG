import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static java.awt.Component.CENTER_ALIGNMENT;

public class TimetableGUI {

    private JLabel timetableLabel;
    private JButton menuButton;
    private JPanel mainPanel;
    private JLabel closeButton;
    private JFrame frame;
    private JPanel topBar;
    private JPanel timetablePanel;
    public JLabel programmeNameLabel;
    public JLabel programmeYearAndTermLabel;
    private JButton clashesButton;
    private GridLayout grid;
    private JButton buttonGrid;
    int numberOfRows = 13;
    int numberOfColumns = 6;
    private final JPanel[][] componentHolder = new JPanel[numberOfRows][numberOfColumns];



    public JPanel[][] getComponentHolder() {
        return componentHolder;
    }

    public TimetableGUI getCurrentInstance() {
        return this;
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
        String chosenClashDetection = "";
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        int dialogResponse = JOptionPane.showConfirmDialog(frame,
                "If you want to use Kotlin for Clash Detection press Yes, otherwise press No to use Scala",
                "Clash Detection Selection", JOptionPane.YES_NO_OPTION);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        if (dialogResponse == JOptionPane.YES_OPTION) {
            chosenClashDetection = "Kotlin";
        } else {
            chosenClashDetection = "Scala";
        }

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                JsonHandler jsonHandler = new JsonHandler();
//                jsonHandler.saveJsonFile(df.toJson());
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
                MenuGUI menuWindow  = MenuGUI.getInstance(getCurrentInstance(), df);
            }
        });

        clashesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClashesGUI clashesWindow  = ClashesGUI.getInstance(getCurrentInstance(), df);
            }
        });

    }


    private void createUIComponents() {
        closeButton = new JLabel((new ImageIcon("images/close.png")));
        timetablePanel = new JPanel();
        ArrayList<JLabel> timeLabels = new ArrayList<JLabel>();
        ArrayList<JLabel> dayLabels = new ArrayList<JLabel>();
        Font outerLabelsFont = new Font("Arial", Font.BOLD, 16);
        Font innerLabelsFont = new Font("Arial", Font.PLAIN, 10);

        for (int hour=0; hour<=numberOfRows-2; hour++) {
            timeLabels.add(new JLabel(String.format("%d:00",hour+9)));
            timeLabels.get(hour).setFont(outerLabelsFont);
            timeLabels.get(hour).setForeground(Color.white);
        }

        for (int day=0; day<=numberOfColumns-2; day++) {
            dayLabels.add(new JLabel(String.valueOf(DayOfWeek.of(day + 1))));
            dayLabels.get(day).setFont(outerLabelsFont);
            dayLabels.get(day).setForeground(Color.white);
        }


        timetablePanel.setLayout(new GridLayout(numberOfRows, numberOfColumns, 1, 1));

        for (int row = 0; row < numberOfRows; row++) {
            for( int column = 0; column < numberOfColumns; column++) {
                if (row <= numberOfRows && column == 0 || row == 0) {
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
                getLabelFromCoordinates(column, row).setFont(innerLabelsFont);
                getLabelFromCoordinates(column, row).setEditable(false);
            }
        }

        //Adding hour labels to GUI
        for (int row = 0; row< timeLabels.size(); row++) {
            componentHolder[row+1][0].add(timeLabels.get(row));
        }

        //Adding day labels to GUI
        for (int column = 0; column< dayLabels.size(); column++) {
            componentHolder[0][column+1].add(dayLabels.get(column));
        }
    }
}

