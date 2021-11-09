import com.beust.klaxon.Json;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MenuGUI {
    private JPanel mainPanel, topBar;
    private JLabel adminMenuLabel;
    private JLabel closeButton;
    private JLabel addLabel, removeLabel, viewLabel;
    private JTextField programmeNameField;
    private JPanel addPanel, removePanel, viewPanel;
    private JLabel programmeName, underOrPostLabel, yearOfStudyLabel;
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
    private JFrame frame;

    private static MenuGUI instance=null;
    private int posX, posY;

    public MenuGUI(DataFactory df) {
        init();

        addProgrammeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                df.add(new Programme(programmeNameField.getText(), (String) underOrPostDropdown.getSelectedItem(), null));
                System.out.println(df);
                JsonHandler test = new JsonHandler("programmes.json");
                test.saveJsonFile(df);
            }
        });
    }

    public static MenuGUI getInstance(DataFactory df){
        if (instance == null) {
            instance = new MenuGUI(df);
        }
        return instance;
    }

    private void init() {
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
