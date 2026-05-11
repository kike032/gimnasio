package com.ues.edu.controlador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ues.edu.modelo.Rol;
import com.ues.edu.services.RolServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RolServlet", urlPatterns = {"/RolServlet"})
public class RolServlet extends HttpServlet {

    private RolServiceImpl service = new RolServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new GsonBuilder().create();

        List<Rol> lista = service.listar();

        List<Map<String, Object>> rolesJson = new ArrayList<>();

        for (Rol r : lista) {
            Map<String, Object> item = new HashMap<>();

            item.put("idRol", r.getIdRol());
            item.put("nombreRol", r.getNombreRol());
            item.put("descripcion", r.getDescripcion());

            rolesJson.add(item);
        }

        response.getWriter().print(gson.toJson(rolesJson));
    }

    @Override
    public String getServletInfo() {
        return "RolServlet";
    }
}