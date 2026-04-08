import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class InstructorDashboard extends JPanel {

    private final AppFrame app;

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

        JLabel title = new JLabel("Instructor Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

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
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Selected Student"));
        detailsPanel.add(new JScrollPane(studentDetailsArea), BorderLayout.CENTER);

        centerPanel.add(coursePanel);
        centerPanel.add(studentPanel);
        centerPanel.add(detailsPanel);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        JButton logoutButton = new JButton("Logout");

        refreshButton.addActionListener(e -> refreshStudentList());
        logoutButton.addActionListener(e -> app.showLogin());

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
        JLabel title = new JLabel("Instructor Dashboard - " + currentInstructor.getInstructorName());
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

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

        builder.append("Documents:\n");
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

        studentDetailsArea.setText(builder.toString());
    }
}