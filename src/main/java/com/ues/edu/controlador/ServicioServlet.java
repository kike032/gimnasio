/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ues.edu.modelo.Servicio;
import com.ues.edu.services.ServicioServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kikej
 */
@WebServlet(name = "ServicioServlet", urlPatterns = {"/ServicioServlet"})
public class ServicioServlet extends HttpServlet {

       private ServicioServiceImpl service = new ServicioServiceImpl();
       private Gson gson = new Gson();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ServicioServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ServicioServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
 
         try {
            List<Servicio> servicios = service.listar();

            List<Map<String, Object>> lista = new ArrayList<>();

            for (Servicio s : servicios) {
                Map<String, Object> item = new HashMap<>();
                item.put("idServicio", s.getIdServicio());
                item.put("nombreServicio", s.getNombreServicio());
                item.put("descripcion", s.getDescripcion());
                item.put("precio", s.getPrecio());
                item.put("estado", s.isEstado());
                lista.add(item);
            }

            responderJson(response, lista);

        } catch (Exception e) {
            responderMensaje(response, "Error al listar servicios: " + e.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         try {
            Servicio servicio = gson.fromJson(request.getReader(), Servicio.class);

            String validacion = validarServicio(servicio);

            if (validacion != null) {
                responderMensaje(response, validacion);
                return;
            }

            service.guardar(servicio);
            responderMensaje(response, "Servicio guardado correctamente");

        } catch (Exception e) {
            responderMensaje(response, "Error al guardar servicio: " + e.getMessage());
        }
    }
    
    
      @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Servicio servicio = gson.fromJson(request.getReader(), Servicio.class);

            if (servicio.getIdServicio() == null) {
                responderMensaje(response, "El ID del servicio es obligatorio");
                return;
            }

            String validacion = validarServicio(servicio);

            if (validacion != null) {
                responderMensaje(response, validacion);
                return;
            }

            service.actualizar(servicio);
            responderMensaje(response, "Servicio actualizado correctamente");

        } catch (Exception e) {
            responderMensaje(response, "Error al actualizar servicio: " + e.getMessage());
        }
    }

    
    
     @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String id = request.getParameter("idServicio");

            if (id == null || id.trim().isEmpty()) {
                responderMensaje(response, "El ID del servicio es obligatorio");
                return;
            }

            service.eliminar(Integer.parseInt(id));
            responderMensaje(response, "Servicio eliminado correctamente");

        } catch (Exception e) {
            responderMensaje(response, "Error al eliminar servicio: " + e.getMessage());
        }
    }

    private String validarServicio(Servicio servicio) {
        if (servicio == null) {
            return "Los datos del servicio son obligatorios";
        }

        if (servicio.getNombreServicio() == null || servicio.getNombreServicio().trim().isEmpty()) {
            return "El nombre del servicio no puede estar vacío";
        }

        if (servicio.getPrecio() == null) {
            return "El precio no puede estar vacío";
        }

        if (servicio.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            return "El precio debe ser mayor a cero";
        }

        return null;
    }

    private void responderJson(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(data));
    }
    
    
    private void responderMensaje(HttpServletResponse response, String mensaje) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("mensaje", mensaje);

        response.getWriter().write(gson.toJson(respuesta));
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
