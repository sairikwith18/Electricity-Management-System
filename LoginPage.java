import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {
    private JLabel usernameLabel, passwordLabel, roleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;

    public LoginPage() {
        setTitle("Login Page");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2));

        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        roleLabel = new JLabel("Role:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[] { "Admin", "User" });

        loginButton = new JButton("Login");

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameField);
        mainPanel.add(passwordLabel);
        mainPanel.add(passwordField);
        mainPanel.add(roleLabel);
        mainPanel.add(roleComboBox);
        mainPanel.add(new JLabel());
        mainPanel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        add(mainPanel);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (role.equals("Admin") && "rikwith".equals(username) && "112".equals(password)) {
            new AdminDashboard().setVisible(true);
            dispose();
        } else if (role.equals("User") && DataStorage.verifyUser(username, password)) {
            new UserDashboard(username).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.");
        }
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}
