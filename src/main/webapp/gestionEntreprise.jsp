<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="navbar.jsp" %>
<%@ page import="models.Entreprise" %>
<%@ page import="models.Employe" %>
<%@ page import="dao.EntrepriseDAO" %>
<%@ page import="dao.EmployeDAO" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Entreprises</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="container mx-auto p-4">
        <h2 class="text-2xl font-bold text-red-500 mb-4">Gestion des Entreprises</h2>

        <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("ADMIN")) { %>
            <a href="ajoutEntreprise.jsp" class="bg-blue-500 hover:bg-blue-700 text-white py-2 px-4 rounded mb-4 inline-block">Ajouter une entreprise</a>

            <table class="min-w-full bg-gray-700 rounded-lg shadow-lg">
                <thead>
                    <tr>
                        <th class="py-2 px-4">Nom</th>
                        <th class="py-2 px-4">Adresse</th>
                        <th class="py-2 px-4">Chiffre d'affaire</th>
                        <th class="py-2 px-4">Date de création</th>
                        <th class="py-2 px-4">Employés</th>
                        <th class="py-2 px-4">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Entreprise> entreprises = EntrepriseDAO.getAllEntreprises();
                        if (entreprises != null && !entreprises.isEmpty()) {
                            for (Entreprise entreprise : entreprises) {
                                List<Employe> employesEntreprise = EmployeDAO.getEmployesByEntreprise(entreprise.getId());
                    %>
                    <tr class="hover:bg-gray-600">
                        <td class="py-2 px-4"><%= entreprise.getNom() %></td>
                        <td class="py-2 px-4"><%= entreprise.getAdresse() %></td>
                        <td class="py-2 px-4"><%= String.format("%,.2f €", entreprise.getChiffreAffaire()) %></td>
                        <td class="py-2 px-4"><%= entreprise.getDateCreation() %></td>
                        <td class="py-2 px-4">
                            <% if (employesEntreprise != null && !employesEntreprise.isEmpty()) { %>
                                <div class="max-h-32 overflow-y-auto">
                                    <% for (Employe emp : employesEntreprise) { %>
                                        <div class="text-sm py-1">
                                            <%= emp.getNom() %> <%= emp.getPrenom() %> - <%= emp.getFonction() %>
                                        </div>
                                    <% } %>
                                </div>
                            <% } else { %>
                                <span class="text-gray-400">Aucun employé</span>
                            <% } %>
                        </td>
                        <td class="py-2 px-4">
                            <div class="flex space-x-2">
                                <form action="GestionEntrepriseServlet" method="post" class="inline">
                                    <input type="hidden" name="id" value="<%= entreprise.getId() %>">
                                    <input type="hidden" name="action" value="delete">
                                    <button type="submit" class="bg-red-500 hover:bg-red-700 text-white py-1 px-3 rounded">
                                        Supprimer
                                    </button>
                                </form>
                                <a href="GestionEntrepriseServlet?id=<%= entreprise.getId() %>&action=update" 
                                   class="bg-yellow-500 hover:bg-yellow-700 text-white py-1 px-3 rounded">
                                    Modifier
                                </a>
                            </div>
                        </td>
                    </tr>
                    <% 
                            }
                        }
                    %>
                </tbody>
            </table>
        <% } else { %>
            <div class="bg-red-500 text-white p-4 rounded">
                Accès refusé. Vous devez être administrateur pour accéder à cette page.
            </div>
        <% } %>
    </div>
</body>
</html>
