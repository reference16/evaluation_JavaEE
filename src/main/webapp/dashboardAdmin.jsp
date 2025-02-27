<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Admin</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="container mx-auto px-4 py-8">
        <div class="bg-gray-900 p-8 rounded-lg shadow-lg max-w-2xl mx-auto">
            <h2 class="text-3xl font-bold mb-6 text-red-500">Tableau de bord - Admin</h2>
            
            <div class="space-y-4">
                <a href="GestionEntrepriseServlet" 
                   class="bg-orange-500 hover:bg-orange-700 text-white px-6 py-4 rounded w-full block text-center text-lg font-semibold transition duration-200">
                    Gérer les Entreprises
                </a>
                
                <a href="GestionEmployeServlet" 
                   class="bg-red-500 hover:bg-red-700 text-white px-6 py-4 rounded w-full block text-center text-lg font-semibold transition duration-200">
                    Gérer les Employés
                </a>
                
                <a href="GestionUtilisateurServlet" 
                   class="bg-blue-500 hover:bg-blue-700 text-white px-6 py-4 rounded w-full block text-center text-lg font-semibold transition duration-200">
                    Gérer les Utilisateurs
                </a>
            </div>

            <div class="mt-8 text-center text-gray-400">
                <p>Connecté en tant qu'administrateur</p>
            </div>
        </div>
    </div>
</body>
</html>
