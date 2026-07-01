package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ues.edu.modelo.Atencion;
import com.ues.edu.services.AtencionServiceImpl;
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

@WebServlet(name = "AtencionServlet", urlPatterns = {"/AtencionServlet"})
public class AtencionServlet extends HttpServlet {

    private AtencionServiceImpl service = new AtencionServiceImpl();
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Object[]> lista = service.listar();
        List<Map<String, Object>> atencionesJson = new ArrayList<>();

        for (Object[] fila : lista) {
            Map<String, Object> item = new HashMap<>();
            item.put("idAtencion", fila[0]);
            String nombreAl = fila[1] != null ? fila[1].toString() : "";
            String apellidoAl = fila[2] != null ? fila[2].toString() : "";
            item.put("alumno", nombreAl + " " + apellidoAl);
            item.put("usuario", fila[3] != null ? fila[3].toString() : "");
            item.put("inscripcion", "Inscripción #" + fila[4]);
            item.put("fechaAtencion", fila[5] != null ? fila[5].toString() : "");
            item.put("descripcion", fila[6] != null ? fila[6].toString() : "");
            item.put("tipoAtencion", fila[7] != null ? fila[7].toString() : "");
            
            item.put("idAlumno", fila[8]);
            item.put("idUsuario", fila[9]);
            item.put("idInscripcion", fila[10]);

            atencionesJson.add(item);
        }
        response.getWriter().print(gson.toJson(atencionesJson));
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
            Integer idUsuario = ((Double) datos.get("idUsuario")).intValue();
            Integer idInscripcion = ((Double) datos.get("idInscripcion")).intValue();
            String descripcion = (String) datos.get("descripcion");
            String tipoAtencion = (String) datos.get("tipoAtencion");

            Atencion nueva = new Atencion();
            nueva.setDescripcion(descripcion);
            nueva.setTipoAtencion(tipoAtencion);
            nueva.setFechaAtencion(LocalDate.now());

            boolean exito = service.guardar(nueva, idAlumno, idUsuario, idInscripcion);
            respuesta.put("mensaje", exito ? "Atención guardada correctamente" : "No se pudo guardar la atención.");
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

            Integer idAtencion = ((Double) datos.get("idAtencion")).intValue();
            Integer idAlumno = ((Double) datos.get("idAlumno")).intValue();
            Integer idUsuario = ((Double) datos.get("idUsuario")).intValue();
            Integer idInscripcion = ((Double) datos.get("idInscripcion")).intValue();
            String descripcion = (String) datos.get("descripcion");
            String tipoAtencion = (String) datos.get("tipoAtencion");
            String fechaStr = (String) datos.get("fechaAtencion");

            Atencion editada = new Atencion();
            editada.setIdAtencion(idAtencion);
            editada.setDescripcion(descripcion);
            editada.setTipoAtencion(tipoAtencion);
            editada.setFechaAtencion(fechaStr != null ? LocalDate.parse(fechaStr) : LocalDate.now());

            boolean exito = service.modificar(editada, idAlumno, idUsuario, idInscripcion);
            respuesta.put("mensaje", exito ? "Atención actualizada correctamente" : "No se pudo actualizar.");
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

        String idParam = request.getParameter("idAtencion");
        if (idParam != null) {
            try {
                boolean exito = service.eliminar(Integer.parseInt(idParam));
                respuesta.put("mensaje", exito ? "Atención eliminada correctamente" : "No se pudo eliminar.");
            } catch (Exception e) {
                respuesta.put("mensaje", "ID inválido");
            }
        } else {
            respuesta.put("mensaje", "Falta el parámetro idAtencion");
        }
        response.getWriter().print(gson.toJson(respuesta));
    }
}