/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.Dao;

import com.ues.edu.modelo.Comprobante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
/**
 *
 * @author kikej
 */



public class ComprobanteDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");

    public List<Comprobante> listar() {
        EntityManager em = emf.createEntityManager();

        List<Comprobante> lista = em.createQuery("SELECT c FROM Comprobante c", Comprobante.class)
                .getResultList();

        em.close();

        return lista;
    }
}
