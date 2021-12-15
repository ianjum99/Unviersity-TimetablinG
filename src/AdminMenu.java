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
    //Although a lot of variables say they are not used, in fact they are but just in the adminmenu.form

    private static AdminMenu instance=null;
    private int posX, posY;

    public AdminMenu(Timetable gui, DataFactory df)  {
        GUICommands.GUICommands commands = new GUICommands.GUICommands(gui, df);
        init(gui, df, commands);
    }
    //The AdminMenu constructor is passed a Timetable and DataFactory instance as well as creating a GUICommands instance
    //it then passes all of these as parameters to the init method

    public static AdminMenu getInstance(Timetable gui, DataFactory df){
        if (instance == null) {
            instance = new AdminMenu(gui, df);
        }
        instance.frame.toFront();
        return instance;
    }
    //This method is used to check if an instance of AdminMenu exists if doesn't then create one otherwise return it

    private void init(Timetable gui, DataFactory df, GUICommands.GUICommands commands) {
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
        //This creates the jframe and adds the content pane plus a few other customisations

        programmeSelectionBoxFiller(df, moduleProgrammeSelectionDropdown, activityProgrammeSelectionDropdown, viewProgrammeDropdown, removeProgrammeDropdown);

        Programme firstProgrammeInstance = getProgrammeInstance(df, activityProgrammeSelectionDropdown);

        for (Module module : firstProgrammeInstance.getModules()) {
            moduleSelectionDropdown.addItem(module.getName());
        }

        viewSectionBoxFiller(df, viewProgrammeDropdown);

        removeSectionBoxFiller(df, removeProgrammeDropdown, "module");
        //The 6 lines above use methods to fill the jcomboboxes with data

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
        //The 3 listeners above are for the top bar of the window which allow you to drag or close the window

        addProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (programmeNameField.getText().length() >= 4) {
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
        //This button listener checks if the programme name has at least 4 chars and then uses the data fields to create a programme
        //it also adds the new programme to all the programme jcomboboxes

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
        //This button listener is very similar to the one above, but it's for adding a module instead of a programme
        //it also adds the new module to all the module jcomboboxes

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
        //This button listener is similar but it's for an activity instead and it has a few extra validation checks
        //such as not being able to add a 2-hour activity at 8pm
        //it also checks if the current timetable being viewed is the one which just had the activity added and if it is
        //it calls the updateGUI method to update the timetable with the new activity

        activityProgrammeSelectionDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Programme programmeInstance = getProgrammeInstance(df, activityProgrammeSelectionDropdown);
                moduleSelectionDropdown.removeAllItems();
                for (Module module : programmeInstance.getModules()) {
                    moduleSelectionDropdown.addItem(module.getName());
                }
            }
        });
        //This listener will clear and update the module jcombobox depending on which programme is selected

        moduleProgrammeSelectionDropdown.addItemListener(e -> {
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
        });
        //This adds the available years depending on the programme, so undergraduate has 3 and postgraduate had 1

        viewProgrammeDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                viewYearOfStudyDropdown.removeAllItems();
                viewTermDropdown.removeAllItems();
                viewSectionBoxFiller(df, viewProgrammeDropdown);
            }
        });
        //Year and term is updated according to the programme jcombobox selection

        removeProgrammeDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                removeSectionBoxFiller(df, removeProgrammeDropdown, "module");
            }
        });
        //Remove section is refilled with new data if the programme jcombobox is changed

        removeModuleDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                removeSectionBoxFiller(df, removeProgrammeDropdown, "activity");
            }
        });
        //Remove section is refilled with new data if the module jcombobox is changed

        viewProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ArrayList<Pair<Activity, Activity>> currentClashes = setCurrentClashes(df, gui);
                ClashesWindow clashesGUI = ClashesWindow.getInstance(gui, df, true);
                clashesGUI.updateClashList(currentClashes, df);
                updateGUI(df, gui, commands, currentClashes);
            }
        });
        //When view programme button is pressed this will get all the clashes and store it in an arraylist
        //this will then open an instance of clasheswindow and update the window with that list
        //as well as call the updategui method which will populate the timetable gui

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
        //When the remove programme button is pressed it will check if the current timetable being viewed is the one which is
        //being requested to be removed and in that case that case it will be cleared
        //all the programmeSelectionBoxFiller is called to refill the boxes without the removed programme

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
        //This gets the instances of the program and module then removes them as well as checking the GUI if the module
        //is a part of the current timetable which then calls the update method if so
        //also updating all the jcomboboxes which could have this module in there

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
        //This will get the module instance and then remove the specified activity from that module and update the remove
        //section boxes so the activity is gone as well as update the GUI if relevant
    }

    private void createUIComponents() {
        closeButton = new JLabel((new ImageIcon("images/close.png")));
        programmeNameField = new JTextField();
        moduleNameField = new JTextField();
        JTextField activityNameField = new JTextField();
        programmeNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        moduleNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        activityNameField.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
    }
    //The first line creates a close button with the icon and the next 3 creates jtextfields
    //the last 3 lines give these name fields a border

    @SafeVarargs
    private void programmeSelectionBoxFiller(DataFactory df, JComboBox<String>... args) {
        for (JComboBox<String> box: args) {
            box.removeAllItems();
            for (Programme item: df) {
                box.addItem(item.getName());
            }
        }
    }
    //This method is used to fill all the programme jcomboboxes which are passed to it

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
    //The method above is to fill the view section area on the menu which is the year and term but depending on the programme

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
    //Again a similar method but it's to fill the remove section jcomboboxes depending on the parameters
    //it has a case statement so if only activites are being removed the module jcomboxes don't need to be updated

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
    //This will fill the activity jcombobox in the remove section of the menu, it takes the details from an activity
    //and formats it before adding it to the box

    private Programme getProgrammeInstance(DataFactory df, JComboBox<String> programmeDropdown) {
        return df.getProgrammeInstanceFromString((String) Objects.requireNonNull(programmeDropdown.getSelectedItem()));
    }
    //This method is used to get fetch a programme instance from DataFactory using a string

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
    //The updateGUI method is responsible for updating the timetable with activities, it calls a method in GUICommands
    //to populate the gui as well as highlighting any clashes which are detected

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
        return new ArrayList<>(CollectionConverters.asJava(scala.getClashes()));
    }
    //This method is used to get the current clashes from the current timetable, first it checks the chosenClashDetection
    //variable from the first dialog and then it uses the chosen language for the clash detection, returning an
    //arraylist of clashes

    public ArrayList<Pair<Activity, Activity>> getCurrentClashes (DataFactory df, Timetable gui) {
        return setCurrentClashes(df, gui);
    }
    //This is the getter for the setCurrentClashes method

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
    //The last method is used for highlighting clashes on the timetable gui, it does this by calculating duration
    //from the first activity for each clash in the list and then changing the colour of that jpanel and jtextarea
    //as well as the text
}