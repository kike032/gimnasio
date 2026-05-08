/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

let paginaActual = 1;
const size = 5;

// Buscar
function buscar() {
    paginaActual = 1;
    cargarProductos();
}

//  Cargar productos
function cargarProductos() {
    

    let busqueda = $("#busqueda").val();

    fetch(`RolServlet?pagina=${paginaActual}&size=${size}&busqueda=${busqueda}`)
            .then(res => res.json())
            .then(rol => {

                let html = `
<table class="table table-bordered table-striped">
    <thead class="table-dark">
        <tr>
            <th>Nombre</th>
            <th>descripcion</th>
        </tr>
    </thead>
  
`;
                rol.forEach(rol => {
                    html += `
                <tr>
                       <td>${producto.nombre}</td>
                        <td>${producto.descripcion}</td>
                </tr>`;
                });

                html += "</table>";

                $("#listaRol").html(html);

                renderPaginacion();
            });
}

//  Paginación
function renderPaginacion() {
    $("#paginacion").html(`
        <button onclick="anterior()" class="btn btn-secondary">Anterior</button>
        Página ${paginaActual}
        <button onclick="siguiente()" class="btn btn-secondary">Siguiente</button>
    `);
}

function siguiente() {
    paginaActual++;
    cargarProductos();
}

function anterior() {
    if (paginaActual > 1) {
        paginaActual--;
        cargarProductos();
    }
}


function editar (id){
    
    fetch(`RolServlet?id=${id}`)
            .then(res => res.json())
            .then(p =>{
              $("#nombre").val(p.nombre);
              $("#descripcion").val(p.descrpcion);    
            });
}



// Guardar o actualizar
$("#formRol").submit(function (e) {
    e.preventDefault();

    let id = $(this).data("id");//Guarda el ID internamente en el formulario

//es un objeto de JavaScript.
    let rol = {
        id: id,
        nombre: $("#nombre").val(),
        precio: $("#descripcion").val(),
        
    };
    
    

//Si id tiene valor (true) → "PUT" → actualizar
//Si id es null, undefined o vacío (false) → "POST" → crear
    let metodo = id ? "PUT" : "POST";//DEBE SER PUT y POST

    fetch("rolServlet", {
        method: metodo,
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(producto)//convierte un objeto java script a texto Json
    })
            .then(() => {
                $("#FormRol")[0].reset();//limpia el formulario
                $("#FormRol").removeData("id");
                cargarProductos();
            });
});

//  Eliminar
function eliminar(id) {
    if (!confirm("¿Eliminar producto?"))
        return;

    fetch(`RolServlet?id=${id}`, {
        method: "DELETE"//Debe ser DELETE
    })
            .then(() => cargarProductos());
}




// Inicial
$(document).ready(function () {
    cargarProductos();
});


