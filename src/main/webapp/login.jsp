<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="navbar.jsp" %>
<div class="flex items-center justify-center h-screen bg-black">
    <div class="bg-gray-900 p-6 rounded-lg shadow-lg text-white">
        <h2 class="text-2xl font-bold mb-4 text-red-500">Connexion</h2>
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p class="text-red-500"><%= request.getAttribute("errorMessage") %></p>
        <% } %>
        <form action="LoginServlet" method="post">
            <label class="block">Email :</label>
            <input type="email" name="email" class="w-full p-2 rounded bg-gray-800 text-white" required>
            
            <label class="block mt-2">Mot de passe :</label>
            <input type="password" name="password" class="w-full p-2 rounded bg-gray-800 text-white" required>

            <button type="submit" class="mt-4 bg-red-500 hover:bg-red-700 text-white px-4 py-2 rounded w-full">Se connecter</button>
        </form>
    </div>
</div>


</body>
</html>