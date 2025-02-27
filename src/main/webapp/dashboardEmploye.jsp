<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%@ page import="models.Entreprise" %>
<%@ page import="models.Employe" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Employé</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="container mx-auto px-4 py-8">
        <% 
            // Récupérer l'employé et les messages
            Employe employe = (Employe) request.getAttribute("employe");
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

        <% if (employe != null) { %>
            <div class="bg-gray-700 rounded-lg shadow-lg p-6 mb-8">
                <h2 class="text-2xl font-bold text-red-500 mb-4">Mon Profil</h2>
                <div class="grid grid-cols-2 gap-4">
                    <div>
                        <p class="text-gray-400">Matricule:</p>
                        <p class="font-semibold"><%= employe.getMatricule() %></p>
                    </div>
                    <div>
                        <p class="text-gray-400">Nom:</p>
                        <p class="font-semibold"><%= employe.getNom() %></p>
                    </div>
                    <div>
                        <p class="text-gray-400">Prénom:</p>
                        <p class="font-semibold"><%= employe.getPrenom() %></p>
                    </div>
                    <div>
                        <p class="text-gray-400">Fonction:</p>
                        <p class="font-semibold"><%= employe.getFonction() %></p>
                    </div>
                    <div>
                        <p class="text-gray-400">Service:</p>
                        <p class="font-semibold"><%= employe.getService() %></p>
                    </div>
                    <div>
                        <p class="text-gray-400">Date d'embauche:</p>
                        <p class="font-semibold">
                            <%= new SimpleDateFormat("dd/MM/yyyy").format(employe.getDateEmbauche()) %>
                        </p>
                    </div>
                </div>
                <div class="mt-4">
                    <a href="DashboardEmployeServlet?action=edit" 
                       class="bg-red-500 hover:bg-red-700 text-white px-4 py-2 rounded inline-block">
                        Modifier mon profil
                    </a>
                </div>
            </div>

            <% if (employe.getEntreprise() == null) { %>
                <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                    <% 
                        List<Entreprise> entreprises = (List<Entreprise>) request.getAttribute("entreprises");
                        if (entreprises != null && !entreprises.isEmpty()) {
                            for (Entreprise entreprise : entreprises) {
                    %>
                    <div class="bg-gray-700 p-6 rounded-lg shadow-lg">
                        <h3 class="text-xl font-bold text-red-500 mb-4"><%= entreprise.getNom() %></h3>
                        <p class="text-sm mb-2">Adresse: <%= entreprise.getAdresse() %></p>
                        <p class="text-sm mb-2">Chiffre d'affaire: <%= String.format("%,.2f €", entreprise.getChiffreAffaire()) %></p>
                        <p class="text-sm mb-4">
                            Date de création: 
                            <%= new SimpleDateFormat("dd/MM/yyyy").format(entreprise.getDateCreation()) %>
                        </p>
                        <form action="AddEntrepriseToEmployeServlet" method="post" class="mt-4">
                            <input type="hidden" name="entrepriseId" value="<%= entreprise.getId() %>">
                            <button type="submit" 
                                    class="bg-red-500 hover:bg-red-700 text-white px-4 py-2 rounded w-full">
                                Rejoindre cette entreprise
                            </button>
                        </form>
                    </div>
                    <% 
                            }
                        } else { 
                    %>
                    <div class="col-span-3 text-center text-red-500 p-4">
                        Aucune entreprise disponible pour le moment.
                    </div>
                    <% } %>
                </div>
            <% } else { %>
                <div class="bg-gray-700 rounded-lg shadow-lg p-6">
                    <h2 class="text-2xl font-bold text-red-500 mb-4">Mon Entreprise</h2>
                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <p class="text-gray-400">Nom:</p>
                            <p class="font-semibold"><%= employe.getEntreprise().getNom() %></p>
                        </div>
                        <div>
                            <p class="text-gray-400">Adresse:</p>
                            <p class="font-semibold"><%= employe.getEntreprise().getAdresse() %></p>
                        </div>
                        <div>
                            <p class="text-gray-400">Chiffre d'affaire:</p>
                            <p class="font-semibold">
                                <%= String.format("%,.2f €", employe.getEntreprise().getChiffreAffaire()) %>
                            </p>
                        </div>
                        <div>
                            <p class="text-gray-400">Date de création:</p>
                            <p class="font-semibold">
                                <%= new SimpleDateFormat("dd/MM/yyyy").format(employe.getEntreprise().getDateCreation()) %>
                            </p>
                        </div>
                    </div>
                </div>
            <% } %>
        <% } else { %>
            <div class="bg-red-500 text-white p-4 rounded">
                Erreur : Impossible de charger les informations de l'employé.
                <a href="login.jsp" class="underline">Se reconnecter</a>
            </div>
        <% } %>
    </div>
</body>
</html>
