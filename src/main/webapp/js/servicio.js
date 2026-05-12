/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function cargarServicios() {
    fetch("ServicioServlet")
        .then(res => res.json())
        .then(pintarTabla);
}

function pintarTabla(lista) {

    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Nombre Servicio</th>
            <th>Descripción</th>
            <th>Precio</th>
            <th>Estado</th>
        </tr>`;

    lista.forEach(servicio => {
        tabla += `
        <tr>
            <td>${servicio.idServicio}</td>
            <td>${servicio.nombreServicio}</td>
            <td>${servicio.descripcion}</td>
            <td>$${servicio.precio}</td>
            <td>${servicio.estado}</td>
        </tr>`;
    });

    tabla += "</table>";

    $("#listaServicios").html(tabla);
}

cargarServicios();
