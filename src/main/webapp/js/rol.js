let editandoRol = false;

$(document).ready(function () {
    console.log("rol.js cargado correctamente");

    cargarRoles();

    $("#formRol").on("submit", function (e) {
        e.preventDefault();
        guardarRol();
    });

    $("#formRol").on("reset", function () {
        setTimeout(function () {
            limpiarFormularioRol();
            ocultarMensajeRol();
            limpiarErroresRol();
        }, 50);
    });
});

function cargarRoles() {
    fetch(window.APP_CONTEXT + "/RolServlet")
        .then(res => res.json())
        .then(data => {
            console.log("Roles recibidos:", data);

            if (Array.isArray(data)) {
                pintarTablaRoles(data);
            } else {
                $("#listaRoles").html(`
                    <div class="text-center p-3 text-danger">
                        ${data.mensaje ?? "Error al cargar roles"}
                    </div>
                `);
            }
        })
        .catch(error => {
            console.error("Error al cargar roles:", error);

            $("#listaRoles").html(`
                <div class="text-center p-3 text-danger">
                    Error al cargar roles.
                </div>
            `);
        });
}

function pintarTablaRoles(lista) {
    let tabla = `
        <table class="table table-bordered table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre del rol</th>
                    <th>Descripción</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
    `;

    if (lista.length === 0) {
        tabla += `
            <tr>
                <td colspan="5" class="text-center">
                    No hay roles registrados.
                </td>
            </tr>
        `;
    } else {
        lista.forEach(rol => {
            let objetoRol = JSON.stringify(rol).replace(/'/g, "&#39;");

            tabla += `
                <tr>
                    <td>${rol.idRol}</td>
                    <td>${rol.nombreRol}</td>
                    <td>${rol.descripcion ?? ""}</td>
                    <td>
                        <span class="badge ${rol.estado ? "bg-success" : "bg-danger"}">
                            ${rol.estado ? "Activo" : "Inactivo"}
                        </span>
                    </td>
                    <td>
                        <button type="button" class="btn btn-warning btn-sm" onclick='editarRol(${objetoRol})'>
                            <i class="ti ti-edit"></i> Editar
                        </button>

                        <button type="button" class="btn btn-danger btn-sm" onclick="eliminarRol(${rol.idRol})">
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

    $("#listaRoles").html(tabla);
}

function guardarRol() {
    ocultarMensajeRol();
    limpiarErroresRol();

    if (condicionesRol() === false) {
        return;
    }

    let rol = {
        nombreRol: $("#nombreRol").val().trim(),
        descripcion: $("#descripcion").val().trim(),
        estado: $("#estado").val() === "true"
    };

    let metodo = "POST";

    if (editandoRol) {
        rol.idRol = parseInt($("#idRol").val());
        metodo = "PUT";
    }

    fetch(window.APP_CONTEXT + "/RolServlet", {
        method: metodo,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(rol)
    })
    .then(res => res.json())
    .then(data => {
        if (
            data.mensaje === "Rol guardado correctamente" ||
            data.mensaje === "Rol actualizado correctamente"
        ) {
            mostrarExitoRol(data.mensaje);
            limpiarFormularioRol();
            cargarRoles();
        } else {
            mostrarFracasoRol(data.mensaje);
        }
    })
    .catch(error => {
        console.error("Error al guardar:", error);
        mostrarFracasoRol("Error al guardar rol.");
    });
}

function editarRol(rol) {
    editandoRol = true;

    $("#idRol").val(rol.idRol);
    $("#nombreRol").val(rol.nombreRol);
    $("#descripcion").val(rol.descripcion ?? "");
    $("#estado").val(rol.estado ? "true" : "false");

    $("#btnGuardarRol").html(`
        <i class="ti ti-device-floppy"></i>
        Actualizar rol
    `);

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}

function eliminarRol(idRol) {
    if (!confirm("¿Estás seguro de eliminar este rol?")) {
        return;
    }

    fetch(window.APP_CONTEXT + "/RolServlet?idRol=" + idRol, {
        method: "DELETE"
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Rol eliminado correctamente") {
            mostrarExitoRol(data.mensaje);
            cargarRoles();
        } else {
            mostrarFracasoRol(data.mensaje);
        }
    })
    .catch(error => {
        console.error("Error al eliminar:", error);
        mostrarFracasoRol("Error al eliminar rol.");
    });
}

function condicionesRol() {
    let nombreRol = $("#nombreRol").val().trim();
    let descripcion = $("#descripcion").val().trim();

    limpiarErroresRol();

    if (nombreRol === "") {
        marcarErrorRol("#nombreRol");
        mostrarFracasoRol("El nombre del rol no puede estar vacío");
        return false;
    }

    if (nombreRol.length > 50) {
        marcarErrorRol("#nombreRol");
        mostrarFracasoRol("El nombre del rol no puede tener más de 50 caracteres");
        return false;
    }

    if (descripcion.length > 150) {
        marcarErrorRol("#descripcion");
        mostrarFracasoRol("La descripción no puede tener más de 150 caracteres");
        return false;
    }

    return true;
}

function limpiarFormularioRol() {
    editandoRol = false;

    $("#idRol").val("");
    $("#nombreRol").val("");
    $("#descripcion").val("");
    $("#estado").val("true");

    $("#btnGuardarRol").html(`
        <i class="ti ti-device-floppy"></i>
        Guardar rol
    `);
}

function mostrarExitoRol(mensaje) {
    $("#alertaRol")
        .removeClass("alerta-error")
        .addClass("alerta-exito")
        .show();

    $("#mensajeAlertaRol").text(mensaje);
}

function mostrarFracasoRol(mensaje) {
    $("#alertaRol")
        .removeClass("alerta-exito")
        .addClass("alerta-error")
        .show();

    $("#mensajeAlertaRol").text(mensaje);
}

function ocultarMensajeRol() {
    $("#alertaRol").hide();
    $("#mensajeAlertaRol").text("");
}

function marcarErrorRol(campo) {
    $(campo).closest(".input-ic").addClass("input-error");
}

function limpiarErroresRol() {
    $(".input-ic").removeClass("input-error");
}