import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminDashboard extends JPanel {

    private final AppFrame app;

    private final DefaultListModel<String> instructorModel;
    private final JList<String> instructorList;
    private final JTextArea instructorArea;
    private final JTextArea policyArea;

    private Admin currentAdmin;

    public AdminDashboard(AppFrame app) {
        this.app = app;
        this.currentAdmin = null;

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Administrator Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        instructorModel = new DefaultListModel<>();
        instructorList = new JList<>(instructorModel);

        instructorArea = new JTextArea();
        instructorArea.setEditable(false);

        policyArea = new JTextArea();
        policyArea.setLineWrap(true);
        policyArea.setWrapStyleWord(true);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 15, 15));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Instructors"));
        leftPanel.add(new JScrollPane(instructorList), BorderLayout.CENTER);

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBorder(BorderFactory.createTitledBorder("Instructor Details"));
        middlePanel.add(new JScrollPane(instructorArea), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Policies"));
        rightPanel.add(new JScrollPane(policyArea), BorderLayout.CENTER);

        centerPanel.add(leftPanel);
        centerPanel.add(middlePanel);
        centerPanel.add(rightPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton savePoliciesButton = new JButton("Save Policies");
        JButton logoutButton = new JButton("Logout");

        savePoliciesButton.addActionListener(e -> {
            app.getDataStore().setPolicies(policyArea.getText());
            JOptionPane.showMessageDialog(app, "Policies updated.");
        });

        logoutButton.addActionListener(e -> app.showLogin());

        bottomPanel.add(savePoliciesButton);
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        instructorList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshInstructorDetails();
            }
        });
    }

    public void loadAdmin(Admin admin) {
        this.currentAdmin = admin;
        JLabel title = new JLabel("Administrator Dashboard - " + currentAdmin.getAdminName());
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);
        instructorModel.clear();
        instructorArea.setText("");
        policyArea.setText(app.getDataStore().getPolicies());

        for (Instructor instructor : app.getAllInstructors()) {
            instructorModel.addElement(instructor.getInstructorId() + " - " + instructor.getInstructorName());
        }
    }

    private void refreshInstructorDetails() {
        String selected = instructorList.getSelectedValue();
        if (selected == null) {
            instructorArea.setText("");
            return;
        }

        String instructorId = selected.split(" - ")[0];
        Instructor instructor = app.getInstructorById(instructorId);

        if (instructor == null) {
            instructorArea.setText("");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(instructor.getInstructorName()).append("\n");
        builder.append("Instructor ID: ").append(instructor.getInstructorId()).append("\n");
        builder.append("Email: ").append(instructor.getEmail()).append("\n");
        builder.append("Department: ").append(instructor.getDepartment()).append("\n\n");

        builder.append("Courses:\n");
        for (String course : instructor.getCourses()) {
            builder.append("- ").append(course).append("\n");
        }

        instructorArea.setText(builder.toString());
    }
}