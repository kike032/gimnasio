<%-- 
    Document   : servicios
    Created on : 20 jun 2026, 14:45:26
    Author     : jimen
--%>

<%-- 
    Document   : AgregarServicio
    Descripción: Vista para agregar servicios
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    request.setAttribute("activePage", "agregarServicio");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Servicio | IronCore</title>

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
                <div class="page-title">Agregar Servicio</div>
                <div class="page-sub">Registro de nuevos servicios del gimnasio</div>
            </div>

            <div class="topbar-actions">
                <a href="${pageContext.request.contextPath}/FormServicios.jsp" class="btn-ic">
                    <i class="ti ti-arrow-left"></i>
                    Volver
                </a>
            </div>
        </header>

        <section class="content">

            <div class="form-card">

                <div class="form-card-header">
                    <div>
                        <h3>Nuevo servicio</h3>
                        <p>Completa la información para registrar un servicio del gimnasio.</p>
                    </div>

                    <div class="form-icon">
                        <i class="ti ti-list-details"></i>
                    </div>
                </div>

                <!-- Aviso fijo -->
                <div class="alerta-form alerta-exito">
                    <i class="ti ti-info-circle"></i>
                    <span>El ID del servicio se genera automáticamente al guardar.</span>
                </div>

                <!-- Mensajes dinámicos del JS -->
                <div id="alertaServicio" class="alerta-form" style="display:none;">
                    <i class="ti ti-alert-triangle"></i>
                    <span id="mensajeAlertaServicio"></span>
                </div>

                <form id="formServicio">

                    <input type="hidden" id="idServicio">

                    <div class="form-grid">

                        <div class="form-group-ic">
                            <label>Nombre del servicio</label>
                            <div class="input-ic">
                                <i class="ti ti-tag"></i>
                                <input type="text" id="nombreServicio" placeholder="Ej: Mensualidad, Matrícula, Personalizado">
                            </div>
                        </div>

                        <div class="form-group-ic">
                            <label>Precio</label>
                            <div class="input-ic">
                                <i class="ti ti-cash"></i>
                                <input type="number" id="precio" step="0.01" min="0" placeholder="Ej: 25.00">
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
                                <input type="text" id="descripcion" placeholder="Descripción del servicio">
                            </div>
                        </div>

                    </div>

                    <div class="form-actions">
                        <button type="reset" class="btn-ic">
                            <i class="ti ti-eraser"></i>
                            Limpiar
                        </button>

                        <button type="submit" id="btnGuardarServicio" class="btn-ic primary">
                            <i class="ti ti-device-floppy"></i>
                            Guardar servicio
                        </button>

                        <button type="button" id="btnCancelarEdicion" class="btn-ic" style="display:none;">
                            <i class="ti ti-x"></i>
                            Cancelar
                        </button>
                    </div>

                </form>

            </div>

        </section>

    </main>

    <!-- IMPORTANTE: esto debe ir antes del servicio.js -->
    <script>
        window.APP_CONTEXT = "${pageContext.request.contextPath}";
        console.log("APP_CONTEXT:", window.APP_CONTEXT);
    </script>

    <script src="${pageContext.request.contextPath}/js/servicio.js"></script>

</body>
</html>
