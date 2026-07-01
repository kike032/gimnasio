<%-- 
    Document   : FormAtenciones
    Created on : 7 may 2026, 22:31:46
    Author     : kikej
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% request.setAttribute("activePage", "atenciones"); %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>IronCore — Atenciones</title>
  <script src="https://code.jquery.com/jquery-3.7.1.js"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
  <style>
    #overlayAtencion, #overlayEditarAtencion {
      display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%;
      background: rgba(0, 0, 0, 0.6); z-index: 9999; align-items: center; justify-content: center;
    }
    #divAgregarAtencion, #divEditarAtencion {
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
        <div class="page-title">Atenciones al Alumno</div>
        <div class="page-sub">Bitácora de asesorías, quejas o seguimientos especiales</div>
      </div>
      <button type="button" class="btn-ic primary" id="btnAbrirFormAtencion">
        <i class="ti ti-plus"></i> Registrar Atención
      </button>
    </div>

    <div class="table-card">
      <div class="table-toolbar">
        <span class="table-toolbar-title">Historial de Atenciones</span>
      </div>
      <div id="listaAtenciones" class="lista-tabla"></div>
      <div class="pagination-bar">
        <span class="pag-info" id="paginaInfo">Mostrando registros</span>
        <div class="pag-btns"></div>
      </div>
    </div>
  </div>

  <%-- OVERLAY AGREGAR --%>
  <div id="overlayAtencion">
    <div id="divAgregarAtencion">
      <div class="form-card-header">
        <div>
          <h3>Nueva Ficha de Atención</h3>
          <p>Especifique el tipo y detalle de la atención realizada.</p>
        </div>
        <div class="form-icon"><i class="ti ti-heart-handshake"></i></div>
      </div>
      <form id="formAtencion">
        <div class="form-grid">
          <div class="form-group-ic"><label>Alumno</label>
            <div class="input-ic"><i class="ti ti-user"></i><select id="idAlumno"></select></div>
          </div>
          <div class="form-group-ic"><label>Atendido por (Usuario)</label>
            <div class="input-ic"><i class="ti ti-id-badge"></i><select id="idUsuario"></select></div>
          </div>
          <div class="form-group-ic"><label>Inscripción Asociada</label>
            <div class="input-ic"><i class="ti ti-file-text"></i><select id="idInscripcion"></select></div>
          </div>
          <div class="form-group-ic"><label>Tipo de Atención</label>
            <div class="input-ic"><i class="ti ti-category"></i>
              <select id="tipoAtencion">
                <option value="Asesoría Técnica">Asesoría Técnica</option>
                <option value="Nutricional">Nutricional</option>
                <option value="Fisioterapia">Fisioterapia</option>
                <option value="Reclamo/Queja">Reclamo / Queja</option>
              </select>
            </div>
          </div>
          <div class="form-group-ic" style="grid-column: span 2;"><label>Descripción / Observaciones</label>
            <div class="input-ic"><i class="ti ti-notes"></i><textarea id="descripcion" rows="3" class="form-control" style="border:none; padding-left:2.5rem;"></textarea></div>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn-ic" id="btnCerrarFormAtencion"><i class="ti ti-x"></i> Cancelar</button>
          <button type="submit" class="btn-ic primary"><i class="ti ti-device-floppy"></i> Guardar Ficha</button>
        </div>
      </form>
    </div>
  </div>

  <%-- OVERLAY EDITAR --%>
  <div id="overlayEditarAtencion">
    <div id="divEditarAtencion">
      <div class="form-card-header">
        <div>
          <h3>Modificar Ficha de Atención</h3>
          <p>Actualice las anotaciones del expediente.</p>
        </div>
        <div class="form-icon"><i class="ti ti-edit"></i></div>
      </div>
      <form id="formEditarAtencion">
        <input type="hidden" id="editIdAtencion">
        <input type="hidden" id="editFechaAtencion">
        <div class="form-grid">
          <div class="form-group-ic"><label>Alumno</label>
            <div class="input-ic"><i class="ti ti-user"></i><select id="editIdAlumno"></select></div>
          </div>
          <div class="form-group-ic"><label>Usuario</label>
            <div class="input-ic"><i class="ti ti-id-badge"></i><select id="editIdUsuario"></select></div>
          </div>
          <div class="form-group-ic"><label>Inscripción</label>
            <div class="input-ic"><i class="ti ti-file-text"></i><select id="editIdInscripcionRow"></select></div>
          </div>
          <div class="form-group-ic"><label>Tipo de Atención</label>
            <div class="input-ic"><i class="ti ti-category"></i>
              <select id="editTipoAtencion">
                <option value="Asesoría Técnica">Asesoría Técnica</option>
                <option value="Nutricional">Nutricional</option>
                <option value="Fisioterapia">Fisioterapia</option>
                <option value="Reclamo/Queja">Reclamo / Queja</option>
              </select>
            </div>
          </div>
          <div class="form-group-ic" style="grid-column: span 2;"><label>Descripción / Observaciones</label>
            <div class="input-ic"><i class="ti ti-notes"></i><textarea id="editDescripcion" rows="3" class="form-control" style="border:none; padding-left:2.5rem;"></select></textarea></div>
          </div>
        </div>
        <div class="form-actions">
          <button type="button" class="btn-ic" id="btnCerrarEditarAtencion"><i class="ti ti-x"></i> Cancelar</button>
          <button type="submit" class="btn-ic primary"><i class="ti ti-device-floppy"></i> Guardar Cambios</button>
        </div>
      </form>
    </div>
  </div>

  <script>window.APP_CONTEXT = "${pageContext.request.contextPath}";</script>
  <script src="${pageContext.request.contextPath}/js/atencion.js"></script>
</body>
</html>