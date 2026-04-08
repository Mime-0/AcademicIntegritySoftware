import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginScreen extends JPanel {

    private final AppFrame app;

    private final JComboBox<String> roleBox;
    private final JTextField idField;
    private final JPasswordField passwordField;
    private final JTextField otpField;

    public LoginScreen(AppFrame app) {
        this.app = app;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Academic Integrity Tracker - Login", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));

        roleBox = new JComboBox<>(new String[] { "Student", "Instructor", "Administrator" });
        idField = new JTextField();
        passwordField = new JPasswordField();
        otpField = new JTextField();

        form.add(new JLabel("Role:"));
        form.add(roleBox);
        form.add(new JLabel("User ID:"));
        form.add(idField);
        form.add(new JLabel("Password:"));
        form.add(passwordField);
        form.add(new JLabel("OTP (Admin only):"));
        form.add(otpField);

        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear");
        form.add(loginButton);
        form.add(clearButton);

        add(form, BorderLayout.CENTER);

        roleBox.addActionListener(e -> updateOtpVisibility());
        loginButton.addActionListener(e -> attemptLogin());
        clearButton.addActionListener(e -> resetFields());

        updateOtpVisibility();
    }

    public void resetFields() {
        idField.setText("");
        passwordField.setText("");
        otpField.setText("");
        updateOtpVisibility();
    }

    private void updateOtpVisibility() {
        boolean showOtp = "Administrator".equals(roleBox.getSelectedItem());
        otpField.setEnabled(showOtp);
        if (!showOtp) {
            otpField.setText("");
        }
    }

    private void attemptLogin() {
        String role = (String) roleBox.getSelectedItem();
        String id = idField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String otp = otpField.getText().trim();

        boolean success = false;

        if ("Student".equals(role)) {
            success = app.loginStudent(id, password);
        } else if ("Instructor".equals(role)) {
            success = app.loginInstructor(id, password);
        } else if ("Administrator".equals(role)) {
            success = app.loginAdmin(id, password, otp);
        }

        if (!success) {
            JOptionPane.showMessageDialog(app, "Invalid login credentials.");
        }
    }
}
