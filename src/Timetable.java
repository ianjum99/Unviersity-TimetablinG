import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Timetable {

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
    public JLabel clashAlertLabel;
    private GridLayout grid;
    private JButton buttonGrid;
    int numberOfRows = 13;
    int numberOfColumns = 6;
    private final JPanel[][] componentHolder = new JPanel[numberOfRows][numberOfColumns];
    private ImageIcon icon;
    public Boolean chosenClashDetection;
    private int posX, posY;

    public JPanel[][] getComponentHolder() {
        return componentHolder;
    }
    //Returning a jpanel matrix

    public Timetable getCurrentInstance() {
        return this;
    }
    //Returning the timetable instance

    public JTextArea getLabelFromCoordinates(int column,int row) {
        return (JTextArea) componentHolder[row][column].getComponent(0);
    }
    //Getting a specific jpanel from the jpanel matrix

    public Timetable(DataFactory dataFactory) {
        init(dataFactory);
    }
    //The Timetable constructor is passed datafactory and it calls the init method with that parameter

    private void init(DataFactory dataFactory) {
        ImageIcon icon = new ImageIcon("Images/clashDetection.png");
        String[] options = {"Kotlin", "Scala"};
        //The top variable is the icon for the dialog when the program is first run and the second stores two strings
        //for that same dialog

        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        int questionResponse = JOptionPane.showOptionDialog(frame,
                "Which clash detection do you wish to use?",
                "Clash Detection Selection", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon,
                options, options[0]);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //This creates the jframe and adds the content pane plus a few other customisations
        //it also creates a joptionpane which asks the user which clash detection they would like to use

        if (questionResponse == JOptionPane.YES_OPTION) {
            chosenClashDetection = true;
        } else if (questionResponse == JOptionPane.NO_OPTION) {
            chosenClashDetection = false;
        } else {
            System.exit(0);
        }
        //This sets the chosenClashDetection variable in accordance to the users response or stops the program

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JsonHandler jsonHandler = new JsonHandler();
                jsonHandler.saveJSONFile(dataFactory.toJson());
                System.exit(0);
            }
        });
        //When the close button is pressed this will call the JsonHandler class to save the data to the file

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
        //The two listeners above are to make the window draggable as it's undecorated


        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AdminMenu menuWindow  = AdminMenu.getInstance(getCurrentInstance(), dataFactory);

            }
        });

        clashesButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ClashesWindow clashesWindow  = ClashesWindow.getInstance(getCurrentInstance(), dataFactory, false);
            }
        });
        //The two buttons above are to create an instance of the clashesWindow or adminMenu class

    }


    private void createUIComponents() {
        closeButton = new JLabel(new ImageIcon("Images/close.png"));
        timetablePanel = new JPanel();
        ArrayList<JLabel> timeLabels = new ArrayList<JLabel>();
        ArrayList<JLabel> dayLabels = new ArrayList<JLabel>();
        Font outerLabelsFont = new Font("Arial", Font.BOLD, 16);
        Font innerLabelsFont = new Font("Arial", Font.PLAIN, 10);
        //The first line creates a close button and gives it an icon, the next lines create a jpanel and two arraylists
        //the last two lines create two font variables

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
        //The two for loops make time and day labels for the timetable grid as well they apply fonts and colours


        timetablePanel.setLayout(new GridLayout(numberOfRows, numberOfColumns, 1, 1));
        //The jpanel has a new grid layout set
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
        //The nested for loop adds smaller jpanels inside the larger timetable jpanel, creating a matrix of jpanels
        //adding a layout and a colour to each one

        for (int row = 1; row < numberOfRows; row++){
            for (int column = 1; column < numberOfColumns; column++) {
                componentHolder[row][column].add(new JTextArea(""));
                getLabelFromCoordinates(column, row).setBackground(Color.decode("#2D142C"));
                getLabelFromCoordinates(column, row).setForeground(Color.white);
                getLabelFromCoordinates(column, row).setFont(innerLabelsFont);
                getLabelFromCoordinates(column, row).setEditable(false);
            }
        }
        //This nested for loop goes through each jpanel in the matrix (timetable) and adds an empty jtextarea with
        //a few colour and font customisations

        for (int row = 0; row< timeLabels.size(); row++) {
            componentHolder[row+1][0].add(timeLabels.get(row));
        }
        //Adding hour labels to GUI

        for (int column = 0; column< dayLabels.size(); column++) {
            componentHolder[0][column+1].add(dayLabels.get(column));
        }
        //Adding day labels to GUI
    }
}

