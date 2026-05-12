/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.services;

import com.ues.edu.Dao.ServicioDao;
import com.ues.edu.modelo.Servicio;
import java.util.List;
/**
 *
 * @author kikej
 */


public class ServicioServiceImpl {

    private ServicioDao dao = new ServicioDao();

    public List<Servicio> listar() {
        return dao.listar();
    }
}
