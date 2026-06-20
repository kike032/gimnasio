package com.ues.edu.services;

import com.ues.edu.Dao.AlumnoDao;
import com.ues.edu.modelo.Alumno;
import java.util.List;

public class AlumnoServiceImpl {

    private AlumnoDao dao = new AlumnoDao();

    public List<Alumno> listar() {
        return dao.listar();
    }

    public void guardar(Alumno alumno) {
        dao.guardar(alumno);
    }

    public void actualizar(Alumno alumno) {
        dao.actualizar(alumno);
    }

    public void eliminar(int id) {
        dao.eliminar(id);
    }
}