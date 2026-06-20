/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let listaCompleta = [];
let paginaActual = 1;
const registrosPorPagina = 6;

let listaAlumnosCombo = [];
let listaServiciosCombo = [];

// ── CARGAR LISTA PRINCIPAL DESDE EL SERVLET ────────────────────────────
function cargarInscripciones() {
    fetch(window.APP_CONTEXT + "/InscripcionServlet")
        .then(res => res.json())
        .then(data => {
            listaCompleta = data;
            paginaActual = 1;
            renderPagina();
        })
        .catch(error => console.error("Error al cargar inscripciones:", error));
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
    $("#paginaInfo").text(`Mostrando ${desde}–${hasta} de ${listaCompleta.length} inscripciones`);
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
            <th>Acciones</th>
        </tr>`;

    if (lista.length === 0) {
        tabla += `<tr><td colspan="6" class="text-center">No hay registros de inscripciones activos.</td></tr>`;
    } else {
        lista.forEach(inscripcion => {
            let stringObjeto = JSON.stringify(inscripcion).replace(/'/g, "&#39;");
            tabla += `
            <tr>
                <td>${inscripcion.idInscripcion}</td>
                <td>${inscripcion.alumno}</td>
                <td>${inscripcion.servicio}</td>
                <td>${inscripcion.fechaInscripcion ?? ""}</td>
                <td><span class="badge ${inscripcion.estado ? 'bg-success' : 'bg-danger'}">${inscripcion.estado ? "Activo" : "Inactivo"}</span></td>
                <td>
                    <button class="btn-ic btn-sm" style="background-color: #ffc107; color: black; border: none; padding: 4px 8px; border-radius: 4px;" onclick='abrirEditar(${stringObjeto})'>
                        <i class="ti ti-edit"></i> Editar
                    </button>
                    <button class="btn-ic btn-sm btn-danger-ic" onclick="eliminarInscripcion(${inscripcion.idInscripcion})">
                        <i class="ti ti-trash"></i> Eliminar
                    </button>
                </td>
            </tr>`;
        });
    }

    tabla += "</table>";
    $("#listaInscripciones").html(tabla);
}

// ── COMBOS DINÁMICOS: ALUMNOS Y SERVICIOS ──────────────────────────────
function cargarCombosAlumnoServicio() {
    fetch(window.APP_CONTEXT + "/AlumnoServlet")
        .then(res => res.json())
        .then(data => {
            listaAlumnosCombo = data;
            llenarComboAlumnos("#idAlumno");
            llenarComboAlumnos("#editIdAlumno");
        })
        .catch(error => console.error("Error al cargar combos de alumnos:", error));

    fetch(window.APP_CONTEXT + "/ServicioServlet")
        .then(res => res.json())
        .then(data => {
            listaServiciosCombo = data;
            llenarComboServicios("#idServicio");
            llenarComboServicios("#editIdServicio");
        })
        .catch(error => console.error("Error al cargar combos de servicios:", error));
}

function llenarComboAlumnos(selector) {
    const $select = $(selector);
    $select.empty();
    $select.append(`<option value="">Seleccione un alumno</option>`);
    listaAlumnosCombo.forEach(alumno => {
        $select.append(`<option value="${alumno.idAlumno}">${alumno.nombre} ${alumno.apellido}</option>`);
    });
}

function llenarComboServicios(selector) {
    const $select = $(selector);
    $select.empty();
    $select.append(`<option value="">Seleccione un servicio</option>`);
    listaServiciosCombo.forEach(servicio => {
        $select.append(`<option value="${servicio.idServicio}">${servicio.nombreServicio}</option>`);
    });
}

// ── LOGICA MODAL/OVERLAY AGREGAR ───────────────────────────────────────
function cerrarOverlay() {
    $("#overlayInscripcion").hide();
    $("#formInscripcion")[0].reset();
    limpiarErrores();
    ocultarMensaje();
}

$("#btnAbrirFormInscripcion").on("click", function () {
    $("#overlayInscripcion").css("display", "flex");
});

$("#btnCerrarFormInscripcion").on("click", cerrarOverlay);

$("#btnLimpiarInscripcion").on("click", function () {
    $("#formInscripcion")[0].reset();
    limpiarErrores();
    ocultarMensaje();
});

$("#overlayInscripcion").on("click", function (e) {
    if ($(e.target).is("#overlayInscripcion")) cerrarOverlay();
});

// ── PROCESAR GUARDAR INSCRIPCIÓN (POST) ───────────────────────────────────
$("#formInscripcion").on("submit", function (e) {
    e.preventDefault();
    ocultarMensaje();
    if (!condicionesInscripcion()) return;

    let inscripcion = {
        idAlumno:   parseInt($("#idAlumno").val()),
        idServicio: parseInt($("#idServicio").val()),
        estado:     $("#estado").val() === "true"
    };

    fetch(window.APP_CONTEXT + "/InscripcionServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(inscripcion)
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Inscripción guardada correctamente") {
            mostrarExito(data.mensaje);
            $("#formInscripcion")[0].reset();
            cargarInscripciones();
            setTimeout(cerrarOverlay, 1500);
        } else {
            mostrarFracaso(data.mensaje);
        }
    })
    .catch(() => mostrarFracaso("Ocurrió un error en el servidor al guardar la inscripción"));
});

// ── LOGICA MODAL/OVERLAY EDITAR ────────────────────────────────────────
function abrirEditar(inscripcion) {
    $("#editIdInscripcion").val(inscripcion.idInscripcion);
    $("#editIdAlumno").val(inscripcion.idAlumno);
    $("#editIdServicio").val(inscripcion.idServicio);
    $("#editEstado").val(inscripcion.estado ? "true" : "false");
    $("#editFechaInscripcion").val(inscripcion.fechaInscripcion ?? "");
    $("#alertaEditar").hide();
    $("#overlayEditarInscripcion").css("display", "flex");
}

// Vinculaciones de cierre del modal de edición
function cerrarEditar() {
    $("#overlayEditarInscripcion").hide();
    $("#formEditarInscripcion")[0].reset();
    $("#alertaEditar").hide();
}

$("#btnCerrarEditarInscripcion").on("click", cerrarEditar);

$("#overlayEditarInscripcion").on("click", function (e) {
    if ($(e.target).is("#overlayEditarInscripcion")) cerrarEditar();
});

// ── PROCESAR MODIFICAR REGISTRO (PUT) ────────────────────────────────────
$("#formEditarInscripcion").on("submit", function (e) {
    e.preventDefault();

    let inscripcion = {
        idInscripcion:    parseInt($("#editIdInscripcion").val()),
        idAlumno:         parseInt($("#editIdAlumno").val()),
        idServicio:       parseInt($("#editIdServicio").val()),
        estado:           $("#editEstado").val() === "true",
        fechaInscripcion: $("#editFechaInscripcion").val()
    };

    fetch(window.APP_CONTEXT + "/InscripcionServlet", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(inscripcion)
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Inscripción actualizada correctamente") {
            cerrarEditar();
            cargarInscripciones();
        } else {
            $("#alertaEditar")
                .removeClass("alerta-exito").addClass("alerta-error")
                .html("<i class='ti ti-alert-triangle'></i> " + data.mensaje).show();
        }
    })
    .catch(() => {
        $("#alertaEditar")
            .removeClass("alerta-exito").addClass("alerta-error")
            .html("<i class='ti ti-alert-triangle'></i> Ocurrió un error al actualizar la inscripción").show();
    });
});

// ── OPERACIÓN ELIMINAR REGISTRO (DELETE) ─────────────────────────────────
function eliminarInscripcion(id) {
    if (!confirm("¿Estás seguro de que deseas eliminar esta inscripción?")) return;

    fetch(window.APP_CONTEXT + "/InscripcionServlet?idInscripcion=" + id, {
        method: "DELETE"
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Inscripción eliminada correctamente") {
            cargarInscripciones();
        } else {
            alert("Error: " + data.mensaje);
        }
    })
    .catch(() => alert("Ocurrió un error al eliminar la inscripción"));
}

// ── VALIDACIONES INTERNAS ──────────────────────────────────────────────
function condicionesInscripcion() {
    let idAlumno   = $("#idAlumno").val();
    let idServicio = $("#idServicio").val();

    limpiarErrores();

    if (idAlumno === "") {
        marcarError("#idAlumno");
        mostrarFracaso("Debe seleccionar un alumno");
        return false;
    }
    if (idServicio === "") {
        marcarError("#idServicio");
        mostrarFracaso("Debe seleccionar un servicio");
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
    $("#alertaInscripcion")
        .removeClass("alerta-error").addClass("alerta-exito")
        .show();
    $("#mensajeAlertaInscripcion").text(mensaje);
}

function mostrarFracaso(mensaje) {
    $("#alertaInscripcion")
        .removeClass("alerta-exito").addClass("alerta-error")
        .show();
    $("#mensajeAlertaInscripcion").text(mensaje);
}

function ocultarMensaje() {
    $("#alertaInscripcion").hide();
    $("#mensajeAlertaInscripcion").text("");
}

// Inicialización
$(document).ready(function() {
    cargarInscripciones();
    cargarCombosAlumnoServicio();
});