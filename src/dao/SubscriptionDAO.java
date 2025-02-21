package dao;

import models.Subscription;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAO {
    public SubscriptionDAO() {
        // Constructor is now empty as we'll get fresh connections for each operation
    }

    public void addSubscription(Subscription subscription) throws SQLException {
        String query = "INSERT INTO abonnement (libelle, duree_mois, prix_mensuel) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, subscription.getOfferName());
            pst.setInt(2, subscription.getDurationMonths());
            pst.setDouble(3, subscription.getMonthlyPrice());
            pst.executeUpdate();
        }
    }

    public List<Subscription> getAllSubscriptions() throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = "SELECT * FROM abonnement";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                subscriptions.add(new Subscription(
                    rs.getInt("id_abonnement"),
                    rs.getString("libelle"),
                    rs.getInt("duree_mois"),
                    rs.getDouble("prix_mensuel")
                ));
            }
        }
        return subscriptions;
    }

    public void updateSubscription(Subscription subscription) throws SQLException {
        String query = "UPDATE abonnement SET libelle=?, duree_mois=?, prix_mensuel=? WHERE id_abonnement=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, subscription.getOfferName());
            pst.setInt(2, subscription.getDurationMonths());
            pst.setDouble(3, subscription.getMonthlyPrice());
            pst.setInt(4, subscription.getId());
            pst.executeUpdate();
        }
    }

    public void deleteSubscription(int id) throws SQLException {
        String query = "DELETE FROM abonnement WHERE id_abonnement=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }
}