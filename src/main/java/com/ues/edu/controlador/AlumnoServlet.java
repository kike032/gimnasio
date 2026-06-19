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
            /*
             * No usamos gson.fromJson(request.getReader(), Alumno.class)
             * porque la entidad Alumno tiene relaciones con clases que usan LocalTime.
             */
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

            /*
             * idAlumno automático.
             * fechaRegistro automática.
             * usuario null porque no quieres pedir id_usuario en el formulario.
             */
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