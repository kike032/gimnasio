<%-- 
    Document   : AgregarAlumno
    Descripción: Vista para agregar alumnos
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    request.setAttribute("activePage", "agregarAlumno");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Alumno | IronCore</title>

    <!-- JQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
            crossorigin="anonymous"></script>

    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
          crossorigin="anonymous">

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
            crossorigin="anonymous"></script>

    <!-- Iconos Tabler -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/tabler-icons.min.css">

    <!-- CSS principal -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
</head>

<body>

    <%@ include file="../menu/FormMenu.jsp" %>

    <main id="mainContent">

        <header class="topbar">
            <div class="topbar-left">
                <div class="page-title">Agregar Alumno</div>
                <div class="page-sub">Registro de nuevos alumnos del gimnasio</div>
            </div>

            <div class="topbar-actions">
                <a href="${pageContext.request.contextPath}/FormAlumnos.jsp" class="btn-ic">
                    <i class="ti ti-arrow-left"></i>
                    Volver
                </a>
            </div>
        </header>

        <section class="content">

            <div class="form-card">

                <div class="form-card-header">
                    <div>
                        <h3>Nuevo alumno</h3>
                        <p>Completa la información para registrar un alumno.</p>
                    </div>

                    <div class="form-icon">
                        <i class="ti ti-user-plus"></i>
                    </div>
                </div>

                <!-- Aviso fijo -->
                <div class="alerta-form alerta-exito">
                    <i class="ti ti-info-circle"></i>
                    <span>El ID del alumno y la fecha de registro se generan automáticamente al guardar.</span>
                </div>

                <!-- Mensajes dinámicos del JS -->
                <div id="alertaAlumno" class="alerta-form" style="display:none;">
                    <i class="ti ti-alert-triangle"></i>
                    <span id="mensajeAlertaAlumno"></span>
                </div>

                <form id="formAlumno">

                    <div class="form-grid">

                        <div class="form-group-ic">
                            <label>Nombre</label>
                            <div class="input-ic">
                                <i class="ti ti-user"></i>
                                <input type="text" id="nombre" placeholder="Nombre del alumno">
                            </div>
                        </div>

                        <div class="form-group-ic">
                            <label>Apellido</label>
                            <div class="input-ic">
                                <i class="ti ti-user"></i>
                                <input type="text" id="apellido" placeholder="Apellido del alumno">
                            </div>
                        </div>

                        <div class="form-group-ic">
                            <label>Teléfono</label>
                            <div class="input-ic">
                                <i class="ti ti-phone"></i>
                                <input type="text" id="telefono" placeholder="Ej: 7777-7777">
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
                            <label>Dirección</label>
                            <div class="input-ic">
                                <i class="ti ti-map-pin"></i>
                                <input type="text" id="direccion" placeholder="Dirección del alumno">
                            </div>
                        </div>

                    </div>

                    <div class="form-actions">
                        <button type="reset" class="btn-ic">
                            <i class="ti ti-eraser"></i>
                            Limpiar
                        </button>

                        <button type="submit" class="btn-ic primary">
                            <i class="ti ti-device-floppy"></i>
                            Guardar alumno
                        </button>
                    </div>

                </form>

            </div>

        </section>

    </main>

    <!-- IMPORTANTE: esto debe ir antes del alumno.js -->
    <script>
        window.APP_CONTEXT = "${pageContext.request.contextPath}";
        console.log("APP_CONTEXT:", window.APP_CONTEXT);
    </script>

    <script src="${pageContext.request.contextPath}/js/alumno.js"></script>

</body>
</html>