package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.User;
import models.Employe;
import utils.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
    
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
}
