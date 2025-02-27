package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // URL de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_entreprise_db";
    private static final String USER = "root"; // Remplace par ton utilisateur MySQL
    private static final String PASSWORD = ""; // Remplace par ton mot de passe MySQL

    // Méthode pour obtenir une nouvelle connexion
    public static Connection getConnection() {
        try {
            // Chargement du driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Établissement d'une nouvelle connexion
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            
            // Configuration importante pour MySQL
            connection.setAutoCommit(true);
            
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Driver JDBC non trouvé !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Erreur de connexion à la base de données !");
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour fermer une connexion
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion !");
                e.printStackTrace();
            }
        }
    }
}
