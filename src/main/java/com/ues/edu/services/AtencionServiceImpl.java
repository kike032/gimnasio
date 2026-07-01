/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.services;

import com.ues.edu.Dao.AtencionDao;
import com.ues.edu.modelo.Atencion;
import java.util.List;
/**
 *
 * @author kikej
 */

public class AtencionServiceImpl {

    private AtencionDao dao = new AtencionDao();

    public List<Object[]> listar() {
        return dao.listar();
    }

    public boolean guardar(Atencion atencion, Integer idAlumno, Integer idUsuario, Integer idInscripcion) {
        return dao.guardar(atencion, idAlumno, idUsuario, idInscripcion);
    }

    public boolean modificar(Atencion atencion, Integer idAlumno, Integer idUsuario, Integer idInscripcion) {
        return dao.modificar(atencion, idAlumno, idUsuario, idInscripcion);
    }

    public boolean eliminar(Integer idAtencion) {
        return dao.eliminar(idAtencion);
    }
}