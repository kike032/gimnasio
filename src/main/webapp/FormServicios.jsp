<%-- 
    Document   : FormServicios
    Created on : 7 may 2026, 22:32:01
    Author     : kikej
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión de Servicios</title>

        <script src="https://code.jquery.com/jquery-3.7.1.js"></script>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" 
              rel="stylesheet">

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
    </head>

    <body class="container mt-4">

        <h2>Gestión de Servicios</h2>

        <h3>Lista de Servicios</h3>

        <div id="listaServicios"></div>

        <script src="${pageContext.request.contextPath}/js/servicio.js?v=1"></script>
    </body>
</html>