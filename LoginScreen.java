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
            dispose();
            new StudentDashboard();
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
}
