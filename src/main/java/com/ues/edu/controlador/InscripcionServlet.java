package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ues.edu.modelo.Inscripcion;
import com.ues.edu.services.InscripcionServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "InscripcionServlet", urlPatterns = {"/InscripcionServlet"})
public class InscripcionServlet extends HttpServlet {

    private InscripcionServiceImpl service = new InscripcionServiceImpl();
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Object[]> lista = service.listar();
        List<Map<String, Object>> inscripcionesJson = new ArrayList<>();

        for (Object[] fila : lista) {
            Map<String, Object> item = new HashMap<>();
            item.put("idInscripcion", fila[0]);

            String nombre = fila[1] != null ? fila[1].toString() : "";
            String apellido = fila[2] != null ? fila[2].toString() : "";
            item.put("alumno", nombre + " " + apellido);
            item.put("servicio", fila[3]);
            item.put("fechaInscripcion", fila[4] != null ? fila[4].toString() : "");
            item.put("estado", fila[5]);
            
            // Atributos clave agregados para que el Front-End asocie los combos al editar
            item.put("idAlumno", fila[6]);
            item.put("idServicio", fila[7]);

            inscripcionesJson.add(item);
        }

        response.getWriter().print(gson.toJson(inscripcionesJson));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> respuesta = new HashMap<>();

        try {
            // Leer JSON enviado por Fetch
            BufferedReader reader = request.getReader();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> datos = gson.fromJson(reader, type);

            // Extraer IDs de relaciones
            Integer idAlumno = ((Double) datos.get("idAlumno")).intValue();
            Integer idServicio = ((Double) datos.get("idServicio")).intValue();
            Boolean estado = (Boolean) datos.get("estado");

            // Instanciar Entidad Base
            Inscripcion nuevaInscripcion = new Inscripcion();
            nuevaInscripcion.setEstado(estado);
            nuevaInscripcion.setFechaInscripcion(LocalDate.now()); // Asignación automática de fecha actual

            boolean exito = service.guardar(nuevaInscripcion, idAlumno, idServicio);

            if (exito) {
                respuesta.put("mensaje", "Inscripción guardada correctamente");
            } else {
                respuesta.put("mensaje", "No se pudo guardar la inscripción. Verifique los datos.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            respuesta.put("mensaje", "Error interno: " + e.getMessage());
        }

        response.getWriter().print(gson.toJson(respuesta));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> respuesta = new HashMap<>();

        try {
            BufferedReader reader = request.getReader();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> datos = gson.fromJson(reader, type);

            Integer idInscripcion = ((Double) datos.get("idInscripcion")).intValue();
            Integer idAlumno = ((Double) datos.get("idAlumno")).intValue();
            Integer idServicio = ((Double) datos.get("idServicio")).intValue();
            Boolean estado = (Boolean) datos.get("estado");
            String fechaStr = (String) datos.get("fechaInscripcion");

            Inscripcion inscripcionEditada = new Inscripcion();
            inscripcionEditada.setIdInscripcion(idInscripcion);
            inscripcionEditada.setEstado(estado);
            
            if (fechaStr != null && !fechaStr.isEmpty()) {
                inscripcionEditada.setFechaInscripcion(LocalDate.parse(fechaStr));
            } else {
                inscripcionEditada.setFechaInscripcion(LocalDate.now());
            }

            boolean exito = service.modificar(inscripcionEditada, idAlumno, idServicio);

            if (exito) {
                respuesta.put("mensaje", "Inscripción actualizada correctamente");
            } else {
                respuesta.put("mensaje", "No se pudo actualizar la inscripción.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            respuesta.put("mensaje", "Error al editar: " + e.getMessage());
        }

        response.getWriter().print(gson.toJson(respuesta));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> respuesta = new HashMap<>();

        String idParam = request.getParameter("idInscripcion");

        if (idParam != null) {
            try {
                Integer id = Integer.parseInt(idParam);
                boolean exito = service.eliminar(id);
                if (exito) {
                    respuesta.put("mensaje", "Inscripción eliminada correctamente");
                } else {
                    respuesta.put("mensaje", "La inscripción no existe o no pudo ser eliminada");
                }
            } catch (NumberFormatException e) {
                respuesta.put("mensaje", "ID inválido");
            }
        } else {
            respuesta.put("mensaje", "Falta el parámetro idInscripcion");
        }

        response.getWriter().print(gson.toJson(respuesta));
    }
}