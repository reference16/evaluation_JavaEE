<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter une Entreprise</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="container mx-auto px-4 py-8">
        <div class="max-w-2xl mx-auto bg-gray-900 rounded-lg shadow-lg p-6">
            <h2 class="text-2xl font-bold text-red-500 mb-6">Ajouter une Entreprise</h2>

            <% 
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                <div class="bg-red-500 text-white p-4 rounded mb-4">
                    <%= errorMessage %>
                </div>
            <% } %>

            <form action="GestionEntrepriseServlet" method="post" class="space-y-4">
                <input type="hidden" name="action" value="add">
                
                <div>
                    <label for="nom" class="block text-lg mb-2">Nom de l'entreprise:</label>
                    <input type="text" name="nom" id="nom" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           required>
                </div>

                <div>
                    <label for="adresse" class="block text-lg mb-2">Adresse:</label>
                    <input type="text" name="adresse" id="adresse" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           required>
                </div>

                <div>
                    <label for="chiffreAffaire" class="block text-lg mb-2">Chiffre d'affaire (€):</label>
                    <input type="number" name="chiffreAffaire" id="chiffreAffaire" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           step="0.01" min="0" 
                           required>
                </div>

                <div>
                    <label for="dateCreation" class="block text-lg mb-2">Date de création:</label>
                    <input type="date" name="dateCreation" id="dateCreation" 
                           class="w-full p-2 bg-gray-700 text-white rounded" 
                           required>
                </div>

                <div class="flex justify-end space-x-4 mt-6">
                    <a href="GestionEntrepriseServlet" 
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
