import com.beust.klaxon.Json;

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
    private JLabel programmeSelectionLabel;
    private JComboBox programmeSelectionDropdown;
    private JComboBox underOrPostDropdown;
    private JLabel moduleSectionLabel;
    private JTextField moduleNameField;
    private JLabel moduleTermLabel, moduleNameLabel, moduleOptionalLabel, moduleSelectionLabel;
    private JComboBox moduleTermDropdown, moduleOptionalDropdown, moduleSelectionDropdown;
    private JButton addModuleButton;
    private JLabel activitySectionLabel, activityNameLabel, activityTypeLabel, startTimeLabel, endTimeLabel;
    private JTextField activityNameField;
    private JComboBox activityTypeDropdown;
    private JButton addActivityButton;
    private JLabel yearOfStudyLabel;
    private JComboBox yearOfStudyDropdown;
    private JFrame frame;
    private DataFactory dataFactory;



    private static MenuGUI instance=null;
    private int posX, posY;

    public MenuGUI(DataFactory df) {
        init(df);

        addProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (programmeNameField.getText().length() > 0) {
                Programme programme = new Programme(programmeNameField.getText(), (String) (Objects.requireNonNull(underOrPostDropdown.getSelectedItem())), null);
                df.add(programme);
                programmeSelectionDropdown.addItem(programme.getName());

            }
        }
    });

        addModuleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Programme programme = (df.getProgrammeInstanceFromString((String) programmeSelectionDropdown.getSelectedItem()));
                ArrayList<Module> modules = programme.getModules();
                Module module = new Module("1",
                        yearOfStudyDropdown.getSelectedItem(),
                        moduleNameField.getText(),
                        moduleOptionalDropdown.getSelectedItem(),
                        moduleTermDropdown.getSelectedItem(),
                        new ArrayList<Activity>());



            }
        });
}

    public static MenuGUI getInstance(DataFactory df){
        if (instance == null) {
            instance = new MenuGUI(df);
        }
        return instance;
    }

    private void init(DataFactory df) {
        for (Programme programme : df) {
            programmeSelectionDropdown.addItem(programme.getName());
        }
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
