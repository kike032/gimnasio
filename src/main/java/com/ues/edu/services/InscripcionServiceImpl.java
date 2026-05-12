/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.services;

import com.ues.edu.Dao.InscripcionDao;
import java.util.List;


/**
 *
 * @author kikej
 */

public class InscripcionServiceImpl {

    private InscripcionDao dao = new InscripcionDao();

    public List<Object[]> listar() {
        return dao.listar();
    }
}