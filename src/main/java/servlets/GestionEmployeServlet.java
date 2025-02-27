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
import dao.EmployeDAO;
import dao.EntrepriseDAO;
import models.Employe;
import models.Entreprise;
import java.util.List;

@WebServlet("/GestionEmployeServlet")
public class GestionEmployeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute("userRole");

        if (!"ADMIN".equals(userRole)) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        
        if ("add".equals(action)) {
            // Pour le formulaire d'ajout, on a besoin de la liste des entreprises
            List<Entreprise> entreprises = EntrepriseDAO.getAllEntreprises();
            request.setAttribute("entreprises", entreprises);
            request.getRequestDispatcher("ajoutEmploye.jsp").forward(request, response);
            return;
        } else if ("update".equals(action)) {
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Employe employe = EmployeDAO.getEmployeById(id);
                if (employe != null) {
                    List<Entreprise> entreprises = EntrepriseDAO.getAllEntreprises();
                    request.setAttribute("entreprises", entreprises);
                    request.setAttribute("employe", employe);
                    request.getRequestDispatcher("updateEmploye.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "ID d'employé invalide");
            }
        }

        // Par défaut, afficher la liste des employés
        List<Employe> employes = EmployeDAO.getAllEmployes();
        request.setAttribute("employes", employes);
        request.getRequestDispatcher("gestionEmploye.jsp").forward(request, response);
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
                String matricule = EmployeDAO.generateMatricule();
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String fonction = request.getParameter("fonction");
                String service = request.getParameter("service");
                Date dateEmbauche = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateEmbauche"));
                
                String entrepriseIdStr = request.getParameter("entrepriseId");
                Entreprise entreprise = null;
                if (entrepriseIdStr != null && !entrepriseIdStr.isEmpty()) {
                    int entrepriseId = Integer.parseInt(entrepriseIdStr);
                    entreprise = EntrepriseDAO.getEntrepriseById(entrepriseId);
                }

                Employe employe = new Employe(0, matricule, nom, prenom, fonction, service, dateEmbauche);
                employe.setEntreprise(entreprise);

                if (EmployeDAO.addEmploye(employe)) {
                    successMessage = "Employé ajouté avec succès";
                } else {
                    errorMessage = "Erreur lors de l'ajout de l'employé";
                }
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String matricule = request.getParameter("matricule");
                String nom = request.getParameter("nom");
                String prenom = request.getParameter("prenom");
                String fonction = request.getParameter("fonction");
                String service = request.getParameter("service");
                Date dateEmbauche = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateEmbauche"));
                
                String entrepriseIdStr = request.getParameter("entrepriseId");
                Entreprise entreprise = null;
                if (entrepriseIdStr != null && !entrepriseIdStr.isEmpty()) {
                    int entrepriseId = Integer.parseInt(entrepriseIdStr);
                    entreprise = EntrepriseDAO.getEntrepriseById(entrepriseId);
                }

                Employe employe = new Employe(id, matricule, nom, prenom, fonction, service, dateEmbauche);
                employe.setEntreprise(entreprise);

                if (EmployeDAO.updateEmploye(employe)) {
                    successMessage = "Employé mis à jour avec succès";
                } else {
                    errorMessage = "Erreur lors de la mise à jour de l'employé";
                }
            } else if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                if (EmployeDAO.deleteEmploye(id)) {
                    successMessage = "Employé supprimé avec succès";
                } else {
                    errorMessage = "Erreur lors de la suppression de l'employé";
                }
            }
        } catch (NumberFormatException e) {
            errorMessage = "Format de nombre invalide";
        } catch (ParseException e) {
            errorMessage = "Format de date invalide";
        } catch (Exception e) {
            errorMessage = "Une erreur inattendue s'est produite : " + e.getMessage();
        }

        if (errorMessage != null) {
            request.setAttribute("errorMessage", errorMessage);
        }
        if (successMessage != null) {
            request.setAttribute("successMessage", successMessage);
        }

        doGet(request, response);
    }
}
