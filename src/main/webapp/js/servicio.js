let editandoServicio = false;

$(document).ready(function () {
    console.log("servicio.js cargado correctamente");

    cargarServicios();

    $("#formServicio").on("submit", function (e) {
        e.preventDefault();
        guardarServicio();
    });

    $("#formServicio").on("reset", function () {
        setTimeout(function () {
            limpiarFormularioServicio();
            ocultarMensajeServicio();
            limpiarErroresServicio();
        }, 50);
    });
});

function cargarServicios() {
    fetch(window.APP_CONTEXT + "/ServicioServlet")
        .then(res => res.json())
        .then(data => {
            console.log("Servicios recibidos:", data);

            if (Array.isArray(data)) {
                pintarTablaServicios(data);
            } else {
                $("#listaServicios").html(`
                    <div class="text-center p-3 text-danger">
                        ${data.mensaje ?? "Error al cargar servicios"}
                    </div>
                `);
            }
        })
        .catch(error => {
            console.error("Error al cargar servicios:", error);

            $("#listaServicios").html(`
                <div class="text-center p-3 text-danger">
                    Error al cargar servicios.
                </div>
            `);
        });
}

function pintarTablaServicios(lista) {
    let tabla = `
        <table class="table table-bordered table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre del servicio</th>
                    <th>Descripción</th>
                    <th>Precio</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
    `;

    if (lista.length === 0) {
        tabla += `
            <tr>
                <td colspan="6" class="text-center">
                    No hay servicios registrados.
                </td>
            </tr>
        `;
    } else {
        lista.forEach(servicio => {
            let objetoServicio = JSON.stringify(servicio).replace(/'/g, "&#39;");

            tabla += `
                <tr>
                    <td>${servicio.idServicio}</td>
                    <td>${servicio.nombreServicio}</td>
                    <td>${servicio.descripcion ?? ""}</td>
                    <td>$${servicio.precio}</td>
                    <td>
                        <span class="badge ${servicio.estado ? "bg-success" : "bg-danger"}">
                            ${servicio.estado ? "Activo" : "Inactivo"}
                        </span>
                    </td>
                    <td>
                        <button type="button" class="btn btn-warning btn-sm" onclick='editarServicio(${objetoServicio})'>
                            <i class="ti ti-edit"></i> Editar
                        </button>

                        <button type="button" class="btn btn-danger btn-sm" onclick="eliminarServicio(${servicio.idServicio})">
                            <i class="ti ti-trash"></i> Eliminar
                        </button>
                    </td>
                </tr>
            `;
        });
    }

    tabla += `
            </tbody>
        </table>
    `;

    $("#listaServicios").html(tabla);
}

function guardarServicio() {
    ocultarMensajeServicio();
    limpiarErroresServicio();

    if (condicionesServicio() === false) {
        return;
    }

    let servicio = {
        nombreServicio: $("#nombreServicio").val().trim(),
        descripcion: $("#descripcion").val().trim(),
        precio: parseFloat($("#precio").val()),
        estado: $("#estado").val() === "true"
    };

    let metodo = "POST";

    if (editandoServicio) {
        servicio.idServicio = parseInt($("#idServicio").val());
        metodo = "PUT";
    }

    fetch(window.APP_CONTEXT + "/ServicioServlet", {
        method: metodo,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(servicio)
    })
    .then(res => res.json())
    .then(data => {
        if (
            data.mensaje === "Servicio guardado correctamente" ||
            data.mensaje === "Servicio actualizado correctamente"
        ) {
            mostrarExitoServicio(data.mensaje);
            limpiarFormularioServicio();
            cargarServicios();
        } else {
            mostrarFracasoServicio(data.mensaje);
        }
    })
    .catch(error => {
        console.error("Error al guardar:", error);
        mostrarFracasoServicio("Error al guardar servicio.");
    });
}

function editarServicio(servicio) {
    editandoServicio = true;

    $("#idServicio").val(servicio.idServicio);
    $("#nombreServicio").val(servicio.nombreServicio);
    $("#descripcion").val(servicio.descripcion ?? "");
    $("#precio").val(servicio.precio);
    $("#estado").val(servicio.estado ? "true" : "false");

    $("#btnGuardarServicio").html(`
        <i class="ti ti-device-floppy"></i>
        Actualizar servicio
    `);

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}

function eliminarServicio(idServicio) {
    if (!confirm("¿Estás seguro de eliminar este servicio?")) {
        return;
    }

    fetch(window.APP_CONTEXT + "/ServicioServlet?idServicio=" + idServicio, {
        method: "DELETE"
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Servicio eliminado correctamente") {
            mostrarExitoServicio(data.mensaje);
            cargarServicios();
        } else {
            mostrarFracasoServicio(data.mensaje);
        }
    })
    .catch(error => {
        console.error("Error al eliminar:", error);
        mostrarFracasoServicio("Error al eliminar servicio.");
    });
}

function condicionesServicio() {
    let nombreServicio = $("#nombreServicio").val().trim();
    let descripcion = $("#descripcion").val().trim();
    let precio = $("#precio").val().trim();

    limpiarErroresServicio();

    if (nombreServicio === "") {
        marcarErrorServicio("#nombreServicio");
        mostrarFracasoServicio("El nombre del servicio no puede estar vacío");
        return false;
    }

    if (nombreServicio.length > 100) {
        marcarErrorServicio("#nombreServicio");
        mostrarFracasoServicio("El nombre del servicio no puede tener más de 100 caracteres");
        return false;
    }

    if (precio === "") {
        marcarErrorServicio("#precio");
        mostrarFracasoServicio("El precio no puede estar vacío");
        return false;
    }

    if (isNaN(precio) || parseFloat(precio) <= 0) {
        marcarErrorServicio("#precio");
        mostrarFracasoServicio("El precio debe ser mayor a cero");
        return false;
    }

    if (descripcion.length > 200) {
        marcarErrorServicio("#descripcion");
        mostrarFracasoServicio("La descripción no puede tener más de 200 caracteres");
        return false;
    }

    return true;
}

function limpiarFormularioServicio() {
    editandoServicio = false;

    $("#idServicio").val("");
    $("#nombreServicio").val("");
    $("#descripcion").val("");
    $("#precio").val("");
    $("#estado").val("true");

    $("#btnGuardarServicio").html(`
        <i class="ti ti-device-floppy"></i>
        Guardar servicio
    `);
}

function mostrarExitoServicio(mensaje) {
    $("#alertaServicio")
        .removeClass("alerta-error")
        .addClass("alerta-exito")
        .show();

    $("#mensajeAlertaServicio").text(mensaje);
}

function mostrarFracasoServicio(mensaje) {
    $("#alertaServicio")
        .removeClass("alerta-exito")
        .addClass("alerta-error")
        .show();

    $("#mensajeAlertaServicio").text(mensaje);
}

function ocultarMensajeServicio() {
    $("#alertaServicio").hide();
    $("#mensajeAlertaServicio").text("");
}

function marcarErrorServicio(campo) {
    $(campo).closest(".input-ic").addClass("input-error");
}

function limpiarErroresServicio() {
    $(".input-ic").removeClass("input-error");
}