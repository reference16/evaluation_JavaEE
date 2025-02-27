package tests;

import java.sql.Connection;

import utils.DatabaseConnection;

public class TestConnection {
    public static void main(String[] args) {
        // Test de connexion
        Connection conn = DatabaseConnection.getConnection();

        // Fermeture de la connexion après test
        DatabaseConnection.closeConnection(conn);
    }
}
