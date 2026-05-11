/*
 * rol.js — Gestión de Roles
 */

function cargarRoles() {
    fetch("RolServlet")
        .then(res => res.json())
        .then(pintarTabla);
}

function pintarTabla(lista) {

    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Nombre</th>
            <th>Descripción</th>
        </tr>`;

    lista.forEach(rol => {
        tabla += `
        <tr>
            <td>${rol.idRol}</td>
            <td>${rol.nombreRol}</td>
            <td>${rol.descripcion}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaRol").html(tabla);
}

cargarRoles();