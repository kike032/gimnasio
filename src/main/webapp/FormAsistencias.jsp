<%-- 
    Document   : FormAsistencias
    Created on : 7 may 2026, 22:31:00
    Author     : kikej
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión de Asistencias</title>

        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
              rel="stylesheet">

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
    </head>

    <body class="container mt-4">

        <h2>Gestión de Asistencias</h2>

        <h3>Lista de Asistencias</h3>

        <div id="listaAsistencias"></div>

        <script src="${pageContext.request.contextPath}/js/asistencia.js?v=1"></script>
    </body>
</html>