/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function cargarInscripciones() {
    fetch("InscripcionServlet")
        .then(res => res.json())
        .then(pintarTabla);
}

function pintarTabla(lista) {

    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Alumno</th>
            <th>Servicio</th>
            <th>Fecha Inscripción</th>
            <th>Estado</th>
        </tr>`;

    lista.forEach(inscripcion => {
        tabla += `
        <tr>
            <td>${inscripcion.idInscripcion}</td>
            <td>${inscripcion.alumno}</td>
            <td>${inscripcion.servicio}</td>
            <td>${inscripcion.fechaInscripcion}</td>
            <td>${inscripcion.estado}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaInscripciones").html(tabla);
}

cargarInscripciones();

