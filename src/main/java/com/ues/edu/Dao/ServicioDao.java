/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.Dao;

import com.ues.edu.modelo.Servicio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
/**
 *
 * @author kikej
 */


public class ServicioDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");

    public List<Servicio> listar() {
        EntityManager em = emf.createEntityManager();

        List<Servicio> lista = em.createQuery("SELECT s FROM Servicio s", Servicio.class)
                .getResultList();

        em.close();

        return lista;
    }
}
