package views;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private MemberPanel memberPanel;
    private SubscriptionPanel subscriptionPanel;
    private StatisticsPanel statisticsPanel;
    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Gym Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    
        // Initialize components
        memberPanel = new MemberPanel();
        subscriptionPanel = new SubscriptionPanel();
        statisticsPanel = new StatisticsPanel();
    
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Members", new ImageIcon(), memberPanel, "Manage gym members");
        tabbedPane.addTab("Subscriptions", new ImageIcon(), subscriptionPanel, "Manage subscription plans");
        tabbedPane.addTab("Statistics", new ImageIcon(), statisticsPanel, "View gym statistics");
    
        // Add to frame
        add(tabbedPane);
    
        // Menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
    }
    
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
    
        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
    
        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
    
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
    
        return menuBar;
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Gym Management System\nVersion 1.0\n© 2024",
            "About",
            JOptionPane.INFORMATION_MESSAGE);
    }
}