package tests;

import org.mindrot.jbcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        // Le mot de passe en clair
        String plainPassword = "passeradmin";

        // Hachage du mot de passe avec BCrypt
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        
        // Afficher le mot de passe haché
        System.out.println("Mot de passe haché : " + hashedPassword);
    }
}

