let listaCompleta = [];
let paginaActual = 1;
const registrosPorPagina = 6;

let listaAlumnosCombo = [];
let listaUsuariosCombo = [];
let listaInscripcionesCombo = [];

function cargarAtenciones() {
    fetch(window.APP_CONTEXT + "/AtencionServlet")
        .then(res => res.json())
        .then(data => {
            listaCompleta = data;
            paginaActual = 1;
            renderPagina();
        })
        .catch(error => console.error("Error al cargar atenciones:", error));
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
    $("#paginaInfo").text(`Mostrando ${desde}–${hasta} de ${listaCompleta.length} atenciones`);
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
            <th>Atendido Por</th>
            <th>Inscripción</th>
            <th>Fecha</th>
            <th>Tipo</th>
            <th>Descripción</th>
            <th>Acciones</th>
        </tr>`;

    if (lista.length === 0) {
        tabla += `<tr><td colspan="8" class="text-center">No hay registros de atenciones activos.</td></tr>`;
    } else {
        lista.forEach(aten => {
            let stringObjeto = JSON.stringify(aten).replace(/'/g, "&#39;");
            tabla += `
            <tr>
                <td>${aten.idAtencion}</td>
                <td>${aten.alumno}</td>
                <td>${aten.usuario}</td>
                <td><span class="badge bg-info text-dark">${aten.inscripcion}</span></td>
                <td>${aten.fechaAtencion ?? ""}</td>
                <td><span class="badge bg-secondary">${aten.tipoAtencion ?? "General"}</span></td>
                <td>${aten.descripcion ?? ""}</td>
                <td>
                    <button class="btn-ic btn-sm" style="background-color: #ffc107; color: black; border: none; padding: 4px 8px; border-radius: 4px;" onclick='abrirEditar(${stringObjeto})'>
                        <i class="ti ti-edit"></i> Editar
                    </button>
                    <button class="btn-ic btn-sm btn-danger-ic" onclick="eliminarAtencion(${aten.idAtencion})">
                        <i class="ti ti-trash"></i> Eliminar
                    </button>
                </td>
            </tr>`;
        });
    }
    tabla += "</table>";
    $("#listaAtenciones").html(tabla);
}

function cargarCombosAtencion() {
    fetch(window.APP_CONTEXT + "/AlumnoServlet").then(res => res.json()).then(data => { listaAlumnosCombo = data; llenarCombo(data, "#idAlumno", "idAlumno", "nombre", "apellido"); llenarCombo(data, "#editIdAlumno", "idAlumno", "nombre", "apellido"); });
    fetch(window.APP_CONTEXT + "/UsuarioServlet").then(res => res.json()).then(data => { listaUsuariosCombo = data; llenarCombo(data, "#idUsuario", "idUsuario", "nombre"); llenarCombo(data, "#editIdUsuario", "idUsuario", "nombre"); });
    fetch(window.APP_CONTEXT + "/InscripcionServlet").then(res => res.json()).then(data => { listaInscripcionesCombo = data; llenarCombo(data, "#idInscripcion", "idInscripcion", "idInscripcion"); llenarCombo(data, "#editIdInscripcionRow", "idInscripcion", "idInscripcion"); });
}

function llenarCombo(data, selector, idKey, labelKey, optionalKey = "") {
    const $select = $(selector).empty().append(`<option value="">Seleccione una opción</option>`);
    data.forEach(item => {
        let text = optionalKey ? `${item[labelKey]} ${item[optionalKey]}` : (idKey === "idInscripcion" ? `Folio de Inscripción #${item[labelKey]}` : item[labelKey]);
        $select.append(`<option value="${item[idKey]}">${text}</option>`);
    });
}

function cerrarOverlay() { $("#overlayAtencion").hide(); $("#formAtencion")[0].reset(); ocultarMensaje(); }
$("#btnAbrirFormAtencion").on("click", function () { $("#overlayAtencion").css("display", "flex"); });
$("#btnCerrarFormAtencion").on("click", cerrarOverlay);
$("#overlayAtencion").on("click", function (e) { if ($(e.target).is("#overlayAtencion")) cerrarOverlay(); });

$("#formAtencion").on("submit", function (e) {
    e.preventDefault();
    if (!$("#idAlumno").val() || !$("#idUsuario").val() || !$("#idInscripcion").val()) { alert("Por favor complete las asociaciones requeridas"); return; }
    
    let atencion = {
        idAlumno: parseInt($("#idAlumno").val()),
        idUsuario: parseInt($("#idUsuario").val()),
        idInscripcion: parseInt($("#idInscripcion").val()),
        tipoAtencion: $("#tipoAtencion").val(),
        descripcion: $("#descripcion").val()
    };

    fetch(window.APP_CONTEXT + "/AtencionServlet", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(atencion)
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje.includes("correctamente")) { cerrarOverlay(); cargarAtenciones(); } 
        else { alert(data.mensaje); }
    });
});

function abrirEditar(aten) {
    $("#editIdAtencion").val(aten.idAtencion);
    $("#editIdAlumno").val(aten.idAlumno);
    $("#editIdUsuario").val(aten.idUsuario);
    $("#editIdInscripcionRow").val(aten.idInscripcion);
    $("#editFechaAtencion").val(aten.fechaAtencion);
    $("#editTipoAtencion").val(aten.tipoAtencion);
    $("#editDescripcion").val(aten.descripcion);
    $("#overlayEditarAtencion").css("display", "flex");
}

function cerrarEditar() { $("#overlayEditarAtencion").hide(); }
$("#btnCerrarEditarAtencion").on("click", cerrarEditar);

$("#formEditarAtencion").on("submit", function (e) {
    e.preventDefault();
    let aten = {
        idAtencion: parseInt($("#editIdAtencion").val()),
        idAlumno: parseInt($("#editIdAlumno").val()),
        idUsuario: parseInt($("#editIdUsuario").val()),
        idInscripcion: parseInt($("#editIdInscripcionRow").val()),
        fechaAtencion: $("#editFechaAtencion").val(),
        tipoAtencion: $("#editTipoAtencion").val(),
        descripcion: $("#editDescripcion").val()
    };

    fetch(window.APP_CONTEXT + "/AtencionServlet", {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(aten)
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje.includes("correctamente")) { cerrarEditar(); cargarAtenciones(); } 
        else { alert(data.mensaje); }
    });
});

function eliminarAtencion(id) {
    if (!confirm("¿Deseas eliminar este registro de atención?")) return;
    fetch(window.APP_CONTEXT + "/AtencionServlet?idAtencion=" + id, { method: "DELETE" })
    .then(res => res.json()).then(() => { cargarAtenciones(); });
}

function ocultarMensaje() { $("#alertaAtencion").hide(); }

$(document).ready(function() {
    cargarAtenciones();
    cargarCombosAtencion();
});