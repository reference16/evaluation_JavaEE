package servlets;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.EntrepriseDAO;
import models.User;
import models.Entreprise;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");

        // Si l'utilisateur est admin, afficher les options pour gérer les entreprises et employés
        if ("ADMIN".equals(userRole)) {
            // Rediriger vers la page de gestion des entreprises et des employés
            response.sendRedirect("dashboardAdmin.jsp");
        }
        // Si l'utilisateur est un employé, afficher les entreprises sous forme de cartes
        else if ("EMPLOYE".equals(userRole)) {
            // Récupérer toutes les entreprises pour les afficher sous forme de cartes
            List<Entreprise> entreprises = EntrepriseDAO.getAllEntreprises();
            request.setAttribute("entreprises", entreprises);
            request.getRequestDispatcher("dashboardEmploye.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp"); // Si le rôle est inconnu, rediriger vers la page de connexion
        }
    }
}
