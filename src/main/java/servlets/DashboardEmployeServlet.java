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
import dao.EmployeDAO;
import models.Entreprise;
import models.Employe;
import models.User;

@WebServlet("/DashboardEmployeServlet")
public class DashboardEmployeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || !"EMPLOYE".equals(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Récupérer l'employé connecté
        Employe employe = currentUser.getEmploye();
        if (employe != null) {
            request.setAttribute("employe", employe);
            
            // Si l'employé n'a pas d'entreprise, récupérer la liste des entreprises disponibles
            if (employe.getEntreprise() == null) {
                List<Entreprise> entreprises = EntrepriseDAO.getAllEntreprises();
                request.setAttribute("entreprises", entreprises);
                request.getRequestDispatcher("dashboardEmploye.jsp").forward(request, response);
            } else {
                // Si l'employé a déjà une entreprise, afficher ses informations
                request.getRequestDispatcher("updateEmploye.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null || !"EMPLOYE".equals(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        
        if ("update".equals(action)) {
            // Mise à jour des informations de l'employé
            Employe employe = currentUser.getEmploye();
            if (employe != null) {
                employe.setNom(request.getParameter("nom"));
                employe.setPrenom(request.getParameter("prenom"));
                employe.setFonction(request.getParameter("fonction"));
                employe.setService(request.getParameter("service"));
                
                if (EmployeDAO.updateEmploye(employe)) {
                    request.setAttribute("successMessage", "Informations mises à jour avec succès");
                } else {
                    request.setAttribute("errorMessage", "Erreur lors de la mise à jour des informations");
                }
            }
            doGet(request, response);
        }
    }
}
