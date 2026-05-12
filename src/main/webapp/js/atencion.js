/*
 * atencion.js — Gestión de Atenciones
 */

function cargarAtenciones() {
    fetch("AtencionServlet")
        .then(res => res.json())
        .then(pintarTabla);
}

function pintarTabla(lista) {

    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Alumno</th>
            <th>Fecha Atención</th>
            <th>Tipo Atención</th>
            <th>Descripción</th>
        </tr>`;

    lista.forEach(atencion => {
        tabla += `
        <tr>
            <td>${atencion.idAtencion}</td>
            <td>${atencion.alumno}</td>
            <td>${atencion.fechaAtencion}</td>
            <td>${atencion.tipoAtencion}</td>
            <td>${atencion.descripcion}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaAtenciones").html(tabla);
}

cargarAtenciones();