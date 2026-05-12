/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.services;

import com.ues.edu.Dao.AtencionDao;
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
}