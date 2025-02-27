package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Employe;
import models.Entreprise;
import utils.DatabaseConnection;

/**
 * DAO pour la gestion des employés.
 */
public class EmployeDAO {

    /**
     * Ajoute un nouvel employé dans la base de données.
     * @param employe L'employé à ajouter
     * @return boolean true si l'ajout est réussi, false sinon
     */
    public static boolean addEmploye(Employe employe) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String query = "INSERT INTO employe (matricule, nomEmp, prenomEmp, fonctionEmp, serviceEmp, dateEmbauche, sexeEmp, salaireBase, id_entreprise) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, employe.getMatricule());
            stmt.setString(2, employe.getNom());
            stmt.setString(3, employe.getPrenom());
            stmt.setString(4, employe.getFonction());
            stmt.setString(5, employe.getService());
            stmt.setDate(6, new java.sql.Date(employe.getDateEmbauche().getTime()));
            stmt.setString(7, employe.getSexe());
            stmt.setDouble(8, employe.getSalaireBase());
            stmt.setNull(9, java.sql.Types.INTEGER); // Pas d'entreprise

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    employe.setId(rs.getInt(1));
                    return true;
                }
            }
            return false;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'employé : " + e.getMessage());
            return false;
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
     * Récupère un employé par son ID.
     * @param id L'ID de l'employé
     * @return Employe l'employé trouvé ou null si non trouvé
     */
    public static Employe getEmployeById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM employe WHERE id_emp = ?";
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return new Employe(
                    rs.getInt("id_emp"),
                    rs.getString("matricule"),
                    rs.getString("nomEmp"),
                    rs.getString("prenomEmp"),
                    rs.getString("fonctionEmp"),
                    rs.getString("serviceEmp"),
                    rs.getDate("dateEmbauche"),
                    rs.getString("sexeEmp"),
                    rs.getDouble("salaireBase"),
                    null // Pas d'entreprise
                );
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'employé : " + e.getMessage());
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
     * Récupère l'ID de l'employé récemment inséré.
     * @return int ID de l'employé
     */
    public static int getLastInsertedEmployeId() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String query = "SELECT LAST_INSERT_ID() as last_id";
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("last_id");
            }
            return -1;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du dernier ID : " + e.getMessage());
            return -1;
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
    
    public static boolean addEntrepriseToEmploye(int employeId, int entrepriseId) {
        String query = "UPDATE employe SET id_entreprise = ? WHERE id_emp = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, entrepriseId);
            stmt.setInt(2, employeId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'entreprise à l'employé : " + e.getMessage());
            return false;
        }
    }

    public static boolean updateEmploye(Employe employe) {
        String sql = "UPDATE employe SET nomEmp = ?, prenomEmp = ?, fonctionEmp = ?, serviceEmp = ?, dateEmbauche = ?, id_entreprise = ? WHERE id_emp = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, employe.getNom());
            pstmt.setString(2, employe.getPrenom());
            pstmt.setString(3, employe.getFonction());
            pstmt.setString(4, employe.getService());
            pstmt.setDate(5, new java.sql.Date(employe.getDateEmbauche().getTime()));
            
            if (employe.getEntreprise() != null) {
                pstmt.setInt(6, employe.getEntreprise().getId());
            } else {
                pstmt.setNull(6, java.sql.Types.INTEGER);
            }
            
            pstmt.setInt(7, employe.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère tous les employés de la base de données.
     * @return List<Employe> la liste des employés
     */
    public static List<Employe> getAllEmployes() {
        List<Employe> employes = new ArrayList<>();
        String query = "SELECT e.*, ent.id_entreprise, ent.nom as nom_entreprise, ent.adresse, " +
                      "ent.chiffre_affaire, ent.date_creation " +
                      "FROM employe e " +
                      "LEFT JOIN entreprise ent ON e.id_entreprise = ent.id_entreprise";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Employe employe = new Employe(
                    rs.getInt("id_emp"),
                    rs.getString("matricule"),
                    rs.getString("nomEmp"),
                    rs.getString("prenomEmp"),
                    rs.getString("fonctionEmp"),
                    rs.getString("serviceEmp"),
                    rs.getDate("dateEmbauche")
                );
                
                // Si l'employé est associé à une entreprise, créer et définir l'objet Entreprise
                if (rs.getObject("id_entreprise") != null) {
                    Entreprise entreprise = new Entreprise(
                        rs.getInt("id_entreprise"),
                        rs.getString("nom_entreprise"),
                        rs.getString("adresse"),
                        rs.getDouble("chiffre_affaire"),
                        rs.getDate("date_creation")
                    );
                    employe.setEntreprise(entreprise);
                }
                
                employes.add(employe);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des employés : " + e.getMessage());
        }
        
        return employes;
    }

    /**
     * Génère un nouveau matricule unique pour un employé.
     * @return String le matricule généré
     */
    public static String generateMatricule() {
        String query = "SELECT COUNT(*) as count FROM employe";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                int count = rs.getInt("count") + 1;
                return String.format("EMP%05d", count);
            }
            return "EMP00001";
            
        } catch (SQLException e) {
            System.err.println("Erreur lors de la génération du matricule : " + e.getMessage());
            return "EMP" + System.currentTimeMillis();
        }
    }

    /**
     * Supprime un employé de la base de données.
     * @param id L'ID de l'employé à supprimer
     * @return boolean true si la suppression a réussi, false sinon
     */
    public static boolean deleteEmploye(int id) {
        String sql = "DELETE FROM employe WHERE id_emp = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
