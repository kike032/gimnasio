/*
 * alumno.js — Gestión de Alumnos
 */

function cargarAlumnos() {
    fetch("AlumnoServlet")
        .then(res => res.json())
        .then(pintarTabla)
        .catch(error => {
            console.error("Error al cargar alumnos:", error);
        });
}

function pintarTabla(lista) {

    if ($("#listaAlumnos").length === 0) {
        return;
    }

    let tabla = `
    <table class="table table-bordered table-striped table-hover">
        <tr class="table-dark">
            <th>#</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Teléfono</th>
            <th>Dirección</th>
            <th>Fecha Registro</th>
            <th>Estado</th>
        </tr>`;

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

    tabla += "</table>";

    $("#listaAlumnos").html(tabla);
}


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