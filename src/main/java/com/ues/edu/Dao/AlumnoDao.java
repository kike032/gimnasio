/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.Dao;

/**
 *
 * @author kikej
 */

import com.ues.edu.modelo.Alumno;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class AlumnoDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");

    public List<Alumno> listar() {
        EntityManager em = emf.createEntityManager();

        List<Alumno> lista = em.createQuery("SELECT a FROM Alumno a", Alumno.class)
                .getResultList();

        em.close();

        return lista;
    }
}