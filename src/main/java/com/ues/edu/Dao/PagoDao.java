/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

/**
 *
 * @author kikej
 */



public class PagoDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");

    public List<Object[]> listar() {
        EntityManager em = emf.createEntityManager();

        List<Object[]> lista = em.createQuery(
                "SELECT p.idPago, "
                + "p.inscripcion.alumno.nombre, "
                + "p.inscripcion.alumno.apellido, "
                + "p.inscripcion.servicio.nombreServicio, "
                + "p.monto, "
                + "p.fechaPago, "
                + "p.metodoPago, "
                + "p.estadoPago "
                + "FROM Pago p",
                Object[].class)
                .getResultList();

        em.close();

        return lista;
    }
}
