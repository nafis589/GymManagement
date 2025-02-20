package views;

import dao.StatisticsDAO;
import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private StatisticsDAO statisticsDAO;
    private JLabel activeMembersLabel;
    private JLabel monthlyRevenueLabel;

    public StatisticsPanel() {
        statisticsDAO = new StatisticsDAO();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Gym Statistics", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);

        // Active Members
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(new JLabel("Active Members:"), gbc);
        
        activeMembersLabel = new JLabel("0");
        gbc.gridx = 1;
        add(activeMembersLabel, gbc);

        // Monthly Revenue
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Monthly Revenue:"), gbc);
        
        monthlyRevenueLabel = new JLabel("$0.00");
        gbc.gridx = 1;
        add(monthlyRevenueLabel, gbc);

        // Refresh Button
        JButton refreshButton = new JButton("Refresh Statistics");
        refreshButton.addActionListener(e -> refreshStatistics());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(refreshButton, gbc);

        // Initial load
        refreshStatistics();
    }

    private void refreshStatistics() {
        try {
            int activeMembers = statisticsDAO.getActiveMembers();
            double monthlyRevenue = statisticsDAO.getMonthlyRevenue();

            activeMembersLabel.setText(String.valueOf(activeMembers));
            monthlyRevenueLabel.setText(String.format("$%.2f", monthlyRevenue));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading statistics: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}