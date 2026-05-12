/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.Dao;

import com.ues.edu.modelo.Asistencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author kikej
 */

public class AsistenciaDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");

    public List<Asistencia> listar() {
        EntityManager em = emf.createEntityManager();

        List<Asistencia> lista = em.createQuery(
                "SELECT a FROM Asistencia a "
                + "JOIN FETCH a.alumno "
                + "JOIN FETCH a.servicio "
                + "JOIN FETCH a.pago", Asistencia.class)
                .getResultList();

        em.close();

        return lista;
    }
}