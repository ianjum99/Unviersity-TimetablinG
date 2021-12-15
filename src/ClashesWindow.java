import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class ClashesWindow {
    private static ClashesWindow instance=null;
    private int posX, posY;
    private JPanel topBar;
    private JLabel closeButton;
    private JPanel mainPanel;
    private JLabel clashesLabel;
    private JScrollPane clashScrollPane;
    private JPanel clashesPanel;
    private JList<String> clashList;
    private JScrollPane listScrollPane;
    private JLabel clashesSubHeaderLabel;
    private JButton fixClashButton;
    private JFrame frame;
    private JPanel componentHolder;


    public static ClashesWindow getInstance(Timetable gui, DataFactory df, boolean stayHidden){
        if (instance == null && !stayHidden) {
            instance = new ClashesWindow(gui, df);
        } else if (instance == null) {
            instance = new ClashesWindow(gui, df);
            instance.frame.setVisible(false);
        } else if (stayHidden) {

        } else {
            instance.frame.setVisible(true);
            instance.frame.toFront();
        }
        return instance;
    }
    //This first method is used by other classes to check whether an instance of ClashesWindow already exists
    //it will also sometimes open but stay hidden so it can fill the list with clashes but it will not appear
    //until the user has pressed the clashes button on the timetable

    public ClashesWindow(Timetable gui, DataFactory df) {
        init(gui, df);
    }
    //The constructor for the class which passes gui and df to the init method

    private void init(Timetable gui, DataFactory df) {
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        //This creates the jframe and adds the content pane plus a few other customisations

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
        //The 3 listeners above are for the top bar of the window which allow you to drag or close the window

        fixClashButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clashList.getSelectedIndex() > -1) {
                    GUICommands.GUICommands commands = new GUICommands.GUICommands(gui, df);
                    ArrayList<Pair<Activity, Activity>> clashes = currentClashes(gui, df);
                    Activity currentActivity = clashes.get(clashList.getSelectedIndex()).getFirst();
                    Module currentModule = df.getModuleFromActivity(currentActivity);
                    AdminMenu adminMenu = AdminMenu.getInstance(gui, df);
                    Pair<Integer, Integer> firstAvailableSlot = commands.findFirstAvailableSlot(currentActivity.getDay(),
                            currentActivity.getTime(),
                            df.getActivitiesInSameProgrammeYearTerm(df.getProgrammeFromActivity(currentActivity),
                                    currentModule.getYear(),
                                    currentModule.getTerm()), currentActivity);
                    assert firstAvailableSlot != null;
                    commands.solveClash(currentActivity, firstAvailableSlot);
                    updateClashList(currentClashes(gui, df), df);
                    adminMenu.updateGUI(df, gui, commands, currentClashes(gui, df));
                }
            }
        });
        //This button is used to fix clashes, it calls the currentClashes method to get an arraylist of clashes then
        //it calls the firstAvailableSlot method from GUICommands to find an available timeslot to fix the clash
        //once this is found it will then solve the clash using solveClash method from GUICommands
        //then update the clash list and timetable gui
    }

    private void createUIComponents() {
        closeButton = new JLabel(new ImageIcon("images/close.png"));
        listScrollPane = new JScrollPane();
        listScrollPane.setBorder(null);
        clashList = new JList<>();
    }
    //Creates a close button with an icon, a scroll pane and a list

    public void updateClashList(ArrayList<Pair<Activity, Activity>> currentClashes, DataFactory df) {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Pair<Activity,Activity> activity: currentClashes) {
            Activity firstActivity = activity.getFirst();
            Activity secondActivity = activity.getSecond();
            String firstActivityDay = activityDayConverter(firstActivity.getDay());
            String secondActivityDay = activityDayConverter(secondActivity.getDay());
            String clash = String.format("Clash Between: (%s, %s, %s:00-%s:00, %s) and (%s, %s, %s:00-%s:00, %s)",
                    df.getModuleFromActivity(firstActivity).getId(),
                    firstActivityDay,
                    firstActivity.getTime(),
                    firstActivity.getTime()+firstActivity.getDuration(),
                    firstActivity.getType(),
                    df.getModuleFromActivity(secondActivity).getId(),
                    secondActivityDay,
                    secondActivity.getTime(),
                    secondActivity.getTime()+secondActivity.getDuration(),
                    secondActivity.getType());
            listModel.addElement(clash);
        }
        clashList.setModel(listModel);
    }
    //The updateClashList is used for updating the clash list with details of the clash, it gets a list of the clashes
    //and creates a new listModel then it adds pairs but first they are formatted so they are more readable by a user,
    //these are added one by one until there are no more clashes to add then the entire listModel is added to
    //the clashList JList

    private String activityDayConverter(Integer day) {
        String activityDay = "";
        switch (day) {
            case 0 -> activityDay = "Monday";
            case 1 -> activityDay = "Tuesday";
            case 2 -> activityDay = "Wednesday";
            case 3 -> activityDay = "Thursday";
            case 4 -> activityDay = "Friday";
        }
        return activityDay;
    }
    //A simple method used to convert numbers to days as the data structure in data.json stores numbers instead of
    //name of days

    private ArrayList<Pair<Activity, Activity>> currentClashes (Timetable gui, DataFactory df) {
        AdminMenu adminMenu = AdminMenu.getInstance(gui, df);
        return adminMenu.getCurrentClashes(df, gui);
    }
    //This calls the getter from AdminMenu to fetch the current clashes in the timetable
}
