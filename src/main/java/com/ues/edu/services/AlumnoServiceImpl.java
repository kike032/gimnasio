/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.services;

import com.ues.edu.Dao.AlumnoDao;
import com.ues.edu.modelo.Alumno;
import java.util.List;
/**
 *
 * @author kikej
 */


public class AlumnoServiceImpl {

    private AlumnoDao dao = new AlumnoDao();

    public List<Alumno> listar() {
        return dao.listar();
    }
}
