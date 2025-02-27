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
        <h2 class="text-2xl font-bold text-red-500">Déconnexion en cours...</h2>
        <p class="text-gray-400">Veuillez patienter...</p>
        <script>
            setTimeout(() => {
                window.location.href = "LogoutServlet";
            }, 2000);
        </script>
    </div>
</div>

</body>
</html>