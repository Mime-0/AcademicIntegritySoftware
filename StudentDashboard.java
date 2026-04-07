import java.awt.*;
import javax.swing.*;

public class StudentDashboard extends JFrame {

    public StudentDashboard() {
        setTitle("Student Dashboard");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Student Profile + Notifications", JLabel.CENTER);

        JButton logout = new JButton("Logout");
        logout.addActionListener(e -> {
            dispose();
            new LoginScreen();
        });

        add(label, BorderLayout.CENTER);
        add(logout, BorderLayout.SOUTH);

        setVisible(true);
    }
}