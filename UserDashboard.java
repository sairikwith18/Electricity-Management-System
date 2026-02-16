import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class UserDashboard extends JFrame {
    private String username;

    private JButton getBillButton, payBillButton, logoutButton;

    public UserDashboard(String username) {
        this.username = username;

        setTitle("User Dashboard");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        getBillButton = new JButton("Get Bill");
        payBillButton = new JButton("Pay Bill");
        logoutButton = new JButton("Logout");

        panel.add(getBillButton);
        panel.add(payBillButton);
        panel.add(logoutButton);

        add(panel);

        getBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] userDetails = getUserDetails(username);
                if (userDetails != null) {
                    showBillPopup(userDetails);
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        payBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] userDetails = getUserDetails(username);
                if (userDetails != null) {
                    showPayBillPopup(userDetails);
                } else {
                    JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LoginPage().setVisible(true);
                dispose();
            }
        });
    }

    private String[] getUserDetails(String username) {
        List<String[]> users = DataStorage.getUsers();
        for (String[] user : users) {
            if (user[0].equals(username)) {
                return user;
            }
        }
        return null;
    }

    private void showBillPopup(String[] userDetails) {
        String username = userDetails[0];
        double totalDue = Double.parseDouble(userDetails[2]);

        String[] durationOptions = {"1 Month", "2 Months", "3 Months"};
        String duration = (String) JOptionPane.showInputDialog(this, "Select Duration:",
                "Electricity Bill", JOptionPane.QUESTION_MESSAGE, null, durationOptions, durationOptions[0]);

        if (duration == null) return;

        double billAmount = totalDue;
        double finalAmount = billAmount;
        finalAmount = Math.round(finalAmount * 100.0) / 100.0;

        String message = "Electricity Bill for " + username + ":\n\n" +
                         "Duration: " + duration + "\n" +
                         "Amount Due: $" + String.format("%.2f", billAmount) + "\n" +
                         "Total Due: $" + String.format("%.2f", finalAmount);

        JOptionPane.showMessageDialog(this, message, "Electricity Bill", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showPayBillPopup(String[] userDetails) {
        String username = userDetails[0];
        double totalDue = Double.parseDouble(userDetails[2]);

        if (totalDue == 0) {
            JOptionPane.showMessageDialog(this, "No outstanding balance. No payment needed.", "No Payment", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String message = "Electricity Bill for " + username + ":\n\n" +
                         "Amount Due: $" + String.format("%.2f", totalDue) + "\n" +
                         "Would you like to pay now?";

        int option = JOptionPane.showConfirmDialog(this, message, "Pay Bill", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            processPayment(userDetails);
            JOptionPane.showMessageDialog(this, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void processPayment(String[] userDetails) {
        List<String[]> users = DataStorage.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i)[0].equals(userDetails[0])) {
                users.get(i)[2] = "0";
                break;
            }
        }
        DataStorage.saveUsers(users);
    }

    public static void main(String[] args) {
        new UserDashboard("testUser").setVisible(true);
    }
}
