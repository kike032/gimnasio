/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function cargarComprobantes() {
    fetch("ComprobanteServlet")
        .then(res => res.json())
        .then(pintarTabla);
}

function pintarTabla(lista) {

    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Número de Comprobante</th>
            <th>Fecha de Emisión</th>
            <th>Total</th>
        </tr>`;

    lista.forEach(comprobante => {
        tabla += `
        <tr>
            <td>${comprobante.idComprobante}</td>
            <td>${comprobante.numeroComprobante}</td>
            <td>${comprobante.fechaEmision}</td>
            <td>$${comprobante.total}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaComprobantes").html(tabla);
}

cargarComprobantes();
