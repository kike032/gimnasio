<%-- 
    Document   : Rolfrm
    Created on : 4 may 2026, 16:06:08
    Author     : kikej
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
   <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js" integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI" crossorigin="anonymous"></script>
    </head>

   <body class="container mt-4">
        <h2>Gestión de Roles</h2>


        <form id="formRol">
            
            <input type="text" id="nombre" class="form-control mb-2" placeholder="nombre de rol" required>

            

            <textarea id="descripcion" class="form-control mb-2" placeholder="Descripción" rows="3" required></textarea>


            <button type="submit" class="btn btn-primary">Guardar</button>
        </form>

        <hr>

        <div id="listaRol"></div>
        <div id="paginacion"></div>

        <script src="${pageContext.request.contextPath}/js/rol.js"></script>
    </body>
</html>
