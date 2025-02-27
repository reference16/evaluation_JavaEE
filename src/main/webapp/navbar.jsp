<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession sessionObj = request.getSession(false);
    String userEmail = (sessionObj != null) ? (String) sessionObj.getAttribute("userEmail") : null;
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion Entreprise</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
    <nav class="bg-black text-white py-4 px-6 flex justify-between items-center">
        <h1 class="text-xl font-bold text-red-500">Gestion Entreprise</h1>
        <div>
            <% if (userEmail != null) { %>
                <span class="mr-4">Bienvenue, <%= userEmail %></span>
                <a href="LogoutServlet" class="bg-red-500 hover:bg-red-700 text-white px-4 py-2 rounded">Déconnexion</a>
            <% } else { %>
                <a href="login.jsp" class="bg-orange-500 hover:bg-orange-700 text-white px-4 py-2 rounded">Connexion</a>
                <a href="register.jsp" class="bg-red-500 hover:bg-red-700 text-white px-4 py-2 rounded ml-2">Inscription</a>
            <% } %>
        </div>
    </nav>
</body>
</html>
