/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.services;

/**
 *
 * @author DELL LATITUDE
 */
import com.ues.edu.Dao.RolDao;
import com.ues.edu.modelo.Rol;
import java.util.List;


public class RolServiceImpl {

    private RolDao dao = new RolDao();

    public void registrarProducto(Rol p) {
        dao.guardar(p);
    }

    public void actualizarProducto(Rol p) {
        dao.actualizar(p);
    }

    public void eliminarProducto(int id) {
        dao.eliminar(id);
    }

    public List<Rol> obtenerRolesPaginados(int pagina, int size, String busqueda) {
        return dao.listarPaginado(pagina, size, busqueda);
    }
    
  public Rol obtenerProductoPorId(int id) {
    return dao.buscarPorId(id);
}
}
