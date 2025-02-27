<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%@ page import="models.Entreprise" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Employé</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="container mx-auto px-4 py-8">
        <div class="max-w-2xl mx-auto bg-gray-900 rounded-lg shadow-lg p-6">
            <h2 class="text-2xl font-bold text-red-500 mb-6">Ajouter un Employé</h2>

            <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                <div class="bg-red-500 text-white p-4 rounded mb-4">
                    <%= errorMessage %>
                </div>
            <% } %>

            <form action="GestionEmployeServlet" method="post" class="space-y-4">
                <input type="hidden" name="action" value="add">
                
                <div>
                    <label for="nom" class="block text-lg mb-2">Nom:</label>
                    <input type="text" name="nom" id="nom" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           required>
                </div>

                <div>
                    <label for="prenom" class="block text-lg mb-2">Prénom:</label>
                    <input type="text" name="prenom" id="prenom" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           required>
                </div>

                <div>
                    <label for="sexe" class="block text-lg mb-2">Sexe:</label>
                    <select name="sexe" id="sexe" 
                            class="w-full p-2 bg-gray-700 text-white rounded" 
                            required>
                        <option value="M">Masculin</option>
                        <option value="F">Féminin</option>
                    </select>
                </div>

                <div>
                    <label for="fonction" class="block text-lg mb-2">Fonction:</label>
                    <input type="text" name="fonction" id="fonction" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           required>
                </div>

                <div>
                    <label for="service" class="block text-lg mb-2">Service:</label>
                    <input type="text" name="service" id="service" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           required>
                </div>

                <div>
                    <label for="salaireBase" class="block text-lg mb-2">Salaire de base:</label>
                    <input type="number" name="salaireBase" id="salaireBase" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           step="0.01" min="0" 
                           required>
                </div>

                <div>
                    <label for="dateEmbauche" class="block text-lg mb-2">Date d'embauche:</label>
                    <input type="date" name="dateEmbauche" id="dateEmbauche" 
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
                        %>
                            <option value="<%= entreprise.getId() %>">
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
                        Ajouter
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
