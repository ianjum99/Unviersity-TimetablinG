import kotlin.Pair;
import scala.Int;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MenuGUI {
    private JPanel mainPanel, topBar;
    private JLabel adminMenuLabel;
    private JLabel closeButton;
    private JLabel addLabel, removeLabel;
    private JTextField programmeNameField;
    private JPanel addPanel, viewAndRemovePanel;
    private JLabel programmeName, underOrPostLabel;
    private JLabel addProgrammeSectionLabel;
    private JButton addProgrammeButton;
    private JLabel moduleProgrammeSelectionLabel;
    private JComboBox moduleProgrammeSelectionDropdown;
    private JComboBox underOrPostDropdown;
    private JLabel addModuleSectionLabel;
    private JTextField moduleNameField;
    private JLabel moduleTermLabel, moduleNameLabel, moduleOptionalLabel, moduleSelectionLabel;
    private JComboBox moduleTermDropdown, moduleOptionalDropdown, moduleSelectionDropdown;
    private JButton addModuleButton;
    private JLabel addActivitySectionLabel, activityTypeLabel, startTimeLabel, timeDurationLabel;
    private JTextField activityNameField;
    private JComboBox activityTypeDropdown;
    private JButton addActivityButton;
    private JLabel yearOfStudyLabel;
    private JComboBox yearOfStudyDropdown;
    private JLabel activityProgrammeSelectionLabel;
    private JComboBox activityProgrammeSelectionDropdown;
    private JComboBox startTimeDropdown;
    private JComboBox timeDurationDropdown;
    private JLabel activityDayLabel;
    private JComboBox dayDropdown;
    private JLabel removeProgrammeSelectionLabel;
    private JComboBox removeProgrammeDropdown;
    private JButton removeProgrammeButton;
    private JLabel removeProgrammeSectionLabel;
    private JLabel removeModuleSectionLabel;
    private JLabel removeModuleSelectionLabel;
    private JComboBox removeModuleDropdown;
    private JButton removeModuleButton;
    private JLabel removeActivitySectionLabel;
    private JLabel removeActivitySelectionLabel;
    private JComboBox removeActivityDropdown;
    private JButton removeActivityButton;
    private JLabel viewProgrammesLabel;
    private JLabel viewProgrammeSelectionLabel;
    private JLabel viewYearOfStudyLabel;
    private JComboBox viewYearOfStudyDropdown;
    private JComboBox viewProgrammeDropdown;
    private JLabel viewTermLabel;
    private JComboBox viewTermDropdown;
    private JButton viewProgrammeButton;
    private JFrame frame;

    private static MenuGUI instance=null;
    private int posX, posY;


    public MenuGUI(TimetableGUI gui, DataFactory df) {
        GUICommands.GUICommands commands = new GUICommands.GUICommands(gui, df);
        init(gui, df, commands);
    }


    public static MenuGUI getInstance(TimetableGUI gui, DataFactory df){
        if (instance == null) {
            instance = new MenuGUI(gui, df);
        }
        instance.frame.toFront();
        return instance;
    }

    private void init(TimetableGUI gui, DataFactory df, GUICommands.GUICommands commands) {
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));

        programmeSelectionBoxFiller(df, moduleProgrammeSelectionDropdown, activityProgrammeSelectionDropdown, viewProgrammeDropdown, removeProgrammeDropdown);

        Programme programmeInstance = getProgrammeInstance(df, activityProgrammeSelectionDropdown);

        for (Module module : programmeInstance.getModules()) {
            moduleSelectionDropdown.addItem(module.getName());
        }

        viewSectionBoxFiller(df, viewProgrammeDropdown);

        removeSectionBoxFiller(df, removeProgrammeDropdown, "module");

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

        addProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (programmeNameField.getText().length() > 4) {
                    String programmeType = underOrPostDropdown.getSelectedItem().toString();
                    if (programmeType == "Undergraduate") {
                        programmeType = "U";
                    } else {
                        programmeType = "P";
                    }
                    Programme programme = new Programme(programmeNameField.getText(), programmeType, new ArrayList<Module>());
                    df.add(programme);
                    programmeSelectionBoxFiller(df, moduleProgrammeSelectionDropdown, activityProgrammeSelectionDropdown, viewProgrammeDropdown, removeProgrammeDropdown);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter a programme name with at least 4 characters");
                }
            }
        });


        addModuleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Programme programmeInstance = getProgrammeInstance(df, moduleProgrammeSelectionDropdown);
                String compulsory = moduleOptionalDropdown.getSelectedItem().toString();
                switch (compulsory) {
                    case "Compulsory":
                        compulsory = "true";
                    case "Optional":
                        compulsory = "false";
                }
                Module module = new Module(df.generateModuleId(programmeInstance),
                        Integer.parseInt(yearOfStudyDropdown.getSelectedItem().toString()),
                        moduleNameField.getText(),
                        Boolean.parseBoolean(compulsory),
                        Integer.parseInt(moduleTermDropdown.getSelectedItem().toString()),
                        new ArrayList<Activity>());
                df.createModule(programmeInstance, module);
                moduleSelectionDropdown.addItem(module.getName());
                removeModuleDropdown.addItem(module.getName());
                viewSectionBoxFiller(df, viewProgrammeDropdown);
            }

        });


        addActivityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (startTimeDropdown.getSelectedItem() == "20:00" && timeDurationDropdown.getSelectedItem() == "2") {
                    JOptionPane.showMessageDialog(frame, "You cannot add a 2 hour activity at 8pm");
                } else {
                    Module module = (df.getModuleInstanceFromString((String) moduleSelectionDropdown.getSelectedItem()));
                    String day = dayDropdown.getSelectedItem().toString();
                    String time = startTimeDropdown.getSelectedItem().toString().substring(0, 2);
                    switch (day) {
                        case "Monday" -> day = "0";
                        case "Tuesday" -> day = "1";
                        case "Wednesday" -> day = "2";
                        case "Thursday" -> day = "3";
                        case "Friday" -> day = "4";
                    }
                    Activity activity = new Activity(activityTypeDropdown.getSelectedItem().toString(),
                            Integer.parseInt(day),
                            Integer.parseInt(time),
                            Integer.parseInt(timeDurationDropdown.getSelectedItem().toString())
                    );
                    df.createActivity(module, activity);
                    if (moduleSelectionDropdown.getSelectedItem() == removeModuleDropdown.getSelectedItem()) {
                        removeActivityBoxFiller(df, moduleSelectionDropdown);
                    }
                    if (gui.programmeNameLabel.getText() != "Programme Info") {
                        updateGUI(df, gui, commands, getCurrentClashes(df));
                    }
                }
            }
        });


        activityProgrammeSelectionDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Programme programmeInstance = getProgrammeInstance(df, activityProgrammeSelectionDropdown);
                    moduleSelectionDropdown.removeAllItems();
                    for (Module module : programmeInstance.getModules()) {
                        moduleSelectionDropdown.addItem(module.getName());
                    }
                }
            }
        });


        moduleProgrammeSelectionDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Programme programmeInstance = getProgrammeInstance(df, moduleProgrammeSelectionDropdown);
                    if (programmeInstance.getType().equals("P")) {
                        yearOfStudyDropdown.removeAllItems();
                        yearOfStudyDropdown.addItem(1);
                    } else {
                        yearOfStudyDropdown.removeAllItems();
                        yearOfStudyDropdown.addItem(1);
                        yearOfStudyDropdown.addItem(2);
                        yearOfStudyDropdown.addItem(3);
                    }
                }
            }
        });

        viewProgrammeDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    viewYearOfStudyDropdown.removeAllItems();
                    viewTermDropdown.removeAllItems();
                    viewSectionBoxFiller(df, viewProgrammeDropdown);
                }
            }
        });

        removeProgrammeDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    removeSectionBoxFiller(df, removeProgrammeDropdown, "module");
                }
            }
        });

        removeModuleDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    removeSectionBoxFiller(df, removeProgrammeDropdown, "activity");
                }
            }
        });

        viewProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<Pair<Activity, Activity>> currentClashes = setCurrentClashes(df);
                ClashesGUI clashesGUI = ClashesGUI.getInstance(gui, df, true);
                clashesGUI.updateClashList(currentClashes, df);
                updateGUI(df, gui, commands, currentClashes);
            }
        });

        removeProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gui.programmeNameLabel.getText() == removeProgrammeDropdown.getSelectedItem()) {
                    commands.clearGUI();
                    gui.programmeNameLabel.setText("Programme Name");
                    gui.programmeYearAndTermLabel.setText("Programme Year/Term");
                }
                df.deleteProgramme(getProgrammeInstance(df, removeProgrammeDropdown));
                programmeSelectionBoxFiller(df, moduleProgrammeSelectionDropdown, activityProgrammeSelectionDropdown, viewProgrammeDropdown, removeProgrammeDropdown);
            }
        });

        removeModuleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Programme programmeInstance = getProgrammeInstance(df, removeProgrammeDropdown);
                Module moduleInstance = df.getModuleInstanceFromString((String) removeModuleDropdown.getSelectedItem());
                df.deleteModule(programmeInstance, moduleInstance);
                removeSectionBoxFiller(df, removeProgrammeDropdown, "module");
                if (!gui.programmeNameLabel.getText().equals("Programme Name")) {
                    updateGUI(df, gui, commands, getCurrentClashes(df));
                }
                if (activityProgrammeSelectionDropdown.getSelectedItem() == removeProgrammeDropdown.getSelectedItem()) {
                    moduleSelectionDropdown.removeAllItems();
                    for (Module module : programmeInstance.getModules()) {
                        moduleSelectionDropdown.addItem(module.getName());
                    }
                }
                viewSectionBoxFiller(df, viewProgrammeDropdown);
            }
        });

        removeActivityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Module moduleInstance = df.getModuleInstanceFromString((String) removeModuleDropdown.getSelectedItem());
                df.deleteActivity(moduleInstance, moduleInstance.getActivities().get(removeActivityDropdown.getSelectedIndex()));
                removeSectionBoxFiller(df, removeProgrammeDropdown, "activity");
                if (!gui.programmeNameLabel.getText().equals("Programme Name")) {
                    updateGUI(df, gui, commands, getCurrentClashes(df));
                }
            }
        });
    }

    private void createUIComponents() {
        closeButton = new JLabel((new ImageIcon("images/close.png")));
        programmeNameField = new JTextField();
        moduleNameField = new JTextField();
        activityNameField = new JTextField();
        addModuleButton = new JButton();

        programmeNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        moduleNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        activityNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

    }

    private void programmeSelectionBoxFiller(DataFactory df, JComboBox... args) {
        for (JComboBox box: args) {
            box.removeAllItems();
            for (Programme item: df) {
                box.addItem(item.getName());
            }
        }
    }


    private void viewSectionBoxFiller(DataFactory df, JComboBox viewProgrammeDropdown) {
        Programme programmeInstance = getProgrammeInstance(df, viewProgrammeDropdown);
        ArrayList<Integer> yearDuplicates = new ArrayList<>();
        ArrayList<Integer> termDuplicates = new ArrayList<>();
        Collections.addAll(yearDuplicates, 1, 2, 3);
        Collections.addAll(termDuplicates, 1, 2);

        viewYearOfStudyDropdown.removeAllItems();
        viewTermDropdown.removeAllItems();
        for (Module module: programmeInstance.getModules()) {
            if (yearDuplicates.contains(module.getYear())) {
                yearDuplicates.remove((Integer) module.getYear());
                viewYearOfStudyDropdown.addItem(module.getYear());
            }
            if (termDuplicates.contains(module.getTerm())) {
                termDuplicates.remove((Integer) module.getTerm());
                viewTermDropdown.addItem(module.getTerm());
            }
        }

    }


    private void removeSectionBoxFiller(DataFactory df, JComboBox removeProgrammeDropdown, String moduleOrActivity) {
        switch (moduleOrActivity) {
            case "module":
                removeModuleDropdown.removeAllItems();
                Programme programmeInstance = getProgrammeInstance(df, removeProgrammeDropdown);
                for (Module module: programmeInstance.getModules()) {
                    removeModuleDropdown.addItem(module.getName());
                }
                removeActivityBoxFiller(df, removeModuleDropdown);
                break;
            case "activity":
                removeActivityBoxFiller(df, removeModuleDropdown);
        }
    }


    private void removeActivityBoxFiller(DataFactory df, JComboBox removeModuleDropdown) {
        removeActivityDropdown.removeAllItems();
        if (removeModuleDropdown.getSelectedItem() == null) {

        } else {
            Module moduleInstance = (df.getModuleInstanceFromString((String) removeModuleDropdown.getSelectedItem()));
            for (Activity activity: moduleInstance.getActivities()) {
                Integer dayInt = activity.getDay();
                String stringInt = null;
                switch (dayInt) {
                    case 0:
                        stringInt = "Monday";
                        break;
                    case 1:
                        stringInt = "Tuesday";
                        break;
                    case 2:
                        stringInt = "Wednesday";
                        break;
                    case 3:
                        stringInt = "Thursday";
                        break;
                    case 4:
                        stringInt = "Friday";
                }
                String classActivity = String.format("%s - %s - %d:00", activity.getType(), stringInt, activity.getTime());
                removeActivityDropdown.addItem(classActivity);
            }
        }
    }


    private Programme getProgrammeInstance(DataFactory df, JComboBox programmeDropdown) {
        Programme programmeInstance = df.getProgrammeInstanceFromString((String) programmeDropdown.getSelectedItem());
        return programmeInstance;
    }


    public void updateGUI(DataFactory df, TimetableGUI gui, GUICommands.GUICommands commands, ArrayList<Pair<Activity, Activity>> clashes) {
        commands.populateGUIbyProgramme(getProgrammeInstance(df, viewProgrammeDropdown),
                (Integer) viewYearOfStudyDropdown.getSelectedItem(),
                (Integer) viewTermDropdown.getSelectedItem());
        String programmeYearAndTerm = String.format("Year %s - Term %s",
                viewYearOfStudyDropdown.getSelectedItem(), viewTermDropdown.getSelectedItem());
        gui.programmeNameLabel.setText((String) viewProgrammeDropdown.getSelectedItem());
        gui.programmeYearAndTermLabel.setText(programmeYearAndTerm);
        highlightingClashesOnTimetable(clashes, gui);
    }

    private ArrayList<Pair<Activity, Activity>> setCurrentClashes (DataFactory df) {
        return df.checkForClashes(getProgrammeInstance(df, viewProgrammeDropdown),
                (Integer) viewYearOfStudyDropdown.getSelectedItem(),
                (Integer) viewTermDropdown.getSelectedItem());
    }

    public ArrayList<Pair<Activity, Activity>> getCurrentClashes (DataFactory df) {
        return setCurrentClashes(df);
    }

    public void highlightingClashesOnTimetable(ArrayList<Pair<Activity, Activity>> clashes, TimetableGUI gui) {
        for (Pair<Activity, Activity> clash : clashes) {
            Activity firstActivity = clash.getFirst();
            Activity secondActivity = clash.getSecond();
            List<Integer> startAndEndTimes = List.of(firstActivity.getTime(),
                    firstActivity.getTime() + firstActivity.getDuration(),
                    secondActivity.getTime(),
                    secondActivity.getTime() + secondActivity.getDuration());
            Integer earliestStart = Collections.min(startAndEndTimes);
            Integer maxDuration = Collections.max(startAndEndTimes) - Collections.min(startAndEndTimes);
            for (int i = earliestStart; i < earliestStart+maxDuration; i++) {
                gui.getLabelFromCoordinates(firstActivity.getDay() + 1, i - 8).getParent().setBackground(Color.red);
                gui.getLabelFromCoordinates(firstActivity.getDay() + 1, i - 8).setBackground(Color.red);
            }
        }
    }

}