<%-- 
    Document   : FormIncripciones
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
</head>
<body>
  <%@ include file="../menu/FormMenu.jsp" %>
  <div id="mainContent">
    <div class="topbar">
      <div class="topbar-left">
        <div class="page-title">Inscripciones</div>
        <div class="page-sub">Gestión de inscripciones activas</div>
      </div>
    </div>
    <div class="table-card">
      <div class="table-toolbar">
        <span class="table-toolbar-title">Lista de inscripciones</span>
      </div>
      <div id="listaInscripciones" class="lista-tabla">
        <div class="skeleton-row"><div class="skel" style="width:30%"></div><div class="skel" style="width:20%"></div><div class="skel" style="width:15%"></div><div class="skel" style="width:15%"></div></div>
        <div class="skeleton-row"><div class="skel" style="width:25%"></div><div class="skel" style="width:22%"></div><div class="skel" style="width:18%"></div><div class="skel" style="width:12%"></div></div>
        <div class="skeleton-row"><div class="skel" style="width:28%"></div><div class="skel" style="width:18%"></div><div class="skel" style="width:16%"></div><div class="skel" style="width:14%"></div></div>
      </div>
      <div class="pagination-bar">
        <span class="pag-info">Mostrando 1–10 de 210 inscripciones</span>
        <div class="pag-btns">
          <button class="pag-btn"><i class="ti ti-chevron-left"></i></button>
          <button class="pag-btn active">1</button>
          <button class="pag-btn">2</button>
          <button class="pag-btn">3</button>
          <button class="pag-btn"><i class="ti ti-chevron-right"></i></button>
        </div>
      </div>
    </div>
  </div>
  <script src="${pageContext.request.contextPath}/js/inscripcion.js"></script>
</body>
</html>

