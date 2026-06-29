let editandoUsuario = false;

$(document).ready(function () {
    console.log("usuario.js cargado correctamente");

    cargarUsuarios();

    $("#formUsuario").on("submit", function (e) {
        e.preventDefault();
        guardarUsuario();
    });

    $("#formUsuario").on("reset", function () {
        setTimeout(function () {
            limpiarFormularioUsuario();
            ocultarMensajeUsuario();
            limpiarErroresUsuario();
        }, 50);
    });
});

function cargarUsuarios() {
    fetch(window.APP_CONTEXT + "/UsuarioServlet")
        .then(res => res.json())
        .then(data => {
            console.log("Usuarios recibidos:", data);

            if (Array.isArray(data)) {
                pintarTablaUsuarios(data);
            } else {
                $("#listaUsuarios").html(`
                    <div class="text-center p-3 text-danger">
                        ${data.mensaje ?? "Error al cargar usuarios"}
                    </div>
                `);
            }
        })
        .catch(error => {
            console.error("Error al cargar usuarios:", error);

            $("#listaUsuarios").html(`
                <div class="text-center p-3 text-danger">
                    Error al cargar usuarios.
                </div>
            `);
        });
}

function pintarTablaUsuarios(lista) {
    let tabla = `
        <table class="table table-bordered table-striped table-hover">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>ID Rol</th>
                    <th>Nombre</th>
                    <th>Correo</th>
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
                    No hay usuarios registrados.
                </td>
            </tr>
        `;
    } else {
        lista.forEach(usuario => {
            let objetoUsuario = JSON.stringify(usuario).replace(/'/g, "&#39;");

            tabla += `
                <tr>
                    <td>${usuario.idUsuario}</td>
                    <td>${usuario.idRol ?? ""}</td>
                    <td>${usuario.nombre}</td>
                    <td>${usuario.correo}</td>
                    <td>
                        <span class="badge ${usuario.estado ? "bg-success" : "bg-danger"}">
                            ${usuario.estado ? "Activo" : "Inactivo"}
                        </span>
                    </td>
                    <td>
                        <button type="button" class="btn btn-warning btn-sm" onclick='editarUsuario(${objetoUsuario})'>
                            <i class="ti ti-edit"></i> Editar
                        </button>

                        <button type="button" class="btn btn-danger btn-sm" onclick="eliminarUsuario(${usuario.idUsuario})">
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

    $("#listaUsuarios").html(tabla);
}

function guardarUsuario() {
    ocultarMensajeUsuario();
    limpiarErroresUsuario();

    if (condicionesUsuario() === false) {
        return;
    }

    let usuario = {
        idRol: parseInt($("#idRol").val()),
        nombre: $("#nombre").val().trim(),
        correo: $("#correo").val().trim(),
        clave: $("#clave").val().trim(),
        estado: $("#estado").val() === "true"
    };

    let metodo = "POST";

    if (editandoUsuario) {
        usuario.idUsuario = parseInt($("#idUsuario").val());
        metodo = "PUT";
    }

    fetch(window.APP_CONTEXT + "/UsuarioServlet", {
        method: metodo,
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(usuario)
    })
    .then(res => res.json())
    .then(data => {
        if (
            data.mensaje === "Usuario guardado correctamente" ||
            data.mensaje === "Usuario actualizado correctamente"
        ) {
            mostrarExitoUsuario(data.mensaje);
            limpiarFormularioUsuario();
            cargarUsuarios();
        } else {
            mostrarFracasoUsuario(data.mensaje);
        }
    })
    .catch(error => {
        console.error("Error al guardar:", error);
        mostrarFracasoUsuario("Error al guardar usuario.");
    });
}

function editarUsuario(usuario) {
    editandoUsuario = true;

    $("#idUsuario").val(usuario.idUsuario);
    $("#idRol").val(usuario.idRol);
    $("#nombre").val(usuario.nombre);
    $("#correo").val(usuario.correo);
    $("#clave").val(usuario.clave);
    $("#estado").val(usuario.estado ? "true" : "false");

    $("#btnGuardarUsuario").html(`
        <i class="ti ti-device-floppy"></i>
        Actualizar usuario
    `);

    window.scrollTo({
        top: 0,
        behavior: "smooth"
    });
}

function eliminarUsuario(idUsuario) {
    if (!confirm("¿Estás seguro de eliminar este usuario?")) {
        return;
    }

    fetch(window.APP_CONTEXT + "/UsuarioServlet?idUsuario=" + idUsuario, {
        method: "DELETE"
    })
    .then(res => res.json())
    .then(data => {
        if (data.mensaje === "Usuario eliminado correctamente") {
            mostrarExitoUsuario(data.mensaje);
            cargarUsuarios();
        } else {
            mostrarFracasoUsuario(data.mensaje);
        }
    })
    .catch(error => {
        console.error("Error al eliminar:", error);
        mostrarFracasoUsuario("Error al eliminar usuario.");
    });
}

function condicionesUsuario() {
    let idRol = $("#idRol").val().trim();
    let nombre = $("#nombre").val().trim();
    let correo = $("#correo").val().trim();
    let clave = $("#clave").val().trim();

    limpiarErroresUsuario();

    if (idRol === "") {
        marcarErrorUsuario("#idRol");
        mostrarFracasoUsuario("Debe ingresar el rol del usuario");
        return false;
    }

    if (nombre === "") {
        marcarErrorUsuario("#nombre");
        mostrarFracasoUsuario("El nombre no puede estar vacío");
        return false;
    }

    if (correo === "") {
        marcarErrorUsuario("#correo");
        mostrarFracasoUsuario("El correo no puede estar vacío");
        return false;
    }

    let expresionCorreo = /^[\w.-]+@[\w.-]+\.[a-zA-Z]{2,}$/;

    if (!expresionCorreo.test(correo)) {
        marcarErrorUsuario("#correo");
        mostrarFracasoUsuario("Formato de correo inválido");
        return false;
    }

    if (clave === "") {
        marcarErrorUsuario("#clave");
        mostrarFracasoUsuario("La clave no puede estar vacía");
        return false;
    }

    return true;
}

function limpiarFormularioUsuario() {
    editandoUsuario = false;

    $("#idUsuario").val("");
    $("#idRol").val("");
    $("#nombre").val("");
    $("#correo").val("");
    $("#clave").val("");
    $("#estado").val("true");

    $("#btnGuardarUsuario").html(`
        <i class="ti ti-device-floppy"></i>
        Guardar usuario
    `);
}

function mostrarExitoUsuario(mensaje) {
    $("#alertaUsuario")
        .removeClass("alerta-error")
        .addClass("alerta-exito")
        .show();

    $("#mensajeAlertaUsuario").text(mensaje);
}

function mostrarFracasoUsuario(mensaje) {
    $("#alertaUsuario")
        .removeClass("alerta-exito")
        .addClass("alerta-error")
        .show();

    $("#mensajeAlertaUsuario").text(mensaje);
}

function ocultarMensajeUsuario() {
    $("#alertaUsuario").hide();
    $("#mensajeAlertaUsuario").text("");
}

function marcarErrorUsuario(campo) {
    $(campo).closest(".input-ic").addClass("input-error");
}

function limpiarErroresUsuario() {
    $(".input-ic").removeClass("input-error");
}