import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AdminDashboard extends JPanel {

    private final AppFrame app;

    private final JLabel titleLabel;
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

        titleLabel = new JLabel("Administrator Dashboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(titleLabel, BorderLayout.NORTH);

        instructorModel = new DefaultListModel<>();
        instructorList = new JList<>(instructorModel);

        instructorArea = new JTextArea();
        instructorArea.setEditable(false);
        instructorArea.setLineWrap(true);
        instructorArea.setWrapStyleWord(true);

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
        JButton addPersonButton = new JButton("Add New Role");
        JButton logoutButton = new JButton("Logout");

        savePoliciesButton.addActionListener(e -> {
            app.getDataStore().setPolicies(policyArea.getText());
            JOptionPane.showMessageDialog(app, "Policies updated.");
        });

        addPersonButton.addActionListener(e -> newPersonPopup());
        logoutButton.addActionListener(e -> app.showLogin());

        bottomPanel.add(savePoliciesButton);
        bottomPanel.add(addPersonButton);
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
        if (currentAdmin != null) {
            titleLabel.setText("Administrator Dashboard - " + currentAdmin.getAdminName());
        } else {
            titleLabel.setText("Administrator Dashboard");
        }

        instructorArea.setText("");
        policyArea.setText(app.getDataStore().getPolicies());
        refreshInstructorList();
    }

    private void refreshInstructorList() {
        instructorModel.clear();
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

    private void newPersonPopup() {
        JDialog newPerson = new JDialog(this.app, "Add New Person", true);
        newPerson.setSize(600, 400);
        newPerson.setLocationRelativeTo(this.app);

        JPanel form = new JPanel(new GridLayout(7, 2, 10, 10));
        form.setBorder(new EmptyBorder(15, 15, 15, 15));

        JComboBox<String> roleBox = new JComboBox<>(new String[] {"Student", "Instructor"});
        JTextField idField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField programField = new JTextField();

        form.add(new JLabel("Role:"));
        form.add(roleBox);
        form.add(new JLabel("Personnel ID:"));
        form.add(idField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);
        form.add(new JLabel("Name:"));
        form.add(nameField);
        form.add(new JLabel("Email:"));
        form.add(emailField);
        form.add(new JLabel("<html>Enrolled Program (Student)<br>Department (Instructor)</html>"));
        form.add(programField);

        JButton createButton = new JButton("Create Person");
        JButton closeButton = new JButton("Close");
        form.add(createButton);
        form.add(closeButton);

        closeButton.addActionListener(e -> newPerson.dispose());

        createButton.addActionListener(e -> {
            String id = idField.getText().trim();
            String pass = passwordField.getText().trim();
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String pgm = programField.getText().trim();

            if (id.isEmpty() || pass.isEmpty() || name.isEmpty() || email.isEmpty() || pgm.isEmpty()) {
                JOptionPane.showMessageDialog(app, "Please fill in all fields.");
                return;
            }

            if ("Student".equals((String) roleBox.getSelectedItem())) {
                app.getDataStore().addStudent(id, pass, name, email, pgm);
                JOptionPane.showMessageDialog(app, "Successfully created student.");
            } else {
                app.getDataStore().addInstructor(id, pass, name, email, pgm);
                JOptionPane.showMessageDialog(app, "Successfully created instructor.");
            }

            refreshInstructorList();
            newPerson.dispose();
        });

        newPerson.add(form);
        newPerson.setVisible(true);
    }
}