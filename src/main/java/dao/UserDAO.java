package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import models.Employe;
import utils.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe DAO pour la gestion des utilisateurs.
 */
public class UserDAO {
    
    /**
     * Authentifie un utilisateur avec son email et mot de passe.
     * @param email L'email de l'utilisateur
     * @param password Le mot de passe en clair
     * @return User l'utilisateur authentifié ou null si échec
     */
    public static User authenticateUser(String email, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM user WHERE email = ?";
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                
                if (BCrypt.checkpw(password, hashedPassword)) {
                    Employe employe = EmployeDAO.getEmployeById(rs.getInt("id_emp"));
                    
                    return new User(
                        rs.getInt("id_user"),
                        rs.getString("email"),
                        hashedPassword,
                        rs.getString("role"),
                        employe
                    );
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification: " + e.getMessage());
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
     * Enregistre un nouvel utilisateur dans la base de données.
     * @param user L'utilisateur à enregistrer
     * @return boolean true si l'enregistrement a réussi, false sinon
     */
    public static boolean registerUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String query = "INSERT INTO user (email, password, role, id_emp) VALUES (?, ?, ?, ?)";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);

            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            stmt.setString(1, user.getEmail());
            stmt.setString(2, hashedPassword);
            stmt.setString(3, user.getRole());
            
            if (user.getEmploye() != null) {
                stmt.setInt(4, user.getEmploye().getId());
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement: " + e.getMessage());
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
     * Récupère un utilisateur par son ID.
     * @param userId L'ID de l'utilisateur
     * @return User l'utilisateur trouvé ou null si non trouvé
     */
    public static User getUserById(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        String query = "SELECT * FROM user WHERE id_user = ?";
        
        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Employe employe = EmployeDAO.getEmployeById(rs.getInt("id_emp"));
                
                return new User(
                    rs.getInt("id_user"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    employe
                );
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'utilisateur : " + e.getMessage());
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
     * Met à jour un utilisateur.
     * @param user L'utilisateur à mettre à jour
     * @return boolean true si la mise à jour a réussi, false sinon
     */
    public static boolean updateUser(User user) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String query = "UPDATE user SET email = ?, role = ?, id_emp = ? WHERE id_user = ?";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getRole());

            if (user.getEmploye() != null) {
                stmt.setInt(3, user.getEmploye().getId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }

            stmt.setInt(4, user.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour: " + e.getMessage());
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
     * Supprime un utilisateur de la base de données.
     * @param userId L'ID de l'utilisateur à supprimer
     * @return boolean true si la suppression a réussi, false sinon
     */
    public static boolean deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        String query = "DELETE FROM user WHERE id_user = ?";

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(query);

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'utilisateur: " + e.getMessage());
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
