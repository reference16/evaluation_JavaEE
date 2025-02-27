<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Inscription</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="flex items-center justify-center h-screen">
        <div class="bg-gray-900 p-6 rounded-lg shadow-lg w-1/3">
            <h2 class="text-2xl font-bold mb-4 text-red-500">Inscription</h2>
            
            <% if (request.getAttribute("errorMessage") != null) { %>
                <p class="text-red-500"><%= request.getAttribute("errorMessage") %></p>
            <% } %>

            <form action="RegisterServlet" method="post">
                <label class="block">Nom :</label>
                <input type="text" name="nom" class="w-full p-2 rounded bg-gray-800 text-white mb-4" required>

                <label class="block">Prénom :</label>
                <input type="text" name="prenom" class="w-full p-2 rounded bg-gray-800 text-white mb-4" required>

                <label class="block">Fonction :</label>
                <input type="text" name="fonction" class="w-full p-2 rounded bg-gray-800 text-white mb-4" required>

                <label class="block">Service :</label>
                <input type="text" name="service" class="w-full p-2 rounded bg-gray-800 text-white mb-4" required>

                <label class="block">Sexe :</label>
                <select name="sexe" class="w-full p-2 rounded bg-gray-800 text-white mb-4">
                    <option value="M">Masculin</option>
                    <option value="F">Féminin</option>
                </select>

                <label class="block">Email :</label>
                <input type="email" name="email" class="w-full p-2 rounded bg-gray-800 text-white mb-4" required>

                <label class="block">Mot de passe :</label>
                <input type="password" name="password" class="w-full p-2 rounded bg-gray-800 text-white mb-4" required>

                <button type="submit" class="bg-red-500 hover:bg-red-700 text-white px-4 py-2 rounded w-full">S'inscrire</button>
            </form>
        </div>
    </div>
</body>
</html>
