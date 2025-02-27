package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.EmployeDAO;
import models.User;
import models.Employe;

@WebServlet("/AddEntrepriseToEmployeServlet")
public class AddEntrepriseToEmployeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Récupérer l'utilisateur connecté
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null || !"EMPLOYE".equals(currentUser.getRole())) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int entrepriseId = Integer.parseInt(request.getParameter("entrepriseId"));
            Employe employe = currentUser.getEmploye();
            
            if (employe != null) {
                // Ajouter l'entreprise à l'employé
                boolean isUpdated = EmployeDAO.addEntrepriseToEmploye(employe.getId(), entrepriseId);

                if (isUpdated) {
                    // Mettre à jour la session avec les nouvelles informations
                    employe = EmployeDAO.getEmployeById(employe.getId());
                    currentUser.setEmploye(employe);
                    session.setAttribute("user", currentUser);
                    
                    response.sendRedirect("DashboardEmployeServlet");
                } else {
                    request.setAttribute("errorMessage", "Erreur lors de l'ajout de l'entreprise.");
                    request.getRequestDispatcher("DashboardEmployeServlet").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Employé non trouvé.");
                request.getRequestDispatcher("DashboardEmployeServlet").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "ID d'entreprise invalide.");
            request.getRequestDispatcher("DashboardEmployeServlet").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Une erreur inattendue s'est produite : " + e.getMessage());
            request.getRequestDispatcher("DashboardEmployeServlet").forward(request, response);
        }
    }
}
