package dao;

import models.Member;
import database.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;


public class MemberDAO {
    public MemberDAO() {
        // Constructor is now empty as we'll get fresh connections for each operation
    }

    public void addMember(Member member) throws SQLException {
        String query = "INSERT INTO abonne (nom, prenom, date_inscription, numero_telephone, statut_souscription) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, member.getLastName());
            pst.setString(2, member.getFirstName());
            pst.setDate(3, new java.sql.Date(member.getRegistrationDate().getTime()));
            pst.setString(4, member.getPhoneNumber());
            pst.setBoolean(5, member.isSubscriptionStatus());
            pst.executeUpdate();
        }
    }

    public List<Member> getAllMembers() throws SQLException {
        List<Member> members = new ArrayList<>();
        String query = "SELECT * FROM abonne";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                members.add(new Member(
                    rs.getInt("id_abonne"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getDate("date_inscription"),
                    rs.getString("numero_telephone"),
                    rs.getBoolean("statut_souscription")
                ));
            }
        }
        return members;
    }

    public Member getMemberById(int id) throws SQLException {
        String query = "SELECT * FROM abonne WHERE id_abonne = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                        rs.getInt("id_abonne"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_inscription"),
                        rs.getString("numero_telephone"),
                        rs.getBoolean("statut_souscription")
                    );
                }
            }
        }
        return null;
    }

    public void updateMember(Member member) throws SQLException {
        String query = "UPDATE abonne SET nom=?, prenom=?, numero_telephone=?, statut_souscription=? WHERE id_abonne=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, member.getLastName());
            pst.setString(2, member.getFirstName());
            pst.setString(3, member.getPhoneNumber());
            pst.setBoolean(4, member.isSubscriptionStatus());
            pst.setInt(5, member.getId());
            pst.executeUpdate();
        }
    }

    public void deleteMember(int id) throws SQLException {
        String query = "DELETE FROM abonne WHERE id_abonne=?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }
}