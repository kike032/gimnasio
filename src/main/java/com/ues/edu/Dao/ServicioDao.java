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

    /*
     * IMPORTANTE:
     * Cambiá "ProyectoGimnasioPU" por el nombre real que tengas
     * en tu archivo persistence.xml
     */
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("ProyectoGimnasioPU");

    public List<Servicio> listar() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT s FROM Servicio s WHERE s.estado = true ORDER BY s.idServicio DESC",
                    Servicio.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    public void guardar(Servicio servicio) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(servicio);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void actualizar(Servicio servicio) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Servicio servicioExistente = em.find(Servicio.class, servicio.getIdServicio());

            if (servicioExistente != null) {
                servicioExistente.setNombreServicio(servicio.getNombreServicio());
                servicioExistente.setDescripcion(servicio.getDescripcion());
                servicioExistente.setPrecio(servicio.getPrecio());
                servicioExistente.setEstado(servicio.isEstado());

                em.merge(servicioExistente);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public void eliminar(int idServicio) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Servicio servicio = em.find(Servicio.class, idServicio);

            if (servicio != null) {
                servicio.setEstado(false);
                em.merge(servicio);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
