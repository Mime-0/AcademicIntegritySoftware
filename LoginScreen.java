import java.awt.*;
import javax.swing.*;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setTitle("Academic Integrity Tracker - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1));

        JLabel title = new JLabel("Select Login Type", JLabel.CENTER);

        JButton studentBtn = new JButton("Student Login");
        JButton instructorBtn = new JButton("Instructor Login");
        JButton adminBtn = new JButton("Admin Login");

        studentBtn.addActionListener(e -> {
            studentLogin();
        });

        instructorBtn.addActionListener(e -> {
            dispose();
            new InstructorDashboard();
        });

        adminBtn.addActionListener(e -> {
            dispose();
            new AdminDashboard();
        });

        add(title);
        add(studentBtn);
        add(instructorBtn);
        add(adminBtn);

        setVisible(true);
    }
    public void studentLogin() {
        JDialog loginPopup = new JDialog(this, "Student Login", true);
        JPanel loginPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        loginPopup.setSize(300,150);
        JButton back = new JButton("Back to Main");
        JButton submit = new JButton("Submit");
        JTextField studentID = new JTextField();
        JLabel student = new JLabel("Student ID:");
        JTextField studentPass = new JTextField();
        JLabel pass = new JLabel ("Password:");
        back.addActionListener(e -> loginPopup.dispose());
        submit.addActionListener(e-> {dispose(); new StudentDashboard();});
        loginPanel.add(student);
        loginPanel.add(pass);
        loginPanel.add(studentID);
        loginPanel.add(studentPass);
        loginPanel.add(submit);
        loginPanel.add(back);
        loginPopup.add(loginPanel);
        loginPopup.setVisible(true);
    }
}
