/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ues.edu.Dao;

import com.ues.edu.modelo.Alumno;
import com.ues.edu.modelo.Asistencia;
import com.ues.edu.modelo.Pago;
import com.ues.edu.modelo.Servicio;
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

    public List<Object[]> listar() {
        EntityManager em = emf.createEntityManager();
        List<Object[]> lista = em.createQuery(
                "SELECT a.idAsistencia, "
                + "a.alumno.nombre, "
                + "a.alumno.apellido, "
                + "a.servicio.nombreServicio, "
                + "a.pago.idPago, " // O el campo correlativo del pago que uses
                + "a.fechaAsistencia, "
                + "a.horaEntrada, "
                + "a.horaSalida, "
                + "a.alumno.idAlumno, "  // [8]
                + "a.servicio.idServicio, " // [9]
                + "a.pago.idPago " // [10]
                + "FROM Asistencia a", Object[].class)
                .getResultList();
        em.close();
        return lista;
    }

    public boolean guardar(Asistencia asistencia, Integer idAlumno, Integer idServicio, Integer idPago) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Alumno al = em.find(Alumno.class, idAlumno);
            Servicio ser = em.find(Servicio.class, idServicio);
            Pago pag = em.find(Pago.class, idPago);

            if (al == null || ser == null || pag == null) return false;

            asistencia.setAlumno(al);
            asistencia.setServicio(ser);
            asistencia.setPago(pag);

            em.persist(asistencia);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean modificar(Asistencia asistencia, Integer idAlumno, Integer idServicio, Integer idPago) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Alumno al = em.find(Alumno.class, idAlumno);
            Servicio ser = em.find(Servicio.class, idServicio);
            Pago pag = em.find(Pago.class, idPago);

            if (al == null || ser == null || pag == null) return false;

            asistencia.setAlumno(al);
            asistencia.setServicio(ser);
            asistencia.setPago(pag);

            em.merge(asistencia);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    public boolean eliminar(Integer idAsistencia) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Asistencia asis = em.find(Asistencia.class, idAsistencia);
            if (asis != null) {
                em.remove(asis);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
}