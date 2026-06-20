let listaCompleta = [];
let paginaActual = 1;
const registrosPorPagina = 6;

// ── CARGAR LISTA PRINCIPAL DESDE EL SERVLET ────────────────────────────
function cargarAlumnos() {
    fetch(window.APP_CONTEXT + "/AlumnoServlet")
        .then(res => res.json())
        .then(pintarTabla)
        .catch(error => {
            console.error("Error al cargar alumnos:", error);
        });

        .then(data => {
            listaCompleta = data;
            paginaActual = 1;
            renderPagina();
        })
        .catch(error => console.error("Error al cargar alumnos:", error));
}

function renderPagina() {
    const totalPaginas = Math.ceil(listaCompleta.length / registrosPorPagina);
    const inicio = (paginaActual - 1) * registrosPorPagina;
    const fin = inicio + registrosPorPagina;
    const paginados = listaCompleta.slice(inicio, fin);

    pintarTabla(paginados);
    actualizarPaginacion(totalPaginas);

    const desde = listaCompleta.length === 0 ? 0 : inicio + 1;
    const hasta = Math.min(fin, listaCompleta.length);
    $("#paginaInfo").text(`Mostrando ${desde}–${hasta} de ${listaCompleta.length} alumnos`);
}

function actualizarPaginacion(totalPaginas) {
    const $btns = $(".pag-btns");
    $btns.empty();

    $btns.append(`
        <button class="pag-btn" id="btnAnterior" ${paginaActual === 1 ? "disabled" : ""}>
            <i class="ti ti-chevron-left"></i>
        </button>
    `);

    for (let i = 1; i <= totalPaginas; i++) {
        $btns.append(`
            <button class="pag-btn ${i === paginaActual ? "active" : ""}" data-pagina="${i}">
                ${i}
            </button>
        `);
    }

    $btns.append(`
        <button class="pag-btn" id="btnSiguiente" ${paginaActual === totalPaginas || totalPaginas === 0 ? "disabled" : ""}>
            <i class="ti ti-chevron-right"></i>
        </button>
    `);

    $("#btnAnterior").off("click").on("click", function () {
        if (paginaActual > 1) {
            paginaActual--;
            renderPagina();
        }
    });

    $("#btnSiguiente").off("click").on("click", function () {
        if (paginaActual < totalPaginas) {
            paginaActual++;
            renderPagina();
        }
    });

    $(".pag-btn[data-pagina]").off("click").on("click", function () {
        paginaActual = parseInt($(this).data("pagina"));
        renderPagina();
    });
>>>>>>> origin/master
}

function pintarTabla(lista) {
<<<<<<< HEAD

    if ($("#listaAlumnos").length === 0) {
        return;
    }

=======
>>>>>>> origin/master
    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Teléfono</th>
            <th>Dirección</th>
            <th>Estado</th>
            <th>Acciones</th>
        </tr>`;

<<<<<<< HEAD
    lista.forEach(alumno => {
        tabla += `
        <tr>
            <td>${alumno.idAlumno}</td>
            <td>${alumno.nombre}</td>
            <td>${alumno.apellido}</td>
            <td>${alumno.telefono ?? ""}</td>
            <td>${alumno.direccion ?? ""}</td>
            <td>${alumno.fechaRegistro ?? ""}</td>
            <td>${alumno.estado === true ? "Activo" : "Inactivo"}</td>
        </tr>`;
    });
=======
    if (lista.length === 0) {
        tabla += `<tr><td colspan="7" class="text-center">No hay registros de alumnos activos.</td></tr>`;
    } else {
        lista.forEach(alumno => {
            let stringObjeto = JSON.stringify(alumno).replace(/'/g, "&#39;");
            tabla += `
            <tr>
                <td>${alumno.idAlumno}</td>
                <td>${alumno.nombre}</td>
                <td>${alumno.apellido}</td>
                <td>${alumno.telefono ?? ""}</td>
                <td>${alumno.direccion ?? ""}</td>
                <td><span class="badge ${alumno.estado ? 'bg-success' : 'bg-danger'}">${alumno.estado ? "Activo" : "Inactivo"}</span></td>
                <td>
                    <button class="btn-ic btn-sm" style="background-color: #ffc107; color: black; border: none; padding: 4px 8px; border-radius: 4px;" onclick='abrirEditar(${stringObjeto})'>
                        <i class="ti ti-edit"></i> Editar
                    </button>
                    <button class="btn-ic btn-sm btn-danger-ic" onclick="eliminarAlumno(${alumno.idAlumno})">
                        <i class="ti ti-trash"></i> Eliminar
                    </button>
                </td>
            </tr>`;
        });
    }
>>>>>>> origin/master

    tabla += "</table>";
    $("#listaAlumnos").html(tabla);
}

<<<<<<< HEAD

// GUARDAR ALUMNO
$("#mainContent").submit(function (e) {
    e.preventDefault();

    ocultarAdvertencia();

    if (condicionesAlumno() === false) {
        return;
    }

   let fechaActual = new Date().toISOString().split("T")[0];

    let alumno = {
    nombre: $("#nombre").val().trim(),
    apellido: $("#apellido").val().trim(),
    telefono: $("#telefono").val().trim(),
    direccion: $("#direccion").val().trim(),
    estado: $("#estado").val() === "true"
};

    fetch("AlumnoServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(alumno)
    })
    .then(res => res.json())
    .then(data => {

        if (data.mensaje === "Alumno guardado correctamente") {
            mostrarExito(data.mensaje);
            $("#formAlumno")[0].reset();

            if ($("#listaAlumnos").length > 0) {
                cargarAlumnos();
            }

        } else {
            mostrarAdvertencia(data.mensaje);
        }
    })
    .catch(error => {
        console.error("Error:", error);
        mostrarAdvertencia("Ocurrió un error al guardar el alumno");
    });
});


function condicionesAlumno() {

    let nombre = $("#nombre").val().trim();
    let apellido = $("#apellido").val().trim();
    let telefono = $("#telefono").val().trim();
    let direccion = $("#direccion").val().trim();

    limpiarErrores();

    if (nombre === "") {
        marcarError("#nombre");
        mostrarAdvertencia("El nombre no puede estar vacío");
        return false;
    }

    if (apellido === "") {
        marcarError("#apellido");
        mostrarAdvertencia("El apellido no puede estar vacío");
        return false;
    }

    if (telefono !== "") {
        let expresionTelefono = /^[0-9\-+() ]{7,20}$/;

        if (!expresionTelefono.test(telefono)) {
            marcarError("#telefono");
            mostrarAdvertencia("Formato de teléfono inválido. Ejemplo válido: 7777-7777");
            return false;
        }
    }

    if (direccion.length > 150) {
        marcarError("#direccion");
        mostrarAdvertencia("La dirección no puede tener más de 150 caracteres");
        return false;
    }

    return true;
}


// ADVERTENCIAS VISUALES
function mostrarAdvertencia(mensaje) {
    $("#alertaAlumno")
        .removeClass("alerta-exito")
        .addClass("alerta-error")
        .show();

    $("#mensajeAlertaAlumno").text(mensaje);
}

function mostrarExito(mensaje) {
    $("#alertaAlumno")
        .removeClass("alerta-error")
        .addClass("alerta-exito")
        .show();

    $("#mensajeAlertaAlumno").text(mensaje);
}

function ocultarAdvertencia() {
    $("#alertaAlumno").hide();
    $("#mensajeAlertaAlumno").text("");
}

function marcarError(campo) {
    $(campo).closest(".input-ic").addClass("input-error");
}

function limpiarErrores() {
    $(".input-ic").removeClass("input-error");
}


// Solo carga alumnos si la página tiene el div listaAlumnos
if ($("#listaAlumnos").length > 0) {
    cargarAlumnos();
}
=======
// ── LOGICA MODAL/OVERLAY AGREGAR ───────────────────────────────────────
function cerrarOverlay() {
    $("#overlayAlumno").hide();
    $("#formAlumno")[0].reset();
    limpiarErrores();
    ocultarMensaje();
}

$("#btnAbrirFormAlumno").on("click", function () {
    $("#overlayAlumno").css("display", "flex");
});

$("#btnCerrarFormAlumno").on("click", cerrarOverlay);

$("#btnLimpiarAlumno").on("click", function () {
    $("#formAlumno")[0].reset();
    limpiarErrores();
    ocultarMensaje();
});

$("#overlayAlumno").on("click", function (e) {
    if ($(e.target).is("#overlayAlumno")) cerrarOverlay();
});

// ── PROCESAR GUARDAR ALUMNO (POST) ───────────────────────────────────────
$("#formAlumno").on("submit", function (e) {
    e.preventDefault();
    ocultarMensaje();
    if (!condicionesAlumno()) return;

    let alumno = {
        nombre:    $("#nombre").val().trim(),
        apellido:  $("#apellido").val().trim(),
        telefono:  $("#telefono").val().trim(),
        estado:    $("#estado").val() === "true",
        direccion: $("#direccion").val().trim()
    };

    fetch(window.APP_CONTEXT + "/AlumnoServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(alumno)
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Alumno guardado correctamente") {
            mostrarExito(data.mensaje);
            $("#formAlumno")[0].reset();
            cargarAlumnos();
            setTimeout(cerrarOverlay, 1500);
        } else {
            mostrarFracaso(data.mensaje);
        }
    })
    .catch(() => mostrarFracaso("Ocurrió un error en el servidor al guardar al alumno"));
});

// ── LOGICA MODAL/OVERLAY EDITAR ────────────────────────────────────────
function abrirEditar(alumno) {
    $("#editIdAlumno").val(alumno.idAlumno);
    $("#editNombre").val(alumno.nombre);
    $("#editApellido").val(alumno.apellido);
    $("#editTelefono").val(alumno.telefono ?? "");
    $("#editEstado").val(alumno.estado ? "true" : "false");
    $("#editDireccion").val(alumno.direccion ?? "");
    $("#editFechaRegistro").val(alumno.fechaRegistro ?? "");
    $("#alertaEditar").hide();
    $("#overlayEditarAlumno").css("display", "flex");
}

function cerrarEditar() {
    $("#overlayEditarAlumno").hide();
    $("#formEditarAlumno")[0].reset();
    $("#alertaEditar").hide();
}

$("#btnCerrarEditarAlumno").on("click", cerrarEditar);

$("#overlayEditarAlumno").on("click", function (e) {
    if ($(e.target).is("#overlayEditarAlumno")) cerrarEditar();
});

// ── PROCESAR MODIFICAR REGISTRO (PUT) ────────────────────────────────────
$("#formEditarAlumno").on("submit", function (e) {
    e.preventDefault();

    let alumno = {
        idAlumno:      parseInt($("#editIdAlumno").val()),
        nombre:        $("#editNombre").val().trim(),
        apellido:      $("#editApellido").val().trim(),
        telefono:      $("#editTelefono").val().trim(),
        estado:        $("#editEstado").val() === "true",
        direccion:     $("#editDireccion").val().trim(),
        fechaRegistro: $("#editFechaRegistro").val()
    };

    fetch(window.APP_CONTEXT + "/AlumnoServlet", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(alumno)
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Alumno actualizado correctamente") {
            cerrarEditar();
            cargarAlumnos();
        } else {
            $("#alertaEditar")
                .removeClass("alerta-exito").addClass("alerta-error")
                .html("<i class='ti ti-alert-triangle'></i> " + data.mensaje).show();
        }
    })
    .catch(() => {
        $("#alertaEditar")
            .removeClass("alerta-exito").addClass("alerta-error")
            .html("<i class='ti ti-alert-triangle'></i> Ocurrió un error al actualizar el alumno").show();
    });
});

// ── OPERACIÓN ELIMINAR REGISTRO (DELETE) ─────────────────────────────────
function eliminarAlumno(id) {
    if (!confirm("¿Estás seguro de que deseas eliminar este alumno?")) return;

    fetch(window.APP_CONTEXT + "/AlumnoServlet?idAlumno=" + id, {
        method: "DELETE"
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Alumno eliminado correctamente") {
            cargarAlumnos();
        } else {
            alert("Error: " + data.mensaje);
        }
    })
    .catch(() => alert("Ocurrió un error al eliminar el alumno"));
}

// ── VALIDACIONES INTERNAS ──────────────────────────────────────────────
function condicionesAlumno() {
    let nombre   = $("#nombre").val().trim();
    let apellido = $("#apellido").val().trim();

    limpiarErrores();

    if (nombre === "") {
        marcarError("#nombre");
        mostrarFracaso("Debe ingresar el nombre del alumno");
        return false;
    }
    if (apellido === "") {
        marcarError("#apellido");
        mostrarFracaso("Debe ingresar el apellido del alumno");
        return false;
    }
    return true;
}

function marcarError(campo) {
    $(campo).closest(".input-ic").addClass("input-error");
}

function limpiarErrores() {
    $(".input-ic").removeClass("input-error");
}

// ── MANEJO VISUAL DE ALERTAS ───────────────────────────────────────────
function mostrarExito(mensaje) {
    $("#alertaAlumno")
        .removeClass("alerta-error").addClass("alerta-exito")
        .show();
    $("#mensajeAlertaAlumno").text(mensaje);
}

function mostrarFracaso(mensaje) {
    $("#alertaAlumno")
        .removeClass("alerta-exito").addClass("alerta-error")
        .show();
    $("#mensajeAlertaAlumno").text(mensaje);
}

function ocultarMensaje() {
    $("#alertaAlumno").hide();
    $("#mensajeAlertaAlumno").text("");
}

// Inicialización
$(document).ready(function() {
    cargarAlumnos();
});
>>>>>>> origin/master
