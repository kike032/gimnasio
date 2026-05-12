/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ues.edu.modelo.Alumno;
import com.ues.edu.modelo.Asistencia;
import com.ues.edu.modelo.Rol;
import com.ues.edu.services.AlumnoServiceImpl;
import com.ues.edu.services.AsistenciaServiceImpl;
import com.ues.edu.services.RolServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author kikej
 */
@WebServlet(name = "AsistenciaServlet", urlPatterns = {"/AsistenciaServlet"})
public class AsistenciaServlet extends HttpServlet {

    
    private AsistenciaServiceImpl service = new AsistenciaServiceImpl();
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
            out.println("<title>Servlet AsistenciaServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AsistenciaServlet at " + request.getContextPath() + "</h1>");
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

        Gson gson = new GsonBuilder().create();

        List<Asistencia> lista = service.listar();

        List<Map<String, Object>> asistenciasJson = new ArrayList<>();

        for (Asistencia a : lista) {
            Map<String, Object> item = new HashMap<>();

            item.put("idAsistencia", a.getIdAsistencia());

            if (a.getAlumno() != null) {
                item.put("idAlumno", a.getAlumno().getIdAlumno());
                item.put("alumno", a.getAlumno().getNombre() + " " + a.getAlumno().getApellido());
            } else {
                item.put("idAlumno", "");
                item.put("alumno", "");
            }

            if (a.getServicio() != null) {
                item.put("idServicio", a.getServicio().getIdServicio());
            } else {
                item.put("idServicio", "");
            }

            if (a.getPago() != null) {
                item.put("idPago", a.getPago().getIdPago());
            } else {
                item.put("idPago", "");
            }

            if (a.getFechaAsistencia() != null) {
                item.put("fechaAsistencia", a.getFechaAsistencia().toString());
            } else {
                item.put("fechaAsistencia", "");
            }

            if (a.getHoraEntrada() != null) {
                item.put("horaEntrada", a.getHoraEntrada().toString());
            } else {
                item.put("horaEntrada", "");
            }

            if (a.getHoraSalida() != null) {
                item.put("horaSalida", a.getHoraSalida().toString());
            } else {
                item.put("horaSalida", "");
            }

            asistenciasJson.add(item);
        }

        response.getWriter().print(gson.toJson(asistenciasJson));
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
        processRequest(request, response);
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
