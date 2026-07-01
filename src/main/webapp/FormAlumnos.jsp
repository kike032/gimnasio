<%-- 
    Document   : FormAlumnos
    Created on : 24 may 2026, 22:31:00
    Author     : kikej
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("activePage", "alumnos"); %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>IronCore — Alumnos</title>
  <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
  <style>
    #overlayAlumno, #overlayEditarAlumno {
      display: none;
      position: fixed;
      top: 0; left: 0;
      width: 100%; height: 100%;
      background: rgba(0, 0, 0, 0.6);
      z-index: 9999;
      align-items: center;
      justify-content: center;
    }
    #divAgregarAlumno, #divEditarAlumno {
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
        <div class="page-title">Alumnos</div>
        <div class="page-sub">Gestión de alumnos registrados</div>
      </div>
      <button type="button" class="btn-ic primary" id="btnAbrirFormAlumno">
        <i class="ti ti-plus"></i> Agregar alumno
      </button>
    </div>

    <div class="table-card">
      <div class="table-toolbar">
        <span class="table-toolbar-title">Lista de alumnos</span>
      </div>
      <div id="listaAlumnos" class="lista-tabla"></div>
      <div class="pagination-bar">
        <span class="pag-info" id="paginaInfo">Mostrando alumnos</span>
        <div class="pag-btns"></div>
      </div>
    </div>
  </div>

  <%-- OVERLAY AGREGAR --%>
  <div id="overlayAlumno">
    <div id="divAgregarAlumno">
      <div class="form-card-header">
        <div>
          <h3>Nuevo alumno</h3>
          <p>Completa la información para registrar un alumno.</p>
        </div>
        <div class="form-icon"><i class="ti ti-user-plus"></i></div>
      </div>
      <div class="alerta-form alerta-exito">
        <i class="ti ti-info-circle"></i>
        <span>El ID y la fecha de registro se generan automáticamente al guardar.</span>
      </div>
      <div id="alertaAlumno" class="alerta-form" style="display:none;">
        <i class="ti ti-alert-triangle"></i>
        <span id="mensajeAlertaAlumno"></span>
      </div>
      <form id="formAlumno">
        <div class="form-grid">
          <div class="form-group-ic">
            <label>Nombre</label>
            <div class="input-ic"><i class="ti ti-user"></i>
              <input type="text" id="nombre" placeholder="Nombre del alumno">
            </div>
          </div>
          <div class="form-group-ic">
            <label>Apellido</label>
            <div class="input-ic"><i class="ti ti-user"></i>
              <input type="text" id="apellido" placeholder="Apellido del alumno">
            </div>
          </div>
          <div class="form-group-ic">
            <label>Teléfono</label>
            <div class="input-ic"><i class="ti ti-phone"></i>
              <input type="text" id="telefono" placeholder="Ej: 7777-7777">
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
          <div class="form-group-ic full">
            <label>Dirección</label>
            <div class="input-ic"><i class="ti ti-map-pin"></i>
              <input type="text" id="direccion" placeholder="Dirección del alumno">
            </div>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn-ic" id="btnCerrarFormAlumno">
            <i class="ti ti-x"></i> Cancelar
          </button>
          <button type="button" class="btn-ic" id="btnLimpiarAlumno">
            <i class="ti ti-eraser"></i> Limpiar
          </button>
          <button type="submit" class="btn-ic primary">
            <i class="ti ti-device-floppy"></i> Guardar alumno
          </button>
        </div>
      </form>
    </div>
  </div>

  <%-- OVERLAY EDITAR --%>
  <div id="overlayEditarAlumno">
    <div id="divEditarAlumno">
      <div class="form-card-header">
        <div>
          <h3>Editar alumno</h3>
          <p>Modifica los datos del alumno seleccionado.</p>
        </div>
        <div class="form-icon"><i class="ti ti-user-edit"></i></div>
      </div>
      <div id="alertaEditar" class="alerta-form" style="display:none;">
        <i class="ti ti-alert-triangle"></i>
        <span id="mensajeAlertaEditar"></span>
      </div>
      <form id="formEditarAlumno">
        <input type="hidden" id="editIdAlumno">
        <input type="hidden" id="editFechaRegistro">
        <div class="form-grid">
          <div class="form-group-ic">
            <label>Nombre</label>
            <div class="input-ic"><i class="ti ti-user"></i>
              <input type="text" id="editNombre" placeholder="Nombre del alumno">
            </div>
          </div>
          <div class="form-group-ic">
            <label>Apellido</label>
            <div class="input-ic"><i class="ti ti-user"></i>
              <input type="text" id="editApellido" placeholder="Apellido del alumno">
            </div>
          </div>
          <div class="form-group-ic">
            <label>Teléfono</label>
            <div class="input-ic"><i class="ti ti-phone"></i>
              <input type="text" id="editTelefono" placeholder="Ej: 7777-7777">
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
          <div class="form-group-ic full">
            <label>Dirección</label>
            <div class="input-ic"><i class="ti ti-map-pin"></i>
              <input type="text" id="editDireccion" placeholder="Dirección del alumno">
            </div>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn-ic" id="btnCerrarEditarAlumno">
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
  <script src="${pageContext.request.contextPath}/js/alumno.js"></script>

</body>
</html>
