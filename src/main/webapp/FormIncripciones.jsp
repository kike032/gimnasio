<%-- 
    Document   : FormInscripciones
    Created on : 7 may 2026, 22:30:04
    Author     : kikej
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("activePage", "inscripciones"); %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>IronCore — Inscripciones</title>
  <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
  <style>
    #overlayInscripcion, #overlayEditarInscripcion {
      display: none;
      position: fixed;
      top: 0; left: 0;
      width: 100%; height: 100%;
      background: rgba(0, 0, 0, 0.6);
      z-index: 9999;
      align-items: center;
      justify-content: center;
    }
    #divAgregarInscripcion, #divEditarInscripcion {
      background: #ffffff;
      border-radius: 12px;
      padding: 2rem;
      width: 600px;
      max-width: 90vw;
      max-height: 85vh;
      overflow-y: auto;
      box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);
    }
  </style>
</head>
<body>

  <%@ include file="../menu/FormMenu.jsp" %>

  <div id="mainContent">
    <div class="topbar">
      <div class="topbar-left">
        <div class="page-title">Inscripciones</div>
        <div class="page-sub">Gestión de inscripciones activas</div>
      </div>
      <button type="button" class="btn-ic primary" id="btnAbrirFormInscripcion">
        <i class="ti ti-plus"></i> Agregar inscripción
      </button>
    </div>

    <div class="table-card">
      <div class="table-toolbar">
        <span class="table-toolbar-title">Lista de inscripciones</span>
      </div>
      <div id="listaInscripciones" class="lista-tabla"></div>
      <div class="pagination-bar">
        <span class="pag-info" id="paginaInfo">Mostrando inscripciones</span>
        <div class="pag-btns">
          </div>
      </div>
    </div>
  </div>

  <%-- OVERLAY AGREGAR --%>
  <div id="overlayInscripcion">
    <div id="divAgregarInscripcion">
      <div class="form-card-header">
        <div>
          <h3>Nueva inscripción</h3>
          <p>Completa la información para registrar una inscripción.</p>
        </div>
        <div class="form-icon"><i class="ti ti-clipboard-plus"></i></div>
      </div>
      <div class="alerta-form alerta-exito">
        <i class="ti ti-info-circle"></i>
        <span>El ID y la fecha de inscripción se generan automáticamente al guardar.</span>
      </div>
      <div id="alertaInscripcion" class="alerta-form" style="display:none;">
        <i class="ti ti-alert-triangle"></i>
        <span id="mensajeAlertaInscripcion"></span>
      </div>
      <form id="formInscripcion">
        <div class="form-grid">
          <div class="form-group-ic">
            <label>Alumno</label>
            <div class="input-ic"><i class="ti ti-user"></i>
              <select id="idAlumno">
                <option value="">Seleccione un alumno</option>
              </select>
            </div>
          </div>
          <div class="form-group-ic">
            <label>Servicio</label>
            <div class="input-ic"><i class="ti ti-list-details"></i>
              <select id="idServicio">
                <option value="">Seleccione un servicio</option>
              </select>
            </div>
          </div>
          <div class="form-group-ic">
            <label>Estado</label>
            <div class="input-ic"><i class="ti ti-circle-check"></i>
              <select id="estado">
                <option value="true">Activo</option>
                <option value="false">Inactivo</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn-ic" id="btnCerrarFormInscripcion">
            <i class="ti ti-x"></i> Cancelar
          </button>
          <button type="button" class="btn-ic" id="btnLimpiarInscripcion">
            <i class="ti ti-eraser"></i> Limpiar
          </button>
          <button type="submit" class="btn-ic primary">
            <i class="ti ti-device-floppy"></i> Guardar inscripción
          </button>
        </div>
      </form>
    </div>
  </div>

  <%-- OVERLAY EDITAR --%>
  <div id="overlayEditarInscripcion">
    <div id="divEditarInscripcion">
      <div class="form-card-header">
        <div>
          <h3>Editar inscripción</h3>
          <p>Modifica los datos de la inscripción seleccionada.</p>
        </div>
        <div class="form-icon"><i class="ti ti-clipboard-text"></i></div>
      </div>
      <div id="alertaEditar" class="alerta-form" style="display:none;">
        <i class="ti ti-alert-triangle"></i>
        <span id="mensajeAlertaEditar"></span>
      </div>
      <form id="formEditarInscripcion">
        <input type="hidden" id="editIdInscripcion">
        <input type="hidden" id="editFechaInscripcion">
        <div class="form-grid">
          <div class="form-group-ic">
            <label>Alumno</label>
            <div class="input-ic"><i class="ti ti-user"></i>
              <select id="editIdAlumno">
                <option value="">Seleccione un alumno</option>
              </select>
            </div>
          </div>
          <div class="form-group-ic">
            <label>Servicio</label>
            <div class="input-ic"><i class="ti ti-list-details"></i>
              <select id="editIdServicio">
                <option value="">Seleccione un servicio</option>
              </select>
            </div>
          </div>
          <div class="form-group-ic">
            <label>Estado</label>
            <div class="input-ic"><i class="ti ti-circle-check"></i>
              <select id="editEstado">
                <option value="true">Activo</option>
                <option value="false">Inactivo</option>
              </select>
            </div>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn-ic" id="btnCerrarEditarInscripcion">
            <i class="ti ti-x"></i> Cancelar
          </button>
          <button type="submit" class="btn-ic primary">
            <i class="ti ti-device-floppy"></i> Guardar cambios
          </button>
        </div>
      </form>
    </div>
  </div>

  <script>
    window.APP_CONTEXT = "${pageContext.request.contextPath}";
  </script>
  <script src="${pageContext.request.contextPath}/js/inscripcion.js"></script>

</body>
</html>