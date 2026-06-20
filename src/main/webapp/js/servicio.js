/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */



$("#formServicio").on("submit", function (e) {
    e.preventDefault();

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

    fetch(window.APP_CONTEXT + "/ServicioServlet", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(servicio)
    })
    .then(res => res.json())
    .then(data => {

        if (data.mensaje === "Servicio guardado correctamente") {
            mostrarExitoServicio(data.mensaje);
            $("#formServicio")[0].reset();
            limpiarErroresServicio();
        } else {
            mostrarFracasoServicio(data.mensaje);
        }

    })
    .catch(error => {
        console.error("Error:", error);
        mostrarFracasoServicio("Ocurrió un error al guardar el servicio");
    });
});

    tabla += "</table>";

// ── validaciones  ─────────

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




$("#formServicio").on("reset", function () {
    setTimeout(function () {
        ocultarMensajeServicio();
        limpiarErroresServicio();
    }, 50);
});