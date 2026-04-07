import java.awt.*;
import javax.swing.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 300);

        JLabel label = new JLabel("Instructor + Policy Management", JLabel.CENTER);

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