package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ues.edu.modelo.Alumno;
import com.ues.edu.services.AlumnoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AlumnoServlet", urlPatterns = {"/AlumnoServlet"})
public class AlumnoServlet extends HttpServlet {

    private AlumnoServiceImpl service = new AlumnoServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            Gson gson = new GsonBuilder().create();

            List<Alumno> lista = service.listar();
            List<Map<String, Object>> alumnosJson = new ArrayList<>();

            for (Alumno a : lista) {
                Map<String, Object> item = new HashMap<>();

                item.put("idAlumno", a.getIdAlumno());
                item.put("nombre", a.getNombre());
                item.put("apellido", a.getApellido());
                item.put("telefono", a.getTelefono());
                item.put("direccion", a.getDireccion());
                item.put("fechaRegistro",
                        a.getFechaRegistro() != null
                                ? a.getFechaRegistro().toString()
                                : "");
                item.put("estado", a.isEstado());

                alumnosJson.add(item);
            }

            response.getWriter().print(gson.toJson(alumnosJson));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribirMensaje(response, "Error al listar alumnos: " + limpiarMensaje(e));
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            JsonObject json = JsonParser.parseReader(request.getReader()).getAsJsonObject();

            Alumno alumno = new Alumno();

            alumno.setNombre(obtenerTexto(json, "nombre"));
            alumno.setApellido(obtenerTexto(json, "apellido"));
            alumno.setTelefono(obtenerTexto(json, "telefono"));
            alumno.setDireccion(obtenerTexto(json, "direccion"));

            if (json.has("estado") && !json.get("estado").isJsonNull()) {
                alumno.setEstado(json.get("estado").getAsBoolean());
            } else {
                alumno.setEstado(true);
            }

            String error = validarAlumno(alumno);

            if (error != null) {
                escribirMensaje(response, error);
                return;
            }

            alumno.setIdAlumno(null);
            alumno.setFechaRegistro(LocalDate.now());
            alumno.setUsuario(null);

            service.guardar(alumno);

            escribirMensaje(response, "Alumno guardado correctamente");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribirMensaje(response, "Error al guardar alumno: " + limpiarMensaje(e));
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            JsonObject json = JsonParser.parseReader(request.getReader()).getAsJsonObject();

            if (!json.has("idAlumno") || json.get("idAlumno").isJsonNull()) {
                escribirMensaje(response, "ID del alumno requerido");
                return;
            }

            int id = json.get("idAlumno").getAsInt();

            Alumno alumno = new Alumno();
            alumno.setIdAlumno(id);
            alumno.setNombre(obtenerTexto(json, "nombre"));
            alumno.setApellido(obtenerTexto(json, "apellido"));
            alumno.setTelefono(obtenerTexto(json, "telefono"));
            alumno.setDireccion(obtenerTexto(json, "direccion"));
            alumno.setEstado(json.has("estado") && !json.get("estado").isJsonNull()
                    ? json.get("estado").getAsBoolean() : true);

            if (json.has("fechaRegistro") && !json.get("fechaRegistro").isJsonNull()
                    && !json.get("fechaRegistro").getAsString().isEmpty()) {
                alumno.setFechaRegistro(LocalDate.parse(json.get("fechaRegistro").getAsString()));
            } else {
                alumno.setFechaRegistro(LocalDate.now());
            }

            alumno.setUsuario(null);

            String error = validarAlumno(alumno);
            if (error != null) { escribirMensaje(response, error); return; }

            service.actualizar(alumno);
            escribirMensaje(response, "Alumno actualizado correctamente");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribirMensaje(response, "Error al actualizar alumno: " + limpiarMensaje(e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String idParam = request.getParameter("idAlumno");

            if (idParam == null || idParam.trim().isEmpty()) {
                escribirMensaje(response, "ID del alumno requerido");
                return;
            }

            int id = Integer.parseInt(idParam);
            service.eliminar(id);
            escribirMensaje(response, "Alumno eliminado correctamente");

        } catch (NumberFormatException e) {
            escribirMensaje(response, "ID inválido");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escribirMensaje(response, "Error al eliminar alumno: " + limpiarMensaje(e));
        }
    }

    private String obtenerTexto(JsonObject json, String campo) {
        if (!json.has(campo) || json.get(campo).isJsonNull()) {
            return "";
        }
        return json.get(campo).getAsString().trim();
    }

    private String validarAlumno(Alumno alumno) {

        if (alumno.getNombre() == null || alumno.getNombre().trim().isEmpty()) {
            return "El nombre no puede estar vacío";
        }

        if (alumno.getApellido() == null || alumno.getApellido().trim().isEmpty()) {
            return "El apellido no puede estar vacío";
        }

        if (alumno.getTelefono() != null && !alumno.getTelefono().trim().isEmpty()) {
            if (!alumno.getTelefono().matches("^[0-9\\-+() ]{7,20}$")) {
                return "Formato de teléfono inválido. Ejemplo válido: 7777-7777";
            }
        }

        if (alumno.getDireccion() != null && alumno.getDireccion().length() > 150) {
            return "La dirección no puede tener más de 150 caracteres";
        }

        return null;
    }

    private void escribirMensaje(HttpServletResponse response, String mensaje) throws IOException {
        Gson gson = new GsonBuilder().create();

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);

        response.getWriter().write(gson.toJson(respuesta));
    }

    private String limpiarMensaje(Exception e) {
        String mensaje = e.getMessage();

        if (mensaje == null || mensaje.trim().isEmpty()) {
            mensaje = "Error desconocido";
        }

        mensaje = mensaje.replace("\n", " ");
        mensaje = mensaje.replace("\r", " ");
        mensaje = mensaje.replace("\"", "'");

        return mensaje;
    }

    @Override
    public String getServletInfo() {
        return "AlumnoServlet";
    }
}