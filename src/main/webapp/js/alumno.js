/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function cargarAlumnos() {
    fetch("AlumnoServlet")
        .then(res => res.json())
        .then(pintarTabla);
}

function pintarTabla(lista) {

    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Teléfono</th>
            <th>Dirección</th>
            <th>Fecha Registro</th>
            <th>Estado</th>
        </tr>`;

    lista.forEach(alumno => {
        tabla += `
        <tr>
            <td>${alumno.idAlumno}</td>
            <td>${alumno.nombre}</td>
            <td>${alumno.apellido}</td>
            <td>${alumno.telefono}</td>
            <td>${alumno.direccion}</td>
            <td>${alumno.fechaRegistro}</td>
            <td>${alumno.estado}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaAlumnos").html(tabla);
}

cargarAlumnos();

