package com.ues.edu.Dao;

import com.ues.edu.modelo.Inscripcion;
import com.ues.edu.modelo.Alumno;
import com.ues.edu.modelo.Servicio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class InscripcionDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");

    public List<Object[]> listar() {
        EntityManager em = emf.createEntityManager();
        // Agregamos los IDs al SELECT para que el Front-End sepa qué fila se está editando
        List<Object[]> lista = em.createQuery(
                "SELECT i.idInscripcion, "
                + "i.alumno.nombre, "
                + "i.alumno.apellido, "
                + "i.servicio.nombreServicio, "
                + "i.fechaInscripcion, "
                + "i.estado, "
                + "i.alumno.idAlumno, " // fila[6]
                + "i.servicio.idServicio " // fila[7]
                + "FROM Inscripcion i",
                Object[].class)
                .getResultList();
        em.close();
        return lista;
    }

    public boolean guardar(Inscripcion inscripcion, Integer idAlumno, Integer idServicio) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            // Buscamos las referencias de las entidades en la BD para no subirlos vacíos
            Alumno al = em.find(Alumno.class, idAlumno);
            Servicio ser = em.find(Servicio.class, idServicio);
            
            if (al == null || ser == null) {
                return false;
            }
            
            inscripcion.setAlumno(al);
            inscripcion.setServicio(ser);
            
            em.persist(inscripcion);
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

    public boolean modificar(Inscripcion inscripcion, Integer idAlumno, Integer idServicio) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            Alumno al = em.find(Alumno.class, idAlumno);
            Servicio ser = em.find(Servicio.class, idServicio);
            
            if (al == null || ser == null) {
                return false;
            }
            
            inscripcion.setAlumno(al);
            inscripcion.setServicio(ser);
            
            em.merge(inscripcion);
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

    public boolean eliminar(Integer idInscripcion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Inscripcion ins = em.find(Inscripcion.class, idInscripcion);
            if (ins != null) {
                em.remove(ins);
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