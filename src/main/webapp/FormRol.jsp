<%-- 
    Document   : FormRol
    Descripción: Vista para consultar y agregar roles
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    request.setAttribute("activePage", "roles");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Roles | IronCore</title>

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>

    <!-- Iconos -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css">

    <!-- CSS principal -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
</head>

<body>

    <%@ include file="../menu/FormMenu.jsp" %>

    <main id="mainContent">

        <header class="topbar">
            <div class="topbar-left">
                <div class="page-title">Roles</div>
                <div class="page-sub">Gestión de roles del sistema</div>
            </div>
        </header>

        <section class="content">

            <div class="form-card mb-4">

                <div class="form-card-header">
                    <div>
                        <h3>Nuevo rol</h3>
                        <p>Completa la información para registrar un rol.</p>
                    </div>

                    <div class="form-icon">
                        <i class="ti ti-shield-check"></i>
                    </div>
                </div>

                

                <div id="alertaRol" class="alerta-form" style="display:none;">
                    <i class="ti ti-alert-triangle"></i>
                    <span id="mensajeAlertaRol"></span>
                </div>

                <form id="formRol">

                    <input type="hidden" id="idRol">

                    <div class="form-grid">

                        <div class="form-group-ic">
                            <label>Nombre del rol</label>
                            <div class="input-ic">
                                <i class="ti ti-shield"></i>
                                <input type="text" id="nombreRol" placeholder="Ej: Administrador">
                            </div>
                        </div>

                        <div class="form-group-ic">
                            <label>Estado</label>
                            <div class="input-ic">
                                <i class="ti ti-circle-check"></i>
                                <select id="estado">
                                    <option value="true">Activo</option>
                                    <option value="false">Inactivo</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group-ic full">
                            <label>Descripción</label>
                            <div class="input-ic">
                                <i class="ti ti-file-description"></i>
                                <input type="text" id="descripcion" placeholder="Descripción del rol">
                            </div>
                        </div>

                    </div>

                    <div class="form-actions">
                        <button type="reset" class="btn-ic">
                            <i class="ti ti-eraser"></i>
                            Limpiar
                        </button>

                        <button type="submit" id="btnGuardarRol" class="btn-ic primary">
                            <i class="ti ti-device-floppy"></i>
                            Guardar rol
                        </button>
                    </div>

                </form>

            </div>

            <div class="table-card">
                <div class="table-toolbar">
                    <span class="table-toolbar-title">Lista de roles</span>
                </div>

                <div id="listaRoles" class="lista-tabla">
                    <div class="text-center p-3">
                        Cargando roles...
                    </div>
                </div>
            </div>

        </section>

    </main>

    <script>
        window.APP_CONTEXT = "${pageContext.request.contextPath}";
        console.log("APP_CONTEXT:", window.APP_CONTEXT);
    </script>

    <!-- Se agrega versión para evitar que el navegador use rol.js viejo -->
    <script src="${pageContext.request.contextPath}/js/rol.js?v=<%= System.currentTimeMillis() %>"></script>

</body>
</html>