<%-- 
    Document   : FormServicios
    Descripción: Vista única para consultar y agregar servicios
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("activePage", "servicios"); %>

<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />

  <title>IronCore — Servicios</title>

  <!-- JQuery -->
  <script src="https://code.jquery.com/jquery-3.7.1.js"></script>

  <!-- Bootstrap -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>

  <!-- Iconos -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css" />

  <!-- CSS principal -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
</head>

<body>

  <%@ include file="../menu/FormMenu.jsp" %>

  <div id="mainContent">

    <div class="topbar">
      <div class="topbar-left">
        <div class="page-title">Servicios</div>
        <div class="page-sub">Catálogo y registro de servicios del gimnasio</div>
      </div>
    </div>

    <section class="content">

      <!-- FORMULARIO PARA AGREGAR SERVICIO -->
      <div class="form-card mb-4">

        <div class="form-card-header">
          <div>
            <h3>Nuevo servicio</h3>
            <p>Completa la información para registrar un servicio del gimnasio.</p>
          </div>

          <div class="form-icon">
            <i class="ti ti-category-plus"></i>
          </div>
        </div>

        

        <!-- Mensajes dinámicos -->
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
          </div>

        </form>

      </div>

      <!-- TABLA DE SERVICIOS -->
      <div class="table-card">

        <div class="table-toolbar">
          <span class="table-toolbar-title">Lista de servicios</span>
        </div>

        <div id="listaServicios" class="lista-tabla">
          <div class="text-center p-3">
            Cargando servicios...
          </div>
        </div>

      </div>

    </section>

  </div>

  <!-- IMPORTANTE: antes de servicio.js -->
  <script>
      window.APP_CONTEXT = "${pageContext.request.contextPath}";
      console.log("APP_CONTEXT:", window.APP_CONTEXT);
  </script>

  <script src="${pageContext.request.contextPath}/js/servicio.js"></script>

</body>
</html>