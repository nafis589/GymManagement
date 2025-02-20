package views;

import models.Member;
import dao.MemberDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

public class MemberPanel extends JPanel {
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private MemberDAO memberDAO;

    public MemberPanel() {
        try {
            memberDAO = new MemberDAO();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error initializing Member Panel: " + e.getMessage(),
                "Initialization Error",
                JOptionPane.ERROR_MESSAGE);
        }
        setLayout(new BorderLayout());

        // Create table
        String[] columns = {"ID", "Last Name", "First Name", "Registration Date", "Phone", "Active"};
        tableModel = new DefaultTableModel(columns, 0);
        memberTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(memberTable);
        add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Member");
        JButton editButton = new JButton("Edit Member");
        JButton deleteButton = new JButton("Delete Member");
        JButton refreshButton = new JButton("Refresh");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> showAddMemberDialog());
        editButton.addActionListener(e -> showEditMemberDialog());
        deleteButton.addActionListener(e -> deleteMember());
        refreshButton.addActionListener(e -> refreshTable());

        // Initial load
        refreshTable();
    }

    private void showAddMemberDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Add New Member", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField lastNameField = new JTextField(20);
        JTextField firstNameField = new JTextField(20);
        JTextField phoneField = new JTextField(20);

        // Add components
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        dialog.add(phoneField, gbc);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                Member newMember = new Member();
                newMember.setLastName(lastNameField.getText());
                newMember.setFirstName(firstNameField.getText());
                newMember.setPhoneNumber(phoneField.getText());
                newMember.setRegistrationDate(new Date());
                newMember.setSubscriptionStatus(true);

                memberDAO.addMember(newMember);
                refreshTable();
                dialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error adding member: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showEditMemberDialog() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to edit", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Get selected member data
        int memberId = (int) memberTable.getValueAt(selectedRow, 0);
        Member member;
        try {
            member = memberDAO.getMemberById(memberId);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error retrieving member: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Edit Member", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields
        JTextField lastNameField = new JTextField(member.getLastName(), 20);
        JTextField firstNameField = new JTextField(member.getFirstName(), 20);
        JTextField phoneField = new JTextField(member.getPhoneNumber(), 20);
        JCheckBox activeCheckBox = new JCheckBox("Active", member.isSubscriptionStatus());

        // Add components
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(lastNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1;
        dialog.add(firstNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        dialog.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(activeCheckBox, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                member.setLastName(lastNameField.getText());
                member.setFirstName(firstNameField.getText());
                member.setPhoneNumber(phoneField.getText());
                member.setSubscriptionStatus(activeCheckBox.isSelected());

                memberDAO.updateMember(member);
                refreshTable();
                dialog.dispose();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "Error updating member: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deleteMember() {
        int selectedRow = memberTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a member to delete", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this member?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int memberId = (int) tableModel.getValueAt(selectedRow, 0);
                memberDAO.deleteMember(memberId);
                refreshTable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error deleting member: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshTable() {
        try {
            tableModel.setRowCount(0);
            List<Member> members = memberDAO.getAllMembers();
            for (Member member : members) {
                Object[] row = {
                    member.getId(),
                    member.getLastName(),
                    member.getFirstName(),
                    member.getRegistrationDate(),
                    member.getPhoneNumber(),
                    member.isSubscriptionStatus()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error refreshing table: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}