package com.ues.edu.Dao;

import com.ues.edu.modelo.Atencion;
import com.ues.edu.modelo.Alumno;
import com.ues.edu.modelo.Usuario;
import com.ues.edu.modelo.Inscripcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class AtencionDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("proyecto");

    public List<Object[]> listar() {
        EntityManager em = emf.createEntityManager();
        List<Object[]> lista = em.createQuery(
                "SELECT a.idAtencion, "
                + "a.alumno.nombre, "
                + "a.alumno.apellido, "
                + "a.usuario.nombre, " // O el campo de nombre del usuario que utilices
                + "a.inscripcion.idInscripcion, "
                + "a.fechaAtencion, "
                + "a.descripcion, "
                + "a.tipoAtencion, "
                + "a.alumno.idAlumno, "      // fila[8]
                + "a.usuario.idUsuario, "    // fila[9]
                + "a.inscripcion.idInscripcion " // fila[10]
                + "FROM Atencion a", Object[].class)
                .getResultList();
        em.close();
        return lista;
    }

    public boolean guardar(Atencion atencion, Integer idAlumno, Integer idUsuario, Integer idInscripcion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Alumno al = em.find(Alumno.class, idAlumno);
            Usuario us = em.find(Usuario.class, idUsuario);
            Inscripcion ins = em.find(Inscripcion.class, idInscripcion);

            if (al == null || us == null || ins == null) return false;

            atencion.setAlumno(al);
            atencion.setUsuario(us);
            atencion.setInscripcion(ins);

            em.persist(atencion);
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

    public boolean modificar(Atencion atencion, Integer idAlumno, Integer idUsuario, Integer idInscripcion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Alumno al = em.find(Alumno.class, idAlumno);
            Usuario us = em.find(Usuario.class, idUsuario);
            Inscripcion ins = em.find(Inscripcion.class, idInscripcion);

            if (al == null || us == null || ins == null) return false;

            atencion.setAlumno(al);
            atencion.setUsuario(us);
            atencion.setInscripcion(ins);

            em.merge(atencion);
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

    public boolean eliminar(Integer idAtencion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Atencion aten = em.find(Atencion.class, idAtencion);
            if (aten != null) {
                em.remove(aten);
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