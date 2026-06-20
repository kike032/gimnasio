package com.ues.edu.services;

import com.ues.edu.Dao.InscripcionDao;
import com.ues.edu.modelo.Inscripcion;
import java.util.List;

public class InscripcionServiceImpl {

    private InscripcionDao dao = new InscripcionDao();

    public List<Object[]> listar() {
        return dao.listar();
    }

    public boolean guardar(Inscripcion inscripcion, Integer idAlumno, Integer idServicio) {
        return dao.guardar(inscripcion, idAlumno, idServicio);
    }

    public boolean modificar(Inscripcion inscripcion, Integer idAlumno, Integer idServicio) {
        return dao.modificar(inscripcion, idAlumno, idServicio);
    }

    public boolean eliminar(Integer idInscripcion) {
        return dao.eliminar(idInscripcion);
    }
}