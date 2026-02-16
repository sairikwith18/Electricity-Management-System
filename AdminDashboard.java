import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard extends JFrame {
    private JLabel welcomeLabel;
    private JPanel mainPanel;
    private JButton newUserButton, showUsersButton, addBillButton, deleteUserButton, logoutButton;
    private JTable userTable;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        add(mainPanel, BorderLayout.CENTER);

        welcomeLabel = new JLabel("Welcome Admin", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));

        newUserButton = new JButton("New User");
        showUsersButton = new JButton("Show Users");
        addBillButton = new JButton("Add Bill");
        deleteUserButton = new JButton("Delete User");
        logoutButton = new JButton("Logout");

        buttonPanel.add(newUserButton);
        buttonPanel.add(showUsersButton);
        buttonPanel.add(addBillButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(logoutButton);

        newUserButton.addActionListener(e -> createNewUser());
        showUsersButton.addActionListener(e -> showUsers());
        addBillButton.addActionListener(e -> addBill());
        deleteUserButton.addActionListener(e -> deleteUser());
        logoutButton.addActionListener(e -> {
            new LoginPage().setVisible(true);
            dispose();
        });

        mainPanel.add(welcomeLabel);
        mainPanel.add(buttonPanel);

        JPanel tablePanel = new JPanel(new BorderLayout());
        userTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(userTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel);
    }

    private void createNewUser() {
        String username = JOptionPane.showInputDialog(this, "Enter username:");
        List<String[]> users = DataStorage.getUsers();
        for (String[] user : users) {
            if (user[0].equals(username)) {
                JOptionPane.showMessageDialog(this, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        String password = JOptionPane.showInputDialog(this, "Enter password:");
        if (password.length() < 8) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DataStorage.saveUser(username, password);
        JOptionPane.showMessageDialog(this, "User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showUsers() {
        List<String[]> users = DataStorage.getUsers();
        String[] columnNames = { "Username", "Password", "Total Due", "Last Bill", "Month" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        for (String[] user : users) {
            tableModel.addRow(user);
        }
        userTable.setModel(tableModel);
    }

    private void addBill() {
        String username = JOptionPane.showInputDialog(this, "Enter username:");
        int month = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter month number (1-3):"));
        double amount = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter bill amount for this month:"));
        DataStorage.saveBill(username, month, amount);
    }

    private void deleteUser() {
        String usernameToDelete = JOptionPane.showInputDialog(this, "Enter the username to delete:");
        List<String[]> users = DataStorage.getUsers();
        boolean userFound = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i)[0].equals(usernameToDelete)) {
                users.remove(i);
                userFound = true;
                break;
            }
        }
        if (userFound) {
            DataStorage.saveUsers(users);
            JOptionPane.showMessageDialog(this, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showUsers();
        } else {
            JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
