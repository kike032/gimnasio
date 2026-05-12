/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.services;

import com.ues.edu.Dao.ComprobanteDao;
import com.ues.edu.modelo.Comprobante;
import java.util.List;
/**
 *
 * @author kikej
 */
public class ComprobanteServiceImpl {

    private ComprobanteDao dao = new ComprobanteDao();

    public List<Comprobante> listar() {
        return dao.listar();
    }
}