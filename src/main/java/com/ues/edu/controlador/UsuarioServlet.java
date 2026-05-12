package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ues.edu.services.UsuarioServiceImpl;
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

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    private UsuarioServiceImpl service = new UsuarioServiceImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UsuarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UsuarioServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().create();

        List<Object[]> lista = service.listar();

        List<Map<String, Object>> usuariosJson = new ArrayList<>();

        for (Object[] fila : lista) {
            Map<String, Object> item = new HashMap<>();

            item.put("idUsuario", fila[0]);
            item.put("rol", fila[1]);
            item.put("nombre", fila[2]);
            item.put("correo", fila[3]);
            item.put("estado", fila[4]);

            usuariosJson.add(item);
        }

        response.getWriter().print(gson.toJson(usuariosJson));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "UsuarioServlet";
    }
}