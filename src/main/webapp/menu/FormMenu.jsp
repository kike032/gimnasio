<%
  String activePage = (String) request.getAttribute("activePage");
  if (activePage == null) activePage = "";
%>

<nav id="sidebar">

  <div class="sidebar-logo">
    <div class="logo-mark"><i class="ti ti-barbell"></i></div>
    <div class="logo-text">
      <div class="logo-name">IronCore</div>
      <div class="logo-sub">Gimnasio</div>
    </div>
  </div>

  <!-- SECCIÓN CONSULTAS -->
  <div class="nav-section-label section-toggle" onclick="toggleMenu('menuConsultas', this)">
    <div>
      <i class="ti ti-layout-dashboard"></i>
      <span>Consultas</span>
    </div>
    <i class="ti ti-chevron-down arrow"></i>
  </div>

  <div class="nav-items submenu" id="menuConsultas">
    <a href="${pageContext.request.contextPath}/FormAlumnos.jsp"
       class="nav-link-item <%= "alumnos".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-user-heart"></i>
      <span class="link-label">Alumnos</span>
    </a>

    <a href="${pageContext.request.contextPath}/FormIncripciones.jsp"
       class="nav-link-item <%= "inscripciones".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-clipboard-list"></i>
      <span class="link-label">Inscripciones</span>
    </a>

    <a href="${pageContext.request.contextPath}/FormAsistencias.jsp"
       class="nav-link-item <%= "asistencias".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-calendar-check"></i>
      <span class="link-label">Asistencias</span>
    </a>

    <a href="${pageContext.request.contextPath}/FormAtenciones.jsp"
       class="nav-link-item <%= "atenciones".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-urgent"></i>
      <span class="link-label">Atenciones</span>
    </a>

    <a href="${pageContext.request.contextPath}/FormServicios.jsp"
       class="nav-link-item <%= "servicios".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-category"></i>
      <span class="link-label">Servicios</span>
    </a>

    <a href="${pageContext.request.contextPath}/FormPagos.jsp"
       class="nav-link-item <%= "pagos".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-coin"></i>
      <span class="link-label">Pagos</span>
    </a>

    <a href="${pageContext.request.contextPath}/FormComprobantes.jsp"
       class="nav-link-item <%= "comprobantes".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-receipt"></i>
      <span class="link-label">Comprobantes</span>
    </a>

    <a href="${pageContext.request.contextPath}/FormUsuarios.jsp"
       class="nav-link-item <%= "usuarios".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-users"></i>
      <span class="link-label">Usuarios</span>
    </a>

    <a href="${pageContext.request.contextPath}/FormRol.jsp"
       class="nav-link-item <%= "roles".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-shield-check"></i>
      <span class="link-label">Roles</span>
    </a>
  </div>


  <!-- SECCIÓN AGREGAR -->
  <div class="nav-section-label section-toggle" onclick="toggleMenu('menuAgregar', this)">
    <div>
      <i class="ti ti-circle-plus"></i>
      <span>Agregar</span>
    </div>
    <i class="ti ti-chevron-down arrow"></i>
  </div>

  <div class="nav-items submenu" id="menuAgregar">
    <a href="${pageContext.request.contextPath}/AgregarAlumno.jsp"
       class="nav-link-item <%= "agregarAlumno".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-user-plus"></i>
      <span class="link-label">Agregar Alumno</span>
    </a>

    <a href="${pageContext.request.contextPath}/AgregarInscripcion.jsp"
       class="nav-link-item <%= "agregarInscripcion".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-clipboard-plus"></i>
      <span class="link-label">Agregar Inscripción</span>
    </a>

    <a href="${pageContext.request.contextPath}/AgregarServicio.jsp"
       class="nav-link-item <%= "agregarServicio".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-category-plus"></i>
      <span class="link-label">Agregar Servicio</span>
    </a>

    <a href="${pageContext.request.contextPath}/AgregarPago.jsp"
       class="nav-link-item <%= "agregarPago".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-cash"></i>
      <span class="link-label">Agregar Pago</span>
    </a>

    <a href="${pageContext.request.contextPath}/AgregarUsuario.jsp"
       class="nav-link-item <%= "agregarUsuario".equals(activePage) ? "active" : "" %>">
      <i class="ti ti-user-cog"></i>
      <span class="link-label">Agregar Usuario</span>
    </a>
  </div>


  <div class="sidebar-footer">
    <div class="user-area">
      <div class="user-ava">AD</div>
      <div class="user-info">
        <div class="user-name">Admin</div>
        <div class="user-role">Administrador</div>
      </div>
    </div>

    <button id="toggleBtn" onclick="toggleSidebar()">
      <i class="ti ti-chevrons-left"></i>
    </button>
  </div>

</nav>

<script>
  function toggleSidebar() {
    document.getElementById('sidebar').classList.toggle('collapsed');
  }

  function toggleMenu(menuId, element) {
    const menu = document.getElementById(menuId);
    const arrow = element.querySelector('.arrow');

    menu.classList.toggle('closed');
    arrow.classList.toggle('rotate');
  }
</script>