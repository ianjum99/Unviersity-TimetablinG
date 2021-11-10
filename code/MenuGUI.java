import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Objects;

public class MenuGUI {
    private JPanel mainPanel, topBar;
    private JLabel adminMenuLabel;
    private JLabel closeButton;
    private JLabel addLabel, removeLabel, viewLabel;
    private JTextField programmeNameField;
    private JPanel addPanel, removePanel, viewPanel;
    private JLabel programmeName, underOrPostLabel;
    private JLabel programmeSectionLabel;
    private JButton addProgrammeButton;
    private JLabel moduleProgrammeSelectionLabel;
    private JComboBox moduleProgrammeSelectionDropdown;
    private JComboBox underOrPostDropdown;
    private JLabel moduleSectionLabel;
    private JTextField moduleNameField;
    private JLabel moduleTermLabel, moduleNameLabel, moduleOptionalLabel, moduleSelectionLabel;
    private JComboBox moduleTermDropdown, moduleOptionalDropdown, moduleSelectionDropdown;
    private JButton addModuleButton;
    private JLabel activitySectionLabel, activityNameLabel, activityTypeLabel, startTimeLabel, timeDurationLabel;
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
        }

        Programme selectedProgramme = df.getProgrammeInstanceFromString((String) activityProgrammeSelectionDropdown.getSelectedItem());

        for (Module module : selectedProgramme.getModules()) {
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
            }
        });

        addProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (programmeNameField.getText().length() > 0) {
                    Programme programme = new Programme(programmeNameField.getText(), (String) (Objects.requireNonNull(underOrPostDropdown.getSelectedItem())), null);
                    df.add(programme);
                    moduleProgrammeSelectionDropdown.addItem(programme.getName());
                    activityProgrammeSelectionDropdown.addItem(programme.getName());
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
                System.out.println(df);
            }

        });

        addActivityButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Programme programme = (df.getProgrammeInstanceFromString((String) activityProgrammeSelectionDropdown.getSelectedItem()));
                //Module module = (df.getModuleInstanceFromString((String) moduleSelectionDropdown.getSelectedItem()));
                String day = dayDropdown.getSelectedItem().toString();
                String time = startTimeDropdown.getSelectedItem().toString();
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
                        Integer.parseInt(time.substring(0, 1)),
                        Integer.parseInt(timeDurationDropdown.getSelectedItem().toString())
                        );

                //df.createActivity(module, activity);
                System.out.println(df);
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