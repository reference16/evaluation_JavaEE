package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.UserDAO;
import models.User;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Authentification de l'utilisateur avec son email et son mot de passe
        User user = UserDAO.authenticateUser(email, password);

        if (user != null) {
            // Si l'utilisateur est authentifié, créer une session
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userRole", user.getRole());

            // Vérifier le rôle de l'utilisateur et rediriger vers la page appropriée
            if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect("dashboardAdmin.jsp"); // Rediriger vers le tableau de bord Admin
            } else if ("EMPLOYE".equals(user.getRole())) {
                response.sendRedirect("dashboardEmploye.jsp"); // Rediriger vers le tableau de bord Employé
            } else {
                request.setAttribute("errorMessage", "Rôle non reconnu.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            // Si l'utilisateur n'est pas trouvé ou que le mot de passe est incorrect
            request.setAttribute("errorMessage", "Email ou mot de passe incorrect.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
