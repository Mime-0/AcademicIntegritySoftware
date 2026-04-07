import java.awt.*;
import javax.swing.*;

public class InstructorDashboard extends JFrame {

    public InstructorDashboard() {
        setTitle("Instructor Dashboard");
        setSize(400, 300);

        JLabel label = new JLabel("Courses + Students View", JLabel.CENTER);

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