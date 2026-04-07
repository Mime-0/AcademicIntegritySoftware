import java.awt.*;
import java.util.List;
import javax.swing.*;

public class AppFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private final DataStore dataStore;

    private final LoginScreen loginPanel;
    private final StudentDashboard studentDashboardPanel;
    private final InstructorDashboard instructorDashboardPanel;
    private final AdminDashboard adminDashboardPanel;

    private Student currentStudent;
    private Instructor currentInstructor;
    private Admin currentAdmin;

    public AppFrame() {
        super("Academic Integrity Tracker");
        this.dataStore = new DataStore();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        loginPanel = new LoginScreen(this);
        studentDashboardPanel = new StudentDashboard(this);
        instructorDashboardPanel = new InstructorDashboard(this);
        adminDashboardPanel = new AdminDashboard(this);

        cardPanel.add(loginPanel, "LOGIN");
        cardPanel.add(studentDashboardPanel, "STUDENT");
        cardPanel.add(instructorDashboardPanel, "INSTRUCTOR");
        cardPanel.add(adminDashboardPanel, "ADMIN");

        add(cardPanel);
        showLogin();
    }

    public DataStore getDataStore() {
        return dataStore;
    }

    public void showLogin() {
        currentStudent = null;
        currentInstructor = null;
        currentAdmin = null;
        loginPanel.resetFields();
        cardLayout.show(cardPanel, "LOGIN");
    }

    public boolean loginStudent(String id, String password) {
        Student student = dataStore.authenticateStudent(id, password);
        if (student != null) {
            currentStudent = student;
            studentDashboardPanel.loadStudent(student);
            cardLayout.show(cardPanel, "STUDENT");
            return true;
        }
        return false;
    }

    public boolean loginInstructor(String id, String password) {
        Instructor instructor = dataStore.authenticateInstructor(id, password);
        if (instructor != null) {
            currentInstructor = instructor;
            instructorDashboardPanel.loadInstructor(instructor);
            cardLayout.show(cardPanel, "INSTRUCTOR");
            return true;
        }
        return false;
    }

    public boolean loginAdmin(String id, String password, String otp) {
        Admin admin = dataStore.authenticateAdmin(id, password, otp);
        if (admin != null) {
            currentAdmin = admin;
            adminDashboardPanel.loadAdmin(admin);
            cardLayout.show(cardPanel, "ADMIN");
            return true;
        }
        return false;
    }

    public Student getCurrentStudent() {
        return currentStudent;
    }

    public Instructor getCurrentInstructor() {
        return currentInstructor;
    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public List<Student> getStudentsByCourse(String courseCode) {
        return dataStore.getStudentsByCourse(courseCode);
    }

    public Student getStudentById(String id) {
        return dataStore.getStudentById(id);
    }

    public Instructor getInstructorById(String id) {
        return dataStore.getInstructorById(id);
    }

    public List<Instructor> getAllInstructors() {
        return dataStore.getAllInstructors();
    }
}