package com.ues.edu.services;

import com.ues.edu.Dao.RolDao;
import com.ues.edu.modelo.Rol;
import java.util.List;

public class RolServiceImpl {

    private RolDao dao = new RolDao();

    public List<Rol> listar() {
        return dao.listar();
    }
}