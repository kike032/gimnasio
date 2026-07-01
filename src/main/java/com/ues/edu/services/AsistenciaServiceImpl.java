/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.ues.edu.services;

import com.ues.edu.Dao.AsistenciaDao;
import com.ues.edu.modelo.Asistencia;
import java.util.List;
/**
 *
 * @author kikej
 */

public class AsistenciaServiceImpl {

    private AsistenciaDao dao = new AsistenciaDao();

    public List<Object[]> listar() {
        return dao.listar();
    }

    public boolean guardar(Asistencia asistencia, Integer idAlumno, Integer idServicio, Integer idPago) {
        return dao.guardar(asistencia, idAlumno, idServicio, idPago);
    }

    public boolean modificar(Asistencia asistencia, Integer idAlumno, Integer idServicio, Integer idPago) {
        return dao.modificar(asistencia, idAlumno, idServicio, idPago);
    }

    public boolean eliminar(Integer idAsistencia) {
        return dao.eliminar(idAsistencia);
    }
}