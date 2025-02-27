package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.EntrepriseDAO;
import models.Entreprise;
import java.util.List;

@WebServlet("/GestionEntrepriseServlet")
public class GestionEntrepriseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Vérifier que l'utilisateur est un admin
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            // Afficher le formulaire d'ajout
            request.getRequestDispatcher("ajoutEntreprise.jsp").forward(request, response);
            return;
        } else if ("update".equals(action)) {
            // Récupérer l'entreprise à modifier
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Entreprise entreprise = EntrepriseDAO.getEntrepriseById(id);
                if (entreprise != null) {
                    request.setAttribute("entreprise", entreprise);
                    request.getRequestDispatcher("updateEntreprise.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID d'entreprise invalide");
            }
        }

        // Par défaut, afficher la liste des entreprises
        List<Entreprise> entreprises = EntrepriseDAO.getAllEntreprises();
        request.setAttribute("entreprises", entreprises);
        request.getRequestDispatcher("gestionEntreprise.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (!"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String errorMessage = null;
        String successMessage = null;

        try {
            if ("add".equals(action)) {
                // Récupérer et valider les données du formulaire
                String nom = request.getParameter("nom");
                String adresse = request.getParameter("adresse");
                double chiffreAffaire = Double.parseDouble(request.getParameter("chiffreAffaire"));
                Date dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateCreation"));

                // Créer et ajouter l'entreprise
                Entreprise entreprise = new Entreprise(0, nom, adresse, chiffreAffaire, dateCreation);
                if (EntrepriseDAO.addEntreprise(entreprise)) {
                    successMessage = "Entreprise ajoutée avec succès";
                } else {
                    errorMessage = "Erreur lors de l'ajout de l'entreprise";
                }
            } else if ("update".equals(action)) {
                // Mise à jour d'une entreprise existante
                int id = Integer.parseInt(request.getParameter("id"));
                String nom = request.getParameter("nom");
                String adresse = request.getParameter("adresse");
                double chiffreAffaire = Double.parseDouble(request.getParameter("chiffreAffaire"));
                Date dateCreation = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateCreation"));

                Entreprise entreprise = new Entreprise(id, nom, adresse, chiffreAffaire, dateCreation);
                if (EntrepriseDAO.updateEntreprise(entreprise)) {
                    successMessage = "Entreprise mise à jour avec succès";
                } else {
                    errorMessage = "Erreur lors de la mise à jour de l'entreprise";
                }
            } else if ("delete".equals(action)) {
                // Suppression d'une entreprise
                int id = Integer.parseInt(request.getParameter("id"));
                if (EntrepriseDAO.deleteEntreprise(id)) {
                    successMessage = "Entreprise supprimée avec succès";
                } else {
                    errorMessage = "Erreur lors de la suppression de l'entreprise";
                }
            }
        } catch (NumberFormatException e) {
            errorMessage = "Format de nombre invalide";
        } catch (ParseException e) {
            errorMessage = "Format de date invalide";
        } catch (Exception e) {
            errorMessage = "Une erreur inattendue s'est produite : " + e.getMessage();
        }

        // Définir les messages dans la requête
        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }

        // Rediriger vers la liste des entreprises
        doGet(request, response);
    }
}
