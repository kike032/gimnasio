package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.ues.edu.modelo.Usuario;
import com.ues.edu.services.UsuarioServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    private UsuarioServiceImpl service = new UsuarioServiceImpl();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Usuario> usuarios = service.listar();

            List<Map<String, Object>> lista = new ArrayList<>();

            for (Usuario u : usuarios) {
                Map<String, Object> item = new HashMap<>();

                item.put("idUsuario", u.getIdUsuario());
                item.put("idRol", u.getRol() != null ? u.getRol().getIdRol() : null);
                item.put("nombre", u.getNombre());
                item.put("correo", u.getCorreo());
                item.put("clave", u.getClave());
                item.put("estado", u.isEstado());

                lista.add(item);
            }

            responderJson(response, lista);

        } catch (Exception e) {
            e.printStackTrace();
            responderMensaje(response, "Error al listar usuarios: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Map<String, Object> datos = gson.fromJson(request.getReader(), Map.class);

            Usuario usuario = construirUsuario(datos);
            Integer idRol = obtenerEntero(datos.get("idRol"));

            String validacion = validarUsuario(usuario, idRol);

            if (validacion != null) {
                responderMensaje(response, validacion);
                return;
            }

            service.guardar(usuario, idRol);

            responderMensaje(response, "Usuario guardado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            responderMensaje(response, "Error al guardar usuario: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Map<String, Object> datos = gson.fromJson(request.getReader(), Map.class);

            Usuario usuario = construirUsuario(datos);
            Integer idUsuario = obtenerEntero(datos.get("idUsuario"));
            Integer idRol = obtenerEntero(datos.get("idRol"));

            usuario.setIdUsuario(idUsuario);

            if (usuario.getIdUsuario() == null) {
                responderMensaje(response, "El ID del usuario es obligatorio");
                return;
            }

            String validacion = validarUsuario(usuario, idRol);

            if (validacion != null) {
                responderMensaje(response, validacion);
                return;
            }

            service.actualizar(usuario, idRol);

            responderMensaje(response, "Usuario actualizado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            responderMensaje(response, "Error al actualizar usuario: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("idUsuario");

            if (id == null || id.trim().isEmpty()) {
                responderMensaje(response, "El ID del usuario es obligatorio");
                return;
            }

            service.eliminar(Integer.parseInt(id));

            responderMensaje(response, "Usuario eliminado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            responderMensaje(response, "Error al eliminar usuario: " + e.getMessage());
        }
    }

    private Usuario construirUsuario(Map<String, Object> datos) {
        Usuario usuario = new Usuario();

        usuario.setNombre((String) datos.get("nombre"));
        usuario.setCorreo((String) datos.get("correo"));
        usuario.setClave((String) datos.get("clave"));

        Object estadoObj = datos.get("estado");
        usuario.setEstado(estadoObj == null || Boolean.parseBoolean(estadoObj.toString()));

        return usuario;
    }

    private Integer obtenerEntero(Object valor) {
        if (valor == null) {
            return null;
        }

        if (valor instanceof Double) {
            return ((Double) valor).intValue();
        }

        return Integer.parseInt(valor.toString());
    }

    private String validarUsuario(Usuario usuario, Integer idRol) {
        if (usuario == null) {
            return "Los datos del usuario son obligatorios";
        }

        if (idRol == null) {
            return "Debe ingresar el rol del usuario";
        }

        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            return "El nombre no puede estar vacío";
        }

        if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
            return "El correo no puede estar vacío";
        }

        if (!usuario.getCorreo().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            return "Formato de correo inválido";
        }

        if (usuario.getClave() == null || usuario.getClave().trim().isEmpty()) {
            return "La clave no puede estar vacía";
        }

        return null;
    }

    private void responderJson(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(data));
    }

    private void responderMensaje(HttpServletResponse response, String mensaje) throws IOException {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);
        responderJson(response, respuesta);
    }
}