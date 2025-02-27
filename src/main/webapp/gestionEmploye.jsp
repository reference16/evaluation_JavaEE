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
    <title>Gestion des Employés</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-800 text-white">
    <div class="container mx-auto px-4 py-8">
        <h2 class="text-3xl font-bold text-red-500 mb-6">Gestion des Employés</h2>

        <% 
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

        <% if (session.getAttribute("userRole") != null && session.getAttribute("userRole").equals("ADMIN")) { %>
            <div class="mb-6">
                <a href="GestionEmployeServlet?action=add" 
                   class="bg-red-500 hover:bg-red-700 text-white px-6 py-3 rounded inline-block transition duration-200">
                    Ajouter un employé
                </a>
            </div>

            <div class="bg-gray-900 rounded-lg shadow-lg overflow-hidden">
                <table class="min-w-full">
                    <thead class="bg-gray-700">
                        <tr>
                            <th class="py-3 px-4 text-left">Matricule</th>
                            <th class="py-3 px-4 text-left">Nom</th>
                            <th class="py-3 px-4 text-left">Prénom</th>
                            <th class="py-3 px-4 text-left">Fonction</th>
                            <th class="py-3 px-4 text-left">Service</th>
                            <th class="py-3 px-4 text-left">Date d'embauche</th>
                            <th class="py-3 px-4 text-left">Entreprise</th>
                            <th class="py-3 px-4 text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody class="divide-y divide-gray-700">
                        <% 
                            List<Employe> employes = (List<Employe>) request.getAttribute("employes");
                            if (employes != null && !employes.isEmpty()) {
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                for (Employe employe : employes) {
                        %>
                        <tr class="hover:bg-gray-700 transition duration-200">
                            <td class="py-3 px-4"><%= employe.getMatricule() %></td>
                            <td class="py-3 px-4"><%= employe.getNom() %></td>
                            <td class="py-3 px-4"><%= employe.getPrenom() %></td>
                            <td class="py-3 px-4"><%= employe.getFonction() %></td>
                            <td class="py-3 px-4"><%= employe.getService() %></td>
                            <td class="py-3 px-4"><%= dateFormat.format(employe.getDateEmbauche()) %></td>
                            <td class="py-3 px-4">
                                <%= employe.getEntreprise() != null ? employe.getEntreprise().getNom() : "Non assigné" %>
                            </td>
                            <td class="py-3 px-4">
                                <div class="flex justify-center space-x-2">
                                    <a href="GestionEmployeServlet?action=update&id=<%= employe.getId() %>" 
                                       class="bg-yellow-500 hover:bg-yellow-700 text-white px-3 py-1 rounded transition duration-200">
                                        Modifier
                                    </a>
                                    <form action="GestionEmployeServlet" method="post" class="inline" 
                                          onsubmit="return confirm('Êtes-vous sûr de vouloir supprimer cet employé ?');">
                                        <input type="hidden" name="action" value="delete">
                                        <input type="hidden" name="id" value="<%= employe.getId() %>">
                                        <button type="submit" 
                                                class="bg-red-500 hover:bg-red-700 text-white px-3 py-1 rounded transition duration-200">
                                            Supprimer
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                        <% 
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="8" class="py-4 px-4 text-center text-gray-400">
                                Aucun employé trouvé
                            </td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        <% } else { %>
            <div class="bg-red-500 text-white p-4 rounded">
                Accès refusé. Vous devez être administrateur pour accéder à cette page.
            </div>
        <% } %>
    </div>
</body>
</html>
