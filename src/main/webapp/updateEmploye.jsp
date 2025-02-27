<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%@ page import="models.Employe" %>
<%@ page import="models.Entreprise" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier un Employé</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="container mx-auto px-4 py-8">
        <div class="max-w-2xl mx-auto bg-gray-900 rounded-lg shadow-lg p-6">
            <h2 class="text-2xl font-bold text-red-500 mb-6">Modifier un Employé</h2>

            <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                String successMessage = (String) request.getAttribute("successMessage");
                Employe employe = (Employe) request.getAttribute("employe");
                
                if (errorMessage != null) {
            %>
                <div class="bg-red-500 text-white p-4 rounded mb-4">
                    <%= errorMessage %>
                </div>
            <% } %>

            <% if (successMessage != null) { %>
                <div class="bg-green-500 text-white p-4 rounded mb-4">
                    <%= successMessage %>
                </div>
            <% } %>

            <% if (employe != null) { 
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateEmbauche = dateFormat.format(employe.getDateEmbauche());
            %>
                <form action="GestionEmployeServlet" method="post" class="space-y-4">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="id" value="<%= employe.getId() %>">
                    
                    <div>
                        <label class="block text-lg mb-2">Matricule:</label>
                        <input type="text" value="<%= employe.getMatricule() %>" 
                               class="w-full p-2 bg-gray-700 text-white rounded" 
                               disabled>
                        <input type="hidden" name="matricule" value="<%= employe.getMatricule() %>">
                    </div>

                    <div>
                        <label for="nom" class="block text-lg mb-2">Nom:</label>
                        <input type="text" name="nom" id="nom" 
                               value="<%= employe.getNom() %>"
                               class="w-full p-2 bg-gray-700 text-white rounded" 
                               required>
                    </div>

                    <div>
                        <label for="prenom" class="block text-lg mb-2">Prénom:</label>
                        <input type="text" name="prenom" id="prenom" 
                               value="<%= employe.getPrenom() %>"
                               class="w-full p-2 bg-gray-700 text-white rounded" 
                               required>
                    </div>

                    <div>
                        <label for="sexe" class="block text-lg mb-2">Sexe:</label>
                        <select name="sexe" id="sexe" 
                                class="w-full p-2 bg-gray-700 text-white rounded" 
                                required>
                            <option value="M" <%= "M".equals(employe.getSexe()) ? "selected" : "" %>>Masculin</option>
                            <option value="F" <%= "F".equals(employe.getSexe()) ? "selected" : "" %>>Féminin</option>
                        </select>
                    </div>

                    <div>
                        <label for="fonction" class="block text-lg mb-2">Fonction:</label>
                        <input type="text" name="fonction" id="fonction" 
                               value="<%= employe.getFonction() %>"
                               class="w-full p-2 bg-gray-700 text-white rounded" 
                               required>
                    </div>

                    <div>
                        <label for="service" class="block text-lg mb-2">Service:</label>
                        <input type="text" name="service" id="service" 
                               value="<%= employe.getService() %>"
                               class="w-full p-2 bg-gray-700 text-white rounded" 
                               required>
                    </div>

                    <div>
                        <label for="salaireBase" class="block text-lg mb-2">Salaire de base:</label>
                        <input type="number" name="salaireBase" id="salaireBase" 
                               value="<%= employe.getSalaireBase() %>"
                               class="w-full p-2 bg-gray-700 text-white rounded" 
                               step="0.01" min="0" 
                               required>
                    </div>

                    <div>
                        <label for="dateEmbauche" class="block text-lg mb-2">Date d'embauche:</label>
                        <input type="date" name="dateEmbauche" id="dateEmbauche" 
                               value="<%= dateEmbauche %>"
                               class="w-full p-2 bg-gray-700 text-white rounded" 
                               required>
                    </div>

                    <div>
                        <label for="entrepriseId" class="block text-lg mb-2">Entreprise:</label>
                        <select name="entrepriseId" id="entrepriseId" 
                                class="w-full p-2 bg-gray-700 text-white rounded">
                            <option value="">-- Sélectionner une entreprise --</option>
                            <% 
                                List<Entreprise> entreprises = (List<Entreprise>) request.getAttribute("entreprises");
                                if (entreprises != null) {
                                    for (Entreprise entreprise : entreprises) {
                                        boolean isSelected = employe.getEntreprise() != null && 
                                                           employe.getEntreprise().getId() == entreprise.getId();
                            %>
                                <option value="<%= entreprise.getId() %>" <%= isSelected ? "selected" : "" %>>
                                    <%= entreprise.getNom() %>
                                </option>
                            <% 
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="flex justify-end space-x-4 mt-6">
                        <a href="GestionEmployeServlet" 
                           class="bg-gray-500 hover:bg-gray-700 text-white px-6 py-2 rounded transition duration-200">
                            Annuler
                        </a>
                        <button type="submit" 
                                class="bg-red-500 hover:bg-red-700 text-white px-6 py-2 rounded transition duration-200">
                            Mettre à jour
                        </button>
                    </div>
                </form>
            <% } else { %>
                <div class="bg-red-500 text-white p-4 rounded">
                    Erreur : Employé non trouvé.
                </div>
                <div class="mt-4">
                    <a href="GestionEmployeServlet" 
                       class="bg-gray-500 hover:bg-gray-700 text-white px-6 py-2 rounded transition duration-200">
                        Retour à la liste
                    </a>
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
