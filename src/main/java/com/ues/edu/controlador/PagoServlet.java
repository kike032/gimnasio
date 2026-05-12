/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ues.edu.services.PagoServiceImpl;
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
@WebServlet(name = "PagoServlet", urlPatterns = {"/PagoServlet"})
public class PagoServlet extends HttpServlet {

        private PagoServiceImpl service = new PagoServiceImpl();

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
            out.println("<title>Servlet PagoServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PagoServlet at " + request.getContextPath() + "</h1>");
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

        List<Object[]> lista = service.listar();

        List<Map<String, Object>> pagosJson = new ArrayList<>();

        for (Object[] fila : lista) {
            Map<String, Object> item = new HashMap<>();

            item.put("idPago", fila[0]);

            String nombre = fila[1] != null ? fila[1].toString() : "";
            String apellido = fila[2] != null ? fila[2].toString() : "";
            item.put("alumno", nombre + " " + apellido);

            item.put("servicio", fila[3]);

            if (fila[4] != null) {
                item.put("monto", fila[4].toString());
            } else {
                item.put("monto", "");
            }

            if (fila[5] != null) {
                item.put("fechaPago", fila[5].toString());
            } else {
                item.put("fechaPago", "");
            }

            item.put("metodoPago", fila[6]);
            item.put("estadoPago", fila[7]);

            pagosJson.add(item);
        }

        response.getWriter().print(gson.toJson(pagosJson));
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
