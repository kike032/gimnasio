<%-- 
    Document   : FormUsuarios
    Descripción: Vista para consultar y agregar usuarios
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    request.setAttribute("activePage", "usuarios");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Usuarios | IronCore</title>

    <script src="https://code.jquery.com/jquery-3.7.1.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
</head>

<body>

    <%@ include file="../menu/FormMenu.jsp" %>

    <main id="mainContent">

        <header class="topbar">
            <div class="topbar-left">
                <div class="page-title">Usuarios</div>
                <div class="page-sub">Gestión de usuarios del sistema</div>
            </div>
        </header>

        <section class="content">

            <div class="form-card mb-4">

                <div class="form-card-header">
                    <div>
                        <h3>Nuevo usuario</h3>
                        <p>Completa la información para registrar un usuario.</p>
                    </div>

                    <div class="form-icon">
                        <i class="ti ti-user-cog"></i>
                    </div>
                </div>

                
                <div id="alertaUsuario" class="alerta-form" style="display:none;">
                    <i class="ti ti-alert-triangle"></i>
                    <span id="mensajeAlertaUsuario"></span>
                </div>

                <form id="formUsuario">

                    <input type="hidden" id="idUsuario">

                    <div class="form-grid">

                        <div class="form-group-ic">
                            <label>ID Rol</label>
                            <div class="input-ic">
                                <i class="ti ti-shield-check"></i>
                                <input type="number" id="idRol" placeholder="Ej: 1">
                            </div>
                        </div>

                        <div class="form-group-ic">
                            <label>Nombre</label>
                            <div class="input-ic">
                                <i class="ti ti-user"></i>
                                <input type="text" id="nombre" placeholder="Nombre del usuario">
                            </div>
                        </div>

                        <div class="form-group-ic">
                            <label>Correo</label>
                            <div class="input-ic">
                                <i class="ti ti-mail"></i>
                                <input type="email" id="correo" placeholder="correo@ejemplo.com">
                            </div>
                        </div>

                        <div class="form-group-ic">
                            <label>Clave</label>
                            <div class="input-ic">
                                <i class="ti ti-lock"></i>
                                <input type="password" id="clave" placeholder="Clave del usuario">
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

                    </div>

                    <div class="form-actions">
                        <button type="reset" class="btn-ic">
                            <i class="ti ti-eraser"></i>
                            Limpiar
                        </button>

                        <button type="submit" id="btnGuardarUsuario" class="btn-ic primary">
                            <i class="ti ti-device-floppy"></i>
                            Guardar usuario
                        </button>
                    </div>

                </form>

            </div>

            <div class="table-card">
                <div class="table-toolbar">
                    <span class="table-toolbar-title">Lista de usuarios</span>
                </div>

                <div id="listaUsuarios" class="lista-tabla">
                    <div class="text-center p-3">
                        Cargando usuarios...
                    </div>
                </div>
            </div>

        </section>

    </main>

    <script>
        window.APP_CONTEXT = "${pageContext.request.contextPath}";
        console.log("APP_CONTEXT:", window.APP_CONTEXT);
    </script>

    <script src="${pageContext.request.contextPath}/js/usuario.js"></script>

</body>
</html>