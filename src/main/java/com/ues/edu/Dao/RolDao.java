package com.ues.edu.Dao;

import com.ues.edu.modelo.Rol;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class RolDao {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("proyecto");

    public List<Rol> listar() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery(
                    "SELECT r FROM Rol r ORDER BY r.idRol DESC",
                    Rol.class
            ).getResultList();

        } finally {
            em.close();
        }
    }

    public void guardar(Rol rol) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            em.persist(rol);

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

    public void actualizar(Rol rol) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Rol rolExistente = em.find(Rol.class, rol.getIdRol());

            if (rolExistente != null) {
                rolExistente.setNombreRol(rol.getNombreRol());
                rolExistente.setDescripcion(rol.getDescripcion());
                rolExistente.setEstado(rol.isEstado());

                em.merge(rolExistente);
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

    public void eliminar(int idRol) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Rol rol = em.find(Rol.class, idRol);

            if (rol != null) {
                rol.setEstado(false);
                em.merge(rol);
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