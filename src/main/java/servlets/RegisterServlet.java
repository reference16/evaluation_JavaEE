package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.UserDAO;
import dao.EmployeDAO;
import models.User;
import models.Employe;
import java.util.Date;

/**
 * Servlet pour l'inscription des utilisateurs
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = "EMPLOYE"; // Par défaut, tout nouvel inscrit est employé

            // Récupération des informations supplémentaires pour l'employé
            String matricule = generateMatricule(); // Génère un matricule unique
            String nom = request.getParameter("nom");
            String prenom = request.getParameter("prenom");
            String fonction = request.getParameter("fonction");
            String service = request.getParameter("service");
            String sexe = request.getParameter("sexe");
            double salaireBase = 0.0; // Salaire par défaut, à définir plus tard

            // 1️⃣ Créer un employé sans entreprise
            Employe newEmploye = new Employe(
                0, // ID sera généré par la base de données
                matricule,
                nom,
                prenom,
                fonction,
                service,
                new Date(), // Date d'embauche = aujourd'hui
                sexe,
                salaireBase,
                null // Pas d'entreprise associée
            );
            
            // Ajouter l'employé à la base de données
            boolean isEmployeCreated = EmployeDAO.addEmploye(newEmploye);

            if (isEmployeCreated) {
                // Récupérer l'ID de l'employé nouvellement créé
                int employeId = EmployeDAO.getLastInsertedEmployeId();
                newEmploye.setId(employeId);

                // 2️⃣ Créer un utilisateur associé à l'employé
                User newUser = new User(0, email, password, role, newEmploye);

                // Enregistrer l'utilisateur dans la base de données
                boolean isUserRegistered = UserDAO.registerUser(newUser);

                if (isUserRegistered) {
                    response.sendRedirect("login.jsp?success=true");
                } else {
                    // En cas d'échec de création de l'utilisateur, on supprime l'employé
                    EmployeDAO.deleteEmploye(employeId);
                    request.setAttribute("errorMessage", "Erreur lors de la création de l'utilisateur.");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Erreur lors de la création de l'employé.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Une erreur inattendue s'est produite : " + e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    private String generateMatricule() {
        // Génère un matricule basé sur la date et un nombre aléatoire
        return "EMP" + System.currentTimeMillis() % 10000;
    }
}
