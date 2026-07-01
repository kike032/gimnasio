<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("activePage", "asistencias"); %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>IronCore — Asistencias</title>
  <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
  <style>
    #overlayAsistencia, #overlayEditarAsistencia {
      display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%;
      background: rgba(0, 0, 0, 0.6); z-index: 9999; align-items: center; justify-content: center;
    }
    #divAgregarAsistencia, #divEditarAsistencia {
      background: #ffffff; border-radius: 12px; padding: 2rem; width: 600px; max-width: 90vw;
      max-height: 85vh; overflow-y: auto; box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);
    }
  </style>
</head>
<body>

  <%@ include file="../menu/FormMenu.jsp" %>

  <div id="mainContent">
    <div class="topbar">
      <div class="topbar-left">
        <div class="page-title">Asistencias</div>
        <div class="page-sub">Control de accesos y asistencias a la sala</div>
      </div>
      <button type="button" class="btn-ic primary" id="btnAbrirFormAsistencia">
        <i class="ti ti-plus"></i> Marcar Entrada
      </button>
    </div>

    <div class="table-card">
      <div class="table-toolbar">
        <span class="table-toolbar-title">Bitácora de Asistencia</span>
      </div>
      <div id="listaAsistencias" class="lista-tabla"></div>
      <div class="pagination-bar">
        <span class="pag-info" id="paginaInfo">Mostrando asistencias</span>
        <div class="pag-btns"></div>
      </div>
    </div>
  </div>

  <%-- OVERLAY AGREGAR --%>
  <div id="overlayAsistencia">
    <div id="divAgregarAsistencia">
      <div class="form-card-header">
        <div>
          <h3>Nueva Asistencia</h3>
          <p>Asocie al Alumno, Servicio y Pago correspondiente.</p>
        </div>
        <div class="form-icon"><i class="ti ti-clock-bolt"></i></div>
      </div>
      <form id="formAsistencia">
        <div class="form-grid">
          <div class="form-group-ic"><label>Alumno</label>
            <div class="input-ic"><i class="ti ti-user"></i><select id="idAlumno"></select></div>
          </div>
          <div class="form-group-ic"><label>Servicio</label>
            <div class="input-ic"><i class="ti ti-barbell"></i><select id="idServicio"></select></div>
          </div>
          <div class="form-group-ic"><label>Vincular Pago</label>
            <div class="input-ic"><i class="ti ti-receipt"></i><select id="idPago"></select></div>
          </div>
          <div class="form-group-ic"><label>Hora Entrada (Opcional)</label>
            <div class="input-ic"><i class="ti ti-login"></i><input type="time" id="horaEntrada"></div>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn-ic" id="btnCerrarFormAsistencia"><i class="ti ti-x"></i> Cancelar</button>
          <button type="submit" class="btn-ic primary"><i class="ti ti-device-floppy"></i> Registrar Entrada</button>
        </div>
      </form>
    </div>
  </div>

  <%-- OVERLAY EDITAR --%>
  <div id="overlayEditarAsistencia">
    <div id="divEditarAsistencia">
      <div class="form-card-header">
        <div>
          <h3>Editar Registro de Asistencia</h3>
          <p>Modifica o asigna tiempos de salida.</p>
        </div>
        <div class="form-icon"><i class="ti ti-clock-edit"></i></div>
      </div>
      <form id="formEditarAsripcion">
        <input type="hidden" id="editIdAsistencia">
        <input type="hidden" id="editFechaAsistencia">
        <div class="form-grid">
          <div class="form-group-ic"><label>Alumno</label>
            <div class="input-ic"><i class="ti ti-user"></i><select id="editIdAlumno"></select></div>
          </div>
          <div class="form-group-ic"><label>Servicio</label>
            <div class="input-ic"><i class="ti ti-barbell"></i><select id="editIdServicio"></select></div>
          </div>
          <div class="form-group-ic"><label>Vincular Pago</label>
            <div class="input-ic"><i class="ti ti-receipt"></i><select id="editIdPago"></select></div>
          </div>
          <div class="form-group-ic"><label>Hora Entrada</label>
            <div class="input-ic"><i class="ti ti-login"></i><input type="time" id="editHoraEntrada"></div>
          </div>
          <div class="form-group-ic"><label>Hora Salida</label>
            <div class="input-ic"><i class="ti ti-logout"></i><input type="time" id="editHoraSalida"></div>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn-ic" id="btnCerrarEditarAsistencia"><i class="ti ti-x"></i> Cancelar</button>
          <button type="submit" id="formEditarAsistencia" class="btn-ic primary"><i class="ti ti-device-floppy"></i> Guardar Cambios</button>
        </div>
      </form>
    </div>
  </div>

  <script>window.APP_CONTEXT = "${pageContext.request.contextPath}";</script>
  <script src="${pageContext.request.contextPath}/js/asistencia.js"></script>
</body>
</html>