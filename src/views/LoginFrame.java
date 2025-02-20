package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Gym Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 1;
        gbc.gridy = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        mainPanel.add(loginButton, gbc);

        add(mainPanel);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // For demo purposes, use simple credentials
        if (username.equals("admin") && password.equals("admin123")) {
            new MainFrame().setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Invalid credentials", 
                "Login Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}