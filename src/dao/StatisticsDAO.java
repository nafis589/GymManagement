package dao;

import database.DatabaseConnection;
import java.sql.*;


public class StatisticsDAO {
    public StatisticsDAO() {
        // Constructor is now empty as we'll get fresh connections for each operation
    }

    public int getActiveMembers() throws SQLException {
        String query = "SELECT COUNT(*) FROM abonne WHERE statut_souscription = true";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public double getMonthlyRevenue() throws SQLException {
        String query = "SELECT SUM(a.prix_mensuel) FROM abonnement a " +
                      "INNER JOIN souscription s ON a.id = s.id_abonnement " +
                      "INNER JOIN abonne m ON s.id_abonne = m.id " +
                      "WHERE m.statut_souscription = true";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        }
        return 0.0;
    }
}