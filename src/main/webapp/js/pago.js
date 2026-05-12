/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
/*
 * pago.js — Gestión de Pagos
 */

function cargarPagos() {
    fetch("PagoServlet")
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
            <th>Monto</th>
            <th>Fecha Pago</th>
            <th>Método Pago</th>
            <th>Estado Pago</th>
        </tr>`;

    lista.forEach(pago => {
        tabla += `
        <tr>
            <td>${pago.idPago}</td>
            <td>${pago.alumno}</td>
            <td>${pago.servicio}</td>
            <td>$${pago.monto}</td>
            <td>${pago.fechaPago}</td>
            <td>${pago.metodoPago}</td>
            <td>${pago.estadoPago}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaPagos").html(tabla);
}

cargarPagos();

