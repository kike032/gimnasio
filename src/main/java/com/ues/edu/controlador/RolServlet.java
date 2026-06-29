package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.ues.edu.modelo.Rol;
import com.ues.edu.services.RolServiceImpl;
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

@WebServlet(name = "RolServlet", urlPatterns = {"/RolServlet"})
public class RolServlet extends HttpServlet {

    private RolServiceImpl service = new RolServiceImpl();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<Rol> roles = service.listar();

            List<Map<String, Object>> lista = new ArrayList<>();

            for (Rol r : roles) {
                Map<String, Object> item = new HashMap<>();

                item.put("idRol", r.getIdRol());
                item.put("nombreRol", r.getNombreRol());
                item.put("descripcion", r.getDescripcion());
                item.put("estado", r.isEstado());

                lista.add(item);
            }

            responderJson(response, lista);

        } catch (Exception e) {
            e.printStackTrace();
            responderMensaje(response, "Error al listar roles: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Rol rol = gson.fromJson(request.getReader(), Rol.class);

            String validacion = validarRol(rol);

            if (validacion != null) {
                responderMensaje(response, validacion);
                return;
            }

            service.guardar(rol);

            responderMensaje(response, "Rol guardado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            responderMensaje(response, "Error al guardar rol: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Rol rol = gson.fromJson(request.getReader(), Rol.class);

            if (rol == null || rol.getIdRol() == null) {
                responderMensaje(response, "El ID del rol es obligatorio");
                return;
            }

            String validacion = validarRol(rol);

            if (validacion != null) {
                responderMensaje(response, validacion);
                return;
            }

            service.actualizar(rol);

            responderMensaje(response, "Rol actualizado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            responderMensaje(response, "Error al actualizar rol: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String id = request.getParameter("idRol");

            if (id == null || id.trim().isEmpty()) {
                responderMensaje(response, "El ID del rol es obligatorio");
                return;
            }

            service.eliminar(Integer.parseInt(id));

            responderMensaje(response, "Rol eliminado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
            responderMensaje(response, "Error al eliminar rol: " + e.getMessage());
        }
    }

    private String validarRol(Rol rol) {
        if (rol == null) {
            return "Los datos del rol son obligatorios";
        }

        if (rol.getNombreRol() == null || rol.getNombreRol().trim().isEmpty()) {
            return "El nombre del rol no puede estar vacío";
        }

        if (rol.getNombreRol().length() > 50) {
            return "El nombre del rol no puede tener más de 50 caracteres";
        }

        if (rol.getDescripcion() != null && rol.getDescripcion().length() > 150) {
            return "La descripción no puede tener más de 150 caracteres";
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