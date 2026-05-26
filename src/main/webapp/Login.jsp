<%-- 
    Document   : Login
    Created on : 25 may 2026, 12:09:30
    Author     : kikej
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Perfect Body — Iniciar sesión</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@latest/dist/tabler-icons.min.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/estilo/estilo.css">
</head>
<body class="login-body">

  <div class="login-card">

    <!-- Panel izquierdo -->
    <div class="login-brand">
      <div class="login-logo">
        <i class="ti ti-barbell"></i>
      </div>
      <div class="login-name">Perfect Body</div>
      <div class="login-gym">Gimnasio</div>
      <div class="login-tagline">Sistema de gestión<br>para tu gimnasio</div>
    </div>

    <!-- Panel derecho -->
    <div class="login-form-wrap">
      <div class="login-form">

        <div class="login-title">Bienvenido</div>
        <div class="login-sub">Ingresá tus credenciales</div>

        <div class="login-group">
          <label class="login-label" for="usuario">Usuario</label>
          <div class="login-input-wrap">
            <i class="ti ti-user"></i>
            <input type="text" id="usuario" name="usuario" placeholder="tu.usuario" autocomplete="username" />
          </div>
        </div>

        <div class="login-group">
          <label class="login-label" for="password">Contraseña</label>
          <div class="login-input-wrap">
            <i class="ti ti-lock"></i>
            <input type="password" id="password" name="password" placeholder="••••••••" autocomplete="current-password" />
            <button class="login-eye" type="button" onclick="togglePass()" aria-label="Mostrar contraseña">
              <i class="ti ti-eye" id="eyeIcon"></i>
            </button>
          </div>
        </div>

        <button class="login-btn" type="button" onclick="window.location='FormAlumnos.jsp'">
          <i class="ti ti-login"></i> Ingresar
        </button>

      </div>
    </div>

  </div>

  <script>
    function togglePass() {
      var p = document.getElementById('password');
      var e = document.getElementById('eyeIcon');
      if (p.type === 'password') {
        p.type = 'text';
        e.className = 'ti ti-eye-off';
      } else {
        p.type = 'password';
        e.className = 'ti ti-eye';
      }
    }
  </script>

</body>
</html>

