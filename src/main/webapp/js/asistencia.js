let listaCompleta = [];
let paginaActual = 1;
const registrosPorPagina = 6;

let listaAlumnosCombo = [];
let listaServiciosCombo = [];
let listaPagosCombo = [];

function cargarAsistencias() {
    fetch(window.APP_CONTEXT + "/AsistenciaServlet")
        .then(res => res.json())
        .then(data => {
            listaCompleta = data;
            paginaActual = 1;
            renderPagina();
        })
        .catch(error => console.error("Error al cargar asistencias:", error));
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
    $("#paginaInfo").text(`Mostrando ${desde}–${hasta} de ${listaCompleta.length} asistencias`);
}

function actualizarPaginacion(totalPaginas) {
    const $btns = $(".pag-btns");
    $btns.empty();

    $btns.append(`<button class="pag-btn" id="btnAnterior" ${paginaActual === 1 ? "disabled" : ""}><i class="ti ti-chevron-left"></i></button>`);
    for (let i = 1; i <= totalPaginas; i++) {
        $btns.append(`<button class="pag-btn ${i === paginaActual ? "active" : ""}" data-pagina="${i}">${i}</button>`);
    }
    $btns.append(`<button class="pag-btn" id="btnSiguiente" ${paginaActual === totalPaginas || totalPaginas === 0 ? "disabled" : ""}><i class="ti ti-chevron-right"></i></button>`);

    $("#btnAnterior").off("click").on("click", function () { if (paginaActual > 1) { paginaActual--; renderPagina(); } });
    $("#btnSiguiente").off("click").on("click", function () { if (paginaActual < totalPaginas) { paginaActual++; renderPagina(); } });
    $(".pag-btn[data-pagina]").off("click").on("click", function () { paginaActual = parseInt($(this).data("pagina")); renderPagina(); });
}

function pintarTabla(lista) {
    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Alumno</th>
            <th>Servicio</th>
            <th>Referencia Pago</th>
            <th>Fecha</th>
            <th>Hora Entrada</th>
            <th>Hora Salida</th>
            <th>Acciones</th>
        </tr>`;

    if (lista.length === 0) {
        tabla += `<tr><td colspan="8" class="text-center">No hay registros de asistencias activos.</td></tr>`;
    } else {
        lista.forEach(asis => {
            let stringObjeto = JSON.stringify(asis).replace(/'/g, "&#39;");
            tabla += `
            <tr>
                <td>${asis.idAsistencia}</td>
                <td>${asis.alumno}</td>
                <td>${asis.servicio}</td>
                <td><span class="badge bg-secondary">${asis.pago}</span></td>
                <td>${asis.fechaAsistencia ?? ""}</td>
                <td>${asis.horaEntrada ?? ""}</td>
                <td>${asis.horaSalida ?? "En sala"}</td>
                <td>
                    <button class="btn-ic btn-sm" style="background-color: #ffc107; color: black; border: none; padding: 4px 8px; border-radius: 4px;" onclick='abrirEditar(${stringObjeto})'>
                        <i class="ti ti-edit"></i> Editar
                    </button>
                    <button class="btn-ic btn-sm btn-danger-ic" onclick="eliminarAsistencia(${asis.idAsistencia})">
                        <i class="ti ti-trash"></i> Eliminar
                    </button>
                </td>
            </tr>`;
        });
    }
    tabla += "</table>";
    $("#listaAsistencias").html(tabla);
}

function cargarCombosAsistencia() {
    fetch(window.APP_CONTEXT + "/AlumnoServlet").then(res => res.json()).then(data => { listaAlumnosCombo = data; llenarCombo(data, "#idAlumno", "idAlumno", "nombre", "apellido"); llenarCombo(data, "#editIdAlumno", "idAlumno", "nombre", "apellido"); });
    fetch(window.APP_CONTEXT + "/ServicioServlet").then(res => res.json()).then(data => { listaServiciosCombo = data; llenarCombo(data, "#idServicio", "idServicio", "nombreServicio"); llenarCombo(data, "#editIdServicio", "idServicio", "nombreServicio"); });
    fetch(window.APP_CONTEXT + "/PagoServlet").then(res => res.json()).then(data => { listaPagosCombo = data; llenarCombo(data, "#idPago", "idPago", "idPago"); llenarCombo(data, "#editIdPago", "idPago", "idPago"); });
}

function llenarCombo(data, selector, idKey, labelKey, optionalKey = "") {
    const $select = $(selector).empty().append(`<option value="">Seleccione una opción</option>`);
    data.forEach(item => {
        let text = optionalKey ? `${item[labelKey]} ${item[optionalKey]}` : (idKey === "idPago" ? `Recibo de Pago #${item[labelKey]}` : item[labelKey]);
        $select.append(`<option value="${item[idKey]}">${text}</option>`);
    });
}

function cerrarOverlay() { $("#overlayAsistencia").hide(); $("#formAsistencia")[0].reset(); limpiarErrores(); ocultarMensaje(); }
$("#btnAbrirFormAsistencia").on("click", function () { $("#overlayAsistencia").css("display", "flex"); });
$("#btnCerrarFormAsistencia").on("click", cerrarOverlay);
$("#overlayAsistencia").on("click", function (e) { if ($(e.target).is("#overlayAsistencia")) cerrarOverlay(); });

$("#formAsistencia").on("submit", function (e) {
    e.preventDefault();
    if ($("#idAlumno").val() === "" || $("#idServicio").val() === "" || $("#idPago").val() === "") { alert("Todos los selectores son obligatorios"); return; }
    
    let asistencia = {
        idAlumno: parseInt($("#idAlumno").val()),
        idServicio: parseInt($("#idServicio").val()),
        idPago: parseInt($("#idPago").val()),
        horaEntrada: $("#horaEntrada").val(),
        horaSalida: $("#horaSalida").val()
    };

    fetch(window.APP_CONTEXT + "/AsistenciaServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(asistencia)
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje.includes("correctamente")) {
            mostrarExito(data.mensaje);
            cerrarOverlay();
            cargarAsistencias();
        } else { mostrarFracaso(data.mensaje); }
    });
});

function abrirEditar(asis) {
    $("#editIdAsistencia").val(asis.idAsistencia);
    $("#editIdAlumno").val(asis.idAlumno);
    $("#editIdServicio").val(asis.idServicio);
    $("#editIdPago").val(asis.idPago);
    $("#editFechaAsistencia").val(asis.fechaAsistencia);
    $("#editHoraEntrada").val(asis.horaEntrada);
    $("#editHoraSalida").val(asis.horaSalida ?? "");
    $("#overlayEditarAsistencia").css("display", "flex");
}

function cerrarEditar() { $("#overlayEditarAsistencia").hide(); }
$("#btnCerrarEditarAsistencia").on("click", cerrarEditar);

$("#formEditarAsistencia").on("submit", function (e) {
    e.preventDefault();
    let asis = {
        idAsistencia: parseInt($("#editIdAsistencia").val()),
        idAlumno: parseInt($("#editIdAlumno").val()),
        idServicio: parseInt($("#editIdServicio").val()),
        idPago: parseInt($("#editIdPago").val()),
        fechaAsistencia: $("#editFechaAsistencia").val(),
        horaEntrada: $("#editHoraEntrada").val(),
        horaSalida: $("#editHoraSalida").val()
    };

    fetch(window.APP_CONTEXT + "/AsistenciaServlet", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(asis)
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje.includes("correctamente")) { cerrarEditar(); cargarAsistencias(); } 
        else { alert(data.mensaje); }
    });
});

function eliminarAsistencia(id) {
    if (!confirm("¿Estás seguro de que deseas eliminar este registro de asistencia?")) return;
    fetch(window.APP_CONTEXT + "/AsistenciaServlet?idAsistencia=" + id, { method: "DELETE" })
    .then(res => res.json()).then(data => { cargarAsistencias(); });
}

function marcarError(campo) { $(campo).closest(".input-ic").addClass("input-error"); }
function limpiarErrores() { $(".input-ic").removeClass("input-error"); }
function mostrarExito(m) { $("#alertaAsistencia").removeClass("alerta-error").addClass("alerta-exito").show(); $("#mensajeAlertaAsistencia").text(m); }
function mostrarFracaso(m) { $("#alertaAsistencia").removeClass("alerta-exito").addClass("alerta-error").show(); $("#mensajeAlertaAsistencia").text(m); }
function ocultarMensaje() { $("#alertaAsistencia").hide(); }

$(document).ready(function() {
    cargarAsistencias();
    cargarCombosAsistencia();
});