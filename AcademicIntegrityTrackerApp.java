import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AcademicIntegrityTrackerApp extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final DataStore dataStore;

    private Student currentStudent;
    private Instructor currentInstructor;
    private Admin currentAdmin;

    private final JTextArea studentProfileArea;
    private final DefaultListModel<String> studentNotificationModel;
    private final JList<String> studentNotificationList;

    private final DefaultListModel<String> instructorCourseModel;
    private final JList<String> instructorCourseList;
    private final DefaultListModel<String> instructorStudentModel;
    private final JList<String> instructorStudentList;
    private final JTextArea instructorStudentViewArea;

    private final DefaultListModel<String> adminInstructorModel;
    private final JList<String> adminInstructorList;
    private final JTextArea adminInstructorViewArea;
    private final JTextArea adminPolicyArea;

    public AcademicIntegrityTrackerApp() {
        super("Academic Integrity Tracker");
        this.dataStore = new DataStore();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        studentProfileArea = createTextArea();
        studentNotificationModel = new DefaultListModel<>();
        studentNotificationList = new JList<>(studentNotificationModel);

        instructorCourseModel = new DefaultListModel<>();
        instructorCourseList = new JList<>(instructorCourseModel);
        instructorStudentModel = new DefaultListModel<>();
        instructorStudentList = new JList<>(instructorStudentModel);
        instructorStudentViewArea = createTextArea();

        adminInstructorModel = new DefaultListModel<>();
        adminInstructorList = new JList<>(adminInstructorModel);
        adminInstructorViewArea = createTextArea();
        adminPolicyArea = createTextArea();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        buildScreens();

        add(cardPanel);
        cardLayout.show(cardPanel, "MAIN_MENU");
    }

    private void buildScreens() {
        cardPanel.add(buildMainMenuPanel(), "MAIN_MENU");
        cardPanel.add(buildStudentLoginPanel(), "STUDENT_LOGIN");
        cardPanel.add(buildInstructorLoginPanel(), "INSTRUCTOR_LOGIN");
        cardPanel.add(buildAdminLoginPanel(), "ADMIN_LOGIN");
        cardPanel.add(buildStudentDashboardPanel(), "STUDENT_DASHBOARD");
        cardPanel.add(buildInstructorDashboardPanel(), "INSTRUCTOR_DASHBOARD");
        cardPanel.add(buildAdminDashboardPanel(), "ADMIN_DASHBOARD");
    }

    private JPanel buildMainMenuPanel() {
        JPanel panel = createBasePanel("Academic Integrity Tracker");

        JLabel subtitle = new JLabel("Select your role to continue");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JButton studentButton = createMenuButton("Student Login");
        JButton instructorButton = createMenuButton("Instructor Login");
        JButton adminButton = createMenuButton("Administrator Login");
        JButton exitButton = createMenuButton("Exit");

        studentButton.addActionListener(e -> cardLayout.show(cardPanel, "STUDENT_LOGIN"));
        instructorButton.addActionListener(e -> cardLayout.show(cardPanel, "INSTRUCTOR_LOGIN"));
        adminButton.addActionListener(e -> cardLayout.show(cardPanel, "ADMIN_LOGIN"));
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(subtitle);
        panel.add(Box.createVerticalStrut(20));
        panel.add(studentButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(instructorButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(adminButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(exitButton);

        return wrapCentered(panel);
    }

    private JPanel buildStudentLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Student Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField idField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        form.add(new JLabel("Student ID:"));
        form.add(idField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        form.add(loginButton);
        form.add(backButton);

        loginButton.addActionListener((ActionEvent e) -> {
            String id = idField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            Student student = dataStore.authenticateStudent(id, password);
            if (student != null) {
                currentStudent = student;
                refreshStudentDashboard();
                cardLayout.show(cardPanel, "STUDENT_DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid student login.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MAIN_MENU"));

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildInstructorLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Instructor Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField idField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        form.add(new JLabel("Instructor ID:"));
        form.add(idField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        form.add(loginButton);
        form.add(backButton);

        loginButton.addActionListener((ActionEvent e) -> {
            String id = idField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            Instructor instructor = dataStore.authenticateInstructor(id, password);
            if (instructor != null) {
                currentInstructor = instructor;
                refreshInstructorDashboard();
                cardLayout.show(cardPanel, "INSTRUCTOR_DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid instructor login.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MAIN_MENU"));

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildAdminLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Administrator Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField idField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField otpField = new JTextField();

        form.add(new JLabel("Admin ID:"));
        form.add(idField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);
        form.add(new JLabel("OTP / Security Answer:"));
        form.add(otpField);

        JButton loginButton = new JButton("Login");
        JButton backButton = new JButton("Back");
        form.add(loginButton);
        form.add(backButton);

        loginButton.addActionListener((ActionEvent e) -> {
            String id = idField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String otp = otpField.getText().trim();

            Admin admin = dataStore.authenticateAdmin(id, password, otp);
            if (admin != null) {
                currentAdmin = admin;
                refreshAdminDashboard();
                cardLayout.show(cardPanel, "ADMIN_DASHBOARD");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid admin login.");
            }
        });

        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MAIN_MENU"));

        panel.add(form, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildStudentDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Student Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));

        JPanel profilePanel = new JPanel(new BorderLayout());
        profilePanel.setBorder(BorderFactory.createTitledBorder("Profile"));
        profilePanel.add(new JScrollPane(studentProfileArea), BorderLayout.CENTER);

        JPanel notificationPanel = new JPanel(new BorderLayout());
        notificationPanel.setBorder(BorderFactory.createTitledBorder("Notifications"));
        notificationPanel.add(new JScrollPane(studentNotificationList), BorderLayout.CENTER);

        centerPanel.add(profilePanel);
        centerPanel.add(notificationPanel);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton acknowledgeButton = new JButton("Acknowledge Selected Notification");
        JButton logoutButton = new JButton("Logout");

        acknowledgeButton.addActionListener(e -> acknowledgeSelectedNotification());
        logoutButton.addActionListener(e -> {
            currentStudent = null;
            cardLayout.show(cardPanel, "MAIN_MENU");
        });

        bottomPanel.add(acknowledgeButton);
        bottomPanel.add(logoutButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildInstructorDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Instructor Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 15, 15));

        JPanel coursePanel = new JPanel(new BorderLayout());
        coursePanel.setBorder(BorderFactory.createTitledBorder("Courses"));
        coursePanel.add(new JScrollPane(instructorCourseList), BorderLayout.CENTER);

        JPanel studentListPanel = new JPanel(new BorderLayout());
        studentListPanel.setBorder(BorderFactory.createTitledBorder("Students"));
        studentListPanel.add(new JScrollPane(instructorStudentList), BorderLayout.CENTER);

        JPanel studentViewPanel = new JPanel(new BorderLayout());
        studentViewPanel.setBorder(BorderFactory.createTitledBorder("Selected Student View"));
        studentViewPanel.add(new JScrollPane(instructorStudentViewArea), BorderLayout.CENTER);

        centerPanel.add(coursePanel);
        centerPanel.add(studentListPanel);
        centerPanel.add(studentViewPanel);

        instructorCourseList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshInstructorStudentList();
            }
        });

        instructorStudentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshInstructorSelectedStudentView();
            }
        });

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton viewButton = new JButton("Refresh View");
        JButton logoutButton = new JButton("Logout");

        viewButton.addActionListener(e -> {
            refreshInstructorStudentList();
            refreshInstructorSelectedStudentView();
        });

        logoutButton.addActionListener(e -> {
            currentInstructor = null;
            cardLayout.show(cardPanel, "MAIN_MENU");
        });

        bottomPanel.add(viewButton);
        bottomPanel.add(logoutButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel buildAdminDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Administrator Dashboard");
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        panel.add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 15, 15));

        JPanel instructorPanel = new JPanel(new BorderLayout());
        instructorPanel.setBorder(BorderFactory.createTitledBorder("Instructors"));
        instructorPanel.add(new JScrollPane(adminInstructorList), BorderLayout.CENTER);

        JPanel instructorViewPanel = new JPanel(new BorderLayout());
        instructorViewPanel.setBorder(BorderFactory.createTitledBorder("Instructor View"));
        instructorViewPanel.add(new JScrollPane(adminInstructorViewArea), BorderLayout.CENTER);

        JPanel policyPanel = new JPanel(new BorderLayout());
        policyPanel.setBorder(BorderFactory.createTitledBorder("Academic Integrity Policies"));
        policyPanel.add(new JScrollPane(adminPolicyArea), BorderLayout.CENTER);

        centerPanel.add(instructorPanel);
        centerPanel.add(instructorViewPanel);
        centerPanel.add(policyPanel);

        adminInstructorList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                refreshAdminSelectedInstructorView();
            }
        });

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton savePolicyButton = new JButton("Save Policy Changes");
        JButton logoutButton = new JButton("Logout");

        savePolicyButton.addActionListener(e -> {
            dataStore.setPolicies(adminPolicyArea.getText());
            JOptionPane.showMessageDialog(this, "Policies updated.");
        });

        logoutButton.addActionListener(e -> {
            currentAdmin = null;
            cardLayout.show(cardPanel, "MAIN_MENU");
        });

        bottomPanel.add(savePolicyButton);
        bottomPanel.add(logoutButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void refreshStudentDashboard() {
        if (currentStudent == null) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(currentStudent.name).append("\n");
        builder.append("Student ID: ").append(currentStudent.id).append("\n");
        builder.append("Integrity Status: ").append(currentStudent.integrityStatus).append("\n\n");

        builder.append("Submitted Documents:\n");
        for (String doc : currentStudent.documents) {
            builder.append("- ").append(doc).append("\n");
        }

        studentProfileArea.setText(builder.toString());

        studentNotificationModel.clear();
        for (Notification notification : currentStudent.notifications) {
            String label = notification.message;
            if (notification.acknowledged) {
                label += " [Acknowledged]";
            }
            studentNotificationModel.addElement(label);
        }
    }

    private void acknowledgeSelectedNotification() {
        if (currentStudent == null) {
            return;
        }

        int selectedIndex = studentNotificationList.getSelectedIndex();
        if (selectedIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select a notification first.");
            return;
        }

        currentStudent.notifications.get(selectedIndex).acknowledged = true;
        refreshStudentDashboard();
        JOptionPane.showMessageDialog(this, "Notification acknowledged and relayed to instructor.");
    }

    private void refreshInstructorDashboard() {
        if (currentInstructor == null) {
            return;
        }

        instructorCourseModel.clear();
        for (String course : currentInstructor.courses) {
            instructorCourseModel.addElement(course);
        }

        instructorStudentModel.clear();
        instructorStudentViewArea.setText("");
    }

    private void refreshInstructorStudentList() {
        instructorStudentModel.clear();
        instructorStudentViewArea.setText("");

        if (currentInstructor == null) {
            return;
        }

        String selectedCourse = instructorCourseList.getSelectedValue();
        if (selectedCourse == null) {
            return;
        }

        List<Student> students = dataStore.getStudentsByCourse(selectedCourse);
        for (Student student : students) {
            instructorStudentModel.addElement(student.id + " - " + student.name);
        }
    }

    private void refreshInstructorSelectedStudentView() {
        if (currentInstructor == null) {
            return;
        }

        String selectedStudentEntry = instructorStudentList.getSelectedValue();
        if (selectedStudentEntry == null) {
            instructorStudentViewArea.setText("");
            return;
        }

        String studentId = selectedStudentEntry.split(" - ")[0];
        Student student = dataStore.getStudentById(studentId);

        if (student == null) {
            instructorStudentViewArea.setText("");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(student.name).append("\n");
        builder.append("Student ID: ").append(student.id).append("\n");
        builder.append("Integrity Status: ").append(student.integrityStatus).append("\n\n");

        builder.append("Documents:\n");
        for (String doc : student.documents) {
            builder.append("- ").append(doc).append("\n");
        }

        builder.append("\nNotifications:\n");
        for (Notification notification : student.notifications) {
            builder.append("- ").append(notification.message);
            if (notification.acknowledged) {
                builder.append(" [Acknowledged]");
            }
            builder.append("\n");
        }

        instructorStudentViewArea.setText(builder.toString());
    }

    private void refreshAdminDashboard() {
        if (currentAdmin == null) {
            return;
        }

        adminInstructorModel.clear();
        for (Instructor instructor : dataStore.instructors.values()) {
            adminInstructorModel.addElement(instructor.id + " - " + instructor.name);
        }

        adminInstructorViewArea.setText("");
        adminPolicyArea.setText(dataStore.getPolicies());
    }

    private void refreshAdminSelectedInstructorView() {
        String selectedEntry = adminInstructorList.getSelectedValue();
        if (selectedEntry == null) {
            adminInstructorViewArea.setText("");
            return;
        }

        String instructorId = selectedEntry.split(" - ")[0];
        Instructor instructor = dataStore.instructors.get(instructorId);

        if (instructor == null) {
            adminInstructorViewArea.setText("");
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Name: ").append(instructor.name).append("\n");
        builder.append("Instructor ID: ").append(instructor.id).append("\n\n");
        builder.append("Courses:\n");
        for (String course : instructor.courses) {
            builder.append("- ").append(course).append("\n");
        }

        adminInstructorViewArea.setText(builder.toString());
    }

    private JPanel createBasePanel(String titleText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(40, 80, 40, 80));

        JLabel title = new JLabel(titleText);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("SansSerif", Font.BOLD, 30));

        panel.add(title);
        panel.add(Box.createVerticalStrut(30));

        return panel;
    }

    private JPanel wrapCentered(JPanel innerPanel) {
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.add(innerPanel);
        return wrapper;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 40));
        return button;
    }

    private JTextArea createTextArea() {
        JTextArea area = new JTextArea();
        area.setEditable(true);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AcademicIntegrityTrackerApp app = new AcademicIntegrityTrackerApp();
            app.setVisible(true);
        });
    }

    static class Notification {
        String message;
        boolean acknowledged;

        Notification(String message, boolean acknowledged) {
            this.message = message;
            this.acknowledged = acknowledged;
        }
    }

    static class Student {
        String id;
        String password;
        String name;
        String integrityStatus;
        List<String> documents;
        List<Notification> notifications;
        List<String> courses;

        Student(String id, String password, String name, String integrityStatus) {
            this.id = id;
            this.password = password;
            this.name = name;
            this.integrityStatus = integrityStatus;
            this.documents = new ArrayList<>();
            this.notifications = new ArrayList<>();
            this.courses = new ArrayList<>();
        }
    }

    static class Instructor {
        String id;
        String password;
        String name;
        List<String> courses;

        Instructor(String id, String password, String name) {
            this.id = id;
            this.password = password;
            this.name = name;
            this.courses = new ArrayList<>();
        }
    }

    static class Admin {
        String id;
        String password;
        String otp;
        String name;

        Admin(String id, String password, String otp, String name) {
            this.id = id;
            this.password = password;
            this.otp = otp;
            this.name = name;
        }
    }

    static class DataStore {
        Map<String, Student> students;
        Map<String, Instructor> instructors;
        Map<String, Admin> admins;
        private String policies;

        DataStore() {
            students = new LinkedHashMap<>();
            instructors = new LinkedHashMap<>();
            admins = new LinkedHashMap<>();
            seedData();
        }

        private void seedData() {
            Student s1 = new Student("S1001", "pass123", "Alice Nguyen", "Under Review");
            s1.documents.add("Essay 1 Submission");
            s1.documents.add("Lab Report 2");
            s1.notifications.add(new Notification("Possible plagiarism detected in Essay 1.", false));
            s1.notifications.add(new Notification("Meeting requested by instructor.", false));
            s1.courses.add("CP317");
            s1.courses.add("UU150");

            Student s2 = new Student("S1002", "pass123", "Liam Patel", "Clear");
            s2.documents.add("Assignment 3 Submission");
            s2.notifications.add(new Notification("No new alerts.", true));
            s2.courses.add("CP317");

            Student s3 = new Student("S1003", "pass123", "Maya Chen", "Flagged");
            s3.documents.add("Final Project Draft");
            s3.notifications.add(new Notification("Repeat integrity pattern threshold reached.", false));
            s3.courses.add("CP220");

            students.put(s1.id, s1);
            students.put(s2.id, s2);
            students.put(s3.id, s3);

            Instructor i1 = new Instructor("I2001", "teach123", "Dr. Harper");
            i1.courses.add("CP317");
            i1.courses.add("UU150");

            Instructor i2 = new Instructor("I2002", "teach123", "Prof. Singh");
            i2.courses.add("CP220");

            instructors.put(i1.id, i1);
            instructors.put(i2.id, i2);

            Admin a1 = new Admin("A3001", "admin123", "9999", "System Admin");
            admins.put(a1.id, a1);

            policies =
                "Policy 1: Students must submit original work.\n" +
                "Policy 2: Instructors may flag integrity-related incidents.\n" +
                "Policy 3: Repeated incidents may trigger disciplinary review.\n" +
                "Policy 4: Administrators may update policy references and access rules.";
        }

        Student authenticateStudent(String id, String password) {
            Student student = students.get(id);
            if (student != null && student.password.equals(password)) {
                return student;
            }
            return null;
        }

        Instructor authenticateInstructor(String id, String password) {
            Instructor instructor = instructors.get(id);
            if (instructor != null && instructor.password.equals(password)) {
                return instructor;
            }
            return null;
        }

        Admin authenticateAdmin(String id, String password, String otp) {
            Admin admin = admins.get(id);
            if (admin != null && admin.password.equals(password) && admin.otp.equals(otp)) {
                return admin;
            }
            return null;
        }

        Student getStudentById(String id) {
            return students.get(id);
        }

        List<Student> getStudentsByCourse(String course) {
            List<Student> result = new ArrayList<>();
            for (Student student : students.values()) {
                if (student.courses.contains(course)) {
                    result.add(student);
                }
            }
            return result;
        }

        String getPolicies() {
            return policies;
        }

        void setPolicies(String policies) {
            this.policies = policies;
        }
    }
}