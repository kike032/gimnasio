// ── PAGINACIÓN ─────────────────────────────────────────────────────

let listaCompleta = [];
let paginaActual = 1;
const registrosPorPagina = 6;

function cargarAlumnos() {
    fetch("AlumnoServlet")
        .then(res => res.json())
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

    $("#btnAnterior").on("click", function () {
        if (paginaActual > 1) { paginaActual--; renderPagina(); }
    });

    $("#btnSiguiente").on("click", function () {
        if (paginaActual < totalPaginas) { paginaActual++; renderPagina(); }
    });

    $(".pag-btn[data-pagina]").on("click", function () {
        paginaActual = parseInt($(this).data("pagina"));
        renderPagina();
    });
}

function pintarTabla(lista) {
    let tabla = `
    <table class="table">
        <thead>
            <tr>
                <th>#</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Teléfono</th>
                <th>Dirección</th>
                <th>Fecha Registro</th>
                <th>Estado</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>`;

    lista.forEach(alumno => {
        const badgeEstado = alumno.estado
            ? `<span class="badge badge-activo">Activo</span>`
            : `<span class="badge badge-inactivo">Inactivo</span>`;

        tabla += `
        <tr>
            <td>${alumno.idAlumno}</td>
            <td>${alumno.nombre}</td>
            <td>${alumno.apellido}</td>
            <td>${alumno.telefono ?? ""}</td>
            <td>${alumno.direccion ?? ""}</td>
            <td>${alumno.fechaRegistro ?? ""}</td>
            <td>${badgeEstado}</td>
            <td>
                <button class="btn-accion edit"
                    onclick='abrirEditar(${JSON.stringify(alumno)})'>
                    <i class="ti ti-edit"></i> Editar
                </button>
                <button class="btn-accion del"
                    onclick="eliminarAlumno(${alumno.idAlumno})">
                    <i class="ti ti-trash"></i> Eliminar
                </button>
            </td>
        </tr>`;
    });

    tabla += `</tbody></table>`;
    $("#listaAlumnos").html(tabla);
}

cargarAlumnos();


// ── OVERLAY AGREGAR ────────────────────────────────────────────────

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


// ── GUARDAR ALUMNO ─────────────────────────────────────────────────

$("#formAlumno").on("submit", function (e) {
    e.preventDefault();
    ocultarMensaje();
    if (!condicionesAlumno()) return;

    let alumno = {
        nombre:    $("#nombre").val().trim(),
        apellido:  $("#apellido").val().trim(),
        telefono:  $("#telefono").val().trim(),
        direccion: $("#direccion").val().trim(),
        estado:    $("#estado").val() === "true"
    };

    fetch("AlumnoServlet", {
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
        } else {
            mostrarFracaso(data.mensaje);
        }
    })
    .catch(() => mostrarFracaso("Ocurrió un error al guardar el alumno"));
});


// ── OVERLAY EDITAR ─────────────────────────────────────────────────

function abrirEditar(alumno) {
    $("#editIdAlumno").val(alumno.idAlumno);
    $("#editNombre").val(alumno.nombre);
    $("#editApellido").val(alumno.apellido);
    $("#editTelefono").val(alumno.telefono ?? "");
    $("#editDireccion").val(alumno.direccion ?? "");
    $("#editEstado").val(alumno.estado ? "true" : "false");
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

$("#formEditarAlumno").on("submit", function (e) {
    e.preventDefault();

    let alumno = {
        idAlumno:      parseInt($("#editIdAlumno").val()),
        nombre:        $("#editNombre").val().trim(),
        apellido:      $("#editApellido").val().trim(),
        telefono:      $("#editTelefono").val().trim(),
        direccion:     $("#editDireccion").val().trim(),
        estado:        $("#editEstado").val() === "true",
        fechaRegistro: $("#editFechaRegistro").val()
    };

    fetch("AlumnoServlet", {
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
                .show();
            $("#mensajeAlertaEditar").text(data.mensaje);
        }
    })
    .catch(() => {
        $("#alertaEditar")
            .removeClass("alerta-exito").addClass("alerta-error")
            .show();
        $("#mensajeAlertaEditar").text("Ocurrió un error al actualizar el alumno");
    });
});


// ── ELIMINAR ALUMNO ────────────────────────────────────────────────

function eliminarAlumno(id) {
    if (!confirm("¿Estás seguro de que deseas eliminar este alumno?")) return;

    fetch("AlumnoServlet?idAlumno=" + id, {
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


// ── VALIDACIONES ───────────────────────────────────────────────────

function condicionesAlumno() {
    let nombre    = $("#nombre").val().trim();
    let apellido  = $("#apellido").val().trim();
    let telefono  = $("#telefono").val().trim();
    let direccion = $("#direccion").val().trim();

    limpiarErrores();

    if (nombre === "") {
        marcarError("#nombre");
        mostrarFracaso("El nombre no puede estar vacío");
        return false;
    }
    if (apellido === "") {
        marcarError("#apellido");
        mostrarFracaso("El apellido no puede estar vacío");
        return false;
    }
    if (telefono !== "" && !/^[0-9\-+() ]{7,20}$/.test(telefono)) {
        marcarError("#telefono");
        mostrarFracaso("Formato de teléfono inválido. Ejemplo válido: 7777-7777");
        return false;
    }
    if (direccion.length > 150) {
        marcarError("#direccion");
        mostrarFracaso("La dirección no puede tener más de 150 caracteres");
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


// ── MENSAJES ───────────────────────────────────────────────────────

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