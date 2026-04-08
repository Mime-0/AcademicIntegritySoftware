import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InstructorDashboard extends JPanel {

    private final AppFrame app;

    private final JLabel titleLabel;
    private final DefaultListModel<String> courseModel;
    private final JList<String> courseList;

    private final DefaultListModel<String> studentModel;
    private final JList<String> studentList;

    private final JTextArea studentDetailsArea;

    private Instructor currentInstructor;

    public InstructorDashboard(AppFrame app) {
        this.app = app;
        this.currentInstructor = null;

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel("Instructor Dashboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(titleLabel, BorderLayout.NORTH);

        courseModel = new DefaultListModel<>();
        courseList = new JList<>(courseModel);

        studentModel = new DefaultListModel<>();
        studentList = new JList<>(studentModel);

        studentDetailsArea = new JTextArea();
        studentDetailsArea.setEditable(false);
        studentDetailsArea.setLineWrap(true);
        studentDetailsArea.setWrapStyleWord(true);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 15, 15));

        JPanel coursePanel = new JPanel(new BorderLayout());
        coursePanel.setBorder(BorderFactory.createTitledBorder("Courses"));
        coursePanel.add(new JScrollPane(courseList), BorderLayout.CENTER);

        JPanel studentPanel = new JPanel(new BorderLayout());
        studentPanel.setBorder(BorderFactory.createTitledBorder("Students"));
        studentPanel.add(new JScrollPane(studentList), BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Selected Student + Reports"));
        detailsPanel.add(new JScrollPane(studentDetailsArea), BorderLayout.CENTER);

        centerPanel.add(coursePanel);
        centerPanel.add(studentPanel);
        centerPanel.add(detailsPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton createReportButton = new JButton("Create Report");
        JButton refreshButton = new JButton("Refresh");
        JButton logoutButton = new JButton("Logout");

        createReportButton.addActionListener(e -> createReportPopup());
        refreshButton.addActionListener(e -> refreshStudentList());
        logoutButton.addActionListener(e -> app.showLogin());

        bottomPanel.add(createReportButton);
        bottomPanel.add(refreshButton);
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        courseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshStudentList();
            }
        });

        studentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshStudentDetails();
            }
        });
    }

    public void loadInstructor(Instructor instructor) {
        this.currentInstructor = instructor;

        if (currentInstructor != null) {
            titleLabel.setText("Instructor Dashboard - " + currentInstructor.getInstructorName());
        } else {
            titleLabel.setText("Instructor Dashboard");
        }

        courseModel.clear();
        studentModel.clear();
        studentDetailsArea.setText("");

        if (instructor != null) {
            for (String course : instructor.getCourses()) {
                courseModel.addElement(course);
            }
        }
    }

    private void refreshStudentList() {
        studentModel.clear();
        studentDetailsArea.setText("");

        String selectedCourse = courseList.getSelectedValue();
        if (selectedCourse == null) {
            return;
        }

        List<Student> students = app.getStudentsByCourse(selectedCourse);
        for (Student student : students) {
            studentModel.addElement(student.getStudentId() + " - " + student.getStudentName());
        }
    }

    private void refreshStudentDetails() {
        String selected = studentList.getSelectedValue();
        if (selected == null) {
            studentDetailsArea.setText("");
            return;
        }

        String studentId = selected.split(" - ")[0];
        Student student = app.getStudentById(studentId);

        if (student == null) {
            studentDetailsArea.setText("");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(student.getStudentName()).append("\n");
        builder.append("Student ID: ").append(student.getStudentId()).append("\n");
        builder.append("Email: ").append(student.getEmail()).append("\n");
        builder.append("Program: ").append(student.getEnrolledProgram()).append("\n");
        builder.append("Integrity Status: ").append(student.getIntegrityStatus()).append("/10").append("\n\n");

        builder.append("Courses:\n");
        for (String course : student.getCourses()) {
            builder.append("- ").append(course).append("\n");
        }

        builder.append("\nDocuments:\n");
        for (String doc : student.getDocuments()) {
            builder.append("- ").append(doc).append("\n");
        }

        builder.append("\nAlerts:\n");
        for (Notification n : student.getNotifications()) {
            builder.append("- ").append(n.getMessage());
            if (n.isAcknowledged()) {
                builder.append(" [Acknowledged]");
            }
            builder.append("\n");
        }

        builder.append("\nReports:\n");
        List<IncidentReport> reports = app.getReportsByStudent(student.getStudentId());
        if (reports.isEmpty()) {
            builder.append("- No reports on file.\n");
        } else {
            for (IncidentReport report : reports) {
                builder.append("--------------------------------------------------\n");
                builder.append("Report ID: ").append(report.getReportId()).append("\n");
                builder.append("Course: ").append(report.getCourse()).append("\n");
                builder.append("Title: ").append(report.getTitle()).append("\n");
                builder.append("Status: ").append(report.getStatus()).append("\n");
                builder.append("Description: ").append(report.getDescription()).append("\n");
                if (!report.getMatchedRules().isEmpty()) {
                    builder.append("Matched Rules: ").append(String.join(", ", report.getMatchedRules())).append("\n");
                }
            }
        }

        studentDetailsArea.setText(builder.toString());
    }

    private void createReportPopup() {
        String selectedCourse = courseList.getSelectedValue();
        String selectedStudent = studentList.getSelectedValue();

        if (currentInstructor == null || selectedCourse == null || selectedStudent == null) {
            JOptionPane.showMessageDialog(app, "Select a course and a student first.");
            return;
        }

        String studentId = selectedStudent.split(" - ")[0];

        JDialog dialog = new JDialog(app, "Create Incident Report", true);
        dialog.setSize(600, 450);
        dialog.setLocationRelativeTo(app);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel fields = new JPanel(new GridLayout(2, 2, 10, 10));
        JTextField titleField = new JTextField();

        fields.add(new JLabel("Report Title:"));
        fields.add(titleField);
        fields.add(new JLabel("Student:"));
        fields.add(new JLabel(selectedStudent));

        JTextArea descriptionArea = new JTextArea(10, 40);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        JPanel buttons = new JPanel();
        JButton submitButton = new JButton("Submit Report");
        JButton cancelButton = new JButton("Cancel");

        buttons.add(submitButton);
        buttons.add(cancelButton);

        submitButton.addActionListener(e -> {
            String title = titleField.getText().trim();
            String description = descriptionArea.getText().trim();

            if (title.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(app, "Enter both a title and a long-form description.");
                return;
            }

            IncidentReport report = app.createReport(
                    studentId,
                    currentInstructor.getInstructorId(),
                    selectedCourse,
                    title,
                    description
            );

            String message = "Report created successfully.";
            if (!report.getMatchedRules().isEmpty()) {
                message += "\nMatched rules: " + String.join(", ", report.getMatchedRules());
            }

            JOptionPane.showMessageDialog(app, message);
            dialog.dispose();
            refreshStudentDetails();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        panel.add(fields, BorderLayout.NORTH);
        panel.add(new JScrollPane(descriptionArea), BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        dialog.add(panel);
        dialog.setVisible(true);
    }
}