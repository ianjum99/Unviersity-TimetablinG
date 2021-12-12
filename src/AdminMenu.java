import kotlin.Pair;
import scala.collection.JavaConverters;
import scala.jdk.javaapi.CollectionConverters;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class AdminMenu {
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
    private JComboBox<String> moduleProgrammeSelectionDropdown;
    private JComboBox<String> underOrPostDropdown;
    private JLabel addModuleSectionLabel;
    private JTextField moduleNameField;
    private JLabel moduleTermLabel, moduleNameLabel, moduleOptionalLabel, moduleSelectionLabel;
    private JComboBox<Integer> moduleTermDropdown;
    private JComboBox<Boolean> moduleOptionalDropdown;
    private JComboBox<String> moduleSelectionDropdown;
    private JButton addModuleButton;
    private JLabel addActivitySectionLabel, activityTypeLabel, startTimeLabel, timeDurationLabel;
    private JComboBox<String> activityTypeDropdown;
    private JButton addActivityButton;
    private JLabel yearOfStudyLabel;
    private JComboBox<Integer> yearOfStudyDropdown;
    private JLabel activityProgrammeSelectionLabel;
    private JComboBox<String> activityProgrammeSelectionDropdown;
    private JComboBox<String> startTimeDropdown;
    private JComboBox<Integer> timeDurationDropdown;
    private JLabel activityDayLabel;
    private JComboBox<String> dayDropdown;
    private JLabel removeProgrammeSelectionLabel;
    private JComboBox<String> removeProgrammeDropdown;
    private JButton removeProgrammeButton;
    private JLabel removeProgrammeSectionLabel;
    private JLabel removeModuleSectionLabel;
    private JLabel removeModuleSelectionLabel;
    private JComboBox<String> removeModuleDropdown;
    private JButton removeModuleButton;
    private JLabel removeActivitySectionLabel;
    private JLabel removeActivitySelectionLabel;
    private JComboBox<String> removeActivityDropdown;
    private JButton removeActivityButton;
    private JLabel viewProgrammesLabel;
    private JLabel viewProgrammeSelectionLabel;
    private JLabel viewYearOfStudyLabel;
    private JComboBox<Integer> viewYearOfStudyDropdown;
    private JComboBox<String> viewProgrammeDropdown;
    private JLabel viewTermLabel;
    private JComboBox<Integer> viewTermDropdown;
    private JButton viewProgrammeButton;
    private JFrame frame;

    private static AdminMenu instance=null;
    private int posX, posY;

    public AdminMenu(Timetable gui, DataFactory df)  {
        GUICommands.GUICommands commands = new GUICommands.GUICommands(gui, df);
        init(gui, df, commands);
    }


    public static AdminMenu getInstance(Timetable gui, DataFactory df){
        if (instance == null) {
            instance = new AdminMenu(gui, df);
        }
        instance.frame.toFront();
        return instance;
    }

    private void init(Timetable gui, DataFactory df, GUICommands.GUICommands commands) {
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
                    String programmeType = (String) underOrPostDropdown.getSelectedItem();
                    assert programmeType != null;
                    if (programmeType.equals("Undergraduate")) {
                        programmeType = "U";
                    } else {
                        programmeType = "P";
                    }
                    Programme programme = new Programme(programmeNameField.getText(), programmeType, new ArrayList<>());
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
                if (!moduleNameField.getText().isEmpty()) {
                    boolean compulsoryBool = Objects.requireNonNull(moduleOptionalDropdown.getSelectedItem()).toString().equals("Compulsory");
                    Programme programmeInstance = getProgrammeInstance(df, moduleProgrammeSelectionDropdown);
                    Module module = new Module(df.generateModuleId(programmeInstance),
                            Integer.parseInt(Objects.requireNonNull(yearOfStudyDropdown.getSelectedItem()).toString()),
                            moduleNameField.getText(),
                            compulsoryBool,
                            Integer.parseInt(Objects.requireNonNull(moduleTermDropdown.getSelectedItem()).toString()),
                            new ArrayList<>());
                    df.createModule(programmeInstance, module);
                    moduleSelectionDropdown.addItem(module.getName());
                    removeModuleDropdown.addItem(module.getName());
                    viewSectionBoxFiller(df, viewProgrammeDropdown);
                }
            }

        });


        addActivityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (startTimeDropdown.getSelectedItem() == "20:00" && timeDurationDropdown.getSelectedItem() == "2") {
                    JOptionPane.showMessageDialog(frame, "You cannot add a 2 hour activity at 8pm");
                } else {
                    Module module = (df.getModuleInstanceFromString((String) Objects.requireNonNull(moduleSelectionDropdown.getSelectedItem())));
                    String day = (String) dayDropdown.getSelectedItem();
                    String time = ((String) Objects.requireNonNull(startTimeDropdown.getSelectedItem())).substring(0, 2);
                    switch (Objects.requireNonNull(day)) {
                        case "Monday" -> day = "0";
                        case "Tuesday" -> day = "1";
                        case "Wednesday" -> day = "2";
                        case "Thursday" -> day = "3";
                        case "Friday" -> day = "4";
                    }
                    Activity activity = new Activity((String) Objects.requireNonNull(activityTypeDropdown.getSelectedItem()),
                            Integer.parseInt(day),
                            Integer.parseInt(time),
                            Integer.parseInt((String) Objects.requireNonNull(timeDurationDropdown.getSelectedItem()))
                    );
                    df.createActivity(module, activity);
                    if (moduleSelectionDropdown.getSelectedItem() == removeModuleDropdown.getSelectedItem()) {
                        removeActivityBoxFiller(df, moduleSelectionDropdown);
                    }
                    if (!gui.programmeNameLabel.getText().equals("Programme Info")) {
                        updateGUI(df, gui, commands, getCurrentClashes(df, gui));
                    }
                }
            }
        });


        activityProgrammeSelectionDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Programme programmeInstance12 = getProgrammeInstance(df, activityProgrammeSelectionDropdown);
                moduleSelectionDropdown.removeAllItems();
                for (Module module : programmeInstance12.getModules()) {
                    moduleSelectionDropdown.addItem(module.getName());
                }
            }
        });


        moduleProgrammeSelectionDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Programme programmeInstance1 = getProgrammeInstance(df, moduleProgrammeSelectionDropdown);
                if (programmeInstance1.getType().equals("P")) {
                    yearOfStudyDropdown.removeAllItems();
                    yearOfStudyDropdown.addItem(1);
                } else {
                    yearOfStudyDropdown.removeAllItems();
                    yearOfStudyDropdown.addItem(1);
                    yearOfStudyDropdown.addItem(2);
                    yearOfStudyDropdown.addItem(3);
                }
            }
        });

        viewProgrammeDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                viewYearOfStudyDropdown.removeAllItems();
                viewTermDropdown.removeAllItems();
                viewSectionBoxFiller(df, viewProgrammeDropdown);
            }
        });

        removeProgrammeDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                removeSectionBoxFiller(df, removeProgrammeDropdown, "module");
            }
        });

        removeModuleDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                removeSectionBoxFiller(df, removeProgrammeDropdown, "activity");
            }
        });

        viewProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<Pair<Activity, Activity>> currentClashes = setCurrentClashes(df, gui);
                ClashesWindow clashesGUI = ClashesWindow.getInstance(gui, df, true);
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
                Module moduleInstance = df.getModuleInstanceFromString((String) Objects.requireNonNull(removeModuleDropdown.getSelectedItem()));
                df.deleteModule(programmeInstance, moduleInstance);
                removeSectionBoxFiller(df, removeProgrammeDropdown, "module");
                if (!gui.programmeNameLabel.getText().equals("Programme Name")) {
                    updateGUI(df, gui, commands, getCurrentClashes(df, gui));
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
                Module moduleInstance = df.getModuleInstanceFromString((String) Objects.requireNonNull(removeModuleDropdown.getSelectedItem()));
                df.deleteActivity(moduleInstance, moduleInstance.getActivities().get(removeActivityDropdown.getSelectedIndex()));
                removeSectionBoxFiller(df, removeProgrammeDropdown, "activity");
                if (!gui.programmeNameLabel.getText().equals("Programme Name")) {
                    updateGUI(df, gui, commands, getCurrentClashes(df, gui));
                }
            }
        });
    }

    private void createUIComponents() {
        closeButton = new JLabel((new ImageIcon("images/close.png")));
        programmeNameField = new JTextField();
        moduleNameField = new JTextField();
        JTextField activityNameField = new JTextField();
        //addModuleButton = new JButton();

        programmeNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        moduleNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        activityNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));

    }

    @SafeVarargs
    private void programmeSelectionBoxFiller(DataFactory df, JComboBox<String>... args) {
        for (JComboBox<String> box: args) {
            box.removeAllItems();
            for (Programme item: df) {
                box.addItem(item.getName());
            }
        }
    }


    private void viewSectionBoxFiller(DataFactory df, JComboBox<String> viewProgrammeDropdown) {
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


    private void removeSectionBoxFiller(DataFactory df, JComboBox<String> removeProgrammeDropdown, String moduleOrActivity) {
        switch (moduleOrActivity) {
            case "module" -> {
                removeModuleDropdown.removeAllItems();
                Programme programmeInstance = getProgrammeInstance(df, removeProgrammeDropdown);
                for (Module module : programmeInstance.getModules()) {
                    removeModuleDropdown.addItem(module.getName());
                }
                removeActivityBoxFiller(df, removeModuleDropdown);
            }
            case "activity" -> removeActivityBoxFiller(df, removeModuleDropdown);
        }
    }


    private void removeActivityBoxFiller(DataFactory df, JComboBox<String> removeModuleDropdown) {
        removeActivityDropdown.removeAllItems();
        if (removeModuleDropdown.getSelectedItem() != null) {
            Module moduleInstance = (df.getModuleInstanceFromString((String) removeModuleDropdown.getSelectedItem()));
            for (Activity activity: moduleInstance.getActivities()) {
                int dayInt = activity.getDay();
                String stringInt = switch (dayInt) {
                    case 0 -> "Monday";
                    case 1 -> "Tuesday";
                    case 2 -> "Wednesday";
                    case 3 -> "Thursday";
                    case 4 -> "Friday";
                    default -> null;
                };
                String classActivity = String.format("%s - %s - %d:00", activity.getType(), stringInt, activity.getTime());
                removeActivityDropdown.addItem(classActivity);
            }
        }
    }


    private Programme getProgrammeInstance(DataFactory df, JComboBox<String> programmeDropdown) {
        return df.getProgrammeInstanceFromString((String) Objects.requireNonNull(programmeDropdown.getSelectedItem()));
    }


    public void updateGUI(DataFactory df, Timetable gui, GUICommands.GUICommands commands, ArrayList<Pair<Activity, Activity>> clashes) {
        commands.populateGUIbyProgramme(getProgrammeInstance(df, viewProgrammeDropdown),
                (Integer) Objects.requireNonNull(viewYearOfStudyDropdown.getSelectedItem()),
                (Integer) Objects.requireNonNull(viewTermDropdown.getSelectedItem()));
        String programmeYearAndTerm = String.format("Year %s - Term %s",
                viewYearOfStudyDropdown.getSelectedItem(), viewTermDropdown.getSelectedItem());
        gui.programmeNameLabel.setText((String) viewProgrammeDropdown.getSelectedItem());
        gui.programmeYearAndTermLabel.setText(programmeYearAndTerm);
        highlightingClashesOnTimetable(clashes, gui);
    }

    private ArrayList<Pair<Activity, Activity>> setCurrentClashes (DataFactory df, Timetable gui) {
        int yearOfStudy = (Integer) Objects.requireNonNull(viewYearOfStudyDropdown.getSelectedItem());
        int Term = (Integer) Objects.requireNonNull(viewTermDropdown.getSelectedItem());
        Programme currentProgramme = getProgrammeInstance(df, viewProgrammeDropdown);
        if (gui.chosenClashDetection) {
            return df.getClashes(currentProgramme,
                    yearOfStudy,
                    Term);
        }

        ScalaClashDetection scala = new ScalaClashDetection(JavaConverters.asScalaIteratorConverter(df.getActivitiesInSameProgrammeYearTerm(currentProgramme, yearOfStudy, Term).iterator()).asScala().toSeq());
        ArrayList<Pair<Activity, Activity>> clashes = new ArrayList<>(CollectionConverters.asJava(scala.getClashes()));
        clashes = df.removeDuplicateClashes(clashes);
        return clashes;

    }

    public ArrayList<Pair<Activity, Activity>> getCurrentClashes (DataFactory df, Timetable gui) {
        return setCurrentClashes(df, gui);
    }

    public void highlightingClashesOnTimetable(ArrayList<Pair<Activity, Activity>> clashes, Timetable gui) {
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
                gui.getLabelFromCoordinates(firstActivity.getDay() + 1, i - 8).getParent().setBackground(Color.decode("#801336"));
                gui.getLabelFromCoordinates(firstActivity.getDay() + 1, i - 8).setBackground(Color.decode("#801336"));
                gui.getLabelFromCoordinates(firstActivity.getDay() + 1, i - 8).setText("CLASH DETECTED\nYou can see details in the clashes window!");
                gui.getLabelFromCoordinates(firstActivity.getDay() + 1, i - 8).setFont(new Font("Arial", Font.BOLD, 10));
            }
        }
    }

}