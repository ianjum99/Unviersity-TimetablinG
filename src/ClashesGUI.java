import kotlin.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

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
    private JButton fixClashButton;
    private JFrame frame;
    private JPanel componentHolder;


    public static ClashesGUI getInstance(TimetableGUI gui, DataFactory df, boolean stayHidden){
        if (instance == null && !stayHidden) {
            instance = new ClashesGUI(gui, df);
        } else if (instance == null) {
            instance = new ClashesGUI(gui, df);
            instance.frame.setVisible(false);
        } else if (stayHidden) {

        } else {
            instance.frame.setVisible(true);
            instance.frame.toFront();
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
                //instance = null;
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

        fixClashButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clashList.getSelectedIndex() > -1) {
                    GUICommands.GUICommands commands = new GUICommands.GUICommands(gui, df);
                    ArrayList<Pair<Activity, Activity>> clashes = currentClashes(gui, df);
                    Activity currentActivity = clashes.get(clashList.getSelectedIndex()).getFirst();
                    Module currentModule = df.getModuleFromActivity(currentActivity);
                    MenuGUI menuGUI = MenuGUI.getInstance(gui, df);
                    Pair<Integer, Integer> firstAvailableSlot = commands.findFirstAvailableSlot(currentActivity.getDay(),
                            currentActivity.getTime(),
                            df.getActivitiesInSameProgrammeYearTerm(df.getProgrammeFromActivity(currentActivity),
                                    currentModule.getYear(),
                                    currentModule.getTerm()), currentActivity);
                    commands.solveClash(currentActivity, firstAvailableSlot);
                    updateClashList(currentClashes(gui, df), df);
                    menuGUI.updateGUI(df, gui, commands);
                }
            }
        });
    }

    private void createUIComponents() {
        closeButton = new JLabel(new ImageIcon("images/close.png"));
        listScrollPane = new JScrollPane();
        listScrollPane.setBorder(null);
        clashList = new JList();
    }

    public void updateClashList(ArrayList<Pair<Activity, Activity>> currentClashes, DataFactory df) {
        DefaultListModel<String> listModel = new DefaultListModel<String>();
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

    private ArrayList<Pair<Activity, Activity>> currentClashes (TimetableGUI gui, DataFactory df) {
        MenuGUI menuGUI = MenuGUI.getInstance(gui, df);
        return menuGUI.getCurrentClashes(df);
    }

}
