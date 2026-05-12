package com.ues.edu.services;

import com.ues.edu.Dao.UsuarioDao;
import java.util.List;

public class UsuarioServiceImpl {

    private UsuarioDao dao = new UsuarioDao();

    public List<Object[]> listar() {
        return dao.listar();
    }
}