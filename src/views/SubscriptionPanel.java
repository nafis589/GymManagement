package views;

import models.Subscription;
import dao.SubscriptionDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SubscriptionPanel extends JPanel {
    private JTable subscriptionTable;
    private DefaultTableModel tableModel;
    private SubscriptionDAO subscriptionDAO;

    public SubscriptionPanel() {
        subscriptionDAO = new SubscriptionDAO();
        setLayout(new BorderLayout());

        // Create table
        String[] columns = {"ID", "Offer Name", "Duration (Months)", "Monthly Price"};
        tableModel = new DefaultTableModel(columns, 0);
        subscriptionTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(subscriptionTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Subscription");
        JButton editButton = new JButton("Edit Subscription");
        JButton deleteButton = new JButton("Delete Subscription");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> showAddSubscriptionDialog());
        editButton.addActionListener(e -> showEditSubscriptionDialog());
        deleteButton.addActionListener(e -> deleteSubscription());
        refreshButton.addActionListener(e -> refreshTable());

        // Initial load
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<Subscription> subscriptions = subscriptionDAO.getAllSubscriptions();
            for (Subscription subscription : subscriptions) {
                tableModel.addRow(new Object[]{
                    subscription.getId(),
                    subscription.getOfferName(),
                    subscription.getDurationMonths(),
                    subscription.getMonthlyPrice()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading subscriptions: " + e.getMessage());
        }
    }

    private void showAddSubscriptionDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Subscription", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField offerNameField = new JTextField(20);
        JSpinner durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 60, 1));
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 10000.0, 10.0));

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Offer Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(offerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Duration (Months):"), gbc);
        gbc.gridx = 1;
        dialog.add(durationSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Monthly Price:"), gbc);
        gbc.gridx = 1;
        dialog.add(priceSpinner, gbc);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                Subscription subscription = new Subscription(
                    0,
                    offerNameField.getText(),
                    (Integer) durationSpinner.getValue(),
                    (Double) priceSpinner.getValue()
                );
                subscriptionDAO.addSubscription(subscription);
                dialog.dispose();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error adding subscription: " + ex.getMessage());
            }
        });

        gbc.gridx = 1; gbc.gridy = 3;
        dialog.add(saveButton, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showEditSubscriptionDialog() {
        int selectedRow = subscriptionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a subscription to edit");
            return;
        }

        int id = (int) subscriptionTable.getValueAt(selectedRow, 0);
        String offerName = (String) subscriptionTable.getValueAt(selectedRow, 1);
        int duration = (int) subscriptionTable.getValueAt(selectedRow, 2);
        double price = (double) subscriptionTable.getValueAt(selectedRow, 3);

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Subscription", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField offerNameField = new JTextField(offerName, 20);
        JSpinner durationSpinner = new JSpinner(new SpinnerNumberModel(duration, 1, 60, 1));
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(price, 0.0, 10000.0, 10.0));

        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Offer Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(offerNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Duration (Months):"), gbc);
        gbc.gridx = 1;
        dialog.add(durationSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Monthly Price:"), gbc);
        gbc.gridx = 1;
        dialog.add(priceSpinner, gbc);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            try {
                Subscription subscription = new Subscription(
                    id,
                    offerNameField.getText(),
                    (Integer) durationSpinner.getValue(),
                    (Double) priceSpinner.getValue()
                );
                subscriptionDAO.updateSubscription(subscription);
                dialog.dispose();
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error updating subscription: " + ex.getMessage());
            }
        });

        gbc.gridx = 1; gbc.gridy = 3;
        dialog.add(updateButton, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deleteSubscription() {
        int selectedRow = subscriptionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a subscription to delete");
            return;
        }

        int id = (int) subscriptionTable.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this subscription?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                subscriptionDAO.deleteSubscription(id);
                refreshTable();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error deleting subscription: " + e.getMessage());
            }
        }
    }
}