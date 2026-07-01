package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ues.edu.modelo.Asistencia;
import com.ues.edu.services.AsistenciaServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AsistenciaServlet", urlPatterns = {"/AsistenciaServlet"})
public class AsistenciaServlet extends HttpServlet {

    private AsistenciaServiceImpl service = new AsistenciaServiceImpl();
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Object[]> lista = service.listar();
        List<Map<String, Object>> jsonList = new ArrayList<>();

        for (Object[] fila : lista) {
            Map<String, Object> item = new HashMap<>();
            item.put("idAsistencia", fila[0]);
            String nombre = fila[1] != null ? fila[1].toString() : "";
            String apellido = fila[2] != null ? fila[2].toString() : "";
            item.put("alumno", nombre + " " + apellido);
            item.put("servicio", fila[3]);
            item.put("pago", "Ref. Pago #" + fila[4]);
            item.put("fechaAsistencia", fila[5] != null ? fila[5].toString() : "");
            item.put("horaEntrada", fila[6] != null ? fila[6].toString() : "");
            item.put("horaSalida", fila[7] != null ? fila[7].toString() : "");
            
            item.put("idAlumno", fila[8]);
            item.put("idServicio", fila[9]);
            item.put("idPago", fila[10]);

            jsonList.add(item);
        }
        response.getWriter().print(gson.toJson(jsonList));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> respuesta = new HashMap<>();

        try {
            BufferedReader reader = request.getReader();
            Type type = new TypeToken<Map<String, Object>>(){}.getType();
            Map<String, Object> datos = gson.fromJson(reader, type);

            Integer idAlumno = ((Double) datos.get("idAlumno")).intValue();
            Integer idServicio = ((Double) datos.get("idServicio")).intValue();
            Integer idPago = ((Double) datos.get("idPago")).intValue();
            
            String horaEntradaStr = (String) datos.get("horaEntrada");
            String horaSalidaStr = (String) datos.get("horaSalida");

            Asistencia nueva = new Asistencia();
            nueva.setFechaAsistencia(LocalDate.now()); // Automatizado a la fecha de hoy
            nueva.setHoraEntrada(horaEntradaStr != null && !horaEntradaStr.isEmpty() ? LocalTime.parse(horaEntradaStr) : LocalTime.now());
            if (horaSalidaStr != null && !horaSalidaStr.isEmpty()) {
                nueva.setHoraSalida(LocalTime.parse(horaSalidaStr));
            }

            boolean exito = service.guardar(nueva, idAlumno, idServicio, idPago);
            respuesta.put("mensaje", exito ? "Asistencia guardada correctamente" : "No se pudo guardar la asistencia.");
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

            Integer idAsistencia = ((Double) datos.get("idAsistencia")).intValue();
            Integer idAlumno = ((Double) datos.get("idAlumno")).intValue();
            Integer idServicio = ((Double) datos.get("idServicio")).intValue();
            Integer idPago = ((Double) datos.get("idPago")).intValue();
            String fechaStr = (String) datos.get("fechaAsistencia");
            String horaEntradaStr = (String) datos.get("horaEntrada");
            String horaSalidaStr = (String) datos.get("horaSalida");

            Asistencia editada = new Asistencia();
            editada.setIdAsistencia(idAsistencia);
            editada.setFechaAsistencia(fechaStr != null ? LocalDate.parse(fechaStr) : LocalDate.now());
            if (horaEntradaStr != null) editada.setHoraEntrada(LocalTime.parse(horaEntradaStr));
            if (horaSalidaStr != null && !horaSalidaStr.isEmpty()) editada.setHoraSalida(LocalTime.parse(horaSalidaStr));

            boolean exito = service.modificar(editada, idAlumno, idServicio, idPago);
            respuesta.put("mensaje", exito ? "Asistencia actualizada correctamente" : "No se pudo actualizar la asistencia.");
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

        String idParam = request.getParameter("idAsistencia");
        if (idParam != null) {
            try {
                boolean exito = service.eliminar(Integer.parseInt(idParam));
                respuesta.put("mensaje", exito ? "Asistencia eliminada correctamente" : "No se pudo eliminar.");
            } catch (Exception e) {
                respuesta.put("mensaje", "ID inválido");
            }
        } else {
            respuesta.put("mensaje", "Falta el parámetro idAsistencia");
        }
        response.getWriter().print(gson.toJson(respuesta));
    }
}