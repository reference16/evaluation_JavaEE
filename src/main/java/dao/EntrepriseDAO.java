package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Entreprise;
import utils.DatabaseConnection;

/**
 * DAO pour la gestion des entreprises.
 */
public class EntrepriseDAO {

    /**
     * Ajoute une nouvelle entreprise dans la base de données.
     * @param entreprise L'entreprise à ajouter
     * @return boolean true si l'ajout est réussi, false sinon
     */
    public static boolean addEntreprise(Entreprise entreprise) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String query = "INSERT INTO entreprise (nomEnt, adresseEnt, chiffreAffaire, dateCreation) VALUES (?, ?, ?, ?)";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);

            stmt.setString(1, entreprise.getNom());
            stmt.setString(2, entreprise.getAdresse());
            stmt.setDouble(3, entreprise.getChiffreAffaire());
            stmt.setDate(4, new java.sql.Date(entreprise.getDateCreation().getTime()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'entreprise : " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Récupère une entreprise par son ID.
     * @param id L'ID de l'entreprise
     * @return Entreprise l'entreprise trouvée ou null si non trouvée
     */
    public static Entreprise getEntrepriseById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM entreprise WHERE id_entreprise = ?";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new Entreprise(
                    rs.getInt("id_entreprise"),
                    rs.getString("nomEnt"),
                    rs.getString("adresseEnt"),
                    rs.getDouble("chiffreAffaire"),
                    rs.getDate("dateCreation")
                );
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'entreprise : " + e.getMessage());
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Récupère la liste de toutes les entreprises.
     * @return List<Entreprise> Liste des entreprises
     */
    public static List<Entreprise> getAllEntreprises() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Entreprise> entreprises = new ArrayList<>();
        
        String query = "SELECT * FROM entreprise ORDER BY nomEnt";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                entreprises.add(new Entreprise(
                    rs.getInt("id_entreprise"),
                    rs.getString("nomEnt"),
                    rs.getString("adresseEnt"),
                    rs.getDouble("chiffreAffaire"),
                    rs.getDate("dateCreation")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des entreprises : " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
        return entreprises;
    }

    /**
     * Met à jour les informations d'une entreprise.
     * @param entreprise L'entreprise avec les nouvelles informations
     * @return boolean true si la mise à jour a réussi, false sinon
     */
    public static boolean updateEntreprise(Entreprise entreprise) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String query = "UPDATE entreprise SET nomEnt = ?, adresseEnt = ?, chiffreAffaire = ?, dateCreation = ? WHERE id_entreprise = ?";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);

            stmt.setString(1, entreprise.getNom());
            stmt.setString(2, entreprise.getAdresse());
            stmt.setDouble(3, entreprise.getChiffreAffaire());
            stmt.setDate(4, new java.sql.Date(entreprise.getDateCreation().getTime()));
            stmt.setInt(5, entreprise.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour de l'entreprise : " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }

    /**
     * Supprime une entreprise de la base de données.
     * @param id L'ID de l'entreprise à supprimer
     * @return boolean true si la suppression a réussi, false sinon
     */
    public static boolean deleteEntreprise(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String query = "DELETE FROM entreprise WHERE id_entreprise = ?";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'entreprise : " + e.getMessage());
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) DatabaseConnection.closeConnection(conn);
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture des ressources : " + e.getMessage());
            }
        }
    }
}
