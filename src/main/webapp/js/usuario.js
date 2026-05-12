function cargarUsuarios() {
    fetch("UsuarioServlet")
        .then(res => res.json())
        .then(pintarTabla);
}

function pintarTabla(lista) {

    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Rol</th>
            <th>Nombre</th>
            <th>Correo</th>
            <th>Estado</th>
        </tr>`;

    lista.forEach(usuario => {
        tabla += `
        <tr>
            <td>${usuario.idUsuario}</td>
            <td>${usuario.rol}</td>
            <td>${usuario.nombre}</td>
            <td>${usuario.correo}</td>
            <td>${usuario.estado}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaUsuarios").html(tabla);
}

cargarUsuarios();