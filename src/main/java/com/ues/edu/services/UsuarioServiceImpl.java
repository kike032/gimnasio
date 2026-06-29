package com.ues.edu.services;

import com.ues.edu.Dao.UsuarioDao;
import com.ues.edu.modelo.Usuario;
import java.util.List;

public class UsuarioServiceImpl {

    private UsuarioDao dao = new UsuarioDao();

    public List<Usuario> listar() {
        return dao.listar();
    }

    public void guardar(Usuario usuario, Integer idRol) {
        dao.guardar(usuario, idRol);
    }

    public void actualizar(Usuario usuario, Integer idRol) {
        dao.actualizar(usuario, idRol);
    }

    public void eliminar(int idUsuario) {
        dao.eliminar(idUsuario);
    }
}