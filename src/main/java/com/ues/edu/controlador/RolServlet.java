/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ues.edu.controlador;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ues.edu.modelo.Rol;
import com.ues.edu.services.RolServiceImpl;
import com.ues.edu.util.LocalDateAdapter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;


/**
 *
 * @author kikej
 */
@WebServlet(name = "RolServlet", urlPatterns = {"/RolServlet"})
public class RolServlet extends HttpServlet {
    
    
    private RolServiceImpl RolService = new RolServiceImpl();

    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

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
            out.println("<title>Servlet RolServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RolServlet at " + request.getContextPath() + "</h1>");
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
        String idParam = request.getParameter("id");

        //  GET por ID (detalle)
        if (idParam != null && !idParam.isEmpty()) {

            int id = Integer.parseInt(idParam);
            Rol rol = RolService.obtenerProductoPorId(id);


            if (rol == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"mensaje\":\"Producto no encontrado\"}");
                return;
            }

            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(rol));
            return;
        }

        //  GET paginado + búsqueda
        int pagina = request.getParameter("pagina") == null ? 1
                : Integer.parseInt(request.getParameter("pagina"));

        int size = request.getParameter("size") == null ? 5
                : Integer.parseInt(request.getParameter("size"));

        String busqueda = request.getParameter("busqueda");

        List<Rol> rol = RolService.obtenerRolesPaginados(pagina, size, busqueda);

        response.setContentType("application/json");
        response.getWriter().write(gson.toJson(rol));
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
         Rol rol = gson.fromJson(request.getReader(), Rol.class);

         RolService.registrarProducto(rol);

        response.setContentType("application/json");
        response.getWriter().write("{\"mensaje\":\"Producto guardado\"}");
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
