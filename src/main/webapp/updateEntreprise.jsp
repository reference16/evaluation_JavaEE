<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%@ page import="models.Entreprise" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier une Entreprise</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="container mx-auto p-4">
        <h2 class="text-2xl font-bold text-red-500 mb-4">Modifier l'Entreprise</h2>

        <% 
            Entreprise entreprise = (Entreprise) request.getAttribute("entreprise");
            String errorMessage = (String) request.getAttribute("errorMessage");
            String successMessage = (String) request.getAttribute("successMessage");
            
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

        <% if (entreprise != null) { 
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = dateFormat.format(entreprise.getDateCreation());
        %>
            <form action="GestionEntrepriseServlet" method="post" class="space-y-4">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="id" value="<%= entreprise.getId() %>">

                <div>
                    <label for="nom" class="block text-lg mb-2">Nom de l'entreprise:</label>
                    <input type="text" name="nom" id="nom" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           value="<%= entreprise.getNom() %>" required>
                </div>

                <div>
                    <label for="adresse" class="block text-lg mb-2">Adresse:</label>
                    <input type="text" name="adresse" id="adresse" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           value="<%= entreprise.getAdresse() %>" required>
                </div>

                <div>
                    <label for="chiffreAffaire" class="block text-lg mb-2">Chiffre d'affaire:</label>
                    <input type="number" name="chiffreAffaire" id="chiffreAffaire" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           value="<%= entreprise.getChiffreAffaire() %>" 
                           step="0.01" min="0" required>
                </div>

                <div>
                    <label for="dateCreation" class="block text-lg mb-2">Date de création:</label>
                    <input type="date" name="dateCreation" id="dateCreation" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           value="<%= formattedDate %>" required>
                </div>

                <div class="flex justify-end space-x-4">
                    <a href="GestionEntrepriseServlet" 
                       class="bg-gray-500 hover:bg-gray-700 text-white py-2 px-4 rounded">
                        Annuler
                    </a>
                    <button type="submit" 
                            class="bg-red-500 hover:bg-red-700 text-white py-2 px-4 rounded">
                        Mettre à jour
                    </button>
                </div>
            </form>
        <% } else { %>
            <div class="bg-red-500 text-white p-4 rounded">
                Erreur : Entreprise non trouvée.
            </div>
            <div class="mt-4">
                <a href="GestionEntrepriseServlet" 
                   class="bg-gray-500 hover:bg-gray-700 text-white py-2 px-4 rounded">
                    Retour à la liste
                </a>
            </div>
        <% } %>
    </div>
</body>
</html>
