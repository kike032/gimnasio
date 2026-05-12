/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
/*
 * asistencia.js — Gestión de Asistencias
 */

function cargarAsistencias() {
    fetch("AsistenciaServlet")
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
            <th>Pago</th>
            <th>Fecha Asistencia</th>
            <th>Hora Entrada</th>
            <th>Hora Salida</th>
        </tr>`;

    lista.forEach(asistencia => {
        tabla += `
        <tr>
            <td>${asistencia.idAsistencia}</td>
            <td>${asistencia.alumno}</td>
            <td>${asistencia.idServicio}</td>
            <td>${asistencia.idPago}</td>
            <td>${asistencia.fechaAsistencia}</td>
            <td>${asistencia.horaEntrada}</td>
            <td>${asistencia.horaSalida}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaAsistencias").html(tabla);
}

cargarAsistencias();

