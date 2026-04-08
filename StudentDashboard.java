import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class StudentDashboard extends JPanel {

    private final AppFrame app;
    private final JLabel titleLabel;
    private final JTextArea profileArea;
    private final DefaultListModel<String> notificationModel;
    private final JList<String> notificationList;
    private final JTextArea reportArea;

    private Student currentStudent;

    public StudentDashboard(AppFrame app) {
        this.app = app;
        this.currentStudent = null;

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel("Student Dashboard");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(titleLabel, BorderLayout.NORTH);

        profileArea = new JTextArea();
        profileArea.setEditable(false);
        profileArea.setLineWrap(true);
        profileArea.setWrapStyleWord(true);

        notificationModel = new DefaultListModel<>();
        notificationList = new JList<>(notificationModel);

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 15, 15));

        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile"));
        profilePanel.add(new JScrollPane(profileArea), BorderLayout.CENTER);

        JPanel notificationsPanel = new JPanel(new BorderLayout());
        notificationsPanel.setBorder(BorderFactory.createTitledBorder("Notifications"));
        notificationsPanel.add(new JScrollPane(notificationList), BorderLayout.CENTER);

        JPanel reportsPanel = new JPanel(new BorderLayout());
        reportsPanel.setBorder(BorderFactory.createTitledBorder("Incident Reports"));
        reportsPanel.add(new JScrollPane(reportArea), BorderLayout.CENTER);

        centerPanel.add(profilePanel);
        centerPanel.add(notificationsPanel);
        centerPanel.add(reportsPanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton acknowledgeButton = new JButton("Acknowledge Selected Notification");
        JButton logoutButton = new JButton("Logout");

        acknowledgeButton.addActionListener(e -> acknowledgeSelected());
        logoutButton.addActionListener(e -> app.showLogin());

        bottomPanel.add(acknowledgeButton);
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void loadStudent(Student student) {
        this.currentStudent = student;
        if (student != null) {
            titleLabel.setText("Student Dashboard - " + student.getStudentName());
        } else {
            titleLabel.setText("Student Dashboard");
        }
        refreshView();
    }

    private void refreshView() {
        if (currentStudent == null) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(currentStudent.getStudentName()).append("\n");
        builder.append("Student ID: ").append(currentStudent.getStudentId()).append("\n");
        builder.append("Email: ").append(currentStudent.getEmail()).append("\n");
        builder.append("Program: ").append(currentStudent.getEnrolledProgram()).append("\n");
        builder.append("Integrity Status: ").append(currentStudent.getIntegrityStatus()).append("/10").append("\n\n");

        builder.append("Courses:\n");
        for (String course : currentStudent.getCourses()) {
            builder.append("- ").append(course).append("\n");
        }

        builder.append("\nSubmitted Documents:\n");
        for (String doc : currentStudent.getDocuments()) {
            builder.append("- ").append(doc).append("\n");
        }

        profileArea.setText(builder.toString());

        notificationModel.clear();
        for (Notification notification : currentStudent.getNotifications()) {
            String text = "[" + notification.getAlertType() + "] " + notification.getMessage();
            if (notification.isAcknowledged()) {
                text += " [Acknowledged]";
            }
            notificationModel.addElement(text);
        }

        StringBuilder reportBuilder = new StringBuilder();
        List<IncidentReport> reports = app.getReportsByStudent(currentStudent.getStudentId());

        if (reports.isEmpty()) {
            reportBuilder.append("No incident reports on file.");
        } else {
            for (IncidentReport report : reports) {
                reportBuilder.append("Report ID: ").append(report.getReportId()).append("\n");
                reportBuilder.append("Course: ").append(report.getCourse()).append("\n");
                reportBuilder.append("Title: ").append(report.getTitle()).append("\n");
                reportBuilder.append("Status: ").append(report.getStatus()).append("\n");
                reportBuilder.append("Description: ").append(report.getDescription()).append("\n");
                if (!report.getMatchedRules().isEmpty()) {
                    reportBuilder.append("Matched Rules: ").append(String.join(", ", report.getMatchedRules())).append("\n");
                }
                reportBuilder.append("\n--------------------------------------------------\n\n");
            }
        }

        reportArea.setText(reportBuilder.toString());
    }

    private void acknowledgeSelected() {
        if (currentStudent == null) {
            return;
        }

        int index = notificationList.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(app, "Select a notification first.");
            return;
        }

        Notification notification = currentStudent.getNotifications().get(index);
        notification.acknowledge();
        refreshView();

        JOptionPane.showMessageDialog(app, "Notification acknowledged.");
    }
}