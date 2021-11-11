import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

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
    private DataFactory dataFactory;

    private static MenuGUI instance=null;
    private int posX, posY;

    public MenuGUI(DataFactory df) {
        init(df);
    }

    public static MenuGUI getInstance(DataFactory df){
        if (instance == null) {
            instance = new MenuGUI(df);
        }
        return instance;
    }

    private void init(DataFactory df) {
        frame = new JFrame();
        frame.setContentPane(mainPanel);
        frame.setUndecorated(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));

        for (Programme programme : df) {
            moduleProgrammeSelectionDropdown.addItem(programme.getName());
            activityProgrammeSelectionDropdown.addItem(programme.getName());
            viewProgrammeDropdown.addItem(programme.getName());
            removeProgrammeDropdown.addItem(programme.getName());
        }

        Programme programme = df.getProgrammeInstanceFromString((String) activityProgrammeSelectionDropdown.getSelectedItem());

        for (Module module : programme.getModules()) {
            moduleSelectionDropdown.addItem(module.getName());

        }

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
                if (programmeNameField.getText().length() > 0) {
                    String programmeType = "";
                    Integer selectedIndex = Integer.parseInt(underOrPostDropdown.getSelectedIndex().toString());
                    switch (selectedIndex) {
                        case 1:
                            programmeType = "U";
                        case 2:
                            programmeType = "P";
                    }
                    Programme programme = new Programme(programmeNameField.getText(), programmeType, null);
                    df.add(programme);
                    System.out.println(df);
                    moduleProgrammeSelectionDropdown.addItem(programmeType + " | " + programme.getName());
                    activityProgrammeSelectionDropdown.addItem(programmeType + " | " + programme.getName());
                    viewProgrammeDropdown.addItem(programmeType + " | " + programme.getName());
                    removeProgrammeDropdown.addItem(programmeType + " | " + programme.getName());
                }
            }
        });

        addModuleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Programme programme = (df.getProgrammeInstanceFromString((String) moduleProgrammeSelectionDropdown.getSelectedItem()));
                String compulsory = moduleOptionalDropdown.getSelectedItem().toString();
                switch (compulsory) {
                    case "Compulsory":
                        compulsory = "true";
                    case "Optional":
                        compulsory = "false";
                }
                Module module = new Module("COMP-2823",
                        Integer.parseInt(yearOfStudyDropdown.getSelectedItem().toString()),
                        moduleNameField.getText(),
                        Boolean.parseBoolean(compulsory),
                        Long.parseLong(moduleTermDropdown.getSelectedItem().toString()),
                        new ArrayList<Activity>());
                df.createModule(programme, module);
                moduleSelectionDropdown.addItem(module.getName());
                removeModuleDropdown.addItem(module.getName());
                System.out.println(df);
            }

        });

        addActivityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Programme programme = (df.getProgrammeInstanceFromString((String) activityProgrammeSelectionDropdown.getSelectedItem()));
                Module module = (df.getModuleInstanceFromString((String) moduleSelectionDropdown.getSelectedItem()));
                String day = dayDropdown.getSelectedItem().toString();
                String time = startTimeDropdown.getSelectedItem().toString().substring(0, 2);
                switch (day) {
                    case "Monday":
                        day = "1";
                    case "Tuesday":
                        day = "2";
                    case "Wednesday":
                        day = "3";
                    case "Thursday":
                        day = "4";
                    case "Friday":
                        day = "5";

                }

                Activity activity = new Activity(activityTypeDropdown.getSelectedItem().toString(),
                        Integer.parseInt(day),
                        Integer.parseInt(time),
                        Integer.parseInt(timeDurationDropdown.getSelectedItem().toString())
                        );

                df.createActivity(module, activity);
                System.out.println(activity);
                System.out.println(df);
            }
        });

        activityProgrammeSelectionDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Programme programme = df.getProgrammeInstanceFromString((String) activityProgrammeSelectionDropdown.getSelectedItem());
                    moduleSelectionDropdown.removeAllItems();
                    for (Module module : programme.getModules()) {
                        moduleSelectionDropdown.addItem(module.getName());
                    }
                }
            }
        });

//        underOrPostDropdown.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                if (underOrPostDropdown.getSelectedIndex() == 1) {
//                    yearOfStudyDropdown.removeAllItems();
//                    yearOfStudyDropdown.addItem(1);
//                } else {
//                    yearOfStudyDropdown.removeAllItems();
//                    yearOfStudyDropdown.addItem(1);
//                    yearOfStudyDropdown.addItem(2);
//                    yearOfStudyDropdown.addItem(3);
//                }
//            }
//        });

        moduleProgrammeSelectionDropdown.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Programme programme = df.getProgrammeInstanceFromString((String) moduleProgrammeSelectionDropdown.getSelectedItem());
                if (programme.getType().equals("P")) {
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
}